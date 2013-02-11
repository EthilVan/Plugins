package fr.ethilvan.bukkit.impl.permissions;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import fr.ethilvan.bukkit.api.permissions.Permission;

@Entity
@Table(name = "permissions")
public class EVPermission implements Permission {

    @Id
    private int id;
    @Column(name = "world")
    private String dimension;
    private String role;
    private String node;
    private boolean inheritable;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDimension() {
        return dimension;
    }

    public void setDimension(String dimension) {
        this.dimension = dimension;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getNode() {
        return node;
    }

    public void setNode(String node) {
        this.node = node;
    }

    public boolean isInheritable() {
        return inheritable;
    }

    public void setInheritable(boolean inheritable) {
        this.inheritable = inheritable;
    }
}
