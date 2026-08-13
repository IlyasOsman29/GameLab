package dk.sdu.cbse.common;

/** Per-frame behaviour contract for one gameplay component. */
public interface IEntityProcessingService {
    /**
     * Updates the entities handled by the component for one frame.
     *
     * <p><strong>Preconditions:</strong> {@code gameData} is non-null and
     * {@code deltaSeconds >= 0}. The caller invokes normal processors before post-processors.</p>
     * <p><strong>Postconditions:</strong> owned entity state reflects at most one frame of elapsed
     * time; unrelated entity types remain valid; the method does not require optional components.</p>
     *
     * @param gameData shared mutable world state
     * @param deltaSeconds elapsed seconds since the previous frame
     */
    void process(GameData gameData, double deltaSeconds);
}
