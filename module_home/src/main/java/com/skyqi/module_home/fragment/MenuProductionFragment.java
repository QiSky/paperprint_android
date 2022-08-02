package com.skyqi.module_home.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.skyqi.module_home.R;

public class MenuProductionFragment extends Fragment {

    public MenuProductionFragment() {

    }

    public static MenuProductionFragment newInstance() {
        MenuProductionFragment fragment = new MenuProductionFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_menu_production, container, false);
    }
}