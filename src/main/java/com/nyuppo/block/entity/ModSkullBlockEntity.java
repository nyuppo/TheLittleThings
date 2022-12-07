package com.nyuppo.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.SkullBlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ModSkullBlockEntity extends BlockEntity {
    private int poweredTicks;
    private boolean powered;

    public ModSkullBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntityType.SKULL, pos, state);
    }

    public static void tick(World world, BlockPos pos, BlockState state, ModSkullBlockEntity blockEntity) {
        if (world.isReceivingRedstonePower(pos)) {
            blockEntity.powered = true;
            ++blockEntity.poweredTicks;
        } else {
            blockEntity.powered = false;
        }

    }

    public float getPoweredTicks(float tickDelta) {
        return this.powered ? (float)this.poweredTicks + tickDelta : (float)this.poweredTicks;
    }
}
