package followingcar;


import java.util.ArrayList;


import java.util.function.Function;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import followingcar.common.entities.FollowingCar;
import followingcar.config.ConfigPacket;
import followingcar.config.FollowingCarConfig;
import followingcar.core.init.EntityTypeInit;
import followingcar.core.init.Registries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.ITeleporter;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.network.NetworkDirection;
import net.minecraft.world.level.GameRules;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;

/*
	DO THESE BEFORE UPLOADING (to @989onan): 
	1. change the build.gradle file to reflect version number
	2. check mods.toml to make sure it reflects the mod info.
	
	DO THESE AFTER UPLOADING
	1. change updates.json on github to match
	2. upload src to github using github desktop
	3. update carpetmodextras updates.json as well if applicable 
*/



@Mod("followingcarmod")
@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
public class MainFollowingCar {
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MODID = "followingcarmod";
    
    public static int disc = 0;
    
    
    //easter egg names ^_^
    @SuppressWarnings("serial")
    public static final ArrayList<String> protectednames = new ArrayList<String>() {
		{
			add("Felix");
			add("Kodachi");
			add("Amari");
			add("T.I.F.F.");
			add("T.I.O.S.");
			add("Jane");
			add("Rakza");
			add("Arashi");
			add("Kazia");
			add("Amay-Lia");
			add("Chokuto");
			add("Makibishi");
			add("Hibiki");
			add("Kadrian");
			add("Fumei");
			add("Guardy");
		}
	};
	
    
    public MainFollowingCar() {
    	
    	//ModLoadingContext.get().registerExtensionPoint(IExtensionPoint.DisplayTest.class, () -> new IExtensionPoint.DisplayTest(() -> "ANY", (remote, isServer) -> true));
    	
    	//Thanks to Aquaculture!! https://github.com/TeamMetallurgy/Aquaculture/blob/master/src/main/java/com/teammetallurgy/aquaculture
    	final IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
    	registerDeferredRegistries(modBus);
    	
    	FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
    	FollowingCarConfig.loadConfig(FollowingCarConfig.config, FMLPaths.CONFIGDIR.get().resolve("car-pets-config.toml").toString());
    	if (FMLEnvironment.dist != Dist.CLIENT) {
    		//sending server config to client
    		 FollowingCarConfig.carscale.get();
		}
    	//EntityTypeInit.ENTITIES.register(bus); <-- Doesn't work
        MinecraftForge.EVENT_BUS.register(this);
        ConfigPacket.register();
        
    }
    
    //this prevents the player from suffocating in a wall while riding the car pet.
    @SubscribeEvent
    public void hurtevent(LivingHurtEvent event)
    {
        if(event.getEntity().getVehicle() instanceof FollowingCar) {
        	if(event.getSource() == DamageSource.IN_WALL){
        		event.setCanceled(true);//if entity took damage from suffocating while in FollowingCar, cancel event.
        	}      	
        }
        //else they are not in FollowingCar and taking suffocation damage don't change the damage.
    	
    }
    
