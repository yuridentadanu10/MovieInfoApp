package android.example.com.newdicodingmovie.apiSource;

import java.util.Locale;

public class switchLanguage {

    public static String getCountry() {
        String country = Locale.getDefault().getCountry().toLowerCase();

        switch (country) {
            case "id":
                break;

            default:
                country = "en";
                break;
        }

        return country;
    }
}
