package followingcar.core.init;


import followingcar.MainFollowingCar;
import followingcar.common.entities.FollowingCar;
import followingcar.common.items.itemsmaster;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
//import net.minecraftforge.fml.RegistryObject;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;

public class EntityTypeInit {
	
	//register our Entities
	@SuppressWarnings("unchecked")
	public static final EntityType<FollowingCar> FOLLOWING_CAR = (EntityType<FollowingCar>) EntityType.Builder.create(FollowingCar::new,EntityClassification.CREATURE).build(MainFollowingCar.MODID+":following_car").setRegistryName(new ResourceLocation(MainFollowingCar.MODID, "following_car"));
	
	
	
	public static void RegisterEntitySpawnEggs(final RegistryEvent.Register<Item> registry) {
		registry.getRegistry().registerAll(
				itemsmaster.FollowingCarSpawnEgg = EntityTypeInit.registerEntitySpawnEgg(EntityTypeInit.FOLLOWING_CAR,0x000000,0xe82907,"followingcar_spawnegg")
						);
	}
	
	public static SpawnEggItem registerEntitySpawnEgg(EntityType<?> type, int color1,int color2,String name) {
		SpawnEggItem Item = new SpawnEggItem(type, color1, color2, new Item.Properties().group(ItemGroup.MISC));
		Item.setRegistryName(MainFollowingCar.MODID+":"+name);
		return Item;
	}
	
	
	
	
}
