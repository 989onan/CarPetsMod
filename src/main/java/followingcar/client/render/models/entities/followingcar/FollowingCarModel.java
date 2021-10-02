package followingcar.client.render.models.entities.followingcar;

import java.util.HashMap;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import followingcar.client.render.models.followingcarvariants.AE86Model;
import followingcar.client.render.models.followingcarvariants.DefaultModel;
import followingcar.client.render.models.followingcarvariants.GeneralPartModel;
import followingcar.common.entities.FollowingCar;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import followingcar.core.init.*;

/**
 * FollowingCar - 989onan
 * Created using Tabula 8.0.0
 * rewritten randomly by 989onan
 */

@OnlyIn(Dist.CLIENT)
public class FollowingCarModel<T extends FollowingCar> extends EntityModel<T> {
	
	//helper functions
	
	
	//stores all model variants
	public HashMap<Integer,GeneralPartModel> AllModelPartModels = new HashMap<Integer,GeneralPartModel>();
	
	
    public FollowingCarModel(ModelPart bakeLayer) {
    	
		//add all models and add them to the bake layer at the same time
		
    	
    	
    	//this is where a new model variant is added.
		AllModelPartModels.put(0, new DefaultModel(bakeLayer));
		AllModelPartModels.put(1, new AE86Model(bakeLayer));
    }
	public static LayerDefinition createBodyMesh(CubeDeformation p_170769_) {
    	
    	
    	MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition modelroot = meshdefinition.getRoot();
        
        
        AE86Model.addModelLayer(modelroot, p_170769_);
        DefaultModel.addModelLayer(modelroot, p_170769_);
    	
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
    	
    	AllModelPartModels.forEach((k,Model) ->{( Model).setDefaultRotations();});
    	
    }
    
    
    private void PrepModel(T entityIn, float limbSwing) {
    	
    	//hide all models and their parts, then unhide what model the car is set to later.
		AllModelPartModels.forEach((k,Model)-> {Model.forEach((j,Part) -> {Part.visible = false;});});
		
		//the code craziness below says this:
		/*
		1. grab the actual car type. Name overrides car type
		2. if both block model and obj model types are null, then render default block model
		3. if obj model doesn't equal null, obj model gets priority so render nothing
		4. if we have a block model, render it
		4. if somehow we got past everything else, render default
		*/
		
		int actualtype = entityIn.getActualCarType();
		
		
		if(AllModelPartModels.get(actualtype) == null && CarBlockTypesMaster.CarObjModels.get(actualtype) == null){
			AllModelPartModels.get(0).forEach((k,Part) -> {Part.visible = true;});
		}
		else if(CarBlockTypesMaster.CarObjModels.get(actualtype) != null) {
			//we don't run anything here, since the model layer will take care of this!
			//this check is to prevent a block model being drawn if a .obj exists when it has a registered variant name.
		}
		else if (AllModelPartModels.get(actualtype) != null){
			AllModelPartModels.get(actualtype).forEach((k,Part) -> {Part.visible = true;});
		}
		else {
			
			AllModelPartModels.get(0).forEach((k,Part) -> {Part.visible = true;});
		}
		
		//getting synced data set num 1 from entity
 	     String incomingstatus1 = Integer.toBinaryString(entityIn.getEntityData().get(FollowingCar.CarFlags1).byteValue());
 	     
 	     incomingstatus1 = ("0".repeat(Math.abs(incomingstatus1.length()-4)))+incomingstatus1;
 	     
 	     
 	     short rotationsign = (short) -(incomingstatus1.charAt(0) == '0' ? Short.parseShort(""+incomingstatus1.charAt(1)) : -1*Short.parseShort(""+incomingstatus1.charAt(1)));
        short movementsign = (short) -(incomingstatus1.charAt(2) == '0' ? Short.parseShort(""+incomingstatus1.charAt(3)) : -1*Short.parseShort(""+incomingstatus1.charAt(3)));
        
		
		//Below this line is rotations. Above this line is car type parameters 
		
		
		//set wheel rotations for all models and all wheels
		if(entityIn.isOrderedToSit() || entityIn.isInSittingPose()) {
	    	AllModelPartModels.forEach((k,Model)-> {
	    		boolean foundwheel = true;
	    		int i = 0;
	    		
	    		//rotate left and right wheels for all car types
	    		while (foundwheel) {
	    			i++;
	    			if(Model.get("wheel-l"+i) != null){
	    				this.setRotateAngle(Model.get("wheel-l"+i), (( limbSwing/1.1F)*movementsign), 0.0f, 0.6f);
	    			}
	    			else {
	    				foundwheel = false;
	    			}
	    		}
	    		foundwheel = true;
	    		i = 0;
	    		while (foundwheel) {
	    			i++;
	    			if(Model.get("wheel-r"+i) != null){
	    				this.setRotateAngle(Model.get("wheel-r"+i), (( limbSwing/1.1F)*movementsign), 0.0f, -0.6f);
	    			}
	    			else {
	    				foundwheel = false;
	    			}
	    		}
	    		this.setRotateAngle(Model.get("Chassis"),-.05F, 0F, 0F);
	    	});
    	}
    	else {
	    	AllModelPartModels.forEach((k,Model)-> {
	    		boolean foundwheel = true;
	    		int i = 0;
	    		
	    		//rotate left and right wheels for all car types
	    		while (foundwheel) {
	    			i++;
	    			if(Model.get("wheel-l"+i) != null){
	    				this.setRotateAngle(Model.get("wheel-l"+i), (( limbSwing/1.1F)*movementsign), (rotationsign*CarTypeRegistry.WheelAngle*Mth.DEG_TO_RAD), 0.0f);
	    			}
	    			else {
	    				foundwheel = false;
	    			}
	    		}
	    		i = 0;
	    		foundwheel = true;
	    		while (foundwheel) {
	    			i++;
	    			
	    			if(Model.get("wheel-r"+i) != null){
	    				this.setRotateAngle(Model.get("wheel-r"+i), (( limbSwing/1.1F)*movementsign), (rotationsign*CarTypeRegistry.WheelAngle*Mth.DEG_TO_RAD), 0.0f);
	    			}
	    			else {
	    				foundwheel = false;
	    			}
	    		}
	    		this.setRotateAngle(Model.get("Chassis"),0F, 0F, 0F);
	    	});
	    	
    	}
		AllModelPartModels.forEach((k,Model) ->{(Model).doorRotations(entityIn,limbSwing);});
	}
	//setup animation really means animation event...
    //we'll use this to change model parts that need rotations dependent on the type of model.
	@Override
	public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks,
			float netHeadYaw, float headPitch) {
		
		
		PrepModel(entityIn, limbSwing);
	}
	
	@Override
	public void renderToBuffer(PoseStack p_103111_, VertexConsumer p_103112_, int p_103113_, int p_103114_,
			float p_103115_, float p_103116_, float p_103117_, float p_103118_) {
		
		
		
		
		AllModelPartModels.forEach((k,Model) ->{Model.renderToBuffer( p_103111_, p_103112_, p_103113_, p_103114_, p_103115_, p_103116_, p_103117_, p_103118_);
        });
	}
	
	
	
	
	
	
}