/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author sumit
 */
@Entity
public class Sport implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private String name;
    private String description;
    
    @OneToMany(mappedBy = "sport")
    private List<SportTeam> sportsTeams;

    public Sport(String name, String description) {
        this.name = name;
        this.description = description;
        sportsTeams = new ArrayList();
    }

    public Sport() {
    }

    public List<SportTeam> getSportsTeams() {
        return sportsTeams;
    }

    public void addSportTeam(SportTeam sportTeam) {
        if(sportTeam != null){
            this.sportsTeams.add(sportTeam);
            sportTeam.addSport(this);
        }
    }

    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }



}
