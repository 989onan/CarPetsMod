package followingcar.client.render.entities.followingcar;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;

import followingcar.MainFollowingCar;
import followingcar.client.render.models.entities.followingcar.FollowingCarModel;
import followingcar.common.entities.FollowingCar;
import followingcar.core.init.CarBlockTypesMaster;
import net.minecraft.ChatFormatting;
import net.minecraft.client.GraphicsStatus;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.util.Mth;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.model.data.EmptyModelData;

import java.util.ArrayList;

@OnlyIn(Dist.CLIENT)
public class FollowingCarModelLayer extends RenderLayer<FollowingCar,FollowingCarModel<FollowingCar>>{

	//important! Defines what the offsets and scales are for each model!!
	
	public static class modelstore{
		
		public BakedModel model;
		public BlockState state;

		modelstore(BakedModel model, BlockState state){
			this.model = model;
			this.state = state;
		}
		
	}
	
	public static HashMap<Integer,HashMap<Integer,modelstore>> BakedColorModels = new HashMap<Integer,HashMap<Integer,modelstore>>();
	public static HashMap<Integer,HashMap<Integer,modelstore>> BakedColorModelsHigh = new HashMap<Integer,HashMap<Integer,modelstore>>();
	
	
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
		
	 	7. repeat steps 2-5 but with "High" on the end of Name.
	
	*/
	public FollowingCarModelLayer(RenderLayerParent<FollowingCar, FollowingCarModel<FollowingCar>> p_117346_, EntityModelSet entityModelSet) {
		super(p_117346_);
	}
	
	public BakedModel cachequads(BakedModel colorbakedmodel, BlockState colorblockstate) {
		 List<BakedQuad> quads = colorbakedmodel.getQuads(colorblockstate, null, new Random(), null);
	       
   	 
		 //give the quads tint
	     List<BakedQuad> tintedquads = new ArrayList<BakedQuad>(quads.size());
	     for (BakedQuad item : quads) tintedquads.add(new BakedQuad(item.getVertices(),
	    		1,
		 item.getDirection(),
		 item.getSprite(),
		 true));
	   	 
		    
		 colorbakedmodel.getQuads(colorblockstate, null, new Random(), null).clear();
		 colorbakedmodel.getQuads(colorblockstate, null, new Random(), null).addAll(tintedquads);
		 return colorbakedmodel;
	}
	
	public static int prevtick = 0;
	
	public static int curtick = 0;
	
	
	
