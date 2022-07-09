package net.totodev.infogame.health;

public class HealthEvents {
    /**
     * Called whenever the health in a health tank changes <br/>
     * Parameters: <br/>
     * <pre><code>
     * int entityId: The entityId of the health tank the health amount was changed in
     * int newHealth: The new amount of health
     * </code></pre>
     */
    public static final String HealthChanged = "HealthChanged";
}
