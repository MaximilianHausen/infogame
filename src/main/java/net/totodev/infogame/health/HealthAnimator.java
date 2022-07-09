package net.totodev.infogame.health;

import net.totodev.infoengine.ecs.*;
import net.totodev.infoengine.rendering.Sprite2d;
import net.totodev.infoengine.resources.ResourceManager;

public class HealthAnimator extends BaseSystem {
    @CachedComponent
    private Sprite2d sprite;

    @EventSubscriber(HealthEvents.HealthChanged)
    public void updateImage(int entityId, int newHealth) {
        sprite.setSprite(entityId, ResourceManager.getImage("textures/healthbar/" + newHealth + ".png"));
    }
}
