package com.youye.controller;

import com.youye.model.User;
import com.youye.service.UserService;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * **********************************************
 * <p/>
 * Date: 2018-04-25 15:56
 * <p/>
 * Author: SinPingWu
 * <p/>
 * Email: wuxinping@ubinavi.com.cn
 * <p/>
 * brief:
 * <p/>
 * history:
 * <p/>
 * **********************************************
 */
@RestController
@RequestMapping(value = "/users")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 查找推荐的主播信息
     */
    @RequestMapping(value = "/recommend", method = RequestMethod.GET)
    public List<User> findRecommend() {
        return userService.findAllAnchor();
    }

    /**
     * 查找推荐的主播信息
     */
    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public List<User> findNew() {
        return userService.findAllAnchor();
    }

    /**
     * 查找推荐的主播信息
     */
    @RequestMapping(value = "/active", method = RequestMethod.GET)
    public List<User> findActive() {
        return userService.findAllAnchor();
    }

    // 创建线程安全的Map
    private static ConcurrentHashMap<Long, User> users = new ConcurrentHashMap<>();

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<User> getUserList() {
        // 处理 "/users/"的请求，用来获取用户列表
        // 还可以通过@RequestParam从页面中传递参数来进行查询或者翻页信息的传递
        return new ArrayList<>(users.values());
    }

    @RequestMapping(value="/", method=RequestMethod.POST)
    public String postUser(@ModelAttribute User user) {
        // 处理"/users/"的POST请求，用来创建User
        // 除了@ModelAttribute绑定参数之外，还可以通过@RequestParam从页面中传递参数
        users.put(user.getId(), user);
        return "success";
    }

    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public User getUser(@PathVariable Long id) {
        // 处理"/users/{id}"的GET请求，用来获取url中id值的User信息
        // url中的id可通过@PathVariable绑定到函数的参数中
        return users.get(id);
    }

    @RequestMapping(value="/{id}", method=RequestMethod.PUT)
    public String putUser(@PathVariable Long id, @ModelAttribute User user) {
        // 处理"/users/{id}"的PUT请求，用来更新User信息
        User u = users.get(id);
        u.setAge(user.getAge());
        users.put(id, u);
        return "success";
    }

    @RequestMapping(value="/{id}", method=RequestMethod.DELETE)
    public String deleteUser(@PathVariable Long id) {
        // 处理"/users/{id}"的DELETE请求，用来删除User
        users.remove(id);
        return "success";
    }

    @RequestMapping("/count")
    public String getUserCount() {
        Integer count = userService.getAllUsers();
        return "count = " + count;
    }

}
