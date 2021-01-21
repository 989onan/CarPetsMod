package followingcar.client.render.entities.followingcar;

import com.mojang.blaze3d.matrix.MatrixStack;

import followingcar.MainFollowingCar;
import followingcar.client.render.models.entities.followingcar.FollowingCarModel;
import followingcar.common.entities.FollowingCar;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;

public class FollowingCarColorLayer extends LayerRenderer<FollowingCar, FollowingCarModel>{
	
	private static final ResourceLocation CAR_COLOR = MainFollowingCar.Location("textures/entities/livingcarcolor.png");
	public FollowingCarColorLayer(IEntityRenderer<FollowingCar, FollowingCarModel> entityRendererIn) {
		super(entityRendererIn);
		
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, FollowingCar entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
	      //if (entitylivingbaseIn.isTamed()) {
	         float[] afloat = entitylivingbaseIn.getColor().getColorComponentValues();
	         renderCopyCutoutModel(this.getEntityModel(), new FollowingCarModel(), CAR_COLOR, matrixStackIn, bufferIn, packedLightIn, entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, partialTicks, afloat[0], afloat[1], afloat[2]);
	      //}
	   }
	
}
