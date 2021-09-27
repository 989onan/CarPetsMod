package followingcar.common.items;

import followingcar.MainFollowingCar;


import net.minecraft.world.item.Item;
import net.minecraft.world.item.CreativeModeTab;

public class itemsmaster {
	//basic "Non functional" Items (aka they don't do anything by themselves)
	public static Item GASCAN = new Item(new Item.Properties().stacksTo(64).tab(CreativeModeTab.TAB_FOOD)).setRegistryName(MainFollowingCar.MODID+":gascan");
	public static Item WHEEL = new Item(new Item.Properties().stacksTo(64).tab(CreativeModeTab.TAB_MATERIALS)).setRegistryName(MainFollowingCar.MODID+":wheel");
	public static Item SOUL = new Item(new Item.Properties().stacksTo(64).tab(CreativeModeTab.TAB_MATERIALS)).setRegistryName(MainFollowingCar.MODID+":soul");
	
	
	//engine parts
	public static Item Piston = new Item(new Item.Properties().stacksTo(64).tab(CreativeModeTab.TAB_MATERIALS)).setRegistryName(MainFollowingCar.MODID+":piston");
	public static Item Piston_Block = new Item(new Item.Properties().stacksTo(64).tab(CreativeModeTab.TAB_MATERIALS)).setRegistryName(MainFollowingCar.MODID+":piston_engine_block");
	public static Item battery = new Item(new Item.Properties().stacksTo(64).tab(CreativeModeTab.TAB_MATERIALS)).setRegistryName(MainFollowingCar.MODID+":battery");
	public static Item wire = new Item(new Item.Properties().stacksTo(64).tab(CreativeModeTab.TAB_MATERIALS)).setRegistryName(MainFollowingCar.MODID+":wire");
	public static Item acid = new ToolTipItem(new Item.Properties().stacksTo(64).tab(CreativeModeTab.TAB_MATERIALS),"I shouldn't, and won't place this down!",true,false,15).setRegistryName(MainFollowingCar.MODID+":acid");
	
	//Engines
	public static Item Dead_Engine = new ToolTipItem(new Item.Properties().stacksTo(64).tab(CreativeModeTab.TAB_MATERIALS),"This engine seems to not work in the world of Minecraft\ndue to the magic in the air.\nMaybe it needs magic to run? (AHEM.. Use a soul gotten from blasting soul sand.)",true,false,15).setRegistryName(MainFollowingCar.MODID+":dead_engine");
	public static Item Alive_Engine = new ToolTipItem(new Item.Properties().stacksTo(64).tab(CreativeModeTab.TAB_MATERIALS),"It's Alive!!! Oops... Well let's try it anyway...\nThis is alive and innocent!",true,false,15).setRegistryName(MainFollowingCar.MODID+":alive_engine");
	
	
	//car changer
	public static Item CarChanger = new CarChangerItem(new Item.Properties().stacksTo(64).tab(CreativeModeTab.TAB_MATERIALS),"Shift right click not on a car to change target car type.\nRight click on car to change it's type.\nCan only be used once.",true,false,15).setRegistryName(MainFollowingCar.MODID+":car_changer");
	
	
	//spawn egg registers
	public static Item FollowingCarSpawnEgg;
	
}
