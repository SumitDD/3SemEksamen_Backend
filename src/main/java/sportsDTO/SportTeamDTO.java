
package sportsDTO;

import entities.SportTeam;


public class SportTeamDTO {
    
    public int pricePerYear;
    public String teamName;
    public int minAge;
    public int maxAge;

    public SportTeamDTO(SportTeam sportTeam) {
        this.pricePerYear = sportTeam.getPricePerYear();
        this.teamName = sportTeam.getTeamName();
        this.minAge = sportTeam.getMinAge();
        this.maxAge = sportTeam.getMaxAge();
    }
    
    
    
    
}
