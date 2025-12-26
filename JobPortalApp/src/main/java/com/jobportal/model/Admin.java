package com.jobportal.model;

public class Admin extends User {
    
    public Admin(int id, String name) {
        super(id, name);
    }
    
    @Override
    public void showMenu() {
        System.out.println("\n=== Admin Menu ===");
        System.out.println("1. View All Jobs");
        System.out.println("2. View Applicants per Job");
        System.out.println("3. Exit");
    }
}