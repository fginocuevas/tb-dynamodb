package com.thenogicode.dynamodbtest.configurations;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverterFactory;
import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
@EnableDynamoDBRepositories(basePackages = "com.thenogicode", dynamoDBMapperConfigRef = "dynamoDBMapperConfig")
public class DynamoDBConfig {

    @Value("${old-st.database.region}")
    private String region;

    @Value("${old-st.database.accessKey}")
    private String awsAccessKey;

    @Value("${old-st.database.secretKey}")
    private String awsSecretKey;

    @Value("${old-st.database.dynamoDbEndPoint}")
    private String dynamoDbEndPoint;

    @Value("${old-st.database.dynamoDbPrefix}")
    private String dynamoDbEnvironmentPrefix;

    @Bean
    public DynamoDBMapperConfig.TableNameOverride getTableNameOverride() {
        return DynamoDBMapperConfig.TableNameOverride.withTableNamePrefix(getDynamoDbEnvironmentPrefix(dynamoDbEnvironmentPrefix));
    }

    private String getDynamoDbEnvironmentPrefix(String dynamoDbEnvironmentPrefix){
        return dynamoDbEnvironmentPrefix+ "-";
    }

    @Bean
    public DynamoDBMapperConfig dynamoDBMapperConfig(DynamoDBMapperConfig.TableNameOverride tableNameOverride) {
        DynamoDBMapperConfig.Builder builder = new DynamoDBMapperConfig.Builder();
        //builder.setTableNameOverride(tableNameOverride);
        builder.setTypeConverterFactory(DynamoDBTypeConverterFactory.standard());
        return builder.build();
    }

    public AwsClientBuilder.EndpointConfiguration endpointConfiguration() {
        return new AwsClientBuilder.EndpointConfiguration(dynamoDbEndPoint, region);
    }

    public AWSCredentialsProvider awsCredentialsProvider() {
        return new AWSStaticCredentialsProvider(new BasicAWSCredentials(awsAccessKey, awsSecretKey));
    }

    @Bean
    public AmazonDynamoDB amazonDynamoDB() {
        return AmazonDynamoDBClientBuilder
                .standard()
                .withEndpointConfiguration(endpointConfiguration())
                .withCredentials(awsCredentialsProvider())
                .build();
    }

    @Bean
    @Primary
    public DynamoDBMapper dynamoDBMapper() {
        return new DynamoDBMapper(amazonDynamoDB(), dynamoDBMapperConfig(getTableNameOverride()));
    }

}
