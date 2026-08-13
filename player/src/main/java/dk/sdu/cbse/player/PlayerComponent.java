package dk.sdu.cbse.player;

import dk.sdu.cbse.common.Entity;
import dk.sdu.cbse.common.GameData;
import dk.sdu.cbse.common.IEntityProcessingService;
import dk.sdu.cbse.common.IGamePluginService;

public final class PlayerComponent implements IGamePluginService, IEntityProcessingService {
    @Override
    public void start(GameData data) {
        if (data.first(Entity.PLAYER).isEmpty()) {
            Entity player = new Entity(Entity.PLAYER, data.width() / 2.0, data.height() / 2.0, 16);
            player.setHealth(3);
            data.entities().add(player);
        }
    }

    @Override
    public void stop(GameData data) {
        data.entities().removeIf(entity -> entity.type().equals(Entity.PLAYER));
    }

    @Override
    public void process(GameData data, double deltaSeconds) {
        Entity player = data.first(Entity.PLAYER).orElse(null);
        if (player == null) return;

        if (data.isPressed("LEFT")) player.setRotation(player.rotation() - 180 * deltaSeconds);
        if (data.isPressed("RIGHT")) player.setRotation(player.rotation() + 180 * deltaSeconds);
        if (data.isPressed("UP")) {
            double angle = Math.toRadians(player.rotation());
            player.setVelocityX(player.velocityX() + Math.cos(angle) * 90 * deltaSeconds);
            player.setVelocityY(player.velocityY() + Math.sin(angle) * 90 * deltaSeconds);
        }
        player.setX(player.x() + player.velocityX() * deltaSeconds);
        player.setY(player.y() + player.velocityY() * deltaSeconds);
        player.setVelocityX(player.velocityX() * 0.99);
        player.setVelocityY(player.velocityY() * 0.99);
        data.wrap(player);
    }
}
