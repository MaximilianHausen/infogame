package net.totodev.infogame.health;

import net.totodev.infoengine.ecs.Component;
import net.totodev.infoengine.resources.scene.ComponentDataModel;
import org.eclipse.collections.api.map.primitive.MutableIntIntMap;
import org.eclipse.collections.impl.factory.primitive.IntIntMaps;
import org.jetbrains.annotations.*;
import org.joml.Matrix3x2f;

public class Health implements Component {

    //MutableIntIntMap healths:erstellte Map speichert die insgesamte Health aller Entities
    private final MutableIntIntMap healths = IntIntMaps.mutable.empty();

    /**gibt die Health einer bestimmten Entity zurück
     *
     * @param entityId: Eingebene Entity
     * @return
     */
    public int getHealth(int entityId) {
        return healths.get(entityId);
    }

    /**setzt die Health einer Entity auf eine neue Health
     *
     * @param entityId:Eingegebene Entity
     * @param health: Neue Health
     */
    public void setHealth(int entityId, int health) {
        healths.put(entityId, health);
    }

    /**ändert die Health einer Entity um eine Menge
     *
     * @param entityId: Eingegebene Entity
     * @param amount: Eingebene Menge
     */
    public void changeHealth(int entityId, int amount) {
        if (!healths.contains(entityId)) return;
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
        return healths.contains(entityId);
    }
}
