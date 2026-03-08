# DailyRewards

**Automatically claim your Hypixel Skyblock daily rewards in-game with beautiful card animations and instant claim notifications.**

Part of the **JustOptimized Modpack Official Mods** collection.

---

## Features

- **Smart Detection** — Automatically detects Hypixel reward links in chat
- **One-Click Claiming** — Claims rewards instantly
- **Card-Based UI** — Beautiful animated reward cards with rarity-based colors
- **Daily Reminders** — Get notified when you haven't claimed your rewards yet
- **Fully Configurable** — Customize animations, speed, and reminders

### Reward Rarities

| Rarity    | Color  |
| --------- | ------ |
| Common    | Gray   |
| Rare      | Aqua   |
| Epic      | Purple |
| Legendary | Gold   |

---

## Installation

### Requirements

- **Minecraft 1.21.11**
- **Fabric Loader 0.18.4+**
- **Java 21+**

### Required Dependencies

- [Fabric API](https://modrinth.com/mod/fabric-api)
- [Fabric Language Kotlin](https://modrinth.com/mod/fabric-language-kotlin)

### Optional Dependencies

- [Cloth Config](https://modrinth.com/mod/cloth-config) — For the in-game configuration GUI
- [ModMenu](https://modrinth.com/mod/modmenu) — In-game mod management

### Setup

1. Download the latest release
2. Install all required dependencies
3. Place the `.jar` files in your `mods` folder
4. Launch Minecraft with Fabric
5. Join Hypixel and rewards will be handled automatically

---

## Commands

| Command                | Description                       |
| ---------------------- | --------------------------------- |
| `/dailyrewards`        | Open the configuration screen     |
| `/dailyrewards config` | Open the configuration screen     |
| `/dailyrewards status` | View your claim status and streak |

---

## Configuration

Access settings via `/dailyrewards` or through Mod Menu:

- **Enable/Disable Mod** — Toggle the entire mod
- **Daily Reminder** — Toggle join reminders on Hypixel
- **Card Flip Animation** — Enable/disable card flip animations
- **Flip Speed** — Adjust animation speed (0.1x – 3.0x)

---

## How It Works

1. **Detection** — The mod monitors chat for Hypixel reward links
2. **Parsing** — Extracts reward tokens from URLs
3. **Fetching** — Retrieves reward data from the Hypixel API
4. **Display** — Shows a card-based selection UI
5. **Claiming** — Submits your selection and receives the reward

---

## Building from Source

```bash
git clone <repo-url>
cd DailyRewards
./gradlew clean build
```

The built mod will be in `build/libs/`.

---

## License

LGPL-3.0 — See [LICENSE](LICENSE) for details.

---

## Author

**JustMe** — Part of the JustOptimized Modpack Official Mods
