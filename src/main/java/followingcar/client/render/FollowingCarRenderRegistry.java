package followingcar.client.render;

import java.util.HashMap;


import followingcar.MainFollowingCar;

import followingcar.client.render.entities.followingcar.FollowingCarRender;
import followingcar.client.render.models.entities.followingcar.FollowingCarModel;
import followingcar.core.init.EntityTypeInit;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent.RegisterLayerDefinitions;
import net.minecraftforge.client.event.EntityRenderersEvent.RegisterRenderers;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;



@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD,value=Dist.CLIENT)
public class FollowingCarRenderRegistry {
	
	
	//this is where we add the model layer and define where the 
	public static final HashMap<Integer,ModelLayerLocation> ModelTextures = new HashMap<Integer,ModelLayerLocation>(){/**
		 * 
		 */
		private static final long serialVersionUID = 2329830138012838L;

	{
		put(0,new ModelLayerLocation(FollowingCarRender.Colortextures.get(0),"DefaultModel"));
		put(1,new ModelLayerLocation(FollowingCarRender.Colortextures.get(1),"ae86"));
	}};
	
	
	@SubscribeEvent
	public static void renderregister(final RegisterLayerDefinitions event) {
		
		ModelTextures.forEach((k,texture) -> {
			event.registerLayerDefinition(texture, ()->FollowingCarModel.createBodyMesh(CubeDeformation.NONE) );
		});
	}
	
	public FollowingCarRenderRegistry() {
		MainFollowingCar.LOGGER.info("Hello?");
		
	}
	
	
	@SubscribeEvent
	public static void RegisterEntityRenderers(final RegisterRenderers event){
		
		event.registerEntityRenderer(EntityTypeInit.FOLLOWING_CAR, FollowingCarRender::new);
		
	}
	

	
}
