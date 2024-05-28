package quyenvvph20946.fpl.geoteachapplication.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import quyenvvph20946.fpl.geoteachapplication.R;
import quyenvvph20946.fpl.geoteachapplication.adapter.NotificationAdapter;
import quyenvvph20946.fpl.geoteachapplication.api.BaseApi;
import quyenvvph20946.fpl.geoteachapplication.databinding.FragmentNotificationBinding;
import quyenvvph20946.fpl.geoteachapplication.model.Notifi;
import quyenvvph20946.fpl.geoteachapplication.model.response.ListNotifiReponse;
import quyenvvph20946.fpl.geoteachapplication.ultil.AccountUltil;
import quyenvvph20946.fpl.geoteachapplication.ultil.CartUtil;
import quyenvvph20946.fpl.geoteachapplication.ultil.NotificationUtil;
import quyenvvph20946.fpl.geoteachapplication.ultil.TAG;
import quyenvvph20946.fpl.geoteachapplication.view.cart.CartActivity;
import quyenvvph20946.fpl.geoteachapplication.view.chat_message.ChatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FragmentNotification extends Fragment {

    private FragmentNotificationBinding binding;
    private NotificationAdapter notificationAdapter;
    private List<Notifi> notifiList;

    SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");

    // socket để thao tác
    private Socket mSocket;
    {
        try {
            mSocket = IO.socket("http://" + BaseApi.LOCALHOT +":3000");
        } catch (URISyntaxException e) {}
    }


    public FragmentNotification() {
        // Required empty public constructor
    }

    public static FragmentNotification newInstance() {
        FragmentNotification fragment = new FragmentNotification();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentNotificationBinding.inflate(getLayoutInflater());
        initView();
        initController();
        getListNotify();
        initSocket();
        setNumberCart();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SwipeRefreshLayout swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initView();
                initController();
                getListNotify();
                initSocket();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

    }

    private void initSocket() {
        mSocket.connect();
        mSocket.on("new_notification", onNewNotification);
    }
    private Emitter.Listener onNewNotification = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            if(getActivity() == null) {
                return;
            }
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        JSONObject data = (JSONObject) args[0];
                        String content = data.getString("content");
                        NotificationUtil.sendNotification(getActivity(),content);

                    } catch (JSONException e) {
                        throw  new RuntimeException(e);
                    }
                }
            });
        }
    };

    private void getListNotify() {
        String token = AccountUltil.BEARER + AccountUltil.TOKEN;
        String userId = AccountUltil.USER.getId();
        binding.progressBar.setVisibility(View.VISIBLE);
        BaseApi.API.getNotifiList(token, userId).enqueue(new Callback<ListNotifiReponse>() {
            @Override
            public void onResponse(Call<ListNotifiReponse> call, Response<ListNotifiReponse> response) {
                if(response.isSuccessful()){ // chỉ nhận đầu status 200
                    ListNotifiReponse listNotifiReponse = response.body();
                    Log.d(TAG.toString, "onResponse-getNotifiList: " + listNotifiReponse.toString());
                    if(listNotifiReponse.getCode() == 200 || listNotifiReponse.getCode() == 201) {
                        notifiList = listNotifiReponse.getResult();
                        notificationAdapter.setNotifiList(notifiList);
                    }
                } else { // nhận các đầu status #200
                    try {
                        String errorBody = response.errorBody().string();
                        JSONObject errorJson = new JSONObject(errorBody);
                        String errorMessage = errorJson.getString("message");
                        Log.d(TAG.toString, "onResponse-getNotifiList: " + errorMessage);
                        Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT).show();
                    }catch (IOException e){
                        e.printStackTrace();
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
                binding.progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ListNotifiReponse> call, Throwable t) {
                Toast.makeText(getActivity(), t.toString(), Toast.LENGTH_SHORT).show();
                Log.d(TAG.toString, "onFailure-getNotifiList: " + t.toString());
                binding.progressBar.setVisibility(View.GONE);
            }
        });
    }
    private void initController() {

        binding.imgCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CartActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
            }
        });
    }
    private void setNumberCart() {
        // Lấy danh sách cart
        binding.tvQuantityCart.setText(CartUtil.listCart.size() + "");
    }
    public  void initView(){
        Log.d("cccccccccccccccc", "initView: +vao day");
        notifiList = new ArrayList<>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        binding.rcvNofity.setLayoutManager(layoutManager);
        notificationAdapter = new NotificationAdapter(getActivity(), notifiList);
        binding.rcvNofity.setAdapter(notificationAdapter);
    }



}