package com.redtomatocat.notificationr

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.text.TextUtils
import android.widget.RemoteViews
import androidx.annotation.DrawableRes
import androidx.annotation.LayoutRes
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.redtomatocat.notificationr.imp.NotificationRListener
import com.redtomatocat.notificationr.util.ApplicationTool

/**
 * Create on 2020/12/12
 *
 * @author redtomatocat
 * @version 1.0.0
 **/
class NotificationR private constructor(private val context: Context, private val notificationManagerCompat: NotificationManagerCompat,
                                        private val nf: Notification, private val listener: NotificationRListener? = null) {

    fun show(notificationId: Int) {
        listener?.let {
            NotificationRManager.instance.cacheNt[notificationId] = listener
        }
        notificationManagerCompat.notify(notificationId, nf)
    }

    fun show(notificationId: Int, foreground: Service) {
        listener?.let {
            NotificationRManager.instance.cacheNt[notificationId] = listener
        }
        foreground.startForeground(notificationId, nf)
        notificationManagerCompat.notify(notificationId, nf)
    }

    class Builder(private val context: Context, private var channelId: String = "",
                  private var channelName: String = "", private var channelDescription: String = "") {

        private var listenerP : NotificationRListener ?= null

        private var mBuild :NotificationCompat.Builder = NotificationCompat.Builder(context, channelId)
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setShowWhen(true)
                .setSmallIcon(R.drawable.icon_safe_service_logo)

        private var mNotificationManagerCompat:  NotificationManagerCompat = NotificationManagerCompat.from(context)

        init {
            if(TextUtils.isEmpty(channelId)) channelId = ApplicationTool.instance.getTargetSdkVersion(context).toString()
            if(TextUtils.isEmpty(channelName)) channelName = context.packageName
            if(TextUtils.isEmpty(channelDescription)) channelDescription = context.applicationInfo.processName

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val notificationChannel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH)
                notificationChannel.enableLights(false)
                notificationChannel.setShowBadge(false)
                notificationChannel.description = channelDescription
                notificationChannel.lockscreenVisibility = Notification.VISIBILITY_SECRET
                val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?
                manager!!.createNotificationChannel(notificationChannel)
                mBuild.setChannelId(channelId)
            }
        }

        fun setTitle(title: String): Builder {
            mBuild.setContentTitle(title)
            return this
        }

        fun setSmallIcon(@DrawableRes icon: Int): Builder {
            mBuild.setSmallIcon(icon)
            return this
        }

        fun setContentText(content : String): Builder {
            mBuild.setContentText(content)
            return this
        }

        fun setTargetIntent(jumpIntent: Intent){
            val clickIntent = PendingIntent.getActivity(context, 0, jumpIntent, 0)
            mBuild.setContentIntent(clickIntent)
        }

        fun setProgress(max: Int, progress: Int, indeterminate: Boolean): Builder {
            mBuild.setProgress(max, progress, indeterminate)
            return this
        }


        fun setPriority(pri: Int): Builder {
            mBuild.priority = NotificationCompat.PRIORITY_LOW
            return this
        }

        fun setVisibility(@NotificationCompat.NotificationVisibility visibility: Int): Builder {
            mBuild.setVisibility(visibility)
            return this
        }

        fun setShowWhen(show: Boolean): Builder {
            mBuild.setShowWhen(show)
            return this
        }

        fun setCustom(@LayoutRes layout: Int, @LayoutRes bigLayout: Int = -1,
                      listener: NotificationRListener? = null): Builder {
            mBuild.setStyle(NotificationCompat.DecoratedCustomViewStyle())

            var notificationLayout: RemoteViews? = null
            var notificationLayoutExpanded: RemoteViews? = null

            if (layout != -1) {
                notificationLayout = RemoteViews(context.packageName, layout)
                mBuild.setCustomContentView(notificationLayout)
            }
            if (bigLayout != -1) {
                notificationLayoutExpanded = RemoteViews(context.packageName, bigLayout)
                mBuild.setCustomBigContentView(notificationLayoutExpanded)
            }
            listener?.let {
                val viewDataList = it.makeButtonsThing()
                for (viewData in viewDataList) {
                    notificationLayout?.setOnClickPendingIntent(viewData.bindViewId, makePendingIntent(context, viewData.action))
                    notificationLayoutExpanded?.setOnClickPendingIntent(viewData.bindViewId, makePendingIntent(context, viewData.action))
                }
            }
            listenerP = listener
            return this
        }

        private fun makePendingIntent(appContext: Context, action: String): PendingIntent {
            val pteIntent = Intent(appContext, NotificationEventIntentService::class.java)
            pteIntent.action = action
            return PendingIntent.getService(appContext, 0, pteIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        }

        fun make() = mBuild.build()

        fun build(): NotificationR {
            return NotificationR(context, mNotificationManagerCompat, make(), listenerP)
        }
    }




}