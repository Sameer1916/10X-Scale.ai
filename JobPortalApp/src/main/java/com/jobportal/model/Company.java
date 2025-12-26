package com.jobportal.model;

public class Company extends User {
    private String companyName;
    
    public Company(int id, String name, String companyName) {
        super(id, name);
        this.companyName = companyName;
    }
    
    public String getCompanyName() {
        return companyName;
    }
    
    @Override
    public void showMenu() {
        System.out.println("\n=== Company Menu ===");
        System.out.println("1. Post Job");
        System.out.println("2. Close Job");
        System.out.println("3. View My Jobs");
        System.out.println("4. Exit");
    }
}