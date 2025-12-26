package com.jobportal.model;

public class Candidate extends User {
    private int experience;
    
    public Candidate(int id, String name, int experience) {
        super(id, name);
        this.experience = experience;
    }
    
    public int getExperience() {
        return experience;
    }
    
    @Override
    public void showMenu() {
        System.out.println("\n=== Candidate Menu ===");
        System.out.println("1. View All Jobs");
        System.out.println("2. Apply for Job");
        System.out.println("3. View My Applications");
        System.out.println("4. Exit");
    }
}