package net.totodev.infogame.player.states;

import net.totodev.infoengine.ecs.Scene;
import net.totodev.infoengine.rendering.Sprite2d;
import net.totodev.infoengine.resources.ResourceManager;
import net.totodev.infogame.player.*;
import net.totodev.infogame.statemachine.State;
import org.joml.Vector2f;

import java.util.Arrays;

// Ausgangsstate der Bewegung
public class Idle extends State {
    // Targets erstellen (states für aktionen)
    String[] targets = new String[]{"walking", "attack"};

    Animation down = new Animation(
            ResourceManager.getImage("textures/character/idle_down_0.png"),
            ResourceManager.getImage("textures/character/idle_down_1.png"),
            ResourceManager.getImage("textures/character/idle_down_2.png"));
    Animation up = new Animation(
            ResourceManager.getImage("textures/character/idle_up_0.png"),
            ResourceManager.getImage("textures/character/idle_up_1.png"),
            ResourceManager.getImage("textures/character/idle_up_2.png"));
    Animation left = new Animation(
            ResourceManager.getImage("textures/character/idle_left_0.png"),
            ResourceManager.getImage("textures/character/idle_left_1.png"),
            ResourceManager.getImage("textures/character/idle_left_2.png"));
    Animation right = new Animation(
            ResourceManager.getImage("textures/character/idle_right_0.png"),
            ResourceManager.getImage("textures/character/idle_right_1.png"),
            ResourceManager.getImage("textures/character/idle_right_2.png"));

    private int cooldown = 0;

    // updater für die Bewegungen
    @Override
    public void update(Scene scene, int entity) {
        if (cooldown >= 0) {
            cooldown--;
            return;
        }

        Vector2f lookDir = scene.getComponent(LookDir.class).getLookDir(entity);
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

        cooldown = 15;
    }

    // Änderung für Bewegungsstates
    @Override
    public boolean canChangeTo(State state) {
        return Arrays.stream(targets).anyMatch(n -> state.getName().equals(n));
    }

    // Ausgabe für den aktuellen State
    @Override
    public String getName() {
        return "idle";
    }
}
