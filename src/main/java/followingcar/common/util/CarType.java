package followingcar.common.util;

import java.util.HashMap;

import net.minecraft.world.phys.Vec3;

public class CarType {
	
	private float Acceleration;
	private float MaxSpeed;
	private float FrictionMultiplier;
	private String HighDefIdentifier;
	private Vec3[] PassengerOffsets;
	private HashMap<String,Vec3> WheelOffsets;
	private HashMap<Integer, String> ModelNames;
	private String ColorTextureName;
	private String MainTextureName;
	private float DriftMultiplier;
	private String Name;
	
	public CarType(String BodyModelName, String ColorModelName, String WheelModelName, String HighDefIdentifier, String MainColorModelTextureName, String MainBlockModelTextureName) {
		this.MaxSpeed = 1;
		this.FrictionMultiplier = 1;
		this.Acceleration = 1;
		this.ModelNames = new HashMap<Integer,String>(){//this is a hashmap to make block registry quicker on server version
				private static final long serialVersionUID = -632361313663271312L;
				{
					//this is where the model values go
					//all values are in degrees and meters in the Blender3D editor
					put(0,BodyModelName); 
					put(1,WheelModelName); 
					put(2,ColorModelName); 
				}
			};
		this.HighDefIdentifier = HighDefIdentifier;
		//car type 0 offsets (default)
		//this also determines max passenger size
		this.PassengerOffsets = new Vec3[] {
			new Vec3(-0.5,-0.2,0.02),new Vec3(0.5,-0.2,0.02),
			new Vec3(-0.5,-0.75,0.02),new Vec3(0.5,-0.75,0.02)
		};
		this.WheelOffsets = new HashMap<String,Vec3>(){
			/**
			 * 
			 */
			private static final long serialVersionUID = -6323696599663271312L;

				{
					//this is where the model values go
					//all values are in degrees and meters in the Blender3D editor
					put("WheelLRot",new Vec3(0F,0F,0F)); //wheel default rotation
					put("WheelL_1_Offset",new Vec3(-0.894734F,0.310943F,1.35094F)); //position of the first wheel (usually front) on the left looking at the car from the back
					put("WheelL_2_Offset",new Vec3(-0.894734F,0.310943F,-1.35094F)); //same as above but for second
				}
			};
		this.MainTextureName = MainBlockModelTextureName;
		this.ColorTextureName = MainColorModelTextureName;
		this.Name = "NULL";
	}
	
	
	//car behaviors
	public float getAcceleration() {return this.Acceleration;}
	public CarType setAcceleration(float acc) {this.Acceleration = acc;return this;}
	public float getMaxSpeed() {return this.MaxSpeed;}
	public CarType setMaxSpeed(float speed) {this.MaxSpeed = speed;return this;}
	public float getRollFrictionMultiplier() {return this.FrictionMultiplier;}
	public CarType setRollFrictionMultiplier(float friction) {this.FrictionMultiplier = friction;return this;}
	public Vec3[] getPassengerOffsets() {return this.PassengerOffsets;}
	//conversion from old format to new happens inside the entity.
	//format has z as vertical. unlike Minecraft which has y as vertical.
	public CarType setPassengerOffsets(Vec3[] passengeroffsets) {this.PassengerOffsets = passengeroffsets;return this;}
	public CarType setDriftMultiplier(float drift) {this.DriftMultiplier = drift; return this;}
	public float getDriftMultiplier() {return this.DriftMultiplier;}
	
	//car look and appearance
	public String getHighDefIdentifier() {return this.HighDefIdentifier;}
	public String getColorTextureName() {return this.ColorTextureName;}
	public String getMainTextureName() {return this.MainTextureName;}
	public CarType setWheelOffsets(HashMap<String,Vec3> WheelOffsets) {this.WheelOffsets = WheelOffsets;return this;}
	public HashMap<String,Vec3> getWheelOffsets() { return this.WheelOffsets;}
	public HashMap<Integer, String> getModelNames() {return ModelNames;}
	public CarType setName(String name) {this.Name = name;return this;}
	public String getName() {return this.Name;}
	
	
	
	
	
	
}
