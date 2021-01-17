package facades;

import entities.Sport;
import entities.User;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import security.errorhandling.AuthenticationException;
import sportsDTO.SportDTO;
import sportsDTO.SportTeamDTO;


public class SportFacade implements SportInterface {

    private static EntityManagerFactory emf;
    private static SportFacade instance;

    private SportFacade() {
    }

    /**
     *
     * @param _emf
     * @return the instance of this facade.
     */
    public static SportFacade getSportFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new SportFacade();
        }
        return instance;
    }

    public User getVeryfiedUser(String username, String password) throws AuthenticationException {
        EntityManager em = emf.createEntityManager();
        User user;
        try {
            user = em.find(User.class, username);
            if (user == null || !user.verifyPassword(password)) {
                throw new AuthenticationException("Invalid user name or password");
            }
        } finally {
            em.close();
        }
        return user;
    }
    
    @Override
    public SportDTO addNewSport(SportDTO newSportDTO){    
        EntityManager em = emf.createEntityManager();
        Sport sport = em.find(Sport.class, newSportDTO.name); 
        if(sport == null){
            sport = new Sport(newSportDTO.name, newSportDTO.description);
        }
        em.getTransaction().begin();
        em.persist(sport);
        em.getTransaction().commit();
        
        return new SportDTO(sport);

    }
    @Override
    public List<SportDTO> seeAllSports(){     
        EntityManager em = emf.createEntityManager();
        List<SportDTO> allSports = new ArrayList();
        TypedQuery query = em.createQuery("SELECT s FROM Sport s", Sport.class);
        List<Sport> sports = query.getResultList();
        for (Sport sport : sports) {
            allSports.add(new SportDTO(sport));
            
        }
        
        return allSports;
        
    }



    @Override
    public SportTeamDTO addSportTeam(SportTeamDTO sportTeamDTO) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

 



}
