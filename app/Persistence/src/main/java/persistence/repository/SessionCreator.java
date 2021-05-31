package persistence.repository;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class SessionCreator {
    private static SessionFactory sessionFactory = null;

    private SessionCreator() {
    }

    public static SessionFactory getSessionFactory() {
        if(sessionFactory == null){
            final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                    .configure() // configures settings from hibernate.cfg.xml
                    .build();
            try {
                sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
            }
            catch (Exception e) {
                System.err.println("Exception "+e);
                StandardServiceRegistryBuilder.destroy( registry );
            }
        }
        return sessionFactory;
    }

    public static void close(){
        if ( sessionFactory != null ) {
            sessionFactory.close();
        }

    }
}
