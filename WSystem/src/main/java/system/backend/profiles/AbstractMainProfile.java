package system.backend.profiles;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import system.backend.constraints.MyUnique;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@MappedSuperclass
abstract public class AbstractMainProfile implements MainProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected long ID;
    @Size(min = 2, message = "First name - too short")
    @Size(max = 20, message = "First name - too long")
    @Pattern.List({
            @Pattern(regexp = "(?!.*[0-9]).*", message = "First name - cannot contain digits."),
            @Pattern(regexp = "(?!.*[!@#$%^&*+=?_]).*", message = "First name - cannot contain special characters."),
            @Pattern(regexp = "(?![a-z]).*", message = "First name - should start with a capital letter."),
            @Pattern(regexp = "(?!.+[A-Z]).*", message = "First name - only the first letter can be capital.")
            //@Pattern(regexp = "(?![A-Z][A-Z])(?!.+[A-Z]).*", message = "Only the first letter can be capital.")
    })
    protected String firstname;
    @Size(min = 2, message = "Last name - too short")
    @Size(max = 20, message = "Last name - too long")
    @Pattern.List({
            @Pattern(regexp = "(?!.*[0-9]).*", message = "Last name - cannot contain digits."),
            @Pattern(regexp = "(?!.*[!@#$%^&*+=?_]).*", message = "Last name - contain special characters."),
            @Pattern(regexp = "(?![a-z]).*", message = "Last name - should start with a capital letter."),
            @Pattern(regexp = "(?!.+[A-Z]).*", message = "Last name - only the first letter can be capital.")
    })
    protected String lastname;
    @Size(min = 2, message = "Username - too short")
    @Size(max = 50, message = "Username - too long")
    @Pattern.List({
            @Pattern(regexp = "(?!.*[!@#$%\\^&\"\':|*+()\\=?,./]).*", message = "Username - special characters allowed are: -_"),
            @Pattern(regexp = "(?![0-9].*).*", message = "Username - cannot start with digit"),
            @Pattern(regexp = "(?![-].*).*", message = "Username - cannot start with hyphon"),
            @Pattern(regexp = "(?!.*[-]$).*", message = "Username - cannot end with hyphon"),
            @Pattern(regexp = "(?!.*[-].*[-]).*", message = "Username - hyphon is allowed only once"),
            @Pattern(regexp = "(?!.*[_].*[_]).*", message = "Username - underscore is allowed only once")
//            @Pattern(regexp = "(?!.*[-]{2,}.*).*", message = "Hyphon is allowed only once in a row"),
//            @Pattern(regexp = "(?!.*[_]{2,}.*).*", message = "Underscore is allowed only once in a row")
    })
    @MyUnique(type = {Owner.class, Agent.class, Admin.class}, column = "username")
    protected String username;
    @Size(min = 8, message = "Password - too short")
    @Size(max = 30, message = "Password - too long")
    @Pattern.List({
            @Pattern(regexp = "(?=.*[0-9]).*", message = "Password - must contain at least one digit"),
            @Pattern(regexp = "(?=.*[A-Z]).*", message = "Password - must contain at least one capital letter"),
            @Pattern(regexp = "(?=.*[a-z]).*", message = "Password - must contain at least one lowercase letter"),
            @Pattern(regexp = "(?=.*[_@*#$%^&+=]).*", message = "Password - must contain at least one special character"),
            @Pattern(regexp = "(?=\\S+$).*", message = "Password - cannot contain spaces")
    })
    protected String password;
    @Transient
    protected Logger LOGGER;

    AbstractMainProfile(){
        LOGGER = LogManager.getLogger();
    }

    AbstractMainProfile(String firstname, String lastname, String username, String password){
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.password = password;
    }

    public long getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
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
}
