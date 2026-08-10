# Cavern-Chat

Unofficial mod for [TheCavern Minecraft Server](https://thecavern.net/), that added chat filters for channels, and various server messages.

**Channel Toggle** <br/>
<img width="681" height="139" alt="preview" src="https://github.com/user-attachments/assets/58c735c2-4a8e-446f-b497-235787132c2d" />

**Reply Person Indicator** <br/>
<img width="461" height="68" alt="Screenshot From 2026-01-26 16-51-13" src="https://github.com/user-attachments/assets/ac61e1d8-d769-4e31-b116-045c348794f8" />

**Color Coded DM** <br/>
<img width="588" height="100" alt="Screenshot From 2026-01-26 12-52-22" src="https://github.com/user-attachments/assets/414a56e8-c2e4-4a83-947d-f1a2c7d62805" />


https://github.com/user-attachments/assets/29d998f1-e60b-4251-82b3-3cb52a50ea2e

## Supported Minecraft Versions

| Minecraft Version      | Download                                                                                                                          |
| ---------------------- | --------------------------------------------------------------------------------------------------------------------------------- |
| 1.21, 1.21.1           | [cavern-chat-1.5.0+1.21.1.jar](https://github.com/McShinyShoe/cavern-chat/releases/download/1.5.0/cavern-chat-1.5.0+1.21.1.jar)   |
| 1.21.2, 1.21.3         | [cavern-chat-1.5.0+1.21.3.jar](https://github.com/McShinyShoe/cavern-chat/releases/download/1.5.0/cavern-chat-1.5.0+1.21.3.jar)   |
| 1.21.4                 | [cavern-chat-1.5.0+1.21.4.jar](https://github.com/McShinyShoe/cavern-chat/releases/download/1.5.0/cavern-chat-1.5.0+1.21.4.jar)   |
| 1.21.5                 | [cavern-chat-1.5.0+1.21.5.jar](https://github.com/McShinyShoe/cavern-chat/releases/download/1.5.0/cavern-chat-1.5.0+1.21.5.jar)   |
| 1.21.6, 1.21.7, 1.21.8 | [cavern-chat-1.5.0+1.21.8.jar](https://github.com/McShinyShoe/cavern-chat/releases/download/1.5.0/cavern-chat-1.5.0+1.21.8.jar)   |
| 1.21.9, 1.21.10        | [cavern-chat-1.5.0+1.21.10.jar](https://github.com/McShinyShoe/cavern-chat/releases/download/1.5.0/cavern-chat-1.5.0+1.21.10.jar) |
| 1.21.11                | [cavern-chat-1.5.0+1.21.11.jar](https://github.com/McShinyShoe/cavern-chat/releases/download/1.5.0/cavern-chat-1.5.0+1.21.11.jar) |

## Building

Multi-version support uses [Stonecutter](https://stonecutter.kikugie.dev/).
Each target has its own dependency set in `versions/<mc>/gradle.properties`,
and version-specific code is gated with `//? if` comments in the shared `src/` tree.

Build one version:

```sh
./gradlew :1.21.10:build
```

Build every version:

```sh
./gradlew :1.21.1:build :1.21.3:build :1.21.4:build :1.21.5:build :1.21.8:build :1.21.10:build :1.21.11:build
```

Jars are written to `versions/<mc>/build/libs/`.

To work on a specific version in the IDE,
switch the active version so the `//? if` comments are rewritten in place:

```sh
./gradlew "Set active project to 1.21.4"
```

Run this before committing so the tree returns to the `1.21.10` baseline:

```sh
./gradlew "Reset active project"
```

## Channel Button Controls
- `Click` Toggle the visibility of that channel
- `Shift + Click` Channel Button to change active channel to the clicked channel
- `CTRL + Click` Channel Button to only enable that channel

## Features

### Current Features:
- Channel Filters
- Active channel indicator
- Reply person indicator
- Color Coded DM (configurable)

### Features In the Works:
- Filter out ChatGames
- And many more :P
