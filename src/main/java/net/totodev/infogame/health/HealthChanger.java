package net.totodev.infogame.health;

import net.totodev.infoengine.ecs.*;
import net.totodev.infogame.player.PlayerFlag;

public class HealthChanger extends BaseSystem {
    @CachedComponent
    private Health health;
    @CachedComponent
    private PlayerFlag playerFlag;

    @EventSubscriber("PhysStay")
    public void decreaseHealth(int layer, int playerId, int playerType, int enemyId, int enemyType) {
        if (!playerFlag.isPresentOn(playerId)) return;
        // if (!enemyFlag.isPresentOn(enemyId)) return; TODO: Component enemy
        health.changeHealth(playerId, -1);
        getScene().events.invokeEvent(HealthEvents.HealthChanged, getScene().getEntitiesByComponents(Health.class).getFirst(), -1);
    }
}
