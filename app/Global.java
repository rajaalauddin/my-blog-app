import java.lang.reflect.Method;

import play.Application;
import play.GlobalSettings;
import play.Logger;
import play.api.mvc.EssentialFilter;
import play.filters.csrf.CSRFFilter;
import play.mvc.*;
import play.mvc.Http.*;
import play.libs.F.*;

public class Global extends GlobalSettings {
	
	public void onStart(Application app) {
        Logger.info("Application has started");
    }

    public void onStop(Application app) {
        Logger.info("Application shutdown...");
    }
    
	@Override
    public <T extends EssentialFilter> Class<T>[] filters() {
        return new Class[]{CSRFFilter.class};
    }
	
	public Promise<Result> onError(RequestHeader request, Throwable t) {
        return Promise.<Result>pure(Results.internalServerError(
            views.html.errorpage.render(t.toString())
        ));
    }
	
	public Promise<Result> onHandlerNotFound(RequestHeader request) {
        return Promise.<Result>pure(Results.notFound(
            views.html.notfound.render(request.uri())
        ));
    }
	
	public Promise<Result> onBadRequest(RequestHeader request, String error) {
        return Promise.<Result>pure(Results.badRequest(error));
    }
	
	public Action onRequest(Request request, Method actionMethod) {
//        System.out.println("before each request..." + request.toString());

        return super.onRequest(request, actionMethod);
    }

}