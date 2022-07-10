package net.totodev.infogame;

import net.totodev.infoengine.core.Engine;
import net.totodev.infoengine.ecs.Scene;
import net.totodev.infoengine.resources.ResourceManager;
import net.totodev.infoengine.resources.scene.SceneLoader;
import net.totodev.infoengine.util.*;

public class Main {
    public static void main(String[] args) {
        Engine.initialize("Roguelike", new SemVer(1, 0, 0), 1000, 1000);
        ResourceManager.loadResourcePack(IO.getFileFromResource("pack", false));

        String text = IO.getTextFromFile(IO.getFileFromResource("pack/scenes/testScene.json", false));
        Scene scene = SceneLoader.loadScene(text);

        scene.start();
        Engine.start();
        scene.stop();
    }
}
