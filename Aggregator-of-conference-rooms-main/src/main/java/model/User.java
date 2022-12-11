package model;


import javax.persistence.*;

@Entity //e o entitate, deci o cls coresp unui tabel in BD
@Table(name = "users") //in user tinem datele de la b.o. si org.
@Inheritance(strategy = InheritanceType.JOINED) //pt ca e cls abstracta
public abstract class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //ca sa incrementeze id-ul automat in BD
    private Integer id;
    private String firstName;
    private String lastName;
    private String username;
    private String password;


    public User(String firstName, String lastName, String username, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
    }

    public User() {

    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

//    public ArrayList<Message> getRequestedOffers() {
//        return requestedOffers;
//    }
}
