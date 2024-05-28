package quyenvvph20946.fpl.geoteachapplication.adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import quyenvvph20946.fpl.geoteachapplication.R;
import quyenvvph20946.fpl.geoteachapplication.api.BaseApi;
import quyenvvph20946.fpl.geoteachapplication.databinding.LayoutItemOrderBinding;
import quyenvvph20946.fpl.geoteachapplication.model.Order;
import quyenvvph20946.fpl.geoteachapplication.model.response.ListComment1Response;
import quyenvvph20946.fpl.geoteachapplication.model.response.ServerResponse;
import quyenvvph20946.fpl.geoteachapplication.ultil.AccountUltil;
import quyenvvph20946.fpl.geoteachapplication.ultil.ObjectUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class notifiAdapter extends RecyclerView.Adapter<notifiAdapter.OrderViewHolder> {


    private List<Order> orderList;
    private Context context;
    private OrderProductAdapter orderProductAdapter;
    private ObjectUtil objectUtil;
    private int status = 0; // 1, 2, 3, 4 ứng với thứ tự hiển thị trên tab

    public notifiAdapter( Context context, List<Order> orderList, ObjectUtil objectUtil, int status) {
        this.context = context;
        this.orderList = orderList;
        this.objectUtil = objectUtil;
        this.status = status;
    }

    public void setListOrder(List<Order> orderList) {
        this.orderList = orderList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public notifiAdapter.OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutItemOrderBinding binding = LayoutItemOrderBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new notifiAdapter.OrderViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {

    }

    public void onBindViewHolder(@NonNull OrderAdapter.OrderViewHolder holder, int position) {
        Order order = orderList.get(position);
        List<String> listName = new ArrayList<>();
        List<String> listidProduct = new ArrayList<>();
        for(int i=0;i<order.getProductsOrder().size();i++){
            listName.add(order.getProductsOrder().get(i).getOptionProduct().getProduct().getName());
            listidProduct.add(order.getProductsOrder().get(i).getOptionProduct().getProduct().getId());

        }
        setRcvProduct(holder.binding, order);
        holder.binding.tvOrderId.setText("Đơn hàng: " + order.getId());
        DecimalFormat df = new DecimalFormat("###,###,###");
        holder.binding.tvTotalPrice.setText(df.format(order.getTotalPrice()) + "");
        holder.binding.tvStatus.setText(order.getStatus());
        holder.binding.tvQuantityTypeProduct.setText(order.getProductsOrder().size() + " loại sản phẩm");
        if(status == 1) {
            holder.binding.btnItem.setText("Hủy hàng");
            int color = Color.parseColor("#FFCC00");
            holder.binding.tvStatus.setTextColor(color);
        } else if(status == 2) {
            holder.binding.btnItem.setVisibility(View.GONE);
        } else if(status == 3) {
            holder.binding.btnItem.setText("Mua lại");
            holder.binding.btnreview.setVisibility(View.VISIBLE);

        } else if(status == 4) {
            holder.binding.btnItem.setText("Mua lại");
            holder.binding.tvStatus.setTextColor(Color.GRAY);
        }

        holder.binding.btnItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                objectUtil.onclickObject(order);
            }
        });
        holder.binding.btnreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tạo dialog
                Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.layout_modal_review);
                Spinner spinner = dialog.findViewById(R.id.spinnerName);
                ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, listName);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter);
                RatingBar ratingBar = dialog.findViewById(R.id.ratingBar);
                EditText commentEditText = dialog.findViewById(R.id.edt_commentReview);
                EditText commentName = dialog.findViewById(R.id.edt_commentName);
                Button postButton = dialog.findViewById(R.id.btnPost);
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        commentName.setText("");
                        commentEditText.setText("");
                        ratingBar.setRating(5);
                        String productid= listidProduct.get(position);
                        Log.d("test gọi dữ liệu", "prduct"+productid+"order"+order.getId()+"user"+ AccountUltil.USER.getId());

                        BaseApi.API.getReview(productid,order.getId(),AccountUltil.USER.getId()).enqueue(new Callback<ListComment1Response>() {
                            @Override
                            public void onResponse(Call<ListComment1Response> call, Response<ListComment1Response> response) {
                                if (response.isSuccessful() && response.body() != null) {
                                    ListComment1Response listCommentResponse = response.body();
                                    if (listCommentResponse.getData() != null) {
                                        Toast.makeText(adapter.getContext(), "Bạn Đã Đánh Giá Sản Phẩm Này,Vui Lòng Sửa", Toast.LENGTH_SHORT).show();
                                        ratingBar.setRating(listCommentResponse.getData().getRate());
                                        commentName.setText(listCommentResponse.getData().getName());
                                        commentEditText.setText(listCommentResponse.getData().getContent());
                                        postButton.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                String token = AccountUltil.BEARER + AccountUltil.TOKEN;
                                                String comment = commentEditText.getText().toString();
                                                String name = commentName.getText().toString();
                                                float rating = ratingBar.getRating();
                                                int selectedPosition = spinner.getSelectedItemPosition();
                                                String productid= listidProduct.get(selectedPosition);
                                                BaseApi.API.updateComment(token,listCommentResponse.getData().getId(),productid,order.getId(),AccountUltil.USER.getId(),comment, (int) rating).enqueue(new Callback<ServerResponse>() {
                                                    @Override
                                                    public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                                                        Toast.makeText(context, "Sửa Đánh Giá Thành Công", Toast.LENGTH_SHORT).show();
                                                    }
                                                    @Override
                                                    public void onFailure(Call<ServerResponse> call, Throwable t) {
                                                    }
                                                });

                                                dialog.dismiss();
                                            }
                                        });
                                    } else {


                                    }
                                } else {
                                    Log.d("test gọi dữ liệu", "Response không thành công hoặc body là null");
                                    Log.d("test gọi dữ liệu", "onItemSelected: + đã vào hàm thay đổi1");

                                    postButton.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            String token = AccountUltil.BEARER + AccountUltil.TOKEN;
                                            String comment = commentEditText.getText().toString();
                                            String name = commentName.getText().toString();
                                            float rating = ratingBar.getRating();
                                            BaseApi.API.createComment(token,productid,productid,order.getId(),AccountUltil.USER.getId(),comment,name, (int) rating).enqueue(new Callback<ServerResponse>() {
                                                @Override
                                                public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                                                    Toast.makeText(context, "Đánh Giá Thành Công", Toast.LENGTH_SHORT).show();
                                                }
                                                @Override
                                                public void onFailure(Call<ServerResponse> call, Throwable t) {
                                                }
                                            });

                                            dialog.dismiss();
                                        }
                                    });
                                }
                            }

                            @Override
                            public void onFailure(Call<ListComment1Response> call, Throwable t) {
                                Log.d("bugg get d", "onFailure: "+t);
                            }
                        });
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });


                // Hiển thị dialog
                dialog.show();
            }
        });

    }
    private void setRcvProduct(LayoutItemOrderBinding binding, Order order) {
        orderProductAdapter = new OrderProductAdapter(context, order.getProductsOrder());
        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        binding.rcvOrderDetail.setLayoutManager(layoutManager);
        binding.rcvOrderDetail.setAdapter(orderProductAdapter);
    }

    @Override
    public int getItemCount() {
        if(orderList != null){
            return  orderList.size();
        }
        return 0;
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder {
        LayoutItemOrderBinding binding;
        public OrderViewHolder(LayoutItemOrderBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}