package com.studentManagement.controller;

import com.studentManagement.model.DTO.request.SchoolDTOUpdateRequest;
import com.studentManagement.model.DTO.response.SchoolDTOResponse;
import com.studentManagement.service.SchoolService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/school")
public class SchoolController {
    private SchoolService schoolService;
    public SchoolController(SchoolService schoolService) {
        this.schoolService = schoolService;
    }

    @PatchMapping
    public ResponseEntity<SchoolDTOResponse> updateSchool(@RequestBody @Valid SchoolDTOUpdateRequest schoolDTOUpdateRequest) {
        return new ResponseEntity<>(schoolService.updateSchool(schoolDTOUpdateRequest), HttpStatus.OK);
    }
}
