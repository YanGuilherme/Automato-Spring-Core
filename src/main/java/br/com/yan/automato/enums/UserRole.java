package br.com.yan.automato.enums;

public enum UserRole {
    ADMIN("admin"),
    USER("admin");

    private final String role;

    UserRole(String role){
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
