package followingcar.core.init;



import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
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
	public static void registerBlocks(RegistryEvent.Register<Block> Registry) {
		Registry.getRegistry().registerAll(
				
				);
	}
	
	
	@SubscribeEvent
	public static void registerEntities(RegistryEvent.Register<EntityType<?>> e) {
		e.getRegistry().registerAll(
				EntityTypeInit.FOLLOWING_CAR
				);
		//MainFollowingCar.LOGGER.info("trying to get loottables for "+EntityTypeInit.FOLLOWING_CAR.getName()+" from: "+EntityTypeInit.FOLLOWING_CAR.getLootTable().toString());
	}
}
