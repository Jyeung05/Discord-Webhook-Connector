package discordwebhook;

import java.io.IOException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class sender {
    private String webhookUrl;

    public sender(String webhookUrl) {
        this.webhookUrl = webhookUrl;
    }
    
    public void setWebhookUrl(String webhookUrl) {
        this.webhookUrl = webhookUrl;
    }

    public void sendWebhook(String message) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(webhookUrl);

        // Set payload
        String jsonPayload = "{\"content\": \"" + message + "\"}";
        StringEntity jsonEntity = new StringEntity(jsonPayload);
        httpPost.setEntity(jsonEntity);

        // Set headers
        httpPost.setHeader("Content-Type", "application/json");

        // Execute HTTP request
        CloseableHttpResponse response = httpClient.execute(httpPost);

        // Check response status
        int statusCode = response.getStatusLine().getStatusCode();
        System.out.println("Webhook response status: " + statusCode);
        System.out.println("Webhook payload: " + jsonPayload);

        response.close();
        httpClient.close();
    }
}