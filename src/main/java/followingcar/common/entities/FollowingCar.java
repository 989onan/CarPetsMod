package followingcar.common.entities;




import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FollowOwnerGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.SitWhenOrderedToGoal;
import followingcar.client.render.FollowingCarRenderRegistry;
import followingcar.common.items.CarChangerItem;
import followingcar.common.items.itemsmaster;
import followingcar.config.FollowingCarConfig;
import followingcar.core.init.CarTypeRegistry;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.PlayerRideable;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.item.DyeItem;

public class FollowingCar extends TamableAnimal implements PlayerRideable{
	protected static final Ingredient TAMING_ITEMS = Ingredient.of(itemsmaster.GASCAN);
	protected static final EntityDataAccessor<Integer> COLOR = SynchedEntityData.defineId(FollowingCar.class, EntityDataSerializers.INT);
	protected static final EntityDataAccessor<Integer> TYPE = SynchedEntityData.defineId(FollowingCar.class, EntityDataSerializers.INT); 
	public static final EntityDataAccessor<Boolean> RELAX_STATE_ONE = SynchedEntityData.defineId(FollowingCar.class, EntityDataSerializers.BOOLEAN); //if the car is lying down
	
	
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
	
	
	protected SoundEvent getHurtSound(DamageSource p_21239_) {
	      return FollowingCarRenderRegistry.CAR_HURT;
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
					int dyecolor = FollowingCar.convertColorToInt(((DyeItem)item).getDyeColor());
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


		return this.getPassengers().size() < maxpassengers && !this.isEyeInFluid(FluidTags.WATER);
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
			this.playSound(FollowingCarRenderRegistry.CAR_REV,  0.6F + 0.4F * (this.random.nextFloat() - this.random.nextFloat()), (float) (1.0*(this.getDeltaMovement().lengthSqr()/2)));
		}
		if(this.getPassengers().size() > 0) {
			LivingEntity livingentity = (LivingEntity)this.getPassengers().get(this.getPassengers().size()-1);
			
			if(livingentity != null) {
				if(Math.abs(livingentity.xxa) > 0.1F) {
					this.rotationsign = (short) (Math.abs(livingentity.xxa)/livingentity.xxa);
				}
				else {
					this.rotationsign = 0;
				}
			}
		}
		else {
			this.rotationsign = 0;
		}
		
		
		 if (this.tickCount % 20 == 0 && this.entityData.get(RELAX_STATE_ONE).booleanValue() && !this.isOrderedToSit()) {
			 this.playSound(FollowingCarRenderRegistry.CAR_PURR, 0.6F + 0.4F * (this.random.nextFloat() - this.random.nextFloat()), 1.0F);
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
			x = (((x)*CarTypeRegistry.CarScale));
			y = (((y)*CarTypeRegistry.CarScale));
			z = (((z-.5)*CarTypeRegistry.CarScale));
			
			//rearranges them and puts them into a rotated form at 0,0,0 so it matches with the car's rotation.
			
			Vec3 vector3d = (new Vec3(y, z, x)).yRot(-(this.yBodyRot) * ((float)Math.PI / 180F) - ((float)Math.PI / 2F));
			
			
			//sets passenger position to the rotated vector translated to the car's position
			passenger.setPos(this.getX()+vector3d.x,this.getY()+vector3d.y,this.getZ()+vector3d.z);
			
			//sets their body rotation to the car's rotation
			passenger.setYBodyRot(this.getYRot());
			
			//fixes head rotation that's offsetted from 
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
	Vec3 motion = new Vec3(0,0,0);
	
	//moving car with player
	@Override
	public void travel(Vec3 travelVector) {

		if (this.getPassengers().size() > 0) {
			this.maxUpStep = 1.1F;
			LivingEntity livingentity = (LivingEntity)this.getPassengers().get(this.getPassengers().size()-1);//make the last passenger the controlling one, but since the display of the passenger...
			//positions is shifted, the person that is visually the first is the one controlling.
			
			
			
			
			float cntrlx = livingentity.zza;
			float cntrly = livingentity.xxa;
			
			
			//this code makes the car speed up and slow down if old movement is disabled. Else, use old movement.
			if(!FollowingCarConfig.oldmovement.get()) {
				Vec3 motion = this.getDeltaMovement();
				//above is beginning stuff don't touch!
				
				
				
				float defaultAcc = (float) (CarTypeRegistry.CarTypes.containsKey(this.getActualCarType()) ? CarTypeRegistry.CarTypes.get(this.getActualCarType()).getAcceleration(): (60-0)/ /*X Seconds:*/ 5.7F);
				
				defaultAcc = ((defaultAcc/2.237F)*(2.8F/20F));//from tick/block to meters/second. had to bullcrap this because minecraft physics != real physics
				
				
				CarTypeRegistry.CarTypes.get(this.getActualCarType()).setMaxSpeed(117);
				
				
				
				
				
				
				
				float maxspeed = (CarTypeRegistry.CarTypes.get(this.getActualCarType()).getMaxSpeed()/2.23694F);
				
		  	    
		  	    float curspeed = (float) ((this.getDeltaMovement().horizontalDistance()))/(1F/20F);
		  	    
		  	    
		  	    Vec3 diffvec =	getInputVector(1,new Vec3(0,0,((defaultAcc))*(Math.abs(cntrlx) > 0 ? (Math.abs(cntrlx)/cntrlx) : 0)),this.getYRot());
	  	    	if(curspeed<maxspeed) {
	  	    		diffvec  = diffvec.scale(Mth.clamp((maxspeed/((-Math.pow((curspeed), 2)/maxspeed)+maxspeed)),.001,1));
	  	    		
	  	    		motion = motion.add(diffvec.scale((1F/20F)));
	  	    		
	  	    		
	  	    	}
	  	    	//else don't add to motion aka don't accelerate
				
				
				
				
				this.setSpeed(1F);
				this.setDeltaMovement(motion);
				this.supertravel(new Vec3(0,0,0));
				
			}
			else {//else use simple old movement
				Vec3 motion = new Vec3(0,0,cntrlx);

				this.setSpeed(Math.abs(cntrlx));
				this.setYRot(this.getYRot()+(cntrly*5));
				super.travel(motion);
			}

			


		}
		else {
			super.travel(travelVector);
		}

	}
	
	
	private static Vec3 getInputVector(float p_20017_, Vec3 p_20016_, float p_20018_) {
	      double d0 = p_20016_.lengthSqr();
	      if (d0 < 1.0E-7D) {
	         return Vec3.ZERO;
	      } else {
	         Vec3 vec3 = (d0 > 1.0D ? p_20016_.normalize() : p_20016_).scale((double)p_20017_);
	         float f = Mth.sin(p_20018_ * ((float)Math.PI / 180F));
	         float f1 = Mth.cos(p_20018_ * ((float)Math.PI / 180F));
	         return new Vec3(vec3.x * (double)f1 - vec3.z * (double)f, vec3.y, vec3.z * (double)f1 + vec3.x * (double)f);
	      }
	   }


	@SuppressWarnings("deprecation")
	public void supertravel(Vec3 p_21280_) {
		
		
		
		LivingEntity livingentity = (LivingEntity)this.getPassengers().get(this.getPassengers().size()-1);
	      if (this.isEffectiveAi() || this.isControlledByLocalInstance()) {
	         double adjustedgravity = 0.08D;
	         AttributeInstance gravity = this.getAttribute(net.minecraftforge.common.ForgeMod.ENTITY_GRAVITY.get());
	         boolean flag = this.getDeltaMovement().y <= 0.0D;
	         adjustedgravity = gravity.getValue();

	         FluidState fluidstate = this.level.getFluidState(this.blockPosition());
	         if (this.isInWater() && this.isAffectedByFluids() && !this.canStandOnFluid(fluidstate.getType())) {
	            double d8 = this.getY();
	            float f5 = this.isSprinting() ? 0.9F : this.getWaterSlowDown();
	            float f6 = 0.02F;
	            float f7 = (float)EnchantmentHelper.getDepthStrider(this);
	            if (f7 > 3.0F) {
	               f7 = 3.0F;
	            }

	            if (!this.onGround) {
	               f7 *= 0.5F;
	            }

	            if (f7 > 0.0F) {
	               f5 += (0.54600006F - f5) * f7 / 3.0F;
	               f6 += (this.getSpeed() - f6) * f7 / 3.0F;
	            }

	            if (this.hasEffect(MobEffects.DOLPHINS_GRACE)) {
	               f5 = 0.96F;
	            }

	            f6 *= (float)this.getAttribute(net.minecraftforge.common.ForgeMod.SWIM_SPEED.get()).getValue();
	            //this.moveRelative(f6, p_21280_);
	            this.move(MoverType.SELF, this.getDeltaMovement());
	            Vec3 vec36 = this.getDeltaMovement();
	            if (this.horizontalCollision && this.onClimbable()) {
	               vec36 = new Vec3(vec36.x, 0.2D, vec36.z);
	            }

	            this.setDeltaMovement(vec36.multiply((double).1, (double)0.8F, (double).1));
	            Vec3 vec32 = this.getFluidFallingAdjustedMovement(adjustedgravity, flag, this.getDeltaMovement());
	            this.setDeltaMovement(vec32);
	            if (this.horizontalCollision && this.isFree(vec32.x, vec32.y + (double)0.6F - this.getY() + d8, vec32.z)) {
	               this.setDeltaMovement(vec32.x, (double)0.3F, vec32.z);
	            }
	         } else if (this.isInLava() && this.isAffectedByFluids() && !this.canStandOnFluid(fluidstate.getType())) {
	            double d7 = this.getY();
	            //this.moveRelative(0.02F, p_21280_);
	            this.move(MoverType.SELF, this.getDeltaMovement());
	            if (this.getFluidHeight(FluidTags.LAVA) <= this.getFluidJumpThreshold()) {
	               this.setDeltaMovement(this.getDeltaMovement().multiply(0.5D, (double)0.8F, 0.5D));
	               Vec3 vec33 = this.getFluidFallingAdjustedMovement(adjustedgravity, flag, this.getDeltaMovement());
	               this.setDeltaMovement(vec33);
	            } else {
	               this.setDeltaMovement(this.getDeltaMovement().scale(0.5D));
	            }

	            if (!this.isNoGravity()) {
	               this.setDeltaMovement(this.getDeltaMovement().add(0.0D, -adjustedgravity / 4.0D, 0.0D));
	            }

	            Vec3 vec34 = this.getDeltaMovement();
	            if (this.horizontalCollision && this.isFree(vec34.x, vec34.y + (double)0.6F - this.getY() + d7, vec34.z)) {
	               this.setDeltaMovement(vec34.x, (double)0.3F, vec34.z);
	            }
	         } else if (this.isFallFlying()) {
	            Vec3 vec3 = this.getDeltaMovement();
	            if (vec3.y > -0.5D) {
	               this.fallDistance = 1.0F;
	            }

	            Vec3 vec31 = this.getLookAngle();
	            float f = this.getXRot() * ((float)Math.PI / 180F);
	            double d1 = Math.sqrt(vec31.x * vec31.x + vec31.z * vec31.z);
	            double d3 = vec3.horizontalDistance();
	            double d4 = vec31.length();
	            float f1 = Mth.cos(f);
	            f1 = (float)((double)f1 * (double)f1 * Math.min(1.0D, d4 / 0.4D));
	            vec3 = this.getDeltaMovement().add(0.0D, adjustedgravity * (-1.0D + (double)f1 * 0.75D), 0.0D);
	            if (vec3.y < 0.0D && d1 > 0.0D) {
	               double d5 = vec3.y * -0.1D * (double)f1;
	               vec3 = vec3.add(vec31.x * d5 / d1, d5, vec31.z * d5 / d1);
	            }

	            if (f < 0.0F && d1 > 0.0D) {
	               double d9 = d3 * (double)(-Mth.sin(f)) * 0.04D;
	               vec3 = vec3.add(-vec31.x * d9 / d1, d9 * 3.2D, -vec31.z * d9 / d1);
	            }

	            if (d1 > 0.0D) {
	               vec3 = vec3.add((vec31.x / d1 * d3 - vec3.x) * 0.1D, 0.0D, (vec31.z / d1 * d3 - vec3.z) * 0.1D);
	            }

	            this.setDeltaMovement(vec3.multiply((double)0.99F, (double)0.98F, (double)0.99F));
	            this.move(MoverType.SELF, this.getDeltaMovement());
	            if (this.horizontalCollision && !this.level.isClientSide) {
	               double d10 = this.getDeltaMovement().horizontalDistance();
	               double d6 = d3 - d10;
	               float f2 = (float)(d6 * 10.0D - 3.0D);
	               if (f2 > 0.0F) {
	                  this.playSound(this.getFallDamageSound((int)f2), 1.0F, 1.0F);
	                  this.hurt(DamageSource.FLY_INTO_WALL, f2);
	               }
	            }

	            if (this.onGround && !this.level.isClientSide) {
	               this.setSharedFlag(7, false);
	            }
	         } else {
	        	
	        	
		  	    Vec3 A = this.getDeltaMovement();
		  	    
	  	    	
	        	 
	            BlockPos blockpos = this.getBlockPosBelowThatAffectsMyMovement();
	            float f3 = this.level.getBlockState(this.getBlockPosBelowThatAffectsMyMovement()).getFriction(level, this.getBlockPosBelowThatAffectsMyMovement(), this);
	            
	            //first we calculate friction based on whether gas is being pressed or not
	            float f4 = this.onGround ? Mth.clamp(f3*(Math.abs(livingentity.zza)>0.1F ? 1.6F : 1.5F),0F, .98F) : 1F;
	            
	            //get the max speed and current speed from before.
	            float maxspeed = (CarTypeRegistry.CarTypes.get(this.getActualCarType()).getMaxSpeed()/2.23694F);
		  	    float curspeed = (float) ((this.getDeltaMovement().horizontalDistance()))/(1F/20F);
	            
	            //next we calculate the dot product between 90 degrees from forward and the momentum direction
			    
			    Vec3 B = this.getForward().yRot(90*((float)Math.PI / 180F));
			    
			    float top = (float) ((A.x*B.x)+(A.y*B.y)+(A.z*B.z));
			    float bottom = (float) ((Math.pow(Math.pow(A.x,2)+Math.pow(A.y,2)+Math.pow(A.z,2),.5D))*(Math.pow(Math.pow(B.x,2)+Math.pow(B.y,2)+Math.pow(B.z,2),.5D)));
			   
			    float sidewaysfactor = Math.abs(-(top/bottom));
			    
			    sidewaysfactor = Mth.clamp(sidewaysfactor/((curspeed+60F)/maxspeed),.4F,1F);
			    
			    
			    
			    //reverse the factor because if it's not going sideways it will be 1 instead of 0 which will multiply by the friction.
			    sidewaysfactor = Math.abs(sidewaysfactor-1);
			    
			    
			    sidewaysfactor = Mth.clamp((sidewaysfactor*((CarTypeRegistry.CarTypes.get(this.getActualCarType()).getDriftMultiplier()+.001F))),.001F,1F);
			    //multiply the factor by drift factor so the strength isn't like... go to 0 speed instantly when going almost perfectly sideways
			    //if the drift factor is 0, then it 
			    if(curspeed/maxspeed < .1F && Math.abs(livingentity.zza) < 0.1F) {
			    	sidewaysfactor = .2F;
			    }
			    
			    //LOGGER.info("sidewaysfactor: "+sidewaysfactor); //debugging
			    //multiply friction by going sideways
			    f4 = this.onGround ? Mth.clamp((f4*CarTypeRegistry.CarTypes.get(this.getActualCarType()).getRollFrictionMultiplier()) * ((float)(sidewaysfactor)),0F,.98F): 1F; //multiply friction by sideways factor to get friction increased by 2 times when going sideways
		  	    
	            
	            //end sideways friction block
			    
	            Vec3 vec35 = this.handleRelativeFrictionAndCalculateMovement(p_21280_, f3);
	            double d2 = vec35.y;
	            if (this.hasEffect(MobEffects.LEVITATION)) {
	               d2 += (0.05D * (double)(this.getEffect(MobEffects.LEVITATION).getAmplifier() + 1) - vec35.y) * 0.2D;
	               this.fallDistance = 0.0F;
	            } else if (this.level.isClientSide && !this.level.hasChunkAt(blockpos)) {
	               if (this.getY() > (double)this.level.getMinBuildHeight()) {
	                  d2 = -0.1D;
	               } else {
	                  d2 = 0.0D;
	               }
	            } else if (!this.isNoGravity()) {
	               d2 -= adjustedgravity;
	            }

	            if (this.shouldDiscardFriction()) {
	               this.setDeltaMovement(vec35.x, d2, vec35.z);
	            } else {
	               this.setDeltaMovement(vec35.x * (double)f4, d2 * (double)0.9999F, vec35.z * (double)f4);
	            }
	            this.deltarotation = 0F;
	            
	            float rotationspeed = 10/(Math.abs(10F*(float)(curspeed+(maxspeed/4F))/maxspeed));
		  	    if(livingentity.xxa != 0.0F) {
		  	    	double speeddirection = (this.getDeltaMovement().dot(this.getForward()));
		  	    	if(Math.abs(speeddirection) < .01F) {
		  	    		speeddirection = 1;
		  	    	}
		  	    	
		  	    	this.deltarotation = this.getYRot();
		  	    	
		  	    	this.setYRot((float) (this.getYRot()+(rotationspeed*-((Math.abs(livingentity.xxa)/livingentity.xxa)*(Math.abs(speeddirection)/speeddirection)))));
		  	    	this.deltarotation = this.getYRot();
		  	    	
		  	    }
		  	    
	         }
	      }
	     
	      //motion = this.getDeltaMovement();
	      this.calculateEntityAnimation(this, this instanceof FlyingAnimal);
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
		return LivingEntity.createLivingAttributes().add(Attributes.ATTACK_DAMAGE, 5.0D).add(Attributes.MAX_HEALTH, 20.0D).add(Attributes.MOVEMENT_SPEED,.5D).add(Attributes.FOLLOW_RANGE,10D).build();
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
