package followingcar.client.render;

import java.util.HashMap;
import followingcar.MainFollowingCar;
import followingcar.client.render.entities.followingcar.FollowingCarRender;
import followingcar.client.render.models.entities.followingcar.FollowingCarModel;
import followingcar.core.init.CarTypeRegistry;
import followingcar.core.init.EntityTypeInit;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent.RegisterLayerDefinitions;
import net.minecraftforge.client.event.EntityRenderersEvent.RegisterRenderers;
import net.minecraftforge.client.model.ForgeModelBakery;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;


@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD,value=Dist.CLIENT)
public class FollowingCarRenderRegistry {
	
	
	
	@SubscribeEvent
	public static void objloader(net.minecraftforge.client.event.ModelBakeEvent Baker) {
		CarTypeRegistry.CarTypes.forEach((type,carvariant) ->{carvariant.getModelNames().forEach((modelindex,name)->{ //name means model name.
			//this allows us to skip using block registries!!
			//"I am very excited for this!!" - 989onan
			/*models is structured like this:
				first goes car type
				next goes 0-infinity in terms of quality. 0 is simple obj model.
				last goes the model index from 0-infinity. first is the wheel, then the body, then the color
			*/
			if(ModList.get().isLoaded("followingcarmodextras")) {//we have following car extras
				FollowingCarModel.wheelsandbodies.putIfAbsent(type, new HashMap<Integer,HashMap<Integer,BakedModel>>());
    			FollowingCarModel.wheelsandbodies.get(type).putIfAbsent(1, new HashMap<Integer,BakedModel>());
    			FollowingCarModel.wheelsandbodies.get(type).putIfAbsent(2, new HashMap<Integer,BakedModel>());
    			
    			if(name != null && Baker.getModelManager().getModel(MainFollowingCar.ExtrasLocation("block/"+name+(carvariant.getHighDefIdentifier()))) != null) {
    				FollowingCarModel.wheelsandbodies.get(type).get(1).put(modelindex, Baker.getModelManager().getModel(MainFollowingCar.ExtrasLocation("block/"+name)));
    			}
	    		if(name != null && Baker.getModelManager().getModel(MainFollowingCar.ExtrasLocation("block/"+name+(carvariant.getHighDefIdentifier()))) != null) {
	    			FollowingCarModel.wheelsandbodies.get(type).get(2).put(modelindex, Baker.getModelManager().getModel(MainFollowingCar.ExtrasLocation("block/"+name+(carvariant.getHighDefIdentifier()))));
	    		}
			}
			else{//we don't have followingcarextras.
				FollowingCarModel.wheelsandbodies.putIfAbsent(type, new HashMap<Integer,HashMap<Integer,BakedModel>>());
    			FollowingCarModel.wheelsandbodies.get(type).putIfAbsent(0, new HashMap<Integer,BakedModel>());
    			if(modelindex != 1) {
	    			if(name != null && Baker.getModelManager().getModel(MainFollowingCar.Location("block/"+name)) != null) {
	    				
		    			FollowingCarModel.wheelsandbodies.get(type).get(0).put(modelindex, Baker.getModelManager().getModel(MainFollowingCar.Location("block/"+name)));
		    		}
    			}
    			else{
    				if(name != null && Baker.getModelManager().getModel(MainFollowingCar.Location("block/basicwheel")) != null) {
    					FollowingCarModel.wheelsandbodies.get(type).get(0).put(1, Baker.getModelManager().getModel(MainFollowingCar.Location("block/basicwheel")));
    				}
				}
			}
		
		});
		});
	}
	
	//registering block model renderers:
	@SubscribeEvent
    public static void objadder(net.minecraftforge.client.event.ModelRegistryEvent Loader) {
		
    	CarTypeRegistry.CarTypes.forEach((type,carvariant) ->{carvariant.getModelNames().forEach((modelindex,name)->{
    		if(ModList.get().isLoaded("followingcarmodextras")) {
	    		//this allows us to skip using block registries!!
				//"I am very excited for this!!" - 989onan
	    		//low def models
				if(name != null) {
					try {
						ForgeModelBakery.addSpecialModel(MainFollowingCar.ExtrasLocation("block/"+name));
					}
					catch(Exception e) {
						if(e.getMessage() != "Could not find OBJ model") {
							MainFollowingCar.LOGGER.warn("OOppsie! There's a messup from car pets mod! Here's a stacktrace! The problem it said: \""+e.getMessage()+"\"");
							MainFollowingCar.LOGGER.catching(org.apache.logging.log4j.Level.INFO, e);
						}
						else {
							MainFollowingCar.LOGGER.catching(org.apache.logging.log4j.Level.INFO, e);
						}
					}
				}
				
				
				//high def models
				if(name != null) {
					try {
						ForgeModelBakery.addSpecialModel(MainFollowingCar.ExtrasLocation("block/"+name+(carvariant.getHighDefIdentifier())));
					}
					catch(Exception e) {
						if(e.getMessage() != "Could not find OBJ model") {
							MainFollowingCar.LOGGER.warn("OOppsie! There's a messup from car pets mod! Here's a stacktrace! The problem it said: \""+e.getMessage()+"\"");
							MainFollowingCar.LOGGER.catching(org.apache.logging.log4j.Level.INFO, e);
						}
						else {
							MainFollowingCar.LOGGER.catching(org.apache.logging.log4j.Level.INFO, e);
						}
					}
				}
    		}
    		else {
				
				//normal model adding:
				if(modelindex == 0 || modelindex == 2) { //we only have unique body models for each body
					if(name != null) {
						try {
							ForgeModelBakery.addSpecialModel(MainFollowingCar.Location("block/"+name));
						}
						catch(Exception e) {
							if(e.getMessage() != "Could not find OBJ model") {
								MainFollowingCar.LOGGER.warn("OOppsie! There's a messup from car pets mod! Here's a stacktrace! The problem it said: \""+e.getMessage()+"\"");
								MainFollowingCar.LOGGER.catching(org.apache.logging.log4j.Level.INFO, e);
							}
							else {
								MainFollowingCar.LOGGER.catching(org.apache.logging.log4j.Level.INFO, e);
							}
						}
					}
				}
    		}
		});});
    	if(!ModList.get().isLoaded("followingcarmodextras")) {
    		ForgeModelBakery.addSpecialModel(MainFollowingCar.Location("block/basicwheel"));
    	}
    }
	
	
	
	
	
	@SubscribeEvent
	public static void renderregister(final RegisterLayerDefinitions event) {
		
		
		event.registerLayerDefinition(new ModelLayerLocation(MainFollowingCar.Location("textures/entities/simplecaratlas_color"),""), ()->FollowingCarModel.createBodyMesh(CubeDeformation.NONE));
		event.registerLayerDefinition(new ModelLayerLocation(MainFollowingCar.Location("textures/entities/simplecaratlas_body"),""), ()->FollowingCarModel.createBodyMesh(CubeDeformation.NONE));
	}
	
	@SubscribeEvent
	public static void RegisterModelRenderers(final RegisterRenderers event){
		
		event.registerEntityRenderer(EntityTypeInit.FOLLOWING_CAR.get(), FollowingCarRender::new);
		
	}
	
	
	
	
}
