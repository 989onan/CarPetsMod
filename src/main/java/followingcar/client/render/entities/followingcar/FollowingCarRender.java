package followingcar.client.render.entities.followingcar;

import followingcar.MainFollowingCar;

import followingcar.client.render.models.entities.followingcar.FollowingCarModel;
import followingcar.common.entities.FollowingCar;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
public class FollowingCarRender extends MobRenderer<FollowingCar,FollowingCarModel>{

	protected FollowingCarRender(EntityRendererManager rendererManager) {
		super(rendererManager, new FollowingCarModel(), 1f);
		this.addLayer(new FollowingCarColorLayer(this));
	}

	@Override
	public ResourceLocation getEntityTexture(FollowingCar entity) {
		return MainFollowingCar.Location("textures/entities/livingcar.png");
	}
	
	public static class RenderFactory implements IRenderFactory<FollowingCar>{
		
		@Override
		public EntityRenderer<? super FollowingCar> createRenderFor(EntityRendererManager manager) {
			// TODO Auto-generated method stub
			return new FollowingCarRender(manager);
		}
		
	}
}
