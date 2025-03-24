package dev.pm.billingservice.grpc;

import io.grpc.*;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.interceptor.GrpcGlobalServerInterceptor;
import org.springframework.context.annotation.Configuration;

/**
 * @author Nelson Tanko
 */
@Slf4j
@Configuration
public class GrpcServerConfig {

    @GrpcGlobalServerInterceptor
    public ServerInterceptor logServerInterceptor() {
        return new LogGrpcInterceptor();
    }

    private static class LogGrpcInterceptor implements ServerInterceptor {

        @Override
        public  ServerCall.Listener interceptCall(ServerCall call, Metadata headers, ServerCallHandler next) {

            log.info("gRPC call: {}", call.getMethodDescriptor().getFullMethodName());
            return next.startCall(call, headers);
        }
    }
}