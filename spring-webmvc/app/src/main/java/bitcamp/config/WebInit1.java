package bitcamp.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration.Dynamic;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class WebInit1 implements WebInit {
  @Override
  public void start(ServletContext args0) {
    System.out.println("WebInit1.start() 호출됨!");


    AnnotationConfigWebApplicationContext iocContainer =
        new AnnotationConfigWebApplicationContext();
    iocContainer.setServletContext(sce.getServletContext());
    iocContainer.register(bitcamp.AppConfig.class);

    DispatcherServlet frontController = new DispatcherServlet(iocContainer);

    Dynamic options = sce.getServletContext().addServlet("app", frontController);

    options.addMapping("/app/*");
  }
}
