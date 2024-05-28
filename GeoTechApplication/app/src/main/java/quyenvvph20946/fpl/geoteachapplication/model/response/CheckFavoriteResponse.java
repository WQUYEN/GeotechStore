package quyenvvph20946.fpl.geoteachapplication.model.response;

public class CheckFavoriteResponse {
    private boolean exists;

    public CheckFavoriteResponse(boolean exists) {
        this.exists = exists;
    }

    public boolean isExists() {
        return exists;
    }

    public void setExists(boolean exists) {
        this.exists = exists;
    }
}