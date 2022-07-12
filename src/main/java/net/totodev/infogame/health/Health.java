package net.totodev.infogame.health;

import net.totodev.infoengine.ecs.Component;
import net.totodev.infoengine.resources.scene.ComponentDataModel;
import org.eclipse.collections.api.map.primitive.MutableIntIntMap;
import org.eclipse.collections.impl.factory.primitive.IntIntMaps;
import org.jetbrains.annotations.*;
import org.joml.Matrix3x2f;

public class Health implements Component {

    // Speichert die insgesamte Health aller Entities
    private final MutableIntIntMap healths = IntIntMaps.mutable.empty();

    /**
     * Gibt die Health einer bestimmten Entity zurück
     * @param entityId: Eingebene Entity
     */
    public int getHealth(int entityId) {
        return healths.get(entityId);
    }

    /**
     * Setzt die Health einer Entity auf eine neue Health
     * @param entityId: Eingegebene Entity
     * @param health: Neue Health
     */
    public void setHealth(int entityId, int health) {
        healths.put(entityId, health);
    }

    /**
     * Ändert die Health einer Entity um eine Menge
     * @param entityId: Eingegebene Entity
     * @param amount: Eingebene Menge
     */
    public void changeHealth(int entityId, int amount) {
        if (!healths.containsKey(entityId) || healths.get(entityId) + amount < 0 || healths.get(entityId) + amount > 10) return;
        healths.addToValue(entityId, amount);
    }

    @Override
    public void resetEntity(int entityId) {
        healths.remove(entityId);
    }

    @Override
    public void deserializeState(@NotNull ComponentDataModel data) {
        setHealth(data.entity, Integer.parseInt(data.value));
    }
    @Override
    public @Nullable String serializeState(int entityId) {
        if (!isPresentOn(entityId)) return null;
        return Integer.toString(healths.get(entityId));
    }

    @Override
    public boolean isPresentOn(int entityId) {
        return healths.containsKey(entityId);
    }
}
