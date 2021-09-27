package followingcar.core.init;

import java.util.HashMap;

import followingcar.common.util.CarType;
import net.minecraft.world.phys.Vec3;

public class CarTypeRegistry {
	
	public static HashMap<Integer,CarType> CarTypes = new HashMap<Integer,CarType>(){
		private static final long serialVersionUID = 1L;
		{
			//Cuda Plymouth (default)
			put(0,
					
					new CarType("cartypebody0","cartypecolor0","cartypewheel0","high",//obj models
							"textures/entities/livingcarcolor.png","textures/entities/livingcar.png") //block model
					.setAcceleration((60-0)/ /*X Seconds:*/ 5.7F) //0-60mph in X seconds.
					.setRollFrictionMultiplier(2) // 1 - infinity slipperiness. decimal = constantly braking
					.setDriftMultiplier(1)//bigger means more drift. 15 is general max but it can go higher with not ideal results. (higher than 15 not tested)
					.setMaxSpeed(117)//in miles per hour
					.setPassengerOffsets(//in order from driver to first passenger to last passenger
							new Vec3[] {//this also determines the max passengers.
									new Vec3(-0.384801,-0.086847,0.43537),new Vec3(0.384801,-0.086847,0.43537),
									new Vec3(-0.384801,-0.811136,0.43537),new Vec3(0.384801,-0.811136,0.43537),/*THIS IS THE MIDDLE SEAT:*/new Vec3(0,-0.811136,0.43537)
								}
							)
					.setWheelOffsets(new HashMap<String,Vec3>(){private static final long serialVersionUID = -6323696599663271312L;
						{
							//this is where the model values go
							//all values are in degrees and meters in the Blender3D editor
							put("WheelLRot",new Vec3(0F,0F,0F)); //wheel default rotation
							put("WheelL_1_Offset",new Vec3(-0.894734F,0.310943F,1.35094F)); //position of the first wheel (usually front) on the left looking at the car from the back
							put("WheelL_2_Offset",new Vec3(-0.894734F,0.310943F,-1.35094F)); //same as above but for second on same side
						}
					})
					.setName("Cuda Plymouth")
				);
			
			
			//AE86 Tureno (Kodachi)
			put(1,
					new CarType("cartypebody1","cartypecolor1","cartypewheel1","high",//obj models
							"textures/entities/ae86color.png","textures/entities/ae86.png")//block model
					.setAcceleration((60-0)/ /*X Seconds:*/ 8.6F) //0-60mph in X seconds.
					.setRollFrictionMultiplier(4) // 1 - infinity slipperiness. decimal = constantly braking
					.setDriftMultiplier(5)//bigger means more drift. 15 is general max but it can go higher with not ideal results. (higher than 15 not tested)
					.setMaxSpeed(118)//in miles per hour
					.setPassengerOffsets(//in order from driver to first passenger to last passenger
							new Vec3[] {//this also determines the max passengers.
									new Vec3(0.368156,-0.182994,0.362547),new Vec3(-0.368156,-0.182994,0.362547),
									new Vec3(0.368156,-0.886956,0.362547),new Vec3(-0.368156,-0.886956,0.362547),/*THIS IS THE MIDDLE SEAT:*/new Vec3(0,-0.886956,0.362547)
								}
							)
					.setWheelOffsets(new HashMap<String,Vec3>(){private static final long serialVersionUID = -6323696599663271312L;
						{
							//this is where the model values go
							//all values are in degrees and meters in the Blender3D editor
							put("WheelLRot",new Vec3(0F,0F,0F)); //wheel default rotation
							put("WheelL_1_Offset",new Vec3(-0.709092F,0.280732F,1.28826F)); //position of the first wheel (usually front) on the left looking at the car from the back
							put("WheelL_2_Offset",new Vec3(-0.709092F,0.280732F,-1.14622F)); //same as above but for second on same side
						}
					})
					.setName("AE86 Tureno")
				);
			
			//BMW I8 (Fumei)
			put(2,
					new CarType("cartypebody2","cartypecolor2","cartypewheel2","high",//obj models
							null,null)//block model
					.setAcceleration((60-0)/ /*X Seconds:*/ 4.2F) //0-60mph in X seconds.
					.setRollFrictionMultiplier(2) // 1 - infinity slipperiness. decimal = constantly braking
					.setDriftMultiplier(2)//bigger means more drift. 15 is general max but it can go higher with not ideal results. (higher than 15 not tested)
					.setMaxSpeed(155)//in miles per hour
					.setPassengerOffsets(//in order from driver to first passenger to last passenger
							new Vec3[] {//this also determines the max passengers.
									new Vec3(-0.415311,0.027371,0.487572),new Vec3(0.415311,0.027371,0.487572),
									new Vec3(-0.415311,-0.727933,0.487572),new Vec3(0.415311,-0.727933,0.487572)
								}
							
							)
					.setWheelOffsets(new HashMap<String,Vec3>(){private static final long serialVersionUID = -6323696599663271312L;
						{
							//this is where the model values go
							//all values are in degrees and meters in the Blender3D editor
							put("WheelLRot",new Vec3(0F,0F,0F)); //wheel default rotation
							put("WheelL_1_Offset",new Vec3(-0.885266F,0.384F,1.40376F)); //position of the first wheel (usually front) on the left looking at the car from the back
							put("WheelL_2_Offset",new Vec3(-0.885266F,0.384F,-1.40376F)); //same as above but for second on same side
						}
					})
					.setName("BMW I8")
				);
			
			//Hyundai Sonata 2007 (Amari)
			put(3,
					new CarType("hyundaisonata2007body","hyundaisonata2007color","hyundaisonata2007wheel","high",//obj models
							null,null)//block model
					.setAcceleration((60-0)/ /*X Seconds:*/ 7.1F) //0-60mph in X seconds.
					.setRollFrictionMultiplier(2) // 1 - infinity slipperiness. decimal = constantly braking
					.setDriftMultiplier(1)//bigger means more drift. 15 is general max but it can go higher with not ideal results. (higher than 15 not tested)
					.setMaxSpeed(140.4F)//in miles per hour
					.setPassengerOffsets(//in order from driver to first passenger to last passenger
							new Vec3[] {//this also determines the max passengers.
									new Vec3(-0.416037,-0.038737,0.468609),new Vec3(0.416037,-0.038737,0.468609),
									new Vec3(-0.416037,-0.975837,0.468609),new Vec3(0.0,-0.975837,0.468609),/*THIS IS THE MIDDLE SEAT:*/new Vec3(-0.416037,-0.975837,0.468609)
								}
							
							)
					.setWheelOffsets(new HashMap<String,Vec3>(){private static final long serialVersionUID = -6323696599663271312L;
						{
							//this is where the model values go
							//all values are in degrees and meters in the Blender3D editor
							put("WheelLRot",new Vec3(0F,0F,0F)); //wheel default rotation
							put("WheelL_1_Offset",new Vec3(-0.886144F,0.322534F,1.30683F)); //position of the first wheel (usually front) on the left looking at the car from the back
							put("WheelL_2_Offset",new Vec3(-0.886144F,0.322534F,-1.39405F)); //same as above but for second on same side
						}
					})
					.setName("Hyundai Sonata 2007")
				);
			
			//Mazda RX8 (Mazzy)
			put(4,
					new CarType("mazdarx8body","mazdarx8color","mazdarx8wheel","high",//obj models
							null,null)//block model
					.setAcceleration((60-0)/ /*X Seconds:*/ 6.3F) //0-60mph in X seconds.
					.setRollFrictionMultiplier(2) // 1 - infinity slipperiness. decimal = constantly braking
					.setDriftMultiplier(3)//bigger means more drift. 15 is general max but it can go higher with not ideal results. (higher than 15 not tested)
					.setMaxSpeed(146F)//in miles per hour
					.setPassengerOffsets(//in order from driver to first passenger to last passenger
							new Vec3[] {
									new Vec3(-0.338385,0.10926,0.401292),new Vec3(0.338385,0.10926,0.401292),
									new Vec3(-0.338385,-0.599653,0.401292),new Vec3(0.338385,-0.599653,0.401292), /*THIS IS THE MIDDLE SEAT:*/new Vec3(0,-0.599653,0.401292)
								}
							
							)
					.setWheelOffsets(new HashMap<String,Vec3>(){private static final long serialVersionUID = -6323696599663271312L;
						{
							//this is where the model values go
							//all values are in degrees and meters in the Blender3D editor
							put("WheelLRot",new Vec3(0F,0F,0F)); //wheel default rotation
							put("WheelL_1_Offset",new Vec3(-0.901282F,0.352635F,1.53879F)); //position of the first wheel (usually front) on the left looking at the car from the back
							put("WheelL_2_Offset",new Vec3(-0.925513F,0.352635F,-1.3729F)); //same as above but for second on same side
						}
					})
					.setName("Mazda RX8")
				);
			
			//Mercades Benz AMG GT
			put(5,
					new CarType("mercades_benz_amg_gt_body","mercades_benz_amg_gt_color","mercades_benz_amg_gt_wheel","high",//obj models
							null,null)//block model
					.setAcceleration((60-0)/ /*X Seconds:*/ 3.7F) //0-60mph in X seconds.
					.setRollFrictionMultiplier(2) // 1 - infinity slipperiness. decimal = constantly braking
					.setDriftMultiplier(1)//bigger means more drift. 15 is general max but it can go higher with not ideal results. (higher than 15 not tested)
					.setMaxSpeed(146F)//in miles per hour
					.setPassengerOffsets(//in order from driver to first passenger to last passenger
							new Vec3[] {//this also determines the max passengers.
									new Vec3(-0.490111,-0.642195,1.22653),new Vec3(0.490111,-0.642195,1.22653)
								}
							
							)
					.setWheelOffsets(new HashMap<String,Vec3>(){private static final long serialVersionUID = -6323696599663271312L;
						{
							//this is where the model values go
							//all values are in degrees and meters in the Blender3D editor
							put("WheelLRot",new Vec3(0F,0F,0F)); //wheel default rotation
							put("WheelL_1_Offset",new Vec3(-0.979906F,0.360068F,1.47423F)); //position of the first wheel (usually front) on the left looking at the car from the back
							put("WheelL_2_Offset",new Vec3(-0.987304F,0.35836F,-1.35157F)); //same as above but for second on same side
						}
					})
					.setName("Mercades Benz AMG GT")
				);
			
			//Mclaren Senna 
			put(6,
					new CarType("mcclaren_senna_body","mcclaren_senna_color","mcclaren_senna_wheel","high",//obj models
							null,null)//block model
					.setAcceleration((60-0)/ /*X Seconds:*/ 2.8F) //0-60mph in X seconds.
					.setRollFrictionMultiplier(2) // 1 - infinity slipperiness. decimal = constantly braking
					.setDriftMultiplier(1.3F)//bigger means more drift. 15 is general max but it can go higher with not ideal results. (higher than 15 not tested)
					.setMaxSpeed(208F)//in miles per hour
					.setPassengerOffsets(//in order from driver to first passenger to last passenger
							new Vec3[] {//this also determines the max passengers.
									new Vec3(-0.321351,0.682818,0.252496),new Vec3(0.321351,0.682818,0.252496)
								}
							
							)
					.setWheelOffsets(new HashMap<String,Vec3>(){private static final long serialVersionUID = -6323696599663271312L;
						{
							//this is where the model values go
							//all values are in degrees and meters in the Blender3D editor
							put("WheelLRot",new Vec3(0F,0F,0F)); //wheel default rotation
							put("WheelL_1_Offset",new Vec3(-0.894738F,0.343359F,1.39843F)); //position of the first wheel (usually front) on the left looking at the car from the back
							put("WheelL_2_Offset",new Vec3(-0.91967F,0.343359F,-1.27445F)); //same as above but for second on same side
						}
					})
					.setName("Mclaren Senna")
				);
			
			
			//Chevy Tahoe 2007
			put(7,
					new CarType("chevy_tahoe_body","chevy_tahoe_color","chevy_tahoe_wheel","high",//obj models
							null,null)//block model
					.setAcceleration((60-0)/ /*X Seconds:*/ 8.6F) //0-60mph in X seconds.
					.setRollFrictionMultiplier(2) // 1 - infinity slipperiness. decimal = constantly braking
					.setDriftMultiplier(1F)//bigger means more drift. 15 is general max but it can go higher with not ideal results. (higher than 15 not tested)
					.setMaxSpeed(113F)//in miles per hour
					.setPassengerOffsets(//in order from driver to first passenger to last passenger
							new Vec3[] {//this also determines the max passengers.
									new Vec3(-0.451488,0.202603,0.834035),new Vec3(0.451488,0.202603,0.834035),
									new Vec3(-0.451488,-0.768828,0.834035),new Vec3(0.451488,-0.768828,0.834035)
								}
							
							)
					.setWheelOffsets(new HashMap<String,Vec3>(){private static final long serialVersionUID = -6323696599663271312L;
						{
							//this is where the model values go
							//all values are in degrees and meters in the Blender3D editor
							put("WheelLRot",new Vec3(0F,0F,0F)); //wheel default rotation
							put("WheelL_1_Offset",new Vec3(-0.983331F,0.440555F,1.58800F)); //position of the first wheel (usually front) on the left looking at the car from the back
							put("WheelL_2_Offset",new Vec3(-0.983331F,0.440555F,-1.40607F)); //same as above but for second on same side
						}
					})
					.setName("Chevy Tahoe 2007")
				);
			
			//Lamborghini Gallardo 
			put(8,
					new CarType("lamborghini_gallardo_body","lamborghini_gallardo_color","lamborghini_gallardo_wheel","high",//obj models
							null,null)//block model
					.setAcceleration((62-0)/ /*X Seconds:*/ 3.7F) //0-62mph in X seconds.
					.setRollFrictionMultiplier(2) // 1 - infinity slipperiness. decimal = constantly braking
					.setDriftMultiplier(1.1F)//bigger means more drift. 15 is general max but it can go higher with not ideal results. (higher than 15 not tested)
					.setMaxSpeed(202F)//in miles per hour
					.setPassengerOffsets(//in order from driver to first passenger to last passenger
							new Vec3[] {//this also determines the max passengers.
									new Vec3(-0.358225,0.067525,0.273441),new Vec3(0.358225,0.067525,0.273441)
								}
							
							)
					.setWheelOffsets(new HashMap<String,Vec3>(){private static final long serialVersionUID = -6323696599663271312L;
						{
							//this is where the model values go
							//all values are in degrees and meters in the Blender3D editor
							put("WheelLRot",new Vec3(0F,0F,0F)); //wheel default rotation
							put("WheelL_1_Offset",new Vec3(-0.929715F,0.309341F,1.14454F)); //position of the first wheel (usually front) on the left looking at the car from the back
							put("WheelL_2_Offset",new Vec3(-0.933879F,0.318301F,-1.40514F)); //same as above but for second on same side
						}
					})
					.setName("Lamborghini Gallardo")
				);
			
			//Lamborghini Hurrican
			put(9,
					new CarType("lamborghini_hurrican_body","lamborghini_hurrican_color","lamborghini_hurrican_wheel","high",//obj models
							null,null)//block model
					.setAcceleration((62-0)/ /*X Seconds:*/ 3.6F) //0-62mph in X seconds.
					.setRollFrictionMultiplier(2) // 1 - infinity slipperiness. decimal = constantly braking
					.setDriftMultiplier(1F)//bigger means more drift. 15 is general max but it can go higher with not ideal results. (higher than 15 not tested)
					.setMaxSpeed(199F)//in miles per hour
					.setPassengerOffsets(//in order from driver to first passenger to last passenger
							new Vec3[] {//this also determines the max passengers.
									new Vec3(-0.380851,0.21905,0.192779),new Vec3(0.380851,0.21905,0.192779)
								}
							
							)
					.setWheelOffsets(new HashMap<String,Vec3>(){private static final long serialVersionUID = -6323696599663271312L;
						{
							//this is where the model values go
							//all values are in degrees and meters in the Blender3D editor
							put("WheelLRot",new Vec3(0F,0F,0F)); //wheel default rotation
							put("WheelL_1_Offset",new Vec3(-0.960377F,0.323335F,1.45604F)); //position of the first wheel (usually front) on the left looking at the car from the back
							put("WheelL_2_Offset",new Vec3(-0.967583F,0.323335F,-1.32854F)); //same as above but for second on same side
						}
					})
					.setName("Lamborghini Hurrican")
				);
			
			
			//Ford F150 [year 2007?]
			put(10,
					new CarType("ford_f150_body","ford_f150_color","ford_f150_wheel","high",//obj models
							null,null)//block model
					.setAcceleration((60-0)/ /*X Seconds:*/ 6.6F) //0-60mph in X seconds.
					.setRollFrictionMultiplier(2) // 1 - infinity slipperiness. decimal = constantly braking
					.setDriftMultiplier(1.3F)//bigger means more drift. 15 is general max but it can go higher with not ideal results. (higher than 15 not tested)
					.setMaxSpeed(120F)//in miles per hour
					.setPassengerOffsets(//in order from driver to first passenger to last passenger
							new Vec3[] {//this also determines the max passengers.
									new Vec3(-0.418826,0.264139,0.747997),new Vec3(0.418826,0.264139,0.747997),
									new Vec3(-0.418826,-0.801511,0.747997),new Vec3(0.418826,-0.801511,0.747997), /*THIS IS THE MIDDLE SEAT:*/new Vec3(0,-0.801511,0.747997)
								}
							
							)
					.setWheelOffsets(new HashMap<String,Vec3>(){private static final long serialVersionUID = -6323696599663271312L;
						{
							//this is where the model values go
							//all values are in degrees and meters in the Blender3D editor
							put("WheelLRot",new Vec3(0F,0F,0F)); //wheel default rotation
							put("WheelL_1_Offset",new Vec3(-0.925634,0.400239,1.72902)); //position of the first wheel (usually front) on the left looking at the car from the back
							put("WheelL_2_Offset",new Vec3(-0.925634,0.400239,-1.84246)); //same as above but for second on same side
						}
					})
					.setName("Ford F150")
				);
			
			
			//Ferrari F50 
			put(11,
					new CarType("ferrari_f50_body","ferrari_f50_color","ferrari_f50_wheel","high",//obj models
							null,null)//block model
					.setAcceleration((60-0)/ /*X Seconds:*/ 3.7F) //0-60mph in X seconds.
					.setRollFrictionMultiplier(2) // 1 - infinity slipperiness. decimal = constantly braking
					.setDriftMultiplier(1.5F)//bigger means more drift. 15 is general max but it can go higher with not ideal results. (higher than 15 not tested)
					.setMaxSpeed(202F)//in miles per hour
					.setPassengerOffsets(//in order from driver to first passenger to last passenger
							new Vec3[] {//this also determines the max passengers.
									new Vec3(-0.302645,0.079437,0.266015),new Vec3(0.302645,0.079437,0.266015)
								}
							
							)
					.setWheelOffsets(new HashMap<String,Vec3>(){private static final long serialVersionUID = -6323696599663271312L;
						{
							//this is where the model values go
							//all values are in degrees and meters in the Blender3D editor
							put("WheelLRot",new Vec3(0F,0F,0F)); //wheel default rotation
							put("WheelL_1_Offset",new Vec3(-0.947461,0.316172,1.19942)); //position of the first wheel (usually front) on the left looking at the car from the back
							put("WheelL_2_Offset",new Vec3(-0.990188,0.341066,-1.37755)); //same as above but for second on same side
						}
					})
					.setName("Ferrari F50")
				);
		}//types stop here.
	}; 
	
	
	
