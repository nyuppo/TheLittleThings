package com.nyuppo.mixin;

import com.nyuppo.TheLittleThings;
import net.minecraft.entity.mob.CreeperEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(CreeperEntity.class)
public class CreeperDropMultipleHeadsMixin {
    @Overwrite
    public boolean shouldDropHead() {
        return ((CreeperEntity)(Object)this).shouldRenderOverlay();
    }
}
