package FinalOOPProject1;
import java.time.LocalDateTime;
import java.util.*;
//comment
 class Order {
    private String code;
    private String paymentNumber;package FinalOOPProject1;
import java.time.LocalDateTime;
import java.util.*;

 class Order {
    private String code;
    private String paymentNumber;
    private String orderChannel, discountType;
    private LocalDateTime dateTime;
    private ArrayList<Food> foodOrder = new ArrayList<>();
    private ArrayList<Integer> foodQuantity = new ArrayList<>();
    private ArrayList<Beverage> beverageOrder = new ArrayList<>();
    private ArrayList<Integer> beverageQuantity = new ArrayList<>();
    private ArrayList<String> inputBeveSize = new ArrayList<>();  // added by hannami
    private boolean isDiscounted = false; 
    private double totalAfterDiscount = 0.0; 

    public Order() {}

      public Order(String code, LocalDateTime dateTime, String orderChannel,
                 ArrayList<Food> foodOrder, ArrayList<Integer> foodQuantity,
                 ArrayList<Beverage> beverageOrder,
                 ArrayList<Integer> beverageQuantity){
        this.code = code;
        this.dateTime = dateTime;
        this.orderChannel = orderChannel;
        this.foodOrder = foodOrder;
        this.foodQuantity = foodQuantity;
        this.beverageOrder = beverageOrder;
        this.beverageQuantity = beverageQuantity;
          
          
      }
      
      // dito kasama na si  inputBeveSize = new ArrayList<>();  // added by hannami
    public Order(String code, LocalDateTime dateTime, String orderChannel,
                 ArrayList<Food> foodOrder, ArrayList<Integer> foodQuantity,
                 ArrayList<Beverage> beverageOrder,
                 ArrayList<Integer> beverageQuantity,  ArrayList<String> inputBeveSize){    
        
        
        
        this.code = code;
        this.dateTime = dateTime;
        this.orderChannel = orderChannel;
        this.foodOrder = foodOrder;
        this.foodQuantity = foodQuantity;
        this.beverageOrder = beverageOrder;
        this.beverageQuantity = beverageQuantity;
        this.inputBeveSize = inputBeveSize;
    }
    public void setPaymentNumber(String paymentNumber)
    {
    this.paymentNumber = paymentNumber;
    }

    public void setDiscounted(boolean isDiscounted)
    {
        this.isDiscounted = isDiscounted; 
    }
    public void setTotalAfterDiscount(double total)
    { 
            this.totalAfterDiscount = total;
    }
    public String getPaymentNumber() {return paymentNumber;}
    public String getDiscountType() {return discountType;}
    public boolean getIsDiscounted() { return isDiscounted; }
    public double getTotalAfterDiscount() { return totalAfterDiscount; }
    public String getCode() { return code; }
    public LocalDateTime getDate() { return dateTime; }
    public String getOrderChannel() { return orderChannel; }
    public ArrayList<Food> getFoodOrder() { return foodOrder; }
    public ArrayList<Integer> getFoodQuantity() { return foodQuantity; }
    public ArrayList<Beverage> getBeverageOrder() { return beverageOrder; }
    public ArrayList<Integer> getBeverageQuantity() { return beverageQuantity; }
    public ArrayList<String> getInputBeveSize() { return inputBeveSize; }
}

class OrderDetails
{
        Scanner in = new Scanner(System.in);

private static ArrayList<Order> orderDetails = new ArrayList<>();
    private static int orderCounter = 0;

    public void orderMenu()
    {
        int choice;
        do{
        System.out.println("[1] Add Order \n[2] View all orders \n[3] Customer\n[4] Back to main");
        choice = in.nextInt();
        customerQueue customer = new customerQueue();
        
        switch(choice)   
        {
            case 1: 
                inputOrder();
                break;
            case 2:
                for(Order orders: OrderDetails.getOrderList())
                {
                    if(OrderDetails.getOrderList().equals(null))   
                    {
                        System.out.println("No Orders Found!");
                    }
                    System.out.println("========================================================================");
                    System.out.println("Order code: " + orders.getCode());
                    System.out.println("Order Channel: " + orders.getOrderChannel());
                    System.out.println("Payment Number: " + orders.getPaymentNumber());
                    
                    if(!orders.getFoodOrder().isEmpty())
                    {   
                    for(Food f : orders.getFoodOrder())
                    {
                        System.out.print("Order name: " + f.getName());
                        System.out.println("");
                    }
                    }
                    if(!orders.getBeverageOrder().isEmpty())
                    {   
                    for(Beverage b : orders.getBeverageOrder())
                    {
                        System.out.println( "Order name: " + b.getName());
                        System.out.println("");
                    }
                    } 
                }
                System.out.println("=========================================================================");
                break;
            case 3:
                customer.customerMenu();;
                break;
            case 4:
                System.out.println("Going back to main menu...");
                break;
        }
        }while(choice!=4);
    }
    private String generateOrderCode() {
        orderCounter++;
        return String.format("OR-%03d", orderCounter);
    }
    
    public void orderDisplay()
    {
        System.out.println("======================================= ORDERS =======================================");

        
        for(Order orders : OrderDetails.getOrderList() )
        {
           String code = orders.getCode();
           LocalDateTime date =  orders.getDate();
           ArrayList<Food> order = orders.getFoodOrder();
            
            System.out.println(code + date + order);
        }
        System.out.println("");
    }

    public void inputOrder()
    {
        Food foodList = new Food();
        Beverage beverageList = new Beverage();
        String drinkSize;
        ArrayList<Food> inputFoodOrder = new ArrayList<>();
        ArrayList<Integer> inputFoodQty = new ArrayList<>();
        ArrayList<Beverage> inputBeveOrder = new ArrayList<>();
        ArrayList<Integer> inputBeveQty = new ArrayList<>();
        ArrayList<String> inputBeveSize = new ArrayList<>();  // added by hannami

        foodList.showProducts();
        beverageList.showProducts();

        char orderAgain;
        do {
            System.out.print("Enter item code to order: ");
            String orderCode = in.next().toUpperCase().trim();

           // tinanggal ko since need na makuha yung size kapag beve (hannami)
//            System.out.print("Enter quantity: ");
//            int qty = in.nextInt();
//            in.nextLine();

            boolean found = false;

            //dito chinecheck lang nya ung each index ni food para macheck nya if may match na code, 
            //then lagay nya ung item and quantity
            //code added by Lovely
            for (Food item : Food.getFoodList()) {
                if (item.getCode().equalsIgnoreCase(orderCode)) 
                {
                    // addded by hannah
                    System.out.print("Enter quantity: ");
                    int qty = in.nextInt();
                    in.nextLine();
                    
                    inputFoodOrder.add(item);
                    inputFoodQty.add(qty);
                   
                Recipe recipe = Recipe.getRecipeByProduct(item);
               
                if (recipe != null) {
                    for (var entry : recipe.getIngredients().entrySet()) {
                        Ingredient ing = entry.getKey();
                        int neededQty = entry.getValue() * qty;

                        if (ing.getStock() >= neededQty) {
                            ing.setStock(ing.getStock() - neededQty);
                            
                        } else {
                            System.out.println("Insufficient stock for ingredient: " + ing.getName());
                            return;
                        }
                    }
                }
                    System.out.println("Food order accepted!");
                    found = true;
                    break;
                }
            }

            // new condition para macheck kay beverage, tas same logic lang
            if (!found) {
                for (Beverage item : Beverage.getBeverageList()) {
                    if (item.getCode().equalsIgnoreCase(orderCode)) {
                        
                        System.out.println("Enter Size for the Drink");  // added by hannami
                        System.out.print("[R] Regular [L] Large: ");  // added by hannami
                        drinkSize = in.next().toUpperCase();
                        drinkSize = (drinkSize.equalsIgnoreCase("L")) ? "LARGE" : "REGULAR"; // pa-check pa rin nito kaso laging regular kapag hindi L
                        
                        System.out.print("Enter quantity: ");
                        int qty = in.nextInt();
                        in.nextLine();
                        inputBeveOrder.add(item);
                        inputBeveQty.add(qty);
                        inputBeveSize.add(drinkSize);  // added by hannami
                        
                        Recipe recipe = Recipe.getRecipeByProduct(item);
                        if (recipe != null) {
                        for (var entry : recipe.getIngredients().entrySet()) {
                        Ingredient ing = entry.getKey();
                        int neededQty = entry.getValue() * qty;

                        if (ing.getStock() >= neededQty) {
                            ing.setStock(ing.getStock() - neededQty);
                            //System.out.println("Beverage order accepted!");
                        } else {
                            System.out.println("Insufficient stock for ingredient: " + ing.getName());
                            return;
                        }
                    }
                    
                }
                System.out.println("Beverage order accepted!");
                found = true;
                break;
                    }
                }
            }
            //end of code by lovely

            if (!found) {
                System.out.println("Code not found. Try again.");
            }

            System.out.print("Order again? [Y/N]: ");
            orderAgain = in.next().toUpperCase().charAt(0);
        } while (orderAgain == 'Y');
        
        
        String code = generateOrderCode();
        
        Order ordChan = new Order();
        LocalDateTime currentDateTime = LocalDateTime.now();
        
        // BEGIN - input channel: loop + try/catch to accept only W or O and avoid input-buffer issues
        customerQueue cq = new customerQueue();
        String orderChannel = "";
        boolean validChannel = false;
        char inputChannel = ' ';

        while (!validChannel) {
            try {
                System.out.print("Enter Order Channel [W] Walk-In [O] Online: ");
                String token = in.next();
                inputChannel = token.toUpperCase().charAt(0);
                in.nextLine(); // clear remainder of line so subsequent nextLine() calls (e.g. addOnline) work correctly

                if (inputChannel == 'W') {
                    orderChannel = "Walk-In";
                    cq.addWalkIN();
                    validChannel = true;
                } else if (inputChannel == 'O') {
                    orderChannel = "Online";
                    cq.addOnline();
                    validChannel = true;
                } else {
                    System.out.println("Invalid input! Please enter only W or O.\n");
                }
            } catch (Exception e) {
                System.out.println("Error! Please enter a valid letter (W or O).\n");
                in.nextLine(); // clear buffer
            }
        }
        // END - input channel
        
        
        //nilgay natin lahat nung need na info ni order sa isang obj

        Order order = new Order(code, currentDateTime, orderChannel,
                inputFoodOrder, inputFoodQty, inputBeveOrder, inputBeveQty, inputBeveSize); // add ko si inputBeveSize
        System.out.print("Confirm Order [Y/N]: ");
        char choice = in.next().toUpperCase().charAt(0);
        if(choice == 'Y')
        {
     //  Queue<Customer> customer = new LinkedList<>();// so lagay ko muna ito
// new — quick fix
        Customer latest = ((LinkedList<Customer>) customerQueue.customer).getLast();
            
        if (latest != null) {
                if (latest.isWalkIn())
                {
                    System.out.println("========================= CUSTOMERS ===================================");
                    System.out.println("Customer ID: " + latest.getCustomerID() + " (Walk-In)");
                } else{
                    System.out.println("========================= CUSTOMERS ===================================");
                    System.out.println("Customer ID: " + latest.getCustomerID());
                    System.out.println("Name: " + latest.getName());
                    System.out.println("Address: " + latest.getAddress());
                    System.out.println("Contact Number: " + latest.getContactNum());
                }
        orderDetails.add(order);
        showOrderList(order);
        showOrderDetails(order);

        }
        
        // if online == print customer details, if walk in proceed
        
// customer details include name, address, is pwd/senior? if yes, calculate discount in payment method
    }
        else if(choice == 'N')
        {
           cq.cancelCustomer();
        }
    }
    public void showOrderList(Order order) 
    {
        System.out.println("\n========================= ORDERED ITEMS ================================");

        double total = 0.0;
        //here nag for loop para makuha natin ung specific na ordered na food with 
        //quantity para ma calculate natin ung subtotal
        for (int i = 0; i < order.getFoodOrder().size(); i++) 
        {
            Food f = order.getFoodOrder().get(i);
            int qty = order.getFoodQuantity().get(i);
            double subtotal = f.getPrice() * qty;
            total += subtotal;

            System.out.println(f.getCode() + " - " + f.getName() +
                    " | Quantity: " + qty +
                    " | Php. " + f.getPrice() +
                    " | Subtotal: Php. " + String.format("%.2f", subtotal));
        }
        //same logic lang pero for beve langs

    for (int i = 0; i < order.getBeverageOrder().size(); i++) {
        Beverage b = order.getBeverageOrder().get(i);
        int qty = order.getBeverageQuantity().get(i);
        String size = order.getInputBeveSize().get(i); // get the size
        double pricePerUnit = b.getPriceBySize(size);   // NEW METHOD in Beverage to get price by size
        double subtotal = pricePerUnit * qty;
        total += subtotal;

        System.out.println(b.getCode() + " - " + b.getName() + " (" + size + ")" +
                " | Quantity: " + qty +
                " | Php. " + String.format("%.2f", pricePerUnit) +
                " | Subtotal: Php. " + String.format("%.2f", subtotal));
    }

    System.out.println("========================================================================");

    Payment p = new Payment(total);
    System.out.println(" \nTotal Price: Php. " + String.format("%.2f", total));

    p.processPayment(order);
    System.out.println("========================================================================");

    }

    //method lang para macall and mapakita ung details ni order
    public void showOrderDetails(Order order)
{
    System.out.println("\n========================= ORDER DETAILS ================================");
    System.out.println("Order Code: " + order.getCode());
    System.out.println("Payment Number: " + order.getPaymentNumber());
    System.out.println("Date & Time: " + order.getDate());
    System.out.println("Channel: " + order.getOrderChannel());
    System.out.println("Items:");

    double total = 0.0;

    for (int i = 0; i < order.getFoodOrder().size(); i++)
    {
        Food f = order.getFoodOrder().get(i);
        int qty = order.getFoodQuantity().get(i);
        double subtotal = f.getPrice() * qty;
        total += subtotal;

        System.out.println(
            f.getCode() + " " + f.getName() + " x" + qty +
            " | Subtotal: Php. " + String.format("%.2f", subtotal)
        );
    }

//    for (int i = 0; i < order.getBeverageOrder().size(); i++) {
//        Beverage b = order.getBeverageOrder().get(i);
//        int qty = order.getBeverageQuantity().get(i);
//        double subtotal = b.getPrice() * qty;
//        total += subtotal;
//
//        System.out.println(
//            b.getCode() + " " + b.getName() + " x" + qty +
//            " | Subtotal: Php. " + String.format("%.2f", subtotal)
//        );
//
        for (int i = 0; i < order.getBeverageOrder().size(); i++) {
            Beverage b = order.getBeverageOrder().get(i);
            int qty = order.getBeverageQuantity().get(i);
              String size = order.getInputBeveSize().get(i); // get the size
              double pricePerUnit = b.getPriceBySize(size);   // NEW METHOD in Beverage to get price by size
              double subtotal = pricePerUnit * qty;
              total += subtotal;

            System.out.println(
                b.getCode() + " " + b.getName() + "[ " + size + " ]" + " x" + qty +
                " | Subtotal: Php. " + String.format("%.2f", subtotal)
            );
    }

    System.out.println("========================================================================");

    if (order.getIsDiscounted()) {
        System.out.println("Total before discount: Php. " + String.format("%.2f", total));
        System.out.println("PWD/Senior Discount (20%): -Php. " + String.format("%.2f", total * 0.20));
        System.out.println("Total AFTER discount: Php. " + String.format("%.2f", order.getTotalAfterDiscount()));
    } else {
        System.out.println("Total Price: Php. " + String.format("%.2f", total));
    }

    System.out.println("========================================================================\n");
}


    
    //gamitin nyo para ma access nyo ung full list ng orders kahit ala sa orderDetails class
    public static ArrayList<Order> getOrderList() 
    {
        return orderDetails;
    }
}


class Payment {
    private double totalAmount;
    private String paymentType,paymentNumber;
    private boolean isPaid;
    private boolean isDiscounted;
    private static int paymentCounter;

    Scanner in = new Scanner(System.in);
    
    private String paymentNum  = generatePaymentNumber();
    
    public String getPaymentNumber()   
    {
        return paymentNum;
    }
    public String generatePaymentNumber() 
     {
        paymentCounter++;
        return String.format("PN-%03d",paymentCounter);
     }

    public Payment(double totalAmount) {
        this.totalAmount = totalAmount;
        this.isPaid = false; 
        this.isDiscounted = false;
        this.paymentNumber = paymentNum;
    }

    public void processPayment(Order order) {
        System.out.print("Is customer PWD/Senior? [Y/N]: ");
        char discountInput = in.next().toUpperCase().charAt(0);
        if (discountInput == 'Y') {
            totalAmount = totalAmount * 0.8; 
            isDiscounted = true;
            System.out.println("Discount applied! 20% off.");
        }

        order.setDiscounted(isDiscounted);             
        order.setTotalAfterDiscount(totalAmount); 
        order.setPaymentNumber(this.paymentNumber);


        System.out.println("Total amount to pay: Php. " + String.format("%.2f", totalAmount));

        in.nextLine(); 
        System.out.print("Enter payment method [Cash/E-Wallet]: ");
        paymentType = in.nextLine().trim();

        while (!paymentType.equalsIgnoreCase("Cash") &&
               !paymentType.equalsIgnoreCase("E-Wallet")) {
            System.out.print("Invalid payment type. Enter [Cash/E-Wallet]: ");
            paymentType = in.nextLine().trim();
        }

        isPaid = true;
        System.out.println("Payment successful via " + paymentType + "!");
        System.out.println("Final Amount Paid: Php. " + String.format("%.2f", totalAmount));
        if (isDiscounted) {
            System.out.println("Note: 20% PWD/Senior discount applied.");
        }
    }
    
    public double getTotalAmount(){return totalAmount;}
}

class Customer
{
    
    private String name, address,contactNum, customerID, orderChannel;
    private static int customerCounter = 0;
    private boolean isWalkIn;
     private String generateCustomerID() 
     {
        customerCounter++;
        return String.format("CT-%03d",customerCounter);
    }
    public Customer()
    {
        this.customerID = generateCustomerID();
        this.isWalkIn = true;
    }
    public Customer(String name, String address, String contactNum)
    {
        this.name = name;
        this.address = address;
        this.contactNum = contactNum;
        this.customerID = generateCustomerID();
        this.isWalkIn = false;

    }
    public void setAddress(String address)
    {
    this.address = address;
    }
    public void setContactNum(String contactNum)
    {
    this.contactNum = contactNum;
    }

    public String getName() {return name;}
    public String getAddress() {return address;}
    public String getContactNum() {return contactNum;}
    public String getCustomerID() {return customerID;}
    public String getOrderChannel(){return orderChannel;}
    public boolean isWalkIn() { return isWalkIn; }

}

class customerQueue {
    static Queue<Customer> customer = new LinkedList<>();
    Scanner in = new Scanner (System.in);
    int choice;
//    public customerQueue(String name, String address, String contactNum, String customerID) 
//    {
//        super(name, address, contactNum, customerID);
//    }
    
    public void customerMenu()
    {
                OrderDetails details = new OrderDetails();

do{
    System.out.println("========================= CUSTOMER MENU ====================================");
        System.out.println("[1] View all customer\n[2] View latest customer\n[3] Update Customer\n[4] Back to main");
        System.out.print("Enter choice: ");
        choice = in.nextInt();
        switch(choice)  
        {
            case 1: viewCustomer();
            break;
            case 2:
                if(customer.isEmpty())
                {
                    System.out.println("========================================================================");
                    System.out.println("No customer in queue.");
                    System.out.println("========================================================================");

                     break;
                }
             Customer latest = ((LinkedList<Customer>) customer).getLast();
            if (latest != null) {
                if (latest.isWalkIn())
                {
                    System.out.println("========================================================================");
                    System.out.println("Customer ID: " + latest.getCustomerID() + " (Walk-In)");
                    System.out.println("========================================================================");
                } else {
                    System.out.println("========================================================================");
                    System.out.println("Customer ID: " + latest.getCustomerID());
                    System.out.println("Name: " + latest.getName());
                    System.out.println("Address: " + latest.getAddress());
                    System.out.println("Contact Number: " + latest.getContactNum());
                    System.out.println("========================================================================");

                }
            } else {
                System.out.println("========================================================================");
                System.out.println("No customer in queue.");
                System.out.println("========================================================================");

            }
            break;

            case 3:
                updateCustomer();
                break;
            case 4:
                details.orderMenu();
                break;
            default: System.out.println("Invalid option");
            break;
        }
    }while(choice!=4);
        }
    
    public void updateCustomer()
    {
        
    if (customer.isEmpty()) {
        System.out.println("No customers to update!");
        return;
    }

    in.nextLine(); 
    System.out.print("Enter Customer ID to update: ");
    String idToUpdate = in.nextLine().trim().toUpperCase();

    boolean found = false;

    for (Customer c : customer) {
        if (c.getCustomerID().equalsIgnoreCase(idToUpdate))
        {
            found = true;
            if(c.isWalkIn())
            {
                System.out.println("Updating Walk-In Customers is not allowed");
            }
            if(!c.isWalkIn())
            {

            System.out.print("Enter new Address (leave blank to keep current): ");
            String newAddress = in.nextLine().trim();
            if (!newAddress.isEmpty()) {
                c.setAddress(newAddress);
            }

            System.out.print("Enter new Contact Number (leave blank to keep current): ");
            String newContact = in.nextLine().trim();
            if (!newContact.isEmpty()) {
                c.setContactNum(newContact);
            }

            System.out.println("Customer details updated successfully!");
            break;
        }
        }
    }

    if (!found) {
        System.out.println("Customer ID not found!");
    }
}

    void addOnline(){
        
        System.out.print("Enter Customer Name: ");
        String name = in.nextLine();

        System.out.print("Enter Address: ");
        String address = in.nextLine();

        System.out.print("Enter Contact Number: ");
        String contact = in.nextLine();

        Customer c = new Customer(name, address, contact);
        customer.add(c);

        System.out.println("Customer added to queue!");
    }
    public void addWalkIN() {
        Customer c = new Customer();
        System.out.println("Walk-IN customer accepted");
        System.out.println("Customer ID: " + c.getCustomerID());
        customer.add(c);
    }
   
    public void cancelCustomer() {
    if(customer.isEmpty()) {
        System.out.println("No customer found!");
        return;
    }
    System.out.print("Enter Customer ID to cancel: ");
    String inCusID = in.nextLine().trim().toUpperCase();
    Iterator<Customer> it = customer.iterator();
    boolean found = false;

    while(it.hasNext()) {
        Customer c = it.next();
        if (c.getCustomerID().equalsIgnoreCase(inCusID)) {
            it.remove();   
            found = true;
            System.out.println("Customer removed.");
            break;
        }
    }

    if (!found) {
        System.out.println("Customer ID not found.");
    }
}

    
    public void viewCustomer()
    {
        System.out.println("");
        System.out.println("========================= CUSTOMERS ====================================");
        
        if(customer.isEmpty())
        {
            System.out.println("No customer found!");
            System.out.println("========================================================================");

        }
        for(Customer c : customer)
        {
            if(c.isWalkIn())
            {
                System.out.println("Customer ID: " + c.getCustomerID());
                System.out.println("========================================================================");
            }
            if(!c.isWalkIn()){ 
            System.out.println("Customer ID: " + c.getCustomerID());
            System.out.println("Customer Name: " + c.getName());
            System.out.println("Customer Address: " + c.getAddress());
            System.out.println("Contact Number: " + c.getContactNum());
            System.out.println("========================================================================");

            }    
        }
    }
}
//main by lovely

class tryMain {

    public static void main(String[] args) {
       
        Scanner in = new Scanner(System.in);
        Food food = new Food();
        Beverage drink = new Beverage();
        
        OrderDetails details = new OrderDetails();
        customerQueue customer = new customerQueue();

        food.addDefaultValue();
        drink.addDefaultValue();

        Ingredient ingre= new Ingredient();
        ingre.addDefaultValue("SP001", "Arabica Coffee Beans", 800.00, "INGREDIENT", 50);
        ingre.addDefaultValue("SP002", "Milk", 90.00, "INGREDIENT", 100);
        ingre.addDefaultValue("SP003", "Sugar", 60.00, "INGREDIENT", 200);
        //ingre.addDefaultValue("SP004", "Sugar", 60.00, "INGREDIENT", 200);
        ingre.addDefaultValue("SP005", "Croffle Batter", 120.00, "INGREDIENT", 50);
        ingre.addDefaultValue("SP006", "Whipped Cream", 110.00, "INGREDIENT", 40);
        ingre.addDefaultValue("SP007", "Chocolate Syrup", 100.00, "INGREDIENT", 60); 
        //ingre.addDefaultValue("SP007", "Strawberry Syrup", 100.00, "INGREDIENT", 60);
        ingre.addDefaultValue("SP008", "Pork", 120.00, "INGREDIENT", 60); // new pork
        ingre.addDefaultValue("SP009", "Burger Patty", 300.00, "INGREDIENT", 80);
        ingre.addDefaultValue("SP010", "Rice", 60.00, "INGREDIENT", 200);
        ingre.addDefaultValue("SP011", "Pasta Noodles", 90.00, "INGREDIENT", 100);
        ingre.addDefaultValue("SP012", "Cheese", 75.00, "INGREDIENT", 150);

        // Burger Steak (FD101) - SP009, SP010
        // FD101 - Chicken Pesto Pasta - SP011, SP012
        // newwww:  // Burger Steak (FD101) - SP009, SP010
// Burger Steak (FD101) - SP009, SP010
        // FD101 - Chicken Pesto Pasta - SP011, SP012
        
        // dapat ata :  RM001 Burger Steak - hannah
        Food RM001_Product = Food.getFoodList().get(0); // from FD101 to RM001
        Ingredient SP009 = Ingredient.getIngreList().get(7); // Burger Patty
        Ingredient SP010 = Ingredient.getIngreList().get(8); // Rice
        Recipe RM001 = new Recipe(RM001_Product);
        RM001.addIngredient(SP009, 1);
        RM001.addIngredient(SP010, 1); // from FD101 to RM001
        Recipe.addRecipe(RM001);

        // FD102 - Carbonara - SP011, SP012
        // new: dapat ata Pork Sisig
        Food RM002_Product = Food.getFoodList().get(1); // from FD102 to  RM002
        Ingredient SP008 = Ingredient.getIngreList().get(6); // Pork
        Recipe RM002 = new Recipe(RM002_Product);
        RM002.addIngredient(SP008, 1);
        RM002.addIngredient(SP010, 1); //rice
        Recipe.addRecipe(RM002);

        // FD103 - Tuna Sandwich - SP012
        // dapat ata Classic Croffle
        Food CF001_Product = Food.getFoodList().get(2); // from FD103 to  CF001
        Ingredient SP005 = Ingredient.getIngreList().get(3); //Croffle Batter
        Recipe CF001 = new Recipe(CF001_Product);
        CF001.addIngredient(SP005, 1);
        Recipe.addRecipe(CF001);

        // FD104 - BLT Sandwich - SP012
        // Chocolate Croffle
        Food CF002_Product = Food.getFoodList().get(3); // FD104 to CF002
        Recipe CF002 = new Recipe(CF002_Product);
        CF002.addIngredient(SP005, 1);
        Recipe.addRecipe(CF002);

        // FD105 - Grilled Cheese - SP012
        // Ham cheese
        Food HC001_Product = Food.getFoodList().get(4); // FD105 to HC001
        Ingredient SP012 = Ingredient.getIngreList().get(10); //cheese
        Recipe HC001 = new Recipe(HC001_Product);
        HC001.addIngredient(SP012, 1);
        Recipe.addRecipe(HC001);

        // ----------------- BEVERAGE ------------------
        //
        // BV101 - Spanish Latte - SP001, SP002, SP003
        Beverage BV101_Product = Beverage.getBeverageList().get(0); // BV101
        Ingredient SP001 = Ingredient.getIngreList().get(0); // Coffee Beans
        Ingredient SP002 = Ingredient.getIngreList().get(1); // Milk
        Ingredient SP003 = Ingredient.getIngreList().get(2); // Sugar
        Recipe BV101 = new Recipe(BV101_Product);
        BV101.addIngredient(SP001, 1);
        BV101.addIngredient(SP002, 1);
        BV101.addIngredient(SP003, 1);
        Recipe.addRecipe(BV101);

        // BV102 - Caramel Macchiato - SP001, SP002, SP003
        Beverage BV102_Product = Beverage.getBeverageList().get(1); // BV102
        Recipe BV102 = new Recipe(BV102_Product);
        BV102.addIngredient(SP001, 1);
        BV102.addIngredient(SP002, 1);
        BV102.addIngredient(SP003, 1);
        Recipe.addRecipe(BV102);

        // BV103 - Iced Americano - SP001
        Beverage BV103_Product = Beverage.getBeverageList().get(2); // BV103
        Recipe BV103 = new Recipe(BV103_Product);
        BV103.addIngredient(SP001, 1);
        Recipe.addRecipe(BV103);

        // BV104 - Matcha Latte - SP002, SP003, SP006
        Beverage BV104_Product = Beverage.getBeverageList().get(3); // BV104
        Ingredient SP006 = Ingredient.getIngreList().get(4); // Whipped Cream
        Recipe BV104 = new Recipe(BV104_Product);
        BV104.addIngredient(SP002, 1);
        BV104.addIngredient(SP003, 1);
        BV104.addIngredient(SP006, 1);
        Recipe.addRecipe(BV104);

        // BV105 - Cold Brew - SP001, SP002, SP003
        Beverage BV105_Product = Beverage.getBeverageList().get(4); // BV105
        Recipe BV105 = new Recipe(BV105_Product);
        BV105.addIngredient(SP001, 1);
        BV105.addIngredient(SP002, 1);
        BV105.addIngredient(SP003, 1);
        Recipe.addRecipe(BV105);


        // nag add lang ako sa last 2 kasi hindi siya magrereflect -- hannaami
        Beverage SD001_Product = Beverage.getBeverageList().get(5); // SD001 Strawberry
        Recipe SD001 = new Recipe(SD001_Product); 
        SD001.addIngredient(SP002, 1);
        SD001.addIngredient(SP003, 1);
        Recipe.addRecipe(SD001);

        Beverage SD002_Product = Beverage.getBeverageList().get(6); // SD002 Blueberry
        Recipe SD002 = new Recipe(SD002_Product);
        BV105.addIngredient(SP002, 1);
        BV105.addIngredient(SP003, 1);
        Recipe.addRecipe(SD002);

//
int choice =0;
        do {
            try {
            System.out.println("\n========================= CAFE INVENTORY SYSTEM ========================");
           
            System.out.println("[1] Add/Input Food");
            System.out.println("[2] Show Food");
            System.out.println("[3] Update Food");
            System.out.println("[4] Delete Food");
            System.out.println("[5] Add Beverage");
            System.out.println("[6] Show Beverage");
            System.out.println("[7] Update Beverage");
            System.out.println("[8] Delete Beverage");
            System.out.println("[9] Order");
            System.out.println("[10] Customer");
            System.out.println("[0] Exit");
            System.out.println("=========================================================================");
            System.out.print("Enter your choice: ");
            choice = in.nextInt();
            
            } catch (InputMismatchException e)
            {
                System.out.println("Invalid Input...");   
                in.nextLine(); 
                choice = -1;   
            }
            switch (choice) {
                case 1 :  food.addInputProduct();break;
                case 2 :  food.showProducts();break;
                case 3 :  food.updateProduct();break;
                case 4 :  food.deleteProduct();break;
                case 5 :  drink.addInputProduct();break;
                case 6 :  drink.showProducts();break;
                case 7 :  drink.updateProduct();break;
                case 8 :  drink.deleteProduct();break;
                case 9 :  details.orderMenu();break;
                case 10:  customer.customerMenu();break;
                case 0 :  System.out.println("\nExiting system... Thank you!");break;
                default :  System.out.println("\nInvalid choice. Try again.");break;
            }
        } while (choice != 0);
    }
}
    private String orderChannel, discountType;
    private LocalDateTime dateTime;
    private ArrayList<Food> foodOrder = new ArrayList<>();
    private ArrayList<Integer> foodQuantity = new ArrayList<>();
    private ArrayList<Beverage> beverageOrder = new ArrayList<>();
    private ArrayList<Integer> beverageQuantity = new ArrayList<>();
    private ArrayList<String> inputBeveSize = new ArrayList<>();  // added by hannami
    private boolean isDiscounted = false; 
    private double totalAfterDiscount = 0.0; 

    public Order() {}

      public Order(String code, LocalDateTime dateTime, String orderChannel,
                 ArrayList<Food> foodOrder, ArrayList<Integer> foodQuantity,
                 ArrayList<Beverage> beverageOrder,
                 ArrayList<Integer> beverageQuantity){
        this.code = code;
        this.dateTime = dateTime;
        this.orderChannel = orderChannel;
        this.foodOrder = foodOrder;
        this.foodQuantity = foodQuantity;
        this.beverageOrder = beverageOrder;
        this.beverageQuantity = beverageQuantity;
          
          
      }
      
      // dito kasama na si  inputBeveSize = new ArrayList<>();  // added by hannami
    public Order(String code, LocalDateTime dateTime, String orderChannel,
                 ArrayList<Food> foodOrder, ArrayList<Integer> foodQuantity,
                 ArrayList<Beverage> beverageOrder,
                 ArrayList<Integer> beverageQuantity,  ArrayList<String> inputBeveSize){    
        
        
        
        this.code = code;
        this.dateTime = dateTime;
        this.orderChannel = orderChannel;
        this.foodOrder = foodOrder;
        this.foodQuantity = foodQuantity;
        this.beverageOrder = beverageOrder;
        this.beverageQuantity = beverageQuantity;
        this.inputBeveSize = inputBeveSize;
    }
    public void setPaymentNumber(String paymentNumber)
    {
    this.paymentNumber = paymentNumber;
    }

    public void setDiscounted(boolean isDiscounted)
    {
        this.isDiscounted = isDiscounted; 
    }
    public void setTotalAfterDiscount(double total)
    { 
            this.totalAfterDiscount = total;
    }
    public String getPaymentNumber() {return paymentNumber;}
    public String getDiscountType() {return discountType;}
    public boolean getIsDiscounted() { return isDiscounted; }
    public double getTotalAfterDiscount() { return totalAfterDiscount; }
    public String getCode() { return code; }
    public LocalDateTime getDate() { return dateTime; }
    public String getOrderChannel() { return orderChannel; }
    public ArrayList<Food> getFoodOrder() { return foodOrder; }
    public ArrayList<Integer> getFoodQuantity() { return foodQuantity; }
    public ArrayList<Beverage> getBeverageOrder() { return beverageOrder; }
    public ArrayList<Integer> getBeverageQuantity() { return beverageQuantity; }
    public ArrayList<String> getInputBeveSize() { return inputBeveSize; }
}

