/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package pkg422assignment2;

/**
 *
 * @author alexa
 */
//Imports for the entire program
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Scanner;

public class Main {
    
    //Objects/variables that are used throughout the 
    public static ArrayList<Pet> petList = new ArrayList<>();
    static Scanner scan = new Scanner(System.in);
    public static Properties property = new Properties();
    
    public static void main(String[] args) throws IOException  {
        // TODO code application logic here
        boolean validator = true;
        
        try {
            InputStream input = new FileInputStream("database.properties");
            property.load(input);
            int id = 0;
            while(property.getProperty(String.valueOf(id)) != null) {
                String dataSplit[] = property.getProperty(String.valueOf(id)).split(":");
                int age = Integer.parseInt(dataSplit[1]);
                Pet newPet = new Pet(dataSplit[0], age);
                petList.add(newPet);
            }
        }
        catch (FileNotFoundException e) {
            System.out.println("Error: There was an issue loading the database, try again!");
        }
        
        System.out.println("Pet Database Program");
        do {
          menuOptions();  
        }
        while(validator);
    }
    
    public static void menuOptions() throws FileNotFoundException {       
        
        //This code is the print showing the options for the program
        System.out.println("What would you like to do?");
        
        System.out.println("1) View all pets");
        System.out.println("2) Add more pets");
        System.out.println("3) Remove an existing pet");
        System.out.println("4) Exit program");
        System.out.println("Your Choice: ");  
        
        //This is the input code, input 1-4 and depending on the input, a method is initialized
        String menuInput = scan.nextLine();
        boolean intCheck = isInt(menuInput);
        if (intCheck) {
            int menu = Integer.parseInt(menuInput);
            switch (menu) {
                case 1:
                    viewPets();
                    break;
                case 2:
                    addPet();
                    break;
                case 3: 
                    removePet();
                    break;
                case 4:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Error: Please type a number between 1 and 4!");
                    break;
            }
        }
        else {
            System.out.println("Error: Type an Integer as your choice!");
        }
    }
   
    
    //This method displays all of the pets in the database
    public static void viewPets() throws FileNotFoundException {
        
        //This is the display for the Header in the list
        System.out.println("+---------------------+");
        System.out.printf("|%-3s", " ID ");
        System.out.printf("|%-10s", " NAME ");
        System.out.printf("|%-4s", " AGE ");
        System.out.print("|\n");
        System.out.println("+---------------------+");
        
        //This is the code for the individual
        for (int i = 0; i < petList.size(); i++) {
            System.out.printf("|%3d" , i);
            System.out.print(" | ");
            System.out.printf("%-9s", petList.get(i).getName());
            System.out.printf("|%4d", petList.get(i).getAge());
            System.out.print(" |\n");
        }
        System.out.println("+---------------------+");
        System.out.println(petList.size() + " rows in set.\n");
        menuOptions();
    }
    
    //This method adds a pet to the database
    public static void addPet() throws FileNotFoundException {       
        int i = 0;
        
       //While true the user can add a pet's name and age until they type "done"
        while(true) {
            
            //This code will only add five pets to the database
            if (petList.size() < 5) {
                System.out.print("Add pet (name, age): ");
                Scanner data = new Scanner(System.in);
                String petData = data.nextLine();
                
                //If the user types done, no more pets can be added
                if (petData.toLowerCase().equals("done")) {
                    if(i == 1) {
                        System.out.println(i + " pet added.");
                    }
                    else {
                        System.out.println(i + " petss added.");
                    }
                    break;
                }                
                else {
                    String[] splitData = petData.split(" ");
                    if (splitData.length == 2) {
                        Boolean intCheck = isInt(splitData[1]);
                        if (intCheck) {
                            int age = Integer.parseInt(splitData[1]);
                            
                            //The age of the pets can only be between 1 and 20
                            if (age < 21 && age > 0) {
                                Pet newPet = new Pet(splitData[0], age);
                                petList.add(newPet);
                                i++;
                                try {
                                    OutputStream output = new FileOutputStream("database.properties");
                                    for(int j = 0; j < petList.size(); j++) {
                                        property.setProperty(String.valueOf(j), petList.get(j).getName() + ":" + petList.get(j).getAge());
                                    }
                                    property.store(output, null);
                                }
                                catch(IOException e) {
                                    System.out.println("Error: Issue occured while saving your pet!");
                                }
                            }
                            else {
                                System.out.println("Error: Pet age MUST be between 1 and 20!");
                            }  
                        }
                        else {
                            System.out.println("Error: Enter an integer as an age!");
                        }
                    }
                    else {
                        System.out.println("Error: Enter a one word name and an age seperated by a space!");
                    }
                }
            }
            else {
                System.out.println("Error: Only write up to five pets!");
            }
        }
        menuOptions();
    }
    
    //This method removes a pet from the database
    public static void removePet() throws FileNotFoundException {
        viewPets();
        while(true) {
            System.out.println("Enter a pet ID to remove: ");
            String inputId = scan.nextLine();
            boolean idCheck = isInt(inputId);
            if (idCheck) {
                int id = Integer.parseInt(inputId);
                
                //The ID input must be lower than the size of the database size
                if (id < petList.size()) {
                    try {
                        OutputStream output = new FileOutputStream("database.properties");
                        property.clear();
                        for (int i = 0; i < petList.size(); i++) {
                            if (1 != id) {
                                property.setProperty(String.valueOf(id), petList.get(i).getName() + ":" + petList.get(i).getAge());
                            }
                        }
                        property.store(output, null);                       
                    }
                    catch(IOException e) {
                        System.out.println("Error: Issue occured while removing your pet!");
                    }
                    System.out.println(petList.get(id).getName() + " has been removed from the list");
                    petList.remove(id);
                }
                else {
                    System.out.println("Error: Enter a valid ID!");
                }
                menuOptions();
            }
            else {
                System.out.println("Error: Enter the ID as a integer!");
            }
        }      
    }
    
    //This method makes sure that an input is a integer and not any other type of value
    public static boolean isInt(String inputs) {
        try {
            Integer.parseInt(inputs);
            return true;
        }
        catch(NumberFormatException e) {
            return false;
        }
    }
}
