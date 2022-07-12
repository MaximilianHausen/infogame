package net.totodev.infogame.health;

import net.totodev.infoengine.core.CoreEvents;
import net.totodev.infoengine.ecs.*;

public class HealthFiller extends BaseSystem {
    @CachedComponent
    private Health health;

    int cooldown = 0;

    @EventSubscriber(CoreEvents.Update)
    public void update(float deltaTime) {
        if (cooldown >= 0) {
            cooldown--;
            return;
        }

        int healthId = getScene().getEntitiesByComponents(Health.class).getFirst();
        health.changeHealth(healthId, 1);
        getScene().events.invokeEvent(HealthEvents.HealthChanged, healthId, health.getHealth(healthId));

        cooldown = 300;
    }
}
