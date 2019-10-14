package android.example.com.newdicodingmovie.reminder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreferenceCompat;

import android.content.SharedPreferences;
import android.example.com.newdicodingmovie.R;
import android.os.Bundle;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        setActionBarTitle(getResources().getString(R.string.reminder_setting));

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.reminder_fragment, new SettingFragment())
                .commit();

    }

    public static class SettingFragment extends PreferenceFragmentCompat implements Preference.OnPreferenceChangeListener {


        private SwitchPreferenceCompat switchDaily;
        private SwitchPreferenceCompat switchRelease;
        public static final String TIME_DAILY_REMINDER = "07:00";
        public static final String TIME_RELEASE_REMINDER = "08:00";
        public static final String REMINDER_DAILY = "daily_reminder";
        public static final String REMINDER_RELEASE = "release_reminder";
        private NotificationReceiver notificationReceiver;

        @Override
        public void onCreatePreferences(Bundle bundle, String s) {
            addPreferencesFromResource(R.xml.preferences);
            switchDaily =  findPreference(REMINDER_DAILY);
            switchRelease = findPreference(REMINDER_RELEASE);
            preferences();
            notificationReceiver = new NotificationReceiver();
            switchDaily.setOnPreferenceChangeListener(this);
            switchRelease.setOnPreferenceChangeListener(this);

        }

        public void preferences() {
            SharedPreferences sharedPreferences = getPreferenceManager().getSharedPreferences();
            switchDaily.setChecked(sharedPreferences.getBoolean(REMINDER_DAILY, false));
            switchRelease.setChecked(sharedPreferences.getBoolean(REMINDER_RELEASE, false));
        }

        @Override
        public boolean onPreferenceChange(Preference preference, Object o) {
            switch (preference.getKey()) {
                case REMINDER_DAILY:
                    if (o.equals(true)) {
                        notificationReceiver.setRepeatingAlarm(getContext(), TIME_DAILY_REMINDER, NotificationReceiver.DAILY_NOTIF, getResources().getString(R.string.notif_daily));
                    } else {
                        notificationReceiver.cancelAlarm(getContext(), NotificationReceiver.DAILY_NOTIF);
                    }
                    break;

                case REMINDER_RELEASE:
                    if (o.equals(true)) {
                        notificationReceiver.setRepeatingAlarm(getContext(), TIME_RELEASE_REMINDER, NotificationReceiver.RELEASE_NOTIF, null);
                    } else {
                        notificationReceiver.cancelAlarm(getContext(), NotificationReceiver.RELEASE_NOTIF);
                    }
                    break;
            }
            return true;
        }
    }

    private void setActionBarTitle(String title) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }
    }
}
