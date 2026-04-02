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
- MariaDB 10.x or later

### Environment Variables

프로필은 `local`, `prod`로 분리되어 있습니다.

- `local`: 애플리케이션이 `localhost:3306/commerce`로 직접 연결
- `prod`: 모든 DB 접속 정보를 환경변수에서 읽어서 연결

필수 환경변수:

- `DB_USERNAME`
- `DB_PASSWORD`

선택 환경변수:

- `DB_HOST` default: `localhost`
- `DB_PORT` default: `3306`
- `DB_NAME` default: `commerce`
- `SERVER_PORT` default: `8080`
- `DB_ROOT_PASSWORD` docker MariaDB root 계정 비밀번호

### Docker MariaDB

1. `.env.example`을 참고해서 `.env` 파일을 생성합니다.
2. 최소한 `DB_USERNAME`, `DB_PASSWORD`, `DB_ROOT_PASSWORD`를 채웁니다.
3. 아래 명령으로 MariaDB를 실행합니다.

```bash
docker compose up -d mariadb
```

4. 상태 확인:

```bash
docker compose ps
```

5. 종료:

```bash
docker compose down
```

### Run Test

```bash
./gradlew test
```

### Run Application

```bash
SPRING_PROFILES_ACTIVE=local ./gradlew bootRun
```

`bootRun`은 프로젝트 루트의 `.env`를 자동으로 읽습니다.

프로덕션 프로필 예시:

```bash
SPRING_PROFILES_ACTIVE=prod ./gradlew bootRun
```

## Project Structure

```text
docker-compose.yml
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
- `STEP 00-02. Gradle Kotlin DSL 정리` 완료
- `STEP 00-03. 필수 의존성 추가` 완료
- `STEP 00-04. application.yml 설정` 완료
- `STEP 00-05. docker mariadb 실행 환경 구성` 완료
- 다음 진행 예정: `STEP 00-06. QueryDSL 설정 및 QClass 생성 확인`
