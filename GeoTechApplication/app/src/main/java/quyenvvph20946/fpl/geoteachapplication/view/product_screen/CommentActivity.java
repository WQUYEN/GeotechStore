package quyenvvph20946.fpl.geoteachapplication.view.product_screen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import android.window.OnBackInvokedDispatcher;

import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import quyenvvph20946.fpl.geoteachapplication.R;
import quyenvvph20946.fpl.geoteachapplication.adapter.CommentAdapter;
import quyenvvph20946.fpl.geoteachapplication.api.BaseApi;
import quyenvvph20946.fpl.geoteachapplication.databinding.ActivityCommentBinding;
import quyenvvph20946.fpl.geoteachapplication.model.Comment;
import quyenvvph20946.fpl.geoteachapplication.model.ProductDetail;
import quyenvvph20946.fpl.geoteachapplication.model.User;
import quyenvvph20946.fpl.geoteachapplication.model.response.ListCommentResponse;
import quyenvvph20946.fpl.geoteachapplication.model.response.ServerResponse;
import quyenvvph20946.fpl.geoteachapplication.ultil.AccountUltil;
import quyenvvph20946.fpl.geoteachapplication.ultil.TAG;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentActivity extends AppCompatActivity {

    private ActivityCommentBinding binding;
    private ProductDetail productDetail;
    private List<Comment> commentList;
    private CommentAdapter commentAdapter;
    SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCommentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initView();
        initController();
        getListComment();
    }

    private void initController() {
        binding.btnSend.setOnClickListener(view -> {
        });

        binding.imgBack.setOnClickListener(view -> {
            finish();
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
        });
    }

//    private void createComment() {
//        String token = AccountUltil.BEARER + AccountUltil.TOKEN;
//        String content = binding.edComment.getText().toString().trim();
//        binding.edComment.setText("");
//        BaseApi.API.createComment(token, productDetail.getId(), productDetail.getId(), AccountUltil.USER.getId(), content, 5).enqueue(new Callback<ServerResponse>() {
//            @Override
//            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
//                if(response.isSuccessful()){ // chỉ nhận đầu status 200
//                    ServerResponse serverResponse = response.body();
//                    Log.d(TAG.toString, "onResponse-createComment: " + serverResponse.toString());
//                    if(serverResponse.getCode() == 200 || serverResponse.getCode() == 201) {
//                        String userId = AccountUltil.USER.getId();
//                        String username = AccountUltil.USER.getUsername();
//                        String avatar = AccountUltil.USER.getAvatar();
//                        String datetime = sdf.format(new Date());
//
//                        User user = new User(avatar, username);
//                        Comment comment = new Comment(user, content, 5, datetime);
//                        commentList.add(comment);
//                        commentAdapter.notifyItemRangeInserted(commentList.size(), commentList.size());
//                        binding.rcvComment.smoothScrollToPosition(commentList.size() - 1);
//
//                        if(commentList.size() == 0) {
//                            binding.layoutDrum.setVisibility(View.VISIBLE);
//                        } else {
//                            binding.layoutDrum.setVisibility(View.GONE);
//                        }
//                    }
//                } else { // nhận các đầu status #200
//                    try {
//                        String errorBody = response.errorBody().string();
//                        JSONObject errorJson = new JSONObject(errorBody);
//                        String errorMessage = errorJson.getString("message");
//                        Log.d(TAG.toString, "onResponse-createComment: " + errorMessage);
//                        Toast.makeText(CommentActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
//                    }catch (IOException e){
//                        e.printStackTrace();
//                    } catch (JSONException e) {
//                        throw new RuntimeException(e);
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ServerResponse> call, Throwable t) {
//                Toast.makeText(CommentActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
//                Log.d(TAG.toString, "onFailure-createComment: " + t.toString());
//            }
//        });
//    }

    private void getListComment() {
        String token = AccountUltil.BEARER + AccountUltil.TOKEN;
        binding.progressBar.setVisibility(View.VISIBLE);
        BaseApi.API.getListComment(token, productDetail.getId()).enqueue(new Callback<ListCommentResponse>() {
            @Override
            public void onResponse(Call<ListCommentResponse> call, Response<ListCommentResponse> response) {
                if(response.isSuccessful()){ // chỉ nhận đầu status 200
                    ListCommentResponse listCommentResponse = response.body();
                    Log.d(TAG.toString, "onResponse-getListComment: " + listCommentResponse.toString());
                    if(listCommentResponse.getCode() == 200) {
                        commentList = listCommentResponse.getData();
                        commentAdapter.setCommentList(commentList);
                        binding.rcvComment.smoothScrollToPosition(commentList.size());

                        if(commentList.size() == 0) {
                            binding.layoutDrum.setVisibility(View.VISIBLE);
                        } else {
                            binding.layoutDrum.setVisibility(View.GONE);
                        }
                    }
                } else { // nhận các đầu status #200
                    try {
                        String errorBody = response.errorBody().string();
                        JSONObject errorJson = new JSONObject(errorBody);
                        String errorMessage = errorJson.getString("message");
                        Log.d(TAG.toString, "onResponse-getListComment: " + errorMessage);
                        Toast.makeText(CommentActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                    }catch (IOException e){
                        e.printStackTrace();
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
                binding.progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ListCommentResponse> call, Throwable t) {
                Toast.makeText(CommentActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
                Log.d(TAG.toString, "onFailure-getListComment: " + t.toString());
                binding.progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void initView() {
        productDetail = new ProductDetail();
        productDetail.setId(getIntent().getStringExtra("id_product"));
        productDetail.setName(getIntent().getStringExtra("name"));
        Log.d(TAG.toString, "setId: " + productDetail.getId());
        Log.d(TAG.toString, "setName: " + productDetail.getName());


        commentList = new ArrayList<>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(CommentActivity.this, LinearLayoutManager.VERTICAL, false);
        binding.rcvComment.setLayoutManager(layoutManager);
        commentAdapter = new CommentAdapter(CommentActivity.this, commentList);
        binding.rcvComment.setAdapter(commentAdapter);

        Glide.with(this)
                .load(AccountUltil.USER.getAvatar())
                .placeholder(R.drawable.loading)
                .error(R.drawable.avatar1)
                .into(binding.imgAvartar);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_right);
    }
}