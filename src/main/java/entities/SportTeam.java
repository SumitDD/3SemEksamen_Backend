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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 *
 * @author sumit
 */
@Entity
public class SportTeam implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int pricePerYear;
    private String teamName;
    private int minAge;
    private int maxAge;
    
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Sport sport;
    
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Coach coach;
    
    @OneToMany(mappedBy = "sportTeam", cascade = CascadeType.PERSIST)
    private List<MemberInfo> memberInfos;

    public SportTeam(int pricePerYear, String teamName, int minAge, int maxAge) {
        this.pricePerYear = pricePerYear;
        this.teamName = teamName;
        this.minAge = minAge;
        this.maxAge = maxAge;
        memberInfos = new ArrayList();
    }

    public SportTeam() {
    }

    public Coach getCoach() {
        return coach;
    }

    public void addCoach(Coach coach) {
        if(coach != null){
            coach.getSportsTeams().add(this);
            this.coach = coach;
                    
        }
    }

    public Sport getSport() {
        return sport;
    }

    public void addSport(Sport sport) {
        if(sport != null){
           sport.getSportsTeams().add(this);
           this.sport = sport;
        }
    }
    

    public List<MemberInfo> getMemberInfos() {
        return memberInfos;
    }

    public void addMemberInfo(MemberInfo memberInfo) {
        this.memberInfos.add(memberInfo);
        if(memberInfo != null){
            memberInfo.setSportTeam(this);
        }
    }

    
    
    
    public int getPricePerYear() {
        return pricePerYear;
    }

    public void setPricePerYear(int pricePerYear) {
        this.pricePerYear = pricePerYear;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public int getMinAge() {
        return minAge;
    }

    public void setMinAge(int minAge) {
        this.minAge = minAge;
    }

    public int getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(int maxAge) {
        this.maxAge = maxAge;
    }
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    
}
