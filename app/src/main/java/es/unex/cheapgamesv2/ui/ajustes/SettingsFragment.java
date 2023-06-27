package es.unex.cheapgamesv2.ui.ajustes;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreference;

import es.unex.cheapgamesv2.MainActivityButtons;
import es.unex.cheapgamesv2.R;

public class SettingsFragment extends PreferenceFragmentCompat {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.OnSharedPreferenceChangeListener preferenceChangeListener;
    private boolean isDarkMode;
    private MainActivityButtons activity;
    private SwitchPreference switchPreference;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);

        sharedPreferences = getPreferenceManager().getSharedPreferences();
        switchPreference = findPreference("theme");
        isDarkMode = switchPreference.isChecked();

        switchPreference.setOnPreferenceChangeListener((preference, newValue) -> {
            isDarkMode = (boolean) newValue; // Actualizar el estado del modo oscuro
            int nightMode = isDarkMode ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO;
            AppCompatDelegate.setDefaultNightMode(nightMode);

            if (activity != null) {
                activity.setDarkMode(isDarkMode);
            }

            return true;
        });

        // Actualizar el tema oscuro al iniciar la aplicación
        int nightMode = isDarkMode ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO;
        AppCompatDelegate.setDefaultNightMode(nightMode);

        if (activity != null) {
            activity.setDarkMode(isDarkMode);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        activity = (MainActivityButtons) getActivity();
        switchPreference.setChecked(isDarkMode);
    }

    @Override
    public void onResume() {
        super.onResume();

        preferenceChangeListener = (sharedPreferences, key) -> {
            if (key.equals("theme")) {
                isDarkMode = sharedPreferences.getBoolean("theme", false);
                switchPreference.setChecked(isDarkMode);

                int nightMode = isDarkMode ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO;
                AppCompatDelegate.setDefaultNightMode(nightMode);

                if (activity != null) {
                    activity.setDarkMode(isDarkMode);
                }
            }
        };

        sharedPreferences.registerOnSharedPreferenceChangeListener(preferenceChangeListener);
    }

    @Override
    public void onPause() {
        super.onPause();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("theme", isDarkMode);
        editor.apply();
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(preferenceChangeListener);
    }
}


