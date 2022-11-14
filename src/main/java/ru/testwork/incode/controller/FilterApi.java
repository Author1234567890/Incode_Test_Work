package ru.testwork.incode.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.testwork.incode.dto.FilterRequest;
import ru.testwork.incode.dto.FilterResponse;

public interface FilterApi {

    @Operation(summary = "Apply set of filters to elements")
    @PostMapping(value = "/filter", produces = "application/json")
    FilterResponse filter(@RequestBody FilterRequest request);
}
