package dutkercz.com.github.data.dto;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record BookUpdateDTO(
        @NotNull Long id,
        @NotNull BigDecimal price) {
}
