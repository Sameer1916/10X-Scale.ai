package com.jobportal.service;

import com.jobportal.model.*;
import java.util.*;

public class JobService {
    
    private List<Job> jobs = new ArrayList<>();
    private Map<Integer, List<Candidate>> jobApplications = new HashMap<>();
    private Map<Integer, List<Job>> candidateApplications = new HashMap<>();
    
    // Add a new job
    public void addJob(int jobId, String title, String companyName, int requiredExperience) {
        Job job = new Job(jobId, title, companyName, requiredExperience);
        jobs.add(job);
        System.out.println("Job posted successfully: " + job);
    }
    
    // View all jobs
    public void viewJobs() {
        if (jobs.isEmpty()) {
            System.out.println("No jobs available.");
            return;
        }
        System.out.println("\n=== All Jobs ===");
        for (Job job : jobs) {
            System.out.println(job);
        }
    }
    
    // View jobs posted by a specific company
    public void viewJobsByCompany(String companyName) {
        boolean found = false;
        System.out.println("\n=== Jobs for Company: " + companyName + " ===");
        for (Job job : jobs) {
            if (job.getCompanyName().equalsIgnoreCase(companyName)) {
                System.out.println(job);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No jobs found for this company.");
        }
    }
    
    // Close a job
    public void closeJob(int jobId, String companyName) {
        for (Job job : jobs) {
            if (job.getJobId() == jobId && job.getCompanyName().equalsIgnoreCase(companyName)) {
                job.setStatus("CLOSED");
                System.out.println("Job closed successfully: " + job);
                return;
            }
        }
        System.out.println("Job not found or does not belong to your company.");
    }
    
    // Candidate applies for a job
    public void applyForJob(int jobId, Candidate candidate) {
        Job jobToApply = null;
        for (Job job : jobs) {
            if (job.getJobId() == jobId) {
                jobToApply = job;
                break;
            }
        }
        if (jobToApply == null) {
            System.out.println("Job not found.");
            return;
        }
        if (jobToApply.getStatus().equalsIgnoreCase("CLOSED")) {
            System.out.println("Cannot apply. Job is closed.");
            return;
        }
        // Check candidate experience
        if (candidate.getExperience() < jobToApply.getRequiredExperience()) {
            System.out.println("Cannot apply. Candidate does not meet experience requirements.");
            return;
        }
        
        // Add to jobApplications
        jobApplications.computeIfAbsent(jobId, k -> new ArrayList<>()).add(candidate);
        
        // Add to candidateApplications
        candidateApplications.computeIfAbsent(candidate.getId(), k -> new ArrayList<>()).add(jobToApply);
        
        System.out.println(candidate.getName() + " applied successfully for job: " + jobToApply.getTitle());
    }
    
    // View applicants for a specific job
    public void viewApplicantsForJob(int jobId) {
        List<Candidate> applicants = jobApplications.get(jobId);
        if (applicants == null || applicants.isEmpty()) {
            System.out.println("No applicants for this job.");
            return;
        }
        System.out.println("\n=== Applicants for Job ID: " + jobId + " ===");
        for (Candidate c : applicants) {
            System.out.println("ID: " + c.getId() + " | Name: " + c.getName() + " | Experience: " + c.getExperience() + " years");
        }
    }
    
    // View jobs applied by a candidate
    public void viewCandidateApplications(int candidateId) {
        List<Job> appliedJobs = candidateApplications.get(candidateId);
        if (appliedJobs == null || appliedJobs.isEmpty()) {
            System.out.println("You have not applied for any jobs yet.");
            return;
        }
        System.out.println("\n=== Jobs Applied by Candidate ID: " + candidateId + " ===");
        for (Job job : appliedJobs) {
            System.out.println(job);
        }
    }
}
