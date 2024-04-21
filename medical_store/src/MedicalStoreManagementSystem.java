package medical_store.src;
// database
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Class representing a product
class Product {
    // Attributes of a product
    private int productId;
    private String name;
    private double price;
    private int quantity;
    private String expiryDate;

    // Constructor to initialize a product
    public Product(int productId, String name, double price, int quantity, String expiryDate) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.expiryDate = expiryDate;
    }

    // Getter methods for product attributes
    public int getProductId() {
        return productId;
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

    // Setter method for quantity attribute
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    // Setter method for expiryDate attribute
    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }
}

// Class representing a customer
class Customer {
    // Attributes of a customer
    private int customerId;
    private String name;
    private String address;
    private String phone;

    // Constructor to initialize a customer
    public Customer(int customerId, String name, String address, String phone) {
        this.customerId = customerId;
        this.name = name;
        this.address = address;
        this.phone = phone;
    }

    // Getter methods for customer attributes
    public int getCustomerId() {
        return customerId;
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

// Class representing a sale
class Sale {
    // Attributes of a sale
    private int saleId;
    private Customer customer;
    private List<Product> products;
    private double totalAmount;

    // Constructor to initialize a sale
    public Sale(int saleId, Customer customer) {
        this.saleId = saleId;
        this.customer = customer;
        this.products = new ArrayList<>();
        this.totalAmount = 0;
    }

    // Getter methods for sale attributes
    public int getSaleId() {
        return saleId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public List<Product> getProducts() {
        return products;
    }

    // Method to add a product to the sale
    public void addProduct(Product product, int quantity) {
        product.setQuantity(product.getQuantity() - quantity); // Reduce the quantity of the product
        products.add(product); // Add the product to the sale
        totalAmount += product.getPrice() * quantity; // Update the total amount of the sale
    }

    public double getTotalAmount() {
        return totalAmount;
    }
}

// Main class for the medical store management system
public class MedicalStoreManagementSystem {
    // Attributes of the management system
    private List<Product> products;
    private List<Customer> customers;
    private List<Sale> sales;
    private int productCounter = 1; // Counter for product IDs
    private int customerCounter = 1; // Counter for customer IDs
    private int saleCounter = 1; // Counter for sale IDs
    private Scanner scanner;
    private static final int LOW_STOCK_THRESHOLD = 10; // Threshold for low stock

    // Constructor to initialize the management system
    public MedicalStoreManagementSystem() {
        products = new ArrayList<>();
        customers = new ArrayList<>();
        sales = new ArrayList<>();
        scanner = new Scanner(System.in);
    }

    // Method to add a product to the system
    public void addProduct(String name, double price, int quantity, String expiryDate) {
        Product product = new Product(productCounter++, name, price, quantity, expiryDate);
        products.add(product);
    }

    // Method to save product data to a text file
    public void saveProductData(String fileName) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            for (Product product : products) {
                writer.println(product.getProductId() + "," + product.getName() + "," +
                        product.getPrice() + "," + product.getQuantity() + "," + product.getExpiryDate());
            }
            System.out.println("Product data saved to " + fileName);
        } catch (IOException e) {
            System.out.println("Error occurred while saving product data: " + e.getMessage());
        }
    }

    // Method to add a customer to the system
    public void addCustomer(String name, String address, String phone) {
        Customer customer = new Customer(customerCounter++, name, address, phone);
        customers.add(customer);
    }

    // Method to save customer data to a text file
    public void saveCustomerData(String fileName) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            for (Customer customer : customers) {
                writer.println(customer.getCustomerId() + "," + customer.getName() + "," +
                        customer.getAddress() + "," + customer.getPhone());
            }
            System.out.println("Customer data saved to " + fileName);
        } catch (IOException e) {
            System.out.println("Error occurred while saving customer data: " + e.getMessage());
        }
    }

    // Method to make a sale and generate a bill
    public void makeSale(Customer customer, List<Product> products, List<Integer> quantities) {
        Sale sale = new Sale(saleCounter++, customer);
        for (int i = 0; i < products.size(); i++) {
            sale.addProduct(products.get(i), quantities.get(i)); // Add products to the sale
        }
        sales.add(sale); // Add the sale to the system
    }

    // Method to save sale data to a text file
    public void saveSaleData(String fileName) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            for (Sale sale : sales) {
                writer.println(sale.getSaleId() + "," + sale.getCustomer().getCustomerId() + "," +
                        sale.getTotalAmount());
                for (Product product : sale.getProducts()) {
                    writer.println(product.getProductId() + "," + product.getName() + "," +
                            product.getPrice() + "," + product.getQuantity() + "," + product.getExpiryDate());
                }
                writer.println("END");
            }
            System.out.println("Sale data saved to " + fileName);
        } catch (IOException e) {
            System.out.println("Error occurred while saving sale data: " + e.getMessage());
        }
    }

    // Method to display all products
    public void displayProducts() {
        System.out.println("Products Available:");
        for (Product product : products) {
            System.out.println("ID: " + product.getProductId() + ", Name: " + product.getName() +
                    ", Price: " + product.getPrice() + ", Quantity: " + product.getQuantity() +
                    ", Expiry Date: " + product.getExpiryDate());
        }
    }

    // Method to display all customers
    public void displayCustomers() {
        System.out.println("Customers List:");
        for (Customer customer : customers) {
            System.out.println("ID: " + customer.getCustomerId() + ", Name: " + customer.getName() +
                    ", Address: " + customer.getAddress() + ", Phone: " + customer.getPhone());
        }
    }

    // Method to increase the quantity of a product
    public void purchaseProduct(int productId, int quantityToAdd) {
        for (Product product : products) {
            if (product.getProductId() == productId) {
                product.setQuantity(product.getQuantity() + quantityToAdd); // Increase the quantity of the product
                System.out.println("Quantity added successfully!");
                return;
            }
        }
        System.out.println("Product not found with ID: " + productId);
    }

    // Method to check for expired products and low stock
    public void checkExpiryAndStock() {
        boolean expiredProductsFound = false;
        boolean lowStockProductsFound = false;

        System.out.println("\nChecking Expiry and Stock:");
        for (Product product : products) {
            // Check for expiry
            if (product.getExpiryDate() != null && !product.getExpiryDate().isEmpty()) {
                // Logic to compare expiry date with current date and display notification
                System.out.println("Product: " + product.getName() + " with ID: " + product.getProductId() +
                        " expires on: " + product.getExpiryDate());
                expiredProductsFound = true;
            }
            // Check for low stock
            if (product.getQuantity() < LOW_STOCK_THRESHOLD) {
                System.out.println("Low stock for product: " + product.getName() + " with ID: " + product.getProductId() +
                        ", Remaining Quantity: " + product.getQuantity());
                lowStockProductsFound = true;
            }
        }

        if (!expiredProductsFound) {
            System.out.println("No products are expiring soon.");
        }
        if (!lowStockProductsFound) {
            System.out.println("All products are in stock.");
        }
    }

    // Method to make a sale and print the bill
    public void makeSaleAndPrintBill() {
        System.out.println("\nSelect a customer to make sale:");
        displayCustomers();
        System.out.print("Enter customer ID: ");
        int customerId = scanner.nextInt();
        Customer selectedCustomer = null;
        for (Customer customer : customers) {
            if (customer.getCustomerId() == customerId) {
                selectedCustomer = customer;
                break;
            }
        }
        if (selectedCustomer == null) {
            System.out.println("Invalid customer ID.");
            return;
        }

        List<Product> selectedProducts = new ArrayList<>();
        List<Integer> selectedQuantities = new ArrayList<>();

        System.out.println("\nSelect products to purchase:");
        displayProducts();
        System.out.println("Enter product IDs (comma-separated) or -1 to finish: ");
        String input = scanner.next();
        scanner.nextLine(); // Consume newline
        String[] productIdStrs = input.split(",");
        for (String productIdStr : productIdStrs) {
            int productId = Integer.parseInt(productIdStr.trim());
            if (productId == -1) {
                break;
            }
            Product product = getProductById(productId);
            if (product == null) {
                System.out.println("Product with ID " + productId + " not found.");
                continue;
            }
            System.out.print("Enter quantity for " + product.getName() + ": ");
            int quantity = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            if (quantity <= 0 || quantity > product.getQuantity()) {
                System.out.println("Invalid quantity.");
                continue;
            }
            selectedProducts.add(product);
            selectedQuantities.add(quantity);
        }

        if (selectedProducts.isEmpty()) {
            System.out.println("No products selected for sale.");
            return;
        }

        makeSale(selectedCustomer, selectedProducts, selectedQuantities);
        System.out.println("Sale completed successfully.");

        // Print bill
        System.out.println("\n---------- Bill ----------");
        System.out.println("Customer: " + selectedCustomer.getName());
        for (int i = 0; i < selectedProducts.size(); i++) {
            Product product = selectedProducts.get(i);
            int quantity = selectedQuantities.get(i);
            double totalPrice = product.getPrice() * quantity;
            System.out.println(product.getName() + " x " + quantity + " = " + totalPrice);
        }
        System.out.println("Total Amount: " + calculateTotalAmount(selectedProducts, selectedQuantities));
        System.out.println("--------------------------");
    }

    // Method to calculate total amount for a sale
    private double calculateTotalAmount(List<Product> products, List<Integer> quantities) {
        double totalAmount = 0;
        for (int i = 0; i < products.size(); i++) {
            Product product = products.get(i);
            int quantity = quantities.get(i);
            totalAmount += product.getPrice() * quantity;
        }
        return totalAmount;
    }

    // Method to get a product by its ID
    private Product getProductById(int productId) {
        for (Product product : products) {
            if (product.getProductId() == productId) {
                return product;
            }
        }
        return null;
    }

    // Main method
    public static void main(String[] args) {
        MedicalStoreManagementSystem system = new MedicalStoreManagementSystem();
    
        Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            // Display menu
            System.out.println("\nMenu:");
            System.out.println("1. Add Product");
            System.out.println("2. Add Customer");
            System.out.println("3. Make Sale");
            System.out.println("4. Display Products");
            System.out.println("5. Display Customers");
            System.out.println("6. Purchase Product");
            System.out.println("7. Check Expiry and Stock");
            System.out.println("8. Save Data to Files");
            System.out.println("9. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character
    
            // Handle user choice
            switch (choice) {
                case 1:
                    // Add product
                    System.out.print("Enter product name: ");
                    String productName = scanner.nextLine();
                    System.out.print("Enter product price: ");
                    double productPrice = scanner.nextDouble();
                    System.out.print("Enter product quantity: ");
                    int productQuantity = scanner.nextInt();
                    scanner.nextLine(); // Consume newline character
                    System.out.print("Enter expiry date (Optional): ");
                    String expiryDate = scanner.nextLine();
                    system.addProduct(productName, productPrice, productQuantity, expiryDate);
                    System.out.println("Product added successfully!");
                    break;
                case 2:
                    // Add customer
                    System.out.print("Enter customer name: ");
                    String customerName = scanner.nextLine();
                    System.out.print("Enter customer address: ");
                    String customerAddress = scanner.nextLine();
                    System.out.print("Enter customer phone: ");
                    String customerPhone = scanner.nextLine();
                    system.addCustomer(customerName, customerAddress, customerPhone);
                    System.out.println("Customer added successfully!");
                    break;
                case 3:
                    // Make sale
                    system.makeSaleAndPrintBill();
                    break;
                case 4:
                    // Display products
                    system.displayProducts();
                    break;
                case 5:
                    // Display customers
                    system.displayCustomers();
                    break;
                case 6:
                    // Purchase product
                    System.out.print("Enter product ID to increase quantity: ");
                    int productId = scanner.nextInt();
                    System.out.print("Enter quantity to add: ");
                    int quantityToAdd = scanner.nextInt();
                    system.purchaseProduct(productId, quantityToAdd);
                    break;
                case 7:
                    // Check expiry and stock
                    system.checkExpiryAndStock();
                    break;
                case 8:
                    // Save data to files
                    system.saveProductData("product_data.txt");
                    system.saveCustomerData("customer_data.txt");
                    system.saveSaleData("saledata.txt");
                    System.out.println("Data saved to files successfully!");
                    break;
                case 9:
                    // Exit
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 9.");
            }
        } while (choice != 9);
    
        scanner.close();
    }
}
    
