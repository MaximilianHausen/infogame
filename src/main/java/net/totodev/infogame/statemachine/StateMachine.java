package net.totodev.infogame.statemachine;

import net.totodev.infoengine.ecs.Scene;

public class StateMachine {
    private State currentState;

    public StateMachine(State initialState) {
        currentState = initialState;
    }

    public void updateCurrentState(Scene scene, int entity) {
        if (currentState != null) currentState.update(scene, entity);
    }

    public void changeState(State targetState) {
        if (!currentState.canChangeTo(targetState)) return;
        currentState.currentMachine = null;
        currentState = targetState;
        currentState.currentMachine = this;
    }

    public State getCurrentState() {
        return currentState;
    }
}
