package net.totodev.infogame.player.states;

import net.totodev.infoengine.core.components.Transform2d;
import net.totodev.infoengine.ecs.Scene;
import net.totodev.infoengine.rendering.*;
import net.totodev.infoengine.resources.ResourceManager;
import net.totodev.infogame.DirectionalInput;
import net.totodev.infogame.player.*;
import net.totodev.infogame.statemachine.State;
import org.joml.Vector2f;

import java.util.Arrays;

import static org.lwjgl.glfw.GLFW.*;
// Walkingstate
//Funktioniert für alle Bewegungsrichtungen
public class Walking extends State {
    String[] targets = new String[]{"idle", "attack"};

    private final DirectionalInput input = new DirectionalInput(GLFW_KEY_UP, GLFW_KEY_RIGHT, GLFW_KEY_DOWN, GLFW_KEY_LEFT);

    Animation down = new Animation(
            ResourceManager.getImage("textures/character/move_down_0.png"),
            ResourceManager.getImage("textures/character/move_down_1.png"),
            ResourceManager.getImage("textures/character/move_down_2.png"),
            ResourceManager.getImage("textures/character/move_down_3.png"),
            ResourceManager.getImage("textures/character/move_down_4.png"),
            ResourceManager.getImage("textures/character/move_down_5.png"));
    Animation up = new Animation(
            ResourceManager.getImage("textures/character/move_up_0.png"),
            ResourceManager.getImage("textures/character/move_up_1.png"),
            ResourceManager.getImage("textures/character/move_up_2.png"),
            ResourceManager.getImage("textures/character/move_up_3.png"),
            ResourceManager.getImage("textures/character/move_up_4.png"),
            ResourceManager.getImage("textures/character/move_up_5.png"));
    Animation left = new Animation(
            ResourceManager.getImage("textures/character/move_down_0.png"),
            ResourceManager.getImage("textures/character/move_down_1.png"),
            ResourceManager.getImage("textures/character/move_down_2.png"),
            ResourceManager.getImage("textures/character/move_down_3.png"),
            ResourceManager.getImage("textures/character/move_down_4.png"),
            ResourceManager.getImage("textures/character/move_down_5.png"));
    Animation right = new Animation(
            ResourceManager.getImage("textures/character/move_down_0.png"),
            ResourceManager.getImage("textures/character/move_down_1.png"),
            ResourceManager.getImage("textures/character/move_down_2.png"),
            ResourceManager.getImage("textures/character/move_down_3.png"),
            ResourceManager.getImage("textures/character/move_down_4.png"),
            ResourceManager.getImage("textures/character/move_down_5.png"));

    @Override
    public void update(Scene scene, int entity) {
        //region Movement
        LookDir lookDirC = scene.getComponent(LookDir.class);
        Speed speedC = scene.getComponent(Speed.class);
        Vector2f speed = speedC.getSpeed(entity);
        Transform2d transform = scene.getComponent(Transform2d.class);

        Vector2f input = this.input.poll();

        if (input.equals(0, 0)) {
            speed.mul(0.8f);

            if (speed.length() < 0.1f) {
                speed.set(0, 0);
            }
        } else {
            speed.add(input.mul(0.3f));

            if (speed.length() > 1) {
                speed.normalize();
            }
        }

        if (input.length() > 0) {
            lookDirC.setLookDir(entity, input);
        }
        Vector2f lookDir = lookDirC.getLookDir(entity);

        scene.getEntitiesByComponents(PlayerFlag.class, Transform2d.class)
                .forEach(e -> transform.move(e, speed.x / 2, speed.y / 2));

        scene.getEntitiesByComponents(Camera2d.class, Transform2d.class)
                .forEach(e -> transform.setPosition(e, transform.getPosition(e, new Vector2f()).lerp(transform.getPosition(1, new Vector2f()), 0.05f)));
        //endregion

        Sprite2d sprite = scene.getComponent(Sprite2d.class);

        if (lookDir.dot(new Vector2f(0, -1)) < 0.5) sprite.setSprite(entity, down.nextImage());
        else if (lookDir.dot(new Vector2f(0, 1)) < 0.5) sprite.setSprite(entity, up.nextImage());
        else if (lookDir.dot(new Vector2f(-1, 0)) < 0.5) sprite.setSprite(entity, left.nextImage());
        else if (lookDir.dot(new Vector2f(1, 0)) < 0.5) sprite.setSprite(entity, right.nextImage());



        // Animations
    }

    @Override
    public boolean canChangeTo(State state) {
        return Arrays.stream(targets).anyMatch(n -> state.getName().equals(n));
    }
    @Override
    public String getName() {
        return "walking";
    }
}
