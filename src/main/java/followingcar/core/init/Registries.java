package followingcar.core.init;
import net.minecraft.world.item.Item;

import java.util.function.Supplier;

import javax.annotation.Nonnull;

import followingcar.MainFollowingCar;
import followingcar.common.items.itemsmaster;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
public class Registries {
	//Thanks to Aquaculture!! https://github.com/TeamMetallurgy/Aquaculture/blob/master/src/main/java/com/teammetallurgy/aquaculture
	public static final DeferredRegister<Item> ITEM_DEFERRED = DeferredRegister.create(ForgeRegistries.ITEMS, MainFollowingCar.MODID);
	
	
	public static final RegistryObject<Item> GASCAN = register(
				"gascan", () -> itemsmaster.GASCAN);
	public static final RegistryObject<Item> SOUL	=	register(
				"soul", () -> itemsmaster.SOUL);
	public static final RegistryObject<Item> WHEEL	=	register(
				"wheel", () -> itemsmaster.WHEEL);
	public static final RegistryObject<Item> acid	=	register(
				"acid", () -> itemsmaster.acid);
	public static final RegistryObject<Item> Alive_Engine	=	register(
				"alive_engine", () -> itemsmaster.Alive_Engine);
	public static final RegistryObject<Item> battery	=	register(
				"battery", () -> itemsmaster.battery);
	public static final RegistryObject<Item> CarChanger	=	register(
				"car_changer", () -> itemsmaster.CarChanger);
	public static final RegistryObject<Item> Dead_Engine	=	register(
				"dead_engine", () -> itemsmaster.Dead_Engine);
	public static final RegistryObject<Item> Piston	=	register(
				"piston", () -> itemsmaster.Piston);
	public static final RegistryObject<Item> Piston_Block	=	register(
				"piston_engine_block", () -> itemsmaster.Piston_Block);
	public static final RegistryObject<Item> wire	= register(
				"wire", () -> itemsmaster.wire);
	public static final RegistryObject<Item> DyeBundle = register(
				"dye_bundle", () ->  itemsmaster.DyeBundle);
	
	public static RegistryObject<Item> register(@Nonnull String name,@Nonnull Supplier<Item> initializer) {
        return ITEM_DEFERRED.register(name, initializer);
    }
}
