package net.glowstone.entity.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.systems.EntityProcessingSystem;
import net.glowstone.constants.GlowPotionEffect;
import net.glowstone.entity.GlowLivingEntity;
import net.glowstone.entity.components.GlowEntityComponent;
import net.glowstone.entity.components.PotionEffectsComponent;
import org.bukkit.potion.PotionEffect;

import java.util.ArrayList;
import java.util.List;

@Wire
public class PotionEffectSystem extends EntityProcessingSystem {

    private ComponentMapper<PotionEffectsComponent> potionEffectsMapper;
    private ComponentMapper<GlowEntityComponent> glowEntityMapper;

    public PotionEffectSystem() {
        super(Aspect.getAspectForAll(PotionEffectsComponent.class, GlowEntityComponent.class));
    }

    @Override
    protected void process(Entity e) {
        PotionEffectsComponent potionEffectsComponent = potionEffectsMapper.get(e);
        GlowLivingEntity entity = (GlowLivingEntity) glowEntityMapper.get(e).getEntity();

        // potion effects
        List<PotionEffect> effects = new ArrayList<>(potionEffectsComponent.getPotionEffects().values());
        for (PotionEffect effect : effects) {
            // pulse effect
            GlowPotionEffect type = (GlowPotionEffect) effect.getType();
            type.pulse(entity, effect);

            if (effect.getDuration() > 0) {
                // reduce duration and re-add
                entity.addPotionEffect(new PotionEffect(type, effect.getDuration() - 1, effect.getAmplifier(), effect.isAmbient()), true);
            } else {
                // remove
                entity.removePotionEffect(type);
            }
        }
    }
}
