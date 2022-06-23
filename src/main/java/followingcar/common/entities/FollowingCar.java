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
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FollowOwnerGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.SitWhenOrderedToGoal;
import followingcar.MainFollowingCar;
import followingcar.common.items.CarChangerItem;
import followingcar.common.items.itemsmaster;
import followingcar.core.init.CarTypeRegistry;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.PlayerRideable;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;


public class FollowingCar extends TamableAnimal implements PlayerRideable{
	protected static final Ingredient TAMING_ITEMS = Ingredient.of(itemsmaster.GASCAN);
	protected static final EntityDataAccessor<Integer> COLOR = SynchedEntityData.defineId(FollowingCar.class, EntityDataSerializers.INT);
	protected static final EntityDataAccessor<Integer> TYPE = SynchedEntityData.defineId(FollowingCar.class, EntityDataSerializers.INT); 
	public static final EntityDataAccessor<Boolean> RELAX_STATE_ONE = SynchedEntityData.defineId(FollowingCar.class, EntityDataSerializers.BOOLEAN); //if the car is lying down
	public static final EntityDataAccessor<Byte> CarFlags1 = SynchedEntityData.defineId(FollowingCar.class, EntityDataSerializers.BYTE); //car status values like turn, speeddir, and stuff

	//public static final EntityDataAccessor<Rotations> ROTATION = SynchedEntityData.defineId(FollowingCar.class, EntityDataSerializers.ROTATIONS);
	protected List<Integer> openDoorTime = new ArrayList<Integer>() {
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




	protected float nextStep = 1.0F;
	public short rotationsign = 0;
	public short movementsign = 0;


	protected SoundEvent getHurtSound(DamageSource p_21239_) {
		return MainFollowingCar.CAR_HURT.get();
	}

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
					if(itemstack.getItem() == itemsmaster.DyeBundle) {
						String code = itemstack.getDisplayName().getString();
						
						String color = code.substring(code.indexOf('[')+1, code.lastIndexOf("]"));
						int rgb = Integer.valueOf( color.substring( 0, 2 ), 16 );
						rgb = (rgb << 8) + Integer.valueOf( color.substring( 2, 4 ), 16 );
						rgb = (rgb << 8) + Integer.valueOf( color.substring( 4, 6 ), 16 );

						int dyecolor =  rgb;
						if (dyecolor != this.getColor()) {
	
							this.setColor(dyecolor);
							if (!actor.isCreative()) {
								this.consumeItemFromStack(actor, itemstack);
							}
	
							this.setPersistenceRequired();
							return InteractionResult.CONSUME;
						}
					}
				}
				catch(Exception e) {
					//e.printStackTrace();
					String name = "";
					if(this.getCustomName() == null) {
						name = "a car pet";
					}
					else {
						name = this.getCustomName().getString();
					}
						
					this.getOwner().sendSystemMessage(Component.literal("To dye "+name+" with this item, it needs to be named a color hex code with 6 digits with the color you want "+name+" to be!"));
				}


			}

			if(!actor.isCrouching()){//if the person who right clicked it is not sneaking
				if(this.isOwnedBy(actor)) { //and the person who right clicked it is the owner of the car
					if(item instanceof CarChangerItem) {//and it's a car changer item
						if(!actor.isCreative()) {
							this.consumeItemFromStack(actor, itemstack);
						}
						this.setCarType((int)itemstack.getOrCreateTag().getShort("CarType"));
						return InteractionResult.SUCCESS;
					}

				}
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
					this.setOrderedToSit(!(this.isOrderedToSit()||this.isInSittingPose()));
					this.setInSittingPose(false);
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
		int actualtype = this.getActualCarType();

		//gets our offsets.
		int maxpassengers = CarTypeRegistry.CarTypes.get(actualtype).getPassengerOffsets().length;


		return this.getPassengers().size() < maxpassengers && !this.isEyeInFluidType(Fluids.WATER.getFluidType());
	}

	private void openDoor(int size) {
		this.openDoorTime.set(size-1, 80);
	}


	public void setColor(int color) {
		this.entityData.set(COLOR, color);
	}

	@Override
	public void tick() {
		super.tick();
		/*
		rotation = new Vec3(this.getRotationVector().x,this.getRotationVector().y,this.rotation.z);
		this.setRot((float)this.rotation.x,(float) this.rotation.y);
		 */
		for( int i = 0; i<this.openDoorTime.size();i++) {
			int door = this.openDoorTime.get(i);

			if(Math.abs(door) > 5) {
				this.openDoorTime.set(i, door-(5*(Math.abs(door)/door)));
			}
			else {
				this.openDoorTime.set(i, 0);
			}
		}

		if(this.tickCount % 3 == 0 && this.getDeltaMovement().lengthSqr()>.08) {
			this.playSound(MainFollowingCar.CAR_REV.get(),  0.6F + 0.4F * (this.random.nextFloat() - this.random.nextFloat()), (float) (1.0*(this.getDeltaMovement().lengthSqr()/2)));
		}
		else {
			this.rotationsign = 0;
		}


		if (this.tickCount % 20 == 0 && this.entityData.get(RELAX_STATE_ONE).booleanValue() && !this.isOrderedToSit()) {
			this.playSound(MainFollowingCar.CAR_PURR.get(), 0.6F + 0.4F * (this.random.nextFloat() - this.random.nextFloat()), 1.0F);
		}
		//this.travel(new Vec3(1,0,1));

	}

	//get actual car type else return default 0 type
	public int getActualCarType() {
		String name = ChatFormatting.stripFormatting(this.getName().getString());

		int actualtype = this.getCarType();



		if(CarTypeRegistry.NameToVariant.get(name) != null) {
			actualtype = CarTypeRegistry.NameToVariant.get(name);
		}

		//removed since we wanna make the car types accessible in ways other than naming them.
		/*else if(CarTypeRegistry.NameToVariant.containsValue(actualtype)){//so that we can't manually change it to an easter egg car type and get the custom car.
			return 0;
		}*/

		if(CarTypeRegistry.CarTypes.get(actualtype) == null) {
			return 0;
		}
		return actualtype;
	}


	//controlling car block


	private float deltarotation = 0;
	@Override
	public void onPassengerTurned(Entity passenger) {
		updatePassengerPos(passenger);
	}
	@Override
	public void positionRider(Entity passenger) {
		
		if(this.getPassengers().get(0).equals(passenger) && passenger instanceof Player){
			
				
			
			
			
			this.getPassengers().forEach((hello)->{
				hello.setYRot(hello.getYRot() + this.deltarotation);
				hello.setYHeadRot(hello.getYHeadRot() + this.deltarotation);
				//this.clampRotation(hello);
			});
		}
		updatePassengerPos(passenger);
	}

	public void updatePassengerPos(Entity passenger) {
		//super.positionRider(passenger);
		if (this.hasPassenger(passenger)) {

			//takes passengers in rows of 2, infinitely backward. This can easily be modified for a bus with more on each row.	    	  

			int i = this.getPassengers().indexOf(passenger); //get where the passenger is

			int count = this.getPassengers().size(); //get how many passengers there are 
			i = Math.abs(i-(count-1)); //reverse it so the first passenger would be in the last available instead

			double x = 0;
			double y = 0;
			double z = 0;



			int actualtype = this.getActualCarType();

			//gets our offsets.
			Vec3[] offsets = CarTypeRegistry.CarTypes.get(actualtype).getPassengerOffsets();
			if(offsets[i] != null) {
				x = offsets[i].x;
				y = offsets[i].y;
				z = offsets[i].z;
			}
			else {
				x = offsets[offsets.length-1].x;
				y = offsets[offsets.length-1].y;
				z = offsets[offsets.length-1].z;
			}

			//fixes offsets to fit minecraft scale and offset
			x = (((x)*(CarTypeRegistry.CarScale)));
			y = (((y)*(CarTypeRegistry.CarScale)));
			z = (((z)*(CarTypeRegistry.CarScale)))-.5;

			//rearranges them and puts them into a rotated form at 0,0,0 so it matches with the car's rotation.

			Vec3 vector3d = (new Vec3(y, z, x)).yRot(-(this.yBodyRot) * ((float)Math.PI / 180F) - ((float)Math.PI / 2F));


			//sets passenger position to the rotated vector translated to the car's position
			passenger.setPos(this.getX()+vector3d.x,this.getY()+vector3d.y,this.getZ()+vector3d.z);

			//LOGGER.info("hello!");
		}
	}



	//opening door animation timer




	float lastcntrlx = 0.0F;
	Vec3 motion = new Vec3(0,0,0);

	//moving car with player
	@Override
	public void travel(Vec3 travelVector) {
		double speeddir = this.getDeltaMovement().multiply(new Vec3(2,2,2)).dot(this.getForward().multiply(new Vec3(2,2,2)));
		if(Math.abs(speeddir) < .01F) {
			speeddir = 1;
		}
		String speedsign = String.valueOf((int)(Math.abs(speeddir+.1F)/(speeddir+.1F)));
		if (this.getPassengers().size() > 0) {

			



			LivingEntity livingentity = (LivingEntity)this.getPassengers().get(this.getPassengers().size()-1);//make the last passenger the controlling one, but since the display of the passenger...
			//positions is shifted, the person that is visually the first is the one controlling.
			
			
			short rotationsignL = 0;

			if(livingentity != null) {
				if(Math.abs(livingentity.xxa) > 0.1F) {
					rotationsignL = (short) (Math.abs(livingentity.xxa)/livingentity.xxa);
				}
				else {
					rotationsignL = 0;
				}
			}


			if(this.movementsign != ((Math.abs(speeddir)+.1F)/speeddir+.1F) || this.rotationsign != rotationsignL) {
				this.movementsign = (short) ((Math.abs(speeddir)+.1F)/speeddir+.1F);
				this.rotationsign = rotationsignL;
				String turnsign = String.valueOf(this.rotationsign).indexOf("-") != -1 ? "0"+(int)(Math.abs(this.rotationsign)) : "1"+(int)(Math.abs(this.rotationsign));
				this.entityData.set(CarFlags1, speedsign.indexOf("-") != -1 ? Byte.valueOf(turnsign+"0"+speedsign.replace("-", ""),2) : Byte.valueOf(turnsign+"1"+speedsign.replace("-", ""),2));
			}


			
			


			float cntrlx = livingentity.zza;
			float cntrly = livingentity.xxa;


			Vec3 motion = new Vec3(0,0,cntrlx).add(travelVector);
			//
			//this.setSpeed(1);
			if(cntrlx == 0) {
				this.setSpeed((float)(this.getSpeed()-(.03f*(Math.abs(this.getSpeed())/this.getSpeed()))));
				
				if(!(Math.abs(this.getSpeed()) <.1f)) {
					motion = new Vec3(0,0,1).add(travelVector);
				}
				else {
					motion = new Vec3(0,0,0);
				}
				
				
				
				super.travel(motion);
			}
			this.setSpeed(this.getSpeed()+(Math.abs(cntrlx)*(CarTypeRegistry.CarTypes.get(this.getActualCarType()).getAcceleration()/1000)));
			
			this.setSpeed(Math.min(CarTypeRegistry.CarTypes.get(  this.getActualCarType()).getMaxSpeed()  ,  Math.abs(this.getSpeed())  )*(Math.abs(this.getSpeed())/this.getSpeed()));
			
			
			this.setYRot(this.getYRot()+this.deltarotation);
			//this.deltarotation = 0;
			if((Math.abs(((LivingEntity)livingentity).xxa) > 0)) {
				Vec3 WheelOffset1 = CarTypeRegistry.CarTypes.get(this.getActualCarType()).getWheelOffsets().get("WheelL_1_Offset");
				Vec3 WheelOffset2 = CarTypeRegistry.CarTypes.get(this.getActualCarType()).getWheelOffsets().get("WheelL_2_Offset");

				Vec2 WheelFL = new Vec2((float)WheelOffset1.multiply(1, 0, 1).x,(float)WheelOffset1.multiply(1, 0, 1).z);
				Vec2 WheelRL = new Vec2((float)WheelOffset2.multiply(1, 0, 1).x,(float)WheelOffset2.multiply(1, 0, 1).z);
				
				String incomingstatus1 = Integer.toBinaryString(this.getEntityData().get(FollowingCar.CarFlags1).byteValue());
		  	    incomingstatus1 = ("0".repeat(Math.abs(incomingstatus1.length()-4)))+incomingstatus1;
				short movementsign = (short) -(incomingstatus1.charAt(2) == '0' ? Short.parseShort(""+incomingstatus1.charAt(3)) : -1*Short.parseShort(""+incomingstatus1.charAt(3)));
				
				float curspeed = (float) ((this.getDeltaMovement().horizontalDistance()))/(1F/20F);
				double radius = (WheelFL.y-WheelRL.y)/Math.tan(Mth.DEG_TO_RAD*CarTypeRegistry.WheelAngle);
				
				movementsign = movementsign == 0 ? 1 : movementsign;
				
				float rotationspeed = (float) (curspeed/radius);
				
				this.deltarotation = (-this.rotationsign*rotationspeed);
				
			}
			else {
				this.deltarotation = 0;
			}
			super.travel(motion);

			


		}
		else {
			//want wheel rotation direction for model on client to work even with no passengers
			//this will also set the rotation sign based on previous car turning so the car turns
			short rotationsignL = 0;
			rotationsignL = (short) ((this.yHeadRot-this.yBodyRot)> 0? -1:1);
			if(this.yHeadRot-this.yBodyRot == 0) {
				rotationsignL = 0;
			}
			if(this.movementsign != (short)((Math.abs(speeddir)+.1F)/speeddir+.1F) || this.rotationsign != (short) ((this.yHeadRot-this.yBodyRot)> 0? -1:1)) { //the rotation sign thing is done on purpose so it doesn't immedeately go back to straight
				this.movementsign = (short) ((Math.abs(speeddir)+.1F)/speeddir+.1F);
				this.rotationsign = rotationsignL;
				String turnsign = String.valueOf(this.rotationsign).indexOf("-") != -1 ? "0"+(int)(Math.abs(this.rotationsign)) : "1"+(int)(Math.abs(this.rotationsign));
				this.entityData.set(CarFlags1, speedsign.indexOf("-") != -1 ? Byte.valueOf(turnsign+"0"+speedsign.replace("-", ""),2) : Byte.valueOf(turnsign+"1"+speedsign.replace("-", ""),2));
			}
			this.setSpeed(1);

			super.travel(travelVector);
		}

	}


	protected void clampRotation(Entity p_38322_) {
      p_38322_.setYBodyRot(this.getYRot());
      float f = Mth.wrapDegrees((p_38322_.getYRot() - this.getYRot()));
      float f1 = Mth.clamp(f, -360F, 360F);
      p_38322_.yRotO += f1 - f;
      p_38322_.setYRot(p_38322_.getYRot() + f1 - f);
      p_38322_.setYHeadRot(p_38322_.getYRot());
   }




	@Override
	protected float getWaterSlowDown() {
		return 0F;
	}

	@Override
	public Vec3 getFluidFallingAdjustedMovement(double p_20995_, boolean p_20996_, Vec3 p_20997_) {
		if (!this.isNoGravity() && !this.isSprinting()) {
			double d0;
			if (p_20996_ && Math.abs(p_20997_.y - 0.005D) >= 0.003D && Math.abs(p_20997_.y - p_20995_ / 16.0D) < 0.003D) {
				d0 = -0.003D;
			} else {
				d0 = p_20997_.y - p_20995_ / 3.0D;
			}

			return new Vec3(p_20997_.x, d0, p_20997_.z);
		} else {
			return p_20997_;
		}
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
	
	 public SoundEvent getFallDamageSound(int p_21313_) {
	      return p_21313_ > 4 ? this.getFallSounds().big() : this.getFallSounds().small();
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

	public int getColor() {
		return this.entityData.get(COLOR);
	}

	public static int convertColorToInt(DyeColor color){
		float[] original = color.getTextureDiffuseColors();
		int R = Math.round(255 * original[0]);
		int G = Math.round(255 * original[1]);
		int B = Math.round(255 * original[2]);

		R = (R << 16) & 0x00FF0000;
		G = (G << 8) & 0x0000FF00;
		B = B & 0x000000FF;

		return 0xFF000000 | R | G | B;
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(0, new CarTemptGoal(this, 1D, TAMING_ITEMS, false));
		this.goalSelector.addGoal(1, new FollowOwnerGoal(this, 1.0D, 10.0F, 5.0F, false));
		this.goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this, 1D, 1.0000001E-5F));
		//this.goalSelector.addGoal(3, new LookAtGoal(this, PlayerEntity.class, 10.0F));
		this.goalSelector.addGoal(1, new CarRelaxOnOwnerGoal(this));
		this.goalSelector.addGoal(1, new SitWhenOrderedToGoal(this));
	}

	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_28134_, DifficultyInstance p_28135_, MobSpawnType p_28136_, @Nullable SpawnGroupData p_28137_, @Nullable CompoundTag p_28138_) {
		p_28137_ = super.finalizeSpawn(p_28134_, p_28135_, p_28136_, p_28137_, p_28138_);
		this.entityData.set(COLOR,FollowingCar.convertColorToInt(DyeColor.byId(this.random.nextInt(10))));
		this.entityData.set(TYPE, 0);
		this.entityData.set(CarFlags1, Byte.valueOf("0000", 2));
		//this.entityData.set(ROTATION, new Rotations(0, 0, 0));
		//this.setRot(this.entityData.get(ROTATION).getX(), this.entityData.get(ROTATION).getY());
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
		this.entityData.define(COLOR, DyeColor.RED.getId());
		this.entityData.define(RELAX_STATE_ONE, false);
		this.entityData.define(CarFlags1, Byte.valueOf("0000", 2));
		//this.entityData.define(ROTATION, new Rotations(0,0,0));
	}

	@Override
	public void addAdditionalSaveData(CompoundTag p_28156_) {
		super.addAdditionalSaveData(p_28156_);
		p_28156_.putInt("Color", this.getColor());
		p_28156_.putInt("CarType", this.getCarType());
	}
	@Override
	public void readAdditionalSaveData(CompoundTag p_28142_) {
		super.readAdditionalSaveData(p_28142_);
		this.setCarType(p_28142_.getInt("CarType"));
		if (p_28142_.contains("Color", 99)) {

			//data fixer, fixes the change from color ID's to color rgb as int.
			if(DyeColor.byId(p_28142_.getInt("Color")).getId() == 0 && p_28142_.getInt("Color") != 0) {
				this.setColor(p_28142_.getInt("Color"));
			}
			else {
				this.setColor(FollowingCar.convertColorToInt(DyeColor.byId(p_28142_.getInt("Color"))));
			}
		}
	}

	//This is called in the main mod file. in MainFollowingCar.java
	public static AttributeSupplier setCustomAttributes() {
		return LivingEntity.createLivingAttributes().add(Attributes.ATTACK_DAMAGE, 5.0D).add(Attributes.MAX_HEALTH, 20.0D).add(Attributes.MOVEMENT_SPEED,.5D).add(Attributes.FOLLOW_RANGE,10D).add(ForgeMod.STEP_HEIGHT_ADDITION.get(),1.0F).build();
	}


	static class CarTemptGoal extends net.minecraft.world.entity.ai.goal.TemptGoal {
		private final FollowingCar car;

		public CarTemptGoal(FollowingCar p_28219_, double p_28220_, Ingredient p_28221_, boolean p_28222_) {
			super(p_28219_, p_28220_, p_28221_, p_28222_);
			this.car = p_28219_;
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
		@Override
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
		@Override
		public boolean canContinueToUse() {
			return this.car.isTame() && !this.car.isOrderedToSit() && this.ownerPlayer != null && this.ownerPlayer.isSleeping() && this.goalPos != null && !this.spaceIsOccupied();
		}
		@Override
		public void start() {
			if (this.goalPos != null && !(this.car.getPassengers().size() > 0)) {
				this.car.setInSittingPose(false);
				this.car.getNavigation().moveTo((double)this.goalPos.getX(), (double)this.goalPos.getY(), (double)this.goalPos.getZ(), (double)1.1F);
			}

		}
		@Override
		public void stop() {
			this.car.setOrderedToSit(false);
			this.car.setInSittingPose(false);
			this.car.entityData.set(RELAX_STATE_ONE, false);

			this.onBedTicks = 0;
			this.car.getNavigation().stop();
		}

		@Override
		public void tick() {
			super.tick();
			if (this.ownerPlayer != null && this.goalPos != null) {
				this.car.setInSittingPose(false);
				this.car.getNavigation().moveTo((double)this.goalPos.getX(), (double)this.goalPos.getY(), (double)this.goalPos.getZ(), (double)1.1F);
				if (this.car.distanceToSqr(this.ownerPlayer) < 2.5D) {
					++this.onBedTicks;
					if (this.onBedTicks > 16) {
						this.car.setInSittingPose(true);
						this.car.entityData.set(RELAX_STATE_ONE, true);
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
