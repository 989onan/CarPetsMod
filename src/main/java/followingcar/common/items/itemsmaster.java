package followingcar.common.items;

import followingcar.MainFollowingCar;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;

public class itemsmaster {
	//basic "Non functional" Items (aka they don't do anything by themselves)
	public static Item GASCAN = new Item(new Item.Properties().maxStackSize(64).group(ItemGroup.MATERIALS)).setRegistryName(MainFollowingCar.MODID+":gascan");
	public static Item WHEEL = new Item(new Item.Properties().maxStackSize(64).group(ItemGroup.MATERIALS)).setRegistryName(MainFollowingCar.MODID+":wheel");
	public static Item SOUL = new Item(new Item.Properties().maxStackSize(64).group(ItemGroup.MATERIALS)).setRegistryName(MainFollowingCar.MODID+":soul");
	//spawn egg registers
	public static Item FollowingCarSpawnEgg;
	
}
