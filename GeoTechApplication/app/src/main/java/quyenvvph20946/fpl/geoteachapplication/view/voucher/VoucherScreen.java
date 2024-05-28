package quyenvvph20946.fpl.geoteachapplication.view.voucher;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import quyenvvph20946.fpl.geoteachapplication.R;
import quyenvvph20946.fpl.geoteachapplication.adapter.VoucherSCAdapter;
import quyenvvph20946.fpl.geoteachapplication.databinding.ActivityVoucherScreenBinding;
import quyenvvph20946.fpl.geoteachapplication.model.Voucher;

public class VoucherScreen extends AppCompatActivity {
    ActivityVoucherScreenBinding binding;
    List<Voucher> list;
    VoucherSCAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVoucherScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        list = new ArrayList<>();
        list.add(new Voucher("Miễn phí vận chuyển", "2tr" , "31/12/2024", "12%"));
        list.add(new Voucher("Giảm 5% với đơn hàng trên 200k", "2tr" , "31/12/2024", "5%"));
        list.add(new Voucher("Miễn phí vận chuyển", "2tr" , "31/12/2024", "12%"));
        list.add(new Voucher("Giảm 5% với đơn hàng trên 100k", "2tr" , "31/12/2024", "12%"));
        list.add(new Voucher("Giảm 5% với đơn hàng trên 100k", "2tr" , "31/12/2024", "12%"));
        list.add(new Voucher("Miễn phí vận chuyển", "2tr" , "31/12/2024", "12%"));
        list.add(new Voucher("Giảm 5% với đơn hàng trên 100k", "2tr" , "31/12/2024", "12%"));
        adapter = new VoucherSCAdapter(this, list);
        binding.rcvVoucherStore.setAdapter(adapter);
    }
}