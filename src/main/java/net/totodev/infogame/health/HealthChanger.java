package net.totodev.infogame.health;

import net.totodev.infoengine.ecs.*;
import net.totodev.infogame.player.PlayerFlag;

public class HealthChanger extends BaseSystem {
    @CachedComponent
    private Health health;
    @CachedComponent
    private PlayerFlag playerFlag;

    /**
     * prüft ob es sich um einen Player oder Enemy handelt: Ändert die health des Spielers um -1, gibt es an das Event HealthChanged weiter (für HealthAnimator)
     * @param layer
     * @param playerId: Eingebener Player
     * @param playerType
     * @param enemyId: Eingegebener Enemy
     * @param enemyType
     */
    @EventSubscriber("PhysStay")
    public void decreaseHealth(int layer, int playerId, int playerType, int enemyId, int enemyType) {
        if (!playerFlag.isPresentOn(playerId)) return;
        // if (!enemyFlag.isPresentOn(enemyId)) return; TODO: Component enemy
        health.changeHealth(playerId, -1);
        getScene().events.invokeEvent(HealthEvents.HealthChanged, getScene().getEntitiesByComponents(Health.class).getFirst(), -1);
    }
}
