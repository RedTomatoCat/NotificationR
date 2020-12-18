package com.redtomatocat.notificationr.util

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method

/**
 * Create on 2020/12/12
 *
 * @author redtomatocat
 * @version 1.0.0
 **/
class ApplicationTool private constructor(){

    companion object{
        private var sInstance: ApplicationTool? = null
        val instance: ApplicationTool
            get() {
                if (sInstance == null) sInstance = ApplicationTool()
                return sInstance as ApplicationTool
            }
    }

    private var application: Application? = null
    private val context: Context? = null

    @SuppressLint("PrivateApi")
    fun getApplication(): Application? {
        if (application != null) {
            return application
        }
        try {
            val activityThreadClass = Class.forName("android.app.ActivityThread")
            val method2: Method = activityThreadClass.getMethod("currentActivityThread")
            val localObject: Any = method2.invoke(null)
            val method: Method = activityThreadClass.getMethod("getApplication")
            application = method.invoke(localObject) as Application
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        } catch (e: NoSuchMethodException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
        } catch (e: InvocationTargetException) {
            e.printStackTrace()
        }
        return application
    }

    fun context() = if (application == null) {
        getApplication()?.applicationContext
    } else {
        application?.applicationContext
    }

    fun getTargetSdkVersion(): Int {
        return getTargetSdkVersion(getApplication())
    }

    fun getTargetSdkVersion(context: Context?): Int {
        if(context == null) return 0
        val localPackageManager: PackageManager? = getApplication()?.packageManager
        var localApplicationInfo: ApplicationInfo? = null
        if (localPackageManager != null) {
            localApplicationInfo = try {
                context.packageName?.let { localPackageManager.getApplicationInfo(it, 0) }
            } catch (localNameNotFoundException: PackageManager.NameNotFoundException) {
                localNameNotFoundException.printStackTrace()
                return 0
            }
        }
        return localApplicationInfo?.targetSdkVersion ?: 0
    }

}