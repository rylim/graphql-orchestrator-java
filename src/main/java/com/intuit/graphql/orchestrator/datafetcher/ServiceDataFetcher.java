package com.intuit.graphql.orchestrator.datafetcher;

import com.intuit.graphql.orchestrator.schema.ServiceMetadata;
import graphql.GraphQLContext;
import graphql.schema.DataFetchingEnvironment;
import lombok.Getter;

@Getter
public class ServiceDataFetcher implements ServiceAwareDataFetcher {

  /**
   * One of Query or Mutation or Subscription
   */
  private final ServiceMetadata serviceMetadata;


  public ServiceDataFetcher(ServiceMetadata serviceMetadata) {
    this.serviceMetadata = serviceMetadata;
  }

  @Override
  public Object get(final DataFetchingEnvironment environment) {
    String dfeFieldName = environment.getField().getName();
    GraphQLContext context = environment.getContext();
    context.put(dfeFieldName, this.serviceMetadata);
    return environment
        .getDataLoader(serviceMetadata.getServiceProvider().getNameSpace())
        .load(environment);
  }

  @Override
  public String getNamespace() {
    return this.serviceMetadata.getServiceProvider().getNameSpace();
  }
}
