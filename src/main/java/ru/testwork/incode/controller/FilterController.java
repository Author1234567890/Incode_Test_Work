package ru.testwork.incode.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import ru.testwork.incode.dto.FilterRequest;
import ru.testwork.incode.dto.FilterResponse;
import ru.testwork.incode.filter.Filter;
import ru.testwork.incode.filter.FilterRegistry;
import ru.testwork.incode.service.FilterService;

import javax.validation.Valid;
import java.util.stream.Collectors;

import static ru.testwork.incode.dto.Mapper.toFilterResponse;
import static ru.testwork.incode.dto.Mapper.toModel;

@RestController
public class FilterController implements FilterApi {

    @Autowired
    private FilterService filterService;

    @Override
    public FilterResponse filter(@Valid FilterRequest request) {
        var processedElements = filterService.applyFilters(toModel(request));
        return toFilterResponse(processedElements);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public FilterResponse handleValidationExceptions(MethodArgumentNotValidException e) {
        String invalidFields = e.getFieldErrors().stream()
            .map(fieldError -> String.format("Field %s %s", fieldError.getField(), fieldError.getDefaultMessage()))
            .collect(Collectors.joining());
        return new FilterResponse(invalidFields);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({
        FilterRegistry.FilterNotFoundException.class,
        Filter.IncorrectParamException.class,
        Filter.NotEnoughParamsProvidedException.class
    })
    public FilterResponse handleFilterServiceExceptions(Exception e) {
        return new FilterResponse(e.getMessage());
    }
}
