package main.java.com.hit.server;

import java.util.Map;

public class Request<T> implements java.io.Serializable {
    public T body;
    public Map<String, String> headers;

    public Request(T body, Map<String, String> headers) {
        this.body = body;
        this.headers = headers;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public String toString() {
        return "{ \"headers\":\n" + headers.toString() + "\n\"body\":\n" + body.toString() + "\n}";
    }

}
