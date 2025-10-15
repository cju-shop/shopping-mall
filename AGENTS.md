# Repository Guidelines

## Project Structure & Module Organization
Java sources live in `src/main/java/com/cju/shoppingmall`, where `ShoppingmallApplication.java` boots Spring Boot and feature packages (such as `controller`) group HTTP endpoints.
Templates render from `src/main/resources/templates` (`screens`, `product`), static assets stay in `src/main/resources/static`, and shared configuration lives under `src/main/resources`.
Tests mirror the main tree in `src/test/java/com/cju/shoppingmall`, and generated output lands in `build/`.

## Build, Test, and Development Commands
Run `./gradlew bootRun` for an auto-reloading development server; pass `--args="--spring.profiles.active=dev"` to load the MySQL profile.
`./gradlew build` compiles, executes the full JUnit suite, and produces a bootable jar in `build/libs`.
Use `./gradlew test` for quicker verification and `./gradlew clean` before reproducibility-sensitive changes.

## Coding Style & Naming Conventions
Target Java 17 (configured via the Gradle toolchain) with four-space indentation and braces on the same line.
Keep package names lowercase, classes in PascalCase, and methods plus fields in camelCase.
Annotate Spring components with the appropriate stereotype (`@Controller`, `@Service`, `@Repository`) and prefer constructor injection.
Add Lombok only when it meaningfully removes boilerplate and briefly comment non-obvious business rules.

## Testing Guidelines
All tests rely on JUnit 5 and Spring Boot testing utilities.
Name test classes with the `*Tests` suffix and mirror the package you cover.
Favor slice tests such as `@WebMvcTest` or `@DataJpaTest` for fast feedback and configure stubs under `src/test/resources/application.yml`.
Run `./gradlew test` before every push and cover success, validation, and failure paths in assertions.

## Commit & Pull Request Guidelines
History uses conventional prefixes (`docs:`, `init:`); continue with lowercase types like `feat`, `fix`, and `chore` paired with concise summaries.
Reference issue IDs or links in the body when helpful and keep each commit logically scoped.
Pull requests must describe the problem, the solution, database or template changes, and include screenshots for UI updates.
Confirm the Gradle test suite passes and link related issues or discussions before requesting review.

## Configuration & Environment Notes
The default profile uses embedded H2; activate MySQL locally with `application-dev.yml` and document new properties in your PR.
Keep secrets out of version control by relying on environment variables or ignore-listed `.env` files.
When introducing external integrations, describe required keys and fallback behaviour so others can reproduce the setup.
