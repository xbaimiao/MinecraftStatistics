package com.xbaimiao.minecraft.statistics.manager

import com.xbaimiao.minecraft.statistics.api.Analytics
import kotlinx.coroutines.delay
import java.io.File

object ServerManager {

    val servers = ArrayList<Server>()

    fun read() {
        val file = File("servers")
        if (!file.exists()) {
            file.mkdirs()
        }
        file.listFiles()?.forEach {
            // 读取文件内容
            val server = Server.byJson(it.readText())
            println(server.toJson())
            // 添加到服务器列表
            servers.add(server)
        }
        println("读取了 ${servers.size} 个服务器")
    }

    suspend fun analytic(server: Server) {
        while (true) {
            // 获取在线人数
            val online = server.getOnlinePlayers()
            // 发送统计数据
            val props = server.toProp("online" to online.toString())
            Analytics.INSTANCE.handle(server.name, "MinecraftStatistics", props).thenAccept { _ ->
                println("发送了 ${server.name} 的统计数据 $props")
            }
            // 等待 2 秒
            delay(2000)
        }
    }

}