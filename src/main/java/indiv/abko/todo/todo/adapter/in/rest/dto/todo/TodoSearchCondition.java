package indiv.abko.todo.todo.adapter.in.rest.dto.todo;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Todo 검색 조건 DTO")
public record TodoSearchCondition(
    @Schema(description = "정렬 기준", example = "createdAt_desc")
    String orderBy,
    @Schema(description = "제목", example = "Todo 제목")
    String title,
    @Schema(description = "내용", example = "Todo 내용")
    String content,
    @Schema(description = "작성자", example = "Todo 작성자")
    String author
) {
}
