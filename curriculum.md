# 이커머스 풀스택 학습 커리큘럼

대상: Java + Spring Boot 4년차 백엔드 개발자  
목표: Kotlin + Spring Boot + React + TypeScript 기반 이커머스 풀스택 실무 전환  
핵심 스택: Kotlin, Spring Boot, JPA, QueryDSL, MariaDB, Redis, React, TypeScript, HTML/CSS, REST API, Docker  
IDE: IntelliJ IDEA, VS Code  
최종 산출물: 이커머스 주문/정산/재고 백엔드와 상품/장바구니/주문 화면 프론트엔드를 포함한 실무형 프로젝트 1개  
학습 방식: Codex와 step by step으로 직접 구현하며 진행  
진행 원칙: 완료된 항목은 별도 진행관리 md 파일에서 체크하고, 다음 단계에서는 미완료 항목만 이어서 진행 및 리뷰

---

# 문서 구성

이 학습 체계는 아래 2개의 md 파일을 기준으로 운영한다.

1. curriculum.md  
   전체 커리큘럼, 학습 순서, 각 단계별 구현 목표, 산출물 정의

2. progress.md  
   실제 구현 완료 여부를 기록하는 체크리스트 문서  
   Codex는 이 파일을 기준으로 완료/미완료 상태를 확인하고, 다음 step에서는 미완료 항목만 진행해야 한다.

문서 운영 원칙은 아래와 같다.

- `curriculum.md`는 학습 체계의 정본이다.
- `progress.md`는 반드시 이 문서의 step 번호와 이름을 그대로 따른다.
- step 번호, 단계 이름, 완료 기준은 두 문서에서 다르게 관리하지 않는다.
- 기존 백엔드 완료 이력은 유지하고, 퍼블리싱/프론트엔드 단계는 새로운 미완료 step으로 추가한다.

---

# Codex 작업 규칙

Codex에게는 아래 원칙으로 작업을 요청한다.

## 1. 진행 방식
- 항상 `progress.md`를 먼저 확인한다.
- 완료된 항목은 다시 구현하지 않는다.
- 가장 먼저 등장하는 미완료 step 1개만 대상으로 다음 작업을 진행한다.
- 현재 step 범위를 넘어서 다음 기능을 미리 구현하지 않는다.
- 프로젝트가 비어 있는 최초 시작 시에도 예외 없이 `progress.md`의 가장 앞 미완료 step부터 진행한다.
- 매 step이 끝날 때마다 아래 내용을 반드시 정리한다.
  - 이번 step에서 구현한 내용
  - 왜 이렇게 구현했는지
  - 리뷰 포인트
  - 다음 step에서 진행할 미완료 항목

## 2. 완료 처리 방식
`progress.md`에서 각 항목은 아래처럼 관리한다.

```md
- [ ] STEP 00-01. IntelliJ에서 Spring Boot Kotlin 프로젝트 생성
- [x] STEP 00-02. Gradle Kotlin DSL 정리
- [ ] STEP 00-03. 필수 의존성 추가
```

완료 처리 기준은 아래와 같다.

- 구현, 실행, 기본 검증까지 끝난 항목만 `[x]` 처리한다.
- 코드만 작성했고 실행 검증이 안 된 항목은 `[ ]` 유지 또는 별도 메모로 남긴다.
- 하나의 step 안에 여러 작업이 섞이면, step 경계를 다시 나누고 나서 진행한다.

## 3. 리뷰 방식
각 step 완료 후 Codex는 아래 기준으로 리뷰한다.

- Kotlin스럽게 작성되었는지
- Spring Boot 관점에서 구조가 적절한지
- JPA 매핑이 안전한지
- QueryDSL 확장 가능성이 있는지
- React/TypeScript 구조가 자연스러운지
- 퍼블리싱 구조와 반응형 구성이 적절한지
- 실무 이커머스 도메인으로 자연스러운지
- 개선할 부분이 무엇인지

## 4. 구현 후 출력 형식
step 완료 후에는 아래 형식으로 정리한다.

- 이번 step에서 생성/수정한 파일
- 핵심 구현 내용 요약
- 설계 이유
- Kotlin / TypeScript 관점 코드 리뷰
- Spring Boot / React 구조 리뷰
- JPA / QueryDSL / API 연동 리뷰 포인트
- 개선 포인트
- `progress.md`에서 완료 처리할 항목
- 다음으로 진행할 가장 앞의 미완료 항목

