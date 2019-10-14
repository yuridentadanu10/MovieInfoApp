package android.example.com.newdicodingmovie;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.content.Intent;
import android.example.com.newdicodingmovie.base.BaseAppCompatActivity;
import android.example.com.newdicodingmovie.fragment.MixFavoriteFragment;
import android.example.com.newdicodingmovie.fragment.SeriesFragment;
import android.example.com.newdicodingmovie.fragment.TabMovieFragment;
import android.example.com.newdicodingmovie.reminder.SettingActivity;
import android.example.com.newdicodingmovie.searchActivity.TabSearchFragment;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends BaseAppCompatActivity {

    private Fragment pageContent = new TabMovieFragment();
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView mBottomNav=findViewById(R.id.navbottom);
        mBottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {


            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.movie:
                        pageContent = new TabMovieFragment();
                        break;
                    case R.id.topRated:
                        pageContent = new TabSearchFragment();
                        break;
                    case R.id.series_tv:
                        pageContent = new SeriesFragment();
                        break;

                    case R.id.favorite:
                        pageContent = new MixFavoriteFragment();
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.Layout_frame, pageContent).commit();
                return true;
            }
        });
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.Layout_frame, pageContent).commit();
        }
        else {
            pageContent = getSupportFragmentManager().getFragment(savedInstanceState, KEY_FRAGMENT);
            getSupportFragmentManager().beginTransaction().replace(R.id.Layout_frame, pageContent).commit();
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.menuLanguage){
            Intent mIntent =new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(mIntent);
        }else {
            if (item.getItemId() == R.id.settingNotif) {
                Intent intent = new Intent(this, SettingActivity.class );
                startActivity(intent);
            }
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        getSupportFragmentManager().putFragment(outState, KEY_FRAGMENT, pageContent);
        super.onSaveInstanceState(outState);
    }
}
