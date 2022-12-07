package com.nyuppo.mixin;

import com.nyuppo.registration.ModBlocks;
import com.nyuppo.util.ModBoatType;
import net.minecraft.block.Block;
import net.minecraft.entity.vehicle.BoatEntity;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.Arrays;

@Mixin(BoatEntity.Type.class)
public class BoatTypeMixin {
    @SuppressWarnings("InvokerTarget")
    @Invoker("<init>")
    private static BoatEntity.Type newType(String internalName, int internalId, Block baseBlock, String name) {
        throw new AssertionError();
    }

    @SuppressWarnings("ShadowTarget")
    @Shadow
    @Final
    @Mutable
    private static BoatEntity.Type[] field_7724;

    @SuppressWarnings("UnresolvedMixinReference")
    @Inject(method = "<clinit>", at = @At(value = "FIELD",
            opcode = Opcodes.PUTSTATIC,
            target = "Lnet/minecraft/entity/vehicle/BoatEntity$Type;field_7724:[Lnet/minecraft/entity/vehicle/BoatEntity$Type;",
            shift = At.Shift.AFTER))
    private static void addCustomBoatType(CallbackInfo ci) {
        var types = new ArrayList<>(Arrays.asList(field_7724));
        var last = types.get(types.size() - 1);

        var sakura = newType("SAKURA", last.ordinal() + 1, ModBlocks.SAKURA_PLANKS, "sakura");
        ModBoatType.SAKURA = sakura;
        types.add(sakura);

        var azalea = newType("AZALEA", last.ordinal() + 2, ModBlocks.AZALEA_PLANKS, "azalea");
        ModBoatType.AZALEA = azalea;
        types.add(azalea);

        var willow = newType("WILLOW", last.ordinal() + 3, ModBlocks.WILLOW_PLANKS, "willow");
        ModBoatType.WILLOW = willow;
        types.add(willow);

        var baobab = newType("BAOBAB", last.ordinal() + 4, ModBlocks.BAOBAB_PLANKS, "baobab");
        ModBoatType.BAOBAB = baobab;
        types.add(baobab);

        field_7724 = types.toArray(new BoatEntity.Type[0]);
    }
}
