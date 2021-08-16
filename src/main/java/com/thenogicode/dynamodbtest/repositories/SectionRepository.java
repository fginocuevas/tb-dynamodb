package com.thenogicode.dynamodbtest.repositories;

import com.thenogicode.dynamodbtest.entities.Section;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.socialsignin.spring.data.dynamodb.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

@EnableScan
public interface SectionRepository extends PagingAndSortingRepository<Section, String> {

    List<Section> findByAgeFromIsLessThanEqualAndAgeToIsGreaterThanEqual(int ageStart, int ageEnd);

}
