package com.company;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

class AnticaptchaApiWrapper {

    private static HashMap<String, Boolean> HostsChecked = new HashMap<>();

    private static JSONObject jsonPostRequest(String host, String methodName, JSONObject jsonPost) throws JSONException {

        AntigateHttpRequest antigateHttpRequest = new AntigateHttpRequest("http://" + host + "/" + methodName);
        antigateHttpRequest.setRawPost(jsonPost.toString(4));
        antigateHttpRequest.addHeader("Content-Type", "application/json; charset=utf-8");
        antigateHttpRequest.addHeader("Accept", "application/json");
        antigateHttpRequest.setTimeout(30_000);

        try {
            return new JSONObject(AntigateHttpHelper.download(antigateHttpRequest).getBody());
        } catch (Exception ignored) {
        }

        return null;
    }

    private static Boolean checkHostAndPort(String host, Integer port) {
        if (!HostsChecked.containsKey(host)) {
            HostsChecked.put(host, isReachable(host, port));
        }

        return HostsChecked.get(host);
    }

    private static boolean isReachable(String addr, int openPort) {
        try {
            try (Socket soc = new Socket()) {
                soc.connect(new InetSocketAddress(addr, openPort), 1000);
            }

            return true;
        } catch (IOException ex) {
            return false;
        }
    }

    static AnticaptchaTask createNoCaptchaTask(String host, String clientKey, String websiteUrl, String websiteKey, ProxyType proxyType, String proxyAddress, Integer proxyPort, String proxyLogin, String proxyPassword, String userAgent) throws Exception {

        if (proxyAddress == null || proxyAddress.length() == 0 || !checkHostAndPort(proxyAddress, proxyPort)) {
            throw new Exception("Proxy address is incorrect!");
        }

        if (proxyPort < 1 || proxyPort > 65535) {
            throw new Exception("Proxy port is incorrect!");
        }
        if (userAgent == null || userAgent.length() == 0) {
            throw new Exception("User-Agent is incorrect!");
        }
        if (websiteUrl == null || websiteUrl.length() == 0 || !websiteUrl.contains(".") || !websiteUrl.contains("/") ||
                !websiteUrl.contains("http")) {
            throw new Exception("Website URL is incorrect!");
        }

        if (websiteKey == null || websiteKey.length() == 0) {
            throw new Exception("Recaptcha Website Key is incorrect!");
        }

        JSONObject json = new JSONObject();
        json.put("clientKey", clientKey);

        JSONObject taskJson = new JSONObject();
        taskJson.put("type", "NoCaptchaTask");
        taskJson.put("websiteURL", websiteUrl);
        taskJson.put("websiteKey", websiteKey);
        taskJson.put("proxyType", proxyType.toString());
        taskJson.put("proxyAddress", proxyAddress);
        taskJson.put("proxyPort", proxyPort);
        taskJson.put("proxyLogin", proxyLogin);
        taskJson.put("proxyPassword", proxyPassword);
        taskJson.put("userAgent", userAgent);

        json.put("task", taskJson);

        try {
            JSONObject resultJson = jsonPostRequest(host, "createTask", json);

            return new AnticaptchaTask(
                    resultJson.has("taskId") ? resultJson.getInt("taskId") : null,
                    resultJson.has("errorId") ? resultJson.getInt("errorId") : null,
                    resultJson.has("errorCode") ? resultJson.getString("errorCode") : null,
                    resultJson.has("errorDescription") ? resultJson.getString("errorDescription") : null
            );
        } catch (Exception ignored) {
        }

        return null;
    }

    private static String imagePathToBase64String(String path) {
        try {
            return Base64.encode(Files.readAllBytes(Paths.get(path)));
        } catch (Exception e) {
            return null;
        }
    }

    static AnticaptchaTask createImageToTextTask(String host, String clientKey, String body) throws JSONException {
        return createImageToTextTask(host, clientKey, body, null, null, null, null, null, null);
    }