class OrderDetails
{
        Scanner in = new Scanner(System.in);

private static ArrayList<Order> orderDetails = new ArrayList<>();
    private static int orderCounter = 0;

    public void orderMenu()
    {
        int choice;
        System.out.println("[1] Add Order \n[2] View all orders \n[3] Customer\n[4] Back to main");
        choice = in.nextInt();
        customerQueue customer = new customerQueue();
        
        switch(choice)   
        {
            case 1: 
                inputOrder();
                break;
            case 2:
                for(Order orders: OrderDetails.getOrderList())
                {
                    System.out.println("========================================================================");
                    System.out.println("Order code: " + orders.getCode());
                    System.out.println("Order Channel: " + orders.getOrderChannel());
                    
                    if(!orders.getFoodOrder().isEmpty())
                    {   
                    for(Food f : orders.getFoodOrder())
                    {
                        System.out.print("Order name: " + f.getName());
                        System.out.println("");
                    }
                    }
                    if(!orders.getBeverageOrder().isEmpty())
                    {   
                    for(Beverage b : orders.getBeverageOrder())
                    {
                        System.out.println( "Order name: " + b.getName());
                        System.out.println("");
                    }
                    } 
                }
                System.out.println("=========================================================================");
                break;
            case 3:
                customer.customerMenu();;
                break;
            case 4:
                System.out.println("Going back to main menu...");
                break;
        }
    }
    private String generateOrderCode() {
        orderCounter++;
        return String.format("OR-%03d", orderCounter);
    }
    
