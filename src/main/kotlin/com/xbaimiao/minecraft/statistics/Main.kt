package com.xbaimiao.minecraft.statistics

import com.xbaimiao.minecraft.statistics.manager.ServerManager
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@OptIn(DelicateCoroutinesApi::class)
fun main(args: Array<String>) {
    ServerManager.read()
    for (server in ServerManager.servers) {
        GlobalScope.launch {
            ServerManager.analytic(server)
        }
    }
    Thread.currentThread().join()
}