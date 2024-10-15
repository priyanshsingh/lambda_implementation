package controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestContoller {
    @GetMapping("/multiply")
    public int fun1(){
        return 1;
    }
}
