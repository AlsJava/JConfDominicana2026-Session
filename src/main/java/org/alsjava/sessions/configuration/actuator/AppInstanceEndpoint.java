package org.alsjava.sessions.configuration.actuator;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootVersion;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;

import java.net.InetAddress;

@SuppressWarnings("unused")
@Component
@Endpoint(id = "app-instance")
public class AppInstanceEndpoint {

    @Value("${app.id}")
    public String appId;

    @ReadOperation
    private InstanceInformation appInformationEndpoint() {
        String hostname = null;
        String hostIp = null;
        try {
            InetAddress localHost = InetAddress.getLocalHost();
            hostname = localHost.getHostName();
            hostIp = localHost.getHostAddress();
        }catch (Exception ignored) {
        }
        return InstanceInformation.builder()
                .appId(appId)
                .hostname(hostname)
                .hostIp(hostIp)
                .springBootVersion(SpringBootVersion.getVersion())
                .build();
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Getter
    @Setter
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class InstanceInformation {
        private String appId;
        private String hostname;
        private String hostIp;
        private String springBootVersion;
    }
}
