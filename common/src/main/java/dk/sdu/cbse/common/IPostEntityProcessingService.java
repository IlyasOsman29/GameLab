package dk.sdu.cbse.common;

/** Runs rules that need more than one entity, such as collisions. */
public interface IPostEntityProcessingService {
    /**
     * <p><strong>Pre:</strong> {@code gameData} is not null, {@code deltaSeconds >= 0}, and the
     * normal entity processors have finished.</p>
     * <p><strong>Post:</strong> detected interactions have been applied to {@code gameData}.</p>
     *
     * @param gameData shared mutable world state
     * @param deltaSeconds elapsed seconds for the current frame
     */
    void process(GameData gameData, double deltaSeconds);
}
