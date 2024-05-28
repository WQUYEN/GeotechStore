package quyenvvph20946.fpl.geoteachapplication.model.response;

import java.util.List;

import quyenvvph20946.fpl.geoteachapplication.model.OptionProductBestSeller;

public class ProductBestSellerResponse {
    private int code;
    private String message;
    private List<OptionProductBestSeller> result;

    public ProductBestSellerResponse() {
    }

    public ProductBestSellerResponse(int code, String message, List<OptionProductBestSeller> result) {
        this.code = code;
        this.message = message;
        this.result = result;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<OptionProductBestSeller> getResult() {
        return result;
    }

    public void setResult(List<OptionProductBestSeller> result) {
        this.result = result;
    }
}
