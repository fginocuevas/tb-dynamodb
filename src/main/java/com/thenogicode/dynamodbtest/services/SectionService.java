package com.thenogicode.dynamodbtest.services;

import com.thenogicode.dynamodbtest.dtos.SectionDto;
import com.thenogicode.dynamodbtest.entities.Section;
import com.thenogicode.dynamodbtest.enums.SectionStatus;
import com.thenogicode.dynamodbtest.queryscan.SectionQueryScan;
import com.thenogicode.dynamodbtest.repositories.SectionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class SectionService {

    private final SectionRepository sectionRepository;

    private final SectionQueryScan sectionQueryScan;

    public SectionDto createSection(SectionDto dto){

        log.info("Creating Customer Record {}", dto);

        dto.setDateCreated(LocalDateTime.now());
        dto.setDateModified(LocalDateTime.now());

        return mapToDto(sectionRepository.save(mapToEntity(dto)));
    }

    public SectionDto retrieveSection(String id){
        return mapToDto(sectionRepository.findById(id).orElse(null));
    }

    public List<SectionDto> retrieveAllBetweenAgeToAndFrom(int targetAge){
        final var results = sectionRepository.findByAgeFromIsLessThanEqualAndAgeToIsGreaterThanEqual(targetAge, targetAge);
        return mapToDtoList(results);
    }

    public List<SectionDto> retrieveSectionByStatus(SectionStatus status) {

        final var results= sectionQueryScan.querySectionByStatus(status);

        return mapToDtoList(results);


    }

    private List<SectionDto> mapToDtoList(List<Section> results) {
        return results.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    private Section mapToEntity(SectionDto dto) {
        return Section.builder()
                .id(dto.getId())
                .status(dto.getStatus())
                .ageFrom(dto.getAgeFrom())
                .ageTo(dto.getAgeTo())
                .dateCreated(dto.getDateCreated())
                .dateModified(dto.getDateModified())
                .build();
    }

    private SectionDto mapToDto(Section entity) {
        return SectionDto.builder()
                .id(entity.getId())
                .status(entity.getStatus())
                .ageFrom(entity.getAgeFrom())
                .ageTo(entity.getAgeTo())
                .dateCreated(entity.getDateCreated())
                .dateModified(entity.getDateModified())
                .build();
    }

}
