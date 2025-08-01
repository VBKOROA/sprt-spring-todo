package indiv.abko.todo.todo.controller;

import indiv.abko.todo.global.dto.ApiResp;
import indiv.abko.todo.todo.dto.TodoCreateReq;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TodoControllerInterTest {
    @Autowired
    private TestRestTemplate testRestTemplate;

    @Nested
    @DisplayName("Todo 생성 테스트")
    class Todo생성테스트 {
        @Test
        @DisplayName("Todo 생성 성공 테스트")
        void 유효한요청이면_Todo가생성된다() {
            // Given
            TodoCreateReq todoCreateReq = TodoCreateReq.builder()
                    .title("테스트")
                    .content("테스트")
                    .author("테스트")
                    .password("테스트")
                    .build();

            // When
            var reqResponse = testRestTemplate.postForEntity("/api/v1/todos", todoCreateReq, ApiResp.class);

            // Then
            assertThat(reqResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);

            var body = reqResponse.getBody();

            assertThat(body).isNotNull();

            Object data = body.data();

            assertThat(data).isInstanceOf(Map.class);
            @SuppressWarnings("unchecked")
            Map<String, Object> map = (Map<String, Object>) data;

            assertThat(map.get("title")).isNotNull();
            assertThat(map.get("content")).isNotNull();
            assertThat(map.get("author")).isNotNull();

            assertThat(map.get("title")).isEqualTo("테스트");
            assertThat(map.get("content")).isEqualTo("테스트");
            assertThat(map.get("author")).isEqualTo("테스트");
        }

        @Test
        @DisplayName("Todo 생성 실패 테스트 - 제목이 없는 경우")
        void 제목이없으면_Todo생성이실패한다() {
            // Given
            TodoCreateReq todoCreateReq = TodoCreateReq.builder()
                    .title(null)
                    .content("테스트")
                    .author("테스트")
                    .password("테스트")
                    .build();

            // When
            var reqResponse = testRestTemplate.postForEntity("/api/v1/todos", todoCreateReq, ApiResp.class);

            // Then
            assertThat(reqResponse.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
            assertThat(reqResponse.getBody()).isNotNull();

            var body =  reqResponse.getBody();

            assertThat(body.status()).isEqualTo(HttpStatus.BAD_REQUEST);
            assertThat(body.data()).isNull();
        }
    }
}
