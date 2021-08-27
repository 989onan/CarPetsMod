package followingcar.core.init;



import net.minecraft.world.entity.EntityType;


import net.minecraft.world.item.Item;
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
				itemsmaster.WHEEL
		);
    }
	
    
	
	
	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<net.minecraft.world.level.block.Block> Registry) {
		
		//these blocks are to be rendered onto an entity using code derived from the mooshroom which uses code to render a block on it's back... 
		CarBlockTypesMaster.CarObjModels.forEach((i,ModelCollection)->{ModelCollection.forEach((k,Model) ->{Registry.getRegistry().registerAll(Model);});});
	}
	
	
	@SubscribeEvent
	public static void registerEntities(RegistryEvent.Register<EntityType<?>> e) {
		e.getRegistry().registerAll(
				EntityTypeInit.FOLLOWING_CAR
				);
		//MainFollowingCar.LOGGER.info("trying to get loot tables for "+EntityTypeInit.FOLLOWING_CAR.getName()+" from: "+EntityTypeInit.FOLLOWING_CAR.getLootTable().toString());
	}
}
