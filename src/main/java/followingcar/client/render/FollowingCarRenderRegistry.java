package followingcar.client.render;

import followingcar.client.render.entities.followingcar.FollowingCarRender;


import followingcar.core.init.EntityTypeInit;
import net.minecraftforge.fml.client.registry.RenderingRegistry;



public class FollowingCarRenderRegistry {
	
	public static void RegisterEntityRenderers(){
		RenderingRegistry.registerEntityRenderingHandler(EntityTypeInit.FOLLOWING_CAR, new FollowingCarRender.RenderFactory());
	}
	
	
	
}
