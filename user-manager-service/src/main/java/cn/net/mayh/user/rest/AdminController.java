package cn.net.mayh.user.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description:
 *
 * @author mayh
 * @version : 1.0
 * @date 2021/1/1
 **/
@RestController
@RequestMapping("/admin")
public class AdminController {
    @GetMapping("/demo")
    public String demo() {
        return "spring security demo";
    }
}
