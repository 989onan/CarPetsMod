package followingcar.client.render.models.entities.followingcar;

import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;

public class ObjModel{
	public CubeListBuilder builder;
	public String Identifier;
	public PartPose offset;
	
	public ObjModel(CubeListBuilder builder, String idenifier,PartPose offset) {
		this.builder = builder;
		this.Identifier = idenifier;
		this.offset = offset;
	}
}