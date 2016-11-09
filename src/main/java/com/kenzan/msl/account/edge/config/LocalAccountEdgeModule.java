package com.kenzan.msl.account.edge.config;

import com.google.inject.AbstractModule;
import com.kenzan.msl.account.edge.services.AccountEdgeService;
import com.kenzan.msl.account.edge.services.LibraryService;
import com.kenzan.msl.account.edge.services.LibraryServiceHelper;
import com.kenzan.msl.account.edge.services.RatingsService;
import com.kenzan.msl.account.edge.services.stub.AccountEdgeServiceStub;
import com.kenzan.msl.account.edge.services.stub.LibraryServiceHelperStub;
import com.kenzan.msl.account.edge.services.stub.LibraryServiceStub;
import com.kenzan.msl.account.edge.services.stub.RatingsServiceStub;
import com.kenzan.msl.catalog.client.services.CatalogDataClientService;
import com.kenzan.msl.catalog.client.services.CatalogDataClientServiceStub;
import com.kenzan.msl.ratings.client.services.RatingsDataClientService;
import com.kenzan.msl.ratings.client.services.RatingsDataClientServiceStub;
import com.netflix.governator.guice.lazy.LazySingletonScope;
import io.swagger.api.AccountEdgeApiService;
import io.swagger.api.factories.AccountEdgeApiServiceFactory;
import io.swagger.api.impl.AccountEdgeApiServiceImpl;

/**
 * @author Kenzan
 */
public class LocalAccountEdgeModule extends AbstractModule {

  @Override
  protected void configure() {
    requestStaticInjection(AccountEdgeApiServiceFactory.class);
    bind(RatingsDataClientService.class).to(RatingsDataClientServiceStub.class).in(
        LazySingletonScope.get());
    bind(CatalogDataClientService.class).to(CatalogDataClientServiceStub.class).in(
        LazySingletonScope.get());

    bind(LibraryServiceHelper.class).to(LibraryServiceHelperStub.class)
        .in(LazySingletonScope.get());
    bind(LibraryService.class).to(LibraryServiceStub.class).in(LazySingletonScope.get());
    bind(RatingsService.class).to(RatingsServiceStub.class).in(LazySingletonScope.get());

    bind(AccountEdgeService.class).to(AccountEdgeServiceStub.class).in(LazySingletonScope.get());
    bind(AccountEdgeApiService.class).to(AccountEdgeApiServiceImpl.class).in(
        LazySingletonScope.get());
  }
}
