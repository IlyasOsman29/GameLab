package dk.sdu.cbse.common;

/** Contract for cross-entity rules that run after all normal entity processors. */
public interface IPostEntityProcessingService {
    /**
     * Applies rules that need the final entity positions for the current frame.
     *
     * <p><strong>Preconditions:</strong> {@code gameData} is non-null,
     * {@code deltaSeconds >= 0}, and every {@link IEntityProcessingService} has completed for the
     * frame.</p>
     * <p><strong>Postconditions:</strong> detected interactions are applied atomically after
     * iteration; removed entities no longer appear in {@code gameData}; created result entities are
     * valid for processing in the next frame.</p>
     *
     * @param gameData shared mutable world state
     * @param deltaSeconds elapsed seconds for the current frame
     */
    void process(GameData gameData, double deltaSeconds);
}
