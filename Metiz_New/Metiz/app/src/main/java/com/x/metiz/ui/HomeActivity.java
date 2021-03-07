package com.x.metiz.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.x.metiz.Base;
import com.x.metiz.R;
import com.x.metiz.databinding.ActivityHomeBinding;

public class HomeActivity extends Base {

    String[] tabLabels = new String[2];
    ActivityHomeBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        binding= DataBindingUtil.setContentView(this, R.layout.activity_home);

        setTabLayout();
        changeFrag(LocationFragment.newInstance(null), false,false);

    }

    public void changeTab(int position) {
        clearFragment();
        switch (position) {
            case 0:
                changeFrag(LocationFragment.newInstance(null), false, false);
                break;
            case 1:
                changeFrag(ListFragment.newInstance(null), false, false);
                break;
        }
    }

    private void setTabLayout() {
        tabLabels[0]="Map";
        tabLabels[1]="List";

        for (int i = 0; i <tabLabels.length ; i++) {
            binding.tabLayout.addTab(binding.tabLayout.newTab());
        }
        for (int i = 0; i < tabLabels.length; i++) {
            View view = getLayoutInflater().inflate(R.layout.tab_layout, null);
            TextView tabLabel = view.findViewById(R.id.tabLabel);
            tabLabel.setText(tabLabels[i]);
            tabLabel.setTextColor(getResources().getColor(R.color.white));
            binding.tabLayout.getTabAt(i).setCustomView(view);
        }
        binding.tabLayout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                changeTab(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });



    }
}