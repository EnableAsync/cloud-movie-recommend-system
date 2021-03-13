//package com.klaus.gateway.filter;
//
//import com.alibaba.fastjson.JSON;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.codec.Charsets;
//import org.reactivestreams.Publisher;
//import org.springframework.cloud.gateway.filter.GatewayFilterChain;
//import org.springframework.cloud.gateway.filter.GlobalFilter;
//import org.springframework.core.Ordered;WW
//import org.springframework.core.io.buffer.DataBuffer;
//import org.springframework.core.io.buffer.DataBufferFactory;
//import org.springframework.core.io.buffer.DataBufferUtils;
//import org.springframework.core.io.buffer.DefaultDataBufferFactory;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.server.reactive.ServerHttpResponse;
//import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
//import org.springframework.stereotype.Component;
//import org.springframework.web.server.ServerWebExchange;
//import reactor.core.publisher.Flux;
//import reactor.core.publisher.Mono;
//
//import java.nio.charset.Charset;
//import java.nio.charset.StandardCharsets;
//import java.util.HashMap;
//import java.util.Map;
//
//import static org.springframework.http.MediaType.APPLICATION_JSON;
//
//@Component
//@Slf4j
//public class LogGateWayFilter implements GlobalFilter, Ordered {
//    @Override
//    public int getOrder() {
//        return -2;
//    }
//
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//        ServerHttpResponse originalResponse = exchange.getResponse();
//        DataBufferFactory bufferFactory = originalResponse.bufferFactory();
//        ServerHttpResponseDecorator decoratedResponse = new ServerHttpResponseDecorator(originalResponse) {
//
//            @Override
//            public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
//                if (!APPLICATION_JSON.isCompatibleWith(exchange.getResponse().getHeaders().getContentType())) {
//                    if (body instanceof Flux) {
//                        Flux<? extends DataBuffer> fluxBody = (Flux<? extends DataBuffer>) body;
//                        return super.writeWith(fluxBody.map(dataBuffer -> {
//                            // probably should reuse buffers
//                            byte[] content = new byte[dataBuffer.readableByteCount()];
//                            dataBuffer.read(content);
//                            //释放掉内存
//                            DataBufferUtils.release(dataBuffer);
//                            Map<String, Object> map = new HashMap<>();
//                            map.put("success", false);
//                            log.warn(map.toString());
//                            byte[] uppedContent = new String(JSON.toJSONBytes(map), StandardCharsets.UTF_8).getBytes();
//                            return bufferFactory.wrap(uppedContent);
//                        }));
//                    }
//                }
//                // if body is not a flux. never got there.
//                return super.writeWith(body);
//            }
//        };
//        // replace response with decorator
//        return chain.filter(exchange.mutate().response(decoratedResponse).build());
//    }
//}
