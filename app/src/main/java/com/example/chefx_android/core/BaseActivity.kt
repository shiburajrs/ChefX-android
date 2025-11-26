package com.example.chefx_android.core

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

abstract class BaseActivity : AppCompatActivity(), LifecycleObserver {

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)
        lifecycle.addObserver(this)
        setup()
    }

    /**
     * Called after onCreate() for initialization setup.
     */
    protected open fun setup() {}

    /**
     * Fix top and bottom insets for toolbar/content
     * @param rootView The root layout of your activity
     */
    protected fun enableEdgeToEdgeInsets(rootView: View) {
        ViewCompat.setOnApplyWindowInsetsListener(rootView) { view, windowInsets ->
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.updatePadding(
                top = insets.top,
                bottom = insets.bottom
            )
            WindowInsetsCompat.CONSUMED
        }
    }


    /**
     * Utility: quick Toast
     */
    protected fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    /**
     * Coroutine helpers
     */
    protected fun runInBackground(block: suspend CoroutineScope.() -> Unit) {
        lifecycleScope.launch(Dispatchers.IO, block = block)
    }

    protected fun runOnMain(block: suspend CoroutineScope.() -> Unit) {
        lifecycleScope.launch(Dispatchers.Main, block = block)
    }

    /**
     * Navigation shortcut
     */
    protected fun navigateTo(target: Class<*>, finishCurrent: Boolean = false) {
        startActivity(Intent(this, target))
        if (finishCurrent) finish()
    }

    /**
     * Toggle theme
     */
    protected fun setDarkMode(enabled: Boolean) {
        AppCompatDelegate.setDefaultNightMode(
            if (enabled) AppCompatDelegate.MODE_NIGHT_YES
            else AppCompatDelegate.MODE_NIGHT_NO
        )
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroyed() {
        lifecycle.removeObserver(this)
    }
}