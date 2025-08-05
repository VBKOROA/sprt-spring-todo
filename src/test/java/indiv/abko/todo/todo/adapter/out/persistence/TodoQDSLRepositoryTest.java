package indiv.abko.todo.todo.adapter.out.persistence;

import indiv.abko.todo.global.config.QueryDslConfig;
import indiv.abko.todo.todo.adapter.out.persistence.entity.TodoJpaEntity;
import indiv.abko.todo.todo.application.port.in.command.SearchTodosCommand;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@DataJpaTest
@Import({QueryDslConfig.class, TodoQDSLRepository.class})
class TodoQDSLRepositoryTest {

    @Autowired
    private EntityManager em;

    @Autowired
    private TodoQDSLRepository todoQDSLRepository;

    @BeforeEach
    void setUp() {
        em.persist(TodoJpaEntity.builder().title("제목1").content("내용1").author("userA").password("pass1").build());
        em.flush();
        em.persist(TodoJpaEntity.builder().title("제목2").content("내용2").author("userB").password("pass2").build());
        em.flush();
        em.persist(TodoJpaEntity.builder().title("제목3").content("내용3").author("userC").password("pass3").build());
        em.flush();
        em.clear();
    }

    @Test
    @DisplayName("동적 검색 - 성공 케이스: 제목으로 검색")
    void 제목으로_할일을검색해야한다() {
        // given
        SearchTodosCommand command = SearchTodosCommand.builder().title("제목2").build();

        // when
        List<TodoJpaEntity> result = todoQDSLRepository.search(command.toCriteria());

        // then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getTitle()).isEqualTo("제목2");
    }

    @Test
    @DisplayName("동적 검색 - 성공 케이스: 조건 없음")
    void 조건이없을때_모든할일을조회해야한다() {
        // given
        SearchTodosCommand command = SearchTodosCommand.builder().build();

        // when
        List<TodoJpaEntity> result = todoQDSLRepository.search(command.toCriteria());

        // then
        assertThat(result).hasSize(3);
    }

    @Test
    @DisplayName("동적 검색 - 결과 없음 케이스: 일치하는 제목 없음")
    void 일치하는제목이없으면_빈리스트를반환해야한다() {
        // given
        SearchTodosCommand command = SearchTodosCommand.builder().title("존재하지 않는 제목").build();

        // when
        List<TodoJpaEntity> result = todoQDSLRepository.search(command.toCriteria());

        // then
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("정렬 - 성공 케이스: 생성일 오름차순 정렬")
    void 생성일오름차순으로_정렬되어야한다() {
        // given
        SearchTodosCommand command = SearchTodosCommand.builder().orderBy("createdAt_asc").build();

        // when
        List<TodoJpaEntity> result = todoQDSLRepository.search(command.toCriteria());

        // then
        assertThat(result).hasSize(3);
        assertThat(result.get(0).getTitle()).isEqualTo("제목1");
        assertThat(result.get(1).getTitle()).isEqualTo("제목2");
        assertThat(result.get(2).getTitle()).isEqualTo("제목3");
    }

    @Test
    @DisplayName("정렬 - 엣지 케이스: 유효하지 않은 속성명")
    void 유효하지않은속성명으로정렬요청시_기본정렬이적용되어야한다() {
        // given
        SearchTodosCommand command = SearchTodosCommand.builder().orderBy("invalidProperty_desc").build();

        // when
        List<TodoJpaEntity> result = todoQDSLRepository.search(command.toCriteria());

        // then
        assertThat(result).hasSize(3);
        // 기본 정렬은 modifiedAt 내림차순이므로, 가장 나중에 추가된 "제목3"이 맨 앞에 와야 한다.
        assertThat(result.get(0).getTitle()).isEqualTo("제목3");
    }

    @Test
    @DisplayName("정렬 - 엣지 케이스: 잘못된 정렬 형식")
    void 잘못된형식으로정렬요청시_기본정렬이적용되어야한다() {
        // given
        SearchTodosCommand command = SearchTodosCommand.builder().orderBy("title_").build();

        // when
        List<TodoJpaEntity> result = todoQDSLRepository.search(command.toCriteria());

        // then
        assertThat(result).hasSize(3);
        assertThat(result.get(0).getTitle()).isEqualTo("제목3");
    }
}