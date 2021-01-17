/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sportsDTO;

import entities.Sport;


public class NewSportDTO {
    
    public String name;
    public String description;

    public NewSportDTO(Sport sport) {
        this.name = sport.getName();
        this.description = sport.getDescription();
    }
    
    
    
}
