package com.thenogicode.dynamodbtest.entities;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.thenogicode.dynamodbtest.converters.LocalDateTimeConverter;
import com.thenogicode.dynamodbtest.enums.SectionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamoDBTable(tableName= "Sections")
public class Section implements Serializable {

    private static final long serialVersionUID = -5572572455708794857L;

    @DynamoDBHashKey(attributeName = "id")
    @DynamoDBAutoGeneratedKey
    private String id;

    @DynamoDBAttribute
    @DynamoDBTypeConverted( converter = LocalDateTimeConverter.class )
    private LocalDateTime dateCreated;

    @DynamoDBAttribute
    @DynamoDBTypeConverted( converter = LocalDateTimeConverter.class )
    private LocalDateTime dateModified;

    @DynamoDBAttribute
    @DynamoDBTypeConvertedEnum
    @DynamoDBIndexHashKey(globalSecondaryIndexName = "status-index")
    private SectionStatus status;

    @DynamoDBIgnore
    private List<SectionContent> sectionContents;

    @DynamoDBAttribute(attributeName="ageFrom")
    @DynamoDBIndexHashKey(globalSecondaryIndexName = "age-from-index")
    private Integer ageFrom;

    @DynamoDBAttribute
    private Integer ageTo;

}