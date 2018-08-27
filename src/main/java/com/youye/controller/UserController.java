package com.youye.controller;

import com.youye.controller.presenter.UserPresenter;
import com.youye.controller.presenter.UserPresenter.IUserPresenter;
import com.youye.jwt.annotation.User;
import com.youye.model.UserInfo;
import com.youye.model.result.ResultInfo;
import com.youye.service.UserInfoService;
import com.youye.util.ErrCode;
import com.youye.util.StringUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
@RequestMapping(value = "/user")
public class UserController implements IUserPresenter {

    private UserInfoService userInfoService;

    private UserPresenter mPresenter;

    @Autowired
    public UserController(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
        this.mPresenter = new UserPresenter(this, userInfoService);
    }

    /**
     * 删除用户信息，该接口只支持单个删除。
     *
     * 1、只有系统管理员才拥有删除用户的权限。
     * 2、删除的用户不允许为当前用户
     */
    @RequestMapping(value = "/delete")
    @Transactional
    public ResultInfo deleteUserInfo(@User UserInfo userInfo, @RequestParam("userId") Integer deleteUserId) {
        int role = userInfo.getRole();
        // 1 管理员
        if (role != 1 )
            return new ResultInfo(ErrCode.BAD_REQUEST, "", "不具备该权限");

        if (deleteUserId == null)
            return new ResultInfo(ErrCode.BAD_REQUEST, "", "参数错误");

        UserInfo deleteUserInfo = userInfoService.findOneById(deleteUserId);
        if (deleteUserInfo == null || deleteUserId - deleteUserInfo.getId() != 0)
            return new ResultInfo(ErrCode.BAD_REQUEST, "", "该用户不存在");

        boolean deleteResult = userInfoService.deleteUserByIdOrUsername(deleteUserId, null);

        return new ResultInfo(deleteResult ? ErrCode.OK : ErrCode.INTERNAL_SERVER_ERROR, "", deleteResult ? "删除成功" : "删除失败");
    }

    /**
     * 修改用户基本信息，包含：
     * nick_name, sex, age, address, description, email。
     * 不允许通过该接口修改：
     * username, password, role, state, img, mobile
     */
    @RequestMapping(value = "/update/basic")
    @Transactional
    public ResultInfo updateUserInfo(@RequestBody UserInfo userInfo) {
        if (userInfo == null || userInfo.getId() <= 0)
            return new ResultInfo(ErrCode.BAD_REQUEST, "", "参数错误");

        UserInfo tempUserInfo = userInfoService.findOneById((int) (long) userInfo.getId());
        if (tempUserInfo == null)
            return new ResultInfo(ErrCode.BAD_REQUEST, "", "用户不存在");

        ResultInfo resultInfo = mPresenter.isValidUpdateUserBasicInfo(userInfo);
        if (resultInfo != null)
            return resultInfo;

        boolean isSuccess;
        try {
            isSuccess = userInfoService.updateUserBasicInfo(userInfo);
        } catch (Exception e) {
            isSuccess = false;
            e.printStackTrace();
        }

        if (isSuccess)
            resultInfo = new ResultInfo(ErrCode.OK, userInfo, "更新成功");
        else
            resultInfo = new ResultInfo(ErrCode.INTERNAL_SERVER_ERROR, "", "更新失败，服务器错误");

        return resultInfo;
    }

    /**
     * 修改用户名(登录用户名)。
     * 1、用户名只运行修改一次，及从手机号码修改为正式用户名。正式用户名不允许为手机号码、邮箱(防止恶意占用别人的手机号码或邮箱)
     * 2、用户名在库中存在唯一性。不允许存在相同的用户名。
     * 3、正式用户名不允许未纯数字。不允许存在汉字、特殊字符。
     */
    @RequestMapping(value = "/update/username")
    @Transactional
    public ResultInfo updateUsername(@RequestBody UserInfo userInfo) {
        try {
            if (userInfo == null || userInfo.getId() <= 0 || StringUtil.isEmpty(userInfo.getUsername()))
                return new ResultInfo(ErrCode.BAD_REQUEST, "", "参数错误");

            UserInfo tempUserInfo = userInfoService.findOneById((int) (long) userInfo.getId());
            if (tempUserInfo == null)
                return new ResultInfo(ErrCode.BAD_REQUEST, "", "用户不存在");

            String currUsername = userInfo.getUsername().trim();
            //当前用户名既不是手机号码，也不是邮箱地址则不允许进行修改
            if (!StringUtil.isMobileNo(currUsername) && !StringUtil.isEmail(currUsername))
                return new ResultInfo(ErrCode.BAD_REQUEST, "", "用户名只允许修改一次");

            String username = userInfo.getUsername().trim();
            ResultInfo resultInfo = mPresenter.isValidFormalUsername(username);
            if (resultInfo != null)
                return resultInfo;

            boolean isSuccess = userInfoService.updateFormalUsername(userInfo);
            if (!isSuccess)
                throw new RuntimeException("修改失败");
            return new ResultInfo(ErrCode.OK, userInfo, "修改用户名成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultInfo(ErrCode.INTERNAL_SERVER_ERROR, e.getMessage(), "修改用户名成功");
        }
    }

    /**
     * 查找推荐的主播信息
     */
    @RequestMapping(value = "/recommend", method = RequestMethod.GET)
    public ResultInfo findRecommend() {
        try {
            return new ResultInfo(ErrCode.OK, userInfoService.findAllAnchor(), "获取成功");
        } catch (Exception e) {
            return new ResultInfo(ErrCode.INTERNAL_SERVER_ERROR, "", "获取失败");
        }
    }

    /**
     * 查找推荐的主播信息
     */
    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public ResultInfo findNew() {
        try {
            return new ResultInfo(ErrCode.OK, userInfoService.findAllAnchor(), "获取成功");
        } catch (Exception e) {
            return new ResultInfo(ErrCode.INTERNAL_SERVER_ERROR, "", "获取失败");
        }
    }

    /**
     * 查找推荐的主播信息
     */
    @RequestMapping(value = "/active", method = RequestMethod.GET)
    public ResultInfo findActive() {
        try {
            return new ResultInfo(ErrCode.OK, userInfoService.findAllAnchor(), "获取成功");
        } catch (Exception e) {
            return new ResultInfo(ErrCode.INTERNAL_SERVER_ERROR, "", "获取失败");
        }
    }

    // 创建线程安全的Map
    private static ConcurrentHashMap<Long, UserInfo> users = new ConcurrentHashMap<>();

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<UserInfo> getUserList() {
        // 处理 "/users/"的请求，用来获取用户列表
        // 还可以通过@RequestParam从页面中传递参数来进行查询或者翻页信息的传递
        return new ArrayList<>(users.values());
    }

    @RequestMapping(value="/", method=RequestMethod.POST)
    public String postUser(@ModelAttribute UserInfo user) {
        // 处理"/users/"的POST请求，用来创建User
        // 除了@ModelAttribute绑定参数之外，还可以通过@RequestParam从页面中传递参数
        users.put(user.getId(), user);
        return "success";
    }

    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public UserInfo getUser(@PathVariable Long id) {
        // 处理"/users/{id}"的GET请求，用来获取url中id值的User信息
        // url中的id可通过@PathVariable绑定到函数的参数中
        return users.get(id);
    }

    @RequestMapping(value="/{id}", method=RequestMethod.PUT)
    public String putUser(@PathVariable Long id, @ModelAttribute UserInfo user) {
        // 处理"/users/{id}"的PUT请求，用来更新User信息
        UserInfo u = users.get(id);
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
        Integer count = userInfoService.getAllUsers();
        return "count = " + count;
    }

}
