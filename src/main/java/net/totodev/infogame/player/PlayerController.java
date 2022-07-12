package net.totodev.infogame.player;

import net.totodev.infoengine.core.CoreEvents;
import net.totodev.infoengine.ecs.*;
import net.totodev.infogame.player.states.*;
import net.totodev.infogame.statemachine.StateMachine;

public class PlayerController extends BaseSystem {
    private final StateMachine stateMachine = new StateMachine(new Idle());

    @CachedComponent
    private Speed speed;

    @EventSubscriber(CoreEvents.Update)
    public void update(float deltaTime) {
        int player = getScene().getEntitiesByComponents(PlayerFlag.class).getFirst();
        stateMachine.updateCurrentState(getScene(), player);

        if (getScene().getComponent(Speed.class).getSpeed(player).length() > 0 && stateMachine.getCurrentState() instanceof Idle) stateMachine.changeState(new Walking());
        else if (getScene().getComponent(Speed.class).getSpeed(player).length() == 0 && stateMachine.getCurrentState() instanceof Walking) stateMachine.changeState(new Idle());
    }
}
