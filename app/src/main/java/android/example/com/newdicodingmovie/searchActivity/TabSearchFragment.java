package android.example.com.newdicodingmovie.searchActivity;


import android.example.com.newdicodingmovie.base.BaseFragment;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.example.com.newdicodingmovie.R;

import com.google.android.material.tabs.TabLayout;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class TabSearchFragment extends BaseFragment implements TabLayout.OnTabSelectedListener {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    public  TabSearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_tab_search, container, false);
        tabLayout = view.findViewById(R.id.tab_layout);
        viewPager = view.findViewById(R.id.pager);
        setupTab();
        return view;
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        selectTab(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
    private void selectTab(int tabNumber) {
        Objects.requireNonNull(tabLayout.getTabAt(tabNumber)).select();
    }

    private void setupTab() {
        viewPager.setAdapter(new TabSearchAdapter(getFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);

        Objects.requireNonNull(tabLayout.getTabAt(0)).setText(R.string.movie);
        Objects.requireNonNull(tabLayout.getTabAt(1)).setText(R.string.tv_series);
        tabLayout.addOnTabSelectedListener(this);
    }
}
