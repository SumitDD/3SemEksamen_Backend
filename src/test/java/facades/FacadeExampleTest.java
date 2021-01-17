package facades;

import entities.Coach;
import entities.MemberInfo;
import utils.EMF_Creator;
import entities.Role;
import entities.Sport;
import entities.SportTeam;
import entities.User;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import security.errorhandling.AuthenticationException;
import sportsDTO.SportDTO;

//Uncomment the line below, to temporarily disable this test
//@Disabled
public class FacadeExampleTest {

    private static EntityManagerFactory emf;
    private static EntityManager em;
    private static SportFacade facade;
    private static User user;
    private static User admin;
    private static User both;

    public FacadeExampleTest() {
    }

    @BeforeAll
    public static void setUpClass() {
        
    EMF_Creator.startREST_TestWithDB();
    emf = EMF_Creator.createEntityManagerFactoryForTest();
    em = emf.createEntityManager();
    facade = SportFacade.getSportFacade(emf);
    
    User user = new User("user", "testuser", "@user", "415145415", 55);
    User admin = new User("admin", "testadmin", "@admin", "5555", 5);
    User both = new User("user_admin", "testuseradmin", "@both", "4568686865451", 4);
    

    MemberInfo memberInfo = new MemberInfo();
    Coach coach = new Coach("jens", "@jens", "55555");
    SportTeam sportTeam = new SportTeam(500, "aholdet", 15, 18);
    Sport sport = new Sport("Fodbold", "sport med en bold");
    
    user.addMemberInfo(memberInfo);
    sportTeam.addMemberInfo(memberInfo);
    coach.addSportTeam(sportTeam);
    sport.addSportTeam(sportTeam);
    
    // IMPORTAAAAAAAAAANT!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    // This breaks one of the MOST fundamental security rules in that it ships with default users and passwords
    // CHANGE the three passwords below, before you uncomment and execute the code below
    // Also, either delete this file, when users are created or rename and add to .gitignore
    // Whatever you do DO NOT COMMIT and PUSH with the real passwords



    if(admin.getUserPass().equals("test")||user.getUserPass().equals("test")||both.getUserPass().equals("test"))
      throw new UnsupportedOperationException("You have not changed the passwords");

    em.getTransaction().begin();
    Role userRole = new Role("user");
    Role adminRole = new Role("admin");
    user.addRole(userRole);
    admin.addRole(adminRole);
    both.addRole(userRole);
    both.addRole(adminRole);
    em.persist(both);
    em.persist(admin);
    em.persist(user);
    em.persist(userRole);
    em.persist(adminRole);
    em.persist(user);
    em.persist(admin);
    em.persist(both);
    em.persist(sport);

    em.persist(sportTeam);
    em.persist(coach);
    em.getTransaction().commit();
    }

    @AfterAll
    public static void tearDownClass() {
//        Clean up database after test is done or use a persistence unit with drop-and-create to start up clean on every test
    }

    // Setup the DataBase in a known state BEFORE EACH TEST
    //TODO -- Make sure to change the code below to use YOUR OWN entity class
    @BeforeEach
    public void setUp() {

    }
    @AfterEach
    public void tearDown() {
//        Remove any data after each test was run
    }

    // TODO: Delete or change this method 
//    @Test
//    public void testVerifyUser() throws AuthenticationException {
//        User user = facade.getVeryfiedUser("admin", "testadmin");
//        assertEquals("admin", admin.getUserName());
//    }
    

    @Test
    public void testAddNewSport(){
        Sport sport = new Sport("VOlley", "volley");
        SportDTO sDTO = new SportDTO(sport);
        SportDTO newSport = facade.addNewSport(sDTO);
        assertEquals(newSport.name, "VOlley");
    }
        @Test
    public void testGetAllSports(){
        List<SportDTO> sports = facade.seeAllSports();
        assertEquals(sports.size(), 2);
    }
    


}
