package br.com.ciccr.simo.modules.transparency.client;

import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.http.HttpClient;

import org.springframework.stereotype.Component;

@Component
public class PortalHttpClient {

    private final HttpClient httpClient;
    private final CookieManager cookieManager;

    public PortalHttpClient() {

        this.cookieManager = new CookieManager();
        this.cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);

        this.httpClient = HttpClient.newBuilder()
                .cookieHandler(cookieManager)
                .followRedirects(HttpClient.Redirect.NORMAL)
                .build();
    }

    public HttpClient getClient() {
        return httpClient;
    }

    public CookieManager getCookieManager() {
        return cookieManager;
    }
}