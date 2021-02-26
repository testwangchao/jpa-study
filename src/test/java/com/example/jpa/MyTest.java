package com.example.jpa;

import com.example.jpa.pojo.User;
import com.example.jpa.pojo.User2;
import com.example.jpa.utils.Result;
import com.example.jpa.utils.ResultUtils;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import com.alibaba.fastjson.JSONObject;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.Assert.*;

public class MyTest {

    @Test
    public void test1() {
        Optional<String> s = Optional.of("test");
        System.out.println(Optional.of("TEST"));
        System.out.println(s.map(String::toUpperCase));
        assertEquals(Optional.of("TEST"), s.map(String::toUpperCase));
    }

    @Test
    public void test2() {
        System.out.println(Optional.of("a").map(s -> "STRING"));
        assertEquals(Optional.of(Optional.of("STRING")),
                Optional.of("a")
                        .map(s -> Optional.of("STRING")));
    }

    @Test
    public void test3() {
        System.out.println(Optional.of(Optional.of("STRING")).flatMap(s -> Optional.of("STRING")));
        assertEquals(Optional.of("STRING"),
                Optional.of("string")
                .flatMap(s -> Optional.of("STRING")));
    }

    static List<User2> getUserList() {
        List<User2> userList = new ArrayList<>();
        userList.add(new User2(22, "王旭", "wang.xu","123456", 10, true));
        userList.add(new User2(21, "孙萍", "sun.ping","a123456", 11, false));
        userList.add(new User2(23, "步传宇", "bu.zhuan.yu", "b123456", 12, false));
        userList.add(new User2(18, "蔡明浩",  "cai.ming.hao","c123456", 22, true));
        userList.add(new User2(17, "郭林杰", "guo.lin.jie", "d123456", 13, false));
        userList.add(new User2(29, "韩凯", "han.kai", "e123456", 18, true));
        userList.add(new User2(22, "韩天琪",  "han.tian.qi","f123456", 20, false));
        userList.add(new User2(21, "郝玮",  "hao.wei","g123456", 30, false));
        userList.add(new User2(19, "胡亚强",  "hu.ya.qing","h123456", 45, false));
        userList.add(new User2(14, "季恺",  "ji.kai","i123456", 26, false));
        userList.add(new User2(17, "荆帅",  "jing.shuai","j123456", 36, true));
        userList.add(new User2(16, "姜有琪",  "jiang.you.qi","k123456", 12, false));
        return userList;
    }

    static String getOutput(String input) {
        return input == null ? null : "output for " + input;
    }

    static Optional<String> getOutputOpt(String input) {
        return input == null ? Optional.empty() : Optional.of("output for " + input);
    }

    @Test
    public void test5() {
        Optional<String> s = Optional.of("input");
//        s.map(x -> {return x;}).ifPresent(System.out::println);
        System.out.println(s.map(x -> {return "input";}));
        System.out.println(s.map(MyTest::getOutput));
        System.out.println(s.flatMap(MyTest::getOutputOpt));
    }

    @Test
    public void test6() {
        Optional<List<User2>> userList = Optional.ofNullable(MyTest.getUserList());
        // 在集合中有年龄大于 18 岁的才会返回所有对象
        userList.filter(t->t.stream().anyMatch(u->u.getAge()>18))
                .ifPresent((u)->u.forEach(System.out::println));

    }

    @Test
    public void test7() {

        /*
        ofNullable(null)不会报错
        of(null) 会有空指针异常
         */
//        Optional<List<User2>> userList = Optional.ofNullable(MyTest.getUserList());
        List<User2> userList = new ArrayList<>();

        /*
        userList.ifPresent()
        ifPresent 表示 Optional中的对象存在才会执行 Consumer 接口对象中的方法
        当userList不为空时，才会走到ifPresent里面，否则不会执行ifPresent内的代码
         */
//        userList.ifPresent((u)->u.stream().filter(s->s.getAge()>18).forEach(System.out::println));
        Optional.ofNullable(null).ifPresent(t->System.out.println(11111));
    }

    /*
    返回 optional 中的对象年龄在 18 岁以上的
     */
    @Test
    public void test8(){
        Optional<List<User2>> userList = Optional.ofNullable(MyTest.getUserList());
        System.out.println(
                userList.map(t -> {
                            List<User2> tempList = new ArrayList<>();
                            t.stream().filter( u -> u.getAge()>5666).forEach(tempList::add);
                            return null;
                        }
                )
        );
        userList.map(t -> {
            List<User2> tempList = new ArrayList<>();
            t.stream().filter( u -> u.getAge()>18).forEach(tempList::add);
            return tempList;
        }
        ).ifPresent(t -> {
            t.forEach(System.out::println);
        });
    }

    @Test
    public void test9() {
        // 定义一个Optional字符串对象
        Optional<String> s = Optional.of("Hello");
        /*
        map: 将 Optional 中的对象转成其他对象，或者修改对象中的属性
        flatMap: 将 Optional 中的对象转成Optional对象，或者修改对象中的属性, flatmap只能返回Optional 对象
         */
        ;  // d32e434
        System.out.println(s.flatMap(t -> {
            return Optional.of(new User2(16, "姜有琪",  "jiang.you.qi","k123456", 12, false));
        }));
        ;
//        s.map( t -> {
//            return "d32e434";
//        }).ifPresent(System.out::println);
    }

    @Test
    public void test10() {
        User2 user2 = new User2(16, "姜有琪",  "jiang.you.qi","k123456", 12, false);
        User user = new User();
        org.springframework.beans.BeanUtils
                .copyProperties(user2, user);
        System.out.println(user);

    }
}
