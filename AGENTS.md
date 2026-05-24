# AGENTS.md

## Scope

These instructions apply to the entire `MyBase` workspace.

This project is an Android/Kotlin multi-module app using Compose, Hilt, Retrofit,
Apollo GraphQL, coroutines, Navigation 3, ktlint, detekt, and Android lint.

## Project Shape

- `:app` is the application shell and composition entry point.
- `:ui` owns Compose screens, navigation, screen ViewModels, UI state, UI actions,
  and one-off UI effects.
- `:domain` is pure Kotlin and owns domain models plus repository interfaces.
- `:data` implements domain repository interfaces, owns data mappers, and
  coordinates concrete data sources.
- `:network` owns network infrastructure, Retrofit/Apollo setup, network helpers,
  and low-level network concerns.
- `:test-common` owns shared coroutine test rules and test utilities.

Respect the dependency direction:

```text
app -> ui -> domain
app -> data -> domain
data -> network
app -> network
```

Do not make `:domain` depend on Android, Retrofit, Apollo, Hilt, Compose, or any
other framework-specific implementation. Do not make `:ui` import data or network
classes directly.

## Architecture Rules

Follow modern Android architecture with unidirectional data flow:

- State flows downward from data/domain to ViewModel to UI.
- User events flow upward from UI to ViewModel to domain/data.
- UI renders immutable state and forwards events. UI must not fetch data directly
  or apply business rules.
- Compose screens should collect ViewModel state with lifecycle-aware APIs such as
  `collectAsStateWithLifecycle`.
- Navigation should stay centralized and state-driven. Avoid scattering imperative
  navigation decisions across unrelated layers.

Use the existing MVI style in `:ui`:

- ViewModels should expose a single immutable UI state object when practical.
- User input should be represented as sealed `UiAction` types.
- One-off UI work such as snackbars and navigation signals should use `UiEffect`
  rather than direct callbacks from ViewModel to UI.
- Keep screen ViewModels lifecycle-agnostic. Do not hold `Context`, `Activity`, or
  `Fragment` references in ViewModels.

## SOLID In This Codebase

Apply SOLID as practical boundaries, not ceremony.

- Single Responsibility: each class should have one reason to change. Retrofit
  interfaces declare endpoints, repositories coordinate data sources, mappers map
  between layers, ViewModels manage UI state, and Composables render state.
- Open/Closed: add new data strategies as new data sources or repository
  implementations. Prefer swapping bindings in Hilt modules over editing
  callers.
- Liskov Substitution: every repository implementation and test fake must fully
  honor the domain interface contract. Do not return nullable surprises, leak raw
  implementation exceptions, or narrow behavior in an implementation.
- Interface Segregation: keep repository interfaces focused. If an interface grows
  unrelated read/write or feature responsibilities, split it before consumers are
  forced to depend on methods they do not use.
- Dependency Inversion: high-level UI/domain code depends on domain abstractions.
  Concrete implementations meet abstractions only in data/network modules and
  Hilt binding modules.

## Domain And Use Cases

The domain layer is optional only when logic is truly trivial. Add a use case when
logic is reused, non-trivial, orchestrates multiple repositories, transforms data,
or enforces business rules.

Use cases should:

- Be plain Kotlin classes.
- Be stateless.
- Have verb-based names such as `FetchUserProfileUseCase`.
- Do exactly one business action.
- Depend on repository interfaces from `:domain`, not concrete implementations.
- Avoid Android or UI framework types.

For simple one-screen reads, the current style of injecting a repository into a
ViewModel is acceptable. As soon as orchestration or business logic appears, move
it into a use case.

## Data Layer Rules

Repositories are the public API of the data layer:

- Expose domain models, not Retrofit responses, Apollo generated models, database
  entities, or mutable data.
- Use `suspend` functions for one-shot operations.
- Use `Flow<T>` for ongoing observable data when a stream is needed.
- Keep APIs main-safe. Callers should not need to know which dispatcher is safe.
- Catch low-level exceptions at the data boundary and map them to domain-friendly
  results or error types.
- Preserve coroutine cancellation. Do not swallow `CancellationException`.

Data sources are concrete implementation details:

- Retrofit interfaces and Apollo clients belong below repository boundaries.
- Mappers belong at layer boundaries. Keep them small, deterministic, and tested.
- If local storage is added, prefer local storage as the single source of truth:
  store first, refresh from network, and let UI observe local data.
- Use WorkManager for work that must survive process death, retries, or system
  constraints. Do not rely on ViewModels for guaranteed background execution.

## Model Boundaries

Keep models separated as complexity grows:

- Network/API models are not domain models.
- GraphQL generated models are not domain models.
- Database entities are not domain models.
- UI models are allowed when the screen needs presentation-specific shape.

Map explicitly at boundaries. This keeps refactors local and prevents framework
details from leaking across modules.

## Dependency Injection

Use Hilt as the composition root:

- Put abstract bindings such as repository interface to implementation in
  `:data` Hilt modules.
- Put network object creation such as OkHttp, Retrofit, and Apollo in `:network`.
- Keep `:app` depending on DI-providing modules so bindings are available at
  runtime.
- Prefer constructor injection for classes owned by the project.

## Testing

Tests should focus on behavior and layer contracts:

- ViewModels: state transitions and effects for each action.
- Use cases: business rules and repository interactions.
- Repositories: data-source coordination, result mapping, and error paths.
- Mappers: null/default handling and model transformation.

Use the existing tools and patterns:

- JUnit for unit tests.
- MockK where mocks are already used.
- Prefer fakes over mocks for domain contracts when that makes behavior clearer.
- Turbine for Flow/StateFlow assertions.
- Kotest matchers for readable assertions.
- `StandardDispatcherRule` or `UnconfinedDispatcherRule` from `:test-common` for
  coroutine ViewModel tests.

## Commands

Run the broad verification command before handing off meaningful changes:

```sh
./gradlew detekt ktlintCheck lintDebug testDebugUnitTest
```

Useful narrower commands:

```sh
./gradlew ktlintCheck
./gradlew ktlintFormat
./gradlew :ui:testDebugUnitTest
./gradlew :data:testDebugUnitTest
./gradlew :domain:test
```

The repository installs git hooks from `scripts/` during `:app:preBuild`. The
pre-push hook runs detekt, ktlint, Android lint, and debug unit tests.

## Coding Standards

- Keep Kotlin code formatted by ktlint and accepted by detekt.
- Java/Kotlin target is 17.
- Add dependencies through `gradle/libs.versions.toml`; do not hardcode new
  library coordinates in module build files.
- Keep comments sparse and useful. Prefer clear names and small functions.
- Do not introduce cross-module shortcuts that bypass the architecture.
- Do not add abstractions for their own sake. Add them when they protect a real
  boundary, remove duplication, or make tests meaningfully simpler.

## Source Notes

This guide was synthesized from the Notion pages under `Self TODOs`:

- `SOLID Principles in Android`
  `https://www.notion.so/36a473c0c3fc8184bf27d4dec68b5c82`
- `Modern architecture`
  `https://www.notion.so/33c473c0c3fc815389dbf993f3a1ab82`
