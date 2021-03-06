package rest;

import entities.Coach;
import entities.MemberInfo;

import entities.Role;
import entities.Sport;
import entities.SportTeam;
import entities.User;
import utils.EMF_Creator;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import io.restassured.parsing.Parser;
import java.net.URI;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.UriBuilder;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sportsDTO.SportDTO;
import sportsDTO.SportTeamDTO;
//Uncomment the line below, to temporarily disable this test


public class SportResourceTest {

    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";


    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;
    private static EntityManagerFactory emf;
    private static EntityManager em;
    private static Sport sport, sport2, newSport;
    private static SportTeam sportTeam1, sportTeam;
    static HttpServer startServer() {
        ResourceConfig rc = ResourceConfig.forApplication(new ApplicationConfig());
        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
    }

    @BeforeAll
    public static void setUpClass() {
      EMF_Creator.startREST_TestWithDB();
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        em = emf.createEntityManager();

        httpServer = startServer();
        //Setup RestAssured
        RestAssured.baseURI = SERVER_URL;
        RestAssured.port = SERVER_PORT;
        RestAssured.defaultParser = Parser.JSON;
    
    }

    @AfterAll
    public static void closeTestServer() {
        //System.in.read();

        //Don't forget this, if you called its counterpart in @BeforeAll
        EMF_Creator.endREST_TestWithDB();
        httpServer.shutdownNow();
    }

    // Setup the DataBase (used by the test-server and this test) in a known state BEFORE EACH TEST
    //TODO -- Make sure to change the EntityClass used below to use YOUR OWN (renamed) Entity class
    @BeforeEach
    public void setUp() {
     //This method must be called before you request the EntityManagerFactory
         EMF_Creator.startREST_TestWithDB();
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        em = emf.createEntityManager();

 
    User user = new User("user", "testuser", "@user", "415145415", 55);
    User admin = new User("admin", "testadmin", "@admin", "5555", 5);
    User both = new User("user_admin", "testuseradmin", "@both", "4568686865451", 4);
  

    MemberInfo memberInfo = new MemberInfo();
    Coach coach = new Coach("jens", "@jens", "55555");
    sportTeam = new SportTeam(500, "u20", 15, 18);
    sport = new Sport("Fodbold", "sport med en bold");
    sport2 = new Sport("Håndbold", "mandesport");
   
    
    
    // IMPORTAAAAAAAAAANT!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    // This breaks one of the MOST fundamental security rules in that it ships with default users and passwords
    // CHANGE the three passwords below, before you uncomment and execute the code below
    // Also, either delete this file, when users are created or rename and add to .gitignore
    // Whatever you do DO NOT COMMIT and PUSH with the real passwords

    em.getTransaction().begin();
    em.createNativeQuery("DELETE FROM MEMBERINFO").executeUpdate();
    em.createNativeQuery("DELETE FROM SPORTTEAM").executeUpdate();
     em.createNativeQuery("DELETE FROM SPORT").executeUpdate();
    em.createNativeQuery("DELETE FROM COACH").executeUpdate();

            em.createNativeQuery("DELETE FROM user_roles").executeUpdate();
            em.createNativeQuery("DELETE FROM roles").executeUpdate();
            em.createNativeQuery("DELETE FROM users").executeUpdate();
             user.addMemberInfo(memberInfo);
    sportTeam.addMemberInfo(memberInfo);
    sportTeam.addCoach(coach);
    sportTeam.addSport(sport);
    Role userRole = new Role("user");
    Role adminRole = new Role("admin");
    user.addRole(userRole);
    admin.addRole(adminRole);
    both.addRole(userRole);
    both.addRole(adminRole);
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

    @Test
    public void testServerIsUp() {
        given().when().get("/sport").then().statusCode(200);
    }

    //This test assumes the database contains two rows
    @Test
    public void testDummyMsg() throws Exception {
        given()
                .contentType("application/json")
                .get("/sport/").then()
                .assertThat()
                
                .body("msg", equalTo("Hello anonymous"));
    }

    @Test
    public void testGetAllSports() throws Exception {
            SportDTO s1 = new SportDTO(sport);
            SportDTO s2 = new SportDTO(newSport);
        given()
                .contentType("application/json").when()
                .get("/sport/allsports").then()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("name", hasItems(s1.name));
      
    }
    @Test
    public void testAddNewSport(){
        sportTeam1 = new SportTeam(400, "u19", 16, 19);
        EntityManagerFactory emf = EMF_Creator.createEntityManagerFactoryForTest();
        sportTeam1.addSport(sport);
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(sportTeam1);
        em.getTransaction().commit();
     
            given()
                .contentType("application/json")
                .body(new SportTeamDTO(sportTeam1))
                .when()
                .post("/sport/addsportteam/")
                .then();
        
    }
    @Test
    public void testAddSportTeam(){
        newSport = new Sport("gaming", "det sjovt");
        EntityManagerFactory emf = EMF_Creator.createEntityManagerFactoryForTest();
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(newSport);
        em.getTransaction().commit();
     
            given()
                .contentType("application/json")
                .body(new SportDTO(newSport))
                .when()
                .post("/sport/addsport/")
                .then();
    }
    
    @Test
    public void testGetAllSportTeams(){
            SportTeamDTO sDTO = new SportTeamDTO(sportTeam);
                given()
                .contentType("application/json").when()
                .get("/sport/allsportteams").then()      
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("pricePerYear", hasItems(sDTO.pricePerYear));

    }
    @Test
    public void testDeleteSportTeam() {
            given()
                .contentType("application/json")             
                .when()
                .delete("/sport/deletesportteam/" + sportTeam.getTeamName())
                .then()
                .statusCode(HttpStatus.OK_200.getStatusCode());
                
    }
       
        
    

}
