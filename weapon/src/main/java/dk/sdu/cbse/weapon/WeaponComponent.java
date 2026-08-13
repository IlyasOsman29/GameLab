package dk.sdu.cbse.weapon;

import dk.sdu.cbse.common.Entity;
import dk.sdu.cbse.common.GameData;
import dk.sdu.cbse.common.IEntityProcessingService;
import dk.sdu.cbse.common.IGamePluginService;

public final class WeaponComponent implements IGamePluginService, IEntityProcessingService {
    private double playerCooldown;
    private double enemyCooldown;

    @Override public void start(GameData data) { }

    @Override
    public void stop(GameData data) {
        data.entities().removeIf(entity -> entity.type().equals(Entity.BULLET));
        playerCooldown = 0;
        enemyCooldown = 0;
    }

    @Override
    public void process(GameData data, double deltaSeconds) {
        playerCooldown = Math.max(0, playerCooldown - deltaSeconds);
        enemyCooldown = Math.max(0, enemyCooldown - deltaSeconds);

        Entity player = data.first(Entity.PLAYER).orElse(null);
        if (player != null && data.isPressed("FIRE") && playerCooldown == 0) {
            fire(data, player, player.rotation(), Entity.PLAYER);
            playerCooldown = 0.25;
        }

        Entity enemy = data.first(Entity.ENEMY).orElse(null);
        if (player != null && enemy != null && enemyCooldown == 0) {
            double angle = Math.toDegrees(Math.atan2(player.y() - enemy.y(), player.x() - enemy.x()));
            fire(data, enemy, angle, Entity.ENEMY);
            enemyCooldown = 1.0;
        }

        for (Entity bullet : data.all(Entity.BULLET)) {
            bullet.setX(bullet.x() + bullet.velocityX() * deltaSeconds);
            bullet.setY(bullet.y() + bullet.velocityY() * deltaSeconds);
            bullet.setRemainingLife(bullet.remainingLife() - deltaSeconds);
            if (bullet.remainingLife() <= 0 || bullet.x() < 0 || bullet.y() < 0
                    || bullet.x() > data.width() || bullet.y() > data.height()) {
                data.entities().remove(bullet);
            }
        }
    }

    private void fire(GameData data, Entity ship, double rotation, String owner) {
        double angle = Math.toRadians(rotation);
        Entity bullet = new Entity(Entity.BULLET, ship.x(), ship.y(), 3);
        bullet.setOwner(owner);
        bullet.setVelocityX(Math.cos(angle) * 300);
        bullet.setVelocityY(Math.sin(angle) * 300);
        bullet.setRemainingLife(2.5);
        data.entities().add(bullet);
    }
}