	@SuppressWarnings("resource")
	@Override
	public void render(PoseStack p_117349_, MultiBufferSource p_117350_, int p_117351_, FollowingCar entityIn,
			float LimbSwing, float p_117354_, float p_117355_, float p_117356_, float p_117357_, float p_117358_) {
		Minecraft minecraft = Minecraft.getInstance(); 
		boolean flag = true;
		curtick++;
         if ((!entityIn.isInvisible() || flag) && (curtick) > prevtick) {
        	 prevtick = curtick;
        	 BlockRenderDispatcher blockrenderdispatcher = minecraft.getBlockRenderer();
        	 
        	 int type = 0;
        	 
        	 if(entityIn.getCustomName() != null) {
        		 if(FollowingCarRender.NameToVariant.get(ChatFormatting.stripFormatting(entityIn.getCustomName().getString())) != null) {
        			 type = FollowingCarRender.NameToVariant.get(ChatFormatting.stripFormatting(entityIn.getCustomName().getString()));
            	 }
        		 else {
        			 type = entityIn.getCarType();
            	 }
        	 }
        	 else {
        		 type = entityIn.getCarType();
        	 }
        	 
        	 HashMap<Integer,Block> model;
        	 HashMap<Integer,HashMap<Integer,modelstore>> current;
        	 
        	 if(Minecraft.getInstance().options.graphicsMode == GraphicsStatus.FANCY || Minecraft.getInstance().options.graphicsMode == GraphicsStatus.FAST) {
        		 model = CarBlockTypesMaster.CarObjModels.get(type);
        		 current = BakedColorModels;
        	 }
        	 else {
        		 model = CarBlockTypesMaster.CarObjModelsHigh.get(type);
        		 current = BakedColorModelsHigh;
        	 }
        	 
        	 
        	 
        	 
    		 Vec3 scale = new Vec3(1.5,1.5,1.5);
    		 HashMap<String,Vec3> scalelist = ModelScales.get(type);
    		 
    		 if(current.get(type) == null){ //we chache on the first frame, so for the first frame it's not rendered
    			 if(model != null) {
    				 current.put(type, new HashMap<Integer,modelstore>());
            		 HashMap<Integer,modelstore> bakedmodel = current.get(type);
            		 
            		 
            		 BlockState bodyblockstate = model.get(0).defaultBlockState();
        			 
		             
		             BakedModel bodybakedmodel = blockrenderdispatcher.getBlockModel(bodyblockstate);
		             
		             BlockState colorblockstate = model.get(2).defaultBlockState();
		             BakedModel colorbakedmodel = blockrenderdispatcher.getBlockModel(colorblockstate);
    				 
    				 
    				 bakedmodel.put(0, new modelstore(bodybakedmodel,bodyblockstate));
    				 
    				 BlockState wheelblockstate = model.get(1).defaultBlockState();
    				 BakedModel wheelbakedmodel = blockrenderdispatcher.getBlockModel(wheelblockstate);
    				 bakedmodel.put(1, new modelstore(wheelbakedmodel,wheelblockstate));
    				 
    				 colorbakedmodel = cachequads(colorbakedmodel, colorblockstate);
    				 bakedmodel.put(2,new modelstore(colorbakedmodel,colorblockstate));
    				 
            		 
            		 
    			 }
        	 }
    		 else {//after the first frame it should be chached, so grab the chache and render.
    			 
	    		 modelstore body = current.get(type).get(0);
	    		 modelstore wheel = current.get(type).get(1);
	    		 modelstore color = current.get(type).get(2);
	    		 
	    		 BakedModel bodybakedmodel = body.model;
	    		 BakedModel wheelbakedmodel = wheel.model;
	    		 BakedModel colorbakedmodel = color.model;
	    		 
	    		 BlockState bodyblockstate = body.state;
	    		 BlockState wheelblockstate = wheel.state;
	    		 BlockState colorblockstate = color.state;
	    		 
        		 
        		 int coords = LivingEntityRenderer.getOverlayCoords(entityIn, .1F);
        		 //coords = p_117350_.getBuffer(RenderType.outline(InventoryMenu.BLOCK_ATLAS)).applyBakedLighting(coords, null);
        		 
        		 
        		 if(bodybakedmodel != null && wheelbakedmodel != null && scalelist != null && colorbakedmodel != null) {
        			 float[] afloat = entityIn.getColor().getTextureDiffuseColors();
        			 
        			 
        			 
            		    
        			 
        			 //color layer
        			 
        			 //lerp color brightness for each color based on color of color layer png and then lerp between 0.0 and that by alpha

        			 
        			 
        			 
        			 
		             
		             Vec3 offset = new Vec3(0F,1.5,0F);
		             //try to render the car chassis model with given offsets.
		             try {
		            	 p_117349_.pushPose();
		            	 p_117349_.mulPose(Vector3f.YN.rotationDegrees(180F));
		            	 if(entityIn.isOrderedToSit() || entityIn.isInSittingPose()) {
			            	 p_117349_.mulPose(Vector3f.XN.rotationDegrees(-5F));
			             }
			             else {
			            	 p_117349_.mulPose(Vector3f.XN.rotationDegrees(0.0F));
			             }
		            	 
		            	 
		            	 p_117349_.translate((float)offset.x, (float)offset.y, (float)offset.z);
		            	 
		            	 p_117349_.mulPose(Vector3f.XP.rotationDegrees(180.0F));
		            	 
		            	 
		            	 
		            	 
		            	 
		            	 p_117349_.scale((float)scale.x,(float)scale.y,(float)scale.z);
		            	 //body
		            	 FollowingCarModelLayer.renderBlockOnEntity(p_117349_, p_117350_, p_117351_, flag, blockrenderdispatcher, bodyblockstate, coords, bodybakedmodel, new Vec3(1F,1F,1F),true);
			             
		            	 
		            	 //color
		            	 
		            	 
	            		    
	            		 FollowingCarModelLayer.renderBlockOnEntity(p_117349_, p_117350_, p_117351_, flag, blockrenderdispatcher, colorblockstate, coords, colorbakedmodel, new Vec3(afloat[0],afloat[1],afloat[2]),false);
	            		 p_117349_.popPose();
		             }
		             catch(Exception e) {
		            	 MainFollowingCar.LOGGER.warn("Chassis Model render for car \""+type+"\" has failed! Here is the stack trace:");
		            	 e.printStackTrace();
		            	 
		             }
		             
		             
		            
		            //HashMap<String,Vec3> Model = ModelScales.get(entityIn.getCarType());
		            //float scaleVsChassis = (float) ( 0.331178F);//scalelist.get("LongestChassisAxis").x /*scalelist.get("LongestWheelAxis").x*/ );
		            
	 	    		
		            boolean foundwheel = true;
	 	    		int i = 0;
	 	    		
	 	    		//rotate left and right wheels for all car types and position them
	 	    		while (foundwheel) {
	 	    			i++;
	 	    			if(scalelist.get("WheelL_"+i+"_Offset") != null){
	 	    				try {
	 	    					
	 	    					
	 	    					
								p_117349_.pushPose();
								
								p_117349_.mulPose(Vector3f.YN.rotationDegrees(180F));
								p_117349_.translate((double)offset.x, (double)offset.y, (double)offset.z);
								
								p_117349_.translate(scalelist.get("WheelL_"+i+"_Offset").x*scale.x, -scalelist.get("WheelL_"+i+"_Offset").y*scale.y, scalelist.get("WheelL_"+i+"_Offset").z*scale.z);
								
								
								if(entityIn.isOrderedToSit() || entityIn.isInSittingPose()) {
									p_117349_.mulPose(Vector3f.ZN.rotationDegrees(Mth.RAD_TO_DEG*( 6.0F)));
								}
								
								p_117349_.mulPose(Vector3f.XN.rotationDegrees(Mth.RAD_TO_DEG*( LimbSwing/1.1F)));
								
								
								
								
								//p_117349_.translate(0F,0.0F,0F);
								p_117349_.scale((float)scale.x, (float)scale.x, (float)scale.x);
								
								FollowingCarModelLayer.renderBlockOnEntity(p_117349_, p_117350_, p_117351_, flag, blockrenderdispatcher, wheelblockstate, coords, wheelbakedmodel, new Vec3(1F,1F,1F),false);
								p_117349_.popPose();
	 	    				}
	 	    				catch(Exception e) {
	 	    					MainFollowingCar.LOGGER.warn("Wheel Model render for car \""+type+"\" Wheel number: \""+i+"\" on the left side has failed! Here is the stack trace:");
	 			            	e.printStackTrace();
	 	    				}
	 	    				
	 	    				try {
	 	    				 p_117349_.pushPose();
	 	    				 p_117349_.mulPose(Vector3f.YN.rotationDegrees(180F));
	 	    				 p_117349_.translate((double)offset.x, (double)offset.y, (double)offset.z);
							
							 p_117349_.translate(-scalelist.get("WheelL_"+i+"_Offset").x*scale.x, -scalelist.get("WheelL_"+i+"_Offset").y*scale.y, scalelist.get("WheelL_"+i+"_Offset").z*scale.z);///scalelist.get("LongestChassisAxis").x);
								
				             p_117349_.mulPose(Vector3f.YN.rotationDegrees(180.0F));
				             
				             if(entityIn.isOrderedToSit() || entityIn.isInSittingPose()) {
				            	 p_117349_.mulPose(Vector3f.ZN.rotationDegrees(Mth.RAD_TO_DEG*(6.0F)));
				             }
				             
				             p_117349_.mulPose(Vector3f.XN.rotationDegrees(-Mth.RAD_TO_DEG*( LimbSwing/1.1F)));
				             //Model.get("wheel-r"+i).get("scale")
				             p_117349_.scale((float)scale.x, (float)scale.y, (float)scale.z);
				            
				           //uncomment this after test
							
				             
				             FollowingCarModelLayer.renderBlockOnEntity(p_117349_, p_117350_, p_117351_, flag, blockrenderdispatcher, wheelblockstate, coords, wheelbakedmodel, new Vec3(1F,1F,1F),false);
				             p_117349_.popPose();
	 	    				}
	 	    				catch(Exception e) {
	 	    					MainFollowingCar.LOGGER.warn("Wheel Model render for car \""+type+"\" Wheel number: \""+i+"\" on the right side has failed! Here is the stack trace:");
	 			            	 e.printStackTrace();
	 	    				}
	 	    			}
	 	    			else {
	 	    				foundwheel = false;
	 	    			}
	 	    		}
        		 }
        		 else {
        			 
        		 }
        	 }
         }
		
	}

