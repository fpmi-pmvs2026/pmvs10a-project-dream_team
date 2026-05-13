package com.example.project

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.switchmaterial.SwitchMaterial

class SettingsFragment : Fragment(R.layout.fragment_settings) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val prefs = requireActivity().getSharedPreferences("Settings", Context.MODE_PRIVATE)

        // Тема
        val switchTheme = view.findViewById<SwitchMaterial>(R.id.switchTheme)
        val isDarkMode = prefs.getBoolean("Dark_Mode", false)
        switchTheme.isChecked = isDarkMode

        switchTheme.setOnCheckedChangeListener { _, isChecked ->
            prefs.edit().putBoolean("Dark_Mode", isChecked).apply()
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        // Язык
        val btnEn = view.findViewById<MaterialButton>(R.id.btnLangEn)
        val btnRu = view.findViewById<MaterialButton>(R.id.btnLangRu)

        btnEn.setOnClickListener { setLocale("en") }
        btnRu.setOnClickListener { setLocale("ru") }
    }

    private fun setLocale(lang: String) {
        val prefs = requireActivity().getSharedPreferences("Settings", Context.MODE_PRIVATE)
        prefs.edit().putString("My_Lang", lang).apply()

        val intent = Intent(requireContext(), MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }
}