package it.salmanapp.sitemonitor.ui.settings

import android.os.Bundle
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import it.salmanapp.sitemonitor.R
import it.salmanapp.sitemonitor.workers.WorkManagerUtils

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)

    }

    override fun onPreferenceTreeClick(preference: Preference?): Boolean {
        when(preference?.key){
                "intervals_list"->{
                    val workManagerUtils=WorkManagerUtils(requireContext())
                    workManagerUtils.createUpdateWork()
                }
        }

        return super.onPreferenceTreeClick(preference)
    }
}