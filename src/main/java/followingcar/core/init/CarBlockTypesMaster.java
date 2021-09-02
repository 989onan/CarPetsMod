package followingcar.core.init;

import java.util.HashMap;

import followingcar.MainFollowingCar;
import followingcar.common.blocks.CarTypeObjBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class CarBlockTypesMaster{
	public static HashMap<Integer,HashMap<Integer,Block>> CarObjModels = new HashMap<Integer,HashMap<Integer,Block>>(){
		private static final long serialVersionUID = -7079484203885360296L;

		{
			
			//car type "0" (Default)
			put(0, new HashMap<Integer,Block>(){
				private static final long serialVersionUID = 1669012493234372576L;{ 
				put(0,new CarTypeObjBlock(BlockBehaviour.Properties.of(net.minecraft.world.level.material.Material.METAL)).setRegistryName(MainFollowingCar.ExtrasLocation("cartypebody0")));
				put(1,new CarTypeObjBlock(BlockBehaviour.Properties.of(net.minecraft.world.level.material.Material.METAL)).setRegistryName(MainFollowingCar.ExtrasLocation("cartypewheel0")));
				put(2,new CarTypeObjBlock(BlockBehaviour.Properties.of(net.minecraft.world.level.material.Material.METAL)).setRegistryName(MainFollowingCar.ExtrasLocation("cartypecolor0")));
			}}
			);
			
			
			//car type "1" (Kodachi)
			put(1, new HashMap<Integer,Block>(){
				private static final long serialVersionUID = 1669012493234372576L;{ 
				put(0,new CarTypeObjBlock(BlockBehaviour.Properties.of(net.minecraft.world.level.material.Material.METAL)).setRegistryName(MainFollowingCar.ExtrasLocation("cartypebody1")));
				put(1,new CarTypeObjBlock(BlockBehaviour.Properties.of(net.minecraft.world.level.material.Material.METAL)).setRegistryName(MainFollowingCar.ExtrasLocation("cartypewheel1")));
				put(2,new CarTypeObjBlock(BlockBehaviour.Properties.of(net.minecraft.world.level.material.Material.METAL)).setRegistryName(MainFollowingCar.ExtrasLocation("cartypecolor1")));
			}}
			);
			
			//car type "2" (Fumei)
			put(2, new HashMap<Integer,Block>(){
				private static final long serialVersionUID = -2486865856115640642L;{ 
				put(0,new CarTypeObjBlock(BlockBehaviour.Properties.of(net.minecraft.world.level.material.Material.METAL)).setRegistryName(MainFollowingCar.ExtrasLocation("cartypebody2")));
				put(1,new CarTypeObjBlock(BlockBehaviour.Properties.of(net.minecraft.world.level.material.Material.METAL)).setRegistryName(MainFollowingCar.ExtrasLocation("cartypewheel2")));
				put(2,new CarTypeObjBlock(BlockBehaviour.Properties.of(net.minecraft.world.level.material.Material.METAL)).setRegistryName(MainFollowingCar.ExtrasLocation("cartypecolor2")));
			}}
			);
		}
	};
	public static HashMap<Integer,HashMap<Integer,Block>> CarObjModelsHigh = new HashMap<Integer,HashMap<Integer,Block>>();
}
