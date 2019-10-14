package android.example.com.newdicodingmovie.searchActivity;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class TabSearchAdapter extends FragmentStatePagerAdapter {
    private static final int NUMBER_ITEM = 2;


    TabSearchAdapter(FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0 :
                return new SearchFragment();
            case 1:
                return new SearchSeriesFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return NUMBER_ITEM;
    }
}