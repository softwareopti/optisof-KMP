This is a Kotlin Multiplatform project targeting Android, iOS.

# OptiSoftgit  KMP

A multimodule full-featured Kotlin Multiplatform & Compose Multiplatform e-commerce project targeting Android, iOS, Desktop, and Server platforms, built
with a feature-based modular architecture following Clean Architecture principles.

The app is fully **responsive** and **adaptive** across all major form factors — phones (portrait & landscape), foldables, tablets, and desktops.


## ⚡ Architecture Overview

The project follows a **Clean Architecture** approach with **feature-based modularization**,
ensuring separation of concerns, testability, and maintainability across all platforms.

### 📦 Module Structure

```
OptiSoftKMP/
├── 🎯 composeApp/          # Main application module (UI layer)
├── 🔧 build-logic/         # Build configuration and conventions
├── 📊 data/                # Data layer (repositories, network, storage)
├── 🎨 designsystem/        # UI design system and components  
├── 📋 model/               # Data models and entities
├── 🔄 common/              # Shared utilities and resources
└── 🔥 feature/             # Feature modules
    ├── forgotpassword/     # Password recovery
    ├── home/               # Main product catalog
    ├── login/              # User authentication
    ├── ordercompleted/     # Order confirmation
    ├── orders/             # Order history
    ├── profile/            # User profile management
    ├── register/           # User registration
    └── settings/           # App settings
```

## 🎯 Architecture Principles

### ✅ Current Strengths

- **Feature-based modularization** for better organization
- **Multiplatform support** (Android, iOS, Desktop)
- **Clean Architecture** separation of concerns
- **Dependency Injection** with Koin
- **Type-safe project accessors** for build configuration
- **Shared build logic** with convention plugins

### 🚀 Key Benefits

- **Scalability**: Easy to add new features as separate modules
- **Maintainability**: Clear separation of concerns
- **Testability**: Each module can be tested independently
- **Reusability**: Shared code across all platforms
- **Team Collaboration**: Different teams can work on different features
- **Build Performance**: Parallel module compilation

### 🔄 Data Flow

```
UI Layer (composeApp) 
    ↓
Feature Modules (feature:*)
    ↓  
Data Layer (data)
    ↓
Backend (server)
```

## 🧩 Technologies Used

- **Kotlin Multiplatform** - Cross-platform development
- **Compose Multiplatform** - Declarative UI framework
- **Kotlin Coroutines & Flows** - Asynchronous programming & data streams
- **Ktor Client** - HTTP client for API communication
- **Kotlinx Serialization** - JSON serialization/deserialization
- **Koin** - Dependency injection framework
- **DataStore Preferences** - Type-safe data storage
- **Kotlinx DateTime** - Date and time handling
- **Material3** - Material Design 3 components
- **Material3 Adaptive** - Responsive UI components
- **Adaptive Layout** - Multi-screen layout support
- **Adaptive Navigation** - Navigation for different screen sizes
- **Kamel** - Image loading and caching
- **ConstraintLayout Compose** - Complex layouts
- **Convention Plugins** - Shared build configuration
- **Kermit** - Multiplatform logging


