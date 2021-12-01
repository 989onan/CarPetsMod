package followingcar.client.render.entities.followingcar;

import followingcar.MainFollowingCar;
import followingcar.client.render.FollowingCarRenderRegistry;
import followingcar.client.render.models.entities.followingcar.FollowingCarModel;
import followingcar.common.entities.FollowingCar;
import followingcar.common.util.CarType;
import followingcar.core.init.CarBlockTypesMaster;
import followingcar.core.init.CarTypeRegistry;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class FollowingCarRender extends MobRenderer<FollowingCar, FollowingCarModel<FollowingCar>>{
	
	public FollowingCarRender(EntityRendererProvider.Context p_173943_) {
		super(p_173943_, new FollowingCarModel<>(p_173943_.bakeLayer(FollowingCarRenderRegistry.ColorTextures.get(0))), 1f);
		this.addLayer(new FollowingCarColorLayer(this,p_173943_.getModelSet()));
		this.addLayer(new FollowingCarModelLayer(this,p_173943_.getModelSet()));
	}
	
	@Override
	public ResourceLocation getTextureLocation(FollowingCar entity) {
		
		//grab this entity type's true car type
		CarType car = CarTypeRegistry.CarTypes.get(entity.getActualCarType());
		if(CarBlockTypesMaster.CarObjModels.get(entity.getActualCarType()).size() > 0) {
			return MainFollowingCar.ExtrasLocation(car.getTextureAtlas());
		}
		
		//if this car type's texture isn't null, return it
		if(car.getMainTextureName() != null) {
			return MainFollowingCar.Location(car.getMainTextureName());
		}
		//else return default
		return MainFollowingCar.Location("textures/entities/livingcar.png");
	}
	
}
