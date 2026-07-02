# MudanzApp

Mobile Android application developed as a final academic project focused on the management of moving services, vehicle registration, rentals, and incident reporting. The project is built with Kotlin and Jetpack Compose, follows an MVVM architecture, and uses Room as its local persistence layer.[1]

## Overview

MudanzApp was created to simplify the organization of moving-related services through a single mobile application. The app centralizes user authentication, move creation, vehicle management, rentals, and incident reporting in a modular structure designed for Android devices.[1]

The technical implementation described in the project documentation uses Android Studio, Kotlin, Jetpack Compose, Navigation Compose, Room Database, and an MVVM-based architecture with reactive state management.[1]

## Main features

- User registration and login.[1]
- Home screen with access to the main application modules.[1]
- Move creation with vehicle assignment.[1]
- Vehicle registration for vehicles with and without driver.[1]
- Vehicle rental module with filtering by capacity.[1]
- Vehicle profile view with ratings and comments.[1]
- Incident reporting linked to vehicles and services.[1]
- Local offline persistence using Room Database.[1]

## Tech stack

| Area | Technology |
|---|---|
| Language | Kotlin [1] |
| UI | Jetpack Compose [1] |
| Architecture | MVVM [1] |
| Navigation | Navigation Compose [1] |
| Local database | Room Database [1] |
| State management | StateFlow and collectAsState [1] |
| IDE | Android Studio [1] |
| Version control | Git and GitHub [1] |
| Minimum Android API | 26 [1] |
| Target API | 34 [1] |
| Java version | JDK 17+ [1] |

## Project status

The application reached a functional academic stage with implemented modules for authentication, move management, vehicle registration, rentals, and incident reporting. The documentation also indicates that the project required final testing, validation, and interface polishing before broader distribution.[1]

## Application structure

The codebase is organized around a typical Android separation of concerns, with UI, ViewModel, and data responsibilities clearly split as part of the MVVM design described in the project memory.[1]

```text
app/
├── src/main/java/com/[package]/appmudanza/
│   ├── data/
│   │   ├── local/
│   │   └── remote/
│   ├── domain/
│   ├── navigation/
│   ├── ui/
│   │   ├── auth/
│   │   ├── home/
│   │   ├── moves/
│   │   ├── rentals/
│   │   ├── vehicles/
│   │   └── incidents/
│   └── MainActivity.kt
├── res/
├── build.gradle.kts
└── proguard-rules.pro
```

## Core modules

### Authentication

The authentication module allows users to register with name, email, and password, and then log in with their credentials. After a successful login, the user is redirected to the Home screen.[1]

### Move management

The move management module supports creating a move with origin, destination, client, date, and an assigned vehicle with driver. It also includes a view for registered moves and filtering by vehicle capacity.[1]

### Vehicle registration

The app supports the registration of vehicles with driver and vehicles without driver through separate forms. Registered vehicles become available to the modules that depend on them, such as move management and rental screens.[1]

### Vehicle rental

The rental module displays available vehicles without driver, allows filtering by load capacity, and lets the user select a vehicle and consult its profile information.[1]

### Incident reporting

The incident module lets users select a related vehicle and submit a description of a problem encountered during a service. Incidents are stored in the local database and confirmed through the interface.[1]

## Requirements

- Android Studio Otter or newer.[1]
- JDK 17 or newer.[1]
- Android emulator with API 34 or a physical Android device.[1]
- Gradle configured with Kotlin DSL.[1]

## Local setup

1. Clone the repository.
2. Open the project in Android Studio.
3. Let Gradle sync all dependencies.
4. Run the app on an emulator or physical device.
5. Test the main flows: authentication, moves, rentals, vehicles, and incidents.

## Architecture notes

The implementation is based on MVVM, with Jetpack Compose used for the interface layer, ViewModels for state and logic orchestration, and Room entities, DAOs, and repositories for persistence. The documentation also describes centralized navigation with Navigation Compose and reactive UI updates through StateFlow and collectAsState.[1]

## Portfolio note

This repository is presented as an academic and portfolio project. It showcases Android development skills in Kotlin, local data persistence, modular screen design, and mobile application architecture for a logistics-related use case.[1]

## License

This project is shared for personal, academic, and portfolio viewing purposes only. Commercial use, redistribution, publication, sublicensing, or derivative works are not allowed without the express written consent of the authors.

Copyright (c) 2026 Thamiris Bittencourt, Enrique Martínez, and Miguel Martínez.
