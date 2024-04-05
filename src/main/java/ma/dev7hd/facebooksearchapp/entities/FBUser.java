package ma.dev7hd.facebooksearchapp.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor @NoArgsConstructor @Getter
@Table(name = "main_db")
public class FBUser {
    @Id
    @Column(name = "ID")
    private Long id;
    @Column(name = "PHONE")
    private Long phone;
    @Column(name = "LAST_NAME")
    private String lastName;
    @Column(name = "FIRST_NAME")
    private String firstName;
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "BIRTHDAY")
    private String birthday;
    @Column(name = "GENDER")
    private String gender;
    @Column(name = "LOCALE")
    private String locale;
    @Column(name = "HOMETOWN")
    private String home;
    @Column(name = "LOCATION")
    private String location;
    @Column(name = "LINK")
    private String link;
}
