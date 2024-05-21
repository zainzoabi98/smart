package com.tsfn.loaders.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tsfn.loaders.bean.MarketingData;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.Properties;

@Component
@EnableScheduling
public class AutomaticRequestSender {



    @Scheduled(fixedRate = 60000)
    public void sendRequestToPythonServer() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            MarketingData[] dataObjects = createYourDataObjects();

            Properties props = new Properties();
            props.put("bootstrap.servers", "localhost:9092");
            props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
            props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

            KafkaProducer<String, String> producer = new KafkaProducer<>(props);

            String jsonData = objectMapper.writeValueAsString(dataObjects);

            producer.send(new ProducerRecord<>("emailTopics", jsonData));

            producer.close();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private MarketingData[] createYourDataObjects() {
        MarketingData[] marketingDataArray = new MarketingData[2];

        LocalDateTime now = LocalDateTime.now();

        MarketingData marketingData1 = new MarketingData();
        marketingData1.setId(1);
        marketingData1.setAccountId(123);
        marketingData1.setTimestamp(now.minusHours(2).toString());
        marketingData1.setPostID("abc123");
        marketingData1.setContentType("image");
        marketingData1.setImpressions(1000);
        marketingData1.setViews(500);
        marketingData1.setClicks(200);
        marketingData1.setCtr(0.2);
        marketingData1.setLikes(50);
        marketingData1.setComments(10);
        marketingData1.setShares(5);
        marketingData1.setEngagementRate(0.05);
        marketingData1.setFileUploaded("example.jpg");
        marketingData1.setDescription("This is a sample post description.");

        MarketingData marketingData2 = new MarketingData();
        marketingData2.setId(2);
        marketingData2.setAccountId(456);
        marketingData2.setTimestamp(now.minusHours(3).toString());
        marketingData2.setPostID("def456");
        marketingData2.setContentType("video");
        marketingData2.setImpressions(1500);
        marketingData2.setViews(600);
        marketingData2.setClicks(300);
        marketingData2.setCtr(0.2);
        marketingData2.setLikes(70);
        marketingData2.setComments(20);
        marketingData2.setShares(10);
        marketingData2.setEngagementRate(0.05);
        marketingData2.setFileUploaded("example.mp4");
        marketingData2.setDescription("This is another sample post description.");

        marketingDataArray[0] = marketingData1;
        marketingDataArray[1] = marketingData2;

        return marketingDataArray;
    }
}
