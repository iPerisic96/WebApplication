package org.theplu;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.theplu.controllers.ControllerPhoto;
import org.theplu.controllers.ControllerUser;

@ApplicationPath("/rest")
public class MyRestApp extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<Class<?>>();
        classes.add(ControllerUser.class);
        classes.add(ControllerPhoto.class);
		return classes;
    }
}