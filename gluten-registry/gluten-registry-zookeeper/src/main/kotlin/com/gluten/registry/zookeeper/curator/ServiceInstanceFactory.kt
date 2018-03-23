package com.gluten.registry.zookeeper.curator

import org.apache.commons.lang3.RandomStringUtils
import org.apache.curator.x.discovery.ServiceInstance
import org.apache.curator.x.discovery.UriSpec
import java.net.InetAddress

class ServiceInstanceFactory {
    companion object {
        fun createInstance(sname: String,
                           host: String,
                           port: Int,
                           sslPort: Int,
                           uriSpec: String,
                           payload: Object): ServiceInstance<Object> {
            return ServiceInstance
                    .builder<Object>()
                    .id(genInstanceId())
                    .name(sname)
                    .address(host)
                    .port(port)
                    .uriSpec(UriSpec(uriSpec))
                    .payload(payload)
                    .build()
        }

        fun genInstanceId(): String {
            val addr: InetAddress = InetAddress.getLocalHost()
            val id = StringBuilder()
            val hostname: String = addr.hostName
            id.append(if (hostname.length > 12) hostname.substring(0, 12) else hostname)
            id.append(RandomStringUtils.randomNumeric(6))
            return id.toString()
        }
    }
}