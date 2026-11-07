# Java Projects

This repository contains two Java projects developed as part of the Programming Technology course. They explore different programming concepts, including object-oriented programming, physical simulations, graphical interfaces and game development.

## Projects

### 1. Physics Simulator (`Pt1TP2`)

A 2D physics simulator that allows users to create and simulate systems of bodies, such as planets, by applying different physical laws and observing how they interact over time.

The application provides a graphical interface where users can configure simulations, inspect information about bodies and groups, and visualize their movement.

**Main features:**

- **JSON import:** Load predefined simulations from the JSON files available in the `resources/examples/input` directory.
- **Simulation configuration:** Set the number of simulation steps and the time interval (`delta time`) between them directly from the graphical interface.
- **Physical laws:** Use the **Physics** button to select and apply different physical laws to the bodies:
  - Newton's universal gravitation (`nlug`): Models gravitational interactions between bodies.
  - Movement towards a fixed point (`mtfp`): Applies a force towards a configurable point.
  - No force (`nf`): Simulates bodies without applying additional forces.
- **Body and group information:** Inspect the properties of the different bodies and groups in the simulation.
- **Accumulated distances:** View the accumulated distances travelled by the bodies.
- **Simulation visualization:** Open the **Simulation Viewer** to visualize the bodies and observe their movement.
- **Playback controls:** Use the **Play** button in the viewer to start the simulation and watch the bodies move according to the selected physical laws.

#### How to run

The game is designed to be executed from Eclipse.

1. Open the  project in Eclipse.
2. Run the project.

The graphical interface will open automatically. No command-line arguments or input files are required to launch the application.

**How to use the application:**

1. Launch the application to open the main graphical interface.
2. Import a simulation using one of the JSON files in `resources/examples/input`.
3. Configure the number of simulation steps and the `delta time`.
4. Click the **Physics** button to select and apply the desired physical laws to the different bodies.
5. Inspect the information about the bodies and groups, including their accumulated distances.
6. Open the **Simulation Viewer** to visualize the configured system.
7. Press **Play** to start the simulation and observe the movement of the bodies.

---

### 2. Plants vs. Zombies (`PvZ3.0`)

A console-based implementation inspired by the classic Plants vs. Zombies game. The player must defend the garden against incoming zombies by placing different types of plants on the board.

The game is turn-based: each command advances the game and updates the objects on the board, including plant attacks, zombie movement and other game events.

**Main features:**

- **Difficulty levels:** Choose between three different difficulty levels:
  - `EASY`
  - `HARD`
  - `INSANE`
- **Plant management:** Place different types of plants on the board:
  - Sunflower
  - Peashooter
  - Wall-Nut
  - Cherry-Bomb
- **Zombie management:** Face different types of zombies:
  - Normal Zombie
  - BucketHead Zombie
  - Sporty Zombie
  - Explosive Zombie
- **Resource management:** Collect suns to obtain suncoins, which are used to place plants.
- **Combat system:** Plants and zombies have different attack, health and movement properties.
- **Game progression:** Zombies appear over time, move across the board and attack plants in their path.
- **Scoring system:** The game keeps track of the player's score.
- **High scores:** The highest score achieved is saved in a text file (`record.txt`) for each difficulty level.
- **Random generation:** The game uses a numeric seed to control random events. Using the same seed allows a game to be reproduced.
- **Command system:** Interact with the game through commands entered in the console.

#### How to run

The game is designed to be executed from Eclipse.

1. Open the `PvZ3.0` project in Eclipse.
2. Open **Run → Run Configurations...**.
3. Select the `PlantsVsZombies` launch configuration.
4. Go to the **Arguments** tab.
5. In **Program arguments**, enter the desired difficulty and, optionally, a numeric seed.

**Examples:**

```text
EASY 432
```

```text
HARD 12345
```

```text
INSANE 98765
```

The available difficulty levels are `EASY`, `HARD` and `INSANE`. The seed must be a number and is used to control random generation.

6. Click **Apply** and then **Run** to start the game.

The highest score achieved is automatically saved in `record.txt`, with a separate record for each difficulty level.

**Available commands**

Once the game starts, enter commands in the console. Use `help` to display the complete list of available commands.

| Command | Description |
|---|---|
| `add <plant> <col> <row>` | Places a plant on the board, consuming suncoins. |
| `catch <col> <row>` | Collects a sun at the specified position. |
| `list` | Displays the available plants and their properties. |
| `listZombies` | Displays the available zombies and their properties. |
| `addZombie <idx> <col> <row>` | Adds a zombie at the specified position. |
| `cheatPlant <plant> <col> <row>` | Places a plant without consuming suncoins. |
| `reset` | Restarts the current game. |
| `reset <level> <seed>` | Starts a new game with the specified difficulty and seed. |
| `record` | Displays the record for the current difficulty. |
| `help` | Displays the available commands. |
| `exit` | Leaves the game. |
## Authors

Developed as part of the Programming Technology course.
