package FinalOOPProject1;

import java.util.*;

public class Admin extends User {
    static int tries = 3;
    public Admin(String id, String password) {
        super(id, password);
    }

    // Handles admin login
    public static void login(Scanner sc, HashMap<String, Admin> adminMap, Dashboard dashboard, HashMap<String, Staff> staffMap)
    {
        do{
        System.out.println("---------------------------------------------------------------");
        System.out.print("Enter Admin ID: ");
        String id = sc.nextLine();
        System.out.print("Enter Password: ");
        String pw = sc.nextLine();

        Admin a = adminMap.get(id);
        

        if (a != null && a.password.equals(pw)) {
            System.out.println("Login successful! Welcome, " + a.id);
            System.out.println("---------------------------------------------------------------");
            dashboard.showAdminDashboard(sc, a, staffMap);
        } else {
            System.out.println("Invalid credentials. Try again\nTries left " + (tries-1));
            tries--;
        }
        
        if(tries==0)
        {
            System.out.println("Log-In tries attempt failed. Exiting the system");
            System.exit(0);
        }
    }        while(tries>=0);
    }

    public boolean checkPassword(String pw) {
        return this.password.equals(pw);
    }
}
