package fr.trophyquest.web.service.service;

import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.services.lambda.LambdaClient;
import software.amazon.awssdk.services.lambda.model.InvocationType;
import software.amazon.awssdk.services.lambda.model.InvokeRequest;
import software.amazon.awssdk.services.lambda.model.InvokeResponse;

@Service
public class PsnFetcherService {

    private final LambdaClient lambdaClient;

    public PsnFetcherService(LambdaClient lambdaClient) {
        this.lambdaClient = lambdaClient;
    }

    public InvokeResponse trigger(String profileName) {
        String safeProfileName = profileName.replace("\"", "\\\"").trim();
        String payload = "{\"profileName\":\"" + safeProfileName + "\"}";

        InvokeRequest req = InvokeRequest.builder().functionName("psn-fetcher").invocationType(
                InvocationType.REQUEST_RESPONSE).payload(SdkBytes.fromUtf8String(payload)).build();

        return lambdaClient.invoke(req);
    }
}
