package followingcar.config;

import java.io.File;



import followingcar.MainFollowingCar;
import followingcar.core.init.CarTypeRegistry;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.Builder;

public class FollowingCarConfig {
	//making config loader and builders
	public static Builder configbuilder = new ForgeConfigSpec.Builder();
	public static ForgeConfigSpec config;
	
	static{
		init(configbuilder);
		config = configbuilder.build();
	}
	//building config
	public static void loadConfig(ForgeConfigSpec config, String path) {
		MainFollowingCar.LOGGER.info("Car Pet Mod -> Loading Configs at: "+path);
		final CommentedFileConfig file = CommentedFileConfig.builder(new File(path)).sync().autosave().writingMode(WritingMode.REPLACE).build();
		MainFollowingCar.LOGGER.info("Car Pet Mod -> Built config at: "+path);
		file.load();
		MainFollowingCar.LOGGER.info("Car Pet Mod -> Loaded config at: "+path);
		config.setConfig(file);
		CarTypeRegistry.CarScale = FollowingCarConfig.carscale.get();
		MainFollowingCar.LOGGER.info("Car Pet Mod -> Reading realsize varible for initalization. This varible will change according to if the currently connected server has this on or off.");
	}
	//initializing variables that will have the values in the config file
	public static ForgeConfigSpec.BooleanValue oldmovement;
	public static ForgeConfigSpec.BooleanValue userealsize;
	public static ForgeConfigSpec.DoubleValue carscale;
	//loading values into variables
	public static void init(ForgeConfigSpec.Builder serverconfig) {
		serverconfig.comment("Car Pets Config");
		
		oldmovement = serverconfig.comment("(DEPRECIATED, NOT USED!) Whether or not to use the old movement system").define("followingcarconfig.oldmovement", false);
		userealsize = serverconfig.comment("(DEPRECIATED, NOT USED!) Whether or not to use realistic size. This will change in RAM (not in this file) according to server settings! Cars are usually 1.5X this changes it to 1X.").define("followingcarconfig.userealsize", false);
		
		carscale =  serverconfig.comment("Car size. This will change in RAM (not in this file) according to server settings! Cars are usually 1.5X").defineInRange("followingcarconfig.size", 1.5, .01, 100);
		
		
	}
}