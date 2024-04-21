package medical_store.src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

class MedicalStoreManagementSystemGUI {
    private JFrame mainFrame;
    private JPanel controlPanel;
    private JLabel headerLabel;
    private JTextArea displayArea;

    private List<Product> products;
    private List<Customer> customers;
    private List<Sale> sales;
    private int productCounter = 1;
    private int customerCounter = 1;
    private int saleCounter = 1;
    private static final int LOW_STOCK_THRESHOLD = 10;

    public MedicalStoreManagementSystemGUI() {
        products = new ArrayList<>();
        customers = new ArrayList<>();
        sales = new ArrayList<>();
        prepareGUI();
        System.out.println("Testing database connection...");
        Connection conn = DatabaseConnection.getConnection();
        // You can use 'conn' for executing queries or other database operations
        // Don't forget to close the connection when done
        DatabaseConnection.closeConnection(conn);
    }

    private void showLoginFrame() {
        JFrame loginFrame = new JFrame("Admin Login");
        loginFrame.setSize(300, 150);
        loginFrame.setLayout(new GridLayout(3, 1));
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel usernameLabel = new JLabel("Username:");
        JTextField usernameField = new JTextField(20);
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField(20);
        JButton loginButton = new JButton("Login");

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                if (isAdmin(username, password)) {
                    loginFrame.dispose();
                    showFrame();
                } else {
                    JOptionPane.showMessageDialog(loginFrame, "Invalid username or password!");
                }
            }
        });

        loginFrame.add(usernameLabel);
        loginFrame.add(usernameField);
        loginFrame.add(passwordLabel);
        loginFrame.add(passwordField);
        loginFrame.add(loginButton);
        loginFrame.setLocationRelativeTo(null);

        loginFrame.setVisible(true);
    }

    private boolean isAdmin(String username, String password) {
        
        return username.equals("admin") && password.equals("admin123");
    }



    public static void main(String[] args) {
        MedicalStoreManagementSystemGUI systemGUI = new MedicalStoreManagementSystemGUI();
        systemGUI.showLoginFrame();
    }

    private void prepareGUI() {
        mainFrame = new JFrame("Medical Store Management System");
        mainFrame.setSize(600, 400);
        mainFrame.setLayout(new GridLayout(2, 1));
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLocationRelativeTo(null);

        headerLabel = new JLabel("", JLabel.CENTER);

        controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());
        controlPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        mainFrame.add(headerLabel);
        mainFrame.add(controlPanel);
        mainFrame.setVisible(true);
    }

    private void showFrame() {
        headerLabel.setText("Welcome to Medical Store Management System");

        JButton addProductButton = new JButton("Add Product");
        JButton addCustomerButton = new JButton("Add Customer");
        JButton makeSaleButton = new JButton("Make Sale");
        JButton displayProductsButton = new JButton("Display Products");
        JButton displayCustomersButton = new JButton("Display Customers");
        JButton purchaseProductButton = new JButton("Purchase Product");
        JButton checkExpiryAndStockButton = new JButton("Check Expiry and Stock");
        mainFrame.add(controlPanel, BorderLayout.CENTER);

        addProductButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addProductDialog();
            }
        });

        addCustomerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addCustomerDialog();
            }
        });

        makeSaleButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                makeSaleAndPrintBill();
            }
        });

        displayProductsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                displayProducts();
            }
        });

        displayCustomersButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                displayCustomers();
            }
        });

        purchaseProductButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                purchaseProductDialog();
            }
        });

        checkExpiryAndStockButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                checkExpiryAndStock();
            }
        });

        controlPanel.add(addProductButton);
        controlPanel.add(addCustomerButton);
        controlPanel.add(makeSaleButton);
        controlPanel.add(displayProductsButton);
        controlPanel.add(displayCustomersButton);
        controlPanel.add(purchaseProductButton);
        controlPanel.add(checkExpiryAndStockButton);
        

        mainFrame.setVisible(true);
    }

    private void addProductDialog() {
        JFrame addProductFrame = new JFrame("Add Product");
        addProductFrame.setSize(400, 200);
        addProductFrame.setLayout(new GridLayout(6, 1));

        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField(20);
        JLabel priceLabel = new JLabel("Price:");
        JTextField priceField = new JTextField(10);
        JLabel quantityLabel = new JLabel("Quantity:");
        JTextField quantityField = new JTextField(10);
        JLabel expiryLabel = new JLabel("Expiry Date:");
        JTextField expiryField = new JTextField(20);

        JButton addButton = new JButton("Add");

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                double price = Double.parseDouble(priceField.getText());
                int quantity = Integer.parseInt(quantityField.getText());
                String expiryDate = expiryField.getText();
                addProduct(name, price, quantity, expiryDate);
                addProductFrame.dispose();
            }
        });

        addProductFrame.add(nameLabel);
        addProductFrame.add(nameField);
        addProductFrame.add(priceLabel);
        addProductFrame.add(priceField);
        addProductFrame.add(quantityLabel);
        addProductFrame.add(quantityField);
        addProductFrame.add(expiryLabel);
        addProductFrame.add(expiryField);
        addProductFrame.add(addButton);
        addProductFrame.setLocationRelativeTo(null);

        addProductFrame.setVisible(true);
    }

    private void addCustomerDialog() {
        JFrame addCustomerFrame = new JFrame("Add Customer");
        addCustomerFrame.setSize(400, 200);
        addCustomerFrame.setLayout(new GridLayout(5, 1));

        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField(20);
        JLabel addressLabel = new JLabel("Address:");
        JTextField addressField = new JTextField(30);
        JLabel phoneLabel = new JLabel("Phone:");
        JTextField phoneField = new JTextField(15);

        JButton addButton = new JButton("Add");

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String address = addressField.getText();
                String phone = phoneField.getText();
                addCustomer(name, address, phone);
                addCustomerFrame.dispose();
            }
        });

        addCustomerFrame.add(nameLabel);
        addCustomerFrame.add(nameField);
        addCustomerFrame.add(addressLabel);
        addCustomerFrame.add(addressField);
        addCustomerFrame.add(phoneLabel);
        addCustomerFrame.add(phoneField);
        addCustomerFrame.add(addButton);
        addCustomerFrame.setLocationRelativeTo(null);

        addCustomerFrame.setVisible(true);
    }

    private void makeSaleAndPrintBill() {
        JFrame makeSaleFrame = new JFrame("Make Sale");
        makeSaleFrame.setSize(400, 300);
        makeSaleFrame.setLayout(new BorderLayout());
    
        JPanel inputPanel = new JPanel(new GridLayout(4, 1));
    
        JLabel customerIdLabel = new JLabel("Customer ID:");
        JTextField customerIdField = new JTextField(10);
        JLabel productIdLabel = new JLabel("Product ID(s):");
        JTextField productIdField = new JTextField(10);
        JLabel quantityLabel = new JLabel("Quantity(s):");
        JTextField quantityField = new JTextField(10);
    
        inputPanel.add(customerIdLabel);
        inputPanel.add(customerIdField);
        inputPanel.add(productIdLabel);
        inputPanel.add(productIdField);
        inputPanel.add(quantityLabel);
        inputPanel.add(quantityField);
    
        JButton sellButton = new JButton("Sell");
    
        sellButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int customerId;
                try {
                    customerId = Integer.parseInt(customerIdField.getText());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(mainFrame, "Invalid customer ID!");
                    return;
                }
                
                String[] productIdsString = productIdField.getText().split(",");
                String[] quantitiesString = quantityField.getText().split(",");
                
                if (productIdsString.length != quantitiesString.length) {
                    JOptionPane.showMessageDialog(mainFrame, "Please provide quantity for each product.");
                    return;
                }
                
                StringBuilder bill = new StringBuilder();
                double totalBillAmount = 0;
        
                for (int i = 0; i < productIdsString.length; i++) {
                    try {
                        int productId = Integer.parseInt(productIdsString[i].trim());
                        int quantityToSell = Integer.parseInt(quantitiesString[i].trim());
                        SaleItem saleItem = sellProduct(customerId, productId, quantityToSell);
                        if (saleItem != null) {
                            bill.append("Product Sold: ").append(saleItem.product.getName()).append("\n");
                            bill.append("Quantity: ").append(saleItem.quantity).append("\n");
                            bill.append("Total Price: ").append(saleItem.totalPrice).append("\n\n");
                            totalBillAmount += saleItem.totalPrice;
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(mainFrame, "Invalid product ID or quantity!");
                        return;
                    }
                }
        
                if (bill.length() > 0) {
                    bill.insert(0, "Bill for Customer: " + customers.get(customerId - 1).getName() + "\n\n");
                    bill.append("Total Bill Amount: ").append(totalBillAmount).append("\n");
                    JOptionPane.showMessageDialog(mainFrame, bill.toString());
                }
        
                makeSaleFrame.dispose();
            }
        });
    
        makeSaleFrame.add(inputPanel, BorderLayout.CENTER);
        makeSaleFrame.add(sellButton, BorderLayout.SOUTH);
        makeSaleFrame.setLocationRelativeTo(null);
    
        makeSaleFrame.setVisible(true);
    }
    
    private SaleItem sellProduct(int customerId, int productId, int quantityToSell) {
       // Customer customerToSell = customers.get(customerId - 1);
        if (customerId > 0 && customerId <= customers.size()) {
            Customer customerToSell = customers.get(customerId - 1);
        } else {
            JOptionPane.showMessageDialog(mainFrame, "Invalid customer ID!");
            return null; // or handle the error appropriately
        }
        
        Product productToSell = null;
        for (Product product : products) {
            if (product.getId() == productId) {
                productToSell = product;
                break;
            }
        }
    
        if (productToSell == null) {
            JOptionPane.showMessageDialog(mainFrame, "Product not found!");
            return null;
        }
    
        if (productToSell.getQuantity() < quantityToSell) {
            JOptionPane.showMessageDialog(mainFrame, "Insufficient stock for product: " + productToSell.getName());
            return null;
        }
    
        double totalPrice = productToSell.getPrice() * quantityToSell;
        productToSell.setQuantity(productToSell.getQuantity() - quantityToSell);
    
        return new SaleItem(productToSell, quantityToSell, totalPrice);
    }
    
    private class SaleItem {
        Product product;
        int quantity;
        double totalPrice;
    
        SaleItem(Product product, int quantity, double totalPrice) {
            this.product = product;
            this.quantity = quantity;
            this.totalPrice = totalPrice;
        }
    }
    

    private void displayProducts() {
        StringBuilder productsInfo = new StringBuilder();
        if (products.isEmpty()) {
            productsInfo.append("No products available.");
        } else {
            productsInfo.append("Products:\n");
            for (Product product : products) {
                productsInfo.append("ID: ").append(product.getId()).append(", ")
                           .append("Name: ").append(product.getName()).append(", ")
                           .append("Price: ").append(product.getPrice()).append(", ")
                           .append("Quantity: ").append(product.getQuantity()).append(", ")
                           .append("Expiry Date: ").append(product.getExpiryDate()).append("\n");
            }
        }
        JOptionPane.showMessageDialog(mainFrame, productsInfo.toString());
        ((JDialog) SwingUtilities.getWindowAncestor(mainFrame)).setLocationRelativeTo(null);
    }

    private void displayCustomers() {
        // Create a StringBuilder to hold the information of all customers
        StringBuilder customerInfo = new StringBuilder();
    
        // Check if there are any customers
        if (customers.isEmpty()) {
            JOptionPane.showMessageDialog(mainFrame, "No customers", "Customers", JOptionPane.INFORMATION_MESSAGE);
            return; // Exit the method if there are no customers
        }
    
        // Iterate through the list of customers
        for (Customer customer : customers) {
            // Append the details of each customer to the StringBuilder
            customerInfo.append("Customer ID: ").append(customer.getId()).append("\n");
            customerInfo.append("Name: ").append(customer.getName()).append("\n");
            customerInfo.append("Address: ").append(customer.getAddress()).append("\n");
            customerInfo.append("Phone: ").append(customer.getPhone()).append("\n\n");
        }
    
        // Display the customer information in a dialog box
        JOptionPane.showMessageDialog(mainFrame, customerInfo.toString(), "Customers", JOptionPane.PLAIN_MESSAGE);
        ((JDialog) SwingUtilities.getWindowAncestor(mainFrame)).setLocationRelativeTo(null);
    }
    
    

    private void purchaseProductDialog() {
        JFrame purchaseProductFrame = new JFrame("Purchase Product");
        purchaseProductFrame.setSize(400, 200);
        purchaseProductFrame.setLayout(new GridLayout(4, 1));

        JLabel idLabel = new JLabel("Product ID:");
        JTextField idField = new JTextField(10);
        JLabel quantityLabel = new JLabel("Quantity to Add:");
        JTextField quantityField = new JTextField(10);

        JButton purchaseButton = new JButton("Purchase");

        purchaseButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int productId = Integer.parseInt(idField.getText());
                int quantityToAdd = Integer.parseInt(quantityField.getText());
                purchaseProduct(productId, quantityToAdd);
                purchaseProductFrame.dispose();
            }
        });

        purchaseProductFrame.add(idLabel);
        purchaseProductFrame.add(idField);
        purchaseProductFrame.add(quantityLabel);
        purchaseProductFrame.add(quantityField);
        purchaseProductFrame.add(purchaseButton);
        purchaseProductFrame.setLocationRelativeTo(null);

        purchaseProductFrame.setVisible(true);
    }

    private void checkExpiryAndStock() {
        StringBuilder expiredProducts = new StringBuilder("Expired Products:\n");
        StringBuilder lowStockProducts = new StringBuilder("Products with Low Stock:\n");
    
        // Check for expired products
        for (Product product : products) {
            if (isExpired(product.getExpiryDate())) {
                expiredProducts.append(product.getName()).append("\n");
            }
        }
    
        // Check for products with low stock
        for (Product product : products) {
            if (product.getQuantity() <= LOW_STOCK_THRESHOLD) {
                lowStockProducts.append(product.getName()).append(": ").append(product.getQuantity()).append("\n");
            }
        }
    
        // Display the results
        String message = "";
        if (expiredProducts.length() > 20) { // Check if there are expired products
            message += expiredProducts.toString() + "\n";
        }
        if (lowStockProducts.length() > 25) { // Check if there are products with low stock
            message += lowStockProducts.toString();
        }
        if (message.isEmpty()) { // If no issues found
            message = "No expired products or products with low stock found.";
        }
        JOptionPane.showMessageDialog(mainFrame, message);
        ((JDialog) SwingUtilities.getWindowAncestor(mainFrame)).setLocationRelativeTo(null);
        
    }
    
    private boolean isExpired(String expiryDate) {
        // Implement logic to check if a product is expired based on its expiry date
        // For the sake of simplicity, let's assume the current date and compare it with the expiry date
        // You may need to replace this with your actual date comparison logic
        // Here, we'll just return false, indicating no products are expired
        return false;
    }
    

    private void addProduct(String name, double price, int quantity, String expiryDate) {
        Product newProduct = new Product(productCounter++, name, price, quantity, expiryDate);
        products.add(newProduct);
        JOptionPane.showMessageDialog(mainFrame, "Product added successfully!");
    }

    private void addCustomer(String name, String address, String phone) {
        // Create a new Customer object with the provided details
        Customer newCustomer = new Customer(customerCounter++, name, address, phone);
        
        // Add the new customer to the list of customers
        customers.add(newCustomer);
        
        // Display a success message
        JOptionPane.showMessageDialog(mainFrame, "Customer added successfully!");
    }

    private void purchaseProduct(int productId, int quantityToAdd) {
        // Implement logic to purchase product
        // This method will handle the process of purchasing a product and updating its quantity
        for (Product product : products) {
            if (product.getId() == productId) {
                product.setQuantity(product.getQuantity() + quantityToAdd);
                JOptionPane.showMessageDialog(mainFrame, "Product purchased successfully!");
                return;
            }
        }
        JOptionPane.showMessageDialog(mainFrame, "Product not found!");
    }
}

class Product {
    private int id;
    private String name;
    private double price;
    private int quantity;
    private String expiryDate;

    public Product(int id, String name, double price, int quantity, String expiryDate) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.expiryDate = expiryDate;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getExpiryDate() {
        return expiryDate;
    }
}

class Customer {
    private int id;
    private String name;
    private String address;
    private String phone;

    public Customer(int id, String name, String address, String phone) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }
}

class Sale {
    private int id;
    private int customerId;
    private List<Integer> productIds;

    public Sale(int id, int customerId, List<Integer> productIds) {
        this.id = id;
        this.customerId = customerId;
        this.productIds = productIds;
    }

    public int getId() {
        return id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public List<Integer> getProductIds() {
        return productIds;
    }
}

class Admin {
    private String username;
    private String password;

    public Admin(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}

