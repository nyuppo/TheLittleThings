package com.nyuppo.mixin;

import com.nyuppo.TheLittleThings;
import com.nyuppo.entity.ModEntityTypes;
import com.nyuppo.entity.passive.CrabEntity;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.passive.CodEntity;
import net.minecraft.entity.passive.PufferfishEntity;
import net.minecraft.entity.passive.SalmonEntity;
import net.minecraft.entity.passive.TurtleEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.registry.Registry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.ArrayList;
import java.util.List;

@Mixin(FishingBobberEntity.class)
public class FishEntitiesMixin {
    Identifier CRAB_SPAWN_EGG = TheLittleThings.ID("crab_spawn_egg");

    @Inject(method = "use(Lnet/minecraft/item/ItemStack;)I", at = @At(value = "INVOKE", target = "Lnet/minecraft/loot/LootManager;getTable(Lnet/minecraft/util/Identifier;)Lnet/minecraft/loot/LootTable;"), cancellable = true, locals = LocalCapture.CAPTURE_FAILEXCEPTION)
    private void onUse(ItemStack usedItem, CallbackInfoReturnable<Integer> ci, PlayerEntity playerEntity) { {
        int chanceBounds = 25;

        Random random = playerEntity.world.random;
        if (random.nextInt(chanceBounds) == 0) { // 25
            if (!((FishingBobberEntity)(Object)this).world.isClient) {
                if (((FishingBobberEntity)(Object)this).world instanceof ServerWorld) {
                    ServerWorld serverWorld = (ServerWorld)((FishingBobberEntity)(Object)this).world;
                    Entity fishEntity;
                    boolean isTurtle = false;
                    boolean isCrab = false;
                    if (random.nextInt(10) == 0) { // 10
                        fishEntity = (PufferfishEntity) EntityType.PUFFERFISH.create(serverWorld, (NbtCompound)null, (Text)null, playerEntity, ((FishingBobberEntity)(Object)this).getBlockPos().up(2), SpawnReason.NATURAL, true, true);
                    } else if (random.nextInt(25) == 0) { // 25
                        if (random.nextInt(2) == 0) { // 2
                            fishEntity = (CrabEntity) ModEntityTypes.CRAB.create(serverWorld, (NbtCompound)null, (Text)null, playerEntity, ((FishingBobberEntity)(Object)this).getBlockPos().up(2), SpawnReason.NATURAL, true, true);
                            isCrab = true;
                        } else {
                            fishEntity = (TurtleEntity)EntityType.TURTLE.create(serverWorld, (NbtCompound)null, (Text)null, playerEntity, ((FishingBobberEntity)(Object)this).getBlockPos().up(2), SpawnReason.NATURAL, true, true);
                            isTurtle = true;
                        }
                    } else {
                        if (random.nextInt(3) == 0) { // 3
                            fishEntity = (SalmonEntity)EntityType.SALMON.create(serverWorld, (NbtCompound)null, (Text)null, playerEntity, ((FishingBobberEntity)(Object)this).getBlockPos().up(2), SpawnReason.NATURAL, true, true);
                        } else {
                            fishEntity = (CodEntity)EntityType.COD.create(serverWorld, (NbtCompound)null, (Text)null, playerEntity, ((FishingBobberEntity)(Object)this).getBlockPos().up(2), SpawnReason.NATURAL, true, true);
                        }
                    }
                    double d = playerEntity.getX() - ((FishingBobberEntity)(Object)this).getX();
                    double e = playerEntity.getY() - ((FishingBobberEntity)(Object)this).getY();
                    double f = playerEntity.getZ() - ((FishingBobberEntity)(Object)this).getZ();
                    fishEntity.setVelocity(new Vec3d(d, e + 2.0D, f).multiply(0.15D));

                    if (isTurtle) {
                        TurtleEntity turtleEntity = (TurtleEntity)fishEntity;
                        turtleEntity.setBaby(true);
                        ((FishingBobberEntity)(Object)this).world.spawnEntity(turtleEntity);
                        List<ItemStack> list = new ArrayList<ItemStack>();
                        list.add(new ItemStack(Items.TURTLE_SPAWN_EGG));
                        Criteria.FISHING_ROD_HOOKED.trigger((ServerPlayerEntity)playerEntity, usedItem, ((FishingBobberEntity)(Object)this), list);
                    } else if (isCrab) {
                        CrabEntity crabEntity = (CrabEntity)fishEntity;
                        crabEntity.setBaby(true);
                        ((FishingBobberEntity)(Object)this).world.spawnEntity(crabEntity);
                        List<ItemStack> list = new ArrayList<ItemStack>();
                        list.add(new ItemStack(Registry.ITEM.get(CRAB_SPAWN_EGG)));
                        Criteria.FISHING_ROD_HOOKED.trigger((ServerPlayerEntity)playerEntity, usedItem, ((FishingBobberEntity)(Object)this), list);
                    } else {
                        ((FishingBobberEntity)(Object)this).world.spawnEntity(fishEntity);
                        List<ItemStack> list = new ArrayList<ItemStack>();
                        list.add(new ItemStack(Items.COD));
                        list.add(new ItemStack(Items.COD_SPAWN_EGG));
                        Criteria.FISHING_ROD_HOOKED.trigger((ServerPlayerEntity)playerEntity, usedItem, ((FishingBobberEntity)(Object)this), list);
                    }

                    playerEntity.world.spawnEntity(new ExperienceOrbEntity(playerEntity.world, playerEntity.getX(), playerEntity.getY() + 0.5D, playerEntity.getZ() + 0.5D, 2));
                    playerEntity.increaseStat((Identifier) Stats.FISH_CAUGHT, 1);
                    ((FishingBobberEntity)(Object)this).discard();
                    ci.setReturnValue(1);
                }
            }
        }
    }}
}
