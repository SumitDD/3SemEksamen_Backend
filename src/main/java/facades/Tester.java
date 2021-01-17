/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import entities.Coach;
import entities.MemberInfo;
import entities.Player;
import entities.Role;
import entities.Sport;
import entities.SportTeam;
import entities.User;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import sportsDTO.NewSportDTO;
import utils.EMF_Creator;


public class Tester {
    
    public static void main(String[] args) {
          EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
    EntityManager em = emf.createEntityManager();
    
    SportFacade sf = SportFacade.getSportFacade(emf);
    
    Player player = new Player("sumit", "@sumit", "11111", 21);
    boolean payed = true;
    MemberInfo memberInfo = new MemberInfo();
    System.out.println(memberInfo.getPayed() +  ", " + memberInfo.getDatePayed());
    Coach coach = new Coach("jens", "@jens", "55555");
    SportTeam sportTeam = new SportTeam(500, "aholdet", 15, 18);
    Sport sport = new Sport("Fodbold", "sport med en bold");
    
    player.addMemberInfo(memberInfo);
    System.out.println(memberInfo);
    sportTeam.addMemberInfo(memberInfo);
    coach.addSportTeam(sportTeam);
    sport.addSportTeam(sportTeam);
    
    Sport sport2 = new Sport("basket", "MJ");
    
    NewSportDTO ns = sf.addNewSport(new NewSportDTO(sport2));
    
    
    // IMPORTAAAAAAAAAANT!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    // This breaks one of the MOST fundamental security rules in that it ships with default users and passwords
    // CHANGE the three passwords below, before you uncomment and execute the code below
    // Also, either delete this file, when users are created or rename and add to .gitignore
    // Whatever you do DO NOT COMMIT and PUSH with the real passwords

    User user = new User("user", "testuser");
    User admin = new User("admin", "testadmin");
    User both = new User("user_admin", "testuseradmin");

    if(admin.getUserPass().equals("test")||user.getUserPass().equals("test")||both.getUserPass().equals("test"))
      throw new UnsupportedOperationException("You have not changed the passwords");

    em.getTransaction().begin();
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
    em.persist(sport);
    em.persist(player);
    em.persist(sportTeam);
    em.persist(coach);
    em.getTransaction().commit();
    System.out.println("PW: " + user.getUserPass());
    System.out.println("Testing user with OK password: " + user.verifyPassword("test"));
    System.out.println("Testing user with wrong password: " + user.verifyPassword("test1"));
    System.out.println("Created TEST Users");
    }
    
}
