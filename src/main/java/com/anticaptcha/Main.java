package com.anticaptcha;

public class Main {

    private static String host = "api.anti-captcha.com";
    private static String clientKey = "12345678901234567890123456789012";
    private static String proxyHost = "xx.xx.xx.xx";
    private static Integer proxyPort = 30346;
    private static String proxyLogin = "";
    private static String proxyPassword = "";

    private static String userAgent =
            "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.106 Safari/537.36";

    public static void main(String[] args) throws Exception {
        noCaptchaTest();

        System.out.println();
        System.out.println();
        System.out.println();

        noCaptchaProxylessTest();

        System.out.println();
        System.out.println();
        System.out.println();

        imageToTextTest();

        // Exit
        System.in.read();
    }

    private static void noCaptchaTest() {
        try {
            AnticaptchaTask task = AnticaptchaApiWrapper.createNoCaptchaTask(
                    host,
                    clientKey,
                    "http://http.myjino.ru/recaptcha/test-get.php",
                    "6Lc_aCMTAAAAABx7u2W0WPXnVbI_v6ZdbM6rYf16",
                    AnticaptchaApiWrapper.ProxyType.http,
                    proxyHost,
                    proxyPort,
                    proxyLogin,
                    proxyPassword,
                    userAgent
            );

            if (task == null) {
                System.out.println("Somehow task is NULL...");
            } else {
                if (task.getErrorDescription() != null && task.getErrorDescription().length() > 0) {
                    System.out.println("Unfortunately we got the following error from the API: " +
                            task.getErrorDescription());
                } else {
                    System.out.println("Task ID is " + task.getTaskId() +
                            ". NoCaptcha task is sent, will wait for the result.");

                    Thread.sleep(2000);
                    processTask(task);
                }
            }
        } catch (Exception e) {
            System.out.println("NoCaptcha task failed with following error: " + e.getMessage());
        }
    }

    private static void noCaptchaProxylessTest() {
        try {
            AnticaptchaTask task = AnticaptchaApiWrapper.createNoCaptchaTaskProxyless(
                    host,
                    clientKey,
                    "http://http.myjino.ru/recaptcha/test-get.php",
                    "6Lc_aCMTAAAAABx7u2W0WPXnVbI_v6ZdbM6rYf16",
                    userAgent
            );

            if (task == null) {
                System.out.println("Somehow task is NULL...");
            } else {
                if (task.getErrorDescription() != null && task.getErrorDescription().length() > 0) {
                    System.out.println("Unfortunately we got the following error from the API: " +
                            task.getErrorDescription());
                } else {
                    System.out.println("Task ID is " + task.getTaskId() +
                            ". NoCaptcha (proxyless) task is sent, will wait for the result.");

                    Thread.sleep(2000);
                    processTask(task);
                }
            }
        } catch (Exception e) {
            System.out.println("NoCaptcha task (proxyless) failed with following error: " + e.getMessage());
        }
    }

    private static void imageToTextTest() throws Exception {
        AnticaptchaTask task = AnticaptchaApiWrapper.createImageToTextTask(
                host,
                clientKey,
                // you can use either base64 or path to the image file
                "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"
        );

        if (task.getErrorDescription() != null && task.getErrorDescription().length() > 0) {
            System.out.println("Unfortunately we got the following error from the API: " +
                    task.getErrorDescription());
        } else {
            System.out.println("Task ID is " + task.getTaskId() +
                    ". ImageToText task is sent, will wait for the result.");
            Thread.sleep(2000);
            processTask(task);
        }
    }

    private static void processTask(AnticaptchaTask task) throws Exception {
        AnticaptchaResult response;

        do {
            response = AnticaptchaApiWrapper.getTaskResult(host, clientKey, task);

            if (response == null || response.getStatus().equals(AnticaptchaResult.Status.ready)) {
                break;
            }

            if (response.getStatus().equals(AnticaptchaResult.Status.processing)) {
                System.out.println("Not done yet, waiting...");
                Thread.sleep(1000);
            }
        } while (response.getStatus().equals(AnticaptchaResult.Status.processing));

        if (response == null || response.getSolution() == null) {
            System.out.println("Unfortunately we got the following error from the API: " +
                    (response != null ? response.getErrorDescription() : "/empty/"));
        } else {
            System.out.println("The answer is '" + response.getSolution() + "'");
        }
    }
}