출력 원칙은 아래와 같다.

- 전체 코드를 응답에 그대로 모두 붙여넣지 않는다.
- 변경 파일 기준으로 핵심 코드와 의도를 요약한다.
- 필요할 때만 일부 코드 블록이나 핵심 메서드만 발췌한다.

---

# 전체 학습 로드맵

총 학습 구조는 아래 순서로 진행한다.

- 0단계: 프로젝트 생성 및 실행 환경 설정
- 1단계: Kotlin 문법 적응
- 2단계: Spring Boot Kotlin 기본 구조 구현
- 3단계: Kotlin + JPA + QueryDSL 심화
- 4단계: 퍼블리싱 기초 및 화면 설계
- 5단계: React + TypeScript 프론트엔드 구조 구현
- 6단계: 프론트엔드 API 연동 및 상태관리
- 7단계: 이커머스 핵심 도메인 구현
- 8단계: 트랜잭션 / 동시성 / 테스트
- 9단계: 운영 관점 고도화

---

# 시간 기준

이 커리큘럼은 주 단위가 아니라 hour 단위로 진행한다.  
권장 총 학습 시간은 약 180~240시간이다.

- 프로젝트 세팅: 8~12h
- Kotlin 문법 전환: 18~24h
- Spring Boot Kotlin 구조화: 18~24h
- JPA + QueryDSL: 24~32h
- 퍼블리싱 및 UI 설계: 18~24h
- React + TypeScript 구조화: 20~28h
- 프론트 API 연동 및 상태관리: 20~28h
- 이커머스 도메인 구현: 24~32h
- 테스트/동시성/성능: 16~24h
- 배포/정리: 8~12h

---

# 0단계. 프로젝트 생성 및 초기 설정 (8~12h)

목표: Kotlin + Spring Boot + JPA + QueryDSL 기반 프로젝트를 직접 생성하고, 이후 학습이 가능한 기본 실행 환경을 만든다.

## 구현 목표
- IntelliJ에서 Kotlin + Spring Boot 프로젝트 생성
- Gradle Kotlin DSL 구성
- Java/Kotlin 버전 설정
- Spring Web / Spring Data JPA / Validation / QueryDSL / MariaDB Driver 의존성 추가
- application.yml 작성
- 로컬 DB 연결
- 패키지 구조 생성
- 공통 예외/응답 패키지 기본 골격 생성
- 빌드 및 실행 확인

## 세부 항목
- STEP 00-01. IntelliJ에서 Spring Boot Kotlin 프로젝트 생성
- STEP 00-02. Gradle Kotlin DSL 정리
- STEP 00-03. 필수 의존성 추가
- STEP 00-04. application.yml 설정
- STEP 00-05. docker mariadb 실행 환경 구성
- STEP 00-06. QueryDSL 설정 및 QClass 생성 확인
- STEP 00-07. 기본 패키지 구조 생성
- STEP 00-08. 헬스체크 API 작성
- STEP 00-09. DB 연결 테스트
- STEP 00-10. 첫 커밋

## 산출물
- 애플리케이션 정상 실행
- `/health` API 200 응답
- Entity 작성 시 QClass 생성 가능 상태
- 로컬 DB 연결 확인
- Git 초기 커밋 완료

---

# 1단계. Kotlin 기초 전환 (18~24h)

목표: Java 기반 사고방식을 Kotlin 스타일로 바꾸고, 이후 Spring 코드 작성에 익숙해진다.

## 학습 내용
- val, var
- null safety
- safe call, Elvis operator
- let, run, apply, also
- 함수 선언식
- default parameter
- named argument
- data class
- enum class
- sealed class
- object singleton
- extension function
- collection transformation

## 구현 실습
- Java DTO를 Kotlin data class로 변경
- Java util 성격 코드를 Kotlin extension/function으로 변경
- stream 기반 코드를 Kotlin collection DSL로 변경

## 세부 항목
- STEP 01-01. DTO를 Kotlin data class로 전환
- STEP 01-02. null-safe 처리 리팩토링
- STEP 01-03. stream 로직을 Kotlin collection 함수로 전환
- STEP 01-04. 공통 유틸 함수 정리

---

# 2단계. Spring Boot Kotlin 기본 구조 구현 (18~24h)

목표: Kotlin 기반으로 Controller / Service / Repository 계층을 자연스럽게 구성한다.

## 구현 목표
- Controller 작성
- Service 작성
- Repository 작성
- 공통 응답 구조 작성
- 예외 처리 작성
- Validation 작성
- configuration 분리

