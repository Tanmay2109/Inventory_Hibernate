package com.inventory.config;

import com.inventory.entity.Product;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

    private static final SessionFactory factory;

    static {
        try {
            factory = new Configuration()
                    .configure("hibernate.cfg.xml")
                    .addAnnotatedClass(Product.class)
                    .buildSessionFactory();

        } catch (Exception e) {
            throw new RuntimeException("Hibernate init failed: " + e);
        }
    }

    public static SessionFactory getSessionFactory() {
        return factory;
    }
}