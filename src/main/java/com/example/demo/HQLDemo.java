package com.example.demo;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import com.example.entity.Product;
import com.example.loader.ProductDataLoader;
import com.example.util.HibernateUtil;

public class HQLDemo {

 public static void main(String[] args) {

  SessionFactory factory = HibernateUtil.getSessionFactory();
  Session session = factory.openSession();

  // Run once to insert data
  ProductDataLoader.loadSampleProducts(session);

  sortProductsByPriceAscending(session);
  sortProductsByPriceDescending(session);
  sortProductsByQuantityDescending(session);

  getFirstThreeProducts(session);
  getNextThreeProducts(session);

  countTotalProducts(session);
  countProductsInStock(session);

  findMinMaxPrice(session);

  filterProductsByPriceRange(session, 20, 100);

  findProductsStartingWith(session, "D");
  findProductsContaining(session, "Desk");

  session.close();
  factory.close();
 }

 public static void sortProductsByPriceAscending(Session session) {

  String hql = "FROM Product p ORDER BY p.price ASC";

  Query<Product> query = session.createQuery(hql, Product.class);

  List<Product> products = query.list();

  System.out.println("\nProducts Sorted by Price Ascending");

  for (Product p : products) {
   System.out.println(p);
  }
 }

 public static void sortProductsByPriceDescending(Session session) {

  String hql = "FROM Product p ORDER BY p.price DESC";

  Query<Product> query = session.createQuery(hql, Product.class);

  List<Product> products = query.list();

  System.out.println("\nProducts Sorted by Price Descending");

  for (Product p : products) {
   System.out.println(p);
  }
 }

 public static void sortProductsByQuantityDescending(Session session) {

  String hql = "FROM Product p ORDER BY p.quantity DESC";

  Query<Product> query = session.createQuery(hql, Product.class);

  List<Product> products = query.list();

  System.out.println("\nProducts Sorted by Quantity");

  for (Product p : products) {
   System.out.println(p.getName() + " Quantity: " + p.getQuantity());
  }
 }

 public static void getFirstThreeProducts(Session session) {

  String hql = "FROM Product";

  Query<Product> query = session.createQuery(hql, Product.class);

  query.setFirstResult(0);
  query.setMaxResults(3);

  List<Product> products = query.list();

  System.out.println("\nFirst 3 Products");

  for (Product p : products) {
   System.out.println(p);
  }
 }

 public static void getNextThreeProducts(Session session) {

  String hql = "FROM Product";

  Query<Product> query = session.createQuery(hql, Product.class);

  query.setFirstResult(3);
  query.setMaxResults(3);

  List<Product> products = query.list();

  System.out.println("\nNext 3 Products");

  for (Product p : products) {
   System.out.println(p);
  }
 }

 public static void countTotalProducts(Session session) {

  String hql = "SELECT COUNT(p) FROM Product p";

  Query<Long> query = session.createQuery(hql, Long.class);

  Long count = query.uniqueResult();

  System.out.println("\nTotal Products: " + count);
 }

 public static void countProductsInStock(Session session) {

  String hql = "SELECT COUNT(p) FROM Product p WHERE p.quantity > 0";

  Query<Long> query = session.createQuery(hql, Long.class);

  Long count = query.uniqueResult();

  System.out.println("Products In Stock: " + count);
 }

 public static void findMinMaxPrice(Session session) {

  String hql = "SELECT MIN(p.price), MAX(p.price) FROM Product p";

  Query<Object[]> query = session.createQuery(hql, Object[].class);

  Object[] result = query.uniqueResult();

  Double min = (Double) result[0];
  Double max = (Double) result[1];

  System.out.println("\nMinimum Price: " + min);
  System.out.println("Maximum Price: " + max);
 }

 public static void filterProductsByPriceRange(Session session, double min, double max) {

  String hql = "FROM Product p WHERE p.price BETWEEN :min AND :max";

  Query<Product> query = session.createQuery(hql, Product.class);

  query.setParameter("min", min);
  query.setParameter("max", max);

  List<Product> products = query.list();

  System.out.println("\nProducts between " + min + " and " + max);

  for (Product p : products) {
   System.out.println(p.getName() + " " + p.getPrice());
  }
 }

 public static void findProductsStartingWith(Session session, String prefix) {

  String hql = "FROM Product p WHERE p.name LIKE :pattern";

  Query<Product> query = session.createQuery(hql, Product.class);

  query.setParameter("pattern", prefix + "%");

  List<Product> products = query.list();

  System.out.println("\nProducts starting with " + prefix);

  for (Product p : products) {
   System.out.println(p.getName());
  }
 }

 public static void findProductsContaining(Session session, String text) {

  String hql = "FROM Product p WHERE p.name LIKE :pattern";

  Query<Product> query = session.createQuery(hql, Product.class);

  query.setParameter("pattern", "%" + text + "%");

  List<Product> products = query.list();

  System.out.println("\nProducts containing " + text);

  for (Product p : products) {
   System.out.println(p.getName());
  }
 }
}