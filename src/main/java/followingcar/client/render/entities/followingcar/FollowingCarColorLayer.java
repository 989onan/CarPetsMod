package followingcar.client.render.entities.followingcar;





import followingcar.client.render.FollowingCarRenderRegistry;
import followingcar.client.render.models.entities.followingcar.FollowingCarModel;
import followingcar.common.entities.FollowingCar;
import java.util.HashMap;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import followingcar.core.init.CarBlockTypesMaster;


@OnlyIn(Dist.CLIENT)
public class FollowingCarColorLayer extends RenderLayer<FollowingCar, FollowingCarModel<FollowingCar>>{
	
	
	public final HashMap<Integer,EntityModel<FollowingCar>> Models = new HashMap<Integer,EntityModel<FollowingCar>>();
	
	//register our color layers to the render engine so it can access it quickly
	public FollowingCarColorLayer(RenderLayerParent<FollowingCar, FollowingCarModel<FollowingCar>> p_174468_, EntityModelSet p_174469_) {
		super(p_174468_);
		FollowingCarRenderRegistry.ColorTextures.forEach((k,resource) ->{
			Models.put(k,new FollowingCarModel<>(p_174469_.bakeLayer(resource)));
		});
	}
	
	
		
	@Override
	public void render(PoseStack p_117349_, MultiBufferSource p_117350_, int p_117351_, FollowingCar entityIn,
			float p_117353_, float p_117354_, float p_117355_, float p_117356_, float p_117357_, float p_117358_) {
		int h = (entityIn.getColor() & 16711680) >> 16;
		int j = (entityIn.getColor() & '\uff00') >> 8;
		int k = (entityIn.getColor() & 255) >> 0;
		float[] afloat = new float[]{(float)h / 255.0F, (float)j / 255.0F, (float)k / 255.0F};
		
		int actualtype = entityIn.getActualCarType();
		
		
		
		//the code craziness below says this:
		/*
		1. grab the actual car type. Name overrides car type
		2. if both texture layer and obj model types are null, then render default color layer
		3. if obj model doesn't equal null, obj model gets priority so render nothing
		4. if we have a color layer, render it
		4. if somehow we got past everything else, render default layer
		*/
		
		if(Models.get(actualtype) == null && CarBlockTypesMaster.CarObjModels.get(actualtype) == null){
			coloredCutoutModelCopyLayerRender(this.getParentModel(), Models.get(0), FollowingCarRenderRegistry.ColorTextures.get(0).getModel(), p_117349_, p_117350_, p_117351_, entityIn,
	     			p_117353_, p_117354_,p_117355_, p_117356_, p_117357_, p_117358_,afloat[0], afloat[1], afloat[2]);
		}
		else if(CarBlockTypesMaster.CarObjModels.get(actualtype) != null) {
			//we don't run anything here, since the model layer will take care of this!
			//this check is to prevent a block model being drawn if a .obj exists when it has a registered variant name.
		}
		else if (Models.get(actualtype) != null){
			coloredCutoutModelCopyLayerRender(this.getParentModel(), Models.get(actualtype), FollowingCarRenderRegistry.ColorTextures.get(actualtype).getModel(), p_117349_, p_117350_, p_117351_, entityIn,
	     			p_117353_, p_117354_,p_117355_, p_117356_, p_117357_, p_117358_,afloat[0], afloat[1], afloat[2]);
		}
		else {
			
			coloredCutoutModelCopyLayerRender(this.getParentModel(), Models.get(0), FollowingCarRenderRegistry.ColorTextures.get(0).getModel(), p_117349_, p_117350_, p_117351_, entityIn,
	     			p_117353_, p_117354_,p_117355_, p_117356_, p_117357_, p_117358_,afloat[0], afloat[1], afloat[2]);
		}
	}
	
}