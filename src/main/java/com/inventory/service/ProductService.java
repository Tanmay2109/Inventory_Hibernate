package com.inventory.service;

import com.inventory.config.HibernateUtil;
import com.inventory.entity.Product;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class ProductService {

    // SAVE
    public void save(Product p) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = s.beginTransaction();

        s.persist(p);

        tx.commit();
        s.close();
    }

    // UPDATE (FIXED)
    public void update(Product p) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = s.beginTransaction();

        s.merge(p);   // IMPORTANT

        tx.commit();
        s.close();
    }

    // DELETE
    public void delete(Product p) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = s.beginTransaction();

        s.remove(s.merge(p));

        tx.commit();
        s.close();
    }

    // GET ALL
    public List<Product> getAll() {
        Session s = HibernateUtil.getSessionFactory().openSession();

        List<Product> list = s.createQuery("from Product", Product.class).list();

        s.close();
        return list;
    }
}