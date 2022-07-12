package net.totodev.infogame.health;

import net.totodev.infoengine.ecs.*;
import net.totodev.infoengine.rendering.Sprite2d;
import net.totodev.infoengine.resources.ResourceManager;

public class HealthAnimator extends BaseSystem {

    // Component für Images
    @CachedComponent
    private Sprite2d sprite;

    /**
     * Wenn Event HealthChanged eintritt: Änderung des Images zur neuen Health (Bilder sind nummeriert)
     * @param entityId: Eingebene Entity
     * @param newHealth: Neue eingegebene Health
     */
    @EventSubscriber(HealthEvents.HealthChanged)
    public void updateImage(int entityId, int newHealth) {
        sprite.setSprite(entityId, ResourceManager.getImage("textures/healthbar/" + newHealth + ".png"));
    }
}
