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
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import sportsDTO.SportDTO;
import sportsDTO.SportTeamDTO;

//Uncomment the line below, to temporarily disable this test
//@Disabled
public class SportFacadeTest {

    private static EntityManagerFactory emf;
    private static EntityManager em;
    private static SportFacade facade;
    private static User user;
    private static User admin;
    private static User both;
    private static SportTeam sportTeam2, sportTeam1, sportTeam;
    private static Sport sport, sport2;
    private static Role userRole, adminRole;
    private static MemberInfo memberInfo;
    private static Coach coach;
    
    public SportFacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
    
    EMF_Creator.startREST_TestWithDB();
    emf = EMF_Creator.createEntityManagerFactoryForTest();
    em = emf.createEntityManager();
    facade = SportFacade.getSportFacade(emf);

    user = new User("user", "testuser", "@user", "415145415", 55);
    admin = new User("admin", "testadmin", "@admin", "5555", 5);
    both = new User("user_admin", "testuseradmin", "@both", "4568686865451", 4);
  
    memberInfo = new MemberInfo();
    coach = new Coach("jens", "@jens", "55555");
    sportTeam = new SportTeam(500, "u20", 15, 18);
    sport = new Sport("Fodbold", "sport med en bold");
    sport2 = new Sport("Håndbold", "mandesport");
    userRole = new Role("user");
    adminRole = new Role("admin");
    
    
    user.addMemberInfo(memberInfo);
    sportTeam.addMemberInfo(memberInfo);
    sportTeam.addCoach(coach);
    sportTeam.addSport(sport);
    user.addRole(userRole);
    admin.addRole(adminRole);
    both.addRole(userRole);
    both.addRole(adminRole);

   em.getTransaction().begin();
//   em.createNativeQuery("DELETE FROM MEMBERINFO").executeUpdate();
//   em.createNativeQuery("DELETE FROM SPORTTEAM").executeUpdate();
//   em.createNativeQuery("DELETE FROM SPORT").executeUpdate();
//   em.createNativeQuery("DELETE FROM COACH").executeUpdate();
//   em.createNativeQuery("DELETE FROM user_roles").executeUpdate();          
//   em.createNativeQuery("DELETE FROM roles").executeUpdate();      
//   em.createNativeQuery("DELETE FROM users").executeUpdate();
  
    em.persist(userRole);
    em.persist(adminRole);
    em.persist(user);
    em.persist(admin);
    em.persist(both);
    em.persist(sportTeam);
    em.getTransaction().commit();
    System.out.println("PW: " + user.getUserPass());
    System.out.println("Testing user with OK password: " + user.verifyPassword("test"));
    System.out.println("Testing user with wrong password: " + user.verifyPassword("test1"));
    System.out.println("Created TEST Users");

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
     
        SportDTO sDTO = new SportDTO(sport2);
        SportDTO newSport = facade.addNewSport(sDTO);
        String expected = "Håndbold";
        assertEquals(newSport.name, expected);
    }
    
    @Test 
    public void testGetAllSports(){
        List<SportDTO> sports = facade.seeAllSports();
        int expected = 2;
        assertEquals(sports.size(), expected);
    }
    
    @Test
    public void testDeleteSport() throws Exception{
        SportTeamDTO sportTeamDTO = facade.deleteSportTeam(sportTeam.getTeamName());
        String expected = "u20";
        assertEquals(expected, sportTeamDTO.teamName);
    }
    
    @Test
    public void testAddSportTeam() throws Exception{
        SportTeamDTO sportTeamDTO = facade.addSportTeam(new SportTeamDTO(sportTeam));
        String expected = "u20";
        assertEquals(expected, sportTeamDTO.teamName);
    }
    
    @Test   
    public void testGetAllSportTeams() throws Exception{
        List<SportTeamDTO> allSportTeams = facade.seeAllSportTeams();
        int expected = 0;
        assertEquals(0, allSportTeams.size());
    }
  
    
    


}
