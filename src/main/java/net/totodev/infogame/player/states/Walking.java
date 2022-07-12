package net.totodev.infogame.player.states;

import net.totodev.infoengine.core.components.Transform2d;
import net.totodev.infoengine.ecs.Scene;
import net.totodev.infoengine.physics.Velocity2d;
import net.totodev.infoengine.rendering.*;
import net.totodev.infoengine.resources.ResourceManager;
import net.totodev.infogame.DirectionalInput;
import net.totodev.infogame.player.*;
import net.totodev.infogame.statemachine.State;
import org.joml.Vector2f;

import java.util.Arrays;

import static org.lwjgl.glfw.GLFW.*;

//Walkingstate
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
            ResourceManager.getImage("textures/character/move_left_0.png"),
            ResourceManager.getImage("textures/character/move_left_1.png"),
            ResourceManager.getImage("textures/character/move_left_2.png"),
            ResourceManager.getImage("textures/character/move_left_3.png"),
            ResourceManager.getImage("textures/character/move_left_4.png"));
    Animation right = new Animation(
            ResourceManager.getImage("textures/character/move_right_0.png"),
            ResourceManager.getImage("textures/character/move_right_1.png"),
            ResourceManager.getImage("textures/character/move_right_2.png"),
            ResourceManager.getImage("textures/character/move_right_3.png"),
            ResourceManager.getImage("textures/character/move_right_4.png"));

    private int cooldown = 0;

    @Override
    public void update(Scene scene, int entity) {
        //region Movement
        LookDir lookDirC = scene.getComponent(LookDir.class);
        Velocity2d velocityC = scene.getComponent(Velocity2d.class);
        Vector2f velocity = velocityC.getVelocity(entity, new Vector2f());
        Transform2d transform = scene.getComponent(Transform2d.class);

        Vector2f input = this.input.poll();

        // Das ist recht verwirrend, weil sowohl die vorher abgerufene velocity als auch der Component velocityC verwendet werden.
        // Die Vektor-Rechenoperationen verändern die eigene Instanz und geben sich selbst zurück, velocity wird also auch aktuallisiert.
        if (input.equals(0, 0)) {
            velocityC.setVelocity(entity, velocity.mul(0.8f));

            if (velocity.length() < 0.1f)
                velocityC.setVelocity(entity, 0, 0);
        } else {
            velocityC.setVelocity(entity, velocity.add(input));

            if (velocity.length() > 10)
                velocityC.setVelocity(entity, velocity.normalize(10));
        }

        if (input.length() > 0) lookDirC.setLookDir(entity, input);
        Vector2f lookDir = lookDirC.getLookDir(entity);

        scene.getEntitiesByComponents(Camera2d.class, Transform2d.class)
                .forEach(e -> transform.setPosition(e, transform.getPosition(e, new Vector2f()).lerp(transform.getPosition(1, new Vector2f()), 0.05f)));
        //endregion

        if (cooldown >= 0) {
            cooldown--;
            return;
        }

        Sprite2d sprite = scene.getComponent(Sprite2d.class);

        float upDot, rightDot, downDot, leftDot;
        upDot = lookDir.dot(new Vector2f(0, 1));
        rightDot = lookDir.dot(new Vector2f(1, 0));
        downDot = lookDir.dot(new Vector2f(0, -1));
        leftDot = lookDir.dot(new Vector2f(-1, 0));

        if (upDot >= rightDot && upDot >= downDot && upDot >= leftDot)
            sprite.setSprite(entity, up.nextImage());
        else if (downDot >= upDot && downDot >= rightDot && downDot >= leftDot)
            sprite.setSprite(entity, down.nextImage());
        else if (rightDot >= upDot && rightDot >= downDot && upDot >= leftDot)
            sprite.setSprite(entity, right.nextImage());
        else
            sprite.setSprite(entity, left.nextImage());

        cooldown = 10;
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
