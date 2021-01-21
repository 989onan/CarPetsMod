package followingcar.client.render.models.entities.followingcar;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import followingcar.common.entities.FollowingCar;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
/**
 * FollowingCar - 989onan
 * Created using Tabula 8.0.0
 */
public class FollowingCarModel extends EntityModel<FollowingCar> {
    public ModelRenderer Chassis;
    public ModelRenderer Lid;
    public ModelRenderer WindowBack;
    public ModelRenderer WindshieldModel;
    public ModelRenderer doorfl;
    public ModelRenderer doorrl;
    public ModelRenderer doorrr;
    public ModelRenderer doorfr;
    public ModelRenderer wheelfr;
    public ModelRenderer wheelfl;
    public ModelRenderer wheelrl;
    public ModelRenderer wheelrr;

    public FollowingCarModel() {
        this.textureWidth = 256;
        this.textureHeight = 256;
        this.WindshieldModel = new ModelRenderer(this);
        this.WindshieldModel.setRotationPoint(16.0F, -3.0F, -6.0F);
        this.WindshieldModel.setTextureOffset(0, 45+32).addBox(0.0F, 0.0F, 0.0F, 32.0F, 1.0F, 17.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(WindshieldModel, -0.5061454830783556F, 3.141592653589793F, 0.0F);
        this.doorrr = new ModelRenderer(this);
        this.doorrr.setRotationPoint(16.0F, 6.0F, 1.0F);
        this.doorrr.setTextureOffset(99+64, 31+32).addBox(-1.0F, -1.0F, 0.0F, 1.0F, 13.0F, 19.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(doorrr, 0.0F, 0.5335471470083444F, 0.0F);
        this.doorrl = new ModelRenderer(this);
        this.doorrl.setRotationPoint(-15.0F, 6.0F, 1.0F);
        this.doorrl.setTextureOffset(141+64, 12+32).addBox(-1.0F, -1.0F, 0.0F, 1.0F, 13.0F, 19.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(doorrl, 0.0F, -0.6393141236476505F, 0.0F);
        this.Lid = new ModelRenderer(this);
        this.Lid.setRotationPoint(-15.0F, 5.0F, 20.0F);
        this.Lid.setTextureOffset(0, 28+32).addBox(0.0F, 0.0F, 0.0F, 30.0F, 1.0F, 16.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(Lid, 0.23457224414434488F, 0.0F, 0.0F);
        this.WindowBack = new ModelRenderer(this);
        this.WindowBack.setRotationPoint(-15.9F, -3.0F, 12.0F);
        this.WindowBack.setTextureOffset(28+64, 31+32).addBox(0.0F, 0.0F, 0.0F, 32.0F, 1.0F, 13.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(WindowBack, -0.782431135626991F, 0.0F, 0.0F);
        this.wheelfl = new ModelRenderer(this);
        this.wheelfl.setRotationPoint(-17.5F, 18.0F, -30.0F);
        this.wheelfl.setTextureOffset(161+64, 44+32).addBox(-0.0F, -6.0F, -6.0F, 1.0F, 12.0F, 12.0F, 0.0F, 0.0F, 0.0F);
        this.wheelrl = new ModelRenderer(this);
        this.wheelrl.setRotationPoint(-17.5F, 18.0F, 28.0F);
        this.wheelrl.setTextureOffset(161+64, 44+32).addBox(-0.0F, -6.0F, -6.0F, 1.0F, 12.0F, 12.0F, 0.0F, 0.0F, 0.0F);
        this.Chassis = new ModelRenderer(this);
        this.Chassis.setRotationPoint(0.0F, 18.0F, 0.0F);
        this.Chassis.addBox(-16.0F, 0.0F, -20.0F, 32.0F, 1.0F, 40.0F, 0.0F, 0.0F, 0.0F);
        this.Chassis.setTextureOffset(104, 0).addBox(-16.0F, -13.0F, -40.0F, 32.0F, 14.0F, 20.0F, 0.0F, 0.0F, 0.0F);
        this.Chassis.setTextureOffset(128, 34).addBox(-16.0F, -12.0F, 20.0F, 32.0F, 13.0F, 16.0F, 0.0F, 0.0F, 0.0F);
        this.Chassis.addBox(-16.0F, -13.0F, 20.0F, 1.0F, 1.0F, 16.0F, 0.0F, 0.0F, 0.0F);
        this.Chassis.setTextureOffset(188, 0).addBox(15.0F, -13.0F, 20.0F, 1.0F, 1.0F, 16.0F, 0.0F, 0.0F, 0.0F);
        this.Chassis.setTextureOffset(0, 41).addBox(-16.0F, -21.0F, -6.0F, 32.0F, 1.0F, 18.0F, 0.0F, 0.0F, 0.0F);
        this.Chassis.setTextureOffset(34, 0).addBox(15.0F, -20.0F, 0.0F, 1.0F, 20.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.Chassis.setTextureOffset(222, 0).addBox(-16.0F, -20.0F, 0.0F, 1.0F, 20.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.doorfl = new ModelRenderer(this);
        this.doorfl.setRotationPoint(-15.0F, 6.0F, -20.0F);
        this.doorfl.setTextureOffset(144+64, 1).addBox(-1.0F, -1.0F, 0.0F, 1.0F, 13.0F, 20.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(doorfl, 0.0F, -0.3656464916086175F, 0.0F);
        this.doorfr = new ModelRenderer(this);
        this.doorfr.setRotationPoint(16.0F, 6.0F, -20.0F);
        this.doorfr.setTextureOffset(119+64, 43+32).addBox(-1.0F, -1.0F, 0.0F, 1.0F, 13.0F, 20.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(doorfr, 0.0F, 0.5728170551782168F, 0.0F);
        this.wheelfr = new ModelRenderer(this);
        this.wheelfr.setRotationPoint(16F, 16.1F, -30.0F);
        this.wheelfr.setTextureOffset(161+64, 44+32).addBox(-0.0F, -6.0F, -6.0F, 1.0F, 12.0F, 12.0F, 0.0F, 0.0F, 0.0F);
        this.wheelrr = new ModelRenderer(this);
        this.wheelrr.setRotationPoint(16F, 16.1F, 28.0F);
        this.wheelrr.setTextureOffset(161+64, 44+32).addBox(-0.0F, -6.0F, -6.0F, 1.0F, 12.0F, 12.0F, 0.0F, 0.0F, 0.0F);
    }

    @Override
    public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) { 
        ImmutableList.of(this.WindshieldModel, this.doorrr, this.doorrl, this.Lid, this.WindowBack, this.wheelfl, this.wheelrl, this.Chassis, this.doorfl, this.doorfr, this.wheelfr, this.wheelrr).forEach((modelRenderer) -> { 
            modelRenderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        });
    }


    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }

	@Override
	public void setRotationAngles(FollowingCar entityIn, float limbSwing, float limbSwingAmount, float ageInTicks,
			float netHeadYaw, float headPitch) {
		this.setRotateAngle(wheelfl, limbSwing/1.1F, 0.0f, 0.0f);
		this.setRotateAngle(wheelfr, limbSwing/1.1F, 0.0f, 0.0f);
		this.setRotateAngle(wheelrr, limbSwing/1.1F, 0.0f, 0.0f);
		this.setRotateAngle(wheelrl, limbSwing/1.1F, 0.0f, 0.0f);
		if(entityIn.isSitting()) {
			//rotate wheels
			this.setRotateAngle(wheelfl, limbSwing/1.1F, 0.0f, 0.6f);
			this.setRotateAngle(wheelfr, limbSwing/1.1F, 0.0f, -0.6f);
			this.setRotateAngle(wheelrr, limbSwing/1.1F, 0.0f, -0.6f);
			this.setRotateAngle(wheelrl, limbSwing/1.1F, 0.0f, 0.6f);
			
		}
		
		//angle reference sheet, doesn't do anything really
		float rotationaxis = 0.0F;
		
		this.setRotateAngle(doorfl, 0.0F, rotationaxis, 0.0F);
		this.setRotateAngle(doorrl, 0.0F, rotationaxis, 0.0F);
		this.setRotateAngle(doorfr, 0.0F, rotationaxis, 0.0F);
		this.setRotateAngle(doorrr, 0.0F, rotationaxis, 0.0F);
		
		this.setRotateAngle(Lid, rotationaxis, 0.0F, 0.0F);
	}
}