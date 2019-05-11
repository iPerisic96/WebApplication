package org.theplu.controllers;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.theplu.dao.*;
import org.theplu.entities.*;
import org.theplu.services.IServiceUser;
import org.theplu.services.ServiceUser;
import org.theplu.entities.*;

@Stateless
@LocalBean
@Path("/user")
public class ControllerUser {
	public ControllerUser(){
		this.service = new ServiceUser(new DAOUser());
	}
	
    @GET
    @Produces("text/json")
	public List<User> getAll(){
		return this.service.getAll();
	}
    
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    @Path("/register")
    public Boolean add(User user){
    	return this.service.register(user);
    }
    
    @GET
   	@Path("/activate/{name}")
    @Produces("text/json")
    public boolean activateUser(@PathParam("name") String name) {
    	boolean flag = this.service.activateUser(name);
       	URI uri = null;
   		try {
   			uri = new URI("http://localhost:8080/Domaci7/");
   		} catch (URISyntaxException e) {
   			// TODO Auto-generated catch block
   			e.printStackTrace();
   		}
       	Response.temporaryRedirect(uri);
       	return flag;
       }
    
    @POST
    @Produces("application/json")
    @Consumes("application/json")
    @Path("/login")
    public Boolean login(User user){
    	System.out.println(user.getUser_type());
        return this.service.login(user);
    }
    
    @GET
    @Produces("application/json")
    @Path("/comments")
    public List<Comment> getAllComments(){
    	return this.service.getAllComments();
    }
    
    @GET
    @Produces("application/json")
    @Path("/comments2/{username}")
    public List<Comment> getCommentsBySpecificUser(@PathParam("username") String username){
    	System.out.println("USA'");
    	return this.service.getCommentsBySpecificUser(username);
    }
    
    @POST
    @Produces("application/json")
    @Consumes("application/json")
    @Path("/comments/add")
    public Boolean addComment(Comment comment){
    	return this.service.addComment(comment);
    }
    
    @GET
    @Produces("application/json")
    @Path("/photos/day/{id}")
    public Integer photoNrInDays(@PathParam("id") Integer id) {
    	return service.photoNrInDays(id, 1);
    }
    
    @GET
    @Produces("application/json")
    @Path("/photos/week/{id}")
    public Integer photoNrInWeek(@PathParam("id") Integer id) {
    	return service.photoNrInDays(id, 7);
    }
    
    @POST
    @Produces("application/json")
    @Consumes("application/json")
    @Path("/photos/rate")
    public Boolean ratePhoto(Rate rate) {
    	return this.service.ratePicture(rate);
    }
    
    @GET
    @Produces("application/json")
    @Path("/get/{username}")
    public User getUserByName(@PathParam("username") String username) {
    	return this.service.getUserByName(username);
    	
    }
    
    @GET
    @Produces("application/json")
    @Path("/getu/{username}")
    public List<User> getUsersByName(@PathParam("username") String username){
    	return this.service.getUsersByName(username);
    }
    
    @GET
    @Produces("application/json")
    @Path("/ban/{username}")
    public Boolean ban(@PathParam("username") String username) {
    	return this.service.ban(username);
    }
    
	// property
	private IServiceUser service;
}

