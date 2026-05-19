package roomescape.reservation.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import roomescape.reservation.domain.Theme;

public record ThemeSaveRequest(
        @NotBlank String name,
        String description,
        @NotBlank String thumbnailUrl
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
