package com.lambda_demo1.lambdaDemo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.EnvironmentVariableCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.lambda.LambdaClient;
import software.amazon.awssdk.services.lambda.model.InvokeRequest;
import software.amazon.awssdk.services.lambda.model.InvokeResponse;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
class MultiplyController {

    private final LambdaClient lambdaClient;
    private final ObjectMapper objectMapper;

    public MultiplyController() {
        this.lambdaClient = LambdaClient.builder()
                .build();
        this.objectMapper = new ObjectMapper();
    }

    @GetMapping(path = "multiply")
    public Map<String, Object> multiply(@RequestParam int num1, @RequestParam int num2) throws Exception {
        System.out.println(num1);
        System.out.println(num2);
        Map<String, Integer> inputPayload = new HashMap<>();
        inputPayload.put("num1", num1);
        inputPayload.put("num2", num2);

        String inputJson = objectMapper.writeValueAsString(inputPayload);
        SdkBytes payload = SdkBytes.fromString(inputJson, StandardCharsets.UTF_8);

        InvokeRequest invokeRequest = InvokeRequest.builder()
                .functionName("multiply_numbers")
                .payload(payload)
                .build();

        InvokeResponse invokeResponse = lambdaClient.invoke(invokeRequest);

        int result = Integer.parseInt(invokeResponse.payload().asUtf8String());

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Request successful");
        response.put("apiCode", 200);

        Map<String, Object> data = new HashMap<>();
        data.put("result", result);
        response.put("data", data);

        return response;
    }


    @GetMapping(path = "status")
    public String status(){
        return "Check the status of API";
    }
}
