package followingcar.client.render.entities.followingcar;



import followingcar.MainFollowingCar;
import followingcar.client.render.FollowingCarRenderRegistry;
import followingcar.client.render.models.entities.followingcar.FollowingCarModel;
import followingcar.common.entities.FollowingCar;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;

public class FollowingCarColorLayer extends RenderLayer<FollowingCar, FollowingCarModel<FollowingCar>>{
	private static final ResourceLocation CAR_COLOR = new ResourceLocation(MainFollowingCar.MODID,"textures/entities/livingcarcolor.png");
	private final FollowingCarModel<FollowingCar> carmodel;
	
	public FollowingCarColorLayer(RenderLayerParent<FollowingCar, FollowingCarModel<FollowingCar>> p_174468_, EntityModelSet p_174469_) {
		super(p_174468_);
	    this.carmodel = new FollowingCarModel<>(p_174469_.bakeLayer(FollowingCarRenderRegistry.carcolorlayer));
	}
	
	@Override
	public void render(PoseStack p_117349_, MultiBufferSource p_117350_, int p_117351_, FollowingCar p_117352_,
			float p_117353_, float p_117354_, float p_117355_, float p_117356_, float p_117357_, float p_117358_) {
		// TODO Auto-generated method stub
		  //if (entitylivingbaseIn.isTamed()) {
				float[] afloat = p_117352_.getColor().getTextureDiffuseColors();
			         coloredCutoutModelCopyLayerRender(this.getParentModel(), this.carmodel, CAR_COLOR, p_117349_, p_117350_, p_117351_, p_117352_,
			     			p_117353_, p_117354_,p_117355_, p_117356_, p_117357_, p_117358_,afloat[0], afloat[1], afloat[2]);
			      //}
	}
	
}