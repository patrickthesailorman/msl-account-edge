package com.kenzan.msl.account.edge;

import com.google.inject.Injector;
import com.kenzan.msl.account.client.config.AccountDataClientModule;
import com.kenzan.msl.account.client.config.LocalAccountDataClientModule;
import com.kenzan.msl.account.edge.config.AccountEdgeModule;
import com.kenzan.msl.account.edge.config.RestModule;
import com.netflix.governator.guice.LifecycleInjector;
import com.netflix.governator.lifecycle.LifecycleManager;
import io.swagger.api.AccountEdgeApi;
import io.swagger.api.impl.AccountEdgeApiOriginFilter;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.servlet.ServletContainer;

import javax.servlet.DispatcherType;
import java.util.EnumSet;


public class Main {

  /**
   * Runs jetty server to expose jersey API
   *
   * @param args String array
   * @throws Exception if server doesn't start
   */
  public static void main(String[] args) throws Exception {

    // use Local*DataClientModule when config file is local
    // use *DataClientModule when archaius config to use are the ones in definition in config.properties
    Injector injector =
        LifecycleInjector.builder()
            .withModules(new LocalAccountDataClientModule(), new AccountEdgeModule())
            .build().createInjector();
    LifecycleManager manager = injector.getInstance(LifecycleManager.class);

    Server jettyServer = new Server(9002);
    ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
    context.setContextPath("/");
    context.addFilter(AccountEdgeApiOriginFilter.class, "/*", EnumSet.of(DispatcherType.REQUEST));
    jettyServer.setHandler(context);

    ServletHolder jerseyServlet = context.addServlet(ServletContainer.class, "/*");
    jerseyServlet.setInitOrder(0);

    jerseyServlet.setInitParameter(ServerProperties.PROVIDER_CLASSNAMES,
        AccountEdgeApi.class.getCanonicalName());
    jerseyServlet.setInitParameter(ServerProperties.PROVIDER_PACKAGES, "io.swagger.jaxrs.json;io.swagger.jaxrs.listing;io.swagger.api");
    jerseyServlet.setInitParameter("com.sun.jersey.api.json.POJOMappingFeature", "true");

    try {
      manager.start();
      jettyServer.start();
      jettyServer.join();

    } finally {
      manager.close();
      jettyServer.destroy();
    }
  }
}
