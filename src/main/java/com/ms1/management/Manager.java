package com.ms1.management;

import com.ms1.hibernate.HibernateUtil;
import com.ms1.models.User;
import com.sun.jersey.spi.resource.Singleton;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.hibernate.Session;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;

@Singleton
public class Manager {
    private Session session = HibernateUtil.getSessionFactory().openSession();;
    //private UserDao um = new SerializedUserDAO();
    private String key_jwt="asdfbsdf3242";




    public User addUser(User user){

        User ureturn=null;
        try {
            ureturn=(User) session.get(User.class, user.getEmail());
        } catch (Exception e) {
            System.out.println("User is not saved");
        }

        if(ureturn!=null) return null;


        try {
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Could not save a user");
        }



        ureturn=(User) session.get(User.class, user.getEmail());
        return ureturn;

    }

    public User getUserByEmailAndPassword(String email, String password){

        User ureturn=null;
        try {
            ureturn=(User) session.get(User.class, email);
        } catch (Exception e) {
            System.out.println("User is not saved");
        }

        if(ureturn!=null){
            if(ureturn.getPassword().equalsIgnoreCase(password)) return ureturn;
        }

        return null;

    }

    public User getUserByEmail(String email){

        User ureturn=null;
        try {
            ureturn=(User) session.get(User.class, email);
        } catch (Exception e) {
            System.out.println("User is not saved");
        }


        return ureturn;
    }


    public void deleteUser(User duser){

        try {
            session.beginTransaction();
            session.delete(duser);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Could not delete a user");
        }

    }

    public void updateUser(User upuser){

        try {
            session.beginTransaction();
            session.update(upuser);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Could not update a user");
        }

    }

    public String createJWT(String email){
        long nowMillis = System.currentTimeMillis();
        JwtBuilder builder = Jwts.builder()
                .setIssuedAt(new Date(nowMillis))
                .setSubject(email)
                .setExpiration(new Date(nowMillis+86400000))
                .signWith(SignatureAlgorithm.HS256, key_jwt);
        String token= "Bearer " +  builder.compact();


        return token;
    }


    public boolean validateJWT(String token, User user){
        if(token==null) return false;
        String[] parts = token.split("\\s+");
        if (parts.length != 2) return false;
        if(!parts[0].equals("Bearer")) return false;
        try {
            String email=Jwts.parser().setSigningKey(key_jwt).parseClaimsJws(parts[1]).getBody().getSubject();
            if(!email.equals(user.getEmail())) return false;

        } catch (JwtException e) {
            System.out.println("Token is invalide!");
            return false;
        }

        return true;
    }

}
