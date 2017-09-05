package com.dic.bill.utils;

import java.io.File;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

    private static SessionFactory sessionFactory = buildSessionFactory();
    
    protected static SessionFactory buildSessionFactory() {
        // A SessionFactory is set up once for an application!
    	File configurationFile = new File("hibernate.cfg.xml");
    	
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure(configurationFile).build();
        try {
            sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
            if (sessionFactory == null) {
            	System.out.println("ВНИМАНИЕ! ВОЗМОЖНО НЕ НАЙДЕН hibernate.cfg.xml");
            }
        }
        catch (Exception e) {
            // The registry would be destroyed by the SessionFactory, but we had trouble building the SessionFactory
            // so destroy it manually.
        	e.printStackTrace();
            StandardServiceRegistryBuilder.destroy( registry );
        }
        return sessionFactory;
    }
 
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
 
    public static void shutdown() {
        // Close caches and connection pools
        getSessionFactory().close();
    }
    
}