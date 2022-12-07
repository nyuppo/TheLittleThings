package com.nyuppo.mixin;

import com.nyuppo.TheLittleThings;
import com.nyuppo.util.AnimationStateAccessor;
import net.minecraft.entity.AnimationState;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(AnimationState.class)
public class AnimationStateMixin implements AnimationStateAccessor {
    @Override
    public void setRunning(boolean running, int age) {
        if (running) {
            ((AnimationState)(Object)this).startIfNotRunning(age);
        } else {
            ((AnimationState)(Object)this).stop();
        }
    }
}
