package uc.dei.mse.supportivecare.config;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import uc.dei.mse.supportivecare.GeneratedByJHipster;

@RestController
@GeneratedByJHipster
public class WebConfigurerTestController {

    @GetMapping("/api/test-cors")
    public void testCorsOnApiPath() {}

    @GetMapping("/test/test-cors")
    public void testCorsOnOtherPath() {}
}
