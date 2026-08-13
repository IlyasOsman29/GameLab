# GameLab

GameLab is a deliberately small JavaFX Asteroids game split into Maven component modules. Shared data and contracts live in `common`; gameplay modules depend only on that module. `core` assembles the components and owns only the JavaFX window, input, game loop and rendering.

## Modules

| Module | Provides | Requires |
|---|---|---|
| `common` | `Entity`, `GameData`, the three service contracts | JDK only |
| `player` | Player lifecycle and movement processing | `common` |
| `enemy` | Enemy lifecycle and movement processing | `common` |
| `asteroids` | Random asteroid creation and movement | `common` |
| `weapon` | Player and enemy bullets | `common` |
| `collision` | Post-processing collision rules | `common` |
| `core` | JavaFX game loop, input and drawing | all provided service implementations |

Normal entity processors run first. The collision component implements `IPostEntityProcessingService`, so it evaluates the final positions for the frame. Circle collisions use the Pythagorean distance rule. Ships have three health points; only bullets owned by the opposite ship reduce that health. A bullet-hit asteroid splits into two half-size asteroids while the child radius is at least 8 pixels. Smaller asteroids disappear. A ship that touches an asteroid is removed immediately.

## Build and run

Requirements: JDK 21 and Maven 3.9+.

```powershell
mvn clean test package
mvn -pl core -am javafx:run
```

Controls: Left/Right rotate, Up thrusts, Space fires.

## Contract and component notes

The complete preconditions and postconditions are documented in the JavaDoc of `IGamePluginService`, `IEntityProcessingService` and `IPostEntityProcessingService` in `common`. Components communicate only through those interfaces and `GameData`; for example, Weapon locates ships by entity type instead of importing Player or Enemy classes.

Possible components intentionally left out are score display, sound, level progression and persistence. A score component would require collision events or a score service and provide score state. A sound component would require gameplay events and provide audio playback. A level component would require entity counts and provide wave creation. They are optional because the lab focuses on component contracts, processing order and collision behaviour.
