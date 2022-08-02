package com.skyqi.module_home.activity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.google.android.material.navigation.NavigationBarView;
import com.skyqi.module_base.route.RouteBase;
import com.skyqi.module_base.route.RouteManager;
import com.skyqi.module_base.view.activity.BaseViewModelActivity;
import com.skyqi.module_home.R;
import com.skyqi.module_home.databinding.ActivityHomeBinding;
import com.skyqi.module_home.fragment.MenuProductionFragment;
import com.skyqi.module_home.adapter.HomeContainerAdapter;
import com.skyqi.module_home.view_model.HomeViewModel;
import com.skyqi.module_message.list.fragment.MessageListFragment;

import java.util.ArrayList;
import java.util.List;

@Route(path = RouteBase.HOME)
public class HomeActivity extends BaseViewModelActivity {

    private ActivityHomeBinding mActivityHomeBinding;

    private HomeViewModel mHomeViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityHomeBinding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(mActivityHomeBinding.getRoot());
        initViewPager();
        mActivityHomeBinding.bnvHomeNavigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.menu_item_home_index) {
                    mHomeViewModel.postMenuIndex(0);
                } else if(item.getItemId() == R.id.menu_item_home_message) {
                    mHomeViewModel.postMenuIndex(1);
                } else {
                    mHomeViewModel.postMenuIndex(2);
                }
                return true;
            }
        });
        mHomeViewModel.getMenuIndexLiveData().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                mActivityHomeBinding.vpHomeContainer.setCurrentItem(integer);
            }
        });
    }

    private void initViewPager() {
        mActivityHomeBinding.vpHomeContainer.setUserInputEnabled(false);
        HomeContainerAdapter adapter = new HomeContainerAdapter(this);
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(MenuProductionFragment.newInstance());
        fragmentList.add(MessageListFragment.newInstance());
        fragmentList.add(MenuProductionFragment.newInstance());
        adapter.setFragmentList(fragmentList);
        mActivityHomeBinding.vpHomeContainer.setAdapter(adapter);
    }

    @Override
    protected void initViewModel() {
        mHomeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            TextView tv;
            if(System.currentTimeMillis() - mHomeViewModel.clickTime > 2000L) {
                mHomeViewModel.clickTime = System.currentTimeMillis();
                Toast.makeText(this, "再按一次退出", Toast.LENGTH_LONG).show();
                return false;
            } else {
                RouteManager.getInstance().clearAllActivity();
                return true;
            }
        } else {
            return super.onKeyDown(keyCode, event);
        }

    }
}