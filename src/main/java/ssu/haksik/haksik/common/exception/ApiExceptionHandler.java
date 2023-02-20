package ssu.haksik.haksik.common.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ssu.haksik.haksik.common.exception.DodamDinnerNotExistException;
import ssu.haksik.haksik.common.response.FoodResponse;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(DodamDinnerNotExistException.class)
    public FoodResponse HandleDodamDinnerNotExistException(DodamDinnerNotExistException dodamDinnerNotExistException){
        return new FoodResponse(dodamDinnerNotExistException.getMessage());
    }
}


