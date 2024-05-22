package com.mygdx.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Json;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class Server {
    private static final OkHttpClient client = new OkHttpClient();

    public static boolean submitScore(String player, float score) {
        if (Gdx.app.getType() == Application.ApplicationType.HeadlessDesktop){
            return false;
        }
        MediaType JSON = MediaType.get("application/json; charset=utf-8");
        String json = String.format("{\"player\": \"%s\", \"score\": %f}", player, score);
        RequestBody body = RequestBody.create(json, JSON);
        Request request = new Request.Builder()
                .url("https://score-board-afe96bddb988.herokuapp.com/submit_score")
                .post(body)
                .build();

        CountDownLatch latch = new CountDownLatch(1);

        Result result = new Result();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                System.out.println("Failed to submit score");
                result.fail();
                latch.countDown();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) {
                if (response.isSuccessful()) {
                    result.successful();
                } else {
                    System.out.println("Unexpected code " + response);
                    result.fail();
                }
                response.close();
                latch.countDown();
            }
        });

        try {
            latch.await(); // Wait for the callback to count down
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Thread was interrupted");
        }

        return result.getResult();
    }
    public static void fetchScores(List<Data> result) {
        if (Gdx.app.getType() == Application.ApplicationType.HeadlessDesktop){
            return;
        }
        Request request = new Request.Builder()
                .url("https://score-board-afe96bddb988.herokuapp.com/get_scores")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                System.out.println("HTTP Request" + "Failed to fetch scores");
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    String responseBody = response.body().string();
                    Json json = new Json();
                    List<Data> scores = Arrays.asList(json.fromJson(Data[].class, responseBody));
                    result.removeIf(data -> data.getStatus() == 1);
                    result.addAll(scores);
                    Collections.sort(result);
                    Collections.reverse(result);

                } else {
                    System.out.println("Unexpected code" + response);
                }

            }
        });
    }

    public static class Data implements Comparable<Data>{
        private String player;
        private float score;
        private final int status;

        @SuppressWarnings("unused")
        public Data(){
            status = 1;
        }

        public Data(String player, float score, int status){
            this.player = player;
            this.score = score;
            this.status = status;
        }

        public String getPlayer(){
            return player;
        }

        public float getScore(){
            return score;
        }

        public int getStatus(){
            return status;
        }

        @Override
        public int compareTo(Data o) {
            return Float.compare(score, o.score);
        }
    }

    private static class Result{
        private boolean result;
        public void successful(){
            result = true;
        }
        public void fail(){
            result = false;
        }
        public boolean getResult(){
            return result;
        }
    }

}
