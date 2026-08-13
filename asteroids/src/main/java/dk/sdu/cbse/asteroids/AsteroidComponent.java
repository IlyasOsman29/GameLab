package dk.sdu.cbse.asteroids;

import dk.sdu.cbse.common.Entity;
import dk.sdu.cbse.common.GameData;
import dk.sdu.cbse.common.IEntityProcessingService;
import dk.sdu.cbse.common.IGamePluginService;
import java.util.Random;

public final class AsteroidComponent implements IGamePluginService, IEntityProcessingService {
    private final Random random;

    public AsteroidComponent() { this(new Random()); }
    AsteroidComponent(Random random) { this.random = random; }

    @Override
    public void start(GameData data) {
        if (!data.all(Entity.ASTEROID).isEmpty()) return;
        for (int i = 0; i < 6; i++) {
            Entity asteroid = new Entity(Entity.ASTEROID,
                    random.nextDouble() * data.width(), random.nextDouble() * data.height(),
                    24 + random.nextDouble() * 12);
            asteroid.setVelocityX(-35 + random.nextDouble() * 70);
            asteroid.setVelocityY(-35 + random.nextDouble() * 70);
            data.entities().add(asteroid);
        }
    }

    @Override
    public void stop(GameData data) {
        data.entities().removeIf(entity -> entity.type().equals(Entity.ASTEROID));
    }

    @Override
    public void process(GameData data, double deltaSeconds) {
        for (Entity asteroid : data.all(Entity.ASTEROID)) {
            asteroid.setX(asteroid.x() + asteroid.velocityX() * deltaSeconds);
            asteroid.setY(asteroid.y() + asteroid.velocityY() * deltaSeconds);
            data.wrap(asteroid);
        }
    }
}
