# commerce

Kotlin + Spring Boot 기반 이커머스 백엔드 학습 프로젝트입니다.

현재는 백엔드 3단계, 퍼블리싱 4단계, 프론트엔드 6단계까지 진행된 상태이며, 이후 `curriculum.md`와 `progress.md`를 기준으로 step by step으로 구현을 이어갑니다.

## Tech Stack

- Kotlin 1.9.25
- Spring Boot 3.5.13
- Gradle 8.14.4
- Java 17
- React 18
- TypeScript 5
- Vite 5

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

### Frontend Development

```bash
cd frontend
npm install
npm run dev
```

- 기본 개발 주소: `http://localhost:5173`
- 상품 목록: `http://localhost:5173/products`
- 상품 상세: `http://localhost:5173/products/101`
- 장바구니: `http://localhost:5173/cart`
- 주문/결제: `http://localhost:5173/checkout`

프론트 환경변수 예시는 `frontend/.env.sample`을 참고합니다.

- `VITE_API_BASE_URL`: 브라우저에서 사용할 API 기본 경로, 기본값 `/api`
- `VITE_DEV_API_TARGET`: Vite dev server proxy 대상, 기본값 `http://localhost:8080`
- `VITE_USE_MOCK_ORDER`: 주문 요청을 mock 처리할지 여부, 기본값 `true`

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

- 백엔드 `0단계` ~ `3단계` 완료
- 퍼블리싱 `4단계` 완료
- 프론트엔드 `5단계`, `6단계` 완료
- 다음 진행 예정: `STEP 07-01. 장바구니 엔티티 및 API 구현`
