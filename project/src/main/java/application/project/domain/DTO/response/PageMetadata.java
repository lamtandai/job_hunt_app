package application.project.domain.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PageMetadata {
    private final int currentPage;
    private final int pageSize;
    private final int totalPage;
    private final int totalElement;
    
}
