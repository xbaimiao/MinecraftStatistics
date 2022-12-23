package com.xbaimiao.minecraft.statistics.manager

import com.xbaimiao.minecraft.statistics.api.Analytics
import java.io.File

object ServerManager {

    private val servers = ArrayList<Server>()

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

    fun analytics() {
        // 新建一个线程定时执行任务
        Thread {
            while (true) {
                // 遍历服务器列表
                servers.forEach {
                    // 获取在线人数
                    val online = it.getOnlinePlayers()
                    // 发送统计数据
                    val props = it.toProp("online" to online.toString())
                    Analytics.init().handle(it.name, "MinecraftStatistics", props)
                    println("发送了 ${it.name} 的统计数据 $props")
                }
                // 等待 10 秒
                Thread.sleep(10000)
            }
        }.start()
    }

}