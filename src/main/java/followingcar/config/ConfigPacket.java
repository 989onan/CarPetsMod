package followingcar.config;

import java.util.function.Supplier;

import followingcar.MainFollowingCar;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkEvent.Context;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class ConfigPacket {
	private static final String CHANNEL_NAME = "main";
	private static final String PROTOCOL_VERSION = "1";
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

		boolean userealsize;

		public MyPacket(boolean userealsize) {
			this.userealsize = userealsize;
		}
		
		public static void encode(MyPacket packet, FriendlyByteBuf buf) {
			buf.writeBoolean(packet.userealsize);
		}

		public static MyPacket decode(FriendlyByteBuf buf) {
			return new MyPacket(buf.readBoolean());
		}

		public static void handle(MyPacket msg, Supplier<Context> ctx) {
			ctx.get().enqueueWork(()-> {
				MainFollowingCar.LOGGER.info("Received Server Settings!");
				FollowingCarConfig.userealsize.set(msg.userealsize);
			});
		}
	}
}
