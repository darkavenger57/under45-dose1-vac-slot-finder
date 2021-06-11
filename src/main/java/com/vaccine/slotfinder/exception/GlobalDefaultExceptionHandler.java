package com.vaccine.slotfinder.exception;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

/**
 * Global Exception Handler
 * @author 
 *
 */
@ControllerAdvice
class GlobalDefaultExceptionHandler {
	
  public static final String DEFAULT_ERROR_VIEW = "error";
  

  private static final Logger LOGGER = LoggerFactory.getLogger(GlobalDefaultExceptionHandler.class);

  @ExceptionHandler(value = Exception.class)
  public ModelAndView
  defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
   
	  if (AnnotationUtils.findAnnotation
                (e.getClass(), ResponseStatus.class) != null)
      throw e;

    ModelAndView mav = new ModelAndView();
    mav.addObject("exception", e);
    mav.addObject("url", req.getRequestURL());
    mav.setViewName(DEFAULT_ERROR_VIEW);
    
    LOGGER.error(e.getMessage(), e);
    return mav;
  }
}