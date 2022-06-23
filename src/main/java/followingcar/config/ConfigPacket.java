package followingcar.config;

import java.util.function.Supplier;

import followingcar.MainFollowingCar;
import followingcar.core.init.CarTypeRegistry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkEvent.Context;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class ConfigPacket {
	private static final String CHANNEL_NAME = "main";
	private static final String PROTOCOL_VERSION = "2";
	public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
		new ResourceLocation(MainFollowingCar.MODID, CHANNEL_NAME),
		() -> PROTOCOL_VERSION,
		PROTOCOL_VERSION::equals,
		PROTOCOL_VERSION::equals
	);
	
	public static void register() {
		int id = 0;
		INSTANCE.registerMessage(
			id++,
			MyPacket.class,
			MyPacket::encode,
			MyPacket::decode,
			MyPacket::handle
		);
	}
	
	
	public static class MyPacket {

		double carsize;

		public MyPacket(double userealsize) {
			this.carsize = userealsize;
		}
		
		public static void encode(MyPacket packet, FriendlyByteBuf buf) {
			buf.writeDouble(packet.carsize);
		}

		public static MyPacket decode(FriendlyByteBuf buf) {
			return new MyPacket(buf.readDouble());
		}

		public static void handle(MyPacket msg, Supplier<Context> ctx) {
			ctx.get().enqueueWork(() -> {
				MainFollowingCar.LOGGER.info("Received Server Settings!");
				CarTypeRegistry.CarScale = msg.carsize;
			});
		}
	}
}