    public void orderDisplay()
    {
        System.out.println("======================================= ORDERS =======================================");

        
        for(Order orders : OrderDetails.getOrderList() )
        {
           String code = orders.getCode();
           LocalDateTime date =  orders.getDate();
           ArrayList<Food> order = orders.getFoodOrder();
            
            System.out.println(code + date + order);
        }
        System.out.println("");
    }

    public void inputOrder()
    {
        Food foodList = new Food();
        Beverage beverageList = new Beverage();
        String drinkSize;
        ArrayList<Food> inputFoodOrder = new ArrayList<>();
        ArrayList<Integer> inputFoodQty = new ArrayList<>();
        ArrayList<Beverage> inputBeveOrder = new ArrayList<>();
        ArrayList<Integer> inputBeveQty = new ArrayList<>();
        ArrayList<String> inputBeveSize = new ArrayList<>();  // added by hannami

        foodList.showProducts();
        beverageList.showProducts();

        char orderAgain;
        do {
            System.out.print("Enter item code to order: ");
            String orderCode = in.next().toUpperCase().trim();

           // tinanggal ko since need na makuha yung size kapag beve (hannami)
//            System.out.print("Enter quantity: ");
//            int qty = in.nextInt();
//            in.nextLine();

            boolean found = false;

            //dito chinecheck lang nya ung each index ni food para macheck nya if may match na code, 
            //then lagay nya ung item and quantity
            //code added by Lovely
            for (Food item : Food.getFoodList()) {
                if (item.getCode().equalsIgnoreCase(orderCode)) 
                {
                    // addded by hannah
                    System.out.print("Enter quantity: ");
                    int qty = in.nextInt();
                    in.nextLine();
                    
                    inputFoodOrder.add(item);
                    inputFoodQty.add(qty);
                   
                Recipe recipe = Recipe.getRecipeByProduct(item);
               
                if (recipe != null) {
                    for (var entry : recipe.getIngredients().entrySet()) {
                        Ingredient ing = entry.getKey();
                        int neededQty = entry.getValue() * qty;

                        if (ing.getStock() >= neededQty) {
                            ing.setStock(ing.getStock() - neededQty);
                            
                        } else {
                            System.out.println("Insufficient stock for ingredient: " + ing.getName());
                            return;
                        }
                    }
                }
                    System.out.println("Food order accepted!");
                    found = true;
                    break;
                }
            }

            // new condition para macheck kay beverage, tas same logic lang
            if (!found) {
                for (Beverage item : Beverage.getBeverageList()) {
                    if (item.getCode().equalsIgnoreCase(orderCode)) {
                        
                        System.out.println("Enter Size for the Drink");  // added by hannami
                        System.out.print("[R] Regular [L] Large: ");  // added by hannami
                        drinkSize = in.next().toUpperCase();
                        drinkSize = (drinkSize.equalsIgnoreCase("L")) ? "LARGE" : "REGULAR"; // pa-check pa rin nito kaso laging regular kapag hindi L
                        
                        System.out.print("Enter quantity: ");
                        int qty = in.nextInt();
                        in.nextLine();
                        inputBeveOrder.add(item);
                        inputBeveQty.add(qty);
                        inputBeveSize.add(drinkSize);  // added by hannami
                        
                        Recipe recipe = Recipe.getRecipeByProduct(item);
                        if (recipe != null) {
                        for (var entry : recipe.getIngredients().entrySet()) {
                        Ingredient ing = entry.getKey();
                        int neededQty = entry.getValue() * qty;

                        if (ing.getStock() >= neededQty) {
                            ing.setStock(ing.getStock() - neededQty);
                            //System.out.println("Beverage order accepted!");
                        } else {
                            System.out.println("Insufficient stock for ingredient: " + ing.getName());
                            return;
                        }
                    }
                    
                }
                System.out.println("Beverage order accepted!");
                found = true;
                break;
                    }
                }
            }
            //end of code by lovely

            if (!found) {
                System.out.println("Code not found. Try again.");
            }

            System.out.print("Order again? [Y/N]: ");
            orderAgain = in.next().toUpperCase().charAt(0);
        } while (orderAgain == 'Y');
        
        
        String code = generateOrderCode();
        
        Order ordChan = new Order();
        LocalDateTime currentDateTime = LocalDateTime.now();
        
        // BEGIN - input channel: loop + try/catch to accept only W or O and avoid input-buffer issues
        customerQueue cq = new customerQueue();
        String orderChannel = "";
        boolean validChannel = false;
        char inputChannel = ' ';

        while (!validChannel) {
            try {
                System.out.print("Enter Order Channel [W] Walk-In [O] Online: ");
                String token = in.next();
                inputChannel = token.toUpperCase().charAt(0);
                in.nextLine(); // clear remainder of line so subsequent nextLine() calls (e.g. addOnline) work correctly

                if (inputChannel == 'W') {
                    orderChannel = "Walk-In";
                    cq.addWalkIN();
                    validChannel = true;
                } else if (inputChannel == 'O') {
                    orderChannel = "Online";
                    cq.addOnline();
                    validChannel = true;
                } else {
                    System.out.println("Invalid input! Please enter only W or O.\n");
                }
            } catch (Exception e) {
                System.out.println("Error! Please enter a valid letter (W or O).\n");
                in.nextLine(); // clear buffer
            }
        }
        // END - input channel
        
        
        //nilgay natin lahat nung need na info ni order sa isang obj

        Order order = new Order(code, currentDateTime, orderChannel,
                inputFoodOrder, inputFoodQty, inputBeveOrder, inputBeveQty, inputBeveSize); // add ko si inputBeveSize
        System.out.print("Confirm Order [Y/N]: ");
        char choice = in.next().toUpperCase().charAt(0);
        if(choice == 'Y')
        {
     //  Queue<Customer> customer = new LinkedList<>();// so lagay ko muna ito
// new — quick fix
        Customer latest = ((LinkedList<Customer>) customerQueue.customer).getLast();
            
        if (latest != null) {
                if (latest.isWalkIn())
                {
                    System.out.println("========================= CUSTOMERS ===================================");
                    System.out.println("Customer ID: " + latest.getCustomerID() + " (Walk-In)");
                } else{
                    System.out.println("========================= CUSTOMERS ===================================");
                    System.out.println("Customer ID: " + latest.getCustomerID());
                    System.out.println("Name: " + latest.getName());
                    System.out.println("Address: " + latest.getAddress());
                    System.out.println("Contact Number: " + latest.getContactNum());
                }
        orderDetails.add(order);
        showOrderList(order);
        showOrderDetails(order);

        }
        
        // if online == print customer details, if walk in proceed
        
// customer details include name, address, is pwd/senior? if yes, calculate discount in payment method
    }
        else if(choice == 'N')
        {
           cq.cancelCustomer();
        }
    }
    public void showOrderList(Order order) 
    {
        System.out.println("\n========================= ORDERED ITEMS ================================");

        double total = 0.0;
        //here nag for loop para makuha natin ung specific na ordered na food with 
        //quantity para ma calculate natin ung subtotal
        for (int i = 0; i < order.getFoodOrder().size(); i++) 
        {
            Food f = order.getFoodOrder().get(i);
            int qty = order.getFoodQuantity().get(i);
            double subtotal = f.getPrice() * qty;
            total += subtotal;

            System.out.println(f.getCode() + " - " + f.getName() +
                    " | Quantity: " + qty +
                    " | Php. " + f.getPrice() +
                    " | Subtotal: Php. " + String.format("%.2f", subtotal));
        }
        //same logic lang pero for beve langs

    for (int i = 0; i < order.getBeverageOrder().size(); i++) {
        Beverage b = order.getBeverageOrder().get(i);
        int qty = order.getBeverageQuantity().get(i);
        String size = order.getInputBeveSize().get(i); // get the size
        double pricePerUnit = b.getPriceBySize(size);   // NEW METHOD in Beverage to get price by size
        double subtotal = pricePerUnit * qty;
        total += subtotal;

        System.out.println(b.getCode() + " - " + b.getName() + " (" + size + ")" +
                " | Quantity: " + qty +
                " | Php. " + String.format("%.2f", pricePerUnit) +
                " | Subtotal: Php. " + String.format("%.2f", subtotal));
    }

    System.out.println("========================================================================");

    Payment p = new Payment(total);
    String paymentNum  = p.generatePaymentNumber();
    System.out.println("Payment Number: " + paymentNum+ " \nTotal Price: Php. " + String.format("%.2f", total));

    p.processPayment(order);
    System.out.println("========================================================================");

    }

