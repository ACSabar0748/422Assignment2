/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pkg422assignment2;

/**
 *
 * @author alexa
 */
public class Pet {
    private String name;
    private int age;
        
    public Pet(String name, int age) {
        this.name = name;
        this.age = age;
    }
        
    public void setName(String name) {
        this.name = name;
    }
        
    public String getName() {
        return name;
    }
        
    public void setAge(int age) {
        this.age = age;
    }
        
    public int getAge() {
        return age;
    }
}
