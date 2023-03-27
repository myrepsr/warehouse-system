package system.backend;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.validation.ConstraintViolationException;

public class Configuration {
    private static Configuration config;
    private EntityManagerFactory factory;
    private EntityManager manager;
    private Logger LOGGER;

    Configuration(){
        setLOGGER(LogManager.getLogger());
    }

    public static Configuration getInstance(){
        if(config == null) {
            config = new Configuration();
        }
        return config;
    }

    public EntityManagerFactory getFactory() {
        return factory;
    }

    private void createFactory() {
        String unit = "my-persistence-unit";
        factory = Persistence.createEntityManagerFactory(unit);
        LOGGER.info("Successfully connected to " + unit);
    }

    public EntityManager getManager() {
        return manager;
    }

    private void createManager() {
        manager = factory.createEntityManager();
    }

    private void createDB(){
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();

        // Create MetadataSources
        MetadataSources sources = new MetadataSources(registry);

        // Create Metadata
        Metadata metadata = sources.getMetadataBuilder().build();

        // Create SessionFactory
        SessionFactory sessionFactory = metadata.getSessionFactoryBuilder().build();
        sessionFactory.close();
        StandardServiceRegistryBuilder.destroy(registry);
    }
    // Starting a new thread
    public void configure(){
        new Thread(() -> {
                try {
                    // Creating the database
                    createDB();
                    LOGGER.info("Successfully finished creating the database!");
                    // Creating factory and manager
                    createFactory();
                    createManager();
                    LOGGER.info("Successfully finished creating factory and manager!");
                    // Creating the system
                    WSystem wSystem = WSystem.getInstance();
                    wSystem.initializeDB();
                    LOGGER.info("Successfully finished creating the system!");
                    LOGGER.info("Successfully finished the configuration of the application!");
                } catch (Exception e) {
                    LOGGER.error("An error occurred during the configuration: " + e.getMessage());
                    e.printStackTrace();
                }
            }).start();
    }

    public void closeFactory(){
        factory.close();
    }

    public void closeManager(){
        manager.close();
    }

    public void setLOGGER(Logger LOGGER) {
        this.LOGGER = LOGGER;
    }
}