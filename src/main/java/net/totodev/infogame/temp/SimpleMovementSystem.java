package net.totodev.infogame.temp;

import net.totodev.infoengine.core.CoreEvents;
import net.totodev.infoengine.core.components.Transform2d;
import net.totodev.infoengine.ecs.*;
import net.totodev.infoengine.rendering.Camera2d;
import net.totodev.infogame.DirectionalInput;
import net.totodev.infogame.player.PlayerFlag;
import org.joml.Vector2f;

import static org.lwjgl.glfw.GLFW.*;

public class SimpleMovementSystem extends BaseSystem {
    private final DirectionalInput input = new DirectionalInput(GLFW_KEY_UP, GLFW_KEY_RIGHT, GLFW_KEY_DOWN, GLFW_KEY_LEFT);
    private Vector2f speed = new Vector2f();
    private Vector2f lookDir = new Vector2f();

    @CachedComponent
    private Transform2d transform;

    @EventSubscriber(CoreEvents.Update)
    public void update(float deltaTime) {
        Vector2f input = this.input.poll();

        if (input.equals(0, 0)) {
            speed = speed.mul(0.8f);

            if (speed.length() < 0.1f) {
                speed.set(0, 0);
            }
        } else {
            speed.add(input.mul(0.3f));

            if (speed.length() > 1) {
                speed = speed.normalize();
            }
        }

        if (input.length() > 0) {
            lookDir = input;
        }

        getScene().getEntitiesByComponents(PlayerFlag.class, Transform2d.class)
                .forEach(e -> transform.move(e, speed.x * deltaTime * 10, speed.y * deltaTime * 10));

        getScene().getEntitiesByComponents(Camera2d.class, Transform2d.class)
                .forEach(e -> transform.setPosition(e, transform.getPosition(e, new Vector2f()).lerp(transform.getPosition(1, new Vector2f()), 0.05f)));
    }
}