    //method lang para macall and mapakita ung details ni order
    public void showOrderDetails(Order order)
{
    System.out.println("\n========================= ORDER DETAILS ================================");
    System.out.println("Order Code: " + order.getCode());
    System.out.println("Payment Number: " + order.getPaymentNumber());
    System.out.println("Date & Time: " + order.getDate());
    System.out.println("Channel: " + order.getOrderChannel());
    System.out.println("Items:");

    double total = 0.0;

    for (int i = 0; i < order.getFoodOrder().size(); i++)
    {
        Food f = order.getFoodOrder().get(i);
        int qty = order.getFoodQuantity().get(i);
        double subtotal = f.getPrice() * qty;
        total += subtotal;

        System.out.println(
            f.getCode() + " " + f.getName() + " x" + qty +
            " | Subtotal: Php. " + String.format("%.2f", subtotal)
        );
    }

//    for (int i = 0; i < order.getBeverageOrder().size(); i++) {
//        Beverage b = order.getBeverageOrder().get(i);
//        int qty = order.getBeverageQuantity().get(i);
//        double subtotal = b.getPrice() * qty;
//        total += subtotal;
//
//        System.out.println(
//            b.getCode() + " " + b.getName() + " x" + qty +
//            " | Subtotal: Php. " + String.format("%.2f", subtotal)
//        );
//
        for (int i = 0; i < order.getBeverageOrder().size(); i++) {
            Beverage b = order.getBeverageOrder().get(i);
            int qty = order.getBeverageQuantity().get(i);
              String size = order.getInputBeveSize().get(i); // get the size
              double pricePerUnit = b.getPriceBySize(size);   // NEW METHOD in Beverage to get price by size
              double subtotal = pricePerUnit * qty;
              total += subtotal;

            System.out.println(
                b.getCode() + " " + b.getName() + "[ " + size + " ]" + " x" + qty +
                " | Subtotal: Php. " + String.format("%.2f", subtotal)
            );
    }

    System.out.println("========================================================================");

    if (order.getIsDiscounted()) {
        System.out.println("Total before discount: Php. " + String.format("%.2f", total));
        System.out.println("PWD/Senior Discount (20%): -Php. " + String.format("%.2f", total * 0.20));
        System.out.println("Total AFTER discount: Php. " + String.format("%.2f", order.getTotalAfterDiscount()));
    } else {
        System.out.println("Total Price: Php. " + String.format("%.2f", total));
    }

    System.out.println("========================================================================\n");
}


    
    //gamitin nyo para ma access nyo ung full list ng orders kahit ala sa orderDetails class
    public static ArrayList<Order> getOrderList() 
    {
        return orderDetails;
    }
}


