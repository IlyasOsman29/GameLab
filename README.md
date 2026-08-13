# GameLab

This is a small JavaFX Asteroids game built from Maven modules.

## Course progression

This is the first lab. It introduces the game components and their shared interfaces before automatic service discovery is added in [JavaLab](https://github.com/IlyasOsman29/JavaLab).

| Module | Job |
|---|---|
| `common` | Shared entities, game data and interfaces |
| `player` | Creates and moves the player |
| `enemy` | Creates and moves an enemy |
| `asteroids` | Creates and moves asteroids |
| `weapon` | Creates and moves bullets |
| `collision` | Checks collisions after movement |
| `core` | Starts JavaFX and runs the game loop |

The modules communicate through the three service interfaces in `common`. Their preconditions and postconditions are written in the interface JavaDoc. For example, the weapon module finds ships by their entity type and does not import the player or enemy implementation.

## Build and run

Requirements: JDK 21 and Maven.

```text
mvn clean verify
mvn -pl core -am javafx:run
```

Controls: Left/Right turns, Up moves forward and Space shoots.

Ships have three health points. Bullets damage the other ship, and larger asteroids split when they are shot. Score, sound and levels are not included because they would need extra components and are outside this lab.
