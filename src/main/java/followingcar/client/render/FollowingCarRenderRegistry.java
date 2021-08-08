package followingcar.client.render;

import followingcar.MainFollowingCar;

import followingcar.client.render.entities.followingcar.FollowingCarRender;


import followingcar.core.init.EntityTypeInit;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.EntityRenderersEvent.RegisterRenderers;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
public class FollowingCarRenderRegistry {
	
	public static final ModelLayerLocation carcolorlayer = new ModelLayerLocation(new ResourceLocation(MainFollowingCar.MODID, "textures/entities/livingcarcolor.png"),"main");
	
	
	@SubscribeEvent
	public static void RegisterEntityRenderers(final RegisterRenderers event){

		event.registerEntityRenderer(EntityTypeInit.FOLLOWING_CAR, FollowingCarRender::new);
		
	}
	
	
	
}
