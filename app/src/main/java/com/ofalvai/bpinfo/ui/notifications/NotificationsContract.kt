package com.ofalvai.bpinfo.ui.notifications

import com.ofalvai.bpinfo.model.Route
import com.ofalvai.bpinfo.ui.base.MvpPresenter
import com.ofalvai.bpinfo.ui.base.MvpView

interface NotificationsContract {

    interface View : MvpView {

        /**
         * List of all routes, before grouped by route type
         */
        fun displayRouteList(routeList: List<Route>)
        fun onRouteClicked(route: Route)
        fun displaySubscriptions(routeList: List<Route>)
        fun addSubscribedRoute(route: Route)
        fun removeSubscribedRoute(route: Route)
        fun showProgress(show: Boolean)
        fun showRouteListError(show: Boolean)
        fun showSubscriptionError()
    }

    interface Presenter : MvpPresenter<View> {
        fun fetchRouteList()
        fun subscribeTo(routeID: String)
        fun removeSubscription(routeID: String)
        fun fetchSubscriptions()

    }

}