package followingcar.client.render.entities.followingcar;

import java.util.HashMap;


import followingcar.MainFollowingCar;


import followingcar.client.render.FollowingCarRenderRegistry;
import followingcar.client.render.models.entities.followingcar.FollowingCarModel;
import followingcar.common.entities.FollowingCar;
import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.entity.EntityRendererProvider;


import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class FollowingCarRender extends MobRenderer<FollowingCar, FollowingCarModel<FollowingCar>>{
	
	
	
	//this is where a new model color texture is added
	public static final HashMap<Integer,ResourceLocation> Colortextures = new HashMap<Integer,ResourceLocation>(){
		private static final long serialVersionUID = 232983013809903213L;

	{
		put(0,new ResourceLocation(MainFollowingCar.MODID,"textures/entities/livingcarcolor.png"));
		put(1,new ResourceLocation(MainFollowingCar.MODID,"textures/entities/ae86color.png"));
	}};
	
	
	//this is where a new model texture is added
	public static final HashMap<Integer,ResourceLocation> maintextures = new HashMap<Integer,ResourceLocation>(){
		private static final long serialVersionUID = 2329830138012838L;

	{
		put(0,new ResourceLocation(MainFollowingCar.MODID,"textures/entities/livingcar.png"));
		put(1,new ResourceLocation(MainFollowingCar.MODID,"textures/entities/ae86.png"));
	}};
	
	
	public static final HashMap<String,Integer> NameToVariant = new HashMap<String,Integer>(){
		private static final long serialVersionUID = -119518255187550602L;
		{
			put("Kodachi",1);
			//put("Amari",2); //figure out which model will have Amari's model, later...
			put("Fumei",2);
		}
	};
	
	
	
	public FollowingCarRender(EntityRendererProvider.Context p_173943_) {
		super(p_173943_, new FollowingCarModel<>(p_173943_.bakeLayer(FollowingCarRenderRegistry.ModelTextures.get(0))), 1f);
		//this.addLayer(new FollowingCarColorLayer(this,p_173943_.getModelSet()));
		this.addLayer(new FollowingCarModelLayer(this,p_173943_.getModelSet()));
	}
	
	@Override
	public ResourceLocation getTextureLocation(FollowingCar entity) {
		String name = ChatFormatting.stripFormatting(entity.getName().getString());
		
		if(maintextures.get(entity.getCarType()) == null && NameToVariant.get(name) == null) {
			return MainFollowingCar.Location("textures/entities/livingcar.png");
		}
		else if (NameToVariant.get(name) != null){
			if(maintextures.get(NameToVariant.get(name)) != null) {
				return maintextures.get(NameToVariant.get(name));
			}
		}
		if(maintextures.get(entity.getCarType()) != null) {
			return maintextures.get(entity.getCarType());
		}
		return MainFollowingCar.Location("textures/entities/livingcar.png");
	}
	
}
