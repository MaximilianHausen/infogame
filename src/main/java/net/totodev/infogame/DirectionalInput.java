package net.totodev.infogame;

import net.totodev.infoengine.core.Engine;
import org.joml.Vector2f;

import static org.lwjgl.glfw.GLFW.glfwGetKey;

public class DirectionalInput {
    private int up, right, down, left;

    public DirectionalInput(int up, int right, int down, int left) {
        this.up = up;
        this.right = right;
        this.down = down;
        this.left = left;
    }

    public Vector2f poll() {
        Vector2f output = new Vector2f();

        output.y += glfwGetKey(Engine.getMainWindow().getId(), up);
        output.x += glfwGetKey(Engine.getMainWindow().getId(), right);
        output.y -= glfwGetKey(Engine.getMainWindow().getId(), down);
        output.x -= glfwGetKey(Engine.getMainWindow().getId(), left);

        if (!output.equals(0, 0)) {
            output = output.normalize();
        }

        return output;
    }
}
