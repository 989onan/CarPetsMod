package followingcar.common.blocks;

import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
//class to register blocks that will render our 3D car models
public class CarTypeObjBlock extends Block{

	public CarTypeObjBlock(BlockBehaviour.Properties properties) {
		super(properties);
	}
	
	
	
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext p_49820_) {
		return null;
	}
	
}
