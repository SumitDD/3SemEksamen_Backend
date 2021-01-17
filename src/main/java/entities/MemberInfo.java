/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author sumit
 */
@Entity
public class MemberInfo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Boolean payed;
    @Temporal(TemporalType.DATE)
    private Date datePayed;
    
    @ManyToOne
    private Player player;
    
    @ManyToOne
    private SportTeam sportTeam;

    public MemberInfo() {
        this.payed = false;
        this.datePayed = null;
    }

//    public MemberInfo() {
//    }

    public Player getPlayer() {
        return player;
    }

    public void addPlayer(Player player) {
        if(player != null){
           player.getMemberInfos().add(this);
           this.player = player;  
        }
    }

    public SportTeam getSportTeam() {
        return sportTeam;
    }

    public void setSportTeam(SportTeam sportTeam) {
        this.sportTeam = sportTeam;
    }

    public Boolean getPayed() {
        return payed;
    }

    public void setPayed(Boolean payed) {
        if(!payed)
           this.payed = true;
           this.datePayed = new Date();
    }

    public Date getDatePayed() {
        return datePayed;
    }

    public void setDatePayed(Date datePayed) {
        this.datePayed = datePayed;
    }
    
    
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    
}
