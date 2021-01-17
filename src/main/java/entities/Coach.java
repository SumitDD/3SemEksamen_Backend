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
public class Coach implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String phone;
    
    @OneToMany(mappedBy = "coach")
    private List<SportTeam> sportsTeams;

    public Coach(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        sportsTeams = new ArrayList();
    }

    public Coach() {
    }

    
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<SportTeam> getSportsTeams() {
        return sportsTeams;
    }

    public void addSportTeam(SportTeam sportTeam) {
        if(sportTeam != null){
            this.sportsTeams.add(sportTeam);
            sportTeam.addCoach(this);
        }
    }
    

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    
}
