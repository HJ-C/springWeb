package com.example.springweb.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // RestAPI용 컨트롤러 JSON을 반환!
// 일반 @Controller는 body에 html 뷰를 보내고 @RestController는 Json반환하거나 데이터를 반환

public class FirstApiController {

    @GetMapping("/api/hello")
    public String hello(){
        return "hello world";
    }
}
