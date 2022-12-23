package com.xbaimiao.minecraft.statistics.api

interface Analytics {

    fun handle(distinctId: String, eventName: String, propsMap: Map<String, Any>)

    companion object {

        private lateinit var INSTANCE: Analytics

        fun init(): Analytics {
            if (this::INSTANCE.isInitialized) {
                return INSTANCE
            }
            return MixpanelAnalytics().also { INSTANCE = it }
        }

    }

}