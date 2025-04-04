package quyenvvph20946.fpl.geoteachapplication.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import quyenvvph20946.fpl.geoteachapplication.R;
import quyenvvph20946.fpl.geoteachapplication.api.BaseApi;
import quyenvvph20946.fpl.geoteachapplication.databinding.LayoutItemProductBinding;
import quyenvvph20946.fpl.geoteachapplication.model.OptionProduct;
import quyenvvph20946.fpl.geoteachapplication.model.Product;
import quyenvvph20946.fpl.geoteachapplication.model.response.DetailProductResponse;
import quyenvvph20946.fpl.geoteachapplication.ultil.ObjectUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavouriteProductAdapter extends RecyclerView.Adapter<FavouriteProductAdapter.ProductViewHolder>{

    private Context context;
    private List<Product> productList;
    private List<Product> filteredItems;
    private ObjectUtil objectUtil;

    public FavouriteProductAdapter(Context context, List<Product> productList, ObjectUtil objectUtil) {
        this.context = context;
        this.productList = productList;
        this.objectUtil = objectUtil;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
        this.filteredItems = new ArrayList<>(productList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutItemProductBinding binding = LayoutItemProductBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ProductViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.binding.tvName.setText(product.getName());
        DecimalFormat df = new DecimalFormat("###,###,###");
        holder.binding.tvPrice.setText(df.format(product.getMinPrice()) + " đ");
        Glide.with(context)
                .load(product.getImage())
                .placeholder(R.drawable.loading)
                .error(R.drawable.error)
                .into(holder.binding.imgProduct);
        holder.binding.ratingBar.setRating((float) product.getAverageRate());
        BaseApi.API.getDetailProduct(product.getId()).enqueue(new Callback<DetailProductResponse>() {
            @Override
            public void onResponse(Call<DetailProductResponse> call, Response<DetailProductResponse> response) {
                if(response.isSuccessful()){
                    DetailProductResponse detailProductResponse = response.body();
                    List<OptionProduct> listoption =    detailProductResponse.getResult().getOption();
                    double maxDiscount = 0;
                    for(OptionProduct optionProduct: listoption)
                    {
                        if (optionProduct.getDiscountValue() > maxDiscount) {
                            maxDiscount = optionProduct.getDiscountValue();
                        }
                    }
                    if(maxDiscount>0){
                        holder.binding.txtDiscount.setVisibility(View.VISIBLE);
                        holder.binding.txtDiscount.setText("-"+(maxDiscount)+"%");
                    }

                }else{
                    Toast.makeText(context, "Không có dữ liệu detail", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<DetailProductResponse> call, Throwable t) {
                Log.d("checkloiiiiiiii", "onFailure: "+t);

            }
        });

        try {
            holder.binding.tvReview.setText("Đã bán "+product.getSoldQuantity());
        } catch (Exception ex) {
            holder.binding.tvReview.setText("Đã bán "+0);
        }

        holder.binding.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                objectUtil.onclickObject(product);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(productList != null) {
            return productList.size();
        }
        return 0;
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder{
        private LayoutItemProductBinding binding;
        public ProductViewHolder(LayoutItemProductBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
    @SuppressLint("NotifyDataSetChanged")
    public void filterItem(String query) {
        productList.clear();
        if(query.isEmpty()) {
            productList.addAll(filteredItems);
        } else {
            for (Product item : filteredItems) {
                if (item.getName().toLowerCase().contains(query.toLowerCase())){
                    productList.add(item);
                }
            }
        }
        notifyDataSetChanged();
    }
}
