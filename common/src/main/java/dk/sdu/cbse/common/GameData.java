package dk.sdu.cbse.common;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/** Shared world state. Components exchange data here instead of depending on each other. */
public final class GameData {
    private final List<Entity> entities = new ArrayList<>();
    private final Set<String> pressedKeys = new HashSet<>();
    private int width = 800;
    private int height = 600;

    public List<Entity> entities() { return entities; }

    public Optional<Entity> first(String type) {
        return entities.stream().filter(entity -> entity.type().equals(type)).findFirst();
    }

    public List<Entity> all(String type) {
        return entities.stream().filter(entity -> entity.type().equals(type)).toList();
    }

    public boolean isPressed(String key) { return pressedKeys.contains(key); }

    public void setPressed(String key, boolean pressed) {
        if (pressed) {
            pressedKeys.add(key);
        } else {
            pressedKeys.remove(key);
        }
    }

    public int width() { return width; }
    public int height() { return height; }

    public void setSize(int width, int height) {
        this.width = Math.max(1, width);
        this.height = Math.max(1, height);
    }

    public void wrap(Entity entity) {
        if (entity.x() < 0) entity.setX(width);
        if (entity.x() > width) entity.setX(0);
        if (entity.y() < 0) entity.setY(height);
        if (entity.y() > height) entity.setY(0);
    }
}
