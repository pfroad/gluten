package com.gluten.transport;


import com.gluten.common.URL;

/**
 * Transporter. (Singleton, ThreadSafe)
 * <p>
 * <a href="http://en.wikipedia.org/wiki/Transport_Layer">Transport Layer</a>
 * <a href="http://en.wikipedia.org/wiki/Client%E2%80%93server_model">Client/Server</a>
 */
public interface Transporter {

    /**
     * Bind a server.
     *
     * @param url     server url
     * @param handler
     * @return server
     * @throws ConnectException
     */
    Server bind(URL url, ChannelHandler handler) throws ConnectException;

    /**
     * Connect to a server.
     *
     * @param url     server url
     * @param handler
     * @return client
     * @throws ConnectException
     */
    Client connect(URL url, ChannelHandler handler) throws ConnectException;

}