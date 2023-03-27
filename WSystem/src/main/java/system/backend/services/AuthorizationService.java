package system.backend.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import system.backend.dao.DAO;
import system.backend.dao.MainDAO;
import system.backend.profiles.MainProfile;

//This class will be used where authorization is needed
public class AuthorizationService<T> {
    private final Logger LOGGER;

    public AuthorizationService(){
        LOGGER = LogManager.getLogger();
    }

    public <T extends MainProfile> boolean authorizeLogin(String username, String password, Class<T> c){
//        CryptoService cryptoService = CryptoService.getInstance();
//        password = cryptoService.encrypt(password, cryptoService.getKey(), cryptoService.getCipher());
        try {
            DAO<T, String> dao = new MainDAO<>();
            T object = dao.findBy2Values(c, "username", "password", username, password);
            return object.getUsername().equals(username) && object.getPassword().equals(password);
            // can be NoResultException
        } catch(Exception e){
            LOGGER.error("An exception has occurred during the authorization of: " +
                    c.toString() + ", message: " + e.getMessage());
            return false;
        }
    }
}
