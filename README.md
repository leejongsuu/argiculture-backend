# 🌾 Agriculture Insurance Backend API

농업보험 관리 시스템의 Spring Boot 기반 백엔드 API 서버입니다. 농업보험 보험료 계산, 날씨 정보 제공, AI 채팅 상담, OAuth2 소셜 로그인 등의 기능을 제공합니다.

## 📋 목차

- [프로젝트 개요](#프로젝트-개요)
- [주요 기능](#주요-기능)
- [기술 스택](#기술-스택)
- [프로젝트 구조](#프로젝트-구조)
- [API 문서](#api-문서)
- [설치 및 실행](#설치-및-실행)
- [환경 설정](#환경-설정)
- [배포](#배포)
- [개발 가이드](#개발-가이드)

## 🎯 프로젝트 개요

농업인과 농업보험 제공자를 위한 종합 농업보험 관리 시스템입니다.

### 핵심 비즈니스 도메인
- **농업보험 보험료 계산**: 67가지 작물에 대한 4가지 보장 유형별 보험료 계산
- **실시간 날씨 정보**: OpenWeatherMap API를 통한 날씨 예보 제공
- **AI 상담 서비스**: RAG 기반 농업보험 관련 질의응답
- **보험상품 관리**: 작물별 보험상품 정보 및 PDF 문서 관리
- **사용자 인증**: OAuth2 기반 소셜 로그인

## ✨ 주요 기능

### 🧮 보험료 계산 시스템
```
지원 보장 유형:
• 수확감소보장 (HARVEST_REDUCTION)
• 생산비보장 (PRODUCTION_COST)
• 원예시설 생산비보장 (FACILITY_PRODUCTION_COST)
• 손해보장 (LOSS)

지원 작물군:
• 밭작물 (FIELD_CROPS)
• 과수작물 (FRUIT_CROPS)
• 원예시설 (HORTICULTURAL_FACILITY)
• 벼맥류 (PADDY_CEREALS)
• 버섯류 (MUSHROOMS)
```

### 🔐 인증 시스템
- OAuth2 소셜 로그인 (Google, Kakao, Naver 등)
- JWT 토큰 기반 인증
- Spring Security 적용

### 💬 AI 채팅 시스템
- WebSocket 기반 실시간 채팅
- 대화 컨텍스트 관리 (슬라이딩 윈도우 방식)
- 비동기 메시지 처리로 순서 보장
- 대화 요약 및 캐싱

### 🌤️ 날씨 정보
- OpenWeatherMap API 연동
- 서울 지역 실시간 날씨
- 오늘/내일 예보 정보

## 🛠 기술 스택

### Backend
- **Framework**: Spring Boot 3.5.0
- **Language**: Java 17
- **Database**: MySQL (Production), H2 (Test)
- **Security**: Spring Security + OAuth2 + JWT
- **Build Tool**: Gradle

### Dependencies
- **Web**: Spring Web, Spring WebFlux
- **Data**: Spring Data JPA
- **Security**: Spring Security, OAuth2 Client, JWT
- **Validation**: Spring Validation
- **Utils**: Lombok
- **Test**: JUnit 5, Spring Boot Test

### Infrastructure
- **Containerization**: Docker
- **Reverse Proxy**: Nginx
- **External APIs**: OpenWeatherMap, FastAPI (AI Chat)

## 📁 프로젝트 구조

```
src/main/java/com/aivle/agriculture/
├── domain/
│   ├── auth/           # 사용자 인증 (OAuth2, JWT)
│   ├── calculate/      # 보험료 계산 로직
│   ├── chat/          # AI 채팅 시스템
│   ├── detail/        # 보험상품 세부정보
│   ├── mainpage/      # 메인페이지 (날씨, 보험정보)
│   └── test/          # 개발/테스트 유틸리티
├── global/
│   ├── base/          # 기본 엔티티 클래스
│   ├── config/        # 설정 클래스들
│   ├── exception/     # 예외 처리
│   ├── response/      # API 응답 표준화
│   └── security/      # 보안 설정 (JWT, OAuth2)
└── AgricultureApplication.java
```

### 도메인별 아키텍처
각 도메인은 다음과 같은 계층 구조를 따릅니다:
```
controller/    # REST API 엔드포인트
service/       # 비즈니스 로직
repository/    # 데이터 접근 계층
entity/        # JPA 엔티티
dto/           # 데이터 전송 객체
```

## 📚 API 문서

### 인증 API (`/api/auth`)
```http
GET /api/auth/login/{provider}  # OAuth2 로그인 시작
GET /api/auth/success          # 로그인 성공 콜백
GET /api/auth/failure          # 로그인 실패 페이지
```

### 보험료 계산 API (`/api/calc`)
```http
POST /api/calc
Content-Type: application/json

{
  "coverageType": "HARVEST_REDUCTION",
  "cropType": "SWEET_POTATO",
  "insuredAmount": 1000000,
  "damageRate": 30.0,
  "selfBurdenRate": 20.0,
  "area": 1000
}
```

### 채팅 API (`/api/chat`)
```http
POST /api/chat
Content-Type: application/json

{
  "conversationId": "uuid-string",
  "question": "벼 농업보험에 대해 알려주세요"
}
```

### WebSocket 채팅
```javascript
// 연결
const socket = new SockJS('/ws');
const stompClient = Stomp.over(socket);

// 메시지 전송
stompClient.send('/app/chat.send', {}, JSON.stringify(message));

// 응답 구독
stompClient.subscribe('/queue/response', callback);
```

### 보험상품 정보 API (`/api/insurance`)
```http
GET /api/insurance/{cropType}   # 작물별 보험상품 정보
```

### 메인페이지 API (`/api/main`)
```http
GET /api/main                   # 날씨 정보 + 보험 정보
```

## 🚀 설치 및 실행

### 사전 요구사항
- Java 17+
- MySQL 8.0+
- Gradle 7.0+

### 로컬 실행
```bash
# 저장소 클론
git clone <repository-url>
cd agriculture-backend

# 의존성 설치 및 빌드
./gradlew build

# 애플리케이션 실행
./gradlew bootRun
```

### Docker 실행
```bash
# Docker 이미지 빌드
docker build -t agriculture-backend .

# 컨테이너 실행
docker run -p 8080:8080 agriculture-backend
```

## ⚙️ 환경 설정

### 필수 환경변수
```properties
# 데이터베이스 설정
SPRING_DATASOURCE_URL=jdbc:mysql://localhost:3306/agriculture
SPRING_DATASOURCE_USERNAME=username
SPRING_DATASOURCE_PASSWORD=password

# OAuth2 설정
SPRING_SECURITY_OAUTH2_CLIENT_REGISTRATION_GOOGLE_CLIENT_ID=your-google-client-id
SPRING_SECURITY_OAUTH2_CLIENT_REGISTRATION_GOOGLE_CLIENT_SECRET=your-google-client-secret

# JWT 설정
JWT_SECRET_KEY=your-jwt-secret-key
JWT_EXPIRATION_TIME=86400000

# 외부 API 설정
OPENWEATHER_API_KEY=your-openweather-api-key
FASTAPI_BASE_URL=http://your-fastapi-server

# CORS 설정
URL_FRONTEND=http://localhost:3000
URL_TEMP=http://localhost:3001
URL_TEMP2=http://localhost:3002
URL_TEMP3=http://localhost:3003
```

### 보안 설정
- **CORS**: 프론트엔드 URL에 대한 CORS 허용
- **Rate Limiting**: Nginx를 통한 요청 속도 제한 (IP당 초당 10건)
- **Connection Limiting**: 동시 연결 제한 (IP당 10개)

## 🐳 배포

### Docker Compose (권장)
```yaml
version: '3.8'
services:
  app:
    build: .
    ports:
      - "8080:8080"
    environment:
      - SPRING_PROFILES_ACTIVE=prod
    depends_on:
      - mysql

  mysql:
    image: mysql:8.0
    environment:
      MYSQL_DATABASE: agriculture
      MYSQL_ROOT_PASSWORD: password
    ports:
      - "3306:3306"

  nginx:
    image: nginx:alpine
    ports:
      - "80:80"
    volumes:
      - ./nginx:/etc/nginx/conf.d
    depends_on:
      - app
```

### 프로덕션 배포 체크리스트
- [ ] 환경변수 설정 확인
- [ ] 데이터베이스 마이그레이션
- [ ] SSL 인증서 설정
- [ ] 로그 수집 설정
- [ ] 모니터링 설정
- [ ] 백업 전략 수립

## 👨‍💻 개발 가이드

### 코딩 컨벤션
- **패키지 구조**: 도메인 중심 설계 (Domain-Driven Design)
- **네이밍**: 한글 주석과 영문 변수명 병행
- **API Response**: `ResponseFactory`를 통한 표준화된 응답 형식
- **예외 처리**: 전역 예외 처리기 적용

### 새로운 도메인 추가 시
1. `domain/{domain-name}` 패키지 생성
2. Controller, Service, Repository, Entity, DTO 클래스 생성
3. 필요시 Config 클래스에 설정 추가
4. 테스트 코드 작성

### 보험료 계산기 추가 시
1. `calculator` 패키지에 새 계산기 클래스 생성
2. `Calculator` 인터페이스 구현
3. `CalculateService`에 계산기 등록
4. 해당 보장 유형을 `CoverageType` enum에 추가

### 채팅 기능 확장 시
- `ConversationContextManager`: 대화 컨텍스트 관리 로직
- `ChatWebSocketHandler`: WebSocket 메시지 처리
- **주의사항**: 메시지 순서 보장을 위해 CompletableFuture 체이닝 사용

## 📊 성능 최적화

### 캐싱 전략
- **대화 컨텍스트**: Spring Cache를 통한 conversation 캐싱
- **슬라이딩 윈도우**: 최근 10개 메시지만 메모리에 유지
- **요약 시스템**: 20개 이상 메시지 시 이전 대화 요약

### 데이터베이스 최적화
- **인덱싱**: `conversationId`에 대한 인덱스 설정
- **페이징**: 대화 이력 조회 시 PageRequest 사용
- **지연 로딸**: `@ManyToOne(fetch = FetchType.LAZY)` 적용