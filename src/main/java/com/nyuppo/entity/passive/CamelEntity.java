package com.nyuppo.entity.passive;

import com.google.common.annotations.VisibleForTesting;
import com.nyuppo.TheLittleThings;
import com.nyuppo.entity.ModEntityTypes;
import com.nyuppo.registration.ModSoundEvents;
import com.nyuppo.util.AnimationStateAccessor;
import com.nyuppo.util.ModEntityPoseType;
import com.nyuppo.util.tags.ModBlockTags;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.control.BodyControl;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.MobNavigation;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.passive.AbstractHorseEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.RabbitEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Predicate;

public class CamelEntity extends AbstractHorseEntity implements JumpingMount, Saddleable {
    public static final Ingredient BREEDING_INGREDIENT;
    public static final TrackedData<Boolean> DASHING;
    public static final TrackedData<Integer> LAST_POSE_TICK;
    public static final TrackedData<Integer> SIT_WAIT_TICK;
    public final AnimationState walkingAnimationState = new AnimationState();
    public final AnimationState sittingTransitionAnimationState = new AnimationState();
    public final AnimationState sittingAnimationState = new AnimationState();
    public final AnimationState standingTransitionAnimationState = new AnimationState();
    public final AnimationState idlingAnimationState = new AnimationState();
    public final AnimationState dashingAnimationState = new AnimationState();
    private static final EntityDimensions SITTING_DIMENSIONS;
    private static final EntityDimensions STANDING_DIMENSIONS;
    private int dashCooldown = 0;
    private int idleAnimationCooldown = 0;

