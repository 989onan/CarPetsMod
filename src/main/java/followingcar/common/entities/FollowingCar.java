package followingcar.common.entities;





import java.util.EnumSet;

import javax.annotation.Nullable;


import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.SitGoal;
import followingcar.common.items.itemsmaster;
import followingcar.config.FollowingCarConfig;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.IRideable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.DyeColor;
import net.minecraft.item.DyeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.FlyingPathNavigator;
import net.minecraft.pathfinding.GroundPathNavigator;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.pathfinding.WalkNodeProcessor;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class FollowingCar extends TameableEntity implements IRideable{
	private static final Ingredient TAMING_ITEMS = Ingredient.fromItems(itemsmaster.GASCAN);
	private static final DataParameter<Integer> COLOR = EntityDataManager.createKey(FollowingCar.class, DataSerializers.VARINT);
	private static final DataParameter<Integer> TYPE = EntityDataManager.createKey(FollowingCar.class, DataSerializers.VARINT); //<-- this is prep for adding more car models in the future
	private int[] openDoorTime = {
			0,0,
			0,0
	};
	
	//private final int seats = 4; //how many seats the car has. Can be changed in a new kind of vehicle that has 6 or even 100 seats.
	
	public int[] GetOpenDoorTime() {
		return this.openDoorTime;
	}
	
	
	
	//taming the car / claiming it as your own / riding car
	@Override
	public ActionResultType func_230254_b_(PlayerEntity actor, Hand playerhand) {
		 ItemStack itemstack = actor.getHeldItem(playerhand);
	      Item item = itemstack.getItem();
         if (this.isTamed()) {
            if (this.isOwner(actor)) {
                  if (itemstack.getItem() == itemsmaster.GASCAN && this.getHealth() < this.getMaxHealth()) {
                     this.consumeItemFromStack(actor, itemstack);
                     this.heal((float)5);
                     if(this.getHealth() < this.getMaxHealth()) {
                    	 this.setHealth(this.getMaxHealth());
                     }
                     return ActionResultType.CONSUME;
                  }
                  try {
	                  DyeColor dyecolor = ((DyeItem)item).getDyeColor();
	                  if (dyecolor != this.getColor()) {
	               	   this.setColor(dyecolor);
	                     if (!actor.abilities.isCreativeMode) {
	                   	  this.consumeItemFromStack(actor, itemstack);
	                     }
	                  
	                     this.enablePersistence();
	                     return ActionResultType.CONSUME;
	                  }
                  }
                  catch(Exception e) {
                	  
                  }
                  
                  
               }
            
            if(!actor.isSneaking()){//if the person who right clicked it is not sneaking
            	if(this.isSitting()) { //and the car is sitting
            		if(this.isOwner(actor)) { //and the person who right clicked it is the owner of the car
            			this.func_233687_w_(false); //then make it stand up and put the owner in the car
            			openDoor(this.getPassengers().size()+1);
                    	return actor.startRiding(this) ? ActionResultType.CONSUME : ActionResultType.PASS;
            		}
            	}
            	else { //if it is not sitting, allow someone in no matter the person.
            		openDoor(this.getPassengers().size()+1);
                	return actor.startRiding(this) ? ActionResultType.CONSUME : ActionResultType.PASS;
            	}
            }
            else { //if they are sneaking, aka trying to get it to stand
            	if(this.isOwner(actor)) { //only make the car stand if it is the owner.
            		this.func_233687_w_(!this.isSitting());
            	}
            }
            return ActionResultType.func_233537_a_(this.world.isRemote);
	             
         } else if (itemstack.getItem() == itemsmaster.GASCAN) {
	            this.consumeItemFromStack(actor, itemstack);
	            if (this.rand.nextInt(3) == 0 && !net.minecraftforge.event.ForgeEventFactory.onAnimalTame(this, actor)) {
	               this.setTamedBy(actor);
	            } 
	            else {
	            }

	            this.enablePersistence();
	            return ActionResultType.CONSUME;
	         }
             
             return ActionResultType.func_233537_a_(this.world.isRemote);
          }

	@Override
	public boolean canFitPassenger(Entity passenger) {
	      return this.getPassengers().size() < 4 && !this.areEyesInFluid(FluidTags.WATER);
	   }
	
	private void openDoor(int size) {
		// TODO Auto-generated method stub
		this.openDoorTime[size-1] = 80;
	}


	public void setColor(DyeColor color) {
	      this.dataManager.set(COLOR, color.getId());
	   }
		
	
	//controlling car block
	
	
	//fixing passenger position:
	protected void applyYawToEntity(Entity entityToUpdate) {
	      entityToUpdate.setRenderYawOffset(this.rotationYaw);
	      float f = MathHelper.wrapDegrees(entityToUpdate.rotationYaw - this.rotationYaw);
	      float f1 = MathHelper.clamp(f, -105.0F, 105.0F);
	      entityToUpdate.prevRotationYaw += f1 - f;
	      entityToUpdate.rotationYaw += f1 - f;
	      entityToUpdate.setRotationYawHead(entityToUpdate.rotationYaw);
	   }
	@Override
	 public void updatePassenger(Entity passenger) {
		super.updatePassenger(passenger);
		      if (this.isPassenger(passenger)) {
		    	  
		    	  //takes passengers in rows of 2, infinitely backward. This can easily be modified for a bus with more on each row.
		    	   //
		    	  
		    	  int i = this.getPassengers().indexOf(passenger); //get where the passenger is
		    	  
		    	  int count = this.getPassengers().size(); //get how many passengers there are
		    	  
		    	  i = Math.abs(i-(count-1)); //reverse it so the first passenger would be in the last available instead
		    	  
		         float y = ((((int)(i/2)))*-1.1F)  + .5F;
		         float x = (float) ((Math.floorMod(i, 2)-.5));

		         Vector3d vector3d = (new Vector3d((double)y, 0D, x)).rotateYaw(-this.renderYawOffset * ((float)Math.PI / 180F) - ((float)Math.PI / 2F));
		         passenger.setPosition(this.getPosX() + vector3d.x, this.getPosY()-.2, this.getPosZ() + vector3d.z);
		      }
	   }
	
	//opening door animation timer
	
	
	
	
	
	//moving car with player
	@Override
	public void travel(Vector3d travelVector) {
		
		if (this.isBeingRidden()) {
			for(int i=0;i<(this.openDoorTime.length-1);i++) {
				if(this.openDoorTime[i] > 0) {
					this.openDoorTime[i] -= 4;
				}
			}
			this.stepHeight = 1F;
			LivingEntity livingentity = (LivingEntity)this.getPassengers().get(this.getPassengers().size()-1);//make the last passenger the controlling one, but since the display of the passenger...
			//positions is shifted, the person that is visually the first is the one controlling.
			Vector3d motion = new Vector3d(0,0,0);
	    	float f = livingentity.moveForward;
	    	
	    	//this code makes the car speed up and slow down if old movement is disabled. Else, use old movement.
	    	float speed = this.getAIMoveSpeed();
	    	if(!FollowingCarConfig.oldmovement.get()) {
	    		if(f != 0.0F) {
	    			if(speed < 0.01F) {
		    			speed = 0.01F;
		    		}
	    			if(speed < 1.1 && f>0) {
	    				speed += 0.02F;
	    			}else if(speed < .3 && f<0) {
	    				speed += 0.02F;
	    			}
	    			this.setRotation(livingentity.getRotationYawHead(), 0F);
	    			
	    		}
	    		else if(f == 0.0F){
	    			speed -= .05F;
	    			if(speed < 0.01F) {
		    			speed = 0F;
		    		}

			        f=0.98F;
	    		}
	    		
	    		motion = new Vector3d(0,0,f);
				
				this.setAIMoveSpeed(Math.abs(speed));
				
	    	}
	    	else {//else use simple old movement
	    		motion = new Vector3d(0,0,f);
				
				this.setAIMoveSpeed(Math.abs(f));
				
	    	}
	    	
	    	super.travel(motion);
			
			
		}
		else {
			super.travel(travelVector);
		}
		
	}
	@Override
	public boolean onLivingFall(float distance, float damageMultiplier) {
      if (distance > 3F) {
         //this.playSound(SoundEvents.ENTITY_HORSE_LAND, 0.4F, 1.0F); don't have a suspension crack or clang effect... or I'm just lazy..
      }
      int i = this.calculateFallDamage(distance-5, damageMultiplier*10);
      if (i <= 0) {
         return false;
      } else {
         this.attackEntityFrom(DamageSource.FALL, (float)i);
         if (this.isBeingRidden()) {
            for(Entity entity : this.getRecursivePassengers()) {
               entity.attackEntityFrom(DamageSource.FALL, (float)i);
            }
         }

         this.playFallSound();
         return true;
      }
   }
     
	     
	
	//end controlling car block
		
	//have to have some way of getting rid of a protected! ;)
	@Override
	 public void onKillCommand() {
	      this.remove();
	 }
	
	
	public FollowingCar(EntityType<? extends TameableEntity> type, World worldIn) {
		super(type, worldIn);
	}
	
	public DyeColor getColor() {
		return DyeColor.byId(this.dataManager.get(COLOR));
	}
	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(0, new TemptGoal(this, 1D, TAMING_ITEMS, true));
		this.goalSelector.addGoal(1, new FollowOwnerGoal(this, 1D, 10F, 5F, false));
		this.goalSelector.addGoal(2, new WaterAvoidingRandomWalkingGoal(this, 1D, 1.0000001E-5F));
		//this.goalSelector.addGoal(3, new LookAtGoal(this, PlayerEntity.class, 10.0F));
		this.goalSelector.addGoal(1, new SitGoal(this));
	}
	
	@Override
	public ILivingEntityData onInitialSpawn(IServerWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
		this.dataManager.set(COLOR,this.rand.nextInt(10));
		this.setAIMoveSpeed(1F);
		//this.dataManager.set(TYPE, this.rand.nextInt(3)); <-- this is prep for adding more car models in the future
		return spawnDataIn;
	}
	

	public int getCarType() {
	      return this.dataManager.get(TYPE);
	}
	public void setCarType(int type) {
	      this.dataManager.set(TYPE, type);
	}
	
   protected void registerData() {
      super.registerData();
      this.dataManager.register(TYPE, 1);
      //this.dataManager.register(DATA_ID_CHEST, false);
      this.dataManager.register(COLOR, DyeColor.RED.getId());
   }
   public void writeAdditional(CompoundNBT compound) {
	      super.writeAdditional(compound);
	      //compound.putInt("Type", getCarType());
	      compound.putByte("Color", (byte)this.getColor().getId());
   }
   
   public void readAdditional(CompoundNBT compound) {
	      super.readAdditional(compound);
	      
	      this.setCarType(compound.getInt("Type"));
	      if (compound.contains("Color", 99)) {
	         this.setColor(DyeColor.byId(compound.getInt("Color")));
	      }

	   }
	
	public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
		return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MOVEMENT_SPEED,.5D).createMutableAttribute(Attributes.MAX_HEALTH, 20.0D).createMutableAttribute(Attributes.ATTACK_DAMAGE, 5.0D);
	}

	@Override
	public AgeableEntity func_241840_a(ServerWorld p_241840_1_, AgeableEntity p_241840_2_) {
		return null;
	}
	
	
	static class FollowOwnerGoal extends net.minecraft.entity.ai.goal.FollowOwnerGoal{
		
		 private final TameableEntity tameable;
		   private LivingEntity owner;
		   @SuppressWarnings("unused")
		private final IWorldReader world;
		   private final double followSpeed;
		   private final PathNavigator navigator;
		   private int timeToRecalcPath;
		   private final float maxDist;
		   private final float minDist;
		   private float oldWaterCost;
		   @SuppressWarnings("unused")
		private final boolean teleportToLeaves;
		   
		   /**
		    * Returns whether an in-progress EntityAIBase should continue executing
		    */
		   public boolean shouldContinueExecuting() {
		      if (this.navigator.noPath()) {
		         return false;
		      } else if (this.tameable.isSitting()) {
		         return false;
		      } else {
		         return !(this.tameable.getDistanceSq(this.owner) <= (double)(this.maxDist * this.maxDist));
		      }
		   }

		   /**
		    * Execute a one shot task or start executing a continuous task
		    */
		   public void startExecuting() {
		      this.timeToRecalcPath = 0;
		      this.oldWaterCost = this.tameable.getPathPriority(PathNodeType.WATER);
		      this.tameable.setPathPriority(PathNodeType.WATER, 0.0F);
		   }

		   /**
		    * Reset the task's internal state. Called when this task is interrupted by another one
		    */
		   public void resetTask() {
		      this.owner = null;
		      this.navigator.clearPath();
		      this.tameable.setPathPriority(PathNodeType.WATER, this.oldWaterCost);
		   }

		public FollowOwnerGoal(TameableEntity tameable, double speed, float minDist, float maxDist,
				boolean teleportToLeaves) {
			super(tameable, speed, minDist, maxDist, teleportToLeaves);
			this.tameable = tameable;
		      this.world = tameable.world;
		      this.followSpeed = speed;
		      this.navigator = tameable.getNavigator();
		      this.minDist = minDist;
		      this.maxDist = maxDist;
		      this.teleportToLeaves = teleportToLeaves;
		      this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
		      if (!(tameable.getNavigator() instanceof GroundPathNavigator) && !(tameable.getNavigator() instanceof FlyingPathNavigator)) {
		         throw new IllegalArgumentException("Unsupported mob type for FollowOwnerGoal");
		      }
		}
		
		
		@Override
		public boolean shouldExecute() {
		      LivingEntity livingentity = this.tameable.getOwner();
		      if (livingentity == null) {
		         return false;
		      } else if (livingentity.isSpectator()) {
		         return false;
		      } else if (this.tameable.isSitting()) {
		         return false;
		      } else if (this.tameable.getDistanceSq(livingentity) < (double)(this.minDist * this.minDist)) {
		         return false;
		      } else if (this.tameable.getPassengers().size() > 0) {
		    	  return false;
		      }
		      else {
		         this.owner = livingentity;
		         return true;
		      }
		   }
		@Override
		public void tick() {
		      //this.tameable.getLookController().setLookPositionWithEntity(this.owner, 10.0F, (float)this.tameable.getVerticalFaceSpeed());
		      if (--this.timeToRecalcPath <= 0) {
		         this.timeToRecalcPath = 10;
		         if (!this.tameable.getLeashed() && !this.tameable.isPassenger()) {
		            if (this.tameable.getDistanceSq(this.owner) >= 144.0D) {
		               this.tryToTeleportNearEntity();
		            } else {
		               this.navigator.tryMoveToEntityLiving(this.owner, this.followSpeed);
		            }

		         }
		      }
		   }

		   private void tryToTeleportNearEntity() {
		      BlockPos blockpos = this.owner.getPosition();

		      for(int i = 0; i < 10; ++i) {
		         int j = this.getRandomNumber(-3, 3);
		         int k = this.getRandomNumber(-1, 1);
		         int l = this.getRandomNumber(-3, 3);
		         boolean flag = this.tryToTeleportToLocation(blockpos.getX() + j, blockpos.getY() + k, blockpos.getZ() + l);
		         if (flag) {
		            return;
		         }
		      }

		   }

		   private boolean tryToTeleportToLocation(int x, int y, int z) {
		      if (Math.abs((double)x - this.owner.getPosX()) < 2.0D && Math.abs((double)z - this.owner.getPosZ()) < 2.0D) {
		         return false;
		      } else if (!this.isTeleportFriendlyBlock(new BlockPos(x, y, z))) {
		         return false;
		      } else {
		         this.tameable.setLocationAndAngles((double)x + 0.5D, (double)y, (double)z + 0.5D, this.tameable.rotationYaw, this.tameable.rotationPitch);
		         this.navigator.clearPath();
		         return true;
		      }
		   }

		   private boolean isTeleportFriendlyBlock(BlockPos pos) {
		      PathNodeType pathnodetype = WalkNodeProcessor.func_237231_a_(this.world, pos.toMutable());
		      if (pathnodetype != PathNodeType.WALKABLE) {
		         return false;
		      } else {
		         BlockState blockstate = this.world.getBlockState(pos.down());
		         if (!this.teleportToLeaves && blockstate.getBlock() instanceof LeavesBlock) {
		            return false;
		         } else {
		            BlockPos blockpos = pos.subtract(this.tameable.getPosition());
		            return this.world.hasNoCollisions(this.tameable, this.tameable.getBoundingBox().offset(blockpos));
		         }
		      }
		   }

		   private int getRandomNumber(int min, int max) {
		      return this.tameable.getRNG().nextInt(max - min + 1) + min;
		   }
	}
	
	static class TemptGoal extends net.minecraft.entity.ai.goal.TemptGoal {
	      @Nullable
	      private PlayerEntity temptingPlayer;
	      private final FollowingCar car;
	      
	      @Override
	      public void tick() {
	          super.tick();
	          if (this.temptingPlayer == null && this.creature.getRNG().nextInt(600) == 0) {
	             this.temptingPlayer = this.closestPlayer;
	          } else if (this.creature.getRNG().nextInt(500) == 0) {
	             this.temptingPlayer = null;
	          }

	       }
	      
	      public TemptGoal(FollowingCar carIn, double speedIn, Ingredient temptItemsIn, boolean scaredByPlayerMovementIn) {
	          super(carIn, speedIn, temptItemsIn, false);
	          this.car = carIn;
	       }
	      
	      @Override
	      public boolean shouldExecute() {
	    	  return super.shouldExecute() && !this.car.isTamed();
	      }
	}
	
	//make it so that car behaves as if it can fall far distances.
	@Override
	public int getMaxFallHeight() {
	      return 5;
	   }


	@Override
	public boolean boost() {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public void travelTowards(Vector3d travelVec) {
		super.travel(travelVec);
	}


	@Override
	public float getMountedSpeed() {
		// TODO Auto-generated method stub
		return (float)this.getAttributeValue(Attributes.MOVEMENT_SPEED) * 0.225F;
	}


}
