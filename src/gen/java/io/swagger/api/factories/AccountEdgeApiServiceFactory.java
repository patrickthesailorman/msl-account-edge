package io.swagger.api.factories;

import io.swagger.api.AccountEdgeApiService;
import io.swagger.api.impl.AccountEdgeApiServiceImpl;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JaxRSServerCodegen", date = "2016-01-25T12:48:02.255-06:00")
public class AccountEdgeApiServiceFactory {

   private final static AccountEdgeApiService service = new AccountEdgeApiServiceImpl();

   public static AccountEdgeApiService getAccountEdgeApi()
   {
      return service;
   }
}
