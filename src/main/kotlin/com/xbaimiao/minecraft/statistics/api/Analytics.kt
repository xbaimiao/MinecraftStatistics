package com.xbaimiao.minecraft.statistics.api

interface Analytics {

    fun handle(distinctId: String, eventName: String, propsMap: Map<String, Any>)

    companion object {

        val INSTANCE: Analytics by lazy {
            MixpanelAnalytics()
        }

    }

}