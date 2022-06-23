package followingcar.client.render.entities.followingcar;

import java.util.HashMap;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;

import followingcar.MainFollowingCar;
import followingcar.client.render.models.entities.followingcar.FollowingCarModel;
import followingcar.common.entities.FollowingCar;
import followingcar.core.init.CarTypeRegistry;

import net.minecraft.client.GraphicsStatus;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.model.data.EmptyModelData;
import net.minecraftforge.fml.ModList;

@OnlyIn(Dist.CLIENT)
public class FollowingCarWheelLayer extends RenderLayer<FollowingCar,FollowingCarModel<FollowingCar>>{


	


	public FollowingCarWheelLayer(MobRenderer<FollowingCar, FollowingCarModel<FollowingCar>> p_117346_, EntityModelSet entityModelSet) {
		super(p_117346_);
		entityModelSet.bakeLayer(new ModelLayerLocation(new ResourceLocation("followingcarmod","textures/entities/simplecaratlas_body"),""));
	}




	@SuppressWarnings("resource")
	@Override
	public void render(PoseStack poseStack, MultiBufferSource p_117350_, int p_117351_, FollowingCar entityIn,
			float LimbSwing, float p_117354_, float p_117355_, float p_117356_, float p_117357_, float p_117358_) {
		
		
		
		Minecraft minecraft = Minecraft.getInstance(); 
		boolean flag = true;
		if (!entityIn.isInvisible()){
			BlockRenderDispatcher blockrenderdispatcher = minecraft.getBlockRenderer();
			
			//get a caught and sanitized car type so we won't get an error when doing anything with
			//this car type.
			int actualtype = entityIn.getActualCarType();
			
			final int qualitynumber;
			if(ModList.get().isLoaded("followingcarmodextras")) {
				if(Minecraft.getInstance().options.graphicsMode().get()  == GraphicsStatus.FANCY || Minecraft.getInstance().options.graphicsMode().get() == GraphicsStatus.FAST) {
					qualitynumber = 1;
				}
				else {
					qualitynumber = 2;
				}
			}
			else {
				qualitynumber = 0;
			}

			int type = actualtype;
			p_117351_ = this.getParentModel().lighting;


			//this is to make the cars 1.5X bigger, because Steve is a chad and is very tall, making the cars look tiny lol
			Vec3 scale = new Vec3(CarTypeRegistry.CarScale,CarTypeRegistry.CarScale,CarTypeRegistry.CarScale);
			HashMap<String,Vec3> scalelist = CarTypeRegistry.CarTypes.get(type).getWheelOffsets();

			if(FollowingCarModel.wheelsandbodies.get(actualtype) != null){//after the first frame it should be chached, so grab the chache and render.
				//if it didn't chache it on the first frame, because it couldn't find it, this won't run



				BakedModel bodybakedmodel = FollowingCarModel.wheelsandbodies.get(actualtype).get(qualitynumber).get(0);
				BakedModel wheelbakedmodel = FollowingCarModel.wheelsandbodies.get(actualtype).get(qualitynumber).get(1);
				BakedModel colorbakedmodel = FollowingCarModel.wheelsandbodies.get(actualtype).get(qualitynumber).get(2);

				int coords = this.getParentModel().overlaycoords;
				p_117351_ = p_117351_+40;

				if(bodybakedmodel != null && wheelbakedmodel != null && scalelist != null && colorbakedmodel != null) {

					int h = (entityIn.getColor() & 16711680) >> 16;
					int j = (entityIn.getColor() & '\uff00') >> 8;
					int k = (entityIn.getColor() & 255) >> 0;
					
					
					float[] afloat = new float[]{(float)h / 255.0F, (float)j / 255.0F, (float)k / 255.0F};
					Vec3 offset = new Vec3(0F,1.5,0F);


					//getting synced data set num 1 from entity
					String incomingstatus1 = Integer.toBinaryString(entityIn.getEntityData().get(FollowingCar.CarFlags1).byteValue());

					incomingstatus1 = ("0".repeat(Math.abs(incomingstatus1.length()-4)))+incomingstatus1;


					short rotationsign = (short) -(incomingstatus1.charAt(0) == '0' ? Short.parseShort(""+incomingstatus1.charAt(1)) : -1*Short.parseShort(""+incomingstatus1.charAt(1)));
					short movementsign = (short) -(incomingstatus1.charAt(2) == '0' ? Short.parseShort(""+incomingstatus1.charAt(3)) : -1*Short.parseShort(""+incomingstatus1.charAt(3)));
					//MainFollowingCar.LOGGER.info(incomingstatus1);

					//try to render the car chassis model with given offsets.
					try {
						poseStack.pushPose();
						poseStack.mulPose(Vector3f.YN.rotationDegrees(180F));
						if(entityIn.isOrderedToSit() || entityIn.isInSittingPose()) {
							poseStack.mulPose(Vector3f.XN.rotationDegrees(-5F));
						}
						else {
							poseStack.mulPose(Vector3f.XN.rotationDegrees(0.0F));
						}

						/*
		            	 p_117349_.mulPose(Vector3f.XN.rotationDegrees((float) entityIn.rotation.x));
		            	 p_117349_.mulPose(Vector3f.YN.rotationDegrees((float) entityIn.rotation.y));
		            	 p_117349_.mulPose(Vector3f.ZN.rotationDegrees((float) entityIn.rotation.z));
						 */
						poseStack.translate((float)offset.x, (float)offset.y, (float)offset.z);

						poseStack.mulPose(Vector3f.XP.rotationDegrees(180.0F));





						poseStack.scale((float)scale.x,(float)scale.y,(float)scale.z);
						//body
						FollowingCarWheelLayer.renderBlockOnEntity(poseStack, p_117350_, p_117351_, flag, blockrenderdispatcher, null, coords, bodybakedmodel, new Vec3(1,1,1));


						//color



						
						FollowingCarWheelLayer.renderBlockOnEntity(poseStack, p_117350_, p_117351_, false, blockrenderdispatcher, null, coords, colorbakedmodel, new Vec3(afloat[0],afloat[1],afloat[2]));
						poseStack.popPose();
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



								poseStack.pushPose();

								poseStack.mulPose(Vector3f.YN.rotationDegrees(180F));
								poseStack.translate((double)offset.x, (double)offset.y, (double)offset.z);

								poseStack.translate(scalelist.get("WheelL_"+i+"_Offset").x*scale.x, -scalelist.get("WheelL_"+i+"_Offset").y*scale.y, scalelist.get("WheelL_"+i+"_Offset").z*scale.z);

								if(i==1) {//car turn
									poseStack.mulPose(Vector3f.YN.rotationDegrees(0F+(rotationsign*CarTypeRegistry.WheelAngle)));
								}


								if(entityIn.isOrderedToSit() || entityIn.isInSittingPose()) {
									poseStack.mulPose(Vector3f.ZN.rotationDegrees(Mth.RAD_TO_DEG*( 6.0F)));
								}

								poseStack.mulPose(Vector3f.XN.rotationDegrees(Mth.RAD_TO_DEG*( LimbSwing/1.1F)*movementsign));

								//MainFollowingCar.LOGGER.info(entityIn.movementsign);

								//p_117349_.translate(0F,0.0F,0F);
								if(i==2 && actualtype==13) {
									poseStack.scale((float)(scale.x*1.45156), (float)scale.x, (float)scale.x);
								}
								else {
									poseStack.scale((float)scale.x, (float)scale.x, (float)scale.x);
								}
								if(qualitynumber == 0) {
									float scaleingwheel = (float) (scalelist.get("WheelL_"+i+"_Offset").y/((0.316172F)/*this number is the offset on y for it to touch the ground*/));
									poseStack.scale(scaleingwheel, scaleingwheel, scaleingwheel);
								}

								FollowingCarWheelLayer.renderBlockOnEntity(poseStack, p_117350_, p_117351_, flag, blockrenderdispatcher, null, coords, wheelbakedmodel, new Vec3(1,1,1));
								poseStack.popPose();
							}
							catch(Exception e) {
								MainFollowingCar.LOGGER.warn("Wheel Model render for car \""+type+"\" Wheel number: \""+i+"\" on the left side has failed! Here is the stack trace:");
								e.printStackTrace();
							}

							try {
								poseStack.pushPose();
								poseStack.mulPose(Vector3f.YN.rotationDegrees(180F));
								poseStack.translate((double)offset.x, (double)offset.y, (double)offset.z);

								poseStack.translate(-scalelist.get("WheelL_"+i+"_Offset").x*scale.x, -scalelist.get("WheelL_"+i+"_Offset").y*scale.y, scalelist.get("WheelL_"+i+"_Offset").z*scale.z);///scalelist.get("LongestChassisAxis").x);

								if(i==1) {//car turn
									poseStack.mulPose(Vector3f.YN.rotationDegrees(0F+(rotationsign*CarTypeRegistry.WheelAngle)));
								}
								poseStack.mulPose(Vector3f.YN.rotationDegrees(180.0F));
								
								
								if(entityIn.isOrderedToSit() || entityIn.isInSittingPose()) {
									poseStack.mulPose(Vector3f.ZN.rotationDegrees(Mth.RAD_TO_DEG*(6.0F)));
								}

								poseStack.mulPose(Vector3f.XN.rotationDegrees(-Mth.RAD_TO_DEG*( LimbSwing/1.1F)*movementsign));
								//Model.get("wheel-r"+i).get("scale")




								if(i==2 && actualtype==13) {
									poseStack.scale((float)(scale.x*1.45156), (float)scale.x, (float)scale.x);
								}
								else {
									poseStack.scale((float)scale.x, (float)scale.x, (float)scale.x);
								}
								if(qualitynumber == 0) {
									float scaleingwheel = (float) (scalelist.get("WheelL_"+i+"_Offset").y/((0.316172F)/*this number is the offset on y for it to touch the ground*/));
									poseStack.scale(scaleingwheel, scaleingwheel, scaleingwheel);
								}
								//uncomment this after test



								
								FollowingCarWheelLayer.renderBlockOnEntity(poseStack, p_117350_, p_117351_, flag, blockrenderdispatcher, null, coords, wheelbakedmodel, new Vec3(1,1,1));
								poseStack.popPose();
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
	public static void renderBlockOnEntity(PoseStack p_174502_, MultiBufferSource p_174503_, int p_174504_, boolean p_174505_, BlockRenderDispatcher p_174506_, BlockState p_174507_, int p_174508_, BakedModel p_174509_, Vec3 Color) {
		VertexConsumer rendertype;
		rendertype = p_174503_.getBuffer(RenderType.entityTranslucent(new ResourceLocation("minecraft","textures/atlas/blocks.png")));
		p_174506_.getModelRenderer().renderModel(p_174502_.last(), rendertype, p_174507_, p_174509_, (float)Color.x, (float)Color.y, (float)Color.z, p_174504_, p_174508_, EmptyModelData.INSTANCE); 
		
		//p_174506_.getModelRenderer().renderQuadList(p_174502_.last(), rendertype, (float)Color.x, (float)Color.y, (float)Color.z, p_174509_.getQuads(p_174507_, (Direction)null, random, EmptyModelData.INSTANCE), p_174504_, p_174508_); 
		//p_174506_.renderSingleBlock(p_174507_, p_174502_, p_174503_, p_174504_, p_174508_, null);
		
	}

}