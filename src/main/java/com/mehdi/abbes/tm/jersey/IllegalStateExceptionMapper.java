package com.mehdi.abbes.tm.jersey;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class IllegalStateExceptionMapper implements ExceptionMapper<IllegalStateException> {
    
    @Override
    public Response toResponse(final IllegalStateException exception) {
        return Response.status(Status.NOT_FOUND).type(MediaType.TEXT_PLAIN).entity(exception.getMessage()).build();
    }
    
}
