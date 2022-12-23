package com.xbaimiao.minecraft.statistics.api

import com.mixpanel.mixpanelapi.ClientDelivery
import com.mixpanel.mixpanelapi.MessageBuilder
import com.mixpanel.mixpanelapi.MixpanelAPI
import org.json.JSONObject
import java.util.concurrent.Executors

class MixpanelAnalytics : Analytics {

    private val token = "4b7e5b44075462a7f3e60655a5100321"
    private var executor = Executors.newFixedThreadPool(6)

    @Synchronized
    override fun handle(distinctId: String, eventName: String, propsMap: Map<String, Any>) {
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
        }
    }

}