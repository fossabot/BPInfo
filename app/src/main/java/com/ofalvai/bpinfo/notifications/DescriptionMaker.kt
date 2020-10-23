/*
 * Copyright 2018 Olivér Falvai
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ofalvai.bpinfo.notifications

import android.content.Context
import androidx.core.os.ConfigurationCompat
import com.ofalvai.bpinfo.R
import java.util.*

object DescriptionMaker {

    private const val DATA_KEY_ROUTE_BUS = "route_bus"
    private const val DATA_KEY_ROUTE_FERRY = "route_ferry"
    private const val DATA_KEY_ROUTE_RAIL = "route_rail"
    private const val DATA_KEY_ROUTE_TRAM = "route_tram"
    private const val DATA_KEY_ROUTE_TROLLEYBUS = "route_trolleybus"
    private const val DATA_KEY_ROUTE_SUBWAY = "route_subway"
    private const val DATA_KEY_ROUTE_OTHER = "route_other"

    private const val DATA_KEY_ROUTE_SEPARATOR = "|"

    /**
     * Makes the localized description of affected routes, grouped by route types.
     * One route type per line, structure of line (route list) depends on locale.
     * @param routeData Map of route type keys and route short names separated by "|"
     * @param context Needed for localized string resources
     */
    @JvmStatic
    fun makeDescription(routeData: Map<String, String>, context: Context): String {
        val subwayList =
            makeRouteList(routeData[DATA_KEY_ROUTE_SUBWAY], context, DATA_KEY_ROUTE_SUBWAY)
        val busList = makeRouteList(routeData[DATA_KEY_ROUTE_BUS], context, DATA_KEY_ROUTE_BUS)
        val tramList =
            makeRouteList(routeData[DATA_KEY_ROUTE_TRAM], context, DATA_KEY_ROUTE_TRAM)
        val trolleyList = makeRouteList(
            routeData[DATA_KEY_ROUTE_TROLLEYBUS],
            context,
            DATA_KEY_ROUTE_TROLLEYBUS
        )
        val railList =
            makeRouteList(routeData[DATA_KEY_ROUTE_RAIL], context, DATA_KEY_ROUTE_RAIL)
        val ferryList =
            makeRouteList(routeData[DATA_KEY_ROUTE_FERRY], context, DATA_KEY_ROUTE_FERRY)
        val otherList =
            makeRouteList(routeData[DATA_KEY_ROUTE_OTHER], context, DATA_KEY_ROUTE_OTHER)

        return arrayListOf(
            subwayList,
            busList,
            tramList,
            trolleyList,
            railList,
            ferryList,
            otherList
        )
            .asSequence()
            .filter { it.isNotEmpty() }
            .joinToString(separator = "\n")
    }

    private fun makeRouteList(routeData: String?, context: Context, routeType: String): String {
        val langCode = ConfigurationCompat.getLocales(context.resources.configuration)[0].language
        return if (langCode == "hu") {
            makeRouteLineHu(routeData, context, routeType)
        } else {
            makeRouteLineEn(routeData, context, routeType)
        }
    }

    private fun getLocalizedRouteType(context: Context, routeType: String): String {
        return when (routeType) {
            DATA_KEY_ROUTE_BUS -> context.getString(R.string.route_bus_alt)
            DATA_KEY_ROUTE_FERRY -> context.getString(R.string.route_ferry_alt)
            DATA_KEY_ROUTE_RAIL -> context.getString(R.string.route_rail_alt)
            DATA_KEY_ROUTE_TRAM -> context.getString(R.string.route_tram_alt)
            DATA_KEY_ROUTE_TROLLEYBUS -> context.getString(R.string.route_trolleybus_alt)
            DATA_KEY_ROUTE_SUBWAY -> context.getString(R.string.route_subway_alt)
            DATA_KEY_ROUTE_OTHER -> context.getString(R.string.route_other)
            else -> context.getString(R.string.route_other)
        }
    }

    private fun makeRouteLineHu(
        routeData: String?,
        context: Context,
        routeType: String
    ): String {
        @Suppress("LiftReturnOrAssignment")
        if (routeData != null && routeData.isNotEmpty()) {
            val routeList: String = routeData
                .split(DATA_KEY_ROUTE_SEPARATOR)
                .asSequence()
                .map { it.trim() }
                .filter { it.isNotEmpty() }
                .map(this::numberPostfixHu)
                .distinct()
                .joinToString(separator = ", ")

            if (routeType == DATA_KEY_ROUTE_OTHER) {
                // We don't append the type of route, because the route's shortName is the type itself
                return routeList
            } else {
                val name = getLocalizedRouteType(context, routeType)
                return "$routeList $name"
            }
        } else {
            return ""
        }
    }

    private fun makeRouteLineEn(
        routeData: String?,
        context: Context,
        routeType: String
    ): String {
        val sb = StringBuilder()
        if (routeData != null && routeData.isNotEmpty()) {
            val name = getLocalizedRouteType(context, routeType)
            sb.append("$name ")
            val routeList = routeData
                .split(DATA_KEY_ROUTE_SEPARATOR)
                .joinToString(separator = ", ") { it.trim() }
            sb.append(routeList)
        }
        return sb.toString().capitalize(Locale.getDefault())
    }

    private fun numberPostfixHu(name: String): String {
        if (name.isEmpty()) return ""

        return when (name.last()) {
            'A', 'E', 'M' -> name
            '1', '2', '4', '7', '9' -> "$name-es"
            '3', '8' -> "$name-as"
            '5' -> "$name-ös"
            '6' -> "$name-os"
            '0' -> when (name.takeLast(2)) {
                "10", "40", "50", "70", "90" -> "$name-es"
                "20", "30", "60", "80", "00" -> "$name-as"
                else -> name
            }
            else -> name
        }
    }
}
