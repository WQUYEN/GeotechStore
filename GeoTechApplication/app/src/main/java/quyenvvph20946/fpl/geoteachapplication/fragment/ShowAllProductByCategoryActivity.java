package quyenvvph20946.fpl.geoteachapplication.fragment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import quyenvvph20946.fpl.geoteachapplication.R;
import quyenvvph20946.fpl.geoteachapplication.adapter.ProductAdapter;
import quyenvvph20946.fpl.geoteachapplication.databinding.ActivityShowAllProductByCategoryBinding;
import quyenvvph20946.fpl.geoteachapplication.model.Product;
import quyenvvph20946.fpl.geoteachapplication.model.ProductByCategory;
import quyenvvph20946.fpl.geoteachapplication.ultil.ObjectUtil;
import quyenvvph20946.fpl.geoteachapplication.ultil.ProgressLoadingDialog;
import quyenvvph20946.fpl.geoteachapplication.view.product_screen.DetailProduct;

public class ShowAllProductByCategoryActivity extends AppCompatActivity implements ObjectUtil {
    ActivityShowAllProductByCategoryBinding binding;
    List<Product> list;
    ProductAdapter adapter;
    private ProgressLoadingDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityShowAllProductByCategoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        dialog = new ProgressLoadingDialog(this);
        list = new ArrayList<>();
        binding.tvNameCategory.setText("Giá : 499k");
        adapter = new ProductAdapter(this,list,this);
        binding.rcvAllProductByCategory.setAdapter(adapter);
        ShowListAllProductByCategory();
        initController();
    }

    private void ShowListAllProductByCategory() {
        dialog.show();
        Intent intent = getIntent();
        ProductByCategory categoryId = (ProductByCategory) intent.getSerializableExtra("categoryId");
        Log.d("categoryId", "ShowListAllProductByCategory: "+ categoryId);
        binding.tvNameCategory.setText(categoryId.getNameCategory());
        adapter.setProductList(categoryId.getProduct());
        binding.rcvAllProductByCategory.setAdapter(adapter);
        dialog.dismiss();

    }

    private void initController() {
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void onclickObject(Object object) {
        Product product = (Product) object;
        String id = product.getId();
        String averageRate = String.valueOf(product.getAverageRate());
        String sellproduct = String.valueOf(product.getSoldQuantity());
        String reviewcount = String.valueOf(product.getReviewCount());
        Intent intent = new Intent(this, DetailProduct.class);
        intent.putExtra("id_product", id);
        intent.putExtra("sold_quantity",sellproduct);
        intent.putExtra("rating_start",averageRate);
        intent.putExtra("review_count",reviewcount);
        Log.d("rating_start", "onclickObject: "+averageRate);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
    }
}