package bitcamp.myapp.controller;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import java.beans.PropertyEditorSupport;

/**
 * packageName    : bitcamp.myapp.controller
 * fileName       : GlobalControllerAdvice
 * author         : narilee
 * date           : 24. 10. 17.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 24. 10. 17.        narilee       최초 생성
 */
@ControllerAdvice
public class GlobalControllerAdvice {
  public void initBinder(WebDataBinder webDataBinder) {
    webDataBinder.registerCustomEditor(
        java.util.Date.class,
        new PropertyEditorSupport() {
          @Override
          public void setAsText(String text) throws IllegalArgumentException {
            this.setValue(java.sql.Date.valueOf(text));
          }
        }
    );
  }

  @ExceptionHandler
  public ModelAndView exceptionHandler(Exception e) {
    ModelAndView mv = new ModelAndView();
    mv.addObject("error", e);
    mv.setViewName("error");
    return mv;
  }
}
