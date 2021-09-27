package followingcar.core.init;

import net.minecraft.world.entity.EntityType;


import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.HashMap;

import followingcar.MainFollowingCar;
import followingcar.common.blocks.CarTypeObjBlock;
import followingcar.common.items.itemsmaster;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
public class Registries {
	
	
	@SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> Registry) {
    	//register spawn eggs for entities
		EntityTypeInit.RegisterEntitySpawnEggs(Registry);
		//register items
		Registry.getRegistry().registerAll(
				itemsmaster.GASCAN,
				itemsmaster.SOUL,
				itemsmaster.WHEEL,
				itemsmaster.acid,
				itemsmaster.Alive_Engine,
				itemsmaster.battery,
				itemsmaster.CarChanger,
				itemsmaster.Dead_Engine,
				itemsmaster.Piston,
				itemsmaster.Piston_Block,
				itemsmaster.wire
		);
    }
	
    
	
	
	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<net.minecraft.world.level.block.Block> Registry) {
		
		//takes model identifier names from CarTypeRegistry and registers the .obj and .json models associated to blocks.
		
		CarTypeRegistry.CarTypes.forEach((type,carvariant) ->{carvariant.getModelNames().forEach((modelindex,name)->{
			CarBlockTypesMaster.CarObjModels.putIfAbsent(type, new HashMap<Integer,Block>());
			if(name != null) {
				CarBlockTypesMaster.CarObjModels.get(type).putIfAbsent(modelindex, new CarTypeObjBlock(BlockBehaviour.Properties.of(net.minecraft.world.level.material.Material.METAL)).setRegistryName(MainFollowingCar.ExtrasLocation(name))); 
			}
			
		});});
		
		
		CarBlockTypesMaster.CarObjModels.forEach((type,model) ->{model.forEach((num,name)->{
			CarBlockTypesMaster.CarObjModelsHigh.putIfAbsent(type, new HashMap<Integer,Block>());
			if(name != null) {
				CarBlockTypesMaster.CarObjModelsHigh.get(type).putIfAbsent(num, new CarTypeObjBlock(BlockBehaviour.Properties.of(net.minecraft.world.level.material.Material.METAL)).setRegistryName(MainFollowingCar.ExtrasLocation(CarTypeRegistry.CarTypes.get(type).getModelNames().get(num).concat(CarTypeRegistry.CarTypes.get(type).getHighDefIdentifier())))); 
			}
		});});
		
		//these blocks are to be rendered onto an entity using code derived from the mooshroom which uses code to render a mushroom plant block on it's back... 
		CarBlockTypesMaster.CarObjModels.forEach((i,ModelCollection)->{ModelCollection.forEach((k,Model) ->{Registry.getRegistry().registerAll(Model);});});
		CarBlockTypesMaster.CarObjModelsHigh.forEach((i,ModelCollection)->{ModelCollection.forEach((k,Model) ->{Registry.getRegistry().registerAll(Model);});});
	}
	
	
	@SubscribeEvent
	public static void registerEntities(RegistryEvent.Register<EntityType<?>> e) {
		e.getRegistry().registerAll(
				EntityTypeInit.FOLLOWING_CAR
				);
		//MainFollowingCar.LOGGER.info("trying to get loot tables for "+EntityTypeInit.FOLLOWING_CAR.getName()+" from: "+EntityTypeInit.FOLLOWING_CAR.getLootTable().toString());
	}
}
