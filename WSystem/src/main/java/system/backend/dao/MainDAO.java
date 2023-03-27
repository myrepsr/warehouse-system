package system.backend.dao;

import system.backend.Configuration;
import system.backend.profiles.Admin;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.List;

public class MainDAO<T, V> implements DAO<T, V> {
    protected EntityManager manager;

    public MainDAO(){
        Configuration config = Configuration.getInstance();
        manager = config.getManager();
    }

    public void save(T object){
        try {
            manager.getTransaction().begin();
            manager.persist(object);
            manager.getTransaction().commit();
        } catch(Exception e){
            e.printStackTrace();
            System.out.println("LQLQLQLQLQLQLQLQLQLQLQ");
        }
    }

    public T findByID(Class<T> c, Long id){
        T object = manager.find(c, id);

        return object;
    }

    public void delete(T object){
        manager.getTransaction().begin();
        manager.remove(object);
        manager.getTransaction().commit();
    }

    public void deleteByID(Class<T> c, Long id){
        T object = manager.find(c, id);

        manager.getTransaction().begin();
        manager.remove(object);
        manager.getTransaction().commit();
    }

    public void update(T object){
        manager.getTransaction().begin();
        manager.merge(object);
        manager.getTransaction().commit();
    }

    public List<T> selectAll(Class<T> c){
        CriteriaBuilder cb = manager.getCriteriaBuilder();
        CriteriaQuery<T> q = cb.createQuery(c);
        Root<T> r = q.from(c);
        q.select(r);

        TypedQuery<T> query = manager.createQuery(q);
        List<T> results = query.getResultList();

        return results;
    }

    public T findBy2Values(Class<T> c, String column1, String column2, V value1, V value2) {
//        String text = "select a.username, a.password " +
//                "from Admin a " +
//                "where a.username = :username and a.password = :password";
        //String text = "SELECT * FROM Admin WHERE username= :username && password = :password";
//        Query query = manager.createQuery(text);
//        query.setParameter("username", username);
//        query.setParameter("password", password);
//        List admin = query.getResultList();
//        return admin.get(0);

        // Configuring the query
        CriteriaBuilder cb = manager.getCriteriaBuilder();
        CriteriaQuery<T> q = cb.createQuery(c);
        Root<T> entity = q.from(c);
//        ParameterExpression<String> p = cb.parameter(String.class, column1);
//        ParameterExpression<String> p2 = cb.parameter(String.class, column2);

        // Setting the query
        q.select(entity).where(cb.equal(entity.get(column1), value1), cb.equal(entity.get(column2), value2));

        // Executing the query
        TypedQuery<T> typed = manager.createQuery(q);
//        typed.setParameter(column1, value1);
//        typed.setParameter(column2, value2);
        T result = typed.getSingleResult();

        return result;
    }

    public T findBy1Value(Class<T> c, String column1, V value) {
        CriteriaBuilder cb = manager.getCriteriaBuilder();
        CriteriaQuery<T> q = cb.createQuery(c);
        Root<T> entity = q.from(c);
//        ParameterExpression<String> p = cb.parameter(String.class, column1);

        // Setting the query
        q.select(entity).where(cb.equal(entity.get(column1), value));

        // Executing the query
        TypedQuery<T> typed = manager.createQuery(q);
//        typed.setParameter(column1, value);

        try {
            T result = typed.getSingleResult();
            return result;
        } catch(Exception exception){
            return null;
        }
    }

    public T findBy1ValueExcept(Class<T> c, String column1, V value, long ignoreThisID){
        CriteriaBuilder cb = manager.getCriteriaBuilder();
        CriteriaQuery<T> q = cb.createQuery(c);
        Root<T> entity = q.from(c);
//        ParameterExpression<String> p = cb.parameter(String.class, column1);

        // Setting the query
        q.select(entity).where(cb.equal(entity.get(column1), value), cb.notEqual(entity.get("ID"), ignoreThisID));

        // Executing the query
        TypedQuery<T> typed = manager.createQuery(q);
//        typed.setParameter(column1, value);

        try {
            T result = typed.getSingleResult();
            return result;
        } catch(Exception e){
            return null;
        }
    }

    public void updateColumn(Class<T> c, String column, V newValue, V oldValue){
        CriteriaBuilder cb = manager.getCriteriaBuilder();
        CriteriaUpdate<T> update = cb.createCriteriaUpdate(c);
        Root<T> root = update.from(c);
        update.set(column, newValue);
        update.where(cb.equal(root.get(column), oldValue));

        Query query = manager.createQuery(update);
        manager.getTransaction().begin();
        query.executeUpdate();
        manager.getTransaction().commit();
    }
}
