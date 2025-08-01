package indiv.abko.todo.dto;

import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Todo 응답 DTO")
public record TodoResp(
    @Schema(description = "Todo ID", example = "1")
    Long id,
    @Schema(description = "Todo 제목", example = "테스트")
    String title,
    @Schema(description = "Todo 내용", example = "테스트")
    String content,
    @Schema(description = "Todo 작성자", example = "테스트")
    String author,
    @Schema(description = "Todo 생성 날짜/시간", example = "2025-01-01T00:00:00")
    LocalDateTime createdAt,
    @Schema(description = "Todo 수정 날짜/시간", example = "2025-01-01T00:00:00")
    LocalDateTime modifiedAt
) {
    
}
