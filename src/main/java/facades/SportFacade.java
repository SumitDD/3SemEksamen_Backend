package facades;

import entities.Sport;
import entities.SportTeam;
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
    public SportTeamDTO addSportTeam(SportTeamDTO sportTeamDTO) throws Exception {
        EntityManager em = emf.createEntityManager();
        System.out.println("22222");
        Sport sport = em.find(Sport.class, sportTeamDTO.sport);
        System.out.println("333333");
        if(sport == null){
            throw new Exception("Sporten findes ikke");
        }
        SportTeam sportTeam = new SportTeam(sportTeamDTO.pricePerYear, sportTeamDTO.teamName, sportTeamDTO.minAge, sportTeamDTO.maxAge);
        sport = em.find(Sport.class, sportTeamDTO.sport);
        sportTeam.addSport(sport);
        em.getTransaction().begin();
        em.merge(sportTeam);
        em.getTransaction().commit();
        
        return new SportTeamDTO(sportTeam);
    }

    @Override
    public List<SportTeamDTO> seeAllSportTeams() throws Exception {
        EntityManager em = emf.createEntityManager();
        List<SportTeamDTO> allSportTeamDTO = new ArrayList();
        //TypedQuery query = em.createQuery("Select u FROM User u JOIN u.phones p WHERE p.Number =:number", User.class);
        TypedQuery query = em.createQuery("SELECT s FROM SportTeam s", SportTeam.class);
        List<SportTeam> allSportTeams = query.getResultList();
        System.out.println(allSportTeams);
        for (SportTeam SportTeam : allSportTeams) {
            allSportTeamDTO.add(new SportTeamDTO(SportTeam));
            
        }
        return allSportTeamDTO;
        
    }

 
   

 



}
