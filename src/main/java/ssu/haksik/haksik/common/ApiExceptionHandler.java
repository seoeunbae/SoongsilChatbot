package ssu.haksik.haksik.common;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ssu.haksik.haksik.common.response.FoodResponse;
import ssu.haksik.haksik.dodam.exception.DodamDinnerNotExistException;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(DodamDinnerNotExistException.class)
    public FoodResponse HandleDodamDinnerNotExistException(DodamDinnerNotExistException dodamDinnerNotExistException){
        return new FoodResponse(dodamDinnerNotExistException.getMessage());
    }
}


