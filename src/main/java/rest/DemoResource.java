package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entities.Sport;
import entities.User;
import facades.FacadeExample;
import facades.SportFacade;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeoutException;
import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import sportsDTO.SportDTO;
import sportsDTO.SportTeamDTO;
import utils.EMF_Creator;
import utils.SetupTestUsers;

/**
 * @author lam@cphbusiness.dk
 */
@Path("sport")
public class DemoResource {
    
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final ExecutorService ES = Executors.newCachedThreadPool();
    private static final SportFacade FACADE = SportFacade.getSportFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static String cachedResponse;
    @Context
    private UriInfo context;

    @Context
    SecurityContext securityContext;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getInfoForAll() {
        return "{\"msg\":\"Hello anonymous\"}";
    }

    //Just to verify if the database is setup
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("all")
    public String allUsers() {

        EntityManager em = EMF.createEntityManager();
        try {
            TypedQuery<User> query = em.createQuery ("select u from User u",entities.User.class);
            List<User> users = query.getResultList();
            return "[" + users.size() + "]";
        } finally {
            em.close();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("user")
    @RolesAllowed("user")
    public String getFromUser() {
        String thisuser = securityContext.getUserPrincipal().getName();
        return "{\"msg\": \"Hello to User: " + thisuser + "\"}";
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("admin")
    @RolesAllowed("admin")
    public String getFromAdmin() {
        String thisuser = securityContext.getUserPrincipal().getName();
        return "{\"msg\": \"Hello to (admin) User: " + thisuser + "\"}";
    }
    

    @Path("setUpUsers")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public void setUpUsers() {
        SetupTestUsers.setUpUsers();
    }
    
    @Path("allsports")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String allSports() {
        List<SportDTO> allSports = FACADE.seeAllSports();
        return GSON.toJson(allSports);
    }
    
    @Path("addsport")
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    public String allSports(String sportName) {
        SportDTO sportDTO = GSON.fromJson(sportName, SportDTO.class);
        SportDTO sDTO = FACADE.addNewSport(sportDTO);
        return GSON.toJson(sDTO);
    }
    @Path("addsportteam")
    @PUT
    @Produces({MediaType.APPLICATION_JSON})
    public String addSportTeam(String sportTeamName) throws Exception {
        SportTeamDTO sportTeamDTO = GSON.fromJson(sportTeamName, SportTeamDTO.class);
        SportTeamDTO sDTO = FACADE.addSportTeam(sportTeamDTO);
        return GSON.toJson(sDTO);
    }
    @Path("allsportteams")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getAllSportTeams() throws Exception {
        
        List<SportTeamDTO> allSportTeams = FACADE.seeAllSportTeams();
        return GSON.toJson(allSportTeams);
    }
    
    
}