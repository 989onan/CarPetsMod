package followingcar.client.render.models.followingcarvariants;

import java.util.HashMap;


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import followingcar.common.entities.FollowingCar;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
@OnlyIn(Dist.CLIENT)
public class GeneralPartModel extends HashMap<String,ModelPart>{
	
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5876948621029080063L;
	
	public void setRotateAngle(ModelPart ModelPart, float x, float y, float z) {
        ModelPart.xRot = x;
        ModelPart.yRot = y;
        ModelPart.zRot = z;
    }
	
	public void renderToBuffer(PoseStack p_103111_, VertexConsumer p_103112_, int p_103113_, int p_103114_,
			float p_103115_, float p_103116_, float p_103117_, float p_103118_) {
		this.forEach((k,ModelPart) -> {ModelPart.render( p_103111_,  p_103112_,  p_103113_,  p_103114_,
			 p_103115_,  p_103116_,  p_103117_,  p_103118_);});
	}
	
	public void setDefaultRotations() {}
	
	public void doorRotations(FollowingCar entityIn, float limbswing) {}
	
	
}
