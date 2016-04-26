package models;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.*;
import javax.persistence.*;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.*;

@Entity
public class Blog extends Model {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1411777647796364826L;

	@Id
	public Long id;
	
	@Constraints.Required
	public String header;
	
	@Constraints.Required
	@Column(columnDefinition="TEXT")
	public String text;
	
	@Formats.DateTime(pattern="dd/MM/yyyy")
	@Column(name = "created_date")
	public Date createdDate;
	
	public Long getId() {
		return id;
	}
	
	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getText() {
		return text;
	}
	
	public Date getCreatedDate() {
		return createdDate;
	}
	
	public String getFormattedDate() {
		return new SimpleDateFormat("MMMM dd yyyy HH:MM").format(createdDate);
	}

	public void setText(String text) {
		this.text = Jsoup.clean(text, Whitelist.basic());
	}
	
	@Override
	public void save() {
		createdDate();
		super.save();
	}
	
	@PrePersist
	void createdDate() {
		this.createdDate = new Date();
	}

	public static Model.Finder<Long, Blog> find = new Model.Finder<Long, Blog>(
			Long.class, Blog.class);
		
//	public static List<Blog> getAllBlogs() {
//		List<Blog> blogs = new ArrayList<Blog>();
//		blogs = Ebean.find(Blog.class)
//				.findList(); 
//		return blogs; 
//	}
	
}
