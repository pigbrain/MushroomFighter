package pigbrain.game.network.websocket;

import javax.websocket.server.ServerContainer;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.eclipse.jetty.util.thread.ThreadPool;
import org.eclipse.jetty.websocket.jsr356.server.deploy.WebSocketServerContainerInitializer;

import pigbrain.game.network.command.CommandExecutor;
import pigbrain.game.network.command.CommandRouteMonitor;
import pigbrain.game.tick.TickContainer;
import pigbrain.game.tick.TickUpdater;

public class WebSocketServer {

	private final String DEFAULT_HOST = "localhost";
	private final int DEFAULT_PORT = 8080;
	private final String DEFAULT_CONTEXT_PATH = "/";
	private final int DEFAULT_MIN_THREAD_COUNT = 3;
	private final int DEFAULT_MAX_THREAD_COUNT = 10;
	
	private final int DEFAULT_TICK_FRAME = 30;
	
	private String host = DEFAULT_HOST;
	private int port = DEFAULT_PORT;
	private String cotextPath = DEFAULT_CONTEXT_PATH;
	private int minThreadCount = DEFAULT_MIN_THREAD_COUNT;
	private int maxThreadCount = DEFAULT_MAX_THREAD_COUNT;
	private int tickFrame = DEFAULT_TICK_FRAME;
	
	public WebSocketServer() {
		
	}
	
	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}
	
	public String getCotextPath() {
		return cotextPath;
	}

	public void setCotextPath(String cotextPath) {
		this.cotextPath = cotextPath;
	}
	
	public void setThreadCount(int minThreadCount, int maxThreadCount) {
		this.minThreadCount = minThreadCount;
		this.maxThreadCount = maxThreadCount;
	}

	public int getTickFrame() {
		return tickFrame;
	}

	public void setTickFrame(int tickFrame) {
		this.tickFrame = tickFrame;
	}

	public void start() {
		
		TickContainer.getInstance().addInstance(CommandExecutor.getInstance());
		TickContainer.getInstance().addInstance(CommandRouteMonitor.getInstance());
		
		Server webSocketServer = new Server(createThreadPool());
		
		ServerConnector serverConnector = new ServerConnector(webSocketServer);
		serverConnector.setHost(host);
		serverConnector.setPort(port);
		
		webSocketServer.addConnector(serverConnector);
		

		ServletContextHandler servletContextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
		servletContextHandler.setContextPath(cotextPath);
		webSocketServer.setHandler(servletContextHandler);

		try {
			ServerContainer container = WebSocketServerContainerInitializer.configureContext(servletContextHandler);

			container.addEndpoint(WebSocketHandler.class);
			
			new TickUpdater(tickFrame).start();
			
			webSocketServer.start();
			webSocketServer.dump(System.err);
			
			webSocketServer.join();
			
		} catch (Throwable t) {
			t.printStackTrace(System.err);
		}
	}
	
	private ThreadPool createThreadPool()
    {
        QueuedThreadPool threadPool = new QueuedThreadPool();
        threadPool.setMinThreads(minThreadCount);
        threadPool.setMaxThreads(maxThreadCount);
        return threadPool;
    }
}
