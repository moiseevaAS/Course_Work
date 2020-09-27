package main.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Data
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(length = 50, unique = true)
   // @NotBlank(message = "Book name is compulsory")
    @Size(max = 50, message = "Book name can't be longer than 50 characters")
    private String name;

    @Min(value = 0, message = "Count mustn't be negative")
    private long count;

    @ManyToOne (targetEntity = BookType.class)
    private BookType type;

    public Book(String name, long count, BookType type){
        this.name = name;
        this.count = count;
        this.type = type;
    }


}
