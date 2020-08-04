package com.taim.taimcustomerservicelambda.handlers;

import com.amazonaws.serverless.exceptions.ContainerInitializationException;
import com.amazonaws.serverless.proxy.internal.testutils.Timer;
import com.amazonaws.serverless.proxy.model.AwsProxyRequest;
import com.amazonaws.serverless.proxy.model.AwsProxyResponse;
import com.amazonaws.serverless.proxy.spring.SpringBootLambdaContainerHandler;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.taim.taimcustomerservicelambda.TaimCustomerServiceLambdaApplication;
import com.taim.taimcustomerservicelambda.filters.CognitoIdentityFilter;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.EnumSet;

@Slf4j
public class StreamLambdaHandler implements RequestStreamHandler {

    private static SpringBootLambdaContainerHandler<AwsProxyRequest, AwsProxyResponse> handler;

    static {
        try {
            handler = SpringBootLambdaContainerHandler.getAwsProxyHandler(TaimCustomerServiceLambdaApplication.class);

            // For applications that take longer than 10 seconds to start, use the async builder:
            // long startTime = Instant.now().toEpochMilli();
            // handler = new SpringBootProxyHandlerBuilder()
            //                    .defaultProxy()
            //                    .asyncInit(startTime)
            //                    .springBootApplication(Application.class)
            //                    .buildAndInitialize();

            // we use the onStartup method of the handler to register our custom filter
            handler.onStartup(servletContext -> {
                FilterRegistration.Dynamic registration =
                        servletContext.addFilter("CognitoIdentityFilter", CognitoIdentityFilter.class);
                registration.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), true, "/*");
            });
        } catch (ContainerInitializationException e) {
            // if we fail here. We re-throw the exception to force another cold start
            log.error(e.getMessage());
            throw new RuntimeException("Could not initialize Spring Boot application", e);
        }
    }

    public StreamLambdaHandler() {
        // we enable the timer for debugging. This SHOULD NOT be enabled in production.
        Timer.enable();
    }

    @Override
    public void handleRequest(InputStream inputStream, OutputStream outputStream, Context context)
            throws IOException {
        handler.proxyStream(inputStream, outputStream, context);
    }
}
