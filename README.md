# IceWarpTask
IceWarpTask is an Android application built with modern Android development tools and practices. 
It demonstrates a clean architecture approach, combining Jetpack Compose for the UI, Hilt for dependency injection, and SQLDelight for local data persistence.

## Features
- **Login Screen**: Secure user authentication.
- **Channels Screen**: 
    - Interactive list of groups and channels.
    - Collapsible/Expandable group views using Material 3 `Card` and `AnimatedVisibility`.
    - On-demand data loading from local database.
- **Theme Support**: Seamless switching between Light and Dark modes.
- **Clean Architecture**: Decoupled layers (Data, Domain, UI) for better maintainability and testability.

## Tech Stack

- **Language**: [Kotlin](https://kotlinlang.org/)
- **UI Framework**: [Jetpack Compose](https://developer.android.com/jetpack/compose) with Material 3
- **Dependency Injection**: [Hilt](https://dagger.dev/hilt/)
- **Local Database**: [SQLDelight](https://cashapp.github.io/sqldelight/)
- **Networking**: [Retrofit](https://square.github.io/retrofit/) & [OkHttp](https://square.github.io/okhttp/)
- **Asynchronous Programming**: [RxJava](https://github.com/ReactiveX/RxJava) & [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html) & [Flow](https://kotlinlang.org/docs/flow.html)
- **Architecture**: MVVM (Model-View-ViewModel) + Clean Architecture (UseCases)

## Project Structure

- `data`: Contains implementations of repositories and data sources (Network/DB).
- `domain`: Contains business logic, including UseCases and Domain models.
- `ui`: Contains Jetpack Compose screens, ViewModels, and navigation logic.
- `di`: Hilt modules for providing dependencies.
- `util`: Utility classes and constants.

## Building and Running

1. Clone the repository.
2. Open the project in **Android Studio (Narwhal 3 or newer)**.
3. Sync the project with Gradle files.
4. Run the app on an emulator or a physical device.

## Recent Improvements

- **Theming**: Integrated Material 3 `ColorScheme` throughout the application for a consistent look.
- **UI Refinement**: Enhanced the `ChannelScreen` with dynamic colors for collapsible views and improved loading states.
- **Localization**: Moved hardcoded strings to `strings.xml` for better maintainability and future localization support.
