package dk.sdu.cbse.collision;

import dk.sdu.cbse.common.Entity;
import dk.sdu.cbse.common.GameData;
import dk.sdu.cbse.common.IPostEntityProcessingService;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class CollisionComponent implements IPostEntityProcessingService {
    public static final double MIN_ASTEROID_RADIUS = 8;

    @Override
    public void process(GameData data, double deltaSeconds) {
        List<Entity> snapshot = new ArrayList<>(data.entities());
        Set<Entity> removed = new HashSet<>();
        List<Entity> created = new ArrayList<>();

        for (Entity bullet : snapshot.stream().filter(e -> e.type().equals(Entity.BULLET)).toList()) {
            if (removed.contains(bullet)) continue;
            for (Entity target : snapshot) {
                if (target == bullet || removed.contains(target) || !isBulletTarget(bullet, target)) continue;
                if (!collides(bullet, target)) continue;
                removed.add(bullet);
                if (target.type().equals(Entity.ASTEROID)) {
                    removed.add(target);
                    split(target, created);
                } else {
                    target.setHealth(target.health() - 1);
                    if (target.health() <= 0) removed.add(target);
                }
                break;
            }
        }

        List<Entity> ships = snapshot.stream()
                .filter(e -> e.type().equals(Entity.PLAYER) || e.type().equals(Entity.ENEMY)).toList();
        List<Entity> asteroids = snapshot.stream().filter(e -> e.type().equals(Entity.ASTEROID)).toList();
        for (Entity ship : ships) {
            for (Entity asteroid : asteroids) {
                if (!removed.contains(ship) && !removed.contains(asteroid) && collides(ship, asteroid)) {
                    removed.add(ship);
                }
            }
        }

        data.entities().removeAll(removed);
        data.entities().addAll(created);
    }

    private boolean isBulletTarget(Entity bullet, Entity target) {
        if (target.type().equals(Entity.BULLET)) return false;
        if (target.type().equals(Entity.ASTEROID)) return true;
        return (target.type().equals(Entity.PLAYER) || target.type().equals(Entity.ENEMY))
                && !target.type().equals(bullet.owner());
    }

    private void split(Entity asteroid, List<Entity> created) {
        double childRadius = asteroid.radius() / 2.0;
        if (childRadius < MIN_ASTEROID_RADIUS) return;
        for (int direction : new int[]{-1, 1}) {
            Entity child = new Entity(Entity.ASTEROID, asteroid.x(), asteroid.y(), childRadius);
            double speed = Math.max(35, Math.hypot(asteroid.velocityX(), asteroid.velocityY()));
            child.setVelocityX(-asteroid.velocityY() * direction + speed * 0.25 * direction);
            child.setVelocityY(asteroid.velocityX() * direction - speed * 0.25);
            created.add(child);
        }
    }

    public static boolean collides(Entity first, Entity second) {
        double dx = first.x() - second.x();
        double dy = first.y() - second.y();
        double radius = first.radius() + second.radius();
        return dx * dx + dy * dy <= radius * radius;
    }
}
