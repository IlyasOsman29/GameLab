package dk.sdu.cbse.common;

/** Updates one type of game entity. */
public interface IEntityProcessingService {
    /**
     * <p><strong>Pre:</strong> {@code gameData} is not null and {@code deltaSeconds >= 0}.</p>
     * <p><strong>Post:</strong> the component's entities have been updated for one frame.</p>
     *
     * @param gameData shared mutable world state
     * @param deltaSeconds elapsed seconds since the previous frame
     */
    void process(GameData gameData, double deltaSeconds);
}
