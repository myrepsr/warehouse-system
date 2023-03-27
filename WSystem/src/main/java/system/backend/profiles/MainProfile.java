package system.backend.profiles;

public interface MainProfile {
    long getID();
    void setID(int ID);

    String getFirstname();
    void setFirstname(String firstname);

    String getLastname();
    void setLastname(String lastname);

    String getUsername();
    void setUsername(String username);

    String getPassword();
    void setPassword(String password);
}
