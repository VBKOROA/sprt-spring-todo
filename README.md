# 🚀 Spring Boot Todo 애플리케이션

> 💬 **"단순한 Todo 앱을 넘어, 견고한 백엔드 설계를 경험하다"**

이 프로젝트는 단순한 기능 구현을 넘어, **유지보수성과 확장성**을 고려한 소프트웨어 설계를 학습하고 적용하는 데 중점을 둔 Todo 애플리케이션입니다. 클린 아키텍처와 객체지향의 핵심 원칙을 코드에 반영하고자 노력했습니다.

---

## 🎯 핵심 설계 원칙

이 프로젝트는 다음의 설계 원칙을 기반으로 구현되었습니다.

- **계층형 아키텍처 (Layered Architecture)**
  - `Controller`, `Service`, `Repository`의 역할을 명확히 분리하여 각 계층이 자신의 책임에만 집중하도록 설계했습니다.

- **개방-폐쇄 원칙 (OCP, Open-Closed Principle)**
  - `Specification` 패턴과 `Builder`를 활용하여, 새로운 검색 또는 정렬 조건이 추가되더라도 `Service` 코드의 변경 없이 기능을 확장할 수 있는 구조를 구현했습니다.

- **타입 안정성 (Type Safety)**
  - Lombok의 `@FieldNameConstants`를 이용해 엔티티의 필드명을 문자열이 아닌 열거형(`Enum`)으로 관리합니다. 이를 통해 컴파일 시점에 오타 등을 방지하여 런타임 오류 가능성을 줄였습니다.

- **도메인 엔티티의 책임 (Entity Responsibility)**
  - `Todo` 엔티티가 단순한 데이터 객체를 넘어, 댓글 추가 및 개수 제한과 같은 자체적인 비즈니스 로직을 갖도록 구현하여 객체의 자율성을 높였습니다.

## ✨ 주요 기능

- **동적 검색 및 정렬**
  - JPA Specification과 Builder 패턴을 결합하여, 제목, 내용, 작성자 등 여러 조건으로 할일 목록을 동적으로 검색하고 정렬할 수 있습니다.

- **커스텀 유효성 검사**
  - `@OptionalNotBlank` (null은 허용하지만 공백 문자열은 불허), `@ShouldBase64` 등 재사용 가능한 커스텀 어노테이션을 적용하여 데이터의 정합성을 세밀하게 검증합니다.

- **안전한 객체 매핑**
  - MapStruct의 `qualifiedByName` 옵션을 사용하여, DTO와 엔티티를 변환할 때 `password` 같은 특정 필드에만 암호화 메서드가 적용되도록 정밀하게 제어합니다.

- **비밀번호 기반 인가**
  - jBCrypt 라이브러리로 비밀번호를 안전하게 해싱하여 저장하고, 할일 수정 및 삭제 시 비밀번호를 통해 인가(Authorization)를 구현했습니다.

## 🔬 테스트 전략

코드의 신뢰성과 설계의 견고함을 보장하기 위해 단위 테스트와 통합 테스트를 작성했습니다.

- **통합 테스트 (`@SpringBootTest`)**: API 계층부터 데이터베이스까지 시스템의 전체 흐름을 검증하여 각 계층의 연동과 비즈니스 로직의 정확성을 보장합니다.
- **단위 테스트**: `TodoSortBuilder`와 같이 외부 의존성이 적은 특정 컴포넌트의 논리적 정확성을 독립적으로 검증하여 안정성을 높입니다.

## 🛠️ 적용 기술

| 구분 | 기술 | 설명 |
|---|---|---|
| **Framework** | `Spring Boot 3.5.4` | 안정적이고 빠른 애플리케이션 개발 환경 |
| **Language** | `Java 17` | LTS 버전의 Java |
| **Database** | `Spring Data JPA`, `MySQL`, `H2` | ORM을 통한 객체지향적 데이터 관리 (테스트에는 H2 사용) |
| **Query** | `JPA Specification` | 빌더 패턴과 결합된 타입-세이프 동적 쿼리 |
| **Testing** | `JUnit 5`, `AssertJ`, `@SpringBootTest` | 통합/단위 테스트를 통한 코드 신뢰성 및 설계 증명 |
| **Code-Gen** | `MapStruct`, `Lombok` | 보일러플레이트 코드 자동 생성 및 제거 |
| **Security** | `jBCrypt` | 강력한 해시 함수를 사용한 안전한 비밀번호 저장 |
| **Build Tool** | `Gradle` | 유연하고 빠른 빌드 자동화 도구 |
| **API Docs** | `SpringDoc OpenAPI` | API 명세 자동화 및 Swagger UI 제공 |

## 📁 프로젝트 구조

```
.
├── src
│   └── main
│       └── java
│           └── indiv/abko/todo
│               ├── controller      # 📡 API 엔드포인트 (HTTP 요청/응답 처리)
│               ├── dto             # 📦 계층 간 데이터 전송 객체 (Request/Response)
│               ├── entity          # 🏛️ 핵심 도메인 로직과 상태를 가진 JPA 엔티티
│               ├── mapper          # ↔️ DTO-Entity 변환을 담당하는 MapStruct 매퍼
│               ├── repository      # 💾 데이터 영속성 계층 (JPA, Specification, Sort Builder)
│               ├── service         # 🧠 비즈니스 로직 및 트랜잭션 관리
│               ├── validation      # ✅ 도메인에 특화된 유효성 검사 어노테이션 (@ValidTodoTitle)
│               └── global          # 🌐 전역 설정 및 공통 모듈
│                   ├── config      #   - Swagger 등 애플리케이션의 핵심 설정
│                   ├── dto         #   - 표준 API 응답 DTO (ApiResp)
│                   ├── exception   #   - 글로벌 예외 처리 핸들러
│                   ├── util        #   - 암호화 등 프로젝트 전반에서 사용되는 유틸리티
│                   └── validation  #   - 재사용 가능한 범용 유효성 검사기 및 어노테이션
└── ...
```

## 💾 ERD (Entity-Relationship Diagram)

![ERD](./docs/ERD.svg)

## 🚀 시작하기

1.  **저장소 복제**
    ```bash
    git clone https://github.com/VBKOROA/sprt-spring-todo.git
    ```
2.  **데이터베이스 설정**
    - `src/main/resources/init-db.sql` 을 관리자 계정으로 실행하세요.
3.  **애플리케이션 실행**
    ```bash
    ./gradlew bootRun --args='--spring.profiles.active=prod'
    ```
4.  **Swagger API 문서 확인**
    - 애플리케이션 실행 후, 웹 브라우저에서 `http://localhost:8080/swagger-ui/index.html` 로 접속하여 API 문서를 확인할 수 있습니다.
    - `docs/api_spec.md` 파일에서도 API 명세를 확인할 수 있습니다.
