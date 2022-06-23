package followingcar.common.items;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.CreativeModeTab;

public class itemsmaster {
	//basic "Non functional" Items (aka they don't do anything by themselves)
	public static Item GASCAN = new Item(new Item.Properties().stacksTo(64).tab(CreativeModeTab.TAB_FOOD));
	public static Item WHEEL = new Item(new Item.Properties().stacksTo(64).tab(CreativeModeTab.TAB_MATERIALS));
	public static Item SOUL = new Item(new Item.Properties().stacksTo(64).tab(CreativeModeTab.TAB_MATERIALS));
	
	
	//engine parts
	public static Item Piston = new Item(new Item.Properties().stacksTo(64).tab(CreativeModeTab.TAB_MATERIALS));
	public static Item Piston_Block = new Item(new Item.Properties().stacksTo(64).tab(CreativeModeTab.TAB_MATERIALS));
	public static Item battery = new Item(new Item.Properties().stacksTo(64).tab(CreativeModeTab.TAB_MATERIALS));
	public static Item wire = new Item(new Item.Properties().stacksTo(64).tab(CreativeModeTab.TAB_MATERIALS));
	public static Item acid = new ToolTipItem(new Item.Properties().stacksTo(64).tab(CreativeModeTab.TAB_MATERIALS),"I shouldn't, and won't place this down!",true,false,15);
	
	//Engines
	public static Item Dead_Engine = new ToolTipItem(new Item.Properties().stacksTo(64).tab(CreativeModeTab.TAB_MATERIALS),"This engine seems to not work in the world of Minecraft due to the magic in the air. \nMaybe it needs magic to run? \n (AHEM.. Use a soul gotten from blasting soul sand.)",true,false,15);
	public static Item Alive_Engine = new ToolTipItem(new Item.Properties().stacksTo(64).tab(CreativeModeTab.TAB_MATERIALS),"It's Alive!!! Oops... Well let's try it anyway...\nThis is alive and innocent!",true,false,15);
	
	
	//car changer
	public static Item CarChanger = new CarChangerItem(new Item.Properties().stacksTo(64).tab(CreativeModeTab.TAB_MATERIALS),"Shift right click not on a car to change target car type.\nRight click on car to change it's type.\nConsumed on use.",true,false,15);
	
	//spawn egg registers
	public static Item FollowingCarSpawnEgg;
	public static Item DyeBundle = new Item(new Item.Properties().stacksTo(64).tab(CreativeModeTab.TAB_MATERIALS));
	
}
