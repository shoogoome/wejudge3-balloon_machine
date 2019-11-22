package net.wejudge.dto;
import com.alibaba.fastjson.JSON;


import java.util.Map;

public class Response {

    private int status;

    private Map json;

    public Response(){}

    public Response(int status, String result) {
        this.status = status;
        json = (Map) JSON.parse(result);
    }

    public Response(int status, Map json) {
        this.status = status;
        this.json = json;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Map getJson() {
        return json;
    }

    public void setJson(Map json) {
        this.json = json;
    }
}
