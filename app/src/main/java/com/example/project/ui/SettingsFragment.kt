package com.example.project

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.google.android.material.switchmaterial.SwitchMaterial

class SettingsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_settings, container, false)
        val prefs = requireActivity().getSharedPreferences("Settings", Context.MODE_PRIVATE)

        val switchTheme = view.findViewById<SwitchMaterial>(R.id.switchTheme)
        val rgLanguage = view.findViewById<RadioGroup>(R.id.rgLanguage)
        val rbRussian = view.findViewById<RadioButton>(R.id.rbRussian)
        val rbEnglish = view.findViewById<RadioButton>(R.id.rbEnglish)

        val isDarkMode = prefs.getBoolean("dark_mode", false)
        switchTheme.isChecked = isDarkMode

        switchTheme.setOnCheckedChangeListener { _, isChecked ->
            prefs.edit().putBoolean("dark_mode", isChecked).apply()
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        val currentLang = prefs.getString("lang", "ru")
        if (currentLang == "ru") rbRussian.isChecked = true else rbEnglish.isChecked = true

        rgLanguage.setOnCheckedChangeListener { _, checkedId ->
            val lang = if (checkedId == R.id.rbRussian) "ru" else "en"
            if (lang != currentLang) {
                prefs.edit().putString("lang", lang).apply()
                val intent = Intent(requireContext(), MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                requireActivity().finish()
            }
        }

        return view
    }
}