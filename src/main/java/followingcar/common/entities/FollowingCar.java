package followingcar.common.entities;





import java.util.ArrayList;

import java.util.List;

import javax.annotation.Nullable;



import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FollowOwnerGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.SitWhenOrderedToGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import followingcar.common.items.itemsmaster;
import followingcar.config.FollowingCarConfig;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.PlayerRideable;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.item.DyeItem;

public class FollowingCar extends TamableAnimal implements PlayerRideable{
	private static final Ingredient TAMING_ITEMS = Ingredient.of(itemsmaster.GASCAN);
	private static final EntityDataAccessor<Integer> COLOR = SynchedEntityData.defineId(FollowingCar.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> TYPE = SynchedEntityData.defineId(FollowingCar.class, EntityDataSerializers.INT); //<-- this is prep for adding more car models in the future
	private List<Integer> openDoorTime = new ArrayList<Integer>() {
		private static final long serialVersionUID = 432987030978417908L;

		{
			add(0,0);
			add(1,0);
			add(2,0);
			add(3,0);
			add(4,0);
			add(5,0);
			add(6,0);
			add(7,0);
			add(8,0);
			add(9,0);
		}
	};
	
	
	

	
	public List<Integer> GetOpenDoorTime() {
		return this.openDoorTime;
	}

	//taming the car / claiming it as your own / riding car
	@Override
	public InteractionResult mobInteract(Player actor, InteractionHand playerhand) {
		ItemStack itemstack = actor.getItemInHand(playerhand);
		Item item = itemstack.getItem();
		if (this.isTame()) {
			if (this.isOwnedBy(actor)) {
				if (itemstack.getItem() == itemsmaster.GASCAN && this.getHealth() < this.getMaxHealth()) {
					this.consumeItemFromStack(actor, itemstack);
					this.heal((float)5);
					if(this.getHealth() < this.getMaxHealth()) {
						this.setHealth(this.getMaxHealth());
					}
					return InteractionResult.CONSUME;
				}
				try {
					DyeColor dyecolor = ((DyeItem)item).getDyeColor();
					if (dyecolor != this.getColor()) {
						this.setColor(dyecolor);
						if (!actor.isCreative()) {
							this.consumeItemFromStack(actor, itemstack);
						}

						this.setPersistenceRequired();
						return InteractionResult.CONSUME;
					}
				}
				catch(Exception e) {

				}


			}

			if(!actor.isCrouching()){//if the person who right clicked it is not sneaking
				if(this.isInSittingPose() || this.isOrderedToSit() ) { //and the car is sitting
					if(this.isOwnedBy(actor)) { //and the person who right clicked it is the owner of the car
						this.setOrderedToSit(false); //then make it stand up and put the owner in the car
						this.setInSittingPose(false);

						//change this for bus if it has only one door or something
						openDoor(this.getPassengers().size()+1);
						return actor.startRiding(this) ? InteractionResult.SUCCESS : InteractionResult.PASS;
					}
				}
				else { //if it is not sitting, allow someone in no matter the person.

					openDoor(this.getPassengers().size()+1);
					return actor.startRiding(this) ? InteractionResult.SUCCESS : InteractionResult.PASS;
				}
			}
			else { //if they are sneaking, aka trying to get it to stand
				if(this.isOwnedBy(actor)) { //only make the car stand if it is the owner.
					this.setOrderedToSit(!this.isOrderedToSit());
					return InteractionResult.SUCCESS;
				}
			}
			return InteractionResult.PASS;

		} else if (itemstack.getItem() == itemsmaster.GASCAN) {
			this.consumeItemFromStack(actor, itemstack);
			if (this.random.nextInt(3) == 0 && !net.minecraftforge.event.ForgeEventFactory.onAnimalTame(this, actor)) {
				this.tame(actor);
			} 
			else {
				return InteractionResult.CONSUME;
			}

			this.setPersistenceRequired();
			return InteractionResult.SUCCESS;
		}

		return InteractionResult.PASS;
	}

	private void consumeItemFromStack(Player actor, ItemStack itemstack) {
		itemstack.setCount(this.clamp(itemstack.getCount()-1,0,itemstack.getMaxStackSize()));
	}

	private int clamp(int x,int min,int max) {
		return Math.min(Math.max(x,min), max);
	}



	@Override
	public boolean canAddPassenger(net.minecraft.world.entity.Entity passenger) {
		int maxpassengers = 1;
		if(this.getCarType() == 0){
			maxpassengers = 4;
		}
		else if(this.getCarType() == 2){
			maxpassengers = 2;
		}


		return this.getPassengers().size() < maxpassengers && !this.isEyeInFluid(FluidTags.WATER);
	}

	private void openDoor(int size) {
		this.openDoorTime.set(size-1, 80);
	}


	public void setColor(DyeColor color) {
		this.entityData.set(COLOR, color.getId());
	}
	
	@Override
	public void tick() {
		super.tick();
		
		for( int i = 0; i<this.openDoorTime.size();i++) {
			int door = this.openDoorTime.get(i);
			
			if(Math.abs(door) > 5) {
				this.openDoorTime.set(i, door-(5*(Math.abs(door)/door)));
			}
			else {
				this.openDoorTime.set(i, 0);
			}
		}
		
	}
		
	
	//controlling car block
	
	
	private float deltarotation = 0;


	@Override
	public void onPassengerTurned(Entity passenger) {
		updatePassengerPos(passenger);
	}
	@Override
	public void positionRider(Entity passenger) {
		updatePassengerPos(passenger);
	}

	public void updatePassengerPos(Entity passenger) {
		//super.positionRider(passenger);
		if (this.hasPassenger(passenger)) {

			//takes passengers in rows of 2, infinitely backward. This can easily be modified for a bus with more on each row.	    	  

			int i = this.getPassengers().indexOf(passenger); //get where the passenger is

			int count = this.getPassengers().size(); //get how many passengers there are 
			i = Math.abs(i-(count-1)); //reverse it so the first passenger would be in the last available instead
			float y = ((((int)(i/2)))*-1.1F)  + .5F;
			float x = (float) ((Math.floorMod(i, 2)-.5));
			if(this.getCarType() == 2) {
				y-=4/16; //divide by 16 because model is by 16ths of a block.
			}
			if(this.getCarType() == 0) {
				y-=.7;
			}

			Vec3 vector3d = (new Vec3((double)y, 0.0D, (double)x)).yRot(-(this.getYRot()) * ((float)Math.PI / 180F) - ((float)Math.PI / 2F));
			
			passenger.setPos(this.getX()+vector3d.x,this.getY()+.02F,this.getZ()+vector3d.z);
			
			passenger.setYBodyRot(this.getYRot());
		      float f = Mth.wrapDegrees(passenger.getYRot() - this.getYRot());
		      float f1 = Mth.clamp(f, -360F, 360F)+this.deltarotation;
		      passenger.yRotO += f1 - f;
		      passenger.setYRot(passenger.getYRot() + f1 - f);
		      passenger.setYHeadRot(passenger.getYRot());
			
			//LOGGER.info("hello!");
		}
	}



	//opening door animation timer




	float lastcntrlx = 0.0F;
	//moving car with player
	@Override
	public void travel(Vec3 travelVector) {

		if (this.getPassengers().size() > 0) {
			this.maxUpStep = 1.1F;
			LivingEntity livingentity = (LivingEntity)this.getPassengers().get(this.getPassengers().size()-1);//make the last passenger the controlling one, but since the display of the passenger...
			//positions is shifted, the person that is visually the first is the one controlling.
			Vec3 motion = new Vec3(0,0,0);
			
			
			
			
			float cntrlx = livingentity.zza;
			float cntrly = livingentity.xxa;
			
			if(0.8F < Mth.abs(cntrlx)) {
				lastcntrlx = cntrlx;
			}
			
			//this code makes the car speed up and slow down if old movement is disabled. Else, use old movement.
			float speed = this.getSpeed();
			if(!FollowingCarConfig.oldmovement.get()) {
				if(cntrlx != 0.0F) {
					//speed = .1F;
					if((speed > -5 && speed < 10) == false) {
						speed = .1F;
					}
					//LOGGER.info(speed);
					
					speed += (((float)(this.clamp(((int)(Math.log(speed+1)*100)), -5, 100))/1000));//*(Math.abs(speed+1)/(speed+1));
					
					speed = ((float)this.clamp(((int)(speed*1000)),0,400))/1000;
					
					
				}
				else{
					speed -= (((float)(this.clamp(((int)(Math.log(speed+1)*100)), -5, 100))/1000));
					if(Math.abs(speed) < 0.11F) {
						speed = 0.11F;
					}
				}
				
				//LOGGER.info(cntrly);
				
				this.deltarotation = 0;
				float rotationspeed = ((float)this.clamp(((int)((speed-0.1F)*1000)), 0, 4000))/3900;
				
				if(cntrly != 0.0F) {
					float newrot = ((((-3.9F*((rotationspeed-0.5F)*(rotationspeed-0.5F))))+1)* /*Max turn angle speed:*/15)*(-cntrly*(Math.abs(lastcntrlx)/lastcntrlx));
					
					this.setYRot(this.getYRot()+newrot);
					this.deltarotation = newrot/3.1F;
				}
				
				//f *= (Math.abs(speed)/speed);
				//LOGGER.info(f);
				motion = new Vec3(0,0,cntrlx);
				//this.setYRot(livingentity.getYRot());
				this.setSpeed(speed);

			}
			else {//else use simple old movement
				motion = new Vec3(0,0,cntrlx);

				this.setSpeed(Math.abs(cntrlx));
				this.setYRot(this.getYRot()+(cntrly*5));
			}

			super.travel(motion);


		}
		else {
			super.travel(travelVector);
		}

	}

	
	@Override
	 public Vec3 getLightProbePosition(float p_20309_) {
	      return new Vec3(this.getOnPos().getX()+p_20309_,this.getOnPos().getY()+p_20309_,this.getOnPos().getZ()+p_20309_);
	 }
	
	@Override
	public boolean causeFallDamage(float distance, float damageMultiplier, DamageSource p_147189_) {
		float[] ret = net.minecraftforge.common.ForgeHooks.onLivingFall(this, distance, damageMultiplier);
		if (ret == null) return false;
		distance = ret[0];
		damageMultiplier = ret[1];

		boolean flag = super.causeFallDamage(distance-12, damageMultiplier*15, p_147189_);
		int i = this.calculateFallDamage(distance-12, damageMultiplier*15);
		if (i > 0) {
			this.playSound(this.getFallDamageSound(i), 1.0F, 1.0F);
			this.playBlockFallSound();
			this.hurt(p_147189_, (float)i);
			return true;
		} else {
			return flag;
		}
	}



	//end controlling car block

	//have to have some way of getting rid of a protected! ;)
	//2021 Onan Here... What the hell is past me talking about? /\
	@Override
	public void kill() {
		this.remove(RemovalReason.KILLED);
	}


	public FollowingCar(EntityType<? extends TamableAnimal> type, Level worldIn) {
		super(type, worldIn);
	}

	public DyeColor getColor() {
		return DyeColor.byId(this.entityData.get(COLOR));
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(0, new TemptGoal(this, 1D, TAMING_ITEMS, true));
		this.goalSelector.addGoal(1, new FollowOwnerGoal(this, 1.0D, 10.0F, 5.0F, false));
		this.goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this, 1D, 1.0000001E-5F));
		//this.goalSelector.addGoal(3, new LookAtGoal(this, PlayerEntity.class, 10.0F));
		this.goalSelector.addGoal(1, new CarRelaxOnOwnerGoal(this));
		this.goalSelector.addGoal(1, new SitWhenOrderedToGoal(this));
	}

	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_28134_, DifficultyInstance p_28135_, MobSpawnType p_28136_, @Nullable SpawnGroupData p_28137_, @Nullable CompoundTag p_28138_) {
		p_28137_ = super.finalizeSpawn(p_28134_, p_28135_, p_28136_, p_28137_, p_28138_);
		this.entityData.set(COLOR,this.random.nextInt(10));
		this.entityData.set(TYPE, 0);
		this.setSpeed(1F);
		//this.dataManager.set(TYPE, this.rand.nextInt(3)); <-- this is prep for adding more car models in the future
		// Use above comment if we add more car models than the easter egg

		return p_28137_;
	}


	public int getCarType() {
		return this.entityData.get(TYPE);
	}
	public void setCarType(int type) {
		this.entityData.set(TYPE, type);
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(TYPE, 1);
		//this.dataManager.register(DATA_ID_CHEST, false);
		this.entityData.define(COLOR, DyeColor.RED.getId());
	}

	@Override
	public void addAdditionalSaveData(CompoundTag p_28156_) {
		super.addAdditionalSaveData(p_28156_);
		p_28156_.putInt("Color", (byte)this.getColor().getId());
		p_28156_.putInt("CarType", getCarType());
	}
	@Override
	public void readAdditionalSaveData(CompoundTag p_28142_) {
		super.readAdditionalSaveData(p_28142_);
		this.setCarType(p_28142_.getInt("CarType"));
		if (p_28142_.contains("Color", 99)) {
			this.setColor(DyeColor.byId(p_28142_.getInt("Color")));
		}

	}

	//This is called in the main mod file. in MainFollowingCar.java
	public static AttributeSupplier setCustomAttributes() {
		return LivingEntity.createLivingAttributes().add(Attributes.ATTACK_DAMAGE, 5.0D).add(Attributes.MAX_HEALTH, 20.0D).add(Attributes.MOVEMENT_SPEED,.5D).add(Attributes.FOLLOW_RANGE,10D).build();
	}


	static class CarTemptGoal extends net.minecraft.world.entity.ai.goal.TemptGoal {
		@Nullable
		private Player selectedPlayer;
		private final FollowingCar car;

		public CarTemptGoal(FollowingCar p_28219_, double p_28220_, Ingredient p_28221_, boolean p_28222_) {
			super(p_28219_, p_28220_, p_28221_, p_28222_);
			this.car = p_28219_;
		}

		public void tick() {
			super.tick();
			if (this.selectedPlayer == null && this.mob.getRandom().nextInt(600) == 0) {
				this.selectedPlayer = this.player;
			} else if (this.mob.getRandom().nextInt(500) == 0) {
				this.selectedPlayer = null;
			}

		}
		@Override
		protected boolean canScare() {
			return false;
		}
		@Override
		public boolean canUse() {
			return super.canUse() && !this.car.isTame();
		}
	}



	//car sits on owner in bed. This is like the cat one but without the morning gift and stuff
	static class CarRelaxOnOwnerGoal extends Goal {
		private final FollowingCar car;
		private Player ownerPlayer;
		private BlockPos goalPos;
		private int onBedTicks;

		public CarRelaxOnOwnerGoal(FollowingCar p_28203_) {
			this.car = p_28203_;
		}

		public boolean canUse() {
			if (!this.car.isTame()) {
				return false;
			} else if (this.car.isOrderedToSit()) {
				return false;
			}
			else if (this.car.getPassengers().size() > 0) {
				return false;
			} else {
				LivingEntity livingentity = this.car.getOwner();
				if (livingentity instanceof Player) {
					this.ownerPlayer = (Player)livingentity;
					if (!livingentity.isSleeping()) {
						return false;
					}

					if (this.car.distanceToSqr(this.ownerPlayer) > 100.0D) {
						return false;
					}

					BlockPos blockpos = this.ownerPlayer.blockPosition();
					BlockState blockstate = this.car.level.getBlockState(blockpos);
					if (blockstate.is(BlockTags.BEDS)) {
						this.goalPos = blockstate.getOptionalValue(BedBlock.FACING).map((p_28209_) -> {
							return blockpos.relative(p_28209_.getOpposite());
						}).orElseGet(() -> {
							return new BlockPos(blockpos);
						});
						return !this.spaceIsOccupied();
					}
				}

				return false;
			}
		}

		private boolean spaceIsOccupied() {
			for(FollowingCar car : this.car.level.getEntitiesOfClass(FollowingCar.class, (new AABB(this.goalPos)).inflate(2.0D))) {
				if (car != this.car && (car.isInSittingPose())) {
					return true;
				}
			}

			return false;
		}

		public boolean canContinueToUse() {
			return this.car.isTame() && !this.car.isOrderedToSit() && this.ownerPlayer != null && this.ownerPlayer.isSleeping() && this.goalPos != null && !this.spaceIsOccupied();
		}

		public void start() {
			if (this.goalPos != null && !(this.car.getPassengers().size() > 0)) {
				this.car.setInSittingPose(false);
				this.car.getNavigation().moveTo((double)this.goalPos.getX(), (double)this.goalPos.getY(), (double)this.goalPos.getZ(), (double)1.1F);
			}

		}

		public void stop() {
			this.car.setOrderedToSit(false);
			this.car.setInSittingPose(false);

			this.onBedTicks = 0;
			this.car.getNavigation().stop();
		}


		public void tick() {
			super.tick();
			if (this.ownerPlayer != null && this.goalPos != null) {
				this.car.setInSittingPose(false);
				this.car.getNavigation().moveTo((double)this.goalPos.getX(), (double)this.goalPos.getY(), (double)this.goalPos.getZ(), (double)1.1F);
				if (this.car.distanceToSqr(this.ownerPlayer) < 2.5D) {
					++this.onBedTicks;
					if (this.onBedTicks > 16) {
						this.car.setInSittingPose(true);
					} else {
						this.car.lookAt(this.ownerPlayer, 45.0F, 45.0F);
					}
				} else {
					this.car.setInSittingPose(false);
				}
			}

		}
	}



	@Override
	public AgeableMob getBreedOffspring(ServerLevel p_146743_, AgeableMob p_146744_) {
		return null;
	}
}
