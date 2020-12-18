package com.redtomatocat.notificationr.imp

import androidx.annotation.IdRes

/**
 * Create on 2020/12/12
 *
 * @author redtomatocat
 * @version 1.0.0
 **/
interface NotificationRListener {

    fun onClick(action: String)
    fun makeButtonsThing(): MutableList<ViewData>

    class ViewData (@IdRes var id: Int = -1, var action: String = "")

}