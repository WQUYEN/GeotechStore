package quyenvvph20946.fpl.geoteachapplication.fragment.homescreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import quyenvvph20946.fpl.geoteachapplication.R;
import quyenvvph20946.fpl.geoteachapplication.databinding.ActivityMainBinding;
import quyenvvph20946.fpl.geoteachapplication.fragment.FragmentHome;
import quyenvvph20946.fpl.geoteachapplication.fragment.FragmentNotification;
import quyenvvph20946.fpl.geoteachapplication.fragment.FragmentProduct;
import quyenvvph20946.fpl.geoteachapplication.fragment.FragmentProfile;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        onClickBottomNav();
    }

    private void onClickBottomNav() {
        binding.bottomNavigation.add(new MeowBottomNavigation.Model(1,R.drawable.homepage));
        binding.bottomNavigation.add(new MeowBottomNavigation.Model(2,R.drawable.product_24));
        binding.bottomNavigation.add(new MeowBottomNavigation.Model(3,R.drawable.thongbao));
        binding.bottomNavigation.add(new MeowBottomNavigation.Model(4,R.drawable.profile));
        binding.bottomNavigation.show(1,true);
        loadFragment(FragmentHome.newInstance());

        binding.bottomNavigation.setOnClickMenuListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model) {
                switch (model.getId()) {
                    case 1:
                        loadFragment(FragmentHome.newInstance());
                        break;
                    case 2:
                        loadFragment(FragmentProduct.newInstance());
                        break;
                    case 3:
                        loadFragment(FragmentNotification.newInstance());
                        break;
                    case 4:
                        loadFragment(FragmentProfile.newInstance());
                        break;
                }
                return null;
            }
        });
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.framelayout,fragment);
        transaction.commit();
    }
}