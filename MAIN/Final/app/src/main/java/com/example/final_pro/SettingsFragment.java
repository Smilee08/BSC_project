package com.example.final_pro;


import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.ListPreference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

public class SettingsFragment extends PreferenceFragmentCompat {


    @Override
    public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
    setPreferencesFromResource(R.xml.preference,rootKey);
    }
    public void Load_Settings(){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getContext());


        ListPreference LPC = findPreference("night");
        String dark=sp.getString("night","false");
        if("default".equals(dark)){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
            LPC.setSummary(LPC.getEntry());
        }else if("light".equals(dark)){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            LPC.setSummary(LPC.getEntry());
        }else if("dark".equals(dark)){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            LPC.setSummary(LPC.getEntry());
        }
        LPC.setOnPreferenceChangeListener((preference, newValue) -> {
            String theme=(String) newValue;
            if (preference.getKey().equals("night")){

                switch (theme){
                    case "default":
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                        break;
                    case "light":
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                        break;
                    case "dark":
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                        break;
                }
                ListPreference LPPC=(ListPreference) preference;
                LPPC.setSummary(LPPC.getEntries()[LPPC.findIndexOfValue(theme)]);
            }
            return true;
        });


        ListPreference LP = findPreference("ORIENTATION");
        String orien=sp.getString("ORIENTATION","false");
        if("1".equals(orien)){
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_USER);
            LP.setSummary(LP.getEntry());
        }else if("2".equals(orien)){
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            LP.setSummary(LP.getEntry());
        }else if("3".equals(orien)){
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            LP.setSummary(LP.getEntry());
        }
        LP.setOnPreferenceChangeListener((preference, newValue) -> {
            String items=(String) newValue;
            if (preference.getKey().equals("ORIENTATAION")){
                switch (items){
                    case "1":
                        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_USER);
                        break;
                    case "2":
                        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                        break;
                    case "3":
                        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                        break;

                }
                ListPreference LPP=(ListPreference) preference;
                LPP.setSummary(LPP.getEntries()[LPP.findIndexOfValue(items)]);
            }
            return true;
        });

    }

    @Override
    public void onResume() {
        Load_Settings();
        super.onResume();
    }
}
