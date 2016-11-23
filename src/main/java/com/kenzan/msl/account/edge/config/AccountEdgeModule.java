package com.kenzan.msl.account.edge.config;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import com.kenzan.msl.account.edge.services.AccountEdgeService;
import com.kenzan.msl.account.edge.services.LibraryService;
import com.kenzan.msl.account.edge.services.LibraryServiceHelper;
import com.kenzan.msl.account.edge.services.RatingsService;
import com.kenzan.msl.account.edge.services.impl.AccountEdgeServiceImpl;
import com.kenzan.msl.account.edge.services.impl.LibraryServiceHelperImpl;
import com.kenzan.msl.account.edge.services.impl.LibraryServiceImpl;
import com.kenzan.msl.account.edge.services.impl.RatingsServiceImpl;
import com.kenzan.msl.catalog.client.services.CatalogDataClientService;
import com.kenzan.msl.catalog.client.services.CatalogDataClientServiceImpl;
import com.kenzan.msl.ratings.client.services.RatingsDataClientService;
import com.kenzan.msl.ratings.client.services.RatingsDataClientServiceImpl;
import com.netflix.config.DynamicPropertyFactory;
import com.netflix.config.DynamicStringProperty;
import com.netflix.governator.guice.lazy.LazySingletonScope;
import io.swagger.api.AccountEdgeApiService;
import io.swagger.api.factories.AccountEdgeApiServiceFactory;
import io.swagger.api.impl.AccountEdgeApiOriginFilter;
import io.swagger.api.impl.AccountEdgeApiServiceImpl;
import io.swagger.api.impl.AccountEdgeSessionToken;
import io.swagger.api.impl.AccountEdgeSessionTokenImpl;

/**
 * Account Edge Module, a support class for Modules which reduces repetition and results in a more readable configuration
 * if no archaius.configurationSource.additionalUrls property is passed in, archaius uses default configuration. See readme to
 * understand how to pass in these variables
 *
 * @author Kenzan
 */
public class AccountEdgeModule extends AbstractModule {

    private final String DEFAULT_CLIENT_PORT = "3000";

    private DynamicStringProperty CLIENT_PORT =
        DynamicPropertyFactory.getInstance().getStringProperty("clientPort", DEFAULT_CLIENT_PORT);

    @Override
    protected void configure() {
        bindConstant().annotatedWith(Names.named("clientPort")).to(CLIENT_PORT.get());

        requestStaticInjection(AccountEdgeApiServiceFactory.class);
        requestStaticInjection(AccountEdgeApiOriginFilter.class);
        bind(AccountEdgeSessionToken.class).to(AccountEdgeSessionTokenImpl.class).in(LazySingletonScope.get());

        bind(RatingsDataClientService.class).to(RatingsDataClientServiceImpl.class).in(LazySingletonScope.get());
        bind(CatalogDataClientService.class).to(CatalogDataClientServiceImpl.class).in(LazySingletonScope.get());

        bind(LibraryServiceHelper.class).to(LibraryServiceHelperImpl.class).in(LazySingletonScope.get());
        bind(LibraryService.class).to(LibraryServiceImpl.class).in(LazySingletonScope.get());
        bind(RatingsService.class).to(RatingsServiceImpl.class).in(LazySingletonScope.get());

        bind(AccountEdgeService.class).to(AccountEdgeServiceImpl.class).in(LazySingletonScope.get());
        bind(AccountEdgeApiService.class).to(AccountEdgeApiServiceImpl.class).in(LazySingletonScope.get());
    }
}
