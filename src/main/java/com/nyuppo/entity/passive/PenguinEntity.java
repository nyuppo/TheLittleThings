package com.nyuppo.entity.passive;

import com.nyuppo.entity.ModEntityTypes;
import com.nyuppo.registration.ModSoundEvents;
import com.nyuppo.util.ModEntityPoseType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
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
import net.minecraft.entity.passive.PolarBearEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.BiomeTags;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

import java.util.Objects;
import java.util.Optional;

public class PenguinEntity extends AnimalEntity implements IAnimatable {
    public static final float SWIM_SPEED_MULTIPLIER = 5.0f;
    private AnimationFactory factory = GeckoLibUtil.createFactory(this);
    private static final Ingredient BREEDING_INGREDIENT = Ingredient.ofItems(Items.COD, Items.SALMON, Items.TROPICAL_FISH);
    private static final TrackedData<Boolean> SHAKING;
    private static final TrackedData<Boolean> WAS_WET;
    private static final TrackedData<Boolean> CAN_SHAKE;

    private static final AnimationBuilder IDLE = new AnimationBuilder().addAnimation("animation.penguin.idle", ILoopType.EDefaultLoopTypes.LOOP);
    private static final AnimationBuilder WALK = new AnimationBuilder().addAnimation("animation.penguin.walk", ILoopType.EDefaultLoopTypes.LOOP);
    private static final AnimationBuilder SLIDE = new AnimationBuilder().addAnimation("animation.penguin.slide", ILoopType.EDefaultLoopTypes.LOOP);
    private static final AnimationBuilder SHAKE = new AnimationBuilder().addAnimation("animation.penguin.shake", ILoopType.EDefaultLoopTypes.PLAY_ONCE);

    //private static final EntityDimensions DIMENSIONS = EntityDimensions.fixed(0.4f, 1.0f);
    private static final EntityDimensions DIMENSIONS = EntityDimensions.changing(ModEntityTypes.PENGUIN.getWidth(), ModEntityTypes.PENGUIN.getHeight());

