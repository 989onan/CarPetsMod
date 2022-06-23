package followingcar.client.render.models.entities.followingcar;

import java.util.HashMap;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import followingcar.common.entities.FollowingCar;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * FollowingCar - 989onan
 * Created using Tabula 8.0.0
 * yeeted by 989onan lol
 */

@OnlyIn(Dist.CLIENT)
public class FollowingCarModel<T extends FollowingCar> extends EntityModel<T> {
	
	
	public static HashMap<Integer,HashMap<Integer,HashMap<Integer,BakedModel>>> wheelsandbodies = new HashMap<Integer,HashMap<Integer,HashMap<Integer,BakedModel>>>();
	
	public int lighting = 0;
	public int overlaycoords = 0;
	
	public FollowingCarModel(ModelPart bakeLayer) {
		
		//grabbing all types of car models for wheels and body.
    	
    }
    
    //just rando garbage left to show that you don't really need it except to register the model..
	public static LayerDefinition createBodyMesh(CubeDeformation p_170769_) {
    	
    	
    	MeshDefinition meshdefinition = new MeshDefinition();
        //PartDefinition modelroot = meshdefinition.getRoot();
    	
        
        return LayerDefinition.create(meshdefinition, 256, 256);
    }

    
    
    @Override
    public void prepareMobModel(T p_102614_, float p_102615_, float p_102616_, float p_102617_) {
    	super.prepareMobModel(p_102614_, p_102615_, p_102616_, p_102617_);
    	
    }
    public static Vec3 offset = new Vec3(0F,1.5F*16F,0F);
	//setup animation really means animation event...
    //we'll use this to change model parts that need rotations dependent on the type of model.
	@Override
	public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks,
			float netHeadYaw, float headPitch) {
	}
	
	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer p_103112_, int p_103113_, int p_103114_,
			float p_103115_, float p_103116_, float p_103117_, float p_103118_) {
		this.overlaycoords = p_103114_;
		this.lighting = p_103113_;
	}
	
}