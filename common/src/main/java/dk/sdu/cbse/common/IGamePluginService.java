package dk.sdu.cbse.common;

/**
 * Lifecycle contract for a gameplay component.
 *
 * <p>A component may create and remove entities through the supplied shared data, but it must
 * not assume that another optional component is installed.</p>
 */
public interface IGamePluginService {
    /**
     * Activates the component.
     *
     * <p><strong>Preconditions:</strong> {@code gameData} is non-null and the component has not
     * already been started for that world.</p>
     * <p><strong>Postconditions:</strong> the component's initial entities and resources have been
     * added, and unrelated entities are unchanged.</p>
     *
     * @param gameData shared mutable world state
     */
    void start(GameData gameData);

    /**
     * Deactivates the component.
     *
     * <p><strong>Precondition:</strong> {@code gameData} is non-null.</p>
     * <p><strong>Postconditions:</strong> entities and resources owned by this component have been
     * removed; calling the method when none exist is harmless.</p>
     *
     * @param gameData shared mutable world state
     */
    void stop(GameData gameData);
}
