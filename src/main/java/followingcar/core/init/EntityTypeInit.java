package followingcar.core.init;

import java.util.function.Supplier;

import followingcar.MainFollowingCar;

import followingcar.common.entities.FollowingCar;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
//import net.minecraftforge.fml.RegistryObject;

public class EntityTypeInit {
	
	//register our Entities
	//public static final EntityType<FollowingCar> FOLLOWING_CAR = (EntityType<FollowingCar>) EntityType.Builder.of(FollowingCar::new,MobCategory.CREATURE).sized(1.4F, 2F).build(MainFollowingCar.MODID+":following_car");
	
	
	//Thanks to Aquaculture!! https://github.com/TeamMetallurgy/Aquaculture/blob/master/src/main/java/com/teammetallurgy/aquaculture
	public static final DeferredRegister<EntityType<?>> ENTITY_DEFERRED = DeferredRegister.create(ForgeRegistries.ENTITIES, MainFollowingCar.MODID);
	
	
    public static final RegistryObject<EntityType<FollowingCar>> FOLLOWING_CAR = registerMob("following_car", 0x7F8439, 0x5D612A,
            () -> EntityType.Builder.of(FollowingCar::new, MobCategory.CREATURE)
                    .sized(1.4F, 2F));

    private static <T extends Mob> RegistryObject<EntityType<T>> registerMob(String name, int eggPrimary, int eggSecondary, Supplier<EntityType.Builder<T>> builder) {
        RegistryObject<EntityType<T>> entityType = register(name, builder);
        Registries.register("followingcar_spawnegg",() -> new ForgeSpawnEggItem(entityType, eggPrimary, eggSecondary, (new Item.Properties()).tab(CreativeModeTab.TAB_MISC)));
        return entityType;
    }
    
    public static <T extends Entity> RegistryObject<EntityType<T>> register(String name, Supplier<EntityType.Builder<T>> builder) {
        ResourceLocation location = MainFollowingCar.Location(name);
        return ENTITY_DEFERRED.register(name, () -> builder.get().build(location.toString()));
    }
	
	
	
	
}
