package com.portfolio.arithmetic.calculator.infrastructure.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "random-client",
        url = "${client.random.url}")
@Component
public interface RandomClient {
    @GetMapping("strings/?num={num}&len={len}&digits={digits}" +
                "&upperalpha={upperalpha}&loweralpha={loweralpha}" +
                "&unique={unique}&format=plain&rnd=new")
    public String getStrings(@PathVariable("num") int num,
                             @PathVariable("len") int len,
                             @PathVariable("digits") String digits,
                             @PathVariable("upperalpha") String upperalpha,
                             @PathVariable("loweralpha") String loweralpha,
                             @PathVariable("unique") String unique);
}
