package dutkercz.com.github.data.dto;

import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class BookResponseDTO extends RepresentationModel<BookResponseDTO> {

    private Long id;
    private String author;
    private String title;
    private String releaseDate;
    private String price;

    public BookResponseDTO() {
    }

    public BookResponseDTO(Long id, String author, String title, LocalDateTime releaseDate, String price) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        this.id = id;
        this.author = author;
        this.title = title;
        this.releaseDate = releaseDate.format(dtf);
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        BookResponseDTO that = (BookResponseDTO) o;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getAuthor(), that.getAuthor()) && Objects.equals(getTitle(), that.getTitle()) && Objects.equals(getReleaseDate(), that.getReleaseDate()) && Objects.equals(getPrice(), that.getPrice());
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(getId());
        result = 31 * result + Objects.hashCode(getAuthor());
        result = 31 * result + Objects.hashCode(getTitle());
        result = 31 * result + Objects.hashCode(getReleaseDate());
        result = 31 * result + Objects.hashCode(getPrice());
        return result;
    }
}
