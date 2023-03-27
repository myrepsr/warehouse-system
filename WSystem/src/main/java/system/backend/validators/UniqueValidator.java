package system.backend.validators;

import system.backend.WSystem;
import system.backend.constraints.MyUnique;
import system.backend.dao.DAO;
import system.backend.dao.MainDAO;
import system.backend.others.Indicator;
import system.backend.profiles.Admin;
import system.backend.profiles.Agent;
import system.backend.profiles.Owner;
import system.backend.services.ValidationService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueValidator implements ConstraintValidator<MyUnique, String> {

    private Class<?>[] type;
    private String column;

    @Override
    public void initialize(MyUnique constraintAnnotation) {
        this.type = constraintAnnotation.type();
        this.column = constraintAnnotation.column();
    }

    @Override
    public boolean isValid(String object, ConstraintValidatorContext constraintContext) {

//        ValidationService validationService = ValidationService.getInstance();
//        ValidationIndicator indicator = validationService.getValidationIndicator();
//        Long ignoreThisID = validationService.getIgnoreThisID();
        ValidationService validationService = ValidationService.getInstance();
        Indicator indicator = validationService.getIndicator();
        Class<?> validationIndicator = indicator.getValidationIndicator();

        DAO<Owner, String> ownerDAO = new MainDAO<>();
        DAO<Agent, String> agentDAO = new MainDAO<>();
        DAO<Admin, String> adminDAO = new MainDAO<>();

        for (Class<?> type : type) {
            if (type == Owner.class && validationIndicator == Owner.class) {
                System.out.println("Performing OWNER");
                Long ignoreThisID = validationService.getIgnoreThisID();

                if(ignoreThisID == null) {
                    Owner owner = ownerDAO.findBy1Value(Owner.class, column, object);
                    Agent agent = agentDAO.findBy1Value(Agent.class, column, object);
                    Admin admin = null;

                    if(!column.equals("phoneNumber") && !column.equals("emailAddress")) {
                        admin = adminDAO.findBy1Value(Admin.class, column, object);
                    }

                    if(owner != null || agent != null || admin != null)
                        return false;
                }
                else {
                    Owner owner = ownerDAO.findBy1ValueExcept(Owner.class, column, object, ignoreThisID);
                    Agent agent = agentDAO.findBy1Value(Agent.class, column, object);
                    Admin admin = null;

                    if(!column.equals("phoneNumber") && !column.equals("emailAddress")) {
                        admin = adminDAO.findBy1Value(Admin.class, column, object);
                    }

                    if(owner != null || agent != null || admin != null)
                        return false;
                }
            }
            else if(type == Agent.class && validationIndicator == Agent.class){
                System.out.println("Performing Agent");
                Long ignoreThisID = validationService.getIgnoreThisID();

                if(ignoreThisID == null) {
                    Agent agent = agentDAO.findBy1Value(Agent.class, column, object);
                    Owner owner = ownerDAO.findBy1Value(Owner.class, column, object);
                    Admin admin = null;

                    if(!column.equals("phoneNumber") && !column.equals("emailAddress")) {
                        admin = adminDAO.findBy1Value(Admin.class, column, object);
                    }

                    if(agent != null || owner != null || admin != null)
                        return false;
                }
                else {
                    Agent agent = agentDAO.findBy1ValueExcept(Agent.class, column, object, ignoreThisID);
                    Owner owner = ownerDAO.findBy1Value(Owner.class, column, object);
                    Admin admin = null;

                    if(!column.equals("phoneNumber") && !column.equals("emailAddress")) {
                        admin = adminDAO.findBy1Value(Admin.class, column, object);
                    }

                    if(owner != null || agent != null || admin != null)
                        return false;
                }
            }
            else if(type == Admin.class && validationIndicator == Admin.class){
                System.out.println("Performing Admin");
                Admin admin = adminDAO.findBy1Value(Admin.class, column, object);
                Agent agent = agentDAO.findBy1Value(Agent.class, column, object);
                Owner owner = ownerDAO.findBy1Value(Owner.class, column, object);

                if(admin != null || agent != null || owner != null)
                    return false;
            }
        }
        return true;
    }
}