class Payment {
    private double totalAmount;
    private String paymentType,paymentNumber;
    private boolean isPaid;
    private boolean isDiscounted;
    private static int paymentCounter;

    Scanner in = new Scanner(System.in);
    
    public String generatePaymentNumber() 
     {
        paymentCounter++;
        return String.format("PN-%03d",paymentCounter);
    }

    public Payment(double totalAmount) {
        this.totalAmount = totalAmount;
        this.isPaid = false; 
        this.isDiscounted = false;
        this.paymentNumber = generatePaymentNumber();
    }

    public void processPayment(Order order) {
        System.out.print("Is customer PWD/Senior? [Y/N]: ");
        char discountInput = in.next().toUpperCase().charAt(0);
        if (discountInput == 'Y') {
            totalAmount = totalAmount * 0.8; 
            isDiscounted = true;
            System.out.println("Discount applied! 20% off.");
        }

        order.setDiscounted(isDiscounted);             
        order.setTotalAfterDiscount(totalAmount); 
        order.setPaymentNumber(this.paymentNumber);


        System.out.println("Total amount to pay: Php. " + String.format("%.2f", totalAmount));

        in.nextLine(); 
        System.out.print("Enter payment method [Cash/E-Wallet]: ");
        paymentType = in.nextLine().trim();

        while (!paymentType.equalsIgnoreCase("Cash") &&
               !paymentType.equalsIgnoreCase("E-Wallet")) {
            System.out.print("Invalid payment type. Enter [Cash/E-Wallet]: ");
            paymentType = in.nextLine().trim();
        }

        isPaid = true;
        System.out.println("Payment successful via " + paymentType + "!");
        System.out.println("Final Amount Paid: Php. " + String.format("%.2f", totalAmount));
        if (isDiscounted) {
            System.out.println("Note: 20% PWD/Senior discount applied.");
        }
    }
    
