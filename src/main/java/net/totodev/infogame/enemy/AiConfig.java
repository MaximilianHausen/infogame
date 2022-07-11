package net.totodev.infogame.enemy;

import net.totodev.infoengine.ecs.Component;
import net.totodev.infoengine.resources.scene.ComponentDataModel;
import org.eclipse.collections.api.map.primitive.MutableIntObjectMap;
import org.eclipse.collections.impl.factory.primitive.IntObjectMaps;
import org.jetbrains.annotations.*;

public class AiConfig implements Component {
    public static class ConfigStuff {
        public float speed;
        public int target;
    }

    private final MutableIntObjectMap<ConfigStuff> configMap = IntObjectMaps.mutable.empty();

    /**
     * guess what. it gets you your speed
     */
    public float getSpeed(int entityId) {
        if (!isPresentOn(entityId)) return 0;
        return configMap.get(entityId).speed;
    }
    public void setSpeed(int entityId, float speed) {
        if (!isPresentOn(entityId)) return;
        configMap.get(entityId).speed = speed;
    }

    /**
     * guess what. it gets you your currentTarget
     */
    public int getTarget(int entityId) {
        if (!isPresentOn(entityId)) return 0;
        return configMap.get(entityId).target;
    }
    public void setTarget(int entityId, int target) {
        if (!isPresentOn(entityId)) return;
        configMap.get(entityId).target = target;
    }

    @Override
    public void addOnEntity(int entityId) {
        configMap.put(entityId, new ConfigStuff());
    }
    @Override
    public void removeFromEntity(int entityId) {
        configMap.remove(entityId);
    }

    @Override
    public void deserializeState(@NotNull ComponentDataModel data) {
        if (!isPresentOn(data.entity))
            addOnEntity(data.entity);
        String[] splitValue = data.value.split("\\|");
        setSpeed(data.entity, Float.parseFloat(splitValue[0]));
        setTarget(data.entity, Integer.parseInt(splitValue[1]));
    }
    @Override
    public @Nullable String serializeState(int entityId) {
        if (!isPresentOn(entityId)) return null;
        return getSpeed(entityId) + "|" + getTarget(entityId);
    }

    @Override
    public boolean isPresentOn(int entityId) {
        return configMap.containsKey(entityId);
    }
}
