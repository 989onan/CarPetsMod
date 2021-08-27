package followingcar.common.items;

import followingcar.MainFollowingCar;


import net.minecraft.world.item.Item;
import net.minecraft.world.item.CreativeModeTab;

public class itemsmaster {
	//basic "Non functional" Items (aka they don't do anything by themselves)
	public static Item GASCAN = new Item(new Item.Properties().stacksTo(64).tab(CreativeModeTab.TAB_FOOD)).setRegistryName(MainFollowingCar.MODID+":gascan");
	public static Item WHEEL = new Item(new Item.Properties().stacksTo(64).tab(CreativeModeTab.TAB_MATERIALS)).setRegistryName(MainFollowingCar.MODID+":wheel");
	public static Item SOUL = new Item(new Item.Properties().stacksTo(64).tab(CreativeModeTab.TAB_MATERIALS)).setRegistryName(MainFollowingCar.MODID+":soul");
	//spawn egg registers
	public static Item FollowingCarSpawnEgg;
	
}
