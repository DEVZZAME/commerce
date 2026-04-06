# Kotlin + Spring Boot + React 이커머스 풀스택 학습 진행표

last_reviewed_step: STEP 01-04
current_focus: 공통 응답 객체 구현
review_rule: 완료된 항목만 x 처리, 다음 step은 가장 앞의 미완료 항목부터 진행

## 0단계. 프로젝트 생성 및 초기 설정
- [x] STEP 00-01. IntelliJ에서 Spring Boot Kotlin 프로젝트 생성
- [x] STEP 00-02. Gradle Kotlin DSL 정리
- [x] STEP 00-03. 필수 의존성 추가
- [x] STEP 00-04. application.yml 설정
- [x] STEP 00-05. docker mariadb 실행 환경 구성
- [x] STEP 00-06. QueryDSL 설정 및 QClass 생성 확인
- [x] STEP 00-07. 기본 패키지 구조 생성
- [x] STEP 00-08. 헬스체크 API 작성
- [x] STEP 00-09. DB 연결 테스트
- [x] STEP 00-10. 첫 커밋

## 1단계. Kotlin 기초 전환
- [x] STEP 01-01. DTO를 Kotlin data class로 전환
- [x] STEP 01-02. null-safe 처리 리팩토링
- [x] STEP 01-03. stream 로직을 Kotlin collection 함수로 전환
- [x] STEP 01-04. 공통 유틸 함수 정리

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
