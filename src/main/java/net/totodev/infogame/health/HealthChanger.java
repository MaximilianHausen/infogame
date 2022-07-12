package net.totodev.infogame.health;

import net.totodev.infoengine.ecs.*;
import net.totodev.infogame.player.PlayerFlag;

public class HealthChanger extends BaseSystem {
    @CachedComponent
    private Health health;

    int iFrames = 0;

    /**
     * Prüft, ob es sich um einen Player oder Enemy handelt: Ändert die health des Spielers um -1, gibt es an das Event HealthChanged weiter (für HealthAnimator)
     * @param layer
     * @param hurtId: Eingebener Player
     * @param hurtType
     * @param damageId: Eingegebener Enemy
     * @param damageType
     */
    @EventSubscriber("PhysStay")
    public void decreaseHealth(int layer, int hurtId, int hurtType, int damageId, int damageType) {
        // 11 = hurtbox, 10 = hitbox
        if (hurtType != 11 || damageType != 10) return;

        if (iFrames > 0) {
            iFrames--;
            return;
        }

        int healthId = getScene().getEntitiesByComponents(Health.class).getFirst();
        health.changeHealth(healthId, -1);
        getScene().events.invokeEvent(HealthEvents.HealthChanged, healthId, health.getHealth(healthId)); // Listener: HealthAnimator

        iFrames = 60;
    }
}
