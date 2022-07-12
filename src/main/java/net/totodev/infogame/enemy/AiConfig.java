package net.totodev.infogame.enemy;

import net.totodev.infoengine.ecs.Component;
import net.totodev.infoengine.resources.scene.ComponentDataModel;
import org.eclipse.collections.api.map.primitive.*;
import org.eclipse.collections.impl.factory.primitive.*;
import org.jetbrains.annotations.*;

public class AiConfig implements Component {
    private final MutableIntFloatMap speeds = IntFloatMaps.mutable.empty();
    private final MutableIntIntMap targets = IntIntMaps.mutable.empty();

    //region Speed
    public float getSpeed(int entityId) {
        return speeds.get(entityId);
    }

    public void setSpeed(int entityId, float speed) {
        speeds.put(entityId, speed);
    }
    //endregion

    //region Target
    public int getTarget(int entityId) {
        return targets.get(entityId);
    }

    public void setTarget(int entityId, int target) {
        targets.put(entityId, target);
    }
    //endregion

    @Override
    public void resetEntity(int entityId) {
        speeds.remove(entityId);
    }

    @Override
    public void deserializeState(@NotNull ComponentDataModel data) {
        String[] splitValue = data.value.split("\\|");
        setSpeed(data.entity, Float.parseFloat(splitValue[0]));
        setTarget(data.entity, Integer.parseInt(splitValue[1]));
    }
    @Override
    public @Nullable String serializeState(int entityId) {
        if (!isPresentOn(entityId)) return null;
        return speeds.get(entityId) + "|" + targets.get(entityId);
    }

    @Override
    public boolean isPresentOn(int entityId) {
        return speeds.containsKey(entityId) && targets.containsKey(entityId);
    }
}
