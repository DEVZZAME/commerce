# commerce

Kotlin + Spring Boot + React 기반 이커머스 풀스택 학습 프로젝트입니다.

`curriculum.md`와 `progress.md` 기준으로 0단계부터 9단계까지 전 구간을 구현한 상태입니다.

## Tech Stack

- Kotlin 1.9.25
- Spring Boot 3.5.13
- Gradle 8.14.4
- Java 17
- React 18
- TypeScript 5
- Vite 5
- Redis 7

## Getting Started

### Requirements

- JDK 17
- MariaDB 10.x or later
- Redis 7.x for `prod` profile cache

### Environment Variables

프로필은 `local`, `prod`로 분리되어 있습니다.

- `local`: 애플리케이션이 `localhost:3306/commerce`로 직접 연결
- `prod`: MariaDB + Redis를 환경변수에서 읽어서 연결

필수 환경변수:

- `DB_USERNAME`
- `DB_PASSWORD`

선택 환경변수:

- `DB_HOST` default: `localhost`
- `DB_PORT` default: `3306`
- `DB_NAME` default: `commerce`
- `SERVER_PORT` default: `8080`
- `DB_ROOT_PASSWORD` docker MariaDB root 계정 비밀번호
- `REDIS_HOST` default: `localhost` in direct run, `redis` in docker compose
- `REDIS_PORT` default: `6379`
- `REDIS_PASSWORD` optional

프로젝트 루트 `.env` 예시:

```bash
DB_USERNAME=commerce
DB_PASSWORD=commerce1234
DB_ROOT_PASSWORD=root1234
SERVER_PORT=8080
REDIS_PASSWORD=
```

### Docker Services

1. 프로젝트 루트에 `.env` 파일을 생성합니다.
2. 최소한 `DB_USERNAME`, `DB_PASSWORD`, `DB_ROOT_PASSWORD`를 채웁니다.
3. 인프라만 올릴 때:

```bash
docker compose up -d mariadb redis
```

4. 애플리케이션까지 포함해 실행할 때:

```bash
docker compose up --build app
```

5. 상태 확인:

```bash
docker compose ps
```

6. 종료:

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

`bootRun`은 프로젝트 루트의 `.env`를 자동으로 읽습니다. `local`에서는 Redis 없이도 실행되고, 캐시는 메모리 캐시로 동작합니다.

프로덕션 프로필 예시:

```bash
SPRING_PROFILES_ACTIVE=prod ./gradlew bootRun
```

`prod`는 Redis 캐시를 사용합니다.

### Build Container Image

```bash
./gradlew bootJar
docker build -t commerce-app .
```

### 주요 백엔드 API

- `GET /health`
- `GET /api/products`
- `GET /api/products/{productId}`
- `GET /api/products/popular`
- `POST /api/products`
- `GET /api/carts/{customerId}`
- `POST /api/carts/{customerId}/items`
- `DELETE /api/carts/{customerId}/items/{cartItemId}`
- `POST /api/orders`
- `POST /api/orders/{orderId}/payments/complete`
- `GET /api/orders`

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
- 도메인/안정성/운영 단계 `7단계` ~ `9단계` 완료
- 다음 진행 예정: 추가 기능 확장 또는 리팩토링