    public static AnticaptchaTask createImageToTextTask(
            String host,
            String clientKey,
            String pathToImageOrBase64Body,
            Boolean phrase,
            Boolean _case,
            Integer numeric,
            Boolean math,
            Integer minLength,
            Integer maxLength
    ) throws JSONException {
        try {
            File f = new File(pathToImageOrBase64Body);

            if (f.exists()) {
                pathToImageOrBase64Body = imagePathToBase64String(pathToImageOrBase64Body);
            }
        } catch (Exception ignored) {
        }

        JSONObject json = new JSONObject();
        json.put("clientKey", clientKey);

        JSONObject taskJson = new JSONObject();
        taskJson.put("type", "ImageToTextTask");
        taskJson.put("body", pathToImageOrBase64Body);

        if (phrase != null) {
            taskJson.put("phrase", phrase);
        }

        if (_case != null) {
            taskJson.put("case", _case);
        }

        if (numeric != null) {
            taskJson.put("numeric", numeric);
        }

        if (math != null) {
            taskJson.put("math", math);
        }

        if (minLength != null) {
            taskJson.put("minLength", minLength);
        }

        if (maxLength != null) {
            taskJson.put("maxLength", maxLength);
        }

        json.put("task", taskJson);

        try {
            JSONObject resultJson = jsonPostRequest(host, "createTask", json);

            return new AnticaptchaTask(
                    resultJson.has("taskId") ? resultJson.getInt("taskId") : null,
                    resultJson.has("errorId") ? resultJson.getInt("errorId") : null,
                    resultJson.has("errorCode") ? resultJson.getString("errorCode") : null,
                    resultJson.has("errorDescription") ? resultJson.getString("errorDescription") : null
            );
        } catch (Exception ignored) {

        }

        return null;
    }

    static AnticaptchaResult getTaskResult(String host, String clientKey, AnticaptchaTask task) throws JSONException {

        JSONObject json = new JSONObject();
        json.put("clientKey", clientKey);
        json.put("taskId", task.getTaskId());

        try {
            JSONObject resultJson = jsonPostRequest(host, "getTaskResult", json);

            AnticaptchaResult.Status status = resultJson.has("status")
                    ? (resultJson.getString("status").equals("ready") ? AnticaptchaResult.Status.ready : AnticaptchaResult.Status.processing)
                    : AnticaptchaResult.Status.unknown;

            String solution = null;
            Integer errorId = null;
            String errorCode = null;
            String errorDescription = null;
            Double cost = null;
            String ip = null;
            Integer createTime = null;
            Integer endTime = null;
            Integer solveCount = null;

            if (resultJson.has("solution")) {
                if (resultJson.getJSONObject("solution").has("gRecaptchaResponse")) {
                    solution = resultJson.getJSONObject("solution").getString("gRecaptchaResponse");
                } else if (resultJson.getJSONObject("solution").has("text")) {
                    solution = resultJson.getJSONObject("solution").getString("text");
                }
            }

            if (resultJson.has("errorId")) {
                errorId = resultJson.getInt("errorId");
            }

            if (resultJson.has("errorCode")) {
                errorCode = resultJson.getString("errorCode");
            }

            if (resultJson.has("errorDescription")) {
                errorDescription = resultJson.getString("errorDescription");
            }

            if (resultJson.has("cost")) {
                cost = resultJson.getDouble("cost");
            }

            if (resultJson.has("createTime")) {
                createTime = resultJson.getInt("createTime");
            }

            if (resultJson.has("endTime")) {
                endTime = resultJson.getInt("endTime");
            }

            if (resultJson.has("solveCount")) {
                solveCount = resultJson.getInt("solveCount");
            }

            return new AnticaptchaResult(
                    status,
                    solution,
                    errorId,
                    errorCode,
                    errorDescription,
                    cost,
                    ip,
                    createTime,
                    endTime,
                    solveCount
            );
        } catch (Exception ignored) {
        }

        return null;
    }

    enum ProxyType {
        http
    }
}

