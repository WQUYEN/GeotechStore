package quyenvvph20946.fpl.geoteachapplication.view.find_product;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import quyenvvph20946.fpl.geoteachapplication.R;
import quyenvvph20946.fpl.geoteachapplication.adapter.ProductAdapter;
import quyenvvph20946.fpl.geoteachapplication.api.BaseApi;
import quyenvvph20946.fpl.geoteachapplication.databinding.FindProductBinding;
import quyenvvph20946.fpl.geoteachapplication.model.Product;
import quyenvvph20946.fpl.geoteachapplication.model.response.ProductResponse;
import quyenvvph20946.fpl.geoteachapplication.ultil.AccountUltil;
import quyenvvph20946.fpl.geoteachapplication.ultil.ObjectUtil;
import quyenvvph20946.fpl.geoteachapplication.ultil.ProgressLoadingDialog;
import quyenvvph20946.fpl.geoteachapplication.ultil.TAG;
import quyenvvph20946.fpl.geoteachapplication.view.product_screen.DetailProduct;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FindProduct extends AppCompatActivity implements ObjectUtil {
    private FindProductBinding binding;
    private ProgressLoadingDialog loadingDialog;
    private ProductAdapter productAdapter;

    private List<Product> productList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = FindProductBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        callApiGetListAllProducts();
        initView();
        initController();

    }

    private void initController() {

        //icon deleteText
        binding.deleteText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.etdFind.setText("");
            }
        });


        //icon back
        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //icon find




        binding.etdFind.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    performSearch();
                    return true;
                }
                return false;
            }
        });
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() > 0){
                    productAdapter.filterItem(s.toString());
                    binding.deleteText.setVisibility(View.VISIBLE);
                    binding.find.setVisibility(View.VISIBLE);
                    binding.filter.setVisibility(View.GONE);
                }else {
                    productAdapter.filterItem(s.toString());
                    binding.deleteText.setVisibility(View.GONE);
                    binding.find.setVisibility(View.VISIBLE);
                    binding.filter.setVisibility(View.GONE);
                }


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        binding.etdFind.addTextChangedListener(textWatcher);
        binding.recycleView.setAdapter(productAdapter);



    }
    private void performSearch() {
        String searchTerm = binding.etdFind.getText().toString();
        productAdapter.filterItem(searchTerm);
    }


    @SuppressLint("NotifyDataSetChanged")
    private  void initView(){
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        binding.recycleView.setLayoutManager(layoutManager);
        loadingDialog = new ProgressLoadingDialog(this);
        productList = new ArrayList<>();
        productAdapter = new ProductAdapter(this, productList, this);
        binding.recycleView.setAdapter(productAdapter);
    }


    public void callApiGetListAllProducts(){
//        loadingDialog.show();
        BaseApi.API.getListAllProduct(true, AccountUltil.TOKEN).enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                if (response.isSuccessful()) {
                    ProductResponse productResponse = response.body();
                    productAdapter.setProductList(productResponse.getResult());
                    binding.recycleView.setAdapter(productAdapter);
                } else {
                    Toast.makeText(getApplicationContext(), "call list all products err", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Err", Toast.LENGTH_SHORT).show();
//                loadingDialog.dismiss();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG.toString, "onResume: ");
    }



    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG.toString, "onStart: ");
    }

    @Override
    public void onclickObject(Object object) {
        Product product = (Product) object;
        String id = product.getId();
        String averageRate = String.valueOf(product.getAverageRate());
        String sellproduct = String.valueOf(product.getSoldQuantity());
        String reviewcount = String.valueOf(product.getReviewCount());
        Double minPrice = product.getMinPrice();
        Intent intent = new Intent(this, DetailProduct.class);
        intent.putExtra("id_product", id);
        intent.putExtra("sold_quantity",sellproduct);
        intent.putExtra("rating_start",averageRate);
        intent.putExtra("review_count",reviewcount);
        intent.putExtra("minPrice",minPrice);
        this.startActivity(intent);
        this.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
    }
}
