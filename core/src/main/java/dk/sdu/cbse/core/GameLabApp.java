package dk.sdu.cbse.core;

import dk.sdu.cbse.asteroids.AsteroidComponent;
import dk.sdu.cbse.collision.CollisionComponent;
import dk.sdu.cbse.common.Entity;
import dk.sdu.cbse.common.GameData;
import dk.sdu.cbse.common.IEntityProcessingService;
import dk.sdu.cbse.common.IGamePluginService;
import dk.sdu.cbse.common.IPostEntityProcessingService;
import dk.sdu.cbse.enemy.EnemyComponent;
import dk.sdu.cbse.player.PlayerComponent;
import dk.sdu.cbse.weapon.WeaponComponent;
import java.util.List;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public final class GameLabApp extends Application {
    private final GameData data = new GameData();
    private final List<IGamePluginService> plugins;
    private final List<IEntityProcessingService> processors;
    private final List<IPostEntityProcessingService> postProcessors;

    public GameLabApp() {
        PlayerComponent player = new PlayerComponent();
        EnemyComponent enemy = new EnemyComponent();
        AsteroidComponent asteroids = new AsteroidComponent();
        WeaponComponent weapon = new WeaponComponent();
        CollisionComponent collision = new CollisionComponent();
        plugins = List.of(player, enemy, asteroids, weapon);
        processors = List.of(player, enemy, asteroids, weapon);
        postProcessors = List.of(collision);
    }

    @Override
    public void start(Stage stage) {
        Canvas canvas = new Canvas(800, 600);
        Scene scene = new Scene(new Group(canvas));
        scene.setOnKeyPressed(event -> key(event.getCode(), true));
        scene.setOnKeyReleased(event -> key(event.getCode(), false));
        plugins.forEach(plugin -> plugin.start(data));

        stage.setTitle("GameLab - JavaFX component Asteroids");
        stage.setScene(scene);
        stage.setOnCloseRequest(event -> plugins.forEach(plugin -> plugin.stop(data)));
        stage.show();

        new AnimationTimer() {
            private long previous;
            @Override public void handle(long now) {
                double delta = previous == 0 ? 0 : Math.min((now - previous) / 1_000_000_000.0, 0.05);
                previous = now;
                processors.forEach(processor -> processor.process(data, delta));
                postProcessors.forEach(processor -> processor.process(data, delta));
                draw(canvas.getGraphicsContext2D());
            }
        }.start();
    }

    private void key(KeyCode code, boolean pressed) {
        String key = switch (code) {
            case LEFT -> "LEFT";
            case RIGHT -> "RIGHT";
            case UP -> "UP";
            case SPACE -> "FIRE";
            default -> "";
        };
        if (!key.isEmpty()) data.setPressed(key, pressed);
    }

    private void draw(GraphicsContext graphics) {
        graphics.setFill(Color.BLACK);
        graphics.fillRect(0, 0, data.width(), data.height());
        for (Entity entity : data.entities()) {
            graphics.setStroke(switch (entity.type()) {
                case Entity.PLAYER -> Color.CYAN;
                case Entity.ENEMY -> Color.RED;
                case Entity.BULLET -> entity.owner().equals(Entity.ENEMY) ? Color.ORANGE : Color.YELLOW;
                default -> Color.LIGHTGRAY;
            });
            graphics.strokeOval(entity.x() - entity.radius(), entity.y() - entity.radius(),
                    entity.radius() * 2, entity.radius() * 2);
        }
    }

    public static void main(String[] args) { launch(args); }
}
