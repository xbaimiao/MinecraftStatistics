package com.xbaimiao.minecraft.statistics.manager

import org.json.JSONObject
import studio.trc.minecraft.serverpinglib.API.MCServerSocket
import studio.trc.minecraft.serverpinglib.Protocol.ProtocolVersion

data class Server(
    val name: String, val ip: String, val port: Int, val author: String, val fakePlayer: Double
) {

    fun getOnlinePlayers(): Int {
        return try {
            val socket = MCServerSocket.getInstance(ip, port)
            val online = socket.getStatus(ProtocolVersion.v1_12_2).onlinePlayers
            (online / (1 + fakePlayer)).toInt()
//            online - (online * fakePlayer).toInt()
        } catch (th: Throwable) {
            th.printStackTrace()
            0
        }
    }

    fun toJson(): String {
        return JSONObject(toProp()).toString()
    }

    fun toProp(pair: Pair<String, String>? = null): Map<String, String> {
        val map = HashMap<String, String>()
        map["name"] = name
        map["ip"] = ip
        map["port"] = port.toString()
        map["author"] = author
        map["fakePlayer"] = fakePlayer.toString()
        pair?.let { map[it.first] = it.second }
        return map
    }

    companion object {

        fun byJson(json: String): Server {
            val jsonObject = JSONObject(json)
            return Server(
                jsonObject.getString("name"),
                jsonObject.getString("ip"),
                jsonObject.getInt("port"),
                jsonObject.getString("author"),
                jsonObject.getString("fakePlayer").toDouble()
            )
        }

    }

}
