package dutkercz.com.github.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/test")
public class TestLogController {

    private Logger logger = LoggerFactory.getLogger(TestLogController.class.getName());

    @GetMapping
    public String testLog(){
        logger.info("This is in INFO");
        logger.warn("This is a WANR");
        logger.debug("THis is a DEBUG");
        logger.error("This is a ERROR");
        return "Logs Generated successful";
    }
}
