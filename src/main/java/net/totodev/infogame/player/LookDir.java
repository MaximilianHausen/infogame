package net.totodev.infogame.player;

import net.totodev.infoengine.ecs.Component;
import net.totodev.infoengine.resources.scene.ComponentDataModel;
import net.totodev.infoengine.util.SerializationUtils;
import org.eclipse.collections.api.map.primitive.MutableIntObjectMap;
import org.eclipse.collections.impl.factory.primitive.IntObjectMaps;
import org.jetbrains.annotations.*;
import org.joml.Vector2f;

public class LookDir implements Component {
    private MutableIntObjectMap<Vector2f> lookDirs = IntObjectMaps.mutable.empty();

    public Vector2f getLookDir(int entityId) {
        return lookDirs.get(entityId);
    }

    public void setLookDir(int entityId, Vector2f lookDir) {
        lookDirs.put(entityId, lookDir);
    }


    //region Unused
    @Override
    public void resetEntity(int entityId) {
    }

    @Override
    public void deserializeState(@NotNull ComponentDataModel data) {
        float[] values = SerializationUtils.deserialize(data.value);
        setLookDir(data.entity, new Vector2f(values[0], values[1]));
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
