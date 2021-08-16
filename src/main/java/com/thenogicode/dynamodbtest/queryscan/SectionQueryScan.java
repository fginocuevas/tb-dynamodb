package com.thenogicode.dynamodbtest.queryscan;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.thenogicode.dynamodbtest.entities.Section;
import com.thenogicode.dynamodbtest.enums.SectionStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class SectionQueryScan {

    private final DynamoDBMapper dynamoDBMapper;

    public List<Section> querySectionByStatus(SectionStatus status){

        Map<String,String> expressionAttributesNames = new HashMap<>();
        expressionAttributesNames.put("#status", "status");

        Map<String, AttributeValue> expressionAttributeValues = new HashMap<>();
        expressionAttributeValues.put(":status",new AttributeValue().withS(status.toString()));

        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                .withFilterExpression("#status = :status")
                .withExpressionAttributeNames(expressionAttributesNames)
                .withExpressionAttributeValues(expressionAttributeValues);

        return dynamoDBMapper.scan(Section.class, scanExpression);
    }

}
