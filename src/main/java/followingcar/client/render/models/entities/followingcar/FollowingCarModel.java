package followingcar.client.render.models.entities.followingcar;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import followingcar.common.entities.FollowingCar;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.vector.Vector3d;
/**
 * FollowingCar - 989onan
 * Created using Tabula 8.0.0
 */
public class FollowingCarModel extends EntityModel<FollowingCar> {
    public ModelRenderer Chassis;
    public ModelRenderer Lid;
    public ModelRenderer WindowBack;
    public ModelRenderer WindshieldModel;
    public ModelRenderer doorfl;
    public ModelRenderer doorrl;
    public ModelRenderer doorrr;
    public ModelRenderer doorfr;
    public ModelRenderer wheelfr;
    public ModelRenderer wheelfl;
    public ModelRenderer wheelrl;
    public ModelRenderer wheelrr;
    
    //stores the boxes for every seat.
    public ModelRenderer seats;
    
    //this just stores the 
    int[] seatTextureLocation = new int[] {100,82};

    public FollowingCarModel() {
        this.textureWidth = 256;
        this.textureHeight = 256;
        this.WindshieldModel = new ModelRenderer(this);
        this.WindshieldModel.setRotationPoint(16.0F, -3.0F  -18.0F, -6.0F);
        this.WindshieldModel.setTextureOffset(0, 45+32).addBox(0.0F, 0.0F, 0.0F, 32.0F, 1.0F, 17.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(WindshieldModel, -0.5061454830783556F, 3.141592653589793F, 0.0F);
        this.doorrr = new ModelRenderer(this);
        this.doorrr.setRotationPoint(16.0F, 6.0F  -18.0F, 1.0F);
        this.doorrr.setTextureOffset(99+64, 31+32).addBox(-1.0F, -1.0F, 0.0F, 1.0F, 13.0F, 19.0F, 0.0F, 0.0F, 0.0F);
        this.doorrl = new ModelRenderer(this);
        this.doorrl.setRotationPoint(-15.0F, 6.0F  -18.0F, 1.0F);
        this.doorrl.setTextureOffset(141+64, 12+32).addBox(-1.0F, -1.0F, 0.0F, 1.0F, 13.0F, 19.0F, 0.0F, 0.0F, 0.0F);
        this.Lid = new ModelRenderer(this);
        this.Lid.setRotationPoint(-15.0F, 5.0F  -18.0F, 20.0F);
        this.Lid.setTextureOffset(0, 28+32).addBox(0.0F, 0.0F, 0.0F, 30.0F, 1.0F, 16.0F, 0.0F, 0.0F, 0.0F);
        this.WindowBack = new ModelRenderer(this);
        this.WindowBack.setRotationPoint(-15.9F, -3.0F  -18.0F, 12.0F);
        this.WindowBack.setTextureOffset(28+64, 31+32).addBox(0.0F, 0.0F, 0.0F, 32.0F, 1.0F, 13.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(WindowBack, -0.782431135626991F, 0.0F, 0.0F);
        this.Chassis = new ModelRenderer(this);
        this.Chassis.setRotationPoint(0.0F, 18.0F, 0.0F);
        this.Chassis.addBox(-16.0F, 0.0F, -20.0F, 32.0F, 1.0F, 40.0F, 0.0F, 0.0F, 0.0F);
        this.Chassis.setTextureOffset(104, 0).addBox(-16.0F, -13.0F, -40.0F, 32.0F, 14.0F, 20.0F, 0.0F, 0.0F, 0.0F);
        this.Chassis.setTextureOffset(128, 34).addBox(-16.0F, -12.0F, 20.0F, 32.0F, 13.0F, 16.0F, 0.0F, 0.0F, 0.0F);
        this.Chassis.addBox(-16.0F, -13.0F, 20.0F, 1.0F, 1.0F, 16.0F, 0.0F, 0.0F, 0.0F);
        this.Chassis.setTextureOffset(188, 0).addBox(15.0F, -13.0F, 20.0F, 1.0F, 1.0F, 16.0F, 0.0F, 0.0F, 0.0F);
        this.Chassis.setTextureOffset(0, 41).addBox(-16.0F, -21.0F, -6.0F, 32.0F, 1.0F, 18.0F, 0.0F, 0.0F, 0.0F);
        this.Chassis.setTextureOffset(34, 0).addBox(15.0F, -20.0F, 0.0F, 1.0F, 20.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.Chassis.setTextureOffset(222, 0).addBox(-16.0F, -20.0F, 0.0F, 1.0F, 20.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.doorfl = new ModelRenderer(this);
        this.doorfl.setRotationPoint(-15.0F, 6.0F  -18.0F, -20.0F);
        this.doorfl.setTextureOffset(144+64, 1).addBox(-1.0F, -1.0F, 0.0F, 1.0F, 13.0F, 20.0F, 0.0F, 0.0F, 0.0F);
        this.doorfr = new ModelRenderer(this);
        this.doorfr.setRotationPoint(16.0F, 6.0F  -18.0F, -20.0F);
        this.doorfr.setTextureOffset(119+64, 43+32).addBox(-1.0F, -1.0F, 0.0F, 1.0F, 13.0F, 20.0F, 0.0F, 0.0F, 0.0F);
        
        //wheels
        this.wheelfr = new ModelRenderer(this);
        this.wheelfr.setRotationPoint(16F, 18F, -30.0F);
        this.wheelfr.setTextureOffset(161+64, 44+32).addBox(-0.0F, -6.0F, -6.0F, 1.0F, 12.0F, 12.0F, 0.0F, 0.0F, 0.0F);
        this.wheelrr = new ModelRenderer(this);
        this.wheelrr.setRotationPoint(16F, 18F, 28.0F);
        this.wheelrr.setTextureOffset(161+64, 44+32).addBox(-0.0F, -6.0F, -6.0F, 1.0F, 12.0F, 12.0F, 0.0F, 0.0F, 0.0F);
        this.wheelfl = new ModelRenderer(this);
        this.wheelfl.setRotationPoint(-17.5F, 18.0F, -30.0F);
        this.wheelfl.setTextureOffset(161+64, 44+32).addBox(-0.0F, -6.0F, -6.0F, 1.0F, 12.0F, 12.0F, 0.0F, 0.0F, 0.0F);
        this.wheelrl = new ModelRenderer(this);
        this.wheelrl.setRotationPoint(-17.5F, 18.0F, 28.0F);
        this.wheelrl.setTextureOffset(161+64, 44+32).addBox(-0.0F, -6.0F, -6.0F, 1.0F, 12.0F, 12.0F, 0.0F, 0.0F, 0.0F);
        
        //This allows the creation of a bunch of seats in an array and then render them all at once.
        //Better than instantiating 20 variables and then listing them in the renderer.
        //This code was written in preparation for making a bus renderer class in less code.
        //This will use an array filling function that automatically generates the seat positions in a future bus renderer code.
        Vector3d[] seatpositions = new Vector3d[]{
        		new Vector3d(6.25F,-1.5F,-10F), new Vector3d(-6.25F,-1.5F,-10F),
        		new Vector3d(6.25F,-1.5F,10F), new Vector3d(-6.25F,-1.5F,10F)
        };
        
        
        this.seats = new ModelRenderer(this);
        this.seats.setRotationPoint(0, 0, 0);
        this.seats = makeSeats(this.seats,this.seatTextureLocation,seatpositions);//create the seats using the seatpositions array and put them under the ModelRenderer.
        
        //parenting blocks
        this.Chassis.addChild(WindowBack);
        this.Chassis.addChild(WindshieldModel);
        this.Chassis.addChild(doorfl);
        this.Chassis.addChild(doorfr);
        this.Chassis.addChild(doorrl);
        this.Chassis.addChild(doorrr);
        this.Chassis.addChild(Lid);
        this.Chassis.addChild(seats);
        //this.Chassis.addChild(seats);
        
        
        
    }

    @Override
    public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) { 
        ImmutableList.of(this.Chassis, this.wheelfl,this.wheelfr,this.wheelrl,this.wheelrr).forEach((modelRenderer) -> { 
            modelRenderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        });
        
    }


    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
    
    //if you want to expand the library of cars, use this function to add seats to the car. it just makes things easier.
    //look at the seats ModelRenderer for usage.
    //the seats are centered on the point that is inputed
    
    public ModelRenderer makeSeats(ModelRenderer seat,int[] offset, Vector3d[] Positions) {
    	
    	//this takes every seat position in the array, extracts it, and then adds it as an offset
    	//to the ModelRenderer location. Then it returns the modelRenderer so all the seats render at the same time.
    	for(Vector3d Position : Positions) {
	    	seat.setTextureOffset(offset[0], offset[1]).addBox(((float)Position.x-6), ((float)Position.z-6), 12, 1.5F, 12, 0);
	    	seat.setTextureOffset(offset[0], offset[1]+15).addBox(((float)Position.x-6), ((float)Position.y-15), ((float)Position.z+6), 12, 15, 1.5F, 0);
    	}
    	return seat;
    }
    

	@Override
	public void setRotationAngles(FollowingCar entityIn, float limbSwing, float limbSwingAmount, float ageInTicks,
			float netHeadYaw, float headPitch) {
		this.setRotateAngle(wheelfl, limbSwing/1.1F, 0.0f, 0.0f);
		this.setRotateAngle(wheelfr, limbSwing/1.1F, 0.0f, 0.0f);
		this.setRotateAngle(wheelrr, limbSwing/1.1F, 0.0f, 0.0f);
		this.setRotateAngle(wheelrl, limbSwing/1.1F, 0.0f, 0.0f);
		this.setRotateAngle(Chassis, 0F, 0F, 0F);
		if(entityIn.isEntitySleeping()) {
			//rotate wheels if sitting
			this.setRotateAngle(wheelfl, limbSwing/1.1F, 0.0f, 0.6f);
			this.setRotateAngle(wheelfr, limbSwing/1.1F, 0.0f, -0.6f);
			this.setRotateAngle(wheelrr, limbSwing/1.1F, 0.0f, -0.6f);
			this.setRotateAngle(wheelrl, limbSwing/1.1F, 0.0f, 0.6f);
			//rotate body if sitting
			this.setRotateAngle(Chassis, -.05F, 0F, 0F);
		}
		
		//rotate doors to current door angle on entity
		this.setRotateAngle(doorfr, 0, (float)(entityIn.GetOpenDoorTime()[0]*(Math.PI/180)),0);
		this.setRotateAngle(doorfl, 0, (float)(-entityIn.GetOpenDoorTime()[1]*(Math.PI/180)),0);
		this.setRotateAngle(doorrr, 0, (float)(entityIn.GetOpenDoorTime()[2]*(Math.PI/180)),0);
		this.setRotateAngle(doorrl, 0, (float)(-entityIn.GetOpenDoorTime()[3]*(Math.PI/180)),0);
		
		
		//angle reference sheet, doesn't do anything really
		//float rotationaxis = 0.0F;
		
		//this.setRotateAngle(doorfl, 0.0F, rotationaxis, 0.0F);
		//this.setRotateAngle(doorrl, 0.0F, rotationaxis, 0.0F);
		//this.setRotateAngle(doorfr, 0.0F, rotationaxis, 0.0F);
		//this.setRotateAngle(doorrr, 0.0F, rotationaxis, 0.0F);

		//this.setRotateAngle(Lid, rotationaxis, 0.0F, 0.0F);
	}
}