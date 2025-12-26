package com.jobportal.model;

public class Job {
    private int jobId;
    private String title;
    private String companyName;
    private int requiredExperience;
    private String status; // OPEN or CLOSED
    
    public Job(int jobId, String title, String companyName, int requiredExperience) {
        this.jobId = jobId;
        this.title = title;
        this.companyName = companyName;
        this.requiredExperience = requiredExperience;
        this.status = "OPEN";
    }
    
    public int getJobId() {
        return jobId;
    }
    
    public String getTitle() {
        return title;
    }
    
    public String getCompanyName() {
        return companyName;
    }
    
    public int getRequiredExperience() {
        return requiredExperience;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Job job = (Job) obj;
        return jobId == job.jobId;
    }
    
    @Override
    public int hashCode() {
        return Integer.hashCode(jobId);
    }
    
    @Override
    public String toString() {
        return "Job ID: " + jobId + 
               " | Title: " + title + 
               " | Company: " + companyName + 
               " | Experience Required: " + requiredExperience + " years" +
               " | Status: " + status;
    }
}