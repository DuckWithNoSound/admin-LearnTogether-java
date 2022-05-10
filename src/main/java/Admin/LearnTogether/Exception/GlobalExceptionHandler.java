package Admin.LearnTogether.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/*
  Created by Luvbert
*/
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorMessage handleException(){
        return new ErrorMessage(1000, "Something's wrong.");
    }

    @ExceptionHandler(ResourceNotfoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorMessage handleResourceNotFoundException(){
        return new ErrorMessage(404, "Resource not found.");
    }

    @ExceptionHandler(UnAuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorMessage handleUnAuthenticationException(){
        return new ErrorMessage(401, "Unauthorized.");
    }

    @ExceptionHandler(UnAuthorizationException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorMessage handleDoNotHavePermission(){
        return new ErrorMessage(403, "Do not have permission.");
    }
}


