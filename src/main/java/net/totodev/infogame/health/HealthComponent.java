package net.totodev.infogame.health;

import net.totodev.infoengine.ecs.Component;
import net.totodev.infoengine.resources.scene.ComponentDataModel;
import org.eclipse.collections.api.map.primitive.MutableIntIntMap;
import org.eclipse.collections.impl.factory.primitive.IntIntMaps;
import org.jetbrains.annotations.*;

public class HealthComponent implements Component {
    private final MutableIntIntMap healths = IntIntMaps.mutable.empty();

    public int getHealth(int entityId) {
        return healths.get(entityId);
    }
    public void setHealth(int entityId, int health) {
        if (!isPresentOn(entityId)) return;
        healths.put(entityId, health);
    }
    public void changeHealth(int entityId, int amount) {
        int oldHealth = getHealth(entityId);
        setHealth(entityId, oldHealth + amount);
    }

    @Override
    public void addOnEntity(int entityId) {
        healths.put(entityId, 0);
    }
    @Override
    public void removeFromEntity(int entityId) {
        healths.remove(entityId);
    }

    @Override
    public void deserializeState(@NotNull ComponentDataModel data) {
        if (!isPresentOn(data.entity))
            addOnEntity(data.entity);
        setHealth(data.entity, Integer.parseInt(data.value));
    }
    @Override
    public @Nullable String serializeState(int entityId) {
        if (!isPresentOn(entityId)) return null;
        return Integer.toString(healths.get(entityId));
    }

    @Override
    public boolean isPresentOn(int entityId) {
        return healths.contains(entityId);
    }
}
