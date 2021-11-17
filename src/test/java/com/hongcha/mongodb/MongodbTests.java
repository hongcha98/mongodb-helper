package com.hongcha.mongodb;

import com.hongcha.mongodb.core.annotation.Gt;
import com.hongcha.mongodb.core.annotation.Regex;
import com.hongcha.mongodb.core.service.UserService;
import com.hongcha.mongodb.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@SpringBootTest
class MongodbTests {
    @Autowired
    UserService userService;

    //添加
    @Test
    void add() {
        List<User> list = new LinkedList<>();
        for (int i = 10; i <= 25; i++) {
            list.add(new User(UUID.randomUUID().toString(), "demo" + i + "号", 10 + i));
        }
        userService.insertBatch(list);
    }

    //查找
    @Test
    void select() {
        List<User> select = userService.select(createUserParam());
        select.forEach(System.out::println);
    }

    // 修改
    @Test
    void update() {
        User user = new User();
        user.setName("demo1号");
        userService.update(createUserParam(), user);

    }

    // 删除
    @Test
    void delete() {
        userService.delete(createUserParam());

    }

    private UserParam createUserParam() {
        UserParam userParam = new UserParam();
        userParam.nickname = ".*demo1.*";
        userParam.age = 25;
        return userParam;
    }

}

class UserParam {
    //  构建 regex 条件 ， 字段名为name
    @Regex("name")
    public String nickname;
    // 构建 >
    @Gt
    public Integer age;
}
