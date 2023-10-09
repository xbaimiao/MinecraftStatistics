package com.xbaimiao.minecraft.statistics.api

import com.mixpanel.mixpanelapi.ClientDelivery
import com.mixpanel.mixpanelapi.MessageBuilder
import com.mixpanel.mixpanelapi.MixpanelAPI
import org.json.JSONObject
import java.util.concurrent.CompletableFuture
import java.util.concurrent.Executors

class MixpanelAnalytics : Analytics {

    // mixpanle token
    private val token = "44225d86bbba216c4b6a1757b3de1bff"
    private var executor = Executors.newFixedThreadPool(6)

    override fun handle(distinctId: String, eventName: String, propsMap: Map<String, Any>): CompletableFuture<Any> {
        val future = CompletableFuture<Any>()
        executor.submit {
            val messageBuilder = MessageBuilder(token)

            val props = JSONObject()

            propsMap.forEach { (k, v) ->
                props.put(k, v.toString())
            }

            val planEvent = messageBuilder.event(distinctId, eventName, props)

            val delivery = ClientDelivery()
            delivery.addMessage(planEvent)
            val mixpanel = MixpanelAPI()
            mixpanel.deliver(delivery)
            future.complete(null)
        }
        return future
    }

}