## 세부 항목
- STEP 02-01. 공통 응답 객체 구현
- STEP 02-02. 공통 예외 구조 구현
- STEP 02-03. Global Exception Handler 구현
- STEP 02-04. 상품 조회 API 구현
- STEP 02-05. 상품 등록 API 구현
- STEP 02-06. 요청 DTO validation 적용

---

# 3단계. Kotlin + JPA + QueryDSL 심화 (24~32h)

목표: 실무 이커머스 서비스에서 필요한 저장/조회 구조를 안정적으로 만든다.

## 구현 목표
- Kotlin entity 설계
- 연관관계 매핑
- 지연 로딩 이해
- fetch join 적용
- N+1 방지
- QueryDSL 기반 동적 검색
- DTO projection
- paging/sorting

## 세부 항목
- STEP 03-01. Product 엔티티 설계
- STEP 03-02. ProductOption 엔티티 설계
- STEP 03-03. Seller 엔티티 설계
- STEP 03-04. JPA 연관관계 매핑
- STEP 03-05. QueryDSL custom repository 생성
- STEP 03-06. 상품 검색 조건 조회 구현
- STEP 03-07. 페이징/정렬 구현
- STEP 03-08. fetch join 적용 리뷰

---

# 4단계. 퍼블리싱 기초 및 화면 설계 (18~24h)

목표: 이커머스 화면 구조와 반응형 마크업을 설계하고, 프론트엔드 구현 전에 UI 골격을 만든다.

## 구현 목표
- 화면 정보 구조 정의
- 공통 레이아웃 정의
- 상품/장바구니/주문 화면 퍼블리싱
- 반응형 설계
- 공통 UI 컴포넌트 시각 규칙 정의

## 세부 항목
- STEP 04-01. 화면 정보 구조 설계
- STEP 04-02. 공통 레이아웃 구성
- STEP 04-03. 상품 목록 화면 퍼블리싱
- STEP 04-04. 상품 상세 화면 퍼블리싱
- STEP 04-05. 장바구니 화면 퍼블리싱
- STEP 04-06. 주문/결제 화면 퍼블리싱
- STEP 04-07. 반응형 대응
- STEP 04-08. 공통 UI 컴포넌트 정리

---

# 5단계. React + TypeScript 프론트엔드 구조 구현 (20~28h)

목표: React 기반 화면 구조, 라우팅, 컴포넌트 계층, 타입 중심 UI 개발 흐름을 정착시킨다.

## 구현 목표
- React + TypeScript 프로젝트 구조 구성
- 라우팅 구성
- 페이지/컴포넌트 분리
- 공통 UI 재사용 구조 정의
- 타입 정의 분리

## 세부 항목
- STEP 05-01. React + TypeScript 프로젝트 구조 정리
- STEP 05-02. 라우팅 구성
- STEP 05-03. 공통 레이아웃 컴포넌트 구현
- STEP 05-04. 상품 목록 페이지 컴포넌트화
- STEP 05-05. 상품 상세 페이지 컴포넌트화
- STEP 05-06. 장바구니 페이지 컴포넌트화
- STEP 05-07. 타입 정의 분리
- STEP 05-08. 공통 UI 컴포넌트 재사용 구조 정리

---

# 6단계. 프론트엔드 API 연동 및 상태관리 (20~28h)

목표: 백엔드 API와 프론트엔드 화면을 연결하고, 실무형 데이터 흐름을 만든다.

## 구현 목표
- API 클라이언트 계층 분리
- 서버 데이터 연동
- 로딩/에러/빈 상태 처리
- 장바구니/주문 상태관리
- 폼 입력/검증 처리

## 세부 항목
- STEP 06-01. API 클라이언트 계층 구성
- STEP 06-02. 상품 목록 API 연동
- STEP 06-03. 상품 상세 API 연동
- STEP 06-04. 장바구니 상태관리 적용
- STEP 06-05. 주문 요청 흐름 연결
- STEP 06-06. 로딩/에러 상태 처리
- STEP 06-07. 폼 입력/검증 처리
- STEP 06-08. 프론트엔드 환경변수 및 실행 설정 정리

---

# 7단계. 이커머스 핵심 도메인 구현 (24~32h)

목표: 주문, 결제, 재고, 정산 흐름을 가진 이커머스 백엔드의 핵심 구조를 만든다.

