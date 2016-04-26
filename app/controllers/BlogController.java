package controllers;

import java.util.List;

import models.Blog;
import models.User;
import play.mvc.Result;
import play.mvc.Security.Authenticated;
import play.mvc.With;
import utils.ErrorUtils;
import views.html.manageblog;
import views.html.home;
import views.html.addblog;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Security;

public class BlogController extends Controller {
	
	private static Form<Blog> blogForm = Form.form(Blog.class);
	
	public static Result home() {
		
		List<Blog> allBlogs;
		try {
			allBlogs = Blog.find.all();
		} catch (Exception e) {
			return internalServerError(e.getMessage());
		}
		
		return ok(home.render(allBlogs));
	}

	@Security.Authenticated(Secured.class)
	public static Result addBlog() {
		return ok(addblog.render(blogForm));
	}

	@Security.Authenticated(Secured.class)
	public static Result saveBlog() {
		try {
			Blog newBlog = new Blog();
			newBlog = blogForm.bindFromRequest().get();
	    	newBlog.save();
		} catch (Exception e) {
			return internalServerError(e.getMessage());
		}
		
    	return ok("Yesss");
	}
	
	@Security.Authenticated(Secured.class)
	public static Result editBlog(Long id) {
		Blog blog;
		try {
			blog = Blog.find.byId(id);
		} catch (Exception e) {
			return internalServerError(e.getMessage());
		}
		
		return ok(views.html.editblog.render(blogForm, blog));
	}
	
	@Security.Authenticated(Secured.class)
	public static Result updateBlog(Long id) {
		Blog blog;
		
		try {
			blog = Blog.find.byId(id);
			blog = blogForm.bindFromRequest().get();
	    	blog.update();
		} catch (Exception e) {
			return internalServerError(e.getMessage());
		}
	
		return ok("Update successful!");
	}
	
	@Security.Authenticated(Secured.class)
	public static Result deleteBlog(Long id) {

		try {
			Blog.find.ref(id).delete();
		} catch(Exception ex) {
			ex.printStackTrace();
			return internalServerError(ex.getMessage());
		}
		
		return ok(String.format("Blog with id: %d has been successfully deleted.", id));
	}
	
	public static Result viewBlog(Long id) {
		Blog blog;
		try {
			blog = Blog.find.byId(id);
		} catch (Exception e) {
			// TODO: handle exception
			return internalServerError(ErrorUtils.stackTraceToString(e));
		}
		
		if(blog != null) {
			return ok(views.html.viewblog.render(blog));
		} else {
			return badRequest("Sorry there is no blog with the given id.");
		}

	}
	
	@Security.Authenticated(Secured.class)
	public static Result manageBlog() {
		List<Blog> allBlogs;
		try {
			allBlogs = Blog.find.all();
		} catch (Exception e) {
			return internalServerError(e.getMessage());
		}
		
		return ok(manageblog.render(allBlogs));
	}

}
