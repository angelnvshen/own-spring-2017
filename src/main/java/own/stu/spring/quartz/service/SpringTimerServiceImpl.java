package own.stu.spring.quartz.service;

import org.springframework.stereotype.Service;

/**
 * Created by dell on 2017/3/3.
 */
@Service
public class SpringTimerServiceImpl implements SpringTimerService{
    @Override
    public void print(String msg) {
        System.out.println(" ================= " + msg + " ================= ");
    }
}
