package org.example;

import java.util.*;

class Product implements Comparable < Product >
{
    private int id;
    private String name;
    private double price;
    private int stock;

    public Product(int id, String name, double price, int stock)
    {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    @Override
    public String toString()
    {
        return "Product{id=" + id + ", name='" + name + "', price=" + price + ", stock=" + stock + '}';
    }

    @Override
    public int compareTo(Product other)
    {
        return Double.compare(this.price, other.price);
    }

    public double getPrice()
    {
        //soon
        return 0;
    }

    public Integer getId()
    {
        //soon
        return null;
    }

    public <U> U getName()
    {
        //soon
        return null;
    }

    public int getStock()
    {
        //soon
        return 0;
    }
}

class User
{
    private int id;
    private String username;
    private Map < Product, Integer > cart;

    public User(int id, String username)
    {
        this.id = id;
        this.username = username;
        this.cart = new HashMap < > ();
    }

    public void addToCart(Product product, int quantity)
    {
        cart.put(product, cart.getOrDefault(product, 0) + quantity);
    }

    public void removeFromCart(Product product, int quantity)
    {
        if (cart.containsKey(product))
        {
            int currentQuantity = cart.get(product);

            if (currentQuantity <= quantity)
            {
                cart.remove(product);
            }
            else
            {
                cart.put(product, currentQuantity - quantity);
            }
        }
    }

    public void modifyCart(Product product, int newQuantity)
    {
        if (newQuantity > 0)
        {
            cart.put(product, newQuantity);
        }
        else
        {
            cart.remove(product);
        }
    }

    public Integer getId()
    {
        //soon
        return null;
    }
}

class Order
{
    private int id;
    private int userId;
    private Map < Product, Integer > orderDetails;
    private double totalPrice;

    public Order(int id, int userId)
    {
        this.id = id;
        this.userId = userId;
        this.orderDetails = new HashMap < > ();
    }

    public void calculateTotalPrice(Map < Integer, Product > products)
    {
        totalPrice = orderDetails.entrySet().stream()
                .mapToDouble(entry -> entry.getKey().getPrice() * entry.getValue())
                .sum();
    }

    public void setOrderDetails(Map<Product, Integer> orderDetails)
    {
        //soon
    }

    public Integer getId()
    {
        //soon
        return null;
    }
}

class ECommercePlatform
{
    private Map < Integer, User > users;
    private Map < Integer, Product > products;
    private Map < Integer, Order > orders;

    public ECommercePlatform()
    {
        this.users = new HashMap < > ();
        this.products = new HashMap < > ();
        this.orders = new HashMap < > ();
    }

    public void addUser(User user)
    {
        users.put(user.getId(), user);
    }

    public void addProduct(Product product)
    {
        products.put(product.getId(), product);
    }

    public void createOrder(int userId, Map < Product, Integer > orderDetails)
    {
        if (users.containsKey(userId))
        {
            Order order = new Order(orders.size() + 1, userId);

            order.setOrderDetails(orderDetails);

            order.calculateTotalPrice(products);

            orders.put(order.getId(), order);
        }
        else
        {
            System.out.println("User not found.");
        }
    }

    public List < Product > getSortedProductsByName()
    {
        List < Product > productList = new ArrayList < > (products.values());

        productList.sort(Comparator.comparing(Product::getName));

        return productList;
    }

    public List < Product > getSortedProductsByPrice()
    {
        List < Product > productList = new ArrayList < > (products.values());

        productList.sort(Comparator.comparingDouble(Product::getPrice));

        return productList;
    }

    public List < Product > getSortedProductsByStock()
    {
        List < Product > productList = new ArrayList < > (products.values());

        productList.sort(Comparator.comparingInt(Product::getStock));

        return productList;
    }

    public List < Product > filterProductsByStock(int minimumStock)
    {
        return products.values().stream()
                .filter(product -> product.getStock() >= minimumStock)
                .toList();
    }

    public List < Product > recommendProducts(User user)
    {
        List < Product > productList = new ArrayList < > (products.values());

        Collections.shuffle(productList);

        return productList.subList(0, Math.min(5, productList.size()));
    }
}

public class Main
{
    public static void main(String[] args)
    {
        ECommercePlatform platform = new ECommercePlatform();

        // Add users and products to the platform
        User user1 = new User(1, "user1");
        User user2 = new User(2, "user2");

        platform.addUser(user1);
        platform.addUser(user2);

        Product product1 = new Product(1, "Product A", 25.0, 10);
        Product product2 = new Product(2, "Product B", 15.0, 20);

        platform.addProduct(product1);
        platform.addProduct(product2);

        user1.addToCart(product1, 2);
        user1.addToCart(product2, 3);
        user2.addToCart(product1, 1);

        Map < Product, Integer > orderDetails = new HashMap < > ();

        orderDetails.put(product1, 2);
        orderDetails.put(product2, 1);

        platform.createOrder(user1.getId(), orderDetails);

        System.out.println("Users: " + platform.getSortedProductsByPrice());
        System.out.println("Products: " + platform.getSortedProductsByPrice());
        System.out.println("Orders: " + platform.getSortedProductsByPrice());
    }
}