	private static void renderBlockOnEntity(PoseStack p_174502_, MultiBufferSource p_174503_, int p_174504_, boolean p_174505_, BlockRenderDispatcher p_174506_, BlockState p_174507_, int p_174508_, BakedModel p_174509_, Vec3 Color,boolean transparent) {
	      if (p_174505_) {
	    	  VertexConsumer rendertype;
	    	  if(transparent == false) {
	    		  rendertype = p_174503_.getBuffer(RenderType.entitySolid(InventoryMenu.BLOCK_ATLAS));
	    	  }
	    	  else {
	    		  rendertype = p_174503_.getBuffer(RenderType.entityTranslucent(InventoryMenu.BLOCK_ATLAS));
	    	  }
	    	  Random random = new Random();
	    	  random.setSeed(42L);
	         //renderQuadList(p_174502_.last(), rendertype, (float)Color.x, (float)Color.y, (float)Color.z, p_174509_.getQuads(p_174507_, (Direction)null, random, EmptyModelData.INSTANCE), p_174504_, p_174508_); 
	    	  
	         p_174506_.getModelRenderer().renderModel(p_174502_.last(), rendertype, p_174507_, p_174509_, (float)Color.x, (float)Color.y, (float)Color.z, p_174504_, p_174508_, EmptyModelData.INSTANCE); 
	      } else {
	         //p_174506_.renderSingleBlock(p_174507_, p_174502_, p_174503_, p_174504_, p_174508_, EmptyModelData.INSTANCE);
	      }
	}
	
}