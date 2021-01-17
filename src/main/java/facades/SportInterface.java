
package facades;

import java.util.List;
import sportsDTO.SportDTO;
import sportsDTO.SportTeamDTO;

public interface SportInterface {
    
    public abstract SportDTO addNewSport(SportDTO newSportDTO);
    public abstract List<SportDTO> seeAllSports();
    public abstract SportTeamDTO addSportTeam(SportTeamDTO sportTeamDTO) throws Exception;
    public abstract List<SportTeamDTO> seeAllSportTeams() throws Exception;
    public abstract SportTeamDTO deleteSportTeam(String teamName) throws Exception;
}
