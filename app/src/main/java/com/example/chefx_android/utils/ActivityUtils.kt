package com.example.chefx_android.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle

object ActivityUtils {

    /**
     * Launch a new Activity with optional bundle + finish flags.
     *
     * @param context The calling context (Activity required for finish flags)
     * @param target The Activity class to launch
     * @param bundle Optional extras to pass
     * @param clearStack If true, clears entire backstack (used for Login → Home flows)
     * @param finishCurrent If true, finishes the current Activity after navigation
     */
    fun startActivity(
        context: Context,
        target: Class<out Activity>,
        bundle: Bundle? = null,
        clearStack: Boolean = false,
        finishCurrent: Boolean = false
    ) {
        val intent = Intent(context, target)

        bundle?.let { intent.putExtras(it) }

        if (clearStack) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }

        context.startActivity(intent)

        if (finishCurrent && context is Activity) {
            context.finish()
        }
    }

    /**
     * Launch Activity with transition animation.
     */
    fun startActivityWithAnimation(
        activity: Activity,
        target: Class<out Activity>,
        bundle: Bundle? = null,
        enterAnim: Int,
        exitAnim: Int
    ) {
        val intent = Intent(activity, target)
        bundle?.let { intent.putExtras(it) }
        activity.startActivity(intent)
        activity.overridePendingTransition(enterAnim, exitAnim)
    }
}