# Reactor Tester

A Kotlin project for testing and exploring Project Reactor's reactive programming concepts, focusing on `Mono` and `Flux` operators and their behavior.

## Getting Started

### Clone and Build

```bash
git clone <repository-url>
cd reactor-tester
./gradlew build
```

### Run Tests

```bash
# Run all tests
./gradlew test

# Run specific test class
./gradlew test --tests MonoTester
./gradlew test --tests FluxTester
./gradlew test --tests MonoFromFlux
```
