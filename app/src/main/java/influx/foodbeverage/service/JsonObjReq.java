package influx.foodbeverage.service;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by user on 31-Aug-17.
 */

public class JsonObjReq extends JsonObjectRequest {
    private JSONObject jsonObject;
    public JsonObjReq(JSONObject jsonRequest, int method, String url,JSONObject jsonObject, Response.Listener<JSONObject> listener,
                      Response.ErrorListener errorListener){
        super(method,url,jsonRequest,listener,errorListener);
        this.jsonObject =  jsonObject;
    }
    @Override
    public byte[] getBody() {
        return jsonObject.toString().getBytes();
    }
    @Override
    public String getBodyContentType() {
        return "application/json";
    }

    @Override
    public RetryPolicy getRetryPolicy() {
        return super.getRetryPolicy();
    }

    @Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
        return super.parseNetworkResponse(response);
    }
}