	/*
	HOW TO MAKE CAR TYPES!!!!
		
		===============================
		String Name = "examplemodel"; //"Name" is referenced below which represents the model name. could be anything lower case!
		===============================
		
		1. add a new car type to CarTypeRegistry.CarTypes with a car type index and optional custom name to the names hashmap.
			1a. put the .obj model string names in constructor. If null, skip steps 2-7. Else, "Name" is for each of the first 3 strings in the constructor.
		2. put a json file called Name.json inside blockstates like the rest of the model jsons there
		3. make a json file inside the resources: models/block/Name.Json and add the obj model inside with Name. Textures inside don't matter
		4. make a .obj file inside the resources: models/obj/Name.obj
		5. edit the .mtl to have map_Kd followingcarmod:blocks/cartypes/Name at the end.
		6. edit the CarType to include the seat positions and the wheel positions on the model
		
	 	7. repeat steps 2-5 but with "High" on the end of Name.
	 	
	 	8. if you have a block model follow steps below. if not, you're done!
	 	
	 	9. add a new Model class to followingcarvariants package with code following the other ones. use a generator like Tabula for this.
	 	 9a. change the string at the beginning of each .addbox() in the add model layer to the car type number in quotes. (Ex: "0" for default car type)
	 	10. add a new instance of the model class to the hashmap in following car model. index is the car type.
	 	 10a. also add the static addModelLayer() to the createBodyMesh() method in FollowingCarModel
	 	
	 	12. Horray!!! All things needed. Go to the next one!
	
	*/
	
	
	//determines the scale for all cars. If this becomes different on the client due to another mod changing it client side only, expect visual bugs!
	public static final double CarScale = 1.5;
	
	//custom names to make a car type appear if it is named this. It can also become one of these
	//if a conversion kit is used.
	
	public static final HashMap<String,Integer> NameToVariant = new HashMap<String,Integer>(){
		private static final long serialVersionUID = -119518255187550602L;
		{
			put("Kodachi",1);
			put("Fumei",2);
			put("Amari",3);
			put("Jane",3);
			put("Mazzy",4);
			put("TIFF",8);
			put("TIOS",9);
		}
	};
	
	
	
	
	
	
}
