package dk.sdu.cbse.collision;

import dk.sdu.cbse.common.Entity;
import dk.sdu.cbse.common.GameData;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class CollisionComponentTest {
    private final CollisionComponent collision = new CollisionComponent();

    @Test
    void bulletSplitsLargeAsteroidIntoTwoSmallerAsteroids() {
        GameData data = new GameData();
        Entity asteroid = new Entity(Entity.ASTEROID, 100, 100, 32);
        asteroid.setVelocityX(20);
        Entity bullet = bullet(Entity.PLAYER, 100, 100);
        data.entities().addAll(java.util.List.of(asteroid, bullet));

        collision.process(data, 0.016);

        assertEquals(2, data.all(Entity.ASTEROID).size());
        assertEquals(16, data.all(Entity.ASTEROID).getFirst().radius());
        assertFalse(data.entities().contains(bullet));
    }

    @Test
    void smallAsteroidDisappearsInsteadOfSplitting() {
        GameData data = new GameData();
        data.entities().add(new Entity(Entity.ASTEROID, 50, 50, 10));
        data.entities().add(bullet(Entity.PLAYER, 50, 50));
        collision.process(data, 0.016);
        assertEquals(0, data.all(Entity.ASTEROID).size());
    }

    @Test
    void threeEnemyBulletsDestroyPlayer() {
        GameData data = new GameData();
        Entity player = new Entity(Entity.PLAYER, 200, 200, 16);
        player.setHealth(3);
        data.entities().add(player);
        for (int hit = 0; hit < 3; hit++) {
            data.entities().add(bullet(Entity.ENEMY, 200, 200));
            collision.process(data, 0.016);
        }
        assertEquals(0, data.all(Entity.PLAYER).size());
    }

    @Test
    void asteroidCollisionDestroysShipImmediately() {
        GameData data = new GameData();
        Entity enemy = new Entity(Entity.ENEMY, 30, 30, 16);
        enemy.setHealth(3);
        data.entities().addAll(java.util.List.of(enemy, new Entity(Entity.ASTEROID, 30, 30, 20)));
        collision.process(data, 0.016);
        assertEquals(0, data.all(Entity.ENEMY).size());
    }

    private Entity bullet(String owner, double x, double y) {
        Entity bullet = new Entity(Entity.BULLET, x, y, 3);
        bullet.setOwner(owner);
        return bullet;
    }
}
