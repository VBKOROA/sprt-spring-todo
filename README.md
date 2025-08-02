# 🚀 Spring Boot Todo 애플리케이션

> 💬 **"단순한 Todo 앱을 넘어, 견고한 백엔드 설계를 경험하다"**

이 프로젝트는 단순한 기능 구현을 넘어, **유지보수성과 확장성**을 고려한 소프트웨어 설계를 체험하는 데 중점을 둔 Todo 애플리케이션입니다. 도메인 주도 설계(DDD)의 기본 원칙을 적용하여 각 기능의 독립성을 높이고, 클린 아키텍처를 지향합니다.

---

## 🎯 프로젝트 핵심 목표 및 설계 철학

- **계층형 아키텍처 (Layered Architecture)**: `Presentation` - `Application` - `Domain` - `Infrastructure`로 이어지는 명확한 계층 구조를 적용하여 각 레이어의 책임을 분명히 하고, 의존성 규칙을 준수하여 유연하고 확장 가능한 설계를 구현했습니다.
- **도메인 모델 중심 설계**: 도메인(`Todo`, `Comment`)이 비즈니스 로직의 중심이 되도록 설계했습니다. 특히 각 도메인 객체가 스스로의 상태와 행위를 관리하도록 구현하여, 객체의 자율성과 캡슐화를 극대화했습니다.
- **타입-세이프 동적 쿼리**: **QueryDSL**을 도입하여, 컴파일 타임에 타입을 검증할 수 있는 안전하고 직관적인 동적 쿼리를 작성했습니다. 이를 통해 런타임에 발생할 수 있는 쿼리 오류를 줄이고, 복잡한 검색 조건을 명확한 코드로 구현했습니다.
- **체계적인 예외 처리**: 애플리케이션 전역에서 발생할 수 있는 예외를 `@RestControllerAdvice`를 통해 일관되게 처리하고, 도메인 비즈니스 규칙에 따른 예외는 `BusinessException`으로 명확하게 정의하여 코드의 안정성과 예측 가능성을 높였습니다.

## ✨ 주요 기능 하이라이트

- **할일(Todo) 및 댓글(Comment) 관리**: 기본적인 CRUD 기능을 완벽하게 지원합니다.
- **QueryDSL을 이용한 동적 검색**: 작성자, 제목, 내용 등 다양한 조건에 따라 할일 목록을 동적으로 조회할 수 있습니다.
- **커스텀 데이터 유효성 검사**: `@Valid` 어노테이션과 더불어 `@ShouldBase64`, `@OptionalNotBlank` 등 직접 구현한 커스텀 Validation 어노테이션을 활용하여 API 요청 데이터의 정합성을 세밀하게 보장합니다.
- **Base64를 이용한 비밀번호 처리**: 할일 수정/삭제 시, `X-Todo-Password` 헤더를 통해 Base64로 인코딩된 비밀번호를 받아 처리하여 보안성을 강화했습니다.

## 🛠️ 적용 기술

| 구분 | 기술 | 설명 |
|---|---|---|
| **Framework** | `Spring Boot 3.5.4` | 강력하고 빠른 애플리케이션 개발 환경 | 
| **Language** | `Java 17` | 안정성과 성능이 검증된 LTS 버전 | 
| **Database** | `Spring Data JPA`, `MySQL`, `H2` | ORM을 통한 객체지향적 데이터 관리 및 테스트 환경 구축 | 
| **Query** | `QueryDSL` | 타입-세이프(Type-Safe)한 동적 쿼리 빌더 | 
| **Code-Gen** | `Lombok` | 보일러플레이트 코드 자동 생성 및 제거 | 
| **Security** | `jBCrypt` | 안전한 비밀번호 해싱을 위한 라이브러리 |
| **Build Tool** | `Gradle` | 유연하고 빠른 빌드 자동화 도구 | 
| **API Docs** | `SpringDoc OpenAPI` | API 명세 자동화 및 Swagger UI 제공 |

## 📁 프로젝트 구조

```
.
└── src
    └── main
        └── java
            └── indiv/abko/todo/todo
                ├── application     # 📖 애플리케이션 계층: 서비스 로직, DTO 변환
                │   └── service
                ├── domain          # 🧠 도메인 계층: 핵심 비즈니스 로직, 엔티티, VO, 리포지토리 인터페이스
                │   ├── vo
                │   └── repository
                ├── infra           # 🔌 인프라 계층: 외부 시스템 연동, DB, Security 구현
                │   ├── persistence
                │   └── security
                └── presentation    # 📡 프레젠테이션 계층: API 엔드포인트, DTO, 예외 처리, 유효성 검사
                    ├── exception
                    ├── rest
                    └── validation
```

## 💾 ERD (Entity-Relationship Diagram)

![ERD](./docs/ERD.svg)

## 🚀 시작하기

1.  **저장소 복제**
    ```bash
    git clone https://github.com/VBKOROA/sprt-spring-todo.git
    ```
2.  **데이터베이스 설정**
    - `src/main/resources/application.properties` (또는 `application.yml`) 파일을 생성하고, 본인의 MySQL 데이터베이스 정보를 입력하세요.
3.  **애플리케이션 실행**
    ```bash
    ./gradlew bootRun
    ```
4.  **Swagger API 문서 확인**
    - 애플리케이션 실행 후, 웹 브라우저에서 `http://localhost:8080/swagger-ui/index.html` 로 접속하여 API 문서를 확인할 수 있습니다.
    - `docs/api_spec.md` 파일에서도 API 명세를 확인할 수 있습니다.
