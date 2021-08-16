package com.thenogicode.dynamodbtest.controllers;

import com.thenogicode.dynamodbtest.dtos.SectionDto;
import com.thenogicode.dynamodbtest.entities.Section;
import com.thenogicode.dynamodbtest.enums.SectionStatus;
import com.thenogicode.dynamodbtest.services.SectionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/section")
public class SectionController {

    private final SectionService sectionService;

    @GetMapping("/{id}")
    public SectionDto retrieveRecord(@PathVariable("id") String id) {
        return sectionService.retrieveSection(id);
    }

    @GetMapping("/list/{age}")
    public List<SectionDto> retrieveWithFilter(@PathVariable("age") int targetAge){
        return sectionService.retrieveAllBetweenAgeToAndFrom(targetAge);
    }

    @GetMapping("/query/{status}")
    public List<SectionDto> retrieveUsingQueryScan(@PathVariable("status") SectionStatus status){
        return sectionService.retrieveSectionByStatus(status);
    }

    @PostMapping
    public SectionDto createRecord(@RequestBody SectionDto dto){
        var result= sectionService.createSection(dto);
        return result;
    }

}
