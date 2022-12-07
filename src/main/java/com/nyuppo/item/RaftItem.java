package com.nyuppo.item;

import com.nyuppo.entity.vehicle.ChestRaftEntity;
import com.nyuppo.entity.vehicle.RaftEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.entity.vehicle.ChestBoatEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

public class RaftItem extends Item {
    private static final Predicate<Entity> RIDERS;
    private final RaftEntity.Type type;
    private final boolean chest;

    public RaftItem(boolean chest, RaftEntity.Type type, Settings settings) {
        super(settings);
        this.chest = chest;
        this.type = type;
    }

    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        HitResult hitResult = raycast(world, user, RaycastContext.FluidHandling.ANY);
        if (hitResult.getType() == net.minecraft.util.hit.HitResult.Type.MISS) {
            return TypedActionResult.pass(itemStack);
        } else {
            Vec3d vec3d = user.getRotationVec(1.0F);
            double d = 5.0D;
            List<Entity> list = world.getOtherEntities(user, user.getBoundingBox().stretch(vec3d.multiply(5.0D)).expand(1.0D), RIDERS);
            if (!list.isEmpty()) {
                Vec3d vec3d2 = user.getEyePos();
                Iterator var11 = list.iterator();

                while(var11.hasNext()) {
                    Entity entity = (Entity)var11.next();
                    Box box = entity.getBoundingBox().expand((double)entity.getTargetingMargin());
                    if (box.contains(vec3d2)) {
                        return TypedActionResult.pass(itemStack);
                    }
                }
            }

            if (hitResult.getType() == net.minecraft.util.hit.HitResult.Type.BLOCK) {
                RaftEntity raftEntity = this.createEntity(world, hitResult);
                raftEntity.setRaftType(this.type);
                raftEntity.setYaw(user.getYaw());
                if (!world.isSpaceEmpty(raftEntity, raftEntity.getBoundingBox())) {
                    return TypedActionResult.fail(itemStack);
                } else {
                    if (!world.isClient) {
                        world.spawnEntity(raftEntity);
                        world.emitGameEvent(user, GameEvent.ENTITY_PLACE, hitResult.getPos());
                        if (!user.getAbilities().creativeMode) {
                            itemStack.decrement(1);
                        }
                    }

                    user.incrementStat(Stats.USED.getOrCreateStat(this));
                    return TypedActionResult.success(itemStack, world.isClient());
                }
            } else {
                return TypedActionResult.pass(itemStack);
            }
        }
    }

    private RaftEntity createEntity(World world, HitResult hitResult) {
        return (RaftEntity)(this.chest ? new ChestRaftEntity(world, hitResult.getPos().x, hitResult.getPos().y, hitResult.getPos().z) : new RaftEntity(world, hitResult.getPos().x, hitResult.getPos().y, hitResult.getPos().z));
    }

    static {
        RIDERS = EntityPredicates.EXCEPT_SPECTATOR.and(Entity::canHit);
    }
}
