package quyenvvph20946.fpl.geoteachapplication.model.response;


import java.util.List;

import quyenvvph20946.fpl.geoteachapplication.model.ProductType;

public class ProductTypeResponse {
    String message;
    int code;
    List<ProductType> data;

    public ProductTypeResponse() {
    }

    public ProductTypeResponse(String message, int code, List<ProductType> data) {
        this.message = message;
        this.code = code;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<ProductType> getData() {
        return data;
    }

    public void setData(List<ProductType> data) {
        this.data = data;
    }
}
