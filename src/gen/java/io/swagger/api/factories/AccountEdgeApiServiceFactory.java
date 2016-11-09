package io.swagger.api.factories;

import com.google.inject.Inject;
import io.swagger.api.AccountEdgeApiService;
import io.swagger.api.impl.AccountEdgeApiServiceImpl;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JaxRSServerCodegen", date = "2016-01-25T12:48:02.255-06:00")
public class AccountEdgeApiServiceFactory {

   @Inject
   public static AccountEdgeApiService service;

   public static AccountEdgeApiService getAccountEdgeApi()
   {
      return service;
   }
}
