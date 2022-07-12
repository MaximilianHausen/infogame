package net.totodev.infogame.player;

import net.totodev.infoengine.core.*;
import net.totodev.infoengine.ecs.*;
import net.totodev.infoengine.physics.Velocity2d;
import net.totodev.infogame.player.states.*;
import net.totodev.infogame.statemachine.StateMachine;
import org.joml.Vector2f;

import static org.lwjgl.glfw.GLFW.*;

public class PlayerController extends BaseSystem {
    private final StateMachine stateMachine = new StateMachine(new Idle());

    @CachedComponent
    private Velocity2d velocity;

    @EventSubscriber(CoreEvents.Update)
    public void update(float deltaTime) {
        int player = getScene().getEntitiesByComponents(PlayerFlag.class).getFirst();
        stateMachine.updateCurrentState(getScene(), player);

        if (glfwGetKey(Engine.getMainWindow().getId(), GLFW_KEY_UP) != 0 || glfwGetKey(Engine.getMainWindow().getId(), GLFW_KEY_DOWN) != 0 || glfwGetKey(Engine.getMainWindow().getId(), GLFW_KEY_LEFT) != 0 || glfwGetKey(Engine.getMainWindow().getId(), GLFW_KEY_RIGHT) != 0) stateMachine.changeState(new Walking());
        else if (velocity.getVelocity(player, new Vector2f()).length() == 0 && stateMachine.getCurrentState() instanceof Walking) stateMachine.changeState(new Idle());
    }
}
