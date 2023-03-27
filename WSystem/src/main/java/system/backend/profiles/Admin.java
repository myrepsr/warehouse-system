package system.backend.profiles;

import org.apache.logging.log4j.LogManager;
import system.backend.WSystem;
import system.backend.others.Indicator;


import javax.persistence.*;
import javax.validation.ConstraintViolation;
import java.util.Set;

@Entity
public class Admin extends AbstractMainProfile {

    Admin() {
        super();
    }

    public Admin(String firstname, String lastname, String username, String password) {
        super(firstname, lastname, username, password);
        LOGGER = LogManager.getLogger();
    }

//    public Set<ConstraintViolation<Owner>> createOwner(String firstName, String lastName,
//                                                   String username, String pass, String email, String phone) {
//        Indicator.getInstance().setValidationIndicator(ValidationIndicator.OWNER);
//
//        WSystem wSystem = WSystem.getInstance();
//        OwnerValidation ownerValidation = wSystem.getOwnerValidation();
//
//        Owner owner = new Owner();
//        owner.setFirstname(firstName);
//        owner.setLastname(lastName);
//        owner.setUsername(username);
//        owner.setPassword(pass);
//        owner.setEmailAddress(email);
//        owner.setPhoneNumber(phone);
//
//        Set<ConstraintViolation<Owner>> constraints = ownerValidation.validate(owner);
//
//        if(constraints.isEmpty())
//            wSystem.getOwnerDAO().save(owner);
//        else {
//            for (ConstraintViolation<Owner> con : constraints) {
//                LOGGER.error("Agent couldn't be validated");
//                LOGGER.error("The " + con.getPropertyPath() + " is not valid!");
//                System.out.println(con.getPropertyPath());
//                System.out.println(con.getMessage());
//            }
//        }
//        return constraints;
//    }
//
//    public Set<ConstraintViolation<Agent>> createAgent(String firstName, String lastName,
//                                                   String username, String pass, String email, String phone) {
//        Indicator.getInstance().setValidationIndicator(ValidationIndicator.AGENT);
//
//        WSystem wSystem = WSystem.getInstance();
//        AgentValidation agentValidation = wSystem.getAgentValidation();
//
//        Agent agent = new Agent();
//        agent.setFirstname(firstName);
//        agent.setLastname(lastName);
//        agent.setUsername(username);
//        agent.setPassword(pass);
//        agent.setEmailAddress(email);
//        agent.setPhoneNumber(phone);
//
//        Set<ConstraintViolation<Agent>> constraints = agentValidation.validate(agent);
//
//        if(constraints.isEmpty())
//            wSystem.getAgentDAO().save(agent);
//        else {
//            for (ConstraintViolation<Agent> con : constraints) {
//                LOGGER.error("Agent couldn't be validated");
//                LOGGER.error("The " + con.getPropertyPath() + " is not valid!");
//            }
//        }
//        return constraints;
//    }
}
