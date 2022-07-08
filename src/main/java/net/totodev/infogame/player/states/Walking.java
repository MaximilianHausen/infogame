package net.totodev.infogame.player.states;

import net.totodev.infoengine.ecs.Scene;
import net.totodev.infogame.statemachine.State;

import java.util.Arrays;

public class Walking extends State {
    String[] targets = new String[]{"idle", "attack"};

    @Override
    public void update(Scene scene, int entity){
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
