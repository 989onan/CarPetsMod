package followingcar.core.init;


import followingcar.MainFollowingCar;



import followingcar.common.entities.FollowingCar;
import followingcar.common.items.SpawnEggCustomTexture;
import followingcar.common.items.itemsmaster;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.event.RegistryEvent;
//import net.minecraftforge.fml.RegistryObject;

public class EntityTypeInit {
	
	//register our Entities
	@SuppressWarnings("unchecked")
	public static final EntityType<FollowingCar> FOLLOWING_CAR = (EntityType<FollowingCar>) EntityType.Builder.of(FollowingCar::new,MobCategory.CREATURE).build(MainFollowingCar.MODID+":following_car").setRegistryName(new ResourceLocation(MainFollowingCar.MODID, "following_car"));
	
	
	
	public static void RegisterEntitySpawnEggs(final RegistryEvent.Register<Item> registry) {
		registry.getRegistry().registerAll(
				itemsmaster.FollowingCarSpawnEgg = EntityTypeInit.registerEntitySpawnEgg(EntityTypeInit.FOLLOWING_CAR,"followingcar_spawnegg")
						);
	}
	
	public static Item registerEntitySpawnEgg(EntityType<? extends Mob> type, String name) {
		SpawnEggCustomTexture Item = new SpawnEggCustomTexture(type, new Item.Properties().stacksTo(64).tab(CreativeModeTab.TAB_MISC));
		Item.setRegistryName(MainFollowingCar.MODID+":"+name);
		return Item;
	}
	
	
	
	
}
