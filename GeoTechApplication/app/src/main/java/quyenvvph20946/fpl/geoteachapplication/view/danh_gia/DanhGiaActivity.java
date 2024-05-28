package quyenvvph20946.fpl.geoteachapplication.view.danh_gia;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import quyenvvph20946.fpl.geoteachapplication.R;

public class DanhGiaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_gia);

        DialogRate dialogRate = new DialogRate(DanhGiaActivity.this);
        dialogRate.getWindow().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.transparent)));
        dialogRate.setCancelable(false);
        dialogRate.show();
    }
}