    public double getTotalAmount(){return totalAmount;}
}

class Customer
{
    
    private String name, address,contactNum, customerID, orderChannel;
    private static int customerCounter = 0;
    private boolean isWalkIn;
     private String generateCustomerID() 
     {
        customerCounter++;
        return String.format("CT-%03d",customerCounter);
    }
    public Customer()
    {
        this.customerID = generateCustomerID();
        this.isWalkIn = true;
    }
    public Customer(String name, String address, String contactNum)
    {
        this.name = name;
        this.address = address;
        this.contactNum = contactNum;
        this.customerID = generateCustomerID();
        this.isWalkIn = false;

    }
    public void setAddress(String address)
    {
    this.address = address;
    }
    public void setContactNum(String contactNum)
    {
    this.contactNum = contactNum;
    }

    public String getName() {return name;}
    public String getAddress() {return address;}
    public String getContactNum() {return contactNum;}
    public String getCustomerID() {return customerID;}
    public String getOrderChannel(){return orderChannel;}
    public boolean isWalkIn() { return isWalkIn; }

}

class customerQueue {
    static Queue<Customer> customer = new LinkedList<>();
    Scanner in = new Scanner (System.in);
    int choice;
//    public customerQueue(String name, String address, String contactNum, String customerID) 
//    {
//        super(name, address, contactNum, customerID);
//    }
    
