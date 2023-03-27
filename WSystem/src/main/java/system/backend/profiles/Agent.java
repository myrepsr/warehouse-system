package system.backend.profiles;

import system.backend.constraints.MyUnique;

import javax.persistence.Entity;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
public class Agent extends AbstractMainProfile implements SecondaryProfile {
    @Size(max = 50)
    @Email(message = "Invalid email address")
    @MyUnique(type = Agent.class, column = "emailAddress")
    private String emailAddress;
    @Size(min = 10, message = "Phone number must contain 10 digits")
    @Pattern(regexp = "(?!.*[a-z])(?!.*[A-Z])(?!.*[!@#$%^&*)(_=+'|<>~.,?]).*", message = "Phone number can contain only digits")
    @MyUnique(type = Agent.class, column = "phoneNumber")
    private String phoneNumber;

    public Agent(){
        super();
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
