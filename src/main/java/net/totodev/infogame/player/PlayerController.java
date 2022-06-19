package net.totodev.infogame.player;

import net.totodev.infoengine.core.CoreEvents;
import net.totodev.infoengine.ecs.*;
import net.totodev.infogame.player.states.Idle;
import net.totodev.infogame.statemachine.StateMachine;

public class PlayerController extends BaseSystem {
    private StateMachine stateMachine = new StateMachine(new Idle());

    @EventSubscriber(CoreEvents.Update)
    public void update() {
        stateMachine.updateCurrentState(getScene(), getScene().getEntitiesByComponents(PlayerComponent.class).getFirst());
    }
}
