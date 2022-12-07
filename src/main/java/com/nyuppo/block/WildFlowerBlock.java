package com.nyuppo.block;

import com.nyuppo.block.enums.WildFlowerColour;
import com.nyuppo.block.enums.WildFlowerState;
import com.nyuppo.registration.ModItems;
import com.nyuppo.registration.ModSoundEvents;
import com.nyuppo.util.WildFlowerUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.PlantBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.context.LootContext;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.stat.Stats;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.Pair;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WildFlowerBlock extends PlantBlock {
    protected static final VoxelShape SHAPE = Block.createCuboidShape(3.0D, 0.0D, 3.0D, 13.0D, 9.0D, 13.0D);
    public static final EnumProperty<WildFlowerState> FLOWER_STATE;
    private static final Map<Pair<WildFlowerColour, WildFlowerColour>, WildFlowerColour> FLOWER_COLOUR_MAP;
    private static final Map<Pair<WildFlowerColour, WildFlowerColour>, WildFlowerColour> RARE_FLOWER_COLOUR_MAP;
    private final StatusEffect effectInStew;
    private final int effectInStewDuration;
    private final WildFlowerColour flowerColour;

    public WildFlowerBlock(WildFlowerColour flowerColour, StatusEffect suspiciousStewEffect, int effectDuration, Settings settings) {
        super(settings);
        this.effectInStew = suspiciousStewEffect;
        if (suspiciousStewEffect.isInstant()) {
            this.effectInStewDuration = effectDuration;
        } else {
            this.effectInStewDuration = effectDuration * 20;
        }
        this.flowerColour = flowerColour;
        this.setDefaultState(this.getDefaultState().with(FLOWER_STATE, WildFlowerState.DEFAULT));
    }

    public WildFlowerColour getFlowerColour() {
        return this.flowerColour;
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FLOWER_STATE);
    }

    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        Vec3d vec3d = state.getModelOffset(world, pos);
        return SHAPE.offset(vec3d.x, vec3d.y, vec3d.z);
    }

    public StatusEffect getEffectInStew() {
        return this.effectInStew;
    }

    public int getEffectInStewDuration() {
        return this.effectInStewDuration;
    }

    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (state.get(FLOWER_STATE) == WildFlowerState.DEFAULT && player.getStackInHand(hand).isOf(Items.BONE_MEAL)) {
            world.setBlockState(pos, state.with(FLOWER_STATE, WildFlowerState.SPREADING));

            if (!world.isClient) {
                world.syncWorldEvent(WorldEvents.BONE_MEAL_USED, pos, 0);
            }

            return ActionResult.success(world.isClient);
        }

        return ActionResult.PASS;
    }

    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (state.get(FLOWER_STATE) == WildFlowerState.SPREADING) { // && (world.isRaining() || world.isThundering())
            if (random.nextInt(5) == 0) {
                if (this.spread(world, pos, random)) {
                    world.setBlockState(pos, state.with(FLOWER_STATE, WildFlowerState.GERMINATING), 3);
                }
            }
        } else if (state.get(FLOWER_STATE) == WildFlowerState.BLOOMING) {
            if (random.nextInt(3) == 0) {
                world.setBlockState(pos, state.with(FLOWER_STATE, WildFlowerState.DEFAULT), 3);
                //world.syncWorldEvent(WorldEvents.BONE_MEAL_USED, pos, 0);
                world.playSound((PlayerEntity)null, pos, ModSoundEvents.WILD_FLOWER_BLOOM, SoundCategory.BLOCKS, 1.0F, 1.0F);
            }
        } else if (state.get(FLOWER_STATE) == WildFlowerState.GERMINATING) {
            if (random.nextInt(3) == 0) {
                world.setBlockState(pos, state.with(FLOWER_STATE, WildFlowerState.DEFAULT), 3);
                //world.syncWorldEvent(WorldEvents.BONE_MEAL_USED, pos, 0);
                world.playSound((PlayerEntity)null, pos, ModSoundEvents.WILD_FLOWER_GERMINATE, SoundCategory.BLOCKS, 1.0F, 1.0F);
            }
        }
    }

    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if (!player.isCreative() && !player.isSpectator()) {
            if (state.get(FLOWER_STATE) == WildFlowerState.BLOOMING) {
                if (world.random.nextInt(4) == 0) {
                    ItemStack itemStack = new ItemStack(ModItems.WILD_WEEDS);
                    ItemEntity itemEntity = new ItemEntity(world, (double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D, itemStack);
                    itemEntity.setToDefaultPickupDelay();
                    world.spawnEntity(itemEntity);
                } else {
                    ItemStack itemStack = new ItemStack(WildFlowerUtil.COLOUR_TO_BLOCK_MAP.get(((WildFlowerBlock)(state.getBlock())).getFlowerColour()).asItem());
                    NbtCompound nbt = new NbtCompound();
                    nbt.putBoolean("blooming", true);
                    itemStack.setNbt(nbt);
                    ItemEntity itemEntity = new ItemEntity(world, (double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D, itemStack);
                    itemEntity.setToDefaultPickupDelay();
                    world.spawnEntity(itemEntity);
                }
            } else if (state.get(FLOWER_STATE) == WildFlowerState.GERMINATING) {
                ItemStack itemStack = new ItemStack(WildFlowerUtil.COLOUR_TO_BLOCK_MAP.get(((WildFlowerBlock)(state.getBlock())).getFlowerColour()).asItem());
                NbtCompound nbt = new NbtCompound();
                nbt.putBoolean("germinating", true);
                itemStack.setNbt(nbt);
                ItemEntity itemEntity = new ItemEntity(world, (double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D, itemStack);
                itemEntity.setToDefaultPickupDelay();
                world.spawnEntity(itemEntity);
            }
        }

        super.onBreak(world, pos, state, player);
    }

    @Override
    public void afterBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack stack) {
        if (player.isCreative() || player.isSpectator()) {
            super.afterBreak(world, player, pos, state, blockEntity, stack);
        } else {
            if (state.get(FLOWER_STATE) != WildFlowerState.BLOOMING && state.get(FLOWER_STATE) != WildFlowerState.GERMINATING) {
                super.afterBreak(world, player, pos, state, blockEntity, stack);
            } else {
                player.incrementStat(Stats.MINED.getOrCreateStat(this));
                player.addExhaustion(0.005f);
            }
        }
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
        if (itemStack.hasNbt()) {
            NbtCompound nbt = itemStack.getNbt();
            if (nbt.contains("germinating")) {
                world.setBlockState(pos, state.with(FLOWER_STATE, WildFlowerState.GERMINATING));
            } else if (nbt.contains("blooming")) {
                world.setBlockState(pos, state.with(FLOWER_STATE, WildFlowerState.BLOOMING));
            }
        }
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable BlockView world, List<Text> tooltip, TooltipContext options) {
        super.appendTooltip(stack, world, tooltip, options);
        NbtCompound nbt = stack.getNbt();

        if (nbt != null) {
            if (nbt.contains("germinating")) {
                tooltip.add(Text.translatable("block.wild_flower.germinating").formatted(Formatting.GRAY));
            } else if (nbt.contains("blooming")) {
                tooltip.add(Text.translatable("block.wild_flower.blooming").formatted(Formatting.GRAY));
            }
        }
    }

    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (state.get(FLOWER_STATE) == WildFlowerState.SPREADING) {
            if (random.nextDouble() < 0.1D) {
                double d, e, f;
                d = (double)pos.getX() + world.random.nextDouble();
                e = (double)pos.getY() + world.random.nextDouble();
                f = (double)pos.getZ() + world.random.nextDouble();

                world.addParticle(ParticleTypes.HAPPY_VILLAGER, d, e, f, 0.0D, 0.0D, 0.0D);
            }
        }
    }

    public boolean spread(ServerWorld world, BlockPos pos, Random random) {
        Direction dir = Direction.Type.HORIZONTAL.random(random);
        boolean shouldTryClockwise = random.nextBoolean();

        for (int i = 0; i < 4; i++) {
            if (world.getBlockState(pos.offset(dir, 2)).getBlock() instanceof WildFlowerBlock flowerBlock) {
                WildFlowerColour colour1 = ((WildFlowerBlock)world.getBlockState(pos).getBlock()).getFlowerColour();
                WildFlowerColour newColour = getBreedingColour(colour1, flowerBlock.flowerColour, pos, world, random);

                if (world.getBlockState(pos.offset(dir)).getMaterial().isReplaceable() && this.canPlaceAt(world.getBlockState(pos.offset(dir)), world, pos.offset(dir))) {
                    world.setBlockState(pos.offset(dir), WildFlowerUtil.COLOUR_TO_BLOCK_MAP.get(newColour).getDefaultState().with(FLOWER_STATE, WildFlowerState.BLOOMING), 3);
                    world.syncWorldEvent(WorldEvents.BONE_MEAL_USED, pos.offset(dir), 0);

                    return true;
                }
            }
            dir = shouldTryClockwise ? dir.rotateYClockwise() : dir.rotateYCounterclockwise();
        }

        dir = Direction.Type.HORIZONTAL.random(random);
        for (int i = 0; i < 4; i++) {
            WildFlowerColour colour = ((WildFlowerBlock)world.getBlockState(pos).getBlock()).getFlowerColour();
            WildFlowerColour newColour = getBreedingColour(colour, null, pos, world, random);

            if (world.getBlockState(pos.offset(dir)).getMaterial().isReplaceable() && this.canPlaceAt(world.getBlockState(pos.offset(dir)), world, pos.offset(dir))) {
                world.setBlockState(pos.offset(dir), WildFlowerUtil.COLOUR_TO_BLOCK_MAP.get(newColour).getDefaultState().with(FLOWER_STATE, WildFlowerState.BLOOMING), 3);
                world.syncWorldEvent(WorldEvents.BONE_MEAL_USED, pos.offset(dir), 0);

                return true;
            } else {
                dir = shouldTryClockwise ? dir.rotateYClockwise() : dir.rotateYCounterclockwise();
            }
        }

        return false;
    }

    public WildFlowerColour getBreedingColour(WildFlowerColour colour1, @Nullable WildFlowerColour colour2, BlockPos pos, ServerWorld world, Random random) {
        if (colour1 == WildFlowerColour.FIRE || colour2 == WildFlowerColour.FIRE) {
            return random.nextBoolean() ? WildFlowerColour.RED : WildFlowerColour.ORANGE;
        } else if (colour1 == WildFlowerColour.ICE || colour2 == WildFlowerColour.ICE) {
            return random.nextBoolean() ? WildFlowerColour.LIGHT_BLUE : WildFlowerColour.BLUE;
        } else if (colour1 == WildFlowerColour.GOLDEN || colour2 == WildFlowerColour.GOLDEN) {
            return WildFlowerColour.YELLOW;
        }

        if (random.nextFloat() < 0.008F) {
            return WildFlowerColour.GOLDEN;
        }

        if (world.getBiome(pos).value().getTemperature() <= 0.0F && random.nextFloat() < 0.1F) {
            return WildFlowerColour.ICE;
        } else if (world.getBiome(pos).value().getTemperature() >= 2.0F && random.nextFloat() < 0.1F) {
            return WildFlowerColour.FIRE;
        }

        if (colour2 != null) {
            for (Pair<WildFlowerColour, WildFlowerColour> pair : RARE_FLOWER_COLOUR_MAP.keySet()) {
                if ((pair.getLeft() == colour1 && pair.getRight() == colour2) || (pair.getLeft() == colour2 && pair.getRight() == colour1) && random.nextFloat() < 0.2D) {
                    return RARE_FLOWER_COLOUR_MAP.get(pair);
                }
            }

            for (Pair<WildFlowerColour, WildFlowerColour> pair : FLOWER_COLOUR_MAP.keySet()) {
                if ((pair.getLeft() == colour1 && pair.getRight() == colour2) || (pair.getLeft() == colour2 && pair.getRight() == colour1)) {
                    return FLOWER_COLOUR_MAP.get(pair);
                }
            }
        }

        for (Pair<WildFlowerColour, WildFlowerColour> pair : FLOWER_COLOUR_MAP.keySet()) {
            if (FLOWER_COLOUR_MAP.get(pair) == colour1 && random.nextFloat() < 0.8F) {
                if (colour1 == pair.getLeft() || colour1 == pair.getRight()) {
                    return colour1;
                } else if (colour2 == pair.getLeft() || colour2 == pair.getRight()) {
                    return colour2;
                }

                return random.nextBoolean() ? pair.getLeft() : pair.getRight();
            }
        }

        if (colour2 != null) {
            return random.nextBoolean() ? colour1 : colour2;
        }

        return colour1;
    }

    static {
        FLOWER_STATE = EnumProperty.of("flower_state", WildFlowerState.class);

        FLOWER_COLOUR_MAP = new HashMap<>();
        FLOWER_COLOUR_MAP.put(new Pair<>(WildFlowerColour.RED, WildFlowerColour.BLUE), WildFlowerColour.PURPLE);
        FLOWER_COLOUR_MAP.put(new Pair<>(WildFlowerColour.RED, WildFlowerColour.YELLOW), WildFlowerColour.ORANGE);
        FLOWER_COLOUR_MAP.put(new Pair<>(WildFlowerColour.BLUE, WildFlowerColour.YELLOW), WildFlowerColour.GREEN);
        FLOWER_COLOUR_MAP.put(new Pair<>(WildFlowerColour.WHITE, WildFlowerColour.RED), WildFlowerColour.PINK);
        FLOWER_COLOUR_MAP.put(new Pair<>(WildFlowerColour.WHITE, WildFlowerColour.PURPLE), WildFlowerColour.MAGENTA);
        FLOWER_COLOUR_MAP.put(new Pair<>(WildFlowerColour.WHITE, WildFlowerColour.GREEN), WildFlowerColour.LIME);
        FLOWER_COLOUR_MAP.put(new Pair<>(WildFlowerColour.WHITE, WildFlowerColour.BLUE), WildFlowerColour.LIGHT_BLUE);
        FLOWER_COLOUR_MAP.put(new Pair<>(WildFlowerColour.BLUE, WildFlowerColour.GREEN), WildFlowerColour.CYAN);

        RARE_FLOWER_COLOUR_MAP = new HashMap<>();
        RARE_FLOWER_COLOUR_MAP.put(new Pair<>(WildFlowerColour.RED, WildFlowerColour.RED), WildFlowerColour.BLACK);
        RARE_FLOWER_COLOUR_MAP.put(new Pair<>(WildFlowerColour.BLACK, WildFlowerColour.BLACK), WildFlowerColour.WHITE);
    }
}