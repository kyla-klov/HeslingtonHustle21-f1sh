package com.mygdx.game.GitHub;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.google.gson.Gson;
import okhttp3.*;

import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class UpdateGithub {

    private static final String REPO_OWNER = "kyla-klov"; // Repository owner's username
    private static final String REPO_NAME = "HeslingtonHustle21-f1sh";
    private static final String FILE_PATH = "Game/storage/PlayerData.txt";
    private static final String ACCESS_TOKEN_ENCRYPTED = "Z2hwX2U1NU9RZzRIdXFLZGhwM2VjbHVFUkZQcFQzaUNRSTRaWXVHbg==";
    private static final String BRANCH = "main"; // Branch name
    private static final String URL = "https://api.github.com/repos/" + REPO_OWNER + "/" + REPO_NAME + "/contents/" + FILE_PATH;

    private static String decryptToken(String encryptedToken) {
        // For simplicity, we're using Base64 decoding as our "decryption"
        byte[] decodedBytes = Base64.getDecoder().decode(encryptedToken);
        return new String(decodedBytes);
    }

    public static void updateFileOnGitHub(String localFilePath) throws IOException {
        String accessToken = decryptToken(ACCESS_TOKEN_ENCRYPTED);

        OkHttpClient client = new OkHttpClient();

        // Fetch the current file data from GitHub
        Request getRequest = new Request.Builder()
                .url(URL + "?ref=" + BRANCH)
                .header("Authorization", "token " + accessToken)
                .build();

        Response getResponse = client.newCall(getRequest).execute();
        if (!getResponse.isSuccessful()) {
            System.err.println("Error fetching file info: " + getResponse.message());
            return;
        }

        String responseBody = getResponse.body().string();
        Gson gson = new Gson();
        Map<String, Object> map = gson.fromJson(responseBody, Map.class);
        String sha = (String) map.get("sha");

        // Read the local text file using Gdx.files.internal
        FileHandle fileHandle = Gdx.files.internal(localFilePath);
        if (!fileHandle.exists()) {
            System.err.println("Error: File " + localFilePath + " does not exist.");
            return;
        }

        byte[] contentBytes = fileHandle.readBytes();
        String content = Base64.getEncoder().encodeToString(contentBytes);

        // Create JSON payload
        Map<String, String> jsonMap = new HashMap<>();
        jsonMap.put("message", "Updating " + FILE_PATH);
        jsonMap.put("content", content);
        jsonMap.put("sha", sha);

        // Convert map to JSON string
        String jsonPayload = gson.toJson(jsonMap);

        // Create a PUT request to update the file
        RequestBody body = RequestBody.create(
                jsonPayload,
                MediaType.parse("application/json")
        );

        Request request = new Request.Builder()
                .url(URL)
                .put(body)
                .header("Authorization", "token " + accessToken)
                .build();

        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) {
            System.err.println("Failed to update file: " + response.message());
            return;
        }

        System.out.println("File updated successfully: " + response.body().string());
    }
}