## 핵심 도메인
- Product
- ProductOption
- Seller
- Cart
- Order
- OrderItem
- Payment
- Inventory
- Settlement
- SettlementDeduction
- AdCostHistory

## 세부 항목
- STEP 07-01. 장바구니 엔티티 및 API 구현
- STEP 07-02. 주문 엔티티 설계
- STEP 07-03. 주문 생성 API 구현
- STEP 07-04. 결제 완료 처리 구현
- STEP 07-05. 재고 차감 처리 구현
- STEP 07-06. 주문 목록 검색 API 구현
- STEP 07-07. 정산 엔티티 설계
- STEP 07-08. 셀러 주간 정산 계산 로직 구현
- STEP 07-09. 광고비 차감 로직 구현
- STEP 07-10. 지급 예정 금액 계산 구현

---

# 8단계. 트랜잭션 / 동시성 / 테스트 (16~24h)

목표: 운영 가능한 수준의 안정성을 확보한다.

## 구현 목표
- transaction 경계 설정
- readOnly transaction 적용
- 재고 동시성 제어
- 중복 주문 방지
- 테스트 코드 작성

## 세부 항목
- STEP 08-01. 주문 트랜잭션 정리
- STEP 08-02. 재고 동시성 제어 방식 적용
- STEP 08-03. 낙관적 락 또는 비관적 락 비교 적용
- STEP 08-04. 서비스 단위 테스트 작성
- STEP 08-05. Repository 통합 테스트 작성
- STEP 08-06. QueryDSL 조회 테스트 작성

---

# 9단계. 운영 관점 고도화 (8~12h)

목표: 실무 서비스처럼 운영 가능한 구조로 마무리한다.

## 구현 목표
- Redis 캐시
- 로그 구조화
- Docker 실행
- 배포 기초
- README 정리

## 세부 항목
- STEP 09-01. Redis 캐시 적용
- STEP 09-02. 인기 상품 조회 캐시화
- STEP 09-03. Dockerfile 작성
- STEP 09-04. docker compose 구성
- STEP 09-05. 배포용 application 설정 분리
- STEP 09-06. README 정리

---

# progress.md 템플릿

아래 내용은 그대로 `progress.md`로 저장해서 사용한다.

