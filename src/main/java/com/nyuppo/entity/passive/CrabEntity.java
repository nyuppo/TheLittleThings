package com.nyuppo.entity.passive;

import com.nyuppo.entity.ModEntityTypes;
import com.nyuppo.registration.ModSoundEvents;
import com.nyuppo.util.tags.ModBlockTags;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.RabbitEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

public class CrabEntity extends AnimalEntity implements IAnimatable {
    private AnimationFactory factory = GeckoLibUtil.createFactory(this);
    private static final Ingredient BREEDING_INGREDIENT = Ingredient.ofItems(Items.SEAGRASS, Items.KELP);

    private static final AnimationBuilder IDLE = new AnimationBuilder().addAnimation("animation.crab.idle", ILoopType.EDefaultLoopTypes.LOOP);
    private static final AnimationBuilder WALK = new AnimationBuilder().addAnimation("animation.crab.walk", ILoopType.EDefaultLoopTypes.LOOP);
    private static final AnimationBuilder DANCE = new AnimationBuilder().addAnimation("animation.crab.dance", ILoopType.EDefaultLoopTypes.LOOP);

    private static final TrackedData<Integer> CRAB_TYPE = DataTracker.registerData(CrabEntity.class, TrackedDataHandlerRegistry.INTEGER);
    // Red: 0
    // Blue: 1
    // Green: 2
    // Purple: 3

    private static final TrackedData<Boolean> SONG_PLAYING = DataTracker.registerData(CrabEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    @Nullable
    private BlockPos songSource;

    private static final EntityDimensions DIMENSIONS = EntityDimensions.changing(ModEntityTypes.CRAB.getWidth(), ModEntityTypes.CRAB.getHeight());

    public CrabEntity(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
        this.setPathfindingPenalty(PathNodeType.WATER, 0.0F);
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event)
    {
        if (this.isSongPlaying()) {
            event.getController().setAnimation(DANCE);
            return PlayState.CONTINUE;
        }

        if (event.isMoving()) {
            event.getController().setAnimation(WALK);
        } else {
            event.getController().setAnimation(IDLE);
        }
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data)
    {
        data.addAnimationController(new AnimationController(this, "controller", 5, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    public boolean isBreedingItem(ItemStack stack) {
        return BREEDING_INGREDIENT.test(stack);
    }

    public boolean canBreatheInWater() {
        return true;
    }

    protected void playStepSound(BlockPos pos, BlockState blockState) {
        this.playSound(SoundEvents.ENTITY_SPIDER_STEP, 0.15F, 1.0F);
    }

    public static DefaultAttributeContainer.Builder getDefaultAttributes() {
        return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 8.0D).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25D).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 2.0D);
    }

    @Override
    protected  void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(CRAB_TYPE, 0);
        this.dataTracker.startTracking(SONG_PLAYING, false);
    }

    @Override
    public void tickMovement() {
        if (this.songSource == null || !this.songSource.isWithinDistance(this.getPos(), 3.46D) || !this.world.getBlockState(this.songSource).isOf(Blocks.JUKEBOX)) {
            this.setSongPlaying(false);
            this.songSource = null;
        }

        if (this.isSongPlaying()) {
            this.getNavigation().stop();
        }

        super.tickMovement();
    }

    public void setNearbySongPlaying(BlockPos songPosition, boolean playing) {
        this.songSource = songPosition;
        this.dataTracker.set(SONG_PLAYING, playing);
    }

    public void setSongPlaying(boolean playing) {
        this.dataTracker.set(SONG_PLAYING, playing);
    }

    public boolean isSongPlaying() {
        return this.dataTracker.get(SONG_PLAYING);
    }

    public int getCrabType() {
        return this.dataTracker.get(CRAB_TYPE);
    }

    public void setCrabType(int crabType) {
        this.dataTracker.set(CRAB_TYPE, crabType);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("CrabType", this.getCrabType());
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.setCrabType(nbt.getInt("CrabType"));
    }

    protected SoundEvent getAmbientSound() {
        return ModSoundEvents.ENTITY_CRAB_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource source) {
        return ModSoundEvents.ENTITY_CRAB_HURT;
    }

    protected SoundEvent getDeathSound() {
        return ModSoundEvents.ENTITY_CRAB_HURT;
    }

    private int chooseType() {
        int i = this.random.nextInt(16);
        if (i == 0) {
            return 3;
        } else if (i > 0 && i <= 2) {
            return 2;
        } else if (i > 2 && i <= 4) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    @Nullable
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        int i = this.chooseType();
        this.setCrabType(i);

        return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
    }

    @Override
    public CrabEntity createChild(ServerWorld world, PassiveEntity entity) {
        CrabEntity crabEntity = ModEntityTypes.CRAB.create(world);
        int i = this.chooseType();

        if (this.random.nextInt(5) != 0) {
            i = entity instanceof CrabEntity && this.random.nextBoolean() ? ((CrabEntity)entity).getCrabType() : this.getCrabType();
        }

        crabEntity.setCrabType(i);
        return crabEntity;
    }

    public static boolean canSpawn(EntityType<CrabEntity> entity, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
        return world.getBlockState(pos.down()).isIn(ModBlockTags.CRABS_SPAWNABLE_ON) && world.getBaseLightLevel(pos, 0) > 8;
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(1, new SwimGoal(this));
        this.goalSelector.add(2, new MeleeAttackGoal(this, 1.0D, true));
        this.goalSelector.add(3, new AnimalMateGoal(this, 1.0D));
        this.goalSelector.add(4, new TemptGoal(this, 1.0D, Ingredient.ofItems(Items.SEAGRASS, Items.KELP, Items.GOLD_NUGGET, Items.GOLD_INGOT, Items.GOLD_BLOCK, Items.RAW_GOLD, Items.RAW_GOLD_BLOCK), false));
        this.goalSelector.add(5, new FollowParentGoal(this, 1.25D));
        this.goalSelector.add(6, new WanderAroundGoal(this, 1.0D));
        this.goalSelector.add(7, new LookAtEntityGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.add(8, new LookAroundGoal(this));
        this.targetSelector.add(1, new RevengeGoal(this, new Class[0]));
        super.initGoals();
    }

    public EntityDimensions getDimensions(EntityPose pose) {
        return DIMENSIONS.scaled(this.getScaleFactor());
    }
}