package dk.sdu.cbse.enemy;

import dk.sdu.cbse.common.Entity;
import dk.sdu.cbse.common.GameData;
import dk.sdu.cbse.common.IEntityProcessingService;
import dk.sdu.cbse.common.IGamePluginService;

public final class EnemyComponent implements IGamePluginService, IEntityProcessingService {
    @Override
    public void start(GameData data) {
        if (data.first(Entity.ENEMY).isEmpty()) {
            Entity enemy = new Entity(Entity.ENEMY, 100, 100, 16);
            enemy.setHealth(3);
            data.entities().add(enemy);
        }
    }

    @Override
    public void stop(GameData data) {
        data.entities().removeIf(entity -> entity.type().equals(Entity.ENEMY));
    }

    @Override
    public void process(GameData data, double deltaSeconds) {
        Entity player = data.first(Entity.PLAYER).orElse(null);
        if (player == null) return;
        for (Entity enemy : data.all(Entity.ENEMY)) {
            double angle = Math.atan2(player.y() - enemy.y(), player.x() - enemy.x());
            enemy.setRotation(Math.toDegrees(angle));
            enemy.setX(enemy.x() + Math.cos(angle) * 45 * deltaSeconds);
            enemy.setY(enemy.y() + Math.sin(angle) * 45 * deltaSeconds);
            data.wrap(enemy);
        }
    }
}
