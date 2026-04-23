/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.smartcampus;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashMap;
import java.util.Map;
/**
 *
 * @author Randil Henry
 */
@ApplicationPath("/api/v1")
public class RestApplication extends Application{
    
    @Override
    public Map<String, Object> getProperties() {
        Map<String, Object> properties = new HashMap<>();
        // Disable Jersey's built-in exception mappers
        properties.put("jersey.config.server.disableAutoDiscovery", false);
        properties.put("jersey.config.server.disableMoxyJson", true);
        return properties;
    }
}
