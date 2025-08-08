package dutkercz.com.github.data.dto;

import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public class BookRequestDTO{
        @NotBlank
        private String author;
        @NotBlank
        private String title;
        private LocalDate releaseDate;
        private BigDecimal price;

        public BookRequestDTO() {
        }

        public BookRequestDTO(String author, String title, LocalDate releaseDate, BigDecimal price) {
                this.author = author;
                this.title = title;
                this.releaseDate = releaseDate;
                this.price = price;
        }

        public String getAuthor() {
                return author;
        }

        public void setAuthor(String author) {
                this.author = author;
        }

        public String getTitle() {
                return title;
        }

        public void setTitle(String title) {
                this.title = title;
        }

        public LocalDate getReleaseDate() {
                return releaseDate;
        }

        public void setReleaseDate(LocalDate releaseDate) {
                this.releaseDate = releaseDate;
        }

        public BigDecimal getPrice() {
                return price;
        }

        public void setPrice(BigDecimal price) {
                this.price = price;
        }

        @Override
        public boolean equals(Object o) {
                if (o == null || getClass() != o.getClass()) return false;

                BookRequestDTO that = (BookRequestDTO) o;
                return Objects.equals(getAuthor(), that.getAuthor()) && Objects.equals(getTitle(), that.getTitle()) && Objects.equals(getReleaseDate(), that.getReleaseDate()) && Objects.equals(getPrice(), that.getPrice());
        }

        @Override
        public int hashCode() {
                int result = Objects.hashCode(getAuthor());
                result = 31 * result + Objects.hashCode(getTitle());
                result = 31 * result + Objects.hashCode(getReleaseDate());
                result = 31 * result + Objects.hashCode(getPrice());
                return result;
        }
}
