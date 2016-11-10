package com.kenzan.msl.account.edge.config;

import com.google.inject.AbstractModule;
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
import com.netflix.governator.guice.lazy.LazySingletonScope;
import io.swagger.api.AccountEdgeApiService;
import io.swagger.api.factories.AccountEdgeApiServiceFactory;
import io.swagger.api.impl.AccountEdgeApiOriginFilter;
import io.swagger.api.impl.AccountEdgeApiServiceImpl;
import io.swagger.api.impl.AccountEdgeSessionToken;
import io.swagger.api.impl.AccountEdgeSessionTokenImpl;

/**
 * @author Kenzan
 */
public class AccountEdgeModule extends AbstractModule {
  @Override
  protected void configure() {
    requestStaticInjection(AccountEdgeApiServiceFactory.class);
    requestStaticInjection(AccountEdgeApiOriginFilter.class);

    bind(AccountEdgeSessionToken.class).to(AccountEdgeSessionTokenImpl.class).in(
        LazySingletonScope.get());
    bind(RatingsDataClientService.class).to(RatingsDataClientServiceImpl.class).in(
        LazySingletonScope.get());
    bind(CatalogDataClientService.class).to(CatalogDataClientServiceImpl.class).in(
        LazySingletonScope.get());

    bind(AccountEdgeService.class).to(AccountEdgeServiceImpl.class).in(LazySingletonScope.get());
    bind(LibraryServiceHelper.class).to(LibraryServiceHelperImpl.class)
        .in(LazySingletonScope.get());
    bind(LibraryService.class).to(LibraryServiceImpl.class).in(LazySingletonScope.get());
    bind(RatingsService.class).to(RatingsServiceImpl.class).in(LazySingletonScope.get());

    bind(AccountEdgeService.class).to(AccountEdgeServiceImpl.class).in(LazySingletonScope.get());
    bind(AccountEdgeApiService.class).to(AccountEdgeApiServiceImpl.class).in(
        LazySingletonScope.get());
  }
}
