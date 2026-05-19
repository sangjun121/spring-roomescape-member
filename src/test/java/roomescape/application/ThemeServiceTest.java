package roomescape.application;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import roomescape.exception.code.ThemeErrorCode;
import roomescape.exception.custom.BusinessException;
import roomescape.reservation.application.ThemeService;
import roomescape.reservation.infra.ThemeRepository;

@ExtendWith(MockitoExtension.class)
public class ThemeServiceTest {
    private static final Clock FIXED_CLOCK = Clock.fixed(
            Instant.parse("2026-05-06T03:30:00Z"),
            ZoneId.of("Asia/Seoul")
    );

    @Mock
    private ThemeRepository themeRepository;

    private ThemeService themeService;

    @BeforeEach
    void setUp() {
        themeService = new ThemeService(
                themeRepository,
                FIXED_CLOCK
        );
    }

    @Test
    void 존재하지_않는_ID로_테마를_삭제하면_예외가_발생한다() {
        when(themeRepository.deleteById(20L)).thenReturn(0);

        assertThatThrownBy(() -> themeService.delete(20L))
                .isInstanceOf(BusinessException.class)
                .extracting("errorCode")
                .isEqualTo(ThemeErrorCode.THEME_NOT_FOUND);
    }

    @Test
    void 이미_예약이_있는_테마는_삭제할_수_없다() {
        //given
        long id = 1L;

        when(themeRepository.deleteById(id)).thenThrow(new DataIntegrityViolationException("외래키 제약"));

        assertThatThrownBy(() -> themeService.delete(id))
                .isInstanceOf(BusinessException.class)
                .extracting("errorCode")
                .isEqualTo(ThemeErrorCode.THEME_DELETE_CONFLICT);
    }
}
