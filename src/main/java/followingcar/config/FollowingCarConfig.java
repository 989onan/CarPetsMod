package followingcar.config;

import java.io.File;


import followingcar.MainFollowingCar;
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
		MainFollowingCar.LOGGER.info("Loading Configs at: "+path);
		final CommentedFileConfig file = CommentedFileConfig.builder(new File(path)).sync().autosave().writingMode(WritingMode.REPLACE).build();
		MainFollowingCar.LOGGER.info("Built config at: "+path);
		file.load();
		MainFollowingCar.LOGGER.info("Loaded config at: "+path);
		config.setConfig(file);
		
	}
	//initializing variables that will have the values in the config file
	public static ForgeConfigSpec.BooleanValue oldmovement;
	public static ForgeConfigSpec.BooleanValue realturning;
	//loading values into variables
	public static void init(ForgeConfigSpec.Builder serverconfig) {
		serverconfig.comment("Car Pets Config");
		
		oldmovement = serverconfig.comment("Whether or not to use the old movement system").define("followingcarconfig.oldmovement", false);
	}
}