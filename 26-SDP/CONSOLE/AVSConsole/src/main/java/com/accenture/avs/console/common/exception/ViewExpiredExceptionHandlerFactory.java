package com.accenture.avs.console.common.exception;

import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerFactory;

public class ViewExpiredExceptionHandlerFactory extends ExceptionHandlerFactory {

    private ExceptionHandlerFactory base;
   
    public ViewExpiredExceptionHandlerFactory(ExceptionHandlerFactory base) {
        this.base = base;
    }
   
    @Override
    public ExceptionHandler getExceptionHandler() {
        return new ViewExpiredExceptionHandler(base.getExceptionHandler());
    }
   
}