package followingcar.common.entities;




import javax.annotation.Nullable;



import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.ai.goal.FollowOwnerGoal;
import net.minecraft.entity.ai.goal.SitGoal;
import followingcar.common.items.itemsmaster;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
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
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class FollowingCar extends TameableEntity{
	private static final Ingredient TAMING_ITEMS = Ingredient.fromItems(itemsmaster.GASCAN);
	private static final DataParameter<Integer> COLOR = EntityDataManager.createKey(FollowingCar.class, DataSerializers.VARINT);
	private static final DataParameter<Integer> TYPE = EntityDataManager.createKey(FollowingCar.class, DataSerializers.VARINT); //<-- this is prep for adding more car models in the future
	private static final DataParameter<Boolean> DATA_ID_CHEST = EntityDataManager.createKey(FollowingCar.class, DataSerializers.BOOLEAN);
	public float jumpPower;
	
	
	//taming the car / claiming it as your own
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
            if(!actor.isSneaking()){
	            if(!(this.getPassengers().size() >= 4)) {
	            	if(this.isSitting()) {
	            		this.func_233687_w_(false);
	            	}
	          	  return actor.startRiding(this) ? ActionResultType.CONSUME : ActionResultType.PASS;
	          	  
	            }
            }
            else {
            	this.func_233687_w_(!this.isSitting());
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
		    	  int i = this.getPassengers().indexOf(passenger);
		         float y = ((((int)(i/2)))*-1.1F)  + .5F;
		         float x = (float) ((Math.floorMod(i, 2)-.5));

		         Vector3d vector3d = (new Vector3d((double)y, 0D, x)).rotateYaw(-this.renderYawOffset * ((float)Math.PI / 180F) - ((float)Math.PI / 2F));
		         passenger.setPosition(this.getPosX() + vector3d.x, this.getPosY()-.2, this.getPosZ() + vector3d.z);
		         passenger.setRotationYawHead(this.rotationYaw);
		         this.applyYawToEntity(passenger);
		         if (passenger instanceof LivingEntity) {
		             ((LivingEntity)passenger).renderYawOffset = this.renderYawOffset;
		          }

		      }
	   }
	
	//moving car with player
	@Override
	public void travel(Vector3d travelVector) {
		
		if (this.isBeingRidden()) {
			
			LivingEntity livingentity = (LivingEntity)this.getPassengers().get(0);
			
			
	    		float f = livingentity.moveForward;
			f *= 2F;
			this.setRotation(this.getPassengers().get(0).getRotationYawHead(), 0F);
			Vector3d motion = new Vector3d(0.0D, 0.0D, f));
			super.travel(motion);
		}
		else {
			super.travel(travelVector);
		}
		
	}
	     
	     
	     
	
	//end controlling car block
	
	
	
	public FollowingCar(EntityType<? extends TameableEntity> type, World worldIn) {
		super(type, worldIn);
	}
	
	public DyeColor getColor() {
		return DyeColor.byId(this.dataManager.get(COLOR));
	}
	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(0, new TemptGoal(this, .5D, TAMING_ITEMS, true));
		this.goalSelector.addGoal(1, new FollowOwnerGoal(this, .5D, 10F, 5F, false));
		this.goalSelector.addGoal(2, new WaterAvoidingRandomWalkingGoal(this, .5D, 1.0000001E-5F));
		//this.goalSelector.addGoal(3, new LookAtGoal(this, PlayerEntity.class, 10.0F));
		this.goalSelector.addGoal(1, new SitGoal(this));
	}
	
	@Override
	public ILivingEntityData onInitialSpawn(IServerWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
		this.dataManager.set(COLOR,this.rand.nextInt(10));
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
	      compound.putInt("Type", getCarType());
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


}
