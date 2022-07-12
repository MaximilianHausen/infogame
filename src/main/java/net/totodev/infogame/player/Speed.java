package net.totodev.infogame.player;

import net.totodev.infoengine.ecs.Component;
import net.totodev.infoengine.resources.scene.ComponentDataModel;
import org.eclipse.collections.api.map.primitive.MutableIntObjectMap;
import org.eclipse.collections.impl.factory.primitive.IntObjectMaps;
import org.jetbrains.annotations.*;
import org.joml.Vector2f;

public class Speed implements Component {
    private MutableIntObjectMap<Vector2f> speeds = IntObjectMaps.mutable.empty();

    public Vector2f getSpeed(int entityId) {
        return speeds.get(entityId);
    }

    public void setSpeed(int entityId, Vector2f lookDir) {
        speeds.put(entityId, lookDir);
    }


    //region Unused
    @Override
    public void resetEntity(int entityId) {
    }

    @Override
    public void deserializeState(@NotNull ComponentDataModel data) {
    }
    @Override
    public @Nullable String serializeState(int entityId) {
        return null;
    }

    @Override
    public boolean isPresentOn(int entityId) {
        return false;
    }
    //endregion
}
