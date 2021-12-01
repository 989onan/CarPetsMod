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
		
		//Left for actual blocks. maybe..
	}
	
	
	@SubscribeEvent
	public static void registerEntities(RegistryEvent.Register<EntityType<?>> e) {
		e.getRegistry().registerAll(
				EntityTypeInit.FOLLOWING_CAR
				);
		//MainFollowingCar.LOGGER.info("trying to get loot tables for "+EntityTypeInit.FOLLOWING_CAR.getName()+" from: "+EntityTypeInit.FOLLOWING_CAR.getLootTable().toString());
	}
}
