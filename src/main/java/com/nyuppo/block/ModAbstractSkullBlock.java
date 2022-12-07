package com.nyuppo.block;

import com.nyuppo.block.entity.ModBlockEntityType;
import com.nyuppo.block.entity.ModSkullBlockEntity;
import com.nyuppo.registration.ModBlocks;
import net.minecraft.block.AbstractSkullBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.SkullBlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.item.Wearable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class ModAbstractSkullBlock extends BlockWithEntity implements Wearable {
    private final ModSkullBlock.ModSkullType type;

    public ModAbstractSkullBlock(ModSkullBlock.ModSkullType type, Settings settings) {
        super(settings);
        this.type = type;
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new ModSkullBlockEntity(pos, state);
    }

    @Override
    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        if (world.isClient && (state.isOf(ModBlocks.PIGLIN_HEAD) || state.isOf(ModBlocks.PIGLIN_HEAD_WALL))) {
            return ModAbstractSkullBlock.checkType(type, ModBlockEntityType.SKULL, ModSkullBlockEntity::tick);
        }
        return null;
    }

    public ModSkullBlock.ModSkullType getSkullType() { return this.type; }

    @Override
    public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
        return false;
    }
}
