package net.nightshade.nightshade_core.size;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.nightshade.nightshade_core.attributes.ISizeAttribute;
import net.nightshade.nightshade_core.attributes.NightshadeAttributeRegistry;
import net.nightshade.nightshade_core.attributes.NightshadeModifierIds;
import net.nightshade.nightshade_core.attributes.SizeAttribute;


public class SizeHelper {
    public static float getSkillSizeMultiplier(LivingEntity entity) {
        float size = 1.0F;
        if (entity.getAttributes() != null) {
            AttributeInstance instance = entity.getAttribute((Attribute) NightshadeAttributeRegistry.SIZE.get());
            if (instance != null) {
                size *= (float)instance.getValue();
            }
        }

        return size;
    }

    public static float getSize(LivingEntity entity) {
        float size = 1.0F;
        if (entity.getAttributes() != null) {
            AttributeInstance instance = entity.getAttribute((Attribute) NightshadeAttributeRegistry.SIZE.get());
            if (instance != null) {
                size = (float)instance.getValue();
            }
        }

        return size;
    }
    public static float getDefaultSize(LivingEntity entity) {
        float size=0f;
        if (entity.getAttributes() != null) {
            AttributeInstance instance = entity.getAttribute((Attribute) NightshadeAttributeRegistry.SIZE.get());
            if (instance != null) {
                if (instance instanceof ISizeAttribute sizeAttribute){
                    size = (float)sizeAttribute.getDefaultValue();

                }
            }
        }

        return size;
    }
    public static void setDefaultSize(LivingEntity entity, double size) {
        entity.getAttributes();
        AttributeInstance instance = entity.getAttribute((SizeAttribute) NightshadeAttributeRegistry.SIZE.get());
        ISizeAttribute sizeAttribute = (ISizeAttribute) instance.getAttribute();
        if (instance != null) {
            sizeAttribute.setDefaultValue(size);
        }

    }
    public static void setSizeAtt(LivingEntity entity, float size, AttributeModifier.Operation operation) {
        if (entity.getAttributes() != null) {
            AttributeInstance instance = entity.getAttribute((Attribute) NightshadeAttributeRegistry.SIZE.get());
            if (instance != null) {
                instance.addPermanentModifier(new AttributeModifier(NightshadeModifierIds.SIZE_MODIFIER_ID.toString(), size, operation));
            }
        }
    }

    public static void setSize(Player entity, float size) {
        if (entity.getAttributes() != null) {
            AttributeInstance instance = entity.getAttribute((Attribute) NightshadeAttributeRegistry.SIZE.get());
            if (instance != null) {
                instance.setBaseValue(size);
            }
        }
    }

    public static void resetSize(Player entity) {
        if (entity.getAttributes() != null) {
            float size = 1f;
            AttributeInstance instance = entity.getAttribute((Attribute) NightshadeAttributeRegistry.SIZE.get());
            if (instance != null) {
                if (instance instanceof ISizeAttribute sizeAttribute){
                    instance.removeModifier(NightshadeModifierIds.SIZE_MODIFIER_ID);
                    instance.setBaseValue(sizeAttribute.getDefaultValue());

                }

            }
        }
    }
}
