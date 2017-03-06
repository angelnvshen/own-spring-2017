package own.stu.spring;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by dell on 2017/3/2.
 */
@Controller
@RequestMapping("test")
public class TestController {

    @ResponseBody
    @RequestMapping
    public String test(){
        return "success";
    }

    @RequestMapping("/zeroException")
    public int zeroException(){
        return 100/0;
    }
}
