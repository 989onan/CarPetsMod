package followingcar.client.render.models.followingcarvariants;


import java.util.HashMap;

import followingcar.common.entities.FollowingCar;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
@OnlyIn(Dist.CLIENT)
public class AE86Model extends GeneralPartModel{

	/**
	 * 
	 */
	
	
	private static final long serialVersionUID = -8564654248046158383L;
	public HashMap<String,ModelPart> Children = new HashMap<String,ModelPart>();
	
	
	
	/**
	 * 
	 */

	public AE86Model(ModelPart bakeLayer) {
		this.put("Chassis", bakeLayer.getChild("Chassis2"));
		
		this.Children.put("WindowBack", this.get("Chassis").getChild("WindowBack"));
		this.Children.put("WindshieldModel", this.get("Chassis").getChild("WindshieldModel"));
		this.Children.put("door1", this.get("Chassis").getChild("doorfr"));
		this.Children.put("door2", this.get("Chassis").getChild("doorfl"));
		
		
		this.put("wheel-r1", bakeLayer.getChild("wheelfr2"));
		this.put("wheel-l1" ,bakeLayer.getChild("wheelfl2"));
		this.put("wheel-l2", bakeLayer.getChild("wheelrl2"));
		this.put("wheel-r2" ,bakeLayer.getChild("wheelrr2"));
		
		
		this.Children.put("Seats",this.get("Chassis").getChild("seats"));
		this.Children.put("roof", this.get("Chassis").getChild("roof"));
		this.Children.put("HeadlightL", this.get("Chassis").getChild("HeadlightL"));
		this.Children.put("HeadlightR", this.get("Chassis").getChild("HeadlightR"));
	}
	
	
	public static void addModelLayer(PartDefinition modelroot,CubeDeformation p_170769_) {
		
		//AE86 MODEL
		
		PartDefinition body = modelroot.addOrReplaceChild("Chassis2",CubeListBuilder.create().texOffs(0, 0)
				.addBox("ae86",-13.9F, -0.7F, -17.5F, 27, 1, 23,p_170769_,104, 0)
				.addBox("ae86",12.9F, -19.0F, 5.0F, 1, 8, 1,p_170769_,104, 0)
				.addBox("ae86",-14.0F, -11.7F, -31.0F, 28, 12, 14,p_170769_,0, 24)
				.addBox("ae86",-14.0F, -11.7F, 5.0F, 28, 12, 13,p_170769_,84, 24)
				.addBox("ae86",-13.9F, -19.0F, 5.0F, 1, 8, 1, p_170769_,4, 0)
				,PartPose.offset(0.0F, 18.0F, 4.0F));
		body.addOrReplaceChild("WindshieldModel",CubeListBuilder.create().texOffs(64+8, 32+17).addBox("ae86",-14.0F, -1.0F, 0.0F, 28.0F, 1.0F, 12.0F),PartPose.offset(0.0F, -20.0F, -10.0F));
		body.addOrReplaceChild("WindowBack",CubeListBuilder.create().texOffs(64+102, 24).addBox("ae86",-14.0F, 0.0F, 0.0F, 28.0F, 1.0F, 15.0F),PartPose.offset(0.0F, -20.0F, 5.0F));
		
		body.addOrReplaceChild("roof",CubeListBuilder.create().texOffs(0, 32+18).addBox("ae86",-14.0F, 0.0F, 0.0F, 28.0F, 1.0F, 15.0F),PartPose.offset(0.0F, -20.0F, -10.0F));
		
		
		modelroot.addOrReplaceChild("wheelfl2",CubeListBuilder.create().texOffs(231, 215).addBox("ae86",-0.0F, -6.0F, -6.0F, 1.0F, 12.0F, 12.0F),PartPose.offset(-15.1F, 18.0F, -20.0F));
		modelroot.addOrReplaceChild("wheelfr2",CubeListBuilder.create().texOffs(231, 215).addBox("ae86",-0.0F, -6.0F, -6.0F, 1.0F, 12.0F, 12.0F),PartPose.offset(13.1F, 18.0F, -20.0F));
		
		modelroot.addOrReplaceChild("wheelrl2",CubeListBuilder.create().texOffs(231, 215).addBox("ae86",-0.0F, -6.0F, -6.0F, 1.0F, 12.0F, 12.0F),PartPose.offset(-15.1F, 18.0F, 14.0F));
		modelroot.addOrReplaceChild("wheelrr2",CubeListBuilder.create().texOffs(231, 215).addBox("ae86",-0.0F, -6.0F, -6.0F, 1.0F, 12.0F, 12.0F),PartPose.offset(13.1F, 18.0F, 14.0F));
		
		body.addOrReplaceChild("doorfr",CubeListBuilder.create().texOffs(64+127, 32+8).addBox("ae86",-1.0F, 0.0F, 0.0F, 1.0F, 11.0F, 23.0F),PartPose.offset(13.9F, -11.6F, -17.5F));
		body.addOrReplaceChild("doorfl",CubeListBuilder.create().texOffs(64+79, 32+8).addBox("ae86",0.0F, 0.0F, 0.0F, 1.0F, 11.2F, 22.5F),PartPose.offset(-14.0F, -11.7F, -17.5F));
	   
		
	    body.addOrReplaceChild("HeadlightL", CubeListBuilder.create().texOffs(0, 66).addBox("ae86",-3.5F, 0.0F, -8.5F, 5.9F, 5.0F, 9.0F),PartPose.offset(8.0F, -12.0F, -20.0F));
	    body.addOrReplaceChild("HeadlightR", CubeListBuilder.create().texOffs(0, 66).addBox("ae86",-3.5F, 0.0F, -8.5F, 5.9F, 5.0F, 9.0F),PartPose.offset(-8.0F, -12.0F, -20.0F));
		//This allows the creation of a bunch of seats in an array and then render them all at once.
	    //Better than instantiating 20 variables and then listing them in the renderer.
	    //This code was written in preparation for making a bus renderer class in less code.
	    //This will use an array filling function that automatically generates the seat positions in a future bus renderer code.
	    Vec3[] seatpositions1 = new Vec3[]{
	    		new Vec3(6.25F,-1.5F,-10F), new Vec3(-6.25F,-1.5F,-10F),
	    };
	    
	    CubeListBuilder builder = CubeListBuilder.create();
	    
	    int[] offset = new int[] {219,239};
	    
	    
	    //if you want to expand the library of cars, use this function to add seats to the car. it just makes things easier.
	    //look at the seats ModelPart for usage.
	    //the seats are centered on the point that is inputed
	    
	    for(Vec3 Position : seatpositions1) {
			builder.texOffs(offset[0], offset[1]).addBox(((float)Position.x-6), ((float)Position.y-15), ((float)Position.z+6), 12, 15, 1.5F);
		}
	    
	    body.addOrReplaceChild("seats",builder,PartPose.offset(0F,0F,0F));
		
        	
	}
	
	public void setDefaultRotations() {
		this.setRotateAngle(this.Children.get("WindshieldModel"), -2.3856905971616267F, 0.0F, 0.0F);
		this.setRotateAngle(this.Children.get("WindowBack"), -0.5862560984080564F, 0.0F, 0.0F);
		
		this.setRotateAngle(this.Children.get("HeadlightL"), -0.4663519641486599F, 0.0F, 0.0F);
		this.setRotateAngle(this.Children.get("HeadlightR"), -0.4663519641486599F, 0.0F, 0.0F);
	}
	
	public void doorRotations(FollowingCar entityIn, float limbswing) {
		this.setRotateAngle(this.Children.get("door1"),0.0F, (float)(entityIn.GetOpenDoorTime().get(0)*(Math.PI/180)), 0.0f);
		this.setRotateAngle(this.Children.get("door2"),0.0F, (float)-(entityIn.GetOpenDoorTime().get(1)*(Math.PI/180)), 0.0f);
	}
	
}
