/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.smartcampus.exception;

/**
 *
 * @author Randil Henry
 */
public class LinkedResourceNotFoundException extends RuntimeException{
    
    private final String roomId;
    
    public LinkedResourceNotFoundException(String roomId) {
        super("Referenced roomId does not exist: " + roomId);
        this.roomId = roomId;
    }
    
    public String getRoomId() {
        return  roomId;
    }
}
