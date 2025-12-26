package com.jobportal.model;

public abstract class User {
    protected int id;
    protected String name;
    
    public User(int id, String name) {
        this.id = id;
        this.name = name;
    }
    
    public abstract void showMenu();
    
    public int getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        User user = (User) obj;
        return id == user.id;
    }
    
    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}