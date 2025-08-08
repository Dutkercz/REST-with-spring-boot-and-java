package dutkercz.com.github.models;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String author;
    private String title;
    @Column(name = "launch_date")
    private LocalDateTime releaseDate;
    private BigDecimal price;

    public Book() {
    }

    public Book(Long id, String author, String title, LocalDateTime releaseDate, BigDecimal price) {
        this.id = id;
        this.author = author;
        this.title = title;
        this.releaseDate = releaseDate;
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

    public LocalDateTime getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDateTime releaseDate) {
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

        Book book = (Book) o;
        return getId().equals(book.getId()) && Objects.equals(getAuthor(), book.getAuthor()) && getTitle().equals(book.getTitle()) && getReleaseDate().equals(book.getReleaseDate()) && Objects.equals(getPrice(), book.getPrice());
    }

    @Override
    public int hashCode() {
        int result = getId().hashCode();
        result = 31 * result + Objects.hashCode(getAuthor());
        result = 31 * result + getTitle().hashCode();
        result = 31 * result + getReleaseDate().hashCode();
        result = 31 * result + Objects.hashCode(getPrice());
        return result;
    }
}
