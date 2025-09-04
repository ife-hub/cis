package org.vaadin.example;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Map;

public class ActionRequest {
    @NotBlank(message = "Email is required")
    private String email;

    @NotNull(message = "Data is required")
    private Map<String, String> data;

    @NotBlank(message = "Action is required")
    private String action;

    // Getters and setters
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Map<String, String> getData() { return data; }
    public void setData(Map<String, String> data) { this.data = data; }

    public String getAction() { return action; }
    public void setAction(String action) { this.action = action; }
}
