package org.theplu.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.theplu.dao.DAOPhoto;
import org.theplu.dao.DAOUser;
import org.theplu.entities.Buying;
import org.theplu.entities.Category;
import org.theplu.entities.Filter;
import org.theplu.entities.Photo;
import org.theplu.entities.User;
import org.theplu.entities.User2;
import org.theplu.services.IServicePhoto;
import org.theplu.services.IServiceUser;
import org.theplu.services.ServicePhoto;
import org.theplu.services.ServiceUser;

@Stateless
@LocalBean
@Path("/photo")
public class ControllerPhoto {

	public ControllerPhoto(){
		this.service = new ServicePhoto(new DAOPhoto(Photo.class));
	}
	
    @GET
    @Produces("text/json")
	public List<Photo> getAll(){
    	List<Photo> photos = this.service.getAll();
    	System.out.println("Getting all pictures CONTROLLER PHOTO");
    	for(Photo p: photos) {
    		p.setPath(encoder(p.getPath(), p.getExtension()));
    		System.out.println("PATH: " + p.getPath() + " pic: " + p);
    	}
		return photos;
	}
    
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    @Path("/add")
	public Boolean addPhoto(Photo photo){
    	String base64 = photo.getPath();
    	System.out.println(base64);
    	try {
			photo.setPath(decoder(base64, photo.getExtension(), photo.getId(), photo.getUser_id()));
		} catch (Exception e) {
			e.printStackTrace();
		}
    	System.out.println("Path setted: " + photo.getPath());
		return this.service.addPhoto(photo);
	}
    
    @GET
    @Produces("application/json")
    @Path("/next/{cnt}")
    public List<Photo> searchNextFive(@PathParam("cnt") Integer cnt){
    	List<Photo> photos = this.service.searchNextFive(cnt, "", -1);
    	for(Photo p: photos) {
    		p.setPath(encoder(p.getPath(),p.getExtension()));
    		System.out.println(p);
    	}
    	return photos;
    }
    
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    @Path("/nextu")
    public List<Photo> getNextFive(User2 user){
    	List<Photo> photos = this.service.searchNextFive(user.getN(), user.getUsername(), user.getId());
    	for(Photo p: photos) {
    		p.setPath(encoder(p.getPath(),p.getExtension()));
    		System.out.println(p);
    	}
    	return photos;
    }
    
    @GET
    @Produces("text/json")
    @Path("/categories")
    public List<Category> getAllCategories(){
    	return this.service.getAllCategories();
    }
    
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    @Path("/filter")
    public List<Photo> getFilteredPhoto(Filter filter){
    	List<Photo> filteredPhotos = this.service.getFilteredPhoto(filter);
    	for(Photo p: filteredPhotos) {
    		p.setPath(encoder(p.getPath(),p.getExtension()));
    		System.out.println(p);
    	}
    	return filteredPhotos;
    }
    
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    @Path("/buy")
    public Boolean buy(Buying buying) {
    	return this.service.buy(buying);
    }
    
    public static String decoder(String base64Image, String ext, int photoId, int userId) {
        File userDir = new File(String.format("C:\\wp\\%s", userId));

        if (!userDir.exists())
            userDir.mkdir();

        try {
//        	String path = String.format("%s\\" + System.currentTimeMillis() + "%s", userDir, ext);
        	String path = "C:\\wp\\" + userId + "\\" + System.currentTimeMillis() + "." + ext;
        	FileOutputStream imageOutFile = new FileOutputStream(path);
            byte[] imageByteArray = Base64.getDecoder().decode(base64Image);
            imageOutFile.write(imageByteArray);
            imageOutFile.close();
            System.out.println("Path to be set " + path);
            return path;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static String encoder(String path, String ext) {
    	
		File imageFile = new File(String.format("%s",path));
		
		String base64 = null;
		if(ext.contains("jpg") || ext.contains("jpeg")) {
			base64 = "data:image/jpeg;base64,";
		}
		if(ext.contains("png")) {
			base64 = "data:image/png;base64,";
		}
		
	
		try (FileInputStream imageInFile = new FileInputStream(imageFile)) {
			// Reading a Image file from file system
			byte imageData[] = new byte[(int) imageFile.length()];
			imageInFile.read(imageData);
			base64 += Base64.getEncoder().encodeToString(imageData);
			System.out.println("Pic encoded: " + base64);
			return base64;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return base64;
	}

	// property
	private IServicePhoto service;
}
