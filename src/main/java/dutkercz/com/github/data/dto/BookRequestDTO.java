package dutkercz.com.github.data.dto;

import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record BookRequestDTO(
        @NotBlank
        String author,
        @NotBlank
        String title,
        LocalDateTime releaseDate,
        BigDecimal price
) {
}