    public void customerMenu()
    {
                OrderDetails details = new OrderDetails();

do{
    System.out.println("========================= CUSTOMER MENU ====================================");
        System.out.println("[1] View all customer\n[2] View latest customer\n[3] Update Customer\n[4] Back to main");
        System.out.print("Enter choice: ");
        choice = in.nextInt();
        switch(choice)  
        {
            case 1: viewCustomer();
            break;
            case 2:
                if(customer.isEmpty())
                {
                    System.out.println("========================================================================");
                    System.out.println("No customer in queue.");
                    System.out.println("========================================================================");

                     break;
                }
             Customer latest = ((LinkedList<Customer>) customer).getLast();
            if (latest != null) {
                if (latest.isWalkIn())
                {
                    System.out.println("========================================================================");
                    System.out.println("Customer ID: " + latest.getCustomerID() + " (Walk-In)");
                    System.out.println("========================================================================");
                } else {
                    System.out.println("========================================================================");
                    System.out.println("Customer ID: " + latest.getCustomerID());
                    System.out.println("Name: " + latest.getName());
                    System.out.println("Address: " + latest.getAddress());
                    System.out.println("Contact Number: " + latest.getContactNum());
                    System.out.println("========================================================================");

                }
            } else {
                System.out.println("========================================================================");
                System.out.println("No customer in queue.");
                System.out.println("========================================================================");

            }
            break;

            case 3:
                updateCustomer();
                break;
            case 4:
                details.orderMenu();
                break;
            default: System.out.println("Invalid option");
            break;
        }
    }while(choice!=4);
        }
    
    public void updateCustomer()
    {
        
    if (customer.isEmpty()) {
        System.out.println("No customers to update!");
        return;
    }

    in.nextLine(); 
    System.out.print("Enter Customer ID to update: ");
    String idToUpdate = in.nextLine().trim().toUpperCase();

    boolean found = false;

    for (Customer c : customer) {
        if (c.getCustomerID().equalsIgnoreCase(idToUpdate))
        {
            found = true;
            if(c.isWalkIn())
            {
                System.out.println("Updating Walk-In Customers is not allowed");
            }
            if(!c.isWalkIn())
            {

            System.out.print("Enter new Address (leave blank to keep current): ");
            String newAddress = in.nextLine().trim();
            if (!newAddress.isEmpty()) {
                c.setAddress(newAddress);
            }

            System.out.print("Enter new Contact Number (leave blank to keep current): ");
            String newContact = in.nextLine().trim();
            if (!newContact.isEmpty()) {
                c.setContactNum(newContact);
            }

            System.out.println("Customer details updated successfully!");
            break;
        }
        }
    }

    if (!found) {
        System.out.println("Customer ID not found!");
    }
}

    void addOnline(){
        
        System.out.print("Enter Customer Name: ");
        String name = in.nextLine();

        System.out.print("Enter Address: ");
        String address = in.nextLine();

        System.out.print("Enter Contact Number: ");
        String contact = in.nextLine();

        Customer c = new Customer(name, address, contact);
        customer.add(c);

        System.out.println("Customer added to queue!");
    }
    public void addWalkIN() {
        Customer c = new Customer();
        System.out.println("Walk-IN customer accepted");
        System.out.println("Customer ID: " + c.getCustomerID());
        customer.add(c);
    }
   
    public void cancelCustomer() {
    if(customer.isEmpty()) {
        System.out.println("No customer found!");
        return;
    }
    System.out.print("Enter Customer ID to cancel: ");
    String inCusID = in.nextLine().trim().toUpperCase();
    Iterator<Customer> it = customer.iterator();
    boolean found = false;

    while(it.hasNext()) {
        Customer c = it.next();
        if (c.getCustomerID().equalsIgnoreCase(inCusID)) {
            it.remove();   
            found = true;
            System.out.println("Customer removed.");
            break;
        }
    }

    if (!found) {
        System.out.println("Customer ID not found.");
    }
}

    
    public void viewCustomer()
    {
        System.out.println("");
        System.out.println("========================= CUSTOMERS ====================================");
        
        if(customer.isEmpty())
        {
            System.out.println("No customer found!");
            System.out.println("========================================================================");

        }
        for(Customer c : customer)
        {
            if(c.isWalkIn())
            {
                System.out.println("Customer ID: " + c.getCustomerID());
                System.out.println("========================================================================");
            }
            if(!c.isWalkIn()){ 
            System.out.println("Customer ID: " + c.getCustomerID());
            System.out.println("Customer Name: " + c.getName());
            System.out.println("Customer Address: " + c.getAddress());
            System.out.println("Contact Number: " + c.getContactNum());
            System.out.println("========================================================================");

            }    
        }
    }
}
//main by lovely

class tryMain {

