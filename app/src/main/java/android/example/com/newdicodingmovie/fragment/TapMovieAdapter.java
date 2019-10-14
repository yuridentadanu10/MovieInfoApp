package android.example.com.newdicodingmovie.fragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class TapMovieAdapter  extends FragmentStatePagerAdapter {

    private static final int TAB_NUMBER = 2;

    public TapMovieAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }


    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0 :
                return new MovieFragment();
            case 1:
                return new TopRatedFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return TAB_NUMBER;
    }
}