package followingcar;


import org.apache.logging.log4j.LogManager;



import org.apache.logging.log4j.Logger;

import followingcar.client.render.FollowingCarRenderRegistry;
import followingcar.common.entities.FollowingCar;
import followingcar.core.init.EntityTypeInit;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("followingcarmod")
public class MainFollowingCar {
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MODID = "followingcarmod";
    
    
    public MainFollowingCar() {
    	FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
    	FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientRegistries);
    	//EntityTypeInit.ENTITIES.register(bus); <-- Doesn't work
        MinecraftForge.EVENT_BUS.register(this);
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
