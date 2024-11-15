package com.nisanth.util;

import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sns.model.AmazonSNSException;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MessageUtil {

    @Value("${aws.access-key-id}")
    private String accessKeyId;

    @Value("${aws.secret-access-key}")
    private String secretAccessKey;

    public void sendMessage(String phoneNumber, String message) {
        try {
            // Credentials
            BasicAWSCredentials credentials = new BasicAWSCredentials(accessKeyId, secretAccessKey);

            // SNS client
            AmazonSNS snsClient = AmazonSNSClientBuilder.standard()
                    .withCredentials(new AWSStaticCredentialsProvider(credentials))
                    .withRegion(Regions.EU_NORTH_1)
                    .build();

            // Publish request
            PublishResult result = snsClient.publish(
                    new PublishRequest()
                            .withMessage(message)
                            .withPhoneNumber(phoneNumber));

            System.out.println("Message sent! Message ID: " + result.getMessageId());
        } catch (AmazonSNSException e) {
            // Handle Amazon SNS exception
            e.printStackTrace();
        } catch (SdkClientException e) {
            // Handle SDK client exception
            e.printStackTrace();
        }
    }
}
