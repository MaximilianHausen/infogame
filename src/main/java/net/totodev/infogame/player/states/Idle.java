package net.totodev.infogame.player.states;

import net.totodev.infoengine.ecs.Scene;
import net.totodev.infogame.statemachine.State;

import java.util.Arrays;

public class Idle extends State {
    String[] name = new String[]{"walking", "attack"};

    @Override
    public void update(Scene scene, int entity) {
        // Idle code hier
    }

    @Override
    public boolean canChangeTo(State state) {
        return Arrays.stream(name).anyMatch(n -> state.getName().equals(n));
    }
    @Override
    public String getName() {
        return "idle";
    }
}