    public CamelEntity(EntityType<? extends CamelEntity> entityType, World world) {
        super(entityType, world);
        this.stepHeight = 1.5F;
        MobNavigation mobNavigation = (MobNavigation)this.getNavigation();
        mobNavigation.setCanSwim(true);
        //mobNavigation.setCanWalkOverFences(true);
    }

    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putBoolean("IsSitting", this.getPose() == ModEntityPoseType.SITTING);
        nbt.putInt("LastPoseTick", (Integer)this.dataTracker.get(LAST_POSE_TICK));
    }

    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        if (nbt.getBoolean("IsSitting")) {
            this.setPose(ModEntityPoseType.SITTING);
        }

        this.setLastPoseTick(nbt.getInt("LastPoseTick"));
    }

    public static DefaultAttributeContainer.Builder createCamelAttributes() {
        return createBaseHorseAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 32.0D).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.09D).add(EntityAttributes.HORSE_JUMP_STRENGTH, 0.42D);
    }

    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(DASHING, false);
        this.dataTracker.startTracking(LAST_POSE_TICK, -52);
        this.dataTracker.startTracking(SIT_WAIT_TICK, 0);
    }

    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        this.dataTracker.set(LAST_POSE_TICK, (int)world.toServerWorld().getTime() - 52);
        return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new EscapeDangerGoal(this, 1.2F));
        this.goalSelector.add(2, new AnimalMateGoal(this, 1.0F, CamelEntity.class));
        this.goalSelector.add(3, new CamelTemptGoal(this, 1.25F, BREEDING_INGREDIENT, false));
        this.goalSelector.add(4, new FollowParentGoal(this, 1.0F));
        // this goal makes camels follow players within a 2-6 block range for 30-60 ticks, however it's pretty unnecessary with the 'follow cactus' goal
        //this.goalSelector.add(5, new CamelFollowPlayerGoal(this, 1.0F, 2.0F, 6.0F, UniformIntProvider.create(30, 60)));
        this.goalSelector.add(6, new LookAroundGoal(this));
        this.goalSelector.add(7, new CamelWanderAroundGoal(this, 1.0F));
        this.goalSelector.add(7, new CamelSitGoal(this, UniformIntProvider.create(2 * 20, 20 * 20), 30));

    }

    public EntityDimensions getDimensions(EntityPose pose) {
        //return pose == ModEntityPoseType.SITTING ? SITTING_DIMENSIONS.scaled(this.getScaleFactor()) : super.getDimensions(pose);
        return pose == ModEntityPoseType.SITTING ? SITTING_DIMENSIONS.scaled(this.getScaleFactor()) : STANDING_DIMENSIONS.scaled(this.getScaleFactor());
    }

    protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
        return dimensions.height - 0.1F;
    }

    public void tick() {
        super.tick();
        if (this.isDashing() && this.dashCooldown < 55 && (this.onGround || this.isTouchingWater())) {
            this.setDashing(false);
        }

        if (this.dashCooldown > 0) {
            --this.dashCooldown;
            if (this.dashCooldown == 0) {
                this.world.playSound((PlayerEntity)null, this.getBlockPos(), ModSoundEvents.ENTITY_CAMEL_DASH_READY, SoundCategory.PLAYERS, 1.0F, 1.0F);
            }
        }

        if (this.getSitWaitTick() > 0) {
            this.decrementSitWaitTick();
        }

        //if (this.world.isClient()) {
            this.updateAnimations();
        //}

    }


    private void updateAnimations() {
        if (this.idleAnimationCooldown <= 0) {
            this.idleAnimationCooldown = this.random.nextInt(40) + 80;
            this.idlingAnimationState.start(this.age);
        } else {
            --this.idleAnimationCooldown;
        }

        if (this.getPose() == EntityPose.STANDING) {
            this.sittingTransitionAnimationState.stop();
            this.sittingAnimationState.stop();
            ((AnimationStateAccessor)this.dashingAnimationState).setRunning(this.isDashing(), this.age);
            ((AnimationStateAccessor)this.standingTransitionAnimationState).setRunning(this.isChangingPose(), this.age);
            ((AnimationStateAccessor)this.walkingAnimationState).setRunning((this.onGround || this.hasPrimaryPassenger()) && this.getVelocity().horizontalLengthSquared() > 1.0E-6D, this.age);
        } else if (this.getPose() == ModEntityPoseType.SITTING) {
            this.walkingAnimationState.stop();
            this.standingTransitionAnimationState.stop();
            this.dashingAnimationState.stop();
            if (this.shouldPlaySittingTransitionAnimation()) {
                this.sittingTransitionAnimationState.startIfNotRunning(this.age);
                this.sittingAnimationState.stop();
            } else {
                this.sittingTransitionAnimationState.stop();
                this.sittingAnimationState.startIfNotRunning(this.age);
            }
        } else {
            this.walkingAnimationState.stop();
            this.sittingTransitionAnimationState.stop();
            this.sittingAnimationState.stop();
            this.standingTransitionAnimationState.stop();
            this.dashingAnimationState.stop();
        }
    }

    public void travel(Vec3d movementInput) {
        if (this.isAlive()) {
            if (this.isStationary() && this.isOnGround()) {
                this.setVelocity(this.getVelocity().multiply(0.0D, 1.0D, 0.0D));
                movementInput = movementInput.multiply(0.0D, 1.0D, 0.0D);
            }

            if (this.onGround && this.jumpStrength > 0.0f && !this.isInAir() && this.onGround) {
                LivingEntity livingEntity = this.getPrimaryPassenger();
                float f = livingEntity.sidewaysSpeed * 0.5f;
                float g = livingEntity.forwardSpeed;
                if (g <= 0.0f) {
                    g *= 0.25f;
                }
                this.jump(this.jumpStrength, f, g);
                this.jumpStrength = 0.0f;
            }

            super.travel(movementInput);
        }
    }

    public boolean isStationary() {
        return this.isSitting() || this.isChangingPose();
    }

    protected float getHorsebackMovementSpeed(LivingEntity passenger) {
        float f = passenger.isSprinting() && this.getJumpCooldown() == 0 ? 0.1F : 0.0F;
        return (float)this.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED) + f;
    }

    protected boolean ignoresMovementInput(LivingEntity passenger) {
        boolean bl = this.isChangingPose();
        if (this.isSitting() && !bl && passenger.forwardSpeed > 0.0F) {
            this.startStanding();
        }

        return this.isStationary();
    }

    public boolean canJump(PlayerEntity player) {
        return !this.isStationary() && this.getPrimaryPassenger() == player && this.isSaddled();
    }

    public static boolean canSpawn(EntityType<CamelEntity> camel, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
        return world.getBlockState(pos.down()).isIn(ModBlockTags.CAMELS_SPAWNABLE_ON) && isLightLevelValidForNaturalSpawn(world, pos);
    }

    public void setJumpStrength(int strength) {
        if (this.isSaddled() && this.dashCooldown <= 0 && this.isOnGround()) {
            super.setJumpStrength(strength);
        }
    }

    protected void jump(float strength, float sidewaysSpeed, float forwardSpeed) {
        double d = this.getAttributeValue(EntityAttributes.HORSE_JUMP_STRENGTH) * (double)this.getJumpVelocityMultiplier() + this.getJumpBoostVelocityModifier();
        Vec3d jumpVec = this.getRotationVector().multiply(1.0D, 0.0D, 1.0D).normalize().multiply((double)(22.2222F * strength) * this.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED) * (double)this.getVelocityMultiplier()).add(0.0D, (double)(1.4285F * strength) * d, 0.0D);
        this.addVelocity(jumpVec.x, jumpVec.y, jumpVec.z);
        this.dashCooldown = 55;
        this.setDashing(true);
        this.velocityDirty = true;
    }

    public boolean isDashing() {
        return (Boolean)this.dataTracker.get(DASHING);
    }

    public void setDashing(boolean dashing) {
        this.dataTracker.set(DASHING, dashing);
    }

    public int getSitWaitTick() {
        return this.dataTracker.get(SIT_WAIT_TICK);
    }

    public void setSitWaitTick(int ticks) {
        this.dataTracker.set(SIT_WAIT_TICK, ticks);
    }

    public void decrementSitWaitTick() {
        this.setSitWaitTick(this.getSitWaitTick() - 1);
    }

    public boolean canSitAgain() {
        return this.getSitWaitTick() == 0;
    }

    public void startJumping(int height) {
        if (this.dashCooldown == 0) {
            this.playSound(ModSoundEvents.ENTITY_CAMEL_DASH, 1.0F, 1.0F);
            this.setDashing(true);
        }
    }

    public void stopJumping() {
    }

    public int getJumpCooldown() {
        return this.dashCooldown;
    }

    protected SoundEvent getAmbientSound() {
        return ModSoundEvents.ENTITY_CAMEL_AMBIENT;
    }

    protected SoundEvent getDeathSound() {
        return ModSoundEvents.ENTITY_CAMEL_DEATH;
    }

    protected SoundEvent getHurtSound(DamageSource source) {
        return ModSoundEvents.ENTITY_CAMEL_HURT;
    }

    protected void playStepSound(BlockPos pos, BlockState state) {
        if (state.getSoundGroup() == BlockSoundGroup.SAND) {
            this.playSound(ModSoundEvents.ENTITY_CAMEL_STEP_SAND, 1.0F, 1.0F); // ENTITY_CAMEL_STEP_SAND
        } else {
            this.playSound(ModSoundEvents.ENTITY_CAMEL_STEP, 1.0F, 1.0F); // ENTITY_CAMEL_STEP
        }

    }

    public boolean isBreedingItem(ItemStack stack) {
        return BREEDING_INGREDIENT.test(stack);
    }

    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);
        if (player.shouldCancelInteraction()) {
            this.openInventory(player);
            return ActionResult.success(this.world.isClient);
        } else {
            ActionResult actionResult = itemStack.useOnEntity(player, this, hand);
            if (actionResult.isAccepted()) {
                return actionResult;
            } else if (this.isBreedingItem(itemStack)) {
                return this.interactHorse(player, itemStack);
            } else {
                if (this.getPassengerList().size() < 2 && !this.isBaby()) {
                    this.putPlayerOnBack(player);
                }

                return ActionResult.success(this.world.isClient);
            }
        }
    }

    protected void updateForLeashLength(float leashLength) {
        if (leashLength > 6.0F && this.isSitting() && !this.isChangingPose()) {
            this.startStanding();
        }

    }

    protected boolean receiveFood(PlayerEntity player, ItemStack item) {
        if (!this.isBreedingItem(item)) {
            return false;
        } else {
            boolean bl = this.getHealth() < this.getMaxHealth();
            if (bl) {
                this.heal(2.0F);
            }

            boolean bl2 = this.isTame() && this.getBreedingAge() == 0 && this.canEat();
            if (bl2) {
                this.lovePlayer(player);
            }

            boolean bl3 = this.isBaby();
            if (bl3) {
                this.world.addParticle(ParticleTypes.HAPPY_VILLAGER, this.getParticleX(1.0D), this.getRandomBodyY() + 0.5D, this.getParticleZ(1.0D), 0.0D, 0.0D, 0.0D);
                if (!this.world.isClient) {
                    this.growUp(10);
                }
            }

            if (!bl && !bl2 && !bl3) {
                return false;
            } else {
                if (!this.isSilent()) {
                    SoundEvent soundEvent = this.getEatSound();
                    if (soundEvent != null) {
                        this.world.playSound((PlayerEntity)null, this.getX(), this.getY(), this.getZ(), soundEvent, this.getSoundCategory(), 1.0F, 1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.2F);
                    }
                }

                return true;
            }
        }
    }

    protected boolean shouldAmbientStand() {
        return false;
    }

    public boolean canBreedWith(AnimalEntity other) {
        boolean var10000;
        if (other != this && other instanceof CamelEntity) {
            CamelEntity camelEntity = (CamelEntity)other;
            if (this.canBreed() && camelEntity.canBreed()) {
                var10000 = true;
                return var10000;
            }
        }

        var10000 = false;
        return var10000;
    }

    @Nullable
    public CamelEntity createChild(ServerWorld serverWorld, PassiveEntity passiveEntity) {
        return (CamelEntity)ModEntityTypes.CAMEL.create(serverWorld);
    }

    @Nullable
    protected SoundEvent getEatSound() {
        return ModSoundEvents.ENTITY_CAMEL_EAT;
    }

    protected void applyDamage(DamageSource source, float amount) {
        this.setStanding();
        super.applyDamage(source, amount);
    }

    public void updatePassengerPosition(Entity passenger) {
        int i = this.getPassengerList().indexOf(passenger);
        if (i >= 0) {
            boolean bl = i == 0;
            float f = 0.5F;
            float g = (float)(this.isRemoved() ? 0.009999999776482582D : this.method_45346(bl, 0.0F) + passenger.getHeightOffset());
            if (this.getPassengerList().size() > 1) {
                if (!bl) {
                    f = -0.7F;
                }

                if (passenger instanceof AnimalEntity) {
                    f += 0.2F;
                }
            }

            Vec3d vec3d = (new Vec3d(0.0D, 0.0D, (double)f)).rotateY(-this.bodyYaw * 0.017453292F);
            passenger.setPosition(this.getX() + vec3d.x, this.getY() + (double)g, this.getZ() + vec3d.z);
            this.clampPassengerYaw(passenger);
        }
    }

    private double method_45346(boolean bl, float f) {
        double d = this.getMountedHeightOffset();
        float g = this.getScaleFactor() * 1.43F;
        float h = g - this.getScaleFactor() * 0.2F;
        float i = g - h;
        boolean bl2 = this.isChangingPose();
        boolean bl3 = this.getPose() == ModEntityPoseType.SITTING;
        if (bl2) {
            int j = bl3 ? 40 : 52;
            int k;
            float l;
            if (bl3) {
                k = 28;
                l = bl ? 0.5F : 0.1F;
            } else {
                k = bl ? 24 : 32;
                l = bl ? 0.6F : 0.35F;
            }

            float m = (float)this.getLastPoseTickDelta() + f;
            boolean bl4 = m < (float)k;
            float n = bl4 ? m / (float)k : (m - (float)k) / (float)(j - k);
            float o = g - l * h;
            d += bl3 ? (double) MathHelper.lerp(n, bl4 ? g : o, bl4 ? o : i) : (double)MathHelper.lerp(n, bl4 ? i - g : i - o, bl4 ? i - o : 0.0F);
        }

        if (bl3 && !bl2) {
            d += (double)i;
        }

        return d;
    }

    public Vec3d getLeashOffset() {
        //return new Vec3d(0.0D, this.method_45346(true, tickDelta) - (double)(0.2F * this.getScaleFactor()), (double)(this.getWidth() * 0.56F));
        return new Vec3d(0.0D, this.method_45346(true, this.getLastPoseTickDelta()) - (double)(0.2F * this.getScaleFactor()), (double)(this.getWidth() * 0.56F));
    }

    public double getMountedHeightOffset() {
        return (double)(this.getDimensions(this.getPose()).height - (this.isBaby() ? 0.35F : 0.6F));
    }

    public void onPassengerLookAround(Entity passenger) {
        if (this.getPrimaryPassenger() != passenger) {
            this.clampPassengerYaw(passenger);
        }

    }

    private void clampPassengerYaw(Entity passenger) {
        passenger.setBodyYaw(this.getYaw());
        float f = passenger.getYaw();
        float g = MathHelper.wrapDegrees(f - this.getYaw());
        float h = MathHelper.clamp(g, -160.0F, 160.0F);
        passenger.prevYaw += h - g;
        float i = f + h - g;
        passenger.setYaw(i);
        passenger.setHeadYaw(i);
    }

    protected boolean canAddPassenger(Entity passenger) {
        return this.getPassengerList().size() <= 2;
    }

    @Nullable
    public LivingEntity getPrimaryPassenger() {
        if (!this.getPassengerList().isEmpty() && this.isSaddled()) {
            Entity entity = (Entity)this.getPassengerList().get(0);
            if (entity instanceof LivingEntity) {
                LivingEntity livingEntity = (LivingEntity)entity;
                return livingEntity;
            }
        }

        return null;
    }

    public boolean isSitting() {
        return this.getPose() == ModEntityPoseType.SITTING;
    }

    public boolean isChangingPose() {
        Integer l = this.getLastPoseTickDelta();
        boolean var10000;

        if (this.getPose() == EntityPose.STANDING) {
            var10000 = l < 52L;
        } else if (this.getPose() == ModEntityPoseType.SITTING) {
            var10000 = l < 40L;
        } else {
            var10000 = false;
        }

        return var10000;
    }

    private boolean shouldPlaySittingTransitionAnimation() {
        return this.getPose() == ModEntityPoseType.SITTING && this.getLastPoseTickDelta() < 40L;
    }

    public void startSitting() {
        if (!this.isInPose(ModEntityPoseType.SITTING)) {
            this.playSound(ModSoundEvents.ENTITY_CAMEL_SIT, 1.0F, 1.0F);
            this.setPose(ModEntityPoseType.SITTING);
            this.setLastPoseTick((int)this.world.getTime());
        }
    }

    public void startStanding() {
        if (!this.isInPose(EntityPose.STANDING)) {
            this.playSound(ModSoundEvents.ENTITY_CAMEL_STAND, 1.0F, 1.0F);
            this.setPose(EntityPose.STANDING);
            this.setLastPoseTick((int)this.world.getTime());
        }
    }

    public void setStanding() {
        this.setPose(EntityPose.STANDING);
        this.setLastPoseTick((int)this.world.getTime() - 52);
    }

    @VisibleForTesting
    public void setLastPoseTick(Integer lastPoseTick) {
        this.dataTracker.set(LAST_POSE_TICK, lastPoseTick);
    }

    public int getLastPoseTickDelta() {
        return (int)this.world.getTime() - (Integer)this.dataTracker.get(LAST_POSE_TICK);
    }

    public SoundEvent getSaddleSound() {
        return ModSoundEvents.ENTITY_CAMEL_SADDLE;
    }

    public void onTrackedDataSet(TrackedData<?> data) {
        if (!this.firstUpdate && DASHING.equals(data)) {
            this.dashCooldown = this.dashCooldown == 0 ? 55 : this.dashCooldown;
        }

        super.onTrackedDataSet(data);
    }

    protected BodyControl createBodyControl() {
        return new CamelEntity.CamelBodyControl(this);
    }

    public boolean isTame() {
        return true;
    }

    public void openInventory(PlayerEntity player) {
        if (!this.world.isClient) {
            player.openHorseInventory(this, this.items);
        }

    }

    static {
        BREEDING_INGREDIENT = Ingredient.ofItems(new ItemConvertible[]{Items.CACTUS});
        DASHING = DataTracker.registerData(CamelEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
        LAST_POSE_TICK = DataTracker.registerData(CamelEntity.class, TrackedDataHandlerRegistry.INTEGER);
        SIT_WAIT_TICK = DataTracker.registerData(CamelEntity.class, TrackedDataHandlerRegistry.INTEGER);
        SITTING_DIMENSIONS = EntityDimensions.changing(ModEntityTypes.CAMEL.getWidth(), ModEntityTypes.CAMEL.getHeight() - 1.43F);
        STANDING_DIMENSIONS = EntityDimensions.changing(ModEntityTypes.CAMEL.getWidth(), ModEntityTypes.CAMEL.getHeight());
    }

    class CamelBodyControl extends BodyControl {
        public CamelBodyControl(CamelEntity camel) {
            super(camel);
        }

        public void tick() {
            if (!CamelEntity.this.isStationary()) {
                super.tick();
            }

        }
    }

    static class CamelWanderAroundGoal extends WanderAroundGoal {
        public CamelWanderAroundGoal(PathAwareEntity mob, double speed) {
            super(mob, speed);
        }

        @Override
        public void start() {
            if (mob instanceof CamelEntity) {
                CamelEntity camel = (CamelEntity)mob;
                camel.startStanding();
            }
            super.start();
        }
    }

    static class CamelTemptGoal extends TemptGoal {
        public CamelTemptGoal(PathAwareEntity entity, double speed, Ingredient food, boolean canBeScared) {
            super(entity, speed, food, canBeScared);
        }

        @Override
        public void start() {
            if (mob instanceof CamelEntity) {
                CamelEntity camel = (CamelEntity)mob;
                camel.startStanding();
            }
            super.start();
        }
    }

    static class CamelFollowPlayerGoal extends Goal {
        private CamelEntity camel;
        private final double speed;
        private final float minDistance;
        private final float maxDistance;
        private UniformIntProvider tickRange;
        private final Predicate<PlayerEntity> targetPredicate;
        @Nullable
        private PlayerEntity target;
        private final EntityNavigation navigation;
        private int followTicks;

        public CamelFollowPlayerGoal(CamelEntity camel, double speed, float minDistance, float maxDistance, UniformIntProvider tickRange) {
            this.camel = camel;
            this.speed = speed;
            this.minDistance = minDistance;
            this.maxDistance = maxDistance;
            this.tickRange = tickRange;
            this.targetPredicate = target -> target != null;
            this.navigation = camel.getNavigation();
        }

        @Override
        public boolean canStart() {
            List<PlayerEntity> list = this.camel.world.getEntitiesByClass(PlayerEntity.class, this.camel.getBoundingBox().expand(this.maxDistance), this.targetPredicate);
            if (!list.isEmpty()) {
                for (PlayerEntity playerEntity : list) {
                    if (playerEntity.isInvisible()) continue;
                    this.target = playerEntity;
                    return !this.camel.isInLove();
                }
            }
            return false;
        }

        @Override
        public boolean shouldContinue() {
            return this.target != null && !this.navigation.isIdle() && !this.camel.isStationary() && this.camel.squaredDistanceTo(this.target) > (double)(this.minDistance * this.minDistance) && this.followTicks > 0;
        }

        @Override
        public void start() {
            this.followTicks = this.tickRange.get(this.camel.getRandom());
        }

        @Override
        public void stop() {
            this.target = null;
            this.navigation.stop();
        }

        @Override
        public void tick() {
            if (this.target == null || this.camel.isLeashed() || this.camel.isStationary()) {
                return;
            }

            this.camel.getLookControl().lookAt(this.target, 10.0f, this.camel.getMaxLookPitchChange());
            if (this.followTicks <= 0) {
                return;
            }

            this.followTicks--;

            double e, f;
            double d = this.camel.getX() - this.target.getX();
            double g = d * d + (e = this.camel.getY() - this.target.getY()) * e + (f = this.camel.getZ() - this.target.getZ()) * f;
            if (g <= (double)(this.minDistance * this.minDistance)) {
                this.navigation.stop();
                return;
            }
            this.navigation.startMovingTo(this.target, this.speed);
        }
    }

    static class CamelSitGoal extends Goal {
        private CamelEntity camel;
        private UniformIntProvider sitTimeProvider;
        private int sitTicks;
        private int cooldown;

        public CamelSitGoal(CamelEntity camel, UniformIntProvider sitTimeProvider, int cooldownSeconds) {
            this.camel = camel;
            this.sitTimeProvider = sitTimeProvider;
            this.cooldown = cooldownSeconds;
        }

        public boolean canStart() {
            return this.camel.canSitAgain() && this.camel.getRandom().nextInt(200) == 0 && !this.camel.sittingTransitionAnimationState.isRunning() && !this.camel.standingTransitionAnimationState.isRunning() && !this.camel.isSitting() && !this.camel.isTouchingWater() && !this.camel.isLeashed() && this.camel.isOnGround() && !this.camel.hasPrimaryPassenger(); //  && this.camel.getLastPoseTickDelta() >= 20
        }

        public boolean shouldContinue() {
            return this.sitTicks > 0 && !this.camel.isTouchingWater() && !this.camel.isLeashed() && this.camel.isOnGround() && !this.camel.hasPrimaryPassenger(); //  && this.camel.getLastPoseTickDelta() >= 20
        }

        public void start() {
            this.sitTicks = this.sitTimeProvider.get(this.camel.getRandom());
            this.camel.startSitting();
            /*
            if (this.camel.isSitting()) {
                this.camel.startStanding();
            } else {
                this.camel.startSitting();
            }
            */
        }

        public void tick() {
            this.sitTicks--;
        }

        public void stop() {
            this.camel.startStanding();
            this.camel.setSitWaitTick(20 * cooldown);
        }
    }
}
