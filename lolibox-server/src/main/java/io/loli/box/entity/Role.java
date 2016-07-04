package io.loli.box.entity;

public enum Role {
    ROLE_USER("ROLE_USER"), ADMIN("ROLE_ADMIN");

    private String name;


    Role(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}