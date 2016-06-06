package io.loli.box;


import org.springframework.boot.orm.jpa.hibernate.SpringNamingStrategy;

/**
 * @author choco
 */
public class CustomNamingStrategy extends SpringNamingStrategy {
    public String columnName(String columnName) {
        return columnName;
    }
}
