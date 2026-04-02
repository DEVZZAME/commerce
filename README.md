# commerce

Kotlin + Spring Boot 기반 이커머스 백엔드 학습 프로젝트입니다.

현재는 프로젝트 초기 생성 단계까지 완료된 상태이며, 이후 `curriculum.md`와 `progress.md`를 기준으로 step by step으로 구현을 진행합니다.

## Tech Stack

- Kotlin 1.9.25
- Spring Boot 3.5.13
- Gradle 8.14.4
- Java 17

## Getting Started

### Requirements

- JDK 17

### Run Test

```bash
./gradlew test
```

### Run Application

```bash
./gradlew bootRun
```

## Project Structure

```text
src/
  main/
    kotlin/zzame/commerce
    resources/
  test/
    kotlin/zzame/commerce
```

## Learning Docs

- `curriculum.md`: 전체 학습 커리큘럼
- `progress.md`: step 진행 현황 체크리스트

## Current Status

- `STEP 00-01. IntelliJ에서 Spring Boot Kotlin 프로젝트 생성` 완료
- 다음 진행 예정: `STEP 00-02. Gradle Kotlin DSL 정리`
