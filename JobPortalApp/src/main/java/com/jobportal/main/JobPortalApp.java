package com.jobportal.main;

import com.jobportal.model.*;
import com.jobportal.service.JobService;
import java.util.Scanner;

public class JobPortalApp {
    private static JobService jobService = new JobService();
    private static Scanner sc = new Scanner(System.in);
    
    public static void main(String[] args) {
        while (true) {
            try {
                System.out.println("\n=== Job Portal System ===");
                System.out.println("1. Admin");
                System.out.println("2. Company");
                System.out.println("3. Candidate");
                System.out.println("4. Exit");
                System.out.print("Enter your choice: ");
                
                int choice = sc.nextInt();
                sc.nextLine(); // consume newline
                
                switch (choice) {
                    case 1:
                        handleAdmin();
                        break;
                    case 2:
                        handleCompany();
                        break;
                    case 3:
                        handleCandidate();
                        break;
                    case 4:
                        System.out.println("Thank you for using Job Portal System!");
                        sc.close();
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Invalid choice! Please try again.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                sc.nextLine(); // clear buffer
            }
        }
    }
    
    private static void handleAdmin() {
        System.out.print("Enter Admin ID: ");
        int adminId = sc.nextInt();
        sc.nextLine();
        
        System.out.print("Enter Admin Name: ");
        String adminName = sc.nextLine();
        
        Admin admin = new Admin(adminId, adminName);
        System.out.println("Welcome, Admin " + adminName + "!");
        
        boolean adminSession = true;
        while (adminSession) {
            try {
                admin.showMenu();
                System.out.print("Enter your choice: ");
                int choice = sc.nextInt();
                sc.nextLine();
                
                switch (choice) {
                    case 1:
                        jobService.viewJobs();
                        break;
                    case 2:
                        System.out.print("Enter Job ID to view applicants: ");
                        int jobId = sc.nextInt();
                        sc.nextLine();
                        jobService.viewApplicantsForJob(jobId);
                        break;
                    case 3:
                        adminSession = false;
                        System.out.println("Logging out from Admin panel...");
                        break;
                    default:
                        System.out.println("Invalid choice! Please try again.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                sc.nextLine();
            }
        }
    }
    
    private static void handleCompany() {
        System.out.print("Enter Company ID: ");
        int companyId = sc.nextInt();
        sc.nextLine();
        
        System.out.print("Enter Representative Name: ");
        String repName = sc.nextLine();
        
        System.out.print("Enter Company Name: ");
        String companyName = sc.nextLine();
        
        Company company = new Company(companyId, repName, companyName);
        System.out.println("Welcome, " + repName + " from " + companyName + "!");
        
        boolean companySession = true;
        while (companySession) {
            try {
                company.showMenu();
                System.out.print("Enter your choice: ");
                int choice = sc.nextInt();
                sc.nextLine();
                
                switch (choice) {
                    case 1:
                        postJob(company);
                        break;
                    case 2:
                        closeJob(company);
                        break;
                    case 3:
                        jobService.viewJobsByCompany(company.getCompanyName());
                        break;
                    case 4:
                        companySession = false;
                        System.out.println("Logging out from Company panel...");
                        break;
                    default:
                        System.out.println("Invalid choice! Please try again.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                sc.nextLine();
            }
        }
    }
    
    private static void postJob(Company company) {
        try {
            System.out.print("Enter Job ID: ");
            int jobId = sc.nextInt();
            sc.nextLine();
            
            System.out.print("Enter Job Title: ");
            String title = sc.nextLine();
            
            System.out.print("Enter Required Experience (years): ");
            int experience = sc.nextInt();
            sc.nextLine();
            
            jobService.addJob(jobId, title, company.getCompanyName(), experience);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            sc.nextLine();
        }
    }
    
    private static void closeJob(Company company) {
        try {
            System.out.print("Enter Job ID to close: ");
            int jobId = sc.nextInt();
            sc.nextLine();
            
            jobService.closeJob(jobId, company.getCompanyName());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            sc.nextLine();
        }
    }
    
    private static void handleCandidate() {
        System.out.print("Enter Candidate ID: ");
        int candidateId = sc.nextInt();
        sc.nextLine();
        
        System.out.print("Enter Candidate Name: ");
        String candidateName = sc.nextLine();
        
        System.out.print("Enter Your Experience (years): ");
        int experience = sc.nextInt();
        sc.nextLine();
        
        Candidate candidate = new Candidate(candidateId, candidateName, experience);
        System.out.println("Welcome, " + candidateName + "!");
        
        boolean candidateSession = true;
        while (candidateSession) {
            try {
                candidate.showMenu();
                System.out.print("Enter your choice: ");
                int choice = sc.nextInt();
                sc.nextLine();
                
                switch (choice) {
                    case 1:
                        jobService.viewJobs();
                        break;
                    case 2:
                        applyForJob(candidate);
                        break;
                    case 3:
                        jobService.viewCandidateApplications(candidate.getId());
                        break;
                    case 4:
                        candidateSession = false;
                        System.out.println("Logging out from Candidate panel...");
                        break;
                    default:
                        System.out.println("Invalid choice! Please try again.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                sc.nextLine();
            }
        }
    }
    
    private static void applyForJob(Candidate candidate) {
        try {
            System.out.print("Enter Job ID to apply: ");
            int jobId = sc.nextInt();
            sc.nextLine();
            
            jobService.applyForJob(jobId, candidate);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            sc.nextLine();
        }
    }
}