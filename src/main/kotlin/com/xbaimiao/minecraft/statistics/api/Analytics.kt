package com.xbaimiao.minecraft.statistics.api

import java.util.concurrent.CompletableFuture

interface Analytics {

    fun handle(distinctId: String, eventName: String, propsMap: Map<String, Any>): CompletableFuture<Any>

    companion object {

        val INSTANCE: Analytics by lazy {
            MixpanelAnalytics()
        }

    }

}