```md
# Kotlin + Spring Boot + React 이커머스 풀스택 학습 진행표

last_reviewed_step: STEP 00-00
current_focus: 프로젝트 생성 및 환경 설정
review_rule: 완료된 항목만 x 처리, 다음 step은 가장 앞의 미완료 항목부터 진행

## 0단계. 프로젝트 생성 및 초기 설정
- [ ] STEP 00-01. IntelliJ에서 Spring Boot Kotlin 프로젝트 생성
- [ ] STEP 00-02. Gradle Kotlin DSL 정리
- [ ] STEP 00-03. 필수 의존성 추가
- [ ] STEP 00-04. application.yml 설정
- [ ] STEP 00-05. docker mariadb 실행 환경 구성
- [ ] STEP 00-06. QueryDSL 설정 및 QClass 생성 확인
- [ ] STEP 00-07. 기본 패키지 구조 생성
- [ ] STEP 00-08. 헬스체크 API 작성
- [ ] STEP 00-09. DB 연결 테스트
- [ ] STEP 00-10. 첫 커밋

## 1단계. Kotlin 기초 전환
- [ ] STEP 01-01. DTO를 Kotlin data class로 전환
- [ ] STEP 01-02. null-safe 처리 리팩토링
- [ ] STEP 01-03. stream 로직을 Kotlin collection 함수로 전환
- [ ] STEP 01-04. 공통 유틸 함수 정리

## 2단계. Spring Boot Kotlin 기본 구조 구현
- [ ] STEP 02-01. 공통 응답 객체 구현
- [ ] STEP 02-02. 공통 예외 구조 구현
- [ ] STEP 02-03. Global Exception Handler 구현
- [ ] STEP 02-04. 상품 조회 API 구현
- [ ] STEP 02-05. 상품 등록 API 구현
- [ ] STEP 02-06. 요청 DTO validation 적용

## 3단계. Kotlin + JPA + QueryDSL 심화
- [ ] STEP 03-01. Product 엔티티 설계
- [ ] STEP 03-02. ProductOption 엔티티 설계
- [ ] STEP 03-03. Seller 엔티티 설계
- [ ] STEP 03-04. JPA 연관관계 매핑
- [ ] STEP 03-05. QueryDSL custom repository 생성
- [ ] STEP 03-06. 상품 검색 조건 조회 구현
- [ ] STEP 03-07. 페이징/정렬 구현
- [ ] STEP 03-08. fetch join 적용 리뷰

## 4단계. 퍼블리싱 기초 및 화면 설계
- [ ] STEP 04-01. 화면 정보 구조 설계
- [ ] STEP 04-02. 공통 레이아웃 구성
- [ ] STEP 04-03. 상품 목록 화면 퍼블리싱
- [ ] STEP 04-04. 상품 상세 화면 퍼블리싱
- [ ] STEP 04-05. 장바구니 화면 퍼블리싱
- [ ] STEP 04-06. 주문/결제 화면 퍼블리싱
- [ ] STEP 04-07. 반응형 대응
- [ ] STEP 04-08. 공통 UI 컴포넌트 정리

## 5단계. React + TypeScript 프론트엔드 구조 구현
- [ ] STEP 05-01. React + TypeScript 프로젝트 구조 정리
- [ ] STEP 05-02. 라우팅 구성
- [ ] STEP 05-03. 공통 레이아웃 컴포넌트 구현
- [ ] STEP 05-04. 상품 목록 페이지 컴포넌트화
- [ ] STEP 05-05. 상품 상세 페이지 컴포넌트화
- [ ] STEP 05-06. 장바구니 페이지 컴포넌트화
- [ ] STEP 05-07. 타입 정의 분리
- [ ] STEP 05-08. 공통 UI 컴포넌트 재사용 구조 정리

## 6단계. 프론트엔드 API 연동 및 상태관리
- [ ] STEP 06-01. API 클라이언트 계층 구성
- [ ] STEP 06-02. 상품 목록 API 연동
- [ ] STEP 06-03. 상품 상세 API 연동
- [ ] STEP 06-04. 장바구니 상태관리 적용
- [ ] STEP 06-05. 주문 요청 흐름 연결
- [ ] STEP 06-06. 로딩/에러 상태 처리
- [ ] STEP 06-07. 폼 입력/검증 처리
- [ ] STEP 06-08. 프론트엔드 환경변수 및 실행 설정 정리

## 7단계. 이커머스 핵심 도메인 구현
- [ ] STEP 07-01. 장바구니 엔티티 및 API 구현
- [ ] STEP 07-02. 주문 엔티티 설계
- [ ] STEP 07-03. 주문 생성 API 구현
- [ ] STEP 07-04. 결제 완료 처리 구현
- [ ] STEP 07-05. 재고 차감 처리 구현
- [ ] STEP 07-06. 주문 목록 검색 API 구현
- [ ] STEP 07-07. 정산 엔티티 설계
- [ ] STEP 07-08. 셀러 주간 정산 계산 로직 구현
- [ ] STEP 07-09. 광고비 차감 로직 구현
- [ ] STEP 07-10. 지급 예정 금액 계산 구현

## 8단계. 트랜잭션 / 동시성 / 테스트
- [ ] STEP 08-01. 주문 트랜잭션 정리
- [ ] STEP 08-02. 재고 동시성 제어 방식 적용
- [ ] STEP 08-03. 낙관적 락 또는 비관적 락 비교 적용
- [ ] STEP 08-04. 서비스 단위 테스트 작성
- [ ] STEP 08-05. Repository 통합 테스트 작성
- [ ] STEP 08-06. QueryDSL 조회 테스트 작성

## 9단계. 운영 관점 고도화
- [ ] STEP 09-01. Redis 캐시 적용
- [ ] STEP 09-02. 인기 상품 조회 캐시화
- [ ] STEP 09-03. Dockerfile 작성
- [ ] STEP 09-04. docker compose 구성
- [ ] STEP 09-05. 배포용 application 설정 분리
- [ ] STEP 09-06. README 정리
```

---

# Codex 요청 프롬프트 예시

아래 프롬프트로 Codex에게 작업을 넘기면 된다.

