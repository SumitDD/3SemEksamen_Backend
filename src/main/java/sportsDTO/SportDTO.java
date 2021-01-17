/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sportsDTO;

import entities.Sport;


public class SportDTO {
    
    public String name;
    public String description;

    public SportDTO(Sport sport) {
        this.name = sport.getName();
        this.description = sport.getDescription();
    }
    
    
    
}
