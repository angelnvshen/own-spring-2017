package own.stu.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by dell on 2017/3/2.
 */
@Controller
@RequestMapping("test")
public class TestController {

    @Autowired
    private Environment environment;

    @ResponseBody
    @RequestMapping
    public String test(){
        String test = environment.getProperty("spring.test.property");
        if(StringUtils.isEmpty(test))
            test = "no found in properties file ";
        return "success" + " : " + test;
    }
}