```md
너는 Kotlin + Spring Boot + React + TypeScript 학습을 돕는 시니어 풀스택 엔지니어이자 코드 리뷰어다.

반드시 아래 순서로 작업해라.
1. progress.md를 먼저 확인한다.
2. [ ] 상태인 가장 앞 단계 1개만 진행한다.
3. 이미 완료된 [x] 항목은 다시 건드리지 않는다.
4. 현재 step 범위를 넘어서 미리 다음 기능을 구현하지 않는다.
5. 구현 후에는 아래 형식으로 정리한다.
   - 이번 step에서 생성/수정한 파일
   - 핵심 구현 내용 요약
   - 구현 이유
   - Kotlin / TypeScript 관점 코드 리뷰
   - Spring Boot / React 구조 리뷰
   - JPA / QueryDSL / API 연동 리뷰 포인트
   - 개선 포인트
   - progress.md에서 완료 처리할 항목
   - 다음 step에서 진행할 미완료 항목

기술 조건은 아래와 같다.
- Kotlin + Spring Boot
- React + TypeScript
- 이커머스 도메인
- JPA + QueryDSL 사용
- 실무형 구조로 작성
- IntelliJ / VS Code 기준으로 설명

응답 시 전체 코드를 모두 나열하지 말고, 변경된 파일 기준으로 핵심 코드와 설계를 요약해라.

지금은 progress.md 기준으로 가장 먼저 미완료된 step부터 진행해라.
```

---

# 실전용 Codex 초기 프롬프트

아래 프롬프트는 매 step마다 그대로 붙여넣어 사용할 수 있는 실전 버전이다.

```md
너는 시니어 풀스택 엔지니어이자 코드 리뷰어다.

목표는 Kotlin + Spring Boot 백엔드와 React + TypeScript 프론트엔드를 step by step으로 구현하면서 이커머스 풀스택 프로젝트를 학습하는 것이다.

반드시 아래 규칙을 지켜라.

[작업 규칙]
1. progress.md를 가장 먼저 읽는다.
2. 가장 먼저 등장하는 [ ] 미완료 step 하나만 진행한다.
3. 이미 [x] 완료된 step은 절대 다시 수정하지 않는다.
4. 현재 step 범위를 넘어서 미리 다음 기능을 구현하지 않는다.
5. Kotlin스럽고 실무적인 코드로 작성한다.
6. React + TypeScript 구조도 실무적으로 작성한다.
7. JPA + QueryDSL 확장성과 프론트 API 연동 흐름을 함께 고려한다.
8. 이커머스 도메인(Product, Cart, Order, Settlement)에 자연스럽게 이어질 수 있게 설계한다.

[구현 후 반드시 출력]
1. 이번 step에서 생성/수정한 파일 목록
2. 핵심 구현 내용 요약
3. 왜 이렇게 설계했는지
4. Kotlin / TypeScript 관점 코드 리뷰
5. Spring Boot / React 구조 리뷰
6. JPA / QueryDSL / API 연동 리뷰 포인트
7. 다음 step 진입 전 개선하면 좋은 점
8. progress.md 완료 처리 항목
9. 다음으로 진행할 가장 앞의 미완료 항목

[출력 제약]
- 전체 코드를 응답에 전부 붙여넣지 않는다.
- 변경 파일 기준으로 핵심 코드와 의도를 요약한다.
- 필요한 경우에만 일부 코드 블록을 발췌한다.

[기술 스택]
- Kotlin
- Spring Boot
- JPA
- QueryDSL
- MariaDB
- React
- TypeScript
- Docker
- IntelliJ
- VS Code
- 이커머스 도메인

[시작 규칙]
- 프로젝트가 비어 있어도 progress.md의 가장 앞 미완료 step부터 시작한다.
- 초기 시작 순서는 아래와 같아야 한다.
  - STEP 00-01. IntelliJ에서 Spring Boot Kotlin 프로젝트 생성
  - STEP 00-02. Gradle Kotlin DSL 정리
  - STEP 00-03. 필수 의존성 추가
  - STEP 00-04. application.yml 설정
  - STEP 00-05. docker mariadb 실행 환경 구성

지금은 progress.md 기준으로 가장 먼저 미완료된 step 1개만 진행해라.
```

---

# 추천 시작 순서

가장 첫 시작은 반드시 아래 순서로 진행한다.

1. STEP 00-01. IntelliJ에서 Spring Boot Kotlin 프로젝트 생성
2. STEP 00-02. Gradle Kotlin DSL 정리
3. STEP 00-03. 필수 의존성 추가
4. STEP 00-04. application.yml 설정
5. STEP 00-05. docker mariadb 실행 환경 구성
6. STEP 00-06. QueryDSL 설정 및 QClass 생성 확인

즉, 이 문서는 처음부터 프로젝트 생성과 설정부터 시작하도록 구성하며, 실제 진행 제어는 항상 `progress.md`의 가장 앞 미완료 step 기준으로 한다.
