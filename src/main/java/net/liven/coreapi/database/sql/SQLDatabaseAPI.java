package net.liven.coreapi.database.sql;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.NativeQuery;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class SQLDatabaseAPI {
    private static final Set<Class<?>> registeredEntities = new HashSet<>();
    private static SessionFactory sessionFactory;

    public static void initSessionFactory() {
        if (sessionFactory == null) {
            sessionFactory = buildSessionFactory();
        }
    }


    public static boolean updateField(String tableName, String fieldName, Object value, String keyColumn, Object keyValue) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();

            String sql = "UPDATE " + tableName + " SET " + fieldName + " = :value WHERE " + keyColumn + " = :keyValue";
            NativeQuery<?> query = session.createNativeQuery(sql);
            query.setParameter("value", value);
            query.setParameter("keyValue", keyValue);

            int rows = query.executeUpdate();
            transaction.commit();

            return rows > 0;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            return false;
        } finally {
            if (session != null) session.close();
        }
    }

    private static SessionFactory buildSessionFactory() {
        try {
            Configuration configuration = new Configuration();

            configuration.setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
            configuration.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/player_data?serverTimezone=UTC");
            configuration.setProperty("hibernate.connection.username", "liven");
            configuration.setProperty("hibernate.connection.password", "yepGx5+YqbO@yM7y0^jhOmRs");

            configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
            configuration.setProperty("hibernate.hbm2ddl.auto", "update"); // Crucial line for schema updates
            configuration.setProperty("hibernate.show_sql", "false");

            registeredEntities.forEach(configuration::addAnnotatedClass);

            return configuration.buildSessionFactory();

        } catch (Exception ex) {
            throw new ExceptionInInitializerError("SessionFactory creation failed: " + ex);
        }
    }

    public static void registerEntity(Class<?> entityClass) {
        registeredEntities.add(entityClass);
    }

    public static DBActionCallback saveOrUpdate(Object entity) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.saveOrUpdate(entity);
            transaction.commit();
            return DBActionCallback.SUCCESS;
        } catch (Exception ex) {
            // Check for duplicate entry first
            if (ex.getCause() != null && ex.getCause().toString().contains("Duplicate entry")) {
                safeRollback(transaction);
                return DBActionCallback.DUPLICATE_VALUE;
            }

            safeRollback(transaction);
            return DBActionCallback.FAIL;
        } finally {
            if (session != null && session.isOpen()) {
                try {
                    session.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public <T> T getByType(Class<T> clazz, String tableName, String columnName, Object value) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM " + tableName + " WHERE " + columnName + " = :value", clazz)
                    .setParameter("value", value)
                    .uniqueResult();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private static void safeRollback(Transaction transaction) {
        if (transaction != null && transaction.isActive()) {
            try {
                transaction.rollback();
            } catch (Exception rollbackEx) {
                rollbackEx.printStackTrace();
            }
        }
    }

    public static CompletableFuture<DBActionCallback> saveOrUpdateAsync(Object entity) {
        return CompletableFuture.supplyAsync(() -> saveOrUpdate(entity));
    }

    public static <T> List<T> fetchAll(Class<T> entityClass) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM " + entityClass.getSimpleName(), entityClass).list();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
       // registerEntity(Book.class);
      //  registerEntity(StoredProfile.class);

     //   initSessionFactory();


    }
}