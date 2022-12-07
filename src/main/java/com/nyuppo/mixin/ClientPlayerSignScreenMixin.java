package com.nyuppo.mixin;

import com.nyuppo.TheLittleThings;
import com.nyuppo.TheLittleThingsClient;
import com.nyuppo.block.entity.HangingSignBlockEntity;
import com.nyuppo.client.gui.HangingSignEditScreen;
import com.nyuppo.util.PlayerEntitySignScreenAccess;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.recipebook.ClientRecipeBook;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.stat.StatHandler;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ClientPlayerEntity.class)
public class ClientPlayerSignScreenMixin implements PlayerEntitySignScreenAccess {
    @Final
    @Mutable
    @Shadow
    protected MinecraftClient client;

    @Override
    public void editHangingSignScreen(HangingSignBlockEntity sign) {
        client.setScreen(new HangingSignEditScreen(sign, this.client.shouldFilterText()));
    }
}
