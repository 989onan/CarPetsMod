package followingcar.client.render;

import java.util.HashMap;
import followingcar.MainFollowingCar;
import followingcar.client.render.entities.followingcar.FollowingCarRender;
import followingcar.client.render.models.entities.followingcar.FollowingCarModel;
import followingcar.core.init.CarBlockTypesMaster;
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
import net.minecraftforge.fml.common.Mod;


@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD,value=Dist.CLIENT)
public class FollowingCarRenderRegistry {
	
	
	
	public static final HashMap<Integer, ModelLayerLocation> ColorTextures = new HashMap<Integer, ModelLayerLocation>();

	

	public static final HashMap<Integer, ModelLayerLocation> MainTextures = new HashMap<Integer, ModelLayerLocation>();
	
	@SubscribeEvent
	public static void objloader(net.minecraftforge.client.event.ModelBakeEvent Baker) {
		CarTypeRegistry.CarTypes.forEach((type,carvariant) ->{carvariant.getModelNames().forEach((modelindex,name)->{
			//this allows us to skip using block registries!!
			//"I am very excited for this!!" - 989onan
			
    		//low def models
    		CarBlockTypesMaster.CarObjModels.putIfAbsent(type, new HashMap<Integer,BakedModel>());	
    		if(name != null && Baker.getModelRegistry().get(MainFollowingCar.ExtrasLocation("block/"+name)) != null) {
    			
    			CarBlockTypesMaster.CarObjModels.get(type).put(modelindex, Baker.getModelRegistry().getOrDefault(MainFollowingCar.ExtrasLocation("block/"+name), Baker.getModelManager().getMissingModel()));
    		}
    		//high def models
    		CarBlockTypesMaster.CarObjModelsHigh.putIfAbsent(type, new HashMap<Integer,BakedModel>());
    		if(name != null && Baker.getModelRegistry().get(MainFollowingCar.ExtrasLocation("block/"+name+(carvariant.getHighDefIdentifier()))) != null) {
    			CarBlockTypesMaster.CarObjModelsHigh.get(type).put(modelindex, Baker.getModelRegistry().getOrDefault(MainFollowingCar.ExtrasLocation("block/"+name+(carvariant.getHighDefIdentifier())), Baker.getModelManager().getMissingModel()));
    		}
			});
		});
	}
	
	//registering block model renderers:
	@SubscribeEvent
    public static void objadder(net.minecraftforge.client.event.ModelRegistryEvent Loader) {
    	CarTypeRegistry.CarTypes.forEach((type,carvariant) ->{carvariant.getModelNames().forEach((modelindex,name)->{
    		//this allows us to skip using block registries!!
			//"I am very excited for this!!" - 989onan
    		//low def models
    		CarBlockTypesMaster.CarObjModels.putIfAbsent(type, new HashMap<Integer,BakedModel>());
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
			CarBlockTypesMaster.CarObjModelsHigh.putIfAbsent(type, new HashMap<Integer,BakedModel>());
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
			
		});});
    }
	
	
	
	
	
	@SubscribeEvent
	public static void renderregister(final RegisterLayerDefinitions event) {
		
		CarTypeRegistry.CarTypes.forEach((type,cartype) ->{
			if(cartype.getColorTextureName() != null) {
				ColorTextures.put(type, new ModelLayerLocation(MainFollowingCar.Location(cartype.getColorTextureName()),type+""));
			}
		});
		
		CarTypeRegistry.CarTypes.forEach((type,cartype) ->{
			if(cartype.getMainTextureName() != null) {
				MainTextures.put(type, new ModelLayerLocation(MainFollowingCar.Location(cartype.getMainTextureName()),type+""));
			}
		});
		
		ColorTextures.forEach((k,texture) -> {
			event.registerLayerDefinition(texture, ()->FollowingCarModel.createBodyMesh(CubeDeformation.NONE) );
		});
	}
	
	@SubscribeEvent
	public static void RegisterModelRenderers(final RegisterRenderers event){
		
		event.registerEntityRenderer(EntityTypeInit.FOLLOWING_CAR, FollowingCarRender::new);
		
	}
	
	
	
	
}
