package com.android.app.justbarit.presentation.common.ext

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import com.android.app.justbarit.R
import com.android.app.justbarit.presentation.base.JustBarItBaseActivity
import java.io.Serializable

fun JustBarItBaseActivity.showProgress() {
    justBarItProgress.show()
}

fun JustBarItBaseActivity.hideProgress() {
    justBarItProgress.dismiss()
}


/** An inline function is one for which the compiler copies the code from the function definition
 *  directly into the code of the calling function rather than creating a separate set of instructions in memory.
 *  This eliminates call-linkage overhead and can expose significant optimization opportunities.
 *
 * "reified" is a special type of keyword that helps access the information related to a class at runtime.*/
inline fun <reified T> Activity.navigate(
    data: Any? = null,
    requestCode: Int? = -200,
    flags: Array<Int> = emptyArray(),
) {

    val bundle = Bundle()
    val intent = Intent(this, T::class.java).apply {

        flags.forEach {
            addFlags(it)
        }

        data?.let {

            if (data is Array<*>) {

                val extras = data as Array<Pair<String, Any>>

                if (extras.isNotEmpty() && extras.first().first.trim().isNotEmpty()) {
                    extras.forEach {
                        val extraKey = it.first
                        when (it.second) {
                            is Int -> bundle.putInt(extraKey, it.second as Int)
                            is String -> bundle.putString(extraKey, it.second as String)
                            is Boolean -> bundle.putBoolean(extraKey, it.second as Boolean)
                            is ArrayList<*> -> bundle.putParcelableArrayList(
                                extraKey,
                                it.second as ArrayList<out Parcelable>
                            )

                            else -> bundle.putSerializable(extraKey, it.second as Serializable)
                        }
                    }
                }
            }
        }
        putExtras(bundle)
    }
    if (requestCode == -200) {
        startActivity(intent)
        if (flags.isNotEmpty()) finish()
    } else {
        /// TODO: This is not working with Intent Launcher
        startActivityForResult(intent, requestCode!!)
    }

    overridePendingTransition(R.anim.slide_in, R.anim.slide_out)
}