package com.nyuppo.item;

import com.nyuppo.TheLittleThings;
import com.nyuppo.TheLittleThingsClient;
import com.nyuppo.network.TheLittleThingsNetworkingConstants;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;

public class PincerItem extends SwordItem {
    public PincerItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Item.Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.damage(1, attacker, e -> e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND));

        double d = attacker.getX() - target.getX();
        double e = attacker.getY() - target.getY();
        double f = attacker.getZ() - target.getZ();

        if (target instanceof PlayerEntity) {
            PacketByteBuf buf = PacketByteBufs.create();
            buf.writeDouble(d);
            buf.writeDouble(e);
            buf.writeDouble(f);
            ServerPlayNetworking.send((ServerPlayerEntity)target, TheLittleThingsNetworkingConstants.getPincerAttackPacketId(), buf);
        } else {
            target.setVelocity(new Vec3d(d, e + 2.0D, f).multiply(0.15D));
        }

        attacker.world.playSound(null, attacker.getX(), attacker.getY(), attacker.getZ(), SoundEvents.ENCHANT_THORNS_HIT, SoundCategory.PLAYERS, 1.0F, 1.0F);

        return true;
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        if (!user.getItemCooldownManager().isCoolingDown(this)) {
            double d = user.getX() - entity.getX();
            double e = user.getY() - entity.getY();
            double f = user.getZ() - entity.getZ();

            //entity.setVelocity(new Vec3d(d, e + 2.0D, f).multiply(0.15D));
            if (user.world.isClient) {
                PacketByteBuf buf = PacketByteBufs.create();
                buf.writeUuid(entity.getUuid());
                buf.writeDouble(d);
                buf.writeDouble(e);
                buf.writeDouble(f);

                ClientPlayNetworking.send(TheLittleThingsNetworkingConstants.getPincerPullPacketId(), buf);
            }

            user.getItemCooldownManager().set(this, 20);
            if (hand == Hand.MAIN_HAND) {
                user.world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ENCHANT_THORNS_HIT, SoundCategory.PLAYERS, 1.0F, 1.0F);
            }
            return ActionResult.success(user.world.isClient());
        }
        return ActionResult.PASS;
    }
}
