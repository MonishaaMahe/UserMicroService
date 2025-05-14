package com.sample.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import liquibase.util.csv.CSVReader;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class FileProcessorService {

    private final S3Client s3Client;
    private final ObjectMapper mapper;

    @Value("${aws.s3.bucket-name}")
    private String bucket;
    @Value("${aws.s3.input-folder}")
    private String inputFolder;
    @Value("${aws.s3.output-folder}")
    private String outputFolder;

    @Scheduled(fixedRate = 10000) // every 10s
    public void checkAndProcessCsvFiles() throws IOException {
        ListObjectsV2Response listResponse = s3Client.listObjectsV2(
            ListObjectsV2Request.builder().bucket(bucket).prefix(inputFolder).build());

        for (S3Object s3Object : listResponse.contents()) {
            if (!s3Object.key().endsWith(".csv")) continue;

            // Read CSV
            ResponseInputStream<GetObjectResponse> s3is = s3Client.getObject(
                GetObjectRequest.builder().bucket(bucket).key(s3Object.key()).build());

            System.out.println("Found file: " + s3Object.key());

            List<Map<String, String>> records = new ArrayList<>();
            try (Reader reader = new InputStreamReader(s3is)) {
                CSVReader csvReader = new CSVReader(reader);
                String[] headers = csvReader.readNext();
                String[] line;
                while ((line = csvReader.readNext()) != null) {
                    Map<String, String> obj = new HashMap<>();
                    for (int i = 0; i < headers.length; i++) {
                        obj.put(headers[i], line[i]);
                    }
                    records.add(obj);
                }
            }

            // Write JSON to output folder
            String json = mapper.writeValueAsString(records);
            String outputKey = outputFolder + s3Object.key().replace(inputFolder, "").replace(".csv", ".json");
            s3Client.putObject(PutObjectRequest.builder().bucket(bucket).key(outputKey).build(),
                RequestBody.fromString(json));

            // Optional: Delete input CSV after processing
            s3Client.deleteObject(DeleteObjectRequest.builder().bucket(bucket).key(s3Object.key()).build());
        }
    }
}

