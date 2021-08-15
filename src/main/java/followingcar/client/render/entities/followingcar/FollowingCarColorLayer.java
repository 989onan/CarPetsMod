package followingcar.client.render.entities.followingcar;





import followingcar.client.render.FollowingCarRenderRegistry;
import followingcar.client.render.models.entities.followingcar.FollowingCarModel;
import followingcar.common.entities.FollowingCar;

import java.util.HashMap;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.ChatFormatting;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class FollowingCarColorLayer extends RenderLayer<FollowingCar, FollowingCarModel<FollowingCar>>{
	
	
	
	private final HashMap<Integer,EntityModel<FollowingCar>> Models = new HashMap<Integer,EntityModel<FollowingCar>>();
	
	public FollowingCarColorLayer(RenderLayerParent<FollowingCar, FollowingCarModel<FollowingCar>> p_174468_, EntityModelSet p_174469_) {
		super(p_174468_);
		FollowingCarRenderRegistry.ModelTextures.forEach((k,resource) ->{
			Models.put(k,new FollowingCarModel<>(p_174469_.bakeLayer(resource)));
		});
	}
	
	@Override
	public void render(PoseStack p_117349_, MultiBufferSource p_117350_, int p_117351_, FollowingCar entityIn,
			float p_117353_, float p_117354_, float p_117355_, float p_117356_, float p_117357_, float p_117358_) {
		float[] afloat = entityIn.getColor().getTextureDiffuseColors();
		
		String name = ChatFormatting.stripFormatting(entityIn.getName().getString());
		
		if(Models.get(entityIn.getCarType()) == null && FollowingCarRender.NameToVariant.get(name) == null) {
			coloredCutoutModelCopyLayerRender(this.getParentModel(), Models.get(0), FollowingCarRender.Colortextures.get(0), p_117349_, p_117350_, p_117351_, entityIn,
	     			p_117353_, p_117354_,p_117355_, p_117356_, p_117357_, p_117358_,afloat[0], afloat[1], afloat[2]);
		}
		else if(FollowingCarRender.NameToVariant.get(name) != null) {
			coloredCutoutModelCopyLayerRender(this.getParentModel(), Models.get(FollowingCarRender.NameToVariant.get(name)), FollowingCarRender.Colortextures.get(FollowingCarRender.NameToVariant.get(name)), p_117349_, p_117350_, p_117351_, entityIn,
	     			p_117353_, p_117354_,p_117355_, p_117356_, p_117357_, p_117358_,afloat[0], afloat[1], afloat[2]);
		}
		else {
			coloredCutoutModelCopyLayerRender(this.getParentModel(), Models.get(entityIn.getCarType()), FollowingCarRender.Colortextures.get(entityIn.getCarType()), p_117349_, p_117350_, p_117351_, entityIn,
	     			p_117353_, p_117354_,p_117355_, p_117356_, p_117357_, p_117358_,afloat[0], afloat[1], afloat[2]);
		}
	}
	
}