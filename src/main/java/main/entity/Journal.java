package main.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Data
@Table(name = "journal")
public class Journal {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

   // @NotBlank(message = "Book id is compulsory")
    @ManyToOne(targetEntity = Book.class)
    private Book book;

   // @NotBlank(message = "Client id is compulsory")
    @ManyToOne(targetEntity = Client.class)
    private Client client;

   // @NotBlank(message = "Date is compulsory")
    private String dateBeg;

    private String dateEnd;

    private String dateRet;

    public Journal(Book book, Client client, String dateBeg, String dateEnd){
        this.book = book;
        this.client = client;
        this.dateBeg = dateBeg;
        this.dateEnd = dateEnd;
    }

    public Journal(Book book, Client client, String dateBeg, String dateEnd, String dateRet){
        this.book = book;
        this.client = client;
        this.dateBeg = dateBeg;
        this.dateEnd = dateEnd;
        this.dateRet = dateRet;
    }


}

