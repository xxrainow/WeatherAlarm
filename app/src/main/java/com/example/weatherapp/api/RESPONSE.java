package com.example.weatherapp.api;

public class RESPONSE {
    private HEADER header;
    private BODY body;

    public RESPONSE(HEADER header, BODY body) {
        this.header = header;
        this.body = body;
    }

    public HEADER getHeader() {
        return header;
    }

    public void setHeader(HEADER header) {
        this.header = header;
    }

    public BODY getBody() {
        return body;
    }

    public void setBody(BODY body) {
        this.body = body;
    }
}
