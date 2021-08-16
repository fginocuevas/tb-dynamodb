package com.thenogicode.dynamodbtest.converters;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;

import java.time.LocalDateTime;

public class LocalDateTimeConverter implements DynamoDBTypeConverter<String, LocalDateTime> {

    @Override
    public String convert(LocalDateTime localDateTime) {
        if (localDateTime == null)
            return null;
        return localDateTime.toString();
    }

    @Override
    public LocalDateTime unconvert(String s) {
        if (s == null)
            return null;
        return LocalDateTime.parse(s);
    }
}
