//package project;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

abstract class Person{
    protected String name;
    protected String contactnum;

    Person(String name,String contactnum){
        this.name=name;
        this.contactnum=contactnum;

    }
    abstract void displayInfo();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactnum() {
        return contactnum;
    }

    public void setContactnum(String contactnum) {
        this.contactnum = contactnum;
    }
}

class Car extends Person {
    private String vehicletype;
    private String  plate_no;
    private String  company;
    private String  model;
    String id;
    private static ArrayList<Car> customers = new ArrayList<>();

    Car(String id,String name, String contactnum, String vehicletype, String plate_no,String company,String model) {
        super(name, contactnum);
        this.id = id;
        this.vehicletype=vehicletype;
        this.plate_no=plate_no;
        this.company=company;
        this.model=model;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getPlate_no() {
        return plate_no;
    }

    public void setPlate_no(String plate_no) {
        this.plate_no = plate_no;
    }

    public String getVehicletype() {
        return vehicletype;
    }

    public void setVehicletype(String vehicletype) {
        this.vehicletype = vehicletype;
    }

    public void info() {
        Scanner info = new Scanner(System.in);
        int choice;
        System.out.println("*** Welcome to GaragePro Mechanic Shop ***");


        System.out.println("\nMain Menu:");
        System.out.println("1. New Customer");
        System.out.println("2. Existing Customer");
        System.out.println("3. Exit");
        System.out.print("Choose option: ");

        choice = info.nextInt();
        info.nextLine();

        switch (choice) {
            case 1:
                newcustomer();
                break;
            case 2:
                existingcustomer();
                break;
            case 3:
                saveData();
                break;
            default:
                System.out.println("Invalid choice!");
        }


    }

    public  static int previousId(){
        File file = new File("Garagepro.txt");
        int previousId =0;
        String line;

        if(file.exists()){
            try(BufferedReader r = new BufferedReader(new FileReader(file))){
                while((line = r.readLine()) != null){
                    if(line.startsWith("Customer id: C")){
                        String[]  Line = line.split(" ");
                        if(Line[2].startsWith("C")){
                            int newid = Integer.parseInt(Line[2].substring(1));
                            if(newid > previousId){
                                previousId = newid;
                            }
                        }
                    }
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return previousId;
    }
    public void newcustomer(){
        Scanner info = new Scanner(System.in);

        System.out.print("Enter Name: ");
        setName(info.nextLine().toUpperCase());

        System.out.print("Enter Contact Number: ");
        setContactnum (info.nextLine().toUpperCase());

        System.out.print("Enter Vehicle Type: ");
        setVehicletype(info.nextLine().toUpperCase());

        System.out.print("Enter Plate Number: ");
        setPlate_no(info.nextLine().toUpperCase());

        System.out.print("Enter Company: ");
        setCompany(info.nextLine().toUpperCase());

        System.out.print("Enter Model: ");
        setModel(info.nextLine().toUpperCase());

        int lastId = previousId();
        id = "C" + (lastId + 1);
        customers.add(this);
        saveData();
    }

    public void existingcustomer() {
        File file = new File("Garagepro.txt");
        Scanner info = new Scanner(System.in);
        System.out.println("*** Existing Customer ***");
        System.out.print("Enter customer ID: ");
        String id = info.nextLine().toUpperCase();
        String line;

        if(file.exists()){
            try(BufferedReader r = new BufferedReader(new FileReader(file))) {
                while ((line = r.readLine()) != null) {

                    if (line.contains("Customer id: " + id)) {
                        System.out.println("\nCustomer found!");
                        System.out.println(line);
                    }

                }
            }catch (IOException e) {
                System.out.println("Customer not found with ID: " + id);
                throw new RuntimeException(e);
            }
        }
    }

    public void saveData() {


        File file = new File("Garagepro.txt");



        String garagepro = "Phone: " + this.getContactnum();

        boolean exists = false;
        try {
            // file.createNewFile();
            if (file.exists()) {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.contains(garagepro)) {
                        exists = true;
                        break;
                    }
                }
                reader.close();
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading the file.");
            e.printStackTrace();
        }

        if(!exists) {
            try {

                garagepro = "Customer id: "+ this.id + ", Name: " + this.getName() + ", Phone: " + this.getContactnum() + ", Vehicle Type: " + this.getVehicletype()+ ", Plate Number: " + this.getPlate_no() + ", Company: " + this.getCompany() + ", Model: " + this.getModel() ;
                FileWriter writer = new FileWriter("Garagepro.txt",true);
                writer.write(garagepro+ "\n");
                writer.close();

                System.out.println("Your Data is saved ");

                this.displayInfo();

            } catch (IOException e) {
                System.out.println("An error occurred while writing to the file.");
                throw new RuntimeException(e);
            }
        }else {
            System.out.println("thanks for visiting again,you get a discount!!");
        }

    }

    @Override
    void displayInfo() {
        System.out.println("Customer ID: " + id);
        System.out.println("Name: "+ getName());
        System.out.println("Contact Number: "+getContactnum());
        System.out.println("Vehicle Type: "+ getVehicletype());
        System.out.println("Company: "+ getCompany());
        System.out.println("No. Plate: "+ getPlate_no());
        System.out.println("Model: "+ getModel());
    }
}

class ServiceMenu{

    int option,choice = 0,bill = 0;
    List<String> selectedServices = new ArrayList<>();
    String selectedPackage = "";

    public void Package(String packages){
        switch (packages.toUpperCase()) {

            case "B":
                selectedPackage = "Basic Package - 7000 Rs";
                bill += 7000;
                break;
            case "S":
                selectedPackage = "Standard Package - 50000 Rs";
                bill += 50000;
                break;
            case "P":
                selectedPackage = "Premium Package - 100000 Rs";
                bill += 100000;
                break;
            case "R":
                break;
            default:
                System.out.println("Invalid package option.");
                break;
        }
    }

    public  void services(int choice) {
        switch (choice) {
            case 1:
                selectedServices.add("Engine Tuning - 2000 Rs");
                bill += 2000;
                break;
            case 2:
                selectedServices.add("Oil Change - 6000 Rs");
                bill += 6000;
                break;
            case 3:
                selectedServices.add("Full Service - 25000 Rs");
                bill += 25000;
                break;
            case 4:
                selectedServices.add("Body Repair and Paint - 70000 Rs");
                bill += 70000;
                break;
            case 5:
                selectedServices.add("Brake Inspection and Replacement - 5000 Rs");
                bill += 5000;
                break;
            case 6:
                selectedServices.add("AC Service and Regas - 3000 Rs");
                bill += 3000;
                break;
            case 7:
                selectedServices.add("Full Vehicle Health Check - 5000 Rs");
                bill += 5000;
                break;
            case 8:
                selectedServices.add("Transmission Service - 4000 Rs");
                bill += 4000;
                break;
            case 9:
                selectedServices.add("Fuel System Cleaning - 15000 Rs");
                bill += 15000;
                break;
            case 10:
                selectedServices.add("Overhaul Engine Repair - 40000 Rs");
                bill += 40000;
                break;
            case 11:
                selectedServices.add("Electric Diagnostic - 2500 Rs");
                bill += 2500;
                break;
            case 12:
                selectedServices.add("Car Detailing - 15000 Rs");
                bill += 15000;
                break;
            case 13:
                System.out.println("exiting...");
                break;
            default:
                System.out.println("Unknown Service,If you want to exit press 13.");
                break;
        }
    }

    public void displaybill() {
        System.out.println("\n--- Order Summary ---");
        if (!selectedPackage.isEmpty()) {
            System.out.println("Package: " + selectedPackage);
        } else {
            System.out.println("No Package Selected.");
        }

        if (!selectedServices.isEmpty()) {
            System.out.println("\nSelected Services:");
            for (String service : selectedServices) {
                System.out.println(" - " + service);
            }
        } else {
            System.out.println("\nNo Additional Services Selected.");
        }

        if (selectedServices.size() > 5 ){
            double discount = (bill*0.10);
            bill -= discount;
            System.out.println("\n10% Discount is applied\nTotal Bill: " + bill + " Rs");
            System.out.println("Thank you for visiting!");
        } else if (selectedServices.size() > 3) {
            double discount = (bill*0.05);
            bill -= discount;
            System.out.println("\n5% Discount is applied\nTotal Bill: " + bill + " Rs");
            System.out.println("Thank you for visiting!");
        }else{
            System.out.println("\nTotal Bill: " + bill + " Rs");
            System.out.println("Thank you for visiting!");}
    }

    public void displaymenu(){
        Scanner x = new Scanner(System.in);


        int option,choice = 0,bill = 0;
        String packages;
        do {
            System.out.print("1.Packages.\n" + "2.Services. \n" + "3.Exit." +"\nChoose a option:");
            option = x.nextInt();


            switch (option) {
                case 1:
                    System.out.println("           Enter the package you would like to purchase(B, S or P)");
                    System.out.println("  Basic: PKR 7,000         Standard: PKR 50,000         Premium: PKR 100,000");
                    System.out.println("  ✓Engine Tuning           ✓Full Service                ✓Overhaul Engine Repair");
                    System.out.println("  ✓AC Service and Regas    ✓Car Detailing               ✓Body Repair and Paint");
                    System.out.println("  ✓Electric Diagnostic     ✓Fuel System Cleaning        ");
                    System.out.println("If you don't want to select any Packages,Press 'R' to return to main menu.");
                    packages = x.next().toUpperCase();
                    Package(packages);
                    break;

                case 2:
                    System.out.println("***Garage Service Menu***");
                    System.out.println("1. Engine Tuning - Rs.2000");
                    System.out.println("2. Oil Change - Rs.6000 ");
                    System.out.println("3. Full Service - Rs.25000 ");
                    System.out.println("4. Body Repair and Paint - Rs.70000 ");
                    System.out.println("5. Brake Inspection and Replacement - Rs.5000 ");
                    System.out.println("6. AC Service and Regas - Rs.3000 ");
                    System.out.println("7. Full Vehicle Health Check - Rs.5000 ");
                    System.out.println("8. Transmission Service - Rs.4000 ");
                    System.out.println("9. Fuel System Cleaning - Rs.15000 ");
                    System.out.println("10. Overhaul Engine Repair - Rs.40000 ");
                    System.out.println("11. Electric Diagnostic - Rs.2500 ");
                    System.out.println("12. Car Detailing - Rs.15000 ");
                    System.out.println("13. Return to Main Menu");
                    System.out.print("Choose a service (1-12) or 13 to return(seperate services using ',': ");
                    Scanner input = new Scanner(System.in);
                    String line = input.nextLine();
                    String[] choices = line.split(",");

                    for (String c : choices) {
                        c = c.trim(); // Remove any spaces
                        try {
                            int serviceChoice = Integer.parseInt(c);
                            if (serviceChoice == 13) {
                                break;  // Exit the service selection
                            }
                            services(serviceChoice);
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input: " + c);
                        }
                    }
                    break;
                case 3:
                    displaybill();
                    break;
                default:
                    System.out.println("invalid option");
                    break;
            }
        } while (option !=3);
    }
}






public class GaragePro {
    public static void main(String[] args) {
        Scanner a = new Scanner(System.in);
        Car display = new Car("getd","getName()","getContactnum()","getVehicletype()","getPlate_no()","getCompany()","getModel()");
        display.info();

        ServiceMenu p2 = new ServiceMenu();
        p2.displaymenu();


    }
}