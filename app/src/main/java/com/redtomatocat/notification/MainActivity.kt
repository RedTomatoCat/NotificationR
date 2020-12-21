package com.redtomatocat.notification

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.redtomatocat.notificationr.NotificationR
import com.redtomatocat.notificationr.bean.NotificationViewData
import com.redtomatocat.notificationr.imp.NotificationRListener

/**
 * Create on 2020/12/12
 *
 * @author redtomatocat
 * @version 1.0.0
 **/
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initThing()
    }

    private fun initThing() {
        findViewById<View>(R.id.notification_bt).setOnClickListener { showNotification(0) }
        findViewById<View>(R.id.notification_custom_bt).setOnClickListener { showNotification(1) }
        findViewById<View>(R.id.safe_service_bt).setOnClickListener { showNotification(2) }
    }

    private fun showNotification(id: Int) {
        when (id) {
            0 -> {
                NotificationR.Builder(this)
                        .setTitle("Test")
                        .setContentText("Text Content")
                        .build().show(12121)
            }
            1 -> {
                NotificationR.Builder(this)
                        .setCustom(R.layout.custom_notification_small, R.layout.custom_notification_large, object : NotificationRListener {
                            override fun onClick(action: String) {
                                when (action) {
                                    "pre_bt" -> {
                                        //TODO YOUR THING
                                        Toast.makeText(baseContext, action, Toast.LENGTH_SHORT).show()
                                    }
                                    "next_bt" -> {
                                        //TODO YOUR THING
                                        Toast.makeText(baseContext, action, Toast.LENGTH_SHORT).show()
                                    }
                                }
                            }

                            override fun makeButtonsThing(): MutableList<NotificationViewData> = mutableListOf(
                                    NotificationViewData(R.id.pre_bt, "pre_bt"),
                                    NotificationViewData(R.id.next_bt, "next_bt"))

                        })
                        .build()
                        .show(1211111)
            }
            2 -> {
                startService(applicationContext, Intent(applicationContext, TestSafeForegroundService::class.java))
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
    }

    private fun startService(context: Context, intent: Intent) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(intent)
        } else {
            context.startService(intent)
        }
    }

}