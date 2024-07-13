package com.cloudNative._4.FinalProject.controller;

import com.cloudNative._4.FinalProject.VO.ResultVO;
import com.google.common.util.concurrent.RateLimiter;
import org.springframework.beans.factory.annotation.Autowired;


import com.cloudNative._4.FinalProject.service.GreetingService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/greeting")
public class GreetingController {
    @Autowired
    GreetingService greetingService;

    @Autowired
    RateLimiter rateLimiter;
    
    
    @GetMapping
    public ResultVO<String> getGreeting(@RequestParam("message") String message) {
        System.out.println(1);
        if (!rateLimiter.tryAcquire()) {
            // 如果没有获取到令牌，返回429状态码
            return ResultVO.buildFailure("Too many requests","429");
        }
        return ResultVO.buildSuccess(greetingService.getGreeting(message));
    }
    
    

}
