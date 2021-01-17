
package facades;

import java.util.List;
import sportsDTO.SportDTO;
import sportsDTO.SportTeamDTO;

public interface SportInterface {
    
    public abstract SportDTO addNewSport(SportDTO newSportDTO);
    public abstract List<SportDTO> seeAllSports();
    public abstract SportTeamDTO addNewSportTeam();
    
}
