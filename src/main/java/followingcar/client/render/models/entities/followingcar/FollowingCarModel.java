package followingcar.client.render.models.entities.followingcar;

import com.google.common.collect.ImmutableList;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import followingcar.common.entities.FollowingCar;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
/**
 * FollowingCar - 989onan
 * Created using Tabula 8.0.0
 * rewritten randomly by 989onan
 */
@OnlyIn(Dist.CLIENT)
public class FollowingCarModel<T extends FollowingCar> extends EntityModel<T> {
    public ModelPart Chassis;
    public ModelPart Lid;
    public ModelPart WindowBack;
    public ModelPart WindshieldModel;
    public ModelPart doorfl;
    public ModelPart doorrl;
    public ModelPart doorrr;
    public ModelPart doorfr;
    public ModelPart wheelfr;
    public ModelPart wheelfl;
    public ModelPart wheelrl;
    public ModelPart wheelrr;
    
    //stores the boxes for every seat.
    public ModelPart seats;
    
    

    
    
    //this just stores the 
    int[] seatTextureLocation = new int[] {100,82};

    public FollowingCarModel(ModelPart bakeLayer) {
    	this.Chassis = bakeLayer.getChild("Chassis");
        this.Lid = this.Chassis.getChild("Lid");
        this.WindowBack = this.Chassis.getChild("WindowBack");
        this.WindshieldModel = this.Chassis.getChild("WindshieldModel");
        this.doorfl = this.Chassis.getChild("doorfl");
        this.doorrl = this.Chassis.getChild("doorrl");
        this.doorrr = this.Chassis.getChild("doorrr");
        this.doorfr = this.Chassis.getChild("doorfr");
        this.wheelfr = bakeLayer.getChild("wheelfr");
        this.wheelfl = bakeLayer.getChild("wheelfl");
        this.wheelrl = bakeLayer.getChild("wheelrl");
        this.wheelrr = bakeLayer.getChild("wheelrr");
    }
    public static LayerDefinition createBodyMesh(CubeDeformation p_170769_) {
    	
    	MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition modelroot = meshdefinition.getRoot();
        PartDefinition body = modelroot.addOrReplaceChild("Chassis",CubeListBuilder.create().texOffs(0, 0)
    			//.addBox("main",0.0F, 0.0F, 0.0F, 30, 1, 16,p_170769_,0,40) //don't remember what this is, but it breaks the model.
    			.addBox("main",-16.0F, 0.0F, -20.0F, 32, 1, 40,p_170769_,0,0)
    			.addBox("main",-16.0F, -13.0F, -40.0F, 32, 14, 20, p_170769_, 104, 0)
    			.addBox("main",-16.0F, -12.0F, 20.0F, 32, 13, 16, p_170769_, 128, 34)
    			.addBox("main",-16.0F, -12.0F, 20.0F, 32, 13, 16, p_170769_, 128, 34)
    			.addBox("main",15.0F, -13.0F, 20.0F, 1, 1, 16, p_170769_, 188, 0)
    			.addBox("main",-16.0F, -21.0F, -6.0F, 32, 1, 18, p_170769_, 0, 41)
    			.addBox("main",15.0F, -20.0F, 0.0F, 1, 20, 1, p_170769_, 34, 0)
    			.addBox("main",-16.0F, -20F, 0F, 1, 20, 1, p_170769_, 222, 0),
    			
    			PartPose.offset(0.0F, 18.0F, 0.0F));
    	
        body.addOrReplaceChild("doorrr",CubeListBuilder.create().texOffs(99+64, 31+32).addBox("main",-1.0F, -1.0F, 0.0F, 1.0F, 13.0F, 19.0F),PartPose.offset(16.0F, 6.0F  -18.0F, 1.0F));
        body.addOrReplaceChild("WindshieldModel",CubeListBuilder.create().texOffs(0, 45+32).addBox("main",0.0F, 0.0F, 0.0F, 32.0F, 1.0F, 17.0F),PartPose.offset(16.0F, -3.0F  -18.0F, -6.0F));
        body.addOrReplaceChild("doorrl",CubeListBuilder.create().texOffs(141+64, 12+32).addBox("main",-1.0F, -1.0F, 0.0F, 1.0F, 13.0F, 19.0F),PartPose.offset(-15.0F, 6.0F  -18.0F, 1.0F));
        body.addOrReplaceChild("Lid",CubeListBuilder.create().texOffs(0, 28+32).addBox("main",0.0F, 0.0F, 0.0F, 30.0F, 1.0F, 16.0F),PartPose.offset(-15.0F, 5.0F  -18.0F, 20.0F));
        body.addOrReplaceChild("WindowBack",CubeListBuilder.create().texOffs(28+64, 31+32).addBox("main",0.0F, 0.0F, 0.0F, 32.0F, 1.0F, 13.0F),PartPose.offset(-15.9F, -3.0F  -18.0F, 12.0F));
        body.addOrReplaceChild("doorfl",CubeListBuilder.create().texOffs(144+64, 1).addBox("main",-1.0F, -1.0F, 0.0F, 1.0F, 13.0F, 20.0F),PartPose.offset(-15.0F, 6.0F  -18.0F, -20.0F));
        body.addOrReplaceChild("doorfr",CubeListBuilder.create().texOffs(119+64, 43+32).addBox("main",-1.0F, -1.0F, 0.0F, 1.0F, 13.0F, 20.0F),PartPose.offset(16.0F, 6.0F  -18.0F, -20.0F));
        modelroot.addOrReplaceChild("wheelfr",CubeListBuilder.create().texOffs(161+64, 44+32).addBox("main",-0.0F, -6.0F, -6.0F, 1.0F, 12.0F, 12.0F),PartPose.offset(16F, 18F, -30.0F));
        modelroot.addOrReplaceChild("wheelrr",CubeListBuilder.create().texOffs(161+64, 44+32).addBox("main",-0.0F, -6.0F, -6.0F, 1.0F, 12.0F, 12.0F),PartPose.offset(16F, 18F, 28.0F));
        modelroot.addOrReplaceChild("wheelfl",CubeListBuilder.create().texOffs(161+64, 44+32).addBox("main",-0.0F, -6.0F, -6.0F, 1.0F, 12.0F, 12.0F),PartPose.offset(-17.5F, 18.0F, -30.0F));
        modelroot.addOrReplaceChild("wheelrl",CubeListBuilder.create().texOffs(161+64, 44+32).addBox("main",-0.0F, -6.0F, -6.0F, 1.0F, 12.0F, 12.0F),PartPose.offset(-17.5F, 18.0F, 28.0F));
        	//new ObjModel(CubeListBuilder.create()
        //iterate through everything and 
        
        
        
      //This allows the creation of a bunch of seats in an array and then render them all at once.
        //Better than instantiating 20 variables and then listing them in the renderer.
        //This code was written in preparation for making a bus renderer class in less code.
        //This will use an array filling function that automatically generates the seat positions in a future bus renderer code.
        Vec3[] seatpositions = new Vec3[]{
        		new Vec3(6.25F,-1.5F,-10F), new Vec3(-6.25F,-1.5F,-10F),
        		new Vec3(6.25F,-1.5F,10F), new Vec3(-6.25F,-1.5F,10F)
        };
        
        CubeListBuilder builder = CubeListBuilder.create();
        
        int[] offset = new int[] {100,82};
        
        
        //if you want to expand the library of cars, use this function to add seats to the car. it just makes things easier.
        //look at the seats ModelPart for usage.
        //the seats are centered on the point that is inputed
        
        for(Vec3 Position : seatpositions) {
    		builder.texOffs(offset[0], offset[1]).addBox(((float)Position.x-6), ((float)Position.z-6), 12, 1.5F, 12,0);
    		builder.texOffs(offset[0], offset[1]+15).addBox(((float)Position.x-6), ((float)Position.y-15), ((float)Position.z+6), 12, 15, 1.5F);
    	}
        
        body.addOrReplaceChild("seats",builder,PartPose.offset(0F,0F,0F));
        
        //seats = makeSeats(this.seats,this.seatTextureLocation,seatpositions);//create the seats using the seatpositions array and put them under the ModelPart.
        //this.Chassis.addChild(seats);
    	
    	
        return LayerDefinition.create(meshdefinition, 256, 256);
    }
   


    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelPart ModelPart, float x, float y, float z) {
        ModelPart.xRot = x;
        ModelPart.yRot = y;
        ModelPart.zRot = z;
    }
    
    
    
    @Override
    public void prepareMobModel(T p_102614_, float p_102615_, float p_102616_, float p_102617_) {
    	super.prepareMobModel(p_102614_, p_102615_, p_102616_, p_102617_);
    }
    
   
    //setup animation really means animation event...
	@Override
	public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks,
			float netHeadYaw, float headPitch) {
		this.setRotateAngle(WindshieldModel, -0.5061454830783556F, 3.141592653589793F, 0.0F);
		this.setRotateAngle(WindowBack, -0.782431135626991F, 0.0F, 0.0F);
		
		
		this.setRotateAngle(wheelfl, limbSwing/1.1F, 0.0f, 0.0f);
		this.setRotateAngle(wheelfr, limbSwing/1.1F, 0.0f, 0.0f);
		this.setRotateAngle(wheelrr, limbSwing/1.1F, 0.0f, 0.0f);
		this.setRotateAngle(wheelrl, limbSwing/1.1F, 0.0f, 0.0f);
		this.setRotateAngle(Chassis, 0F, 0F, 0F);
		if(entityIn.isOrderedToSit()) {
			//rotate wheels if sitting
			this.setRotateAngle(wheelfl, limbSwing/1.1F, 0.0f, 0.6f);
			this.setRotateAngle(wheelfr, limbSwing/1.1F, 0.0f, -0.6f);
			this.setRotateAngle(wheelrr, limbSwing/1.1F, 0.0f, -0.6f);
			this.setRotateAngle(wheelrl, limbSwing/1.1F, 0.0f, 0.6f);
			//rotate body if sitting
			this.setRotateAngle(Chassis, -.05F, 0F, 0F);
		}
		
		//rotate doors to current door angle on entity
		this.setRotateAngle(doorfr, 0, (float)(entityIn.GetOpenDoorTime()[0]*(Math.PI/180)),0);
		this.setRotateAngle(doorfl, 0, (float)(-entityIn.GetOpenDoorTime()[1]*(Math.PI/180)),0);
		this.setRotateAngle(doorrr, 0, (float)(entityIn.GetOpenDoorTime()[2]*(Math.PI/180)),0);
		this.setRotateAngle(doorrl, 0, (float)(-entityIn.GetOpenDoorTime()[3]*(Math.PI/180)),0);
		
		
		//angle reference sheet, doesn't do anything really
		//float rotationaxis = 0.0F;
		
		//this.setRotateAngle(doorfl, 0.0F, rotationaxis, 0.0F);
		//this.setRotateAngle(doorrl, 0.0F, rotationaxis, 0.0F);
		//this.setRotateAngle(doorfr, 0.0F, rotationaxis, 0.0F);
		//this.setRotateAngle(doorrr, 0.0F, rotationaxis, 0.0F);

		//this.setRotateAngle(Lid, rotationaxis, 0.0F, 0.0F);
		
	}

	@Override
	public void renderToBuffer(PoseStack p_103111_, VertexConsumer p_103112_, int p_103113_, int p_103114_,
			float p_103115_, float p_103116_, float p_103117_, float p_103118_) {
		ImmutableList.of(this.Chassis, this.wheelfl,this.wheelfr,this.wheelrl,this.wheelrr).forEach((ModelPart) -> { 
            ModelPart.render( p_103111_, p_103112_, p_103113_, p_103114_, p_103115_, p_103116_, p_103117_, p_103118_);
        });
	}
}