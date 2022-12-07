package com.nyuppo.mixin;

import com.nyuppo.util.ModEntityPoseType;
import net.minecraft.entity.EntityPose;
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

@Mixin(EntityPose.class)
public class EntityPoseMixin {
    @SuppressWarnings("InvokerTarget")
    @Invoker("<init>")
    private static EntityPose newPose(String internalName, int internalId) {
        throw new AssertionError();
    }

    @SuppressWarnings("ShadowTarget")
    @Shadow
    @Final
    @Mutable
    private static EntityPose[] field_18083;

    @SuppressWarnings("UnresolvedMixinReference")
    @Inject(method = "<clinit>", at = @At(value = "FIELD",
        opcode = Opcodes.PUTSTATIC,
        target = "Lnet/minecraft/entity/EntityPose;field_18083:[Lnet/minecraft/entity/EntityPose;",
        shift = At.Shift.AFTER))
    private static void addPose(CallbackInfo ci) {
        var poses = new ArrayList<>(Arrays.asList(field_18083));
        var last = poses.get(poses.size() - 1);

        var sitting = newPose("SITTING", last.ordinal() + 1);
        ModEntityPoseType.SITTING = sitting;
        poses.add(sitting);

        field_18083 = poses.toArray(new EntityPose[0]);
    }
}
