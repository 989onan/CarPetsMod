package followingcar;


import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import followingcar.client.render.FollowingCarRenderRegistry;
import followingcar.common.entities.FollowingCar;
import followingcar.config.FollowingCarConfig;
import followingcar.core.init.EntityTypeInit;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.GameRules;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;

@Mod("followingcarmod")
@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
public class MainFollowingCar {
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MODID = "followingcarmod";
    
    
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
		}
	};
	
    
    public MainFollowingCar() {
    	FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
    	FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientRegistries);
    	FollowingCarConfig.loadConfig(FollowingCarConfig.config, FMLPaths.CONFIGDIR.get().resolve("car-pets-config.toml").toString());
    	//EntityTypeInit.ENTITIES.register(bus); <-- Doesn't work
        MinecraftForge.EVENT_BUS.register(this);
    }
    
    //this prevents the player from suffocating in a wall while riding the car pet.
    @SubscribeEvent
    public void hurtevent(LivingHurtEvent event)
    {
        if(event.getEntity().getRidingEntity() instanceof FollowingCar) {
        	if(event.getSource().getDamageType() == "inWall"){
        		event.setCanceled(true);//if entity took damage from suffocating while in FollowingCar, cancel event.
        	}      	
        }
        //else they are not in FollowingCar and taking suffocation damage don't change the damage.
    	
    }
    @SubscribeEvent
    public void onDeath(LivingDeathEvent event) {
    	LivingEntity entity = event.getEntityLiving();
    	if(entity instanceof FollowingCar) {
    		FollowingCar entityfollowing = ((FollowingCar)entity);
    		if(MainFollowingCar.protectednames.contains(entityfollowing.getCustomName().getString()) && (!(event.getSource() == DamageSource.OUT_OF_WORLD))) {
    			event.setCanceled(true);
    			//PlayerEntity owner = (PlayerEntity) entityfollowing.getOwner();//get the owner
    			
    			entityfollowing.setHealth(entityfollowing.getMaxHealth());
    			
    			
    			//yes, this is buggy. I don't care. Just keep your bed not covered.
    			try {
    				if(!(entityfollowing.world.isRemote)) {
    					if(entityfollowing.getOwner().getBedPosition().isPresent()) {
    						entityfollowing.teleportKeepLoaded(entityfollowing.getOwner().getBedPosition().get().getX(), entityfollowing.getOwner().getBedPosition().get().getY()+1, entityfollowing.getOwner().getBedPosition().get().getZ());
    					}
    					else {//doesn't work.
    						entityfollowing.teleportKeepLoaded(entityfollowing.world.getWorldInfo().getSpawnX(),entityfollowing.world.getWorldInfo().getSpawnY(),entityfollowing.world.getWorldInfo().getSpawnZ());
        					entityfollowing.func_233687_w_(true);
    					}
    				}
    			}
    			catch(Exception e) {
    				//also keep your spawn area free...
    				//doesn't work.
    				entityfollowing.teleportKeepLoaded(entityfollowing.world.getWorldInfo().getSpawnX(),entityfollowing.world.getWorldInfo().getSpawnY(),entityfollowing.world.getWorldInfo().getSpawnZ());
					entityfollowing.func_233687_w_(true);
    			}
    			
    			
    			//send fake death message
    			if (!entityfollowing.world.isRemote && entityfollowing.world.getGameRules().getBoolean(GameRules.SHOW_DEATH_MESSAGES) && entityfollowing.getOwner() instanceof ServerPlayerEntity) {
    		         //entityfollowing.getOwner().sendMessage(entityfollowing.getCombatTracker().getDeathMessage(), Util.DUMMY_UUID);
    		         entityfollowing.getOwner().sendMessage(new StringTextComponent(entityfollowing.getCustomName().getString()+" respawned at world spawn point!"), Util.DUMMY_UUID);
    			}
    			
    			
    		}
    	}
    	
    }
    
    
    
    public static ResourceLocation Location(String name) {
    	return new ResourceLocation(MODID,name);
    }
	private void setup(final FMLCommonSetupEvent event)
	{
    	GlobalEntityTypeAttributes.put((EntityType<? extends LivingEntity>) EntityTypeInit.FOLLOWING_CAR, FollowingCar.setCustomAttributes().create());
    }
	
	
	private void clientRegistries(final FMLClientSetupEvent event) {
		FollowingCarRenderRegistry.RegisterEntityRenderers();
		
	}
}
