package utils;


import entities.Coach;
import entities.MemberInfo;
import entities.Role;
import entities.Sport;
import entities.SportTeam;
import entities.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class SetupTestUsers {

  public static void setUpUsers() {

    EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
    EntityManager em = emf.createEntityManager();
 
    User user = new User("user", "testuser", "@user", "415145415", 55);
    User admin = new User("admin", "testadmin", "@admin", "5555", 5);
    User both = new User("user_admin", "testuseradmin", "@both", "4568686865451", 4);
    Role userRole = new Role("user");
    Role adminRole = new Role("admin");

    MemberInfo memberInfo = new MemberInfo();
    Coach coach = new Coach("jens", "@jens", "55555");
    SportTeam sportTeam = new SportTeam(500, "aholdet", 15, 18);
    Sport sport = new Sport("Fodbold", "sport med en bold");
    
    user.addMemberInfo(memberInfo);
    memberInfo.setPayed();
    sportTeam.addMemberInfo(memberInfo);
    sportTeam.addCoach(coach);
    sportTeam.addSport(sport);
    

 
    user.addRole(userRole);
    admin.addRole(adminRole);
    both.addRole(userRole);
    both.addRole(adminRole);


    em.getTransaction().begin();
    em.persist(userRole);
    em.persist(adminRole);
    em.persist(user);
    em.persist(admin);
    em.persist(sportTeam);
    em.persist(sport);
    em.getTransaction().commit();
    System.out.println("PW: " + user.getUserPass());
    System.out.println("Testing user with OK password: " + user.verifyPassword("test"));
    System.out.println("Testing user with wrong password: " + user.verifyPassword("test1"));
    System.out.println("Created TEST Users");
   
  }

}
