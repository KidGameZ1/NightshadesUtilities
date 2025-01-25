package net.nightshade.nightshade_core.attributes;

import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraftforge.event.entity.EntityAttributeModificationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class NightshadeAttributeRegistry {
    private static final DeferredRegister<Attribute> registry;
    public static RegistryObject<Attribute> SIZE;
    public static final RegistryObject<Attribute> SPRINTING_SPEED_MULTIPLIER;

    public NightshadeAttributeRegistry() {
    }
    private static void applyAttributesToEntities(EntityAttributeModificationEvent e) {
        e.getTypes().forEach((type) -> {
            e.add(type, (Attribute)SIZE.get());
            e.add(type, (Attribute)SPRINTING_SPEED_MULTIPLIER.get());
        });
    }
    public static void init(IEventBus modEventBus) {
        registry.register(modEventBus);
        modEventBus.addListener(NightshadeAttributeRegistry::applyAttributesToEntities);
    }

    static {
        registry = DeferredRegister.create(ForgeRegistries.ATTRIBUTES, "nightshade_core");
        SIZE = registry.register("size", () -> (new SizeAttribute("nightshade_core.attribute.size.name", (double)1.0F, (double)0.0F, (double)1000.0F)).setSyncable(true));
        SPRINTING_SPEED_MULTIPLIER = registry.register("sprinting_speed_multiplier", () -> (new RangedAttribute("nightshade_core.attribute.sprinting_speed.name", (double)1.0F, (double)0.0F, (double)1024.0F)).setSyncable(true));
    }
}