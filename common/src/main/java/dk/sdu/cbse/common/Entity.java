package dk.sdu.cbse.common;

import java.util.UUID;

/** Mutable game data shared between independent gameplay components. */
public final class Entity {
    public static final String PLAYER = "PLAYER";
    public static final String ENEMY = "ENEMY";
    public static final String ASTEROID = "ASTEROID";
    public static final String BULLET = "BULLET";

    private final String id = UUID.randomUUID().toString();
    private final String type;
    private String owner = "";
    private double x;
    private double y;
    private double velocityX;
    private double velocityY;
    private double rotation;
    private double radius;
    private double remainingLife = Double.POSITIVE_INFINITY;
    private int health = 1;

    public Entity(String type, double x, double y, double radius) {
        this.type = type;
        this.x = x;
        this.y = y;
        this.radius = radius;
    }

    public String id() { return id; }
    public String type() { return type; }
    public String owner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }
    public double x() { return x; }
    public void setX(double x) { this.x = x; }
    public double y() { return y; }
    public void setY(double y) { this.y = y; }
    public double velocityX() { return velocityX; }
    public void setVelocityX(double velocityX) { this.velocityX = velocityX; }
    public double velocityY() { return velocityY; }
    public void setVelocityY(double velocityY) { this.velocityY = velocityY; }
    public double rotation() { return rotation; }
    public void setRotation(double rotation) { this.rotation = rotation; }
    public double radius() { return radius; }
    public void setRadius(double radius) { this.radius = radius; }
    public double remainingLife() { return remainingLife; }
    public void setRemainingLife(double remainingLife) { this.remainingLife = remainingLife; }
    public int health() { return health; }
    public void setHealth(int health) { this.health = health; }
}
