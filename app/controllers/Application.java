package controllers;

import models.User;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;
import views.html.login;

public class Application extends Controller {

    public static Result index() {
        return ok(index.render("Your new application is ready."));
    }
    
    public static Result login() {
    	return ok(login.render(Form.form(Login.class)));
    }
    
    public static class Login {
    	public String email;
    	public String password;
    	
    	public String validate() {
    		if(User.authenticate(email, password) == null) {
    			return "INvalid user";
    		}
    		return null;
    	}
    }
    
    public static Result authenticate() {
    	Form<Login> loginForm = Form.form(Login.class).bindFromRequest();
    	if(loginForm.hasErrors()) {
    		return badRequest(login.render(loginForm));
    	} else {
    		session().clear();
    		session("email", loginForm.get().email);
    		return redirect(routes.BlogController.manageBlog());
    	}

    }
    
    public static Result logout() {
    	session().clear();
    	flash("Success", "You've been logged out");
    	return redirect(routes.Application.login());
    }

}
