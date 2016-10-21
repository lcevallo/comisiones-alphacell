package com.alphacell.util.jsf;


import javax.faces.FacesException;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;
import javax.inject.Inject;
import javax.persistence.PersistenceException;
import javax.swing.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by luis on 19/09/16.
 */
public class JsfExceptionHandler extends ExceptionHandlerWrapper {

    private ExceptionHandler wrapped;



    public JsfExceptionHandler(ExceptionHandler wrapped)
    {
        this.wrapped=wrapped;

    }

    @Override
    public ExceptionHandler getWrapped() {
        return this.wrapped;
    }

    @Override
    public void handle() throws FacesException{
        Iterator<ExceptionQueuedEvent> events=getUnhandledExceptionQueuedEvents().iterator();
        List<String> causas= new ArrayList<String>();
        while(events.hasNext())
        {

            ExceptionQueuedEvent  event= events.next();
            ExceptionQueuedEventContext context=(ExceptionQueuedEventContext)event.getSource();


            Throwable exception= context.getException();

            while(exception.getCause() != null) {
                exception = exception.getCause();

                causas.add(exception.getMessage());

            }

            if (!causas.isEmpty())
            {
                String commaSeparatedMessages = causas.stream()
                        .map(i -> i.toString())
                        .collect(Collectors.joining(" ]>>[ "));

                FacesUtil.addErrorMessage(commaSeparatedMessages);

                boolean handled=false;
                events.remove();

            }


/*
            try {
                if (exception instanceof PSQLException) {
                    handled=true;

                    PSQLException pe = (PSQLException) exception;
                    FacesUtil.addErrorMessage(pe.getMessage());

                }

               if(exception instanceof PersistenceException)
                {
                    handled=true;

                    PSQLException pe = (PSQLException) exception;
                    FacesUtil.addErrorMessage(pe.getMessage());

                }
            }

            finally {
                if(handled)
                events.remove();
            }
*/
        }

        getWrapped().handle();
    }

/*
    private NegocioException getNegocioException(Throwable exception)
    {
        if(exception instanceof  NegocioException){
            return (NegocioException) exception;
        }else if(exception.getCause()!=null){
            return getNegocioException(exception.getCause());
        }

        return null;

    }

    private void manejarPostgresException(Throwable exception)
    {

        while(exception!=null) {
            if (exception instanceof PSQLException) {
                PSQLException pe = (PSQLException) exception;


                break;
            }

            exception= exception.getCause();
            JOptionPane.showMessageDialog(null, "ERROR AL REGISTRAR" + exception.getMessage(), "ERROR", JOptionPane.WARNING_MESSAGE);
        }

    }
    */
}