    public static void main(String[] args) {
       
        Scanner in = new Scanner(System.in);
        Food food = new Food();
        Beverage drink = new Beverage();
        
        OrderDetails details = new OrderDetails();
        customerQueue customer = new customerQueue();

        food.addDefaultValue();
        drink.addDefaultValue();

        Ingredient ingre= new Ingredient();
        ingre.addDefaultValue("SP001", "Arabica Coffee Beans", 800.00, "INGREDIENT", 50);
        ingre.addDefaultValue("SP002", "Milk", 90.00, "INGREDIENT", 100);
        ingre.addDefaultValue("SP003", "Sugar", 60.00, "INGREDIENT", 200);
        //ingre.addDefaultValue("SP004", "Sugar", 60.00, "INGREDIENT", 200);
        ingre.addDefaultValue("SP005", "Croffle Batter", 120.00, "INGREDIENT", 50);
        ingre.addDefaultValue("SP006", "Whipped Cream", 110.00, "INGREDIENT", 40);
        ingre.addDefaultValue("SP007", "Chocolate Syrup", 100.00, "INGREDIENT", 60); 
        //ingre.addDefaultValue("SP007", "Strawberry Syrup", 100.00, "INGREDIENT", 60);
        ingre.addDefaultValue("SP008", "Pork", 120.00, "INGREDIENT", 60); // new pork
        ingre.addDefaultValue("SP009", "Burger Patty", 300.00, "INGREDIENT", 80);
        ingre.addDefaultValue("SP010", "Rice", 60.00, "INGREDIENT", 200);
        ingre.addDefaultValue("SP011", "Pasta Noodles", 90.00, "INGREDIENT", 100);
        ingre.addDefaultValue("SP012", "Cheese", 75.00, "INGREDIENT", 150);

        // Burger Steak (FD101) - SP009, SP010
        // FD101 - Chicken Pesto Pasta - SP011, SP012
        // newwww:  // Burger Steak (FD101) - SP009, SP010
// Burger Steak (FD101) - SP009, SP010
        // FD101 - Chicken Pesto Pasta - SP011, SP012
        
        // dapat ata :  RM001 Burger Steak - hannah
        Food RM001_Product = Food.getFoodList().get(0); // from FD101 to RM001
        Ingredient SP009 = Ingredient.getIngreList().get(7); // Burger Patty
        Ingredient SP010 = Ingredient.getIngreList().get(8); // Rice
        Recipe RM001 = new Recipe(RM001_Product);
        RM001.addIngredient(SP009, 1);
        RM001.addIngredient(SP010, 1); // from FD101 to RM001
        Recipe.addRecipe(RM001);

        // FD102 - Carbonara - SP011, SP012
        // new: dapat ata Pork Sisig
        Food RM002_Product = Food.getFoodList().get(1); // from FD102 to  RM002
        Ingredient SP008 = Ingredient.getIngreList().get(6); // Pork
        Recipe RM002 = new Recipe(RM002_Product);
        RM002.addIngredient(SP008, 1);
        RM002.addIngredient(SP010, 1); //rice
        Recipe.addRecipe(RM002);

        // FD103 - Tuna Sandwich - SP012
        // dapat ata Classic Croffle
        Food CF001_Product = Food.getFoodList().get(2); // from FD103 to  CF001
        Ingredient SP005 = Ingredient.getIngreList().get(3); //Croffle Batter
        Recipe CF001 = new Recipe(CF001_Product);
        CF001.addIngredient(SP005, 1);
        Recipe.addRecipe(CF001);

        // FD104 - BLT Sandwich - SP012
        // Chocolate Croffle
        Food CF002_Product = Food.getFoodList().get(3); // FD104 to CF002
        Recipe CF002 = new Recipe(CF002_Product);
        CF002.addIngredient(SP005, 1);
        Recipe.addRecipe(CF002);

        // FD105 - Grilled Cheese - SP012
        // Ham cheese
        Food HC001_Product = Food.getFoodList().get(4); // FD105 to HC001
        Ingredient SP012 = Ingredient.getIngreList().get(10); //cheese
        Recipe HC001 = new Recipe(HC001_Product);
        HC001.addIngredient(SP012, 1);
        Recipe.addRecipe(HC001);

        // ----------------- BEVERAGE ------------------
        //
        // BV101 - Spanish Latte - SP001, SP002, SP003
        Beverage BV101_Product = Beverage.getBeverageList().get(0); // BV101
        Ingredient SP001 = Ingredient.getIngreList().get(0); // Coffee Beans
        Ingredient SP002 = Ingredient.getIngreList().get(1); // Milk
        Ingredient SP003 = Ingredient.getIngreList().get(2); // Sugar
        Recipe BV101 = new Recipe(BV101_Product);
        BV101.addIngredient(SP001, 1);
        BV101.addIngredient(SP002, 1);
        BV101.addIngredient(SP003, 1);
        Recipe.addRecipe(BV101);

        // BV102 - Caramel Macchiato - SP001, SP002, SP003
        Beverage BV102_Product = Beverage.getBeverageList().get(1); // BV102
        Recipe BV102 = new Recipe(BV102_Product);
        BV102.addIngredient(SP001, 1);
        BV102.addIngredient(SP002, 1);
        BV102.addIngredient(SP003, 1);
        Recipe.addRecipe(BV102);

        // BV103 - Iced Americano - SP001
        Beverage BV103_Product = Beverage.getBeverageList().get(2); // BV103
        Recipe BV103 = new Recipe(BV103_Product);
        BV103.addIngredient(SP001, 1);
        Recipe.addRecipe(BV103);

        // BV104 - Matcha Latte - SP002, SP003, SP006
        Beverage BV104_Product = Beverage.getBeverageList().get(3); // BV104
        Ingredient SP006 = Ingredient.getIngreList().get(4); // Whipped Cream
        Recipe BV104 = new Recipe(BV104_Product);
        BV104.addIngredient(SP002, 1);
        BV104.addIngredient(SP003, 1);
        BV104.addIngredient(SP006, 1);
        Recipe.addRecipe(BV104);

        // BV105 - Cold Brew - SP001, SP002, SP003
        Beverage BV105_Product = Beverage.getBeverageList().get(4); // BV105
        Recipe BV105 = new Recipe(BV105_Product);
        BV105.addIngredient(SP001, 1);
        BV105.addIngredient(SP002, 1);
        BV105.addIngredient(SP003, 1);
        Recipe.addRecipe(BV105);


        // nag add lang ako sa last 2 kasi hindi siya magrereflect -- hannaami
        Beverage SD001_Product = Beverage.getBeverageList().get(5); // SD001 Strawberry
        Recipe SD001 = new Recipe(SD001_Product); 
        SD001.addIngredient(SP002, 1);
        SD001.addIngredient(SP003, 1);
        Recipe.addRecipe(SD001);

        Beverage SD002_Product = Beverage.getBeverageList().get(6); // SD002 Blueberry
        Recipe SD002 = new Recipe(SD002_Product);
        BV105.addIngredient(SP002, 1);
        BV105.addIngredient(SP003, 1);
        Recipe.addRecipe(SD002);

//
int choice =0;
        do {
            try {
            System.out.println("\n========================= CAFE INVENTORY SYSTEM ========================");
           
            System.out.println("[1] Add/Input Food");
            System.out.println("[2] Show Food");
            System.out.println("[3] Update Food");
            System.out.println("[4] Delete Food");
            System.out.println("[5] Add Beverage");
            System.out.println("[6] Show Beverage");
            System.out.println("[7] Update Beverage");
            System.out.println("[8] Delete Beverage");
            System.out.println("[9] Order");
            System.out.println("[10] Customer");
            System.out.println("[0] Exit");
            System.out.println("=========================================================================");
            System.out.print("Enter your choice: ");
            choice = in.nextInt();
            
            } catch (InputMismatchException e)
            {
                System.out.println("Invalid Input...");   
                in.nextLine(); 
                choice = -1;   
            }
            switch (choice) {
                case 1 :  food.addInputProduct();break;
                case 2 :  food.showProducts();break;
                case 3 :  food.updateProduct();break;
                case 4 :  food.deleteProduct();break;
                case 5 :  drink.addInputProduct();break;
                case 6 :  drink.showProducts();break;
                case 7 :  drink.updateProduct();break;
                case 8 :  drink.deleteProduct();break;
                case 9 :  details.orderMenu();break;
                case 10:  customer.customerMenu();break;
                case 0 :  System.out.println("\nExiting system... Thank you!");break;
                default :  System.out.println("\nInvalid choice. Try again.");break;
            }
        } while (choice != 0);
    }

}
