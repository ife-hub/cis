package org.vaadin.example;

import jakarta.persistence.*;
import java.util.Map;

@Entity
@Table(name = "actions")
public class ActionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    @Column(name = "action")
    private String action;

    // store JSON string

    @Column(name = "data", columnDefinition = "TEXT")
    private String data;

    // getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getAction() { return action; }
    public void setAction(String action) { this.action = action; }

    public String getData() { return data; }
    public void setData(String data) { this.data = data; }
}
