package net.totodev.infogame.statemachine;

import net.totodev.infoengine.ecs.Scene;
// Abstract class for Methods
public abstract class State {
    public StateMachine currentMachine;

    public abstract void update(Scene scene, int entity);

    public abstract boolean canChangeTo(State state);

    public abstract String getName();
}
