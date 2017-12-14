package com.xilin.management.school.web.util;

import java.util.Iterator;
import java.util.Map;

import javax.faces.FacesException;
import javax.faces.application.NavigationHandler;
import javax.faces.application.ViewExpiredException;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;

public class CustomExceptionHandler extends ExceptionHandlerWrapper {
    private ExceptionHandler wrapped;

    public CustomExceptionHandler(ExceptionHandler wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public ExceptionHandler getWrapped() {
        return this.wrapped;
    }

    @Override
    public void handle() throws FacesException {
        for (Iterator<ExceptionQueuedEvent> i = getUnhandledExceptionQueuedEvents().iterator(); i.hasNext();) {
            ExceptionQueuedEvent event = i.next();
            ExceptionQueuedEventContext context = (ExceptionQueuedEventContext) event.getSource();

            FacesContext facesContext = FacesContext.getCurrentInstance();
            Map<String, Object> requestMap = facesContext.getExternalContext().getRequestMap();
            NavigationHandler navigationHandler = facesContext.getApplication().getNavigationHandler();
            
            Throwable t = context.getException();
            if (t instanceof ViewExpiredException) {
                ViewExpiredException vee = (ViewExpiredException) t;
                try {
                    // Push some useful stuff to the request scope for use in the page
                    requestMap.put("currentViewId", vee.getViewId());
                    navigationHandler.handleNavigation(facesContext, null, "/viewExpired");
                    facesContext.renderResponse();
                } finally {
                    i.remove();
                }
            }
            else {
            	try {
                    Flash flash = facesContext.getExternalContext().getFlash();
                    
                    // Put the exception in the flash scope to be displayed in the error 
                    // page if necessary ...
                    flash.put("errorDetails", t.getMessage());
                    /*System.out.println("the error is put in the flash: " + t.getMessage());
                    NavigationHandler navigationHandler = facesContext.getApplication().getNavigationHandler();
                    */
                    navigationHandler.handleNavigation(facesContext, null, "/error?faces-redirect=true");
                    
                    facesContext.renderResponse();
                } finally {
                    i.remove();
                }
            }
        }

        // At this point, the queue will not contain any ViewExpiredEvents. Therefore, let the parent handle them.
        getWrapped().handle();
    }
}
