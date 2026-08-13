package dk.sdu.cbse.common;

/** Starts and stops one game component. */
public interface IGamePluginService {
    /**
     * Starts the component.
     * <p><strong>Pre:</strong> {@code gameData} is not null and this component is not active.</p>
     * <p><strong>Post:</strong> the component's starting entities have been added.</p>
     *
     * @param gameData shared mutable world state
     */
    void start(GameData gameData);

    /**
     * Stops the component.
     * <p><strong>Pre:</strong> {@code gameData} is not null.</p>
     * <p><strong>Post:</strong> entities owned by this component have been removed.</p>
     *
     * @param gameData shared mutable world state
     */
    void stop(GameData gameData);
}
