package followingcar.client.render.models.entities.followingcar;

import com.mojang.blaze3d.vertex.PoseStack;

import com.mojang.blaze3d.vertex.VertexConsumer;

import followingcar.client.render.models.followingcarvariants.AE86Model;
import followingcar.client.render.models.followingcarvariants.DefaultModel;
import followingcar.client.render.models.followingcarvariants.GeneralModelObj;
import followingcar.common.entities.FollowingCar;
import net.minecraft.ChatFormatting;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import followingcar.client.render.entities.followingcar.FollowingCarRender;


import java.util.HashMap;


/**
 * FollowingCar - 989onan
 * Created using Tabula 8.0.0
 * rewritten randomly by 989onan
 */

@OnlyIn(Dist.CLIENT)
public class FollowingCarModel<T extends FollowingCar> extends EntityModel<T> {
	
	
	//stores all model variants
	public HashMap<Integer,HashMap<String,ModelPart>> AllModels = new HashMap<Integer,HashMap<String,ModelPart>>();
	

    public FollowingCarModel(ModelPart bakeLayer) {
    	
		//add all models and add them to the bake layer at the same time
    	
		
    	//this is where a new model variant is added.
		AllModels.put(0, new DefaultModel(bakeLayer));
		AllModels.put(1, new AE86Model(bakeLayer));
    }
	public static LayerDefinition createBodyMesh(CubeDeformation p_170769_) {
    	
    	
    	MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition modelroot = meshdefinition.getRoot();
        
        
        DefaultModel.addModelLayer(modelroot, p_170769_);
        AE86Model.addModelLayer(modelroot, p_170769_);
    	
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
    	
    	PrepModel(p_102614_,p_102615_);
    	
    }
    
   
    private void PrepModel(T entityIn, float limbSwing) {
    	//hide all models and their parts, then unhide what model the car is set to.
		AllModels.forEach((k,Model)-> {Model.forEach((j,Part) -> {Part.visible = false;});});
		
		//if it has an easter egg name, render that model. Else use the car model type it is set to.
		String name = ChatFormatting.stripFormatting(entityIn.getName().getString());
		
		if(AllModels.get(entityIn.getCarType()) == null && FollowingCarRender.NameToVariant.get(name) == null){
			AllModels.get(0).forEach((k,Part) -> {Part.visible = true;});
		}
		else if(FollowingCarRender.NameToVariant.get(name) != null){
			AllModels.get(FollowingCarRender.NameToVariant.get(name)).forEach((k,Part) -> {Part.visible = true;});
		}
		else {
			AllModels.get(entityIn.getCarType()).forEach((k,Part) -> {Part.visible = true;});
		}
		
		
		
		
		AllModels.forEach((k,Model) ->{((GeneralModelObj) Model).setDefaultRotations();});
		
		
		
		//Below this line is rotations. Above this line is car type parameters 
		
		
		//set wheel rotations for all models and all wheels
		if(entityIn.isOrderedToSit() || entityIn.isInSittingPose()) {
	    	AllModels.forEach((k,Model)-> {
	    		boolean foundwheel = true;
	    		int i = 0;
	    		
	    		//rotate left and right wheels for all car types
	    		while (foundwheel) {
	    			i++;
	    			if(Model.get("wheel-l"+i) != null){
	    				this.setRotateAngle(Model.get("wheel-l"+i), limbSwing/1.1F, 0.0f, 0.6f);
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
	    				this.setRotateAngle(Model.get("wheel-r"+i), limbSwing/1.1F, 0.0f, -0.6f);
	    			}
	    			else {
	    				foundwheel = false;
	    			}
	    		}
	    		this.setRotateAngle(Model.get("Chassis"),-.05F, 0F, 0F);
	    	});
    	}
    	else {
	    	AllModels.forEach((k,Model)-> {
	    		boolean foundwheel = true;
	    		int i = 0;
	    		
	    		//rotate left and right wheels for all car types
	    		while (foundwheel) {
	    			i++;
	    			if(Model.get("wheel-l"+i) != null){
	    				this.setRotateAngle(Model.get("wheel-l"+i), limbSwing/1.1F, 0.0f, 0.0f);
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
	    				this.setRotateAngle(Model.get("wheel-r"+i), limbSwing/1.1F, 0.0f, 0.0f);
	    			}
	    			else {
	    				foundwheel = false;
	    			}
	    		}
	    		this.setRotateAngle(Model.get("Chassis"),0F, 0F, 0F);
	    	});
	    	
    	}
		AllModels.forEach((k,Model) ->{((GeneralModelObj) Model).doorRotations(entityIn,limbSwing);});
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
		
		AllModels.forEach((k,Model) ->{Model.forEach((j,ModelPart) -> { 
            ModelPart.render( p_103111_, p_103112_, p_103113_, p_103114_, p_103115_, p_103116_, p_103117_, p_103118_);
        });});
	}
}