    public PenguinEntity(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
        this.setPathfindingPenalty(PathNodeType.WATER, 0.0F);
        //this.ignoreCameraFrustum = true;
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (isShaking()) {
            event.getController().setAnimation(SHAKE);
            return PlayState.CONTINUE;
        }

        if (event.isMoving()) {
            BlockState blockState = this.world.getBlockState(this.getBlockPos().down());
            BlockState blockState2 = this.getLandingBlockState();

            if (blockState.isIn(BlockTags.ICE) || blockState2.isOf(Blocks.WATER) || this.isTouchingWater()) {
                event.getController().setAnimation(SLIDE);
            } else {
                event.getController().setAnimation(WALK);
            }
        } else {
            event.getController().setAnimation(IDLE);
        }

        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this, "controller", 5, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    /*
    public void tick() {
        if (this.isShaking()) {
            Vec3d vec3d = this.getVelocity();
            float g = (this.random.nextFloat() * 2.0F - 1.0F) * this.getWidth() * 0.5F;
            float h = (this.random.nextFloat() * 2.0F - 1.0F) * this.getWidth() * 0.5F;
            this.world.addParticle(ParticleTypes.SPLASH, this.getX() + (double)g, (double)((float)this.getY() + 0.8F), this.getZ() + (double)h, vec3d.x, vec3d.y, vec3d.z);
        }
    }
    */

    public EntityDimensions getDimensions(EntityPose pose) {
        return DIMENSIONS.scaled(this.getScaleFactor());
    }

    public void tickMovement() {
        super.tickMovement();
        if (this.isWet() && !this.wasWet()) {
            this.setCanShake(false);
            this.setWasWet(true);
        } else if (!this.isWet() && this.wasWet()) {
            this.setWasWet(false);
            this.setCanShake(true);
        } else if (this.isShaking()) {
            this.setVelocity(this.getVelocity().multiply(0.0D, 1.0D, 0.0D));
            Vec3d vec3d = this.getVelocity();
            float g = (this.random.nextFloat() * 2.0F - 1.0F) * this.getWidth() * 0.5F;
            float h = (this.random.nextFloat() * 2.0F - 1.0F) * this.getWidth() * 0.5F;
            this.world.addParticle(ParticleTypes.SPLASH, this.getX() + (double) g, (double) ((float) this.getY() + 0.8F), this.getZ() + (double) h, vec3d.x, vec3d.y, vec3d.z);
        }
    }

    protected void dropEquipment(DamageSource source, int lootingMultiplier, boolean allowDrops) {
        super.dropEquipment(source, lootingMultiplier, allowDrops);

        if ("Rico".equalsIgnoreCase(Formatting.strip(this.getName().getString()))) {
            this.dropItem(Items.TNT);
        }
    }

    public boolean isBreedingItem(ItemStack stack) {
        return BREEDING_INGREDIENT.test(stack);
    }

    public boolean canBreatheInWater() {
        return true;
    }

    protected SoundEvent getAmbientSound() {
        return ModSoundEvents.ENTITY_PENGUIN_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource source) {
        return ModSoundEvents.ENTITY_PENGUIN_HURT;
    }

    protected SoundEvent getDeathSound() {
        return ModSoundEvents.ENTITY_PENGUIN_DEATH;
    }

    protected void playStepSound(BlockPos pos, BlockState blockState) {
        this.playSound(SoundEvents.ENTITY_CHICKEN_STEP, 0.15F, 1.0F);
    }

    public static DefaultAttributeContainer.Builder getDefaultAttributes() {
        return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 7.0D).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.2D).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 2.0D);
    }

    public static boolean isValidNaturalSpawn(EntityType<? extends AnimalEntity> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
        BlockState state = world.getBlockState(pos.down());
        return (state.isIn(BlockTags.ANIMALS_SPAWNABLE_ON) || state.isIn(BlockTags.ICE) || state.isIn(BlockTags.SNOW)) && AnimalEntity.isLightLevelValidForNaturalSpawn(world, pos);
    }

    public static boolean canSpawn(EntityType<PenguinEntity> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
        RegistryEntry<Biome> registryEntry = world.getBiome(pos);
        if (registryEntry.isIn(BiomeTags.POLAR_BEARS_SPAWN_ON_ALTERNATE_BLOCKS)) {
            return isValidNaturalSpawn(type, world, spawnReason, pos, random);
        } else {
            return isValidNaturalSpawn(type, world, spawnReason, pos, random); //  && world.getBlockState(pos.down()).isOf(Blocks.ICE)
        }
    }

    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
    }

    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(SHAKING, false);
        this.dataTracker.startTracking(WAS_WET, false);
        this.dataTracker.startTracking(CAN_SHAKE, false);
    }

    public boolean isShaking() {
        return (Boolean) this.dataTracker.get(SHAKING);
    }

    public void setShaking(boolean shaking) {
        this.dataTracker.set(SHAKING, shaking);
    }

    public boolean wasWet() {
        return (Boolean) this.dataTracker.get(WAS_WET);
    }

    public void setWasWet(boolean wet) {
        this.dataTracker.set(WAS_WET, wet);
    }

    public boolean canShake() {
        return (Boolean) this.dataTracker.get(CAN_SHAKE);
    }

    public void setCanShake(boolean canShake) {
        this.dataTracker.set(CAN_SHAKE, canShake);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(1, new SwimGoal(this));
        this.goalSelector.add(2, new AnimalMateGoal(this, 1.0D));
        this.goalSelector.add(3, new FleeEntityGoal<>(this, PolarBearEntity.class, 6.0F, 1.0D, 1.25D));
        this.goalSelector.add(4, new TemptGoal(this, 1.0D, Ingredient.ofItems(Items.COD, Items.SALMON, Items.TROPICAL_FISH), false));
        this.goalSelector.add(5, new FollowParentGoal(this, 1.25D));
        //this.goalSelector.add(6, new PounceAtTargetGoal(this, 0.3F));
        //this.goalSelector.add(7, new MeleeAttackGoal(this, 1.0D, true));
        //this.goalSelector.add(8, new SwimAroundGoal(this, 5.0D, 10));
        this.goalSelector.add(6, new WanderAroundGoal(this, 1.0D));
        this.goalSelector.add(6, new PenguinShakeGoal(this));
        this.goalSelector.add(7, new LookAtEntityGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.add(8, new LookAroundGoal(this));
        //this.targetSelector.add(6, new FollowTargetGoal(this, FishEntity.class, false));
        super.initGoals();
    }

    @Override
    public PenguinEntity createChild(ServerWorld world, PassiveEntity entity) {
        return (PenguinEntity) ModEntityTypes.PENGUIN.create(world);
    }

    static {
        SHAKING = DataTracker.registerData(PenguinEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
        WAS_WET = DataTracker.registerData(PenguinEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
        CAN_SHAKE = DataTracker.registerData(PenguinEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    }

    class PenguinShakeGoal extends Goal {
        private static final int ANIMATION_LENGTH = 21; // 20.8, but whatever
        private int shakeTime;
        private final PenguinEntity penguin;

        public PenguinShakeGoal(PenguinEntity penguin) {
            this.penguin = penguin;
        }

        @Override
        public boolean canStart() {
            if (penguin.isTouchingWater()) {
                return false;
            }
            return penguin.canShake();
        }

        @Override
        public void start() {
            shakeTime = ANIMATION_LENGTH;
            penguin.setShaking(true);
            penguin.setCanShake(false);
        }

        @Override
        public boolean shouldContinue() {
            return shakeTime >= 0;
        }

        @Override
        public void stop() {
            penguin.setShaking(false);
        }

        @Override
        public void tick() {
            shakeTime--;
            if (shakeTime == ANIMATION_LENGTH - 5) {
                penguin.playSound(SoundEvents.ENTITY_WOLF_SHAKE, 1.0f, 1.0f);
            }
        }
    }
}