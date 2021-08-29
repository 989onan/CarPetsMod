package followingcar.client.render.models.entities.followingcar;

import java.util.HashMap;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import followingcar.common.entities.FollowingCar;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.level.block.state.BlockState;
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
	
	//helper functions
	
	
	//stores all model variants
	//public HashMap<Integer,GeneralPartModel> AllModelPartModels = new HashMap<Integer,GeneralPartModel>();
	
	public static class modelstore{
		
		public BakedModel model;
		public BlockState state;

		modelstore(BakedModel model, BlockState state){
			this.model = model;
			this.state = state;
		}
		
	}
	
	public static HashMap<Integer,HashMap<Integer,modelstore>> BakedColorModels = new HashMap<Integer,HashMap<Integer,modelstore>>();
	
	
	public static HashMap<Integer,HashMap<String,Vec3>> ModelScales = new HashMap<Integer,HashMap<String,Vec3>>(){
		private static final long serialVersionUID = 5669485508318977301L;

		{
			put(0/*This is where the car type number goes*/,new HashMap<String,Vec3>(){
				/**
				 * 
				 */
				private static final long serialVersionUID = -6323696599663271312L;

					{
						//this is where the model values go
						//all values are in degrees and meters in the Blender3D editor
						put("WheelLRot",new Vec3(0F,0F,0F)); //wheel default rotation
						put("WheelL_1_Offset",new Vec3(-0.894734F,0.310943F,1.35094F)); //position of the first wheel (usually front) on the left looking at the car from the back
						put("WheelL_2_Offset",new Vec3(-0.894734F,0.310943F,-1.35094F)); //same as above but for second
					}
				}
			);
			
			put(1/*This is where the car type number goes*/,new HashMap<String,Vec3>(){
				
				private static final long serialVersionUID = -440888986543410543L;

					{
						//this is where the model values go
						//all values are in degrees and meters in the Blender3D editor
						put("WheelLRot",new Vec3(0F,180F,0F)); //wheel default rotation
						put("WheelL_1_Offset",new Vec3(-0.709092F,0.280732F,1.28826F)); //position of the first wheel (usually front) on the left looking at the car from the back
						put("WheelL_2_Offset",new Vec3(-0.709092F,0.280732F,-1.14622F)); //same as above but for second
					}
				}
			);
			
			put(2/*This is where the car type number goes*/,new HashMap<String,Vec3>(){
				
				private static final long serialVersionUID = -440888986543410543L;

					{
						//this is where the model values go
						//all values are in degrees and meters in the Blender3D editor
						put("WheelLRot",new Vec3(0F,0F,0F)); //wheel default rotation
						put("WheelL_1_Offset",new Vec3(-0.885266F,0.384F,1.40376F)); //position of the first wheel (usually front) on the left looking at the car from the back
						put("WheelL_2_Offset",new Vec3(-0.885266F,0.384F,-1.40376F)); //same as above but for second
					}
				}
			);
			
			//add new one down here!
			
		}
	};
	
	
	
	/*
	HOW TO MAKE MODELS FOR .OBJ!!!!!!!!!!
		
		===============================
		String Name = "examplemodel"; //"Name" is refrenced below which represents the model name
		===============================
		
		1. add a new block in the CarBlockTypesMaster class inside this mod with the MainFollowingCar.Location(Name) set to the name of the model
		2. put a json file called Name.json inside blockstates like the rest of the model jsons there
		3. make a json file inside the resources: models/block/Name.Json and add the obj model inside with Name. Textures inside don't matter
		4. make a .obj file inside the resources: models/obj/Name.obj
		5. edit the .mtl to have map_Kd followingcarmod:blocks/cartypes/Name at the end.
		6. add a new set of scale numbers in the ModelScales hashmap above. 
	
	*/
	
	
    public FollowingCarModel(ModelPart bakeLayer) {
    	
		//add all models and add them to the bake layer at the same time
		
    	
    	
    	//this is where a new model variant is added.
		//AllModelPartModels.put(0, new DefaultModel(bakeLayer));
		
		//this model now has a .obj!
		//AllModelPartModels.put(1, new AE86Model(bakeLayer));
    }
	public static LayerDefinition createBodyMesh(CubeDeformation p_170769_) {
    	
    	
    	MeshDefinition meshdefinition = new MeshDefinition();
        //PartDefinition modelroot = meshdefinition.getRoot();
        
        
        //AE86Model.addModelLayer(modelroot, p_170769_);
    	
        return LayerDefinition.create(meshdefinition, 256, 256);
    }/*


    /**
     * This is a helper function from Tabula to set the rotation of model parts
     
	public void setRotateAngle(ModelPart ModelPart, float x, float y, float z) {
        ModelPart.xRot = x;
        ModelPart.yRot = y;
        ModelPart.zRot = z;
    }
    
    
    
    @Override
    public void prepareMobModel(T p_102614_, float p_102615_, float p_102616_, float p_102617_) {
    	super.prepareMobModel(p_102614_, p_102615_, p_102616_, p_102617_);
    	
    	//AllModelPartModels.forEach((k,Model) ->{( Model).setDefaultRotations();});
    	
    }
    
    /*
    private void PrepModel(T entityIn, float limbSwing) {
    	
    	//hide all models and their parts, then unhide what model the car is set to.
		AllModelPartModels.forEach((k,Model)-> {Model.forEach((j,Part) -> {Part.visible = false;});});
		
		//if it has an easter egg name, render that model. Else use the car model type it is set to.
		String name = ChatFormatting.stripFormatting(entityIn.getName().getString());
		
		if(AllModelPartModels.get(entityIn.getCarType()) == null && FollowingCarRender.NameToVariant.get(name) == null && CarBlockTypesMaster.CarObjModels.get(entityIn.getCarType()) == null){
			AllModelPartModels.get(0).forEach((k,Part) -> {Part.visible = true;});
		}
		else if(FollowingCarRender.NameToVariant.get(name) != null){
			if(CarBlockTypesMaster.CarObjModels.get(entityIn.getCarType()) != null) {
				//we don't run anything here, since the model layer will take care of this!
				//this check is to prevent a block model being drawn if a .obj exists when it has a registered variant name.
			}
			else if(AllModelPartModels.get(FollowingCarRender.NameToVariant.get(name)) != null) {
				AllModelPartModels.get(FollowingCarRender.NameToVariant.get(name)).forEach((k,Part) -> {Part.visible = true;});
			}
		}
		else if (CarBlockTypesMaster.CarObjModels.get(entityIn.getCarType()) == null && CarBlockTypesMaster.CarObjModels.get(FollowingCarRender.NameToVariant.get(name)) == null){
			AllModelPartModels.get(entityIn.getCarType()).forEach((k,Part) -> {Part.visible = true;});
		}
		
		
		
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
	    	AllModelPartModels.forEach((k,Model)-> {
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
		
		
		
		/*
		AllModelPartModels.forEach((k,Model) ->{Model.renderToBuffer( p_103111_, p_103112_, p_103113_, p_103114_, p_103115_, p_103116_, p_103117_, p_103118_);
        });
	}*/
	
	public static int type = 0;
	
	@Override
	public void setupAnim(T entityIn, float p_102619_, float p_102620_, float p_102621_, float p_102622_,
			float p_102623_) {
	
		
	}
	
	
	
	public static int curtick = 0;
	public static int prevtick = 0;
	@Override
	public void renderToBuffer(PoseStack p_103111_, VertexConsumer p_103112_, int p_103113_, int LimbSwing,
			float p_103115_, float p_103116_, float p_103117_, float p_103118_) {
	}
	
	
	
	
	
	
}