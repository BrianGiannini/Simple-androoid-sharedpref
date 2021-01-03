/*
 * Copyright (C) 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.hellosharedprefs

import android.content.SharedPreferences
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*

private const val COUNT_KEY = "count" // Key for current count

private const val COLOR_KEY = "color" // Key for current color

/**
 * HelloSharedPrefs is an adaptation of the HelloToast app from chapter 1.
 * It includes:
 * - Buttons for changing the background color.
 * - Maintenance of instance state.
 * - Themes and styles.
 * - Read and write shared preferences for the current count and the color.
 *
 *
 * This is the solution code for HelloSharedPrefs.
 */
class MainActivity : AppCompatActivity() {
    // Current count
    private var count = 0

    // Current background color
    private var color = 0

    // Shared preferences object
    private lateinit var preferences: SharedPreferences

    // Name of shared preferences file
    private val sharedPrefFile = "com.example.android.hellosharedprefs"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize views, color, preferences
        color = ContextCompat.getColor(this, R.color.default_background)
        preferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE)

        // Restore preferences
        count = preferences.getInt(COUNT_KEY, 0)
        count_textview.text = String.format("%s", count)
        color = preferences.getInt(COLOR_KEY, color)
        count_textview.setBackgroundColor(color)
    }

    /**
     * Handles the onClick for the background color buttons.  Gets background
     * color of the button that was clicked, and sets the TextView background
     * to that color.
     *
     * @param view The view (Button) that was clicked.
     */
    fun changeBackground(view: View) {
        val color = (view.background as ColorDrawable).color
        count_textview.setBackgroundColor(color)
        this.color = color
    }

    /**
     * Handles the onClick for the Count button.  Increments the value of the
     * mCount global and updates the TextView.
     *
     * @param view The view (Button) that was clicked.
     */
    fun countUp(view: View?) {
        count++
        count_textview.text = String.format("%s", count)
    }

    /**
     * Handles the onClick for the Reset button.  Resets the global count and
     * background variables to the defaults and resets the views to those
     * default values.
     *
     * @param view The view (Button) that was clicked.
     */
    fun reset(view: View?) {
        // Reset count
        count = 0
        count_textview.text = String.format("%s", count)

        // Reset color
        color = ContextCompat.getColor(this, R.color.default_background)
        count_textview.setBackgroundColor(color)

        // Clear preferences
        val preferencesEditor = preferences.edit()
        preferencesEditor.clear()
        preferencesEditor.apply()
    }

    /**
     * Callback for activity pause.  Shared preferences are saved here.
     */
    override fun onPause() {
        super.onPause()
        val preferencesEditor = preferences.edit()
        preferencesEditor.putInt(COUNT_KEY, count)
        preferencesEditor.putInt(COLOR_KEY, color)
        preferencesEditor.apply() // apply: asynchronous, commit: synchronous
    }
}