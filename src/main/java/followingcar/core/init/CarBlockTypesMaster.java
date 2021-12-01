package followingcar.core.init;

import java.util.HashMap;

import net.minecraft.client.resources.model.BakedModel;

public class CarBlockTypesMaster {

	//storage for models that are registered as blocks
	
	
	public static HashMap<Integer,HashMap<Integer,BakedModel>> CarObjModels = new HashMap<Integer,HashMap<Integer,BakedModel>>();
	public static HashMap<Integer,HashMap<Integer,BakedModel>> CarObjModelsHigh = new HashMap<Integer,HashMap<Integer,BakedModel>>();

}
