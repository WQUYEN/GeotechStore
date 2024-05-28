package quyenvvph20946.fpl.geoteachapplication.model.response.store;

import com.google.gson.annotations.SerializedName;

public class Favourite {
    @SerializedName("_id")
    String id;
    @SerializedName("user_id")

    String UserId;
    @SerializedName("product_id")

    String UserProduct;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getUserProduct() {
        return UserProduct;
    }

    public void setUserProduct(String userProduct) {
        UserProduct = userProduct;
    }

    public Favourite() {
    }

    public Favourite(String id, String userId, String userProduct) {
        this.id = id;
        UserId = userId;
        UserProduct = userProduct;
    }
}
