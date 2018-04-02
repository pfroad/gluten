package http;

import com.gluten.transport.*;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.jetbrains.annotations.NotNull;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.net.InetSocketAddress;

public class HttpClient extends AbstractClient {
    private CloseableHttpClient closeableHttpClient;

    private InetSocketAddress serverAddress;
    private SSLContext sslContext;
    private HttpRequestRetryHandler retryHandler;
    private ConnectionKeepAliveStrategy keepAliveStrategy;

    private boolean evictExpiredConnections = true;
    private boolean evictIdleConnections = true;
    private Long maxIdleTime = 0L;

    private Integer socketTimeout = 3000;
    private Integer connectTimeout = 1000;
    private Long connTimeToLive = -1L;   // MILLISECONDS
    //    private var keepAlive: Boolean? = null
    private Integer maxConnTotal = 0;
    private Integer maxConnTotalPerRoute = 0;
    private Integer connectionRequestTimeout = 500;

    public HttpClient() {

    }

    @Override
    protected InetSocketAddress getServerAddress() {
        return serverAddress;
    }

    @Override
    protected void doConnect() throws ConnectException {
    }

    @Override
    protected void doDisconnect() {
    }

    @Override
    protected void doClose() throws IOException {
        try {
            if (closeableHttpClient != null) {
                closeableHttpClient.close();
            }
        } catch (Throwable e) {
            throw new IOException("Failed to close http client!", e);
        }
    }

    @NotNull
    @Override
    public Channel getChannel() {
        return null;
    }

    @Override
    public void send(@NotNull Message message, ChannelHandler responseHandler) throws ConnectException {
        if (closeableHttpClient == null) {
            throw new ConnectException("unknown server host!!!");
        }


    }
}
