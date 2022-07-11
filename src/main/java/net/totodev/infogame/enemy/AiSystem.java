package net.totodev.infogame.enemy;

import net.totodev.infoengine.core.CoreEvents;
import net.totodev.infoengine.core.components.Transform2d;
import net.totodev.infoengine.ecs.*;
import net.totodev.infogame.player.PlayerFlag;
import org.joml.Vector2f;

public class AiSystem extends BaseSystem {
    @CachedComponent
    private Transform2d transform;

    @Override
    public void start(Scene scene) {
        super.start(scene);
    }

    @EventSubscriber(CoreEvents.Update)
    public void update(float deltaTime) {
        int playerId = getScene().getEntitiesByComponents(PlayerFlag.class).get(0);
        Vector2f playerPos = transform.getPosition(playerId, new Vector2f());
        getScene().getEntitiesByComponents(AiConfig.class).forEach(e -> {
            Vector2f pos = transform.getPosition(e, new Vector2f());
            transform.setPosition(e, pos.lerp(playerPos, 0.1f));
        });
    }
}
