package me.coldrain.learn.security;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestInfo {

    private String remoteAddress;
    private String sessionId;
    private LocalDateTime loginTime;
}
