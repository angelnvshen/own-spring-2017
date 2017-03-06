package own.stu.spring.util;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import own.stu.spring.model.User;

import javax.annotation.Resource;

/**
 * Created by dell on 2017/3/3.
 */
@Repository
public class JdbcTemplateUtil {
    @Resource
    private JdbcTemplate jdbcTemplate;

    /**
     * 通过id获取user对象.
     * @param id
     * @return
     */
    public User getById(long id){
        String sql = "select *from User where id=?";
        RowMapper<User> rowMapper = new BeanPropertyRowMapper<User>(User.class);
        return jdbcTemplate.queryForObject(sql, rowMapper,id);
    }

    public void save(User user){
        String sql = "insert into User(name,age) values(?,?)";
        jdbcTemplate.update(sql, new Object[]{user.getName(),user.getAge()});
    }
}
