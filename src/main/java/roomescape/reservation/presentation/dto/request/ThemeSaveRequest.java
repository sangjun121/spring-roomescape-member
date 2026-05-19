package roomescape.reservation.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import roomescape.reservation.domain.Theme;

public record ThemeSaveRequest(
        @NotBlank(message = "이름은 공백일 수 없습니다.")
        @Size(max = 50, message = "이름은 50자를 초과할 수 없습니다.")
        String name,
        String description,
        String thumbnailUrl
) {
    public Theme toDomain() {
        return new Theme(
                null,
                this.name,
                this.description,
                this.thumbnailUrl
        );
    }
}
