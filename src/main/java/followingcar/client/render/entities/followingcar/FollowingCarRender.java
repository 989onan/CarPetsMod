package followingcar.client.render.entities.followingcar;

import followingcar.MainFollowingCar;
import followingcar.client.render.FollowingCarRenderRegistry;
import followingcar.client.render.models.entities.followingcar.FollowingCarModel;
import followingcar.common.entities.FollowingCar;
import net.minecraft.client.renderer.entity.EntityRendererProvider;


import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class FollowingCarRender extends MobRenderer<FollowingCar, FollowingCarModel<FollowingCar>>{

	public FollowingCarRender(EntityRendererProvider.Context p_173943_) {
		super(p_173943_, new FollowingCarModel<>(p_173943_.bakeLayer(FollowingCarRenderRegistry.carcolorlayer)), 1f);
		this.addLayer(new FollowingCarColorLayer(this,p_173943_.getModelSet()));
	}

	@Override
	public ResourceLocation getTextureLocation(FollowingCar entity) {
		return MainFollowingCar.Location("textures/entities/livingcar.png");
	}
	
}
