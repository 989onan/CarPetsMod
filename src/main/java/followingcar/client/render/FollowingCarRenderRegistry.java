package followingcar.client.render;

import java.util.HashMap;

import followingcar.MainFollowingCar;
import followingcar.client.render.entities.followingcar.FollowingCarRender;
import followingcar.client.render.models.entities.followingcar.FollowingCarModel;
import followingcar.core.init.CarTypeRegistry;
import followingcar.core.init.EntityTypeInit;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent.RegisterLayerDefinitions;
import net.minecraftforge.client.event.EntityRenderersEvent.RegisterRenderers;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;



@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD,value=Dist.CLIENT)
public class FollowingCarRenderRegistry {
	
	
	public static final SoundEvent CAR_PURR = new SoundEvent(MainFollowingCar.Location("car_purr")).setRegistryName(MainFollowingCar.Location("car_purr"));
	
	public static final HashMap<Integer, ModelLayerLocation> ColorTextures = new HashMap<Integer, ModelLayerLocation>();

	public static final SoundEvent CAR_REV = new SoundEvent(MainFollowingCar.Location("car_rev")).setRegistryName(MainFollowingCar.Location("car_rev"));

	public static final SoundEvent CAR_HURT = new SoundEvent(MainFollowingCar.Location("car_hurt")).setRegistryName(MainFollowingCar.Location("car_hurt"));

	public static final HashMap<Integer, ModelLayerLocation> MainTextures = new HashMap<Integer, ModelLayerLocation>();
	
	
	
	@SubscribeEvent
	public static void renderregister(final RegisterLayerDefinitions event) {
		
		CarTypeRegistry.CarTypes.forEach((type,cartype) ->{
			if(cartype.getColorTextureName() != null) {
				ColorTextures.put(type, new ModelLayerLocation(MainFollowingCar.Location(cartype.getColorTextureName()),type+""));
			}
		});
		
		CarTypeRegistry.CarTypes.forEach((type,cartype) ->{
			if(cartype.getMainTextureName() != null) {
				MainTextures.put(type, new ModelLayerLocation(MainFollowingCar.Location(cartype.getMainTextureName()),type+""));
			}
		});
		
		ColorTextures.forEach((k,texture) -> {
			event.registerLayerDefinition(texture, ()->FollowingCarModel.createBodyMesh(CubeDeformation.NONE) );
		});
	}
	
	@SubscribeEvent
	public static void RegisterModelRenderers(final RegisterRenderers event){
		
		event.registerEntityRenderer(EntityTypeInit.FOLLOWING_CAR, FollowingCarRender::new);
		
	}
	
	
	@SubscribeEvent
	public static void RegisterSound(final RegistryEvent.Register<SoundEvent> event) {
		event.getRegistry().registerAll(
				FollowingCarRenderRegistry.CAR_REV,
				FollowingCarRenderRegistry.CAR_PURR,
				FollowingCarRenderRegistry.CAR_HURT
				);
		
		
	}
	
}
