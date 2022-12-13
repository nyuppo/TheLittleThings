package com.nyuppo.mixin;

import com.mojang.authlib.GameProfile;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin {

    // This is obsolete since Minecraft has it built in now. Glad I figured that out after I made it.
    // Left it so it'll still announce who's sleeping
    @Inject(method = "sleep(Lnet/minecraft/util/math/BlockPos;)V", at = @At("RETURN"))
    public void onSleep(BlockPos pos, CallbackInfo info) {
        ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity)((PlayerEntity)(Object)this);
        if (!serverPlayerEntity.getWorld().isDay()) {
            serverPlayerEntity.getWorld().getServer().getPlayerManager().broadcast(Text.literal(serverPlayerEntity.getDisplayName().getString()).append(Text.literal(" went to bed. Sweet dreams.").formatted(Formatting.YELLOW)), false);
        }
    }

    // Heal player when they wake up to a maximum of half of their health

    /*
    @Inject(method = "wakeUp(ZZ)V", at = @At("HEAD"))
    public void onWakeUp(boolean bl, boolean updateSleepingPlayers, CallbackInfo info) {
        ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity)(PlayerEntity)this;

        if (serverPlayerEntity.getHealth() != serverPlayerEntity.getMaxHealth()) {
            float healthToHeal = serverPlayerEntity.getMaxHealth() - serverPlayerEntity.getHealth();
            float halfOfMaxHealth = serverPlayerEntity.getMaxHealth() / 2.0F;
            if (healthToHeal > halfOfMaxHealth) {
                healthToHeal = halfOfMaxHealth;
            }
            serverPlayerEntity.heal(healthToHeal);
        }
    }
    */
}