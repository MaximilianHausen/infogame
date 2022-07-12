package net.totodev.infogame.statemachine;

import net.totodev.infoengine.ecs.Scene;
// erstellen der Statemaschine für Bewegung
public class StateMachine {
    private State currentState;


    public StateMachine(State initialState) {
        currentState = initialState;
    }

// abfrage des aktuellen state
    public void updateCurrentState(Scene scene, int entity) {
        if (currentState != null) currentState.update(scene, entity);
    }

    // methode zum wechseln der States
    public void changeState(State targetState) {
        if (!currentState.canChangeTo(targetState)) return;
        currentState.currentMachine = null;
        currentState = targetState;
        currentState.currentMachine = this;
    }

    // Ausgabe des aktuellen states
    public State getCurrentState() {
        return currentState;
    }
}
