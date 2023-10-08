package com.portfolio.arithmetic.calculator.infrastructure.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "random", url = "${client.random.url}")
@Component
public interface RandomClient {
    @GetMapping("strings/?num=10&len=10&digits=on&upperalpha=on&loweralpha=on&unique=on&format=plain&rnd=new")
    public String getStrings();
}
