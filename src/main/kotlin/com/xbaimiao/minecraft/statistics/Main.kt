package com.xbaimiao.minecraft.statistics

import com.xbaimiao.minecraft.statistics.manager.ServerManager

fun main(args: Array<String>) {
    ServerManager.read()
    ServerManager.analytics()
    Thread.currentThread().join()
}