    @SubscribeEvent
    public void playerJoin(final PlayerLoggedInEvent event) {
    	MainFollowingCar.LOGGER.info("Player Connected!");
    	ConfigPacket.INSTANCE.sendTo(new ConfigPacket.MyPacket(FollowingCarConfig.carscale.get()), ((ServerPlayer)event.getPlayer()).connection.connection, NetworkDirection.PLAY_TO_CLIENT);
    }
    public static final DeferredRegister<SoundEvent> SOUND_EVENT_DEFERRED = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, MainFollowingCar.MODID);
    
    public static final RegistryObject<SoundEvent> CAR_PURR = registerSound("car_purr");
    
    public static final RegistryObject<SoundEvent> CAR_REV = registerSound("car_rev");
    
    public static final RegistryObject<SoundEvent> CAR_HURT = registerSound("car_hurt");
    
    private static RegistryObject<SoundEvent> registerSound(String name) {
        ResourceLocation resourceLocation = Location(name);
        return SOUND_EVENT_DEFERRED.register(name, () -> new SoundEvent(resourceLocation));
    }
    
    @SubscribeEvent
    public void onDeath(LivingDeathEvent event) {
    	
    	LivingEntity entity = event.getEntityLiving();
    	if(entity instanceof FollowingCar) {
    		FollowingCar entityfollowing = ((FollowingCar)entity);
    		if(entityfollowing.getCustomName() != null) {
	    		if(MainFollowingCar.protectednames.contains(entityfollowing.getCustomName().getString()) && (!(event.getSource() == DamageSource.OUT_OF_WORLD))) {
	    			event.setCanceled(true);
	    			//PlayerEntity owner = (PlayerEntity) entityfollowing.getOwner();//get the owner
	    			
	    			entityfollowing.setHealth(entityfollowing.getMaxHealth());
	    			
	    			//yes, this is buggy. I don't care. Just keep your bed not covered.
    				//LOGGER.info("Current dimension: "+entityfollowing.level.dimension().toString()+" destination dimension: "+Level.OVERWORLD.toString()+" Do they not Equal each other? "+!(entityfollowing.level.dimension() == Level.OVERWORLD));
    				if((!(entityfollowing.level.isClientSide)) && !(entityfollowing.level.dimension() == Level.OVERWORLD)) {
    					if(entityfollowing.getOwner() != null) {
	    					if(entityfollowing.getOwner().getSleepingPos().isPresent()) {//TODO: doesn't run even if the player has a bed spawn point. Maybe I'm using the wrong check? I tried inverse and non inverse of if statement above as well.
	    						entityfollowing = changeDimension(entityfollowing,entityfollowing.getServer().getLevel(Level.OVERWORLD));
	    						entityfollowing.teleportTo(entityfollowing.getOwner().getSleepingPos().get().getX(), entityfollowing.getOwner().getSleepingPos().get().getY()+1, entityfollowing.getOwner().getSleepingPos().get().getZ());
	    						entityfollowing.setOrderedToSit(true);
	    					}
    					}
    					else {
    						entityfollowing = changeDimension(entityfollowing,entityfollowing.getServer().overworld());
    						entityfollowing.teleportTo(entityfollowing.level.getLevelData().getYSpawn(),entityfollowing.level.getLevelData().getYSpawn(),entityfollowing.level.getLevelData().getZSpawn());
        					entityfollowing.setOrderedToSit(true);
    					}
    				}
    				else if(!(entityfollowing.level.isClientSide)) {
    					if(entityfollowing.getOwner() != null) {
	    					if(entityfollowing.getOwner().getSleepingPos().isPresent()) {//TODO: doesn't run even if the player has a bed spawn point. Maybe I'm using the wrong check? I tried inverse and non inverse of if statement above as well.
	    						entityfollowing.teleportTo(entityfollowing.getOwner().getSleepingPos().get().getX(), entityfollowing.getOwner().getSleepingPos().get().getY()+1, entityfollowing.getOwner().getSleepingPos().get().getZ());
	    						entityfollowing.setOrderedToSit(true);
	    					}
	    					else {
	    						entityfollowing.teleportTo(entityfollowing.level.getLevelData().getYSpawn(),entityfollowing.level.getLevelData().getYSpawn(),entityfollowing.level.getLevelData().getZSpawn());
	        					entityfollowing.setOrderedToSit(true);
	    					}
    					}
    					else {
    						entityfollowing.teleportTo(entityfollowing.level.getLevelData().getYSpawn(),entityfollowing.level.getLevelData().getYSpawn(),entityfollowing.level.getLevelData().getZSpawn());
        					entityfollowing.setOrderedToSit(true);
    					}
    				}
	    			
	    			
	    			//send fake death message
	    			if (!entityfollowing.level.isClientSide && entityfollowing.level.getGameRules().getBoolean(GameRules.RULE_SHOWDEATHMESSAGES) && entityfollowing.getOwner() instanceof ServerPlayer) {
	    		         //entityfollowing.getOwner().sendMessage(entityfollowing.getCombatTracker().getDeathMessage(), Util.DUMMY_UUID); //isn't needed because it already sends death message.
	    		         entityfollowing.getOwner().sendSystemMessage(Component.literal(entityfollowing.getCustomName().getString()+" respawned at world spawn point!"));
	    			}
	    			
	    			
	    			
	    		}
    		}
    	}
    	
    }
    
    @SuppressWarnings("unchecked")
	private <T extends Entity> T changeDimension(T entity,ServerLevel dim) {
    	return (T) entity.changeDimension(dim, new ITeleporter()
        {
            @Override
            public Entity placeEntity(Entity entity, ServerLevel currentWorld, ServerLevel destWorld, float yaw, Function<Boolean, Entity> repositionEntity)
            {
                Entity repositionedEntity = repositionEntity.apply(false);
                repositionedEntity.setPos(entity.position().x, entity.position().y, entity.position().z);
                repositionedEntity.setUUID(Mth.createInsecureUUID());
                return repositionedEntity;
            }
        });
	}
    
    
    
	public static ResourceLocation Location(String name) {
    	return new ResourceLocation(MODID,name);
    }
	public static ResourceLocation ExtrasLocation(String name) {
		return new ResourceLocation(MODID+"extras",name);
	}
	
	@SubscribeEvent
	public void setup(final EntityAttributeCreationEvent event)
	{
		event.put((EntityType<? extends LivingEntity>)EntityTypeInit.FOLLOWING_CAR.get(), FollowingCar.setCustomAttributes());
    }

	
	
	public void registerDeferredRegistries(IEventBus modBus) {
        Registries.ITEM_DEFERRED.register(modBus);
        EntityTypeInit.ENTITY_DEFERRED.register(modBus);
        MainFollowingCar.SOUND_EVENT_DEFERRED.register(modBus);
    }
	
	
	
}
