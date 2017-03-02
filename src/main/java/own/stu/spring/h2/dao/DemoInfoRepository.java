package own.stu.spring.h2.dao;

import org.springframework.data.repository.CrudRepository;
import own.stu.spring.h2.model.DemoInfo;

public interface DemoInfoRepository extends CrudRepository<DemoInfo, Long> {

}