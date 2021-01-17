package facades;

import entities.Sport;
import entities.User;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import security.errorhandling.AuthenticationException;
import sportsDTO.NewSportDTO;

/**
 * @author lam@cphbusiness.dk
 */
public class SportFacade {

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
    
    public NewSportDTO addNewSport(NewSportDTO newSportDTO){    
        EntityManager em = emf.createEntityManager();
        Sport sport = em.find(Sport.class, newSportDTO.name);
        
        if(sport == null){
            sport = new Sport(newSportDTO.name, newSportDTO.description);
        }
        em.getTransaction().begin();
        em.persist(sport);
        em.getTransaction().commit();
        
        return new NewSportDTO(sport);

        
        
    }



}
