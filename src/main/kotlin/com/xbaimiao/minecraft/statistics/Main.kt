package com.xbaimiao.minecraft.statistics

import com.xbaimiao.minecraft.statistics.manager.ServerManager
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

val scopes = ArrayList<Job>()

fun main() {
    start()
    while (true) {
        val line = readlnOrNull()
        if (line == null) {
            Thread.sleep(10)
            continue
        }
        println(line)
        if (line == "reload") {
            scopes.removeIf {
                it.cancel()
                true
            }
            start()
            println("重载完成")
        }
    }
}

@OptIn(DelicateCoroutinesApi::class)
private fun start() {
    val servers = ServerManager.read()
    for (server in servers) {
        GlobalScope.launch {
            ServerManager.analytic(server)
        }.also { scopes.add(it) }
    }
}