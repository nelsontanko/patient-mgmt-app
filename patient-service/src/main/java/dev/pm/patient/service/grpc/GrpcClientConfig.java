package dev.pm.patient.service.grpc;

import io.grpc.*;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.interceptor.GrpcGlobalClientInterceptor;
import org.springframework.context.annotation.Configuration;

/**
 * @author Nelson Tanko
 */
@Slf4j
@Configuration
public class GrpcClientConfig {

    @GrpcGlobalClientInterceptor
    public ClientInterceptor logClientInterceptor() {
        return new LogGrpcClientInterceptor();
    }

    private static class LogGrpcClientInterceptor implements ClientInterceptor {
        @Override
        public ClientCall interceptCall(MethodDescriptor method, CallOptions callOptions, Channel next) {

            log.info("gRPC client call: {}",method.getFullMethodName());
            return next.newCall(method, callOptions);
        }
    }
}
