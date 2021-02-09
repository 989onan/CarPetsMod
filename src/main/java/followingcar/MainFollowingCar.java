package followingcar;


import org.apache.logging.log4j.LogManager;




import org.apache.logging.log4j.Logger;

import followingcar.client.render.FollowingCarRenderRegistry;
import followingcar.common.entities.FollowingCar;
import followingcar.config.FollowingCarConfig;
import followingcar.core.init.EntityTypeInit;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;

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
