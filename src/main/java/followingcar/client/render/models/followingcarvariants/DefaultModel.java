package followingcar.client.render.models.followingcarvariants;


import java.util.HashMap;

import followingcar.common.entities.FollowingCar;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.world.phys.Vec3;

public class DefaultModel extends GeneralModelObj{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8194200369631517397L;

	public HashMap<String,ModelPart> Children = new HashMap<String,ModelPart>();
	
	
	
	/**
	 * 
	 */
	
	
	public void setRotateAngle(ModelPart ModelPart, float x, float y, float z) {
        ModelPart.xRot = x;
        ModelPart.yRot = y;
        ModelPart.zRot = z;
    }
	
	public DefaultModel(ModelPart bakeLayer) {
		this.put("Chassis", bakeLayer.getChild("Chassis1"));
		this.Children.put("Lid", this.get("Chassis").getChild("Lid"));
    	this.Children.put("WindowBack",this.get("Chassis").getChild("WindowBack"));
    	this.Children.put("WindshieldModel", this.get("Chassis").getChild("WindshieldModel"));
    	
    	this.Children.put("door1", this.get("Chassis").getChild("doorfr"));
    	this.Children.put("door2", this.get("Chassis").getChild("doorfl"));
    	this.Children.put("door3", this.get("Chassis").getChild("doorrr"));
    	this.Children.put("door4", this.get("Chassis").getChild("doorrl"));
    	
    	this.put("wheel-r1", bakeLayer.getChild("wheelfr1"));
    	this.put("wheel-l1", bakeLayer.getChild("wheelfl1"));
		this.put("wheel-l2", bakeLayer.getChild("wheelrl1"));
		this.put("wheel-r2", bakeLayer.getChild("wheelrr1"));
	}
	
	
	public static void addModelLayer(PartDefinition modelroot,CubeDeformation p_170769_) {
    	
    	//DEFAULT MODEL
    	
    	PartDefinition body = modelroot.addOrReplaceChild("Chassis1",CubeListBuilder.create().texOffs(0, 0)
    			//.addBox("DefaultModel",0.0F, 0.0F, 0.0F, 30, 1, 16,p_170769_,0,40) //don't remember what this is, but it breaks the model.
    			.addBox("DefaultModel",-16.0F, 0.0F, -20.0F, 32, 1, 40,p_170769_,0,0)
    			.addBox("DefaultModel",-16.0F, -13.0F, -40.0F, 32, 14, 20, p_170769_, 104, 0)
    			.addBox("DefaultModel",-16.0F, -12.0F, 20.0F, 32, 13, 16, p_170769_, 128, 34)
    			.addBox("DefaultModel",-16.0F, -12.0F, 20.0F, 32, 13, 16, p_170769_, 128, 34)
    			.addBox("DefaultModel",15.0F, -13.0F, 20.0F, 1, 1, 16, p_170769_, 188, 0)
    			.addBox("DefaultModel",-16.0F, -21.0F, -6.0F, 32, 1, 18, p_170769_, 0, 41)
    			.addBox("DefaultModel",15.0F, -20.0F, 0.0F, 1, 20, 1, p_170769_, 34, 0)
    			.addBox("DefaultModel",-16.0F, -20F, 0F, 1, 20, 1, p_170769_, 222, 0),
    			
    			PartPose.offset(0.0F, 18.0F, 0.0F));
    	
        body.addOrReplaceChild("doorrr",CubeListBuilder.create().texOffs(99+64, 31+32).addBox("DefaultModel",-1.0F, -1.0F, 0.0F, 1.0F, 13.0F, 19.0F),PartPose.offset(16.0F, 6.0F  -18.0F, 1.0F));
        body.addOrReplaceChild("WindshieldModel",CubeListBuilder.create().texOffs(0, 45+32).addBox("DefaultModel",0.0F, 0.0F, 0.0F, 32.0F, 1.0F, 17.0F),PartPose.offset(16.0F, -3.0F  -18.0F, -6.0F));
        body.addOrReplaceChild("doorrl",CubeListBuilder.create().texOffs(141+64, 12+32).addBox("DefaultModel",-1.0F, -1.0F, 0.0F, 1.0F, 13.0F, 19.0F),PartPose.offset(-15.0F, 6.0F  -18.0F, 1.0F));
        body.addOrReplaceChild("Lid",CubeListBuilder.create().texOffs(0, 28+32).addBox("DefaultModel",0.0F, 0.0F, 0.0F, 30.0F, 1.0F, 16.0F),PartPose.offset(-15.0F, 5.0F  -18.0F, 20.0F));
        body.addOrReplaceChild("WindowBack",CubeListBuilder.create().texOffs(28+64, 31+32).addBox("DefaultModel",0.0F, 0.0F, 0.0F, 32.0F, 1.0F, 13.0F),PartPose.offset(-15.9F, -3.0F  -18.0F, 12.0F));
        body.addOrReplaceChild("doorfl",CubeListBuilder.create().texOffs(144+64, 1).addBox("DefaultModel",-1.0F, -1.0F, 0.0F, 1.0F, 13.0F, 20.0F),PartPose.offset(-15.0F, 6.0F  -18.0F, -20.0F));
        body.addOrReplaceChild("doorfr",CubeListBuilder.create().texOffs(119+64, 43+32).addBox("DefaultModel",-1.0F, -1.0F, 0.0F, 1.0F, 13.0F, 20.0F),PartPose.offset(16.0F, 6.0F  -18.0F, -20.0F));
        modelroot.addOrReplaceChild("wheelfr1",CubeListBuilder.create().texOffs(161+64, 44+32).addBox("DefaultModel",-0.0F, -6.0F, -6.0F, 1.0F, 12.0F, 12.0F),PartPose.offset(16F, 18F, -30.0F));
        modelroot.addOrReplaceChild("wheelrr1",CubeListBuilder.create().texOffs(161+64, 44+32).addBox("DefaultModel",-0.0F, -6.0F, -6.0F, 1.0F, 12.0F, 12.0F),PartPose.offset(16F, 18F, 28.0F));
        modelroot.addOrReplaceChild("wheelfl1",CubeListBuilder.create().texOffs(161+64, 44+32).addBox("DefaultModel",-0.0F, -6.0F, -6.0F, 1.0F, 12.0F, 12.0F),PartPose.offset(-17.5F, 18.0F, -30.0F));
        modelroot.addOrReplaceChild("wheelrl1",CubeListBuilder.create().texOffs(161+64, 44+32).addBox("DefaultModel",-0.0F, -6.0F, -6.0F, 1.0F, 12.0F, 12.0F),PartPose.offset(-17.5F, 18.0F, 28.0F));
        	//new ObjModel(CubeListBuilder.create()
        //iterate through everything and 
        
        
        
      //This allows the creation of a bunch of seats in an array and then render them all at once.
        //Better than instantiating 20 variables and then listing them in the renderer.
        //This code was written in preparation for making a bus renderer class in less code.
        //This will use an array filling function that automatically generates the seat positions in a future bus renderer code.
        Vec3[] seatpositions1 = new Vec3[]{
        		new Vec3(6.25F,-1.5F,-10F), new Vec3(-6.25F,-1.5F,-10F),
        		new Vec3(6.25F,-1.5F,10F), new Vec3(-6.25F,-1.5F,10F)
        };
        
        CubeListBuilder builder = CubeListBuilder.create();
        
        int[] offset = new int[] {100,82};
        
        
        //if you want to expand the library of cars, use this function to add seats to the car. it just makes things easier.
        //look at the seats ModelPart for usage.
        //the seats are centered on the point that is inputed
        
        for(Vec3 Position : seatpositions1) {
    		builder.texOffs(offset[0], offset[1]+15).addBox(((float)Position.x-6), ((float)Position.y-15), ((float)Position.z+6), 12, 15, 1.5F);
    	}
        
        body.addOrReplaceChild("seats",builder,PartPose.offset(0F,0F,0F));
        
        //seats = makeSeats(this.seats,this.seatTextureLocation,seatpositions);//create the seats using the seatpositions array and put them under the ModelPart.
        //this.Chassis.addChild(seats);
	}
	
	public void setDefaultRotations() {
		this.setRotateAngle(this.Children.get("WindshieldModel"), -0.5061454830783556F, 3.141592653589793F, 0.0F);
		this.setRotateAngle(this.Children.get("WindowBack"), -0.782431135626991F, 0.0F, 0.0F);
	}
	
	public void doorRotations(FollowingCar entityIn, float limbswing) {
		this.setRotateAngle(this.Children.get("door1"),0.0F, (float)(entityIn.GetOpenDoorTime().get(0)*(Math.PI/180)), 0.0f);
		this.setRotateAngle(this.Children.get("door2"),0.0F, (float)-(entityIn.GetOpenDoorTime().get(1)*(Math.PI/180)), 0.0f);
		
		this.setRotateAngle(this.Children.get("door3"),0.0F, (float)(entityIn.GetOpenDoorTime().get(2)*(Math.PI/180)), 0.0f);
		this.setRotateAngle(this.Children.get("door4"),0.0F, (float)-(entityIn.GetOpenDoorTime().get(3)*(Math.PI/180)), 0.0f);
	}
	
}
