package com.example.ece493assignment1;

import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.view.Menu;
import android.widget.NumberPicker;
import android.widget.Toast;

public class SettingsActivity extends PreferenceActivity implements OnSharedPreferenceChangeListener
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.layout.activity_settings);
	}

	@Override
	protected void onResume() {
	    super.onResume();
	    // Set up a listener whenever a key changes
	    Preference pref = findPreference("Filter Size");
	    if (pref instanceof EditTextPreference) {
			EditTextPreference textPref = (EditTextPreference) pref;
			
			pref.setSummary(textPref.getText());
		}
	    
	    getPreferenceScreen().getSharedPreferences()
	            .registerOnSharedPreferenceChangeListener(this);
	}

	@Override
	protected void onPause() {
	    super.onPause();
	    // Unregister the listener whenever a key changes
	    getPreferenceScreen().getSharedPreferences()
	            .unregisterOnSharedPreferenceChangeListener(this);
	}
	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
			String key)
	{
		// TODO Auto-generated method stub

		Preference pref = findPreference(key);
		if (pref instanceof EditTextPreference) {
			EditTextPreference textPref = (EditTextPreference) pref;
			Integer i = Integer.parseInt(textPref.getText());
			if(i%2==0)
			{
				i++;
				textPref.setText(i.toString());
			}
			pref.setSummary(textPref.getText());
		}
	}

}
