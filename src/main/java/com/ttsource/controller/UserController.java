package com.ttsource.controller;

import com.ttsource.controller.presenter.UserPresenter;
import com.ttsource.controller.presenter.UserPresenter.IUserPresenter;
import com.ttsource.entities.UserDO;
import com.ttsource.jwt.annotation.User;
import com.ttsource.model.result.ResultInfo;
import com.ttsource.model.user.UserInfoDTO;
import com.ttsource.model.user.UserModifyVO;
import com.ttsource.service.UserInfoService;
import com.ttsource.util.ErrCode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
        this.mPresenter = new UserPresenter(this);
    }

    /**
     * 删除用户信息，该接口只支持单个删除。
     *
     * 1、只有系统管理员才拥有删除用户的权限。
     * 2、删除的用户不允许为当前用户
     */
    @RequestMapping(value = "/delete")
    @Transactional
    public ResultInfo deleteUserInfo(@User UserInfoDTO userInfo, @RequestParam("userId") Integer deleteUserId) {
        /*int role = userInfo.getRole();
        // 1 管理员
        if (role != 1 )
            return new ResultInfo(ErrCode.BAD_REQUEST, "", "不具备该权限");

        if (deleteUserId == null)
            return new ResultInfo(ErrCode.BAD_REQUEST, "", "参数错误");

        UserInfoDTO deleteUserInfo = userInfoService.findOneById(deleteUserId);
        if (deleteUserInfo == null || deleteUserId - deleteUserInfo.getId() != 0)
            return new ResultInfo(ErrCode.BAD_REQUEST, "", "该用户不存在");

        boolean deleteResult = userInfoService.deleteUserByIdOrUsername(deleteUserId, null);

        return new ResultInfo(deleteResult ? ErrCode.OK : ErrCode.INTERNAL_SERVER_ERROR, "", deleteResult ? "删除成功" : "删除失败");*/

        return  null;
    }

    /**
     * 该接口用于用户自己修改基本信息，包含：
     * nick_name, sex, age, height。
     * 不允许通过该接口修改：
     * username, password, role, state, img, mobile, address, description, email。
     */
    @RequestMapping(value = "/update/basic")
    @Transactional
    @PreAuthorize("hasRole('')")
    public ResultInfo updateUserInfo(@RequestBody UserModifyVO userInfo) {
        if (userInfo == null || userInfo.getUserId() <= 0)
            return new ResultInfo(ErrCode.BAD_REQUEST, "", "参数错误");

        UserDO user = userInfoService.findUserById(userInfo.getUserId());
        if (user == null)
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
     * 查找用户的详细信息，包括：
     * 1、基本信息
     * 2、权限信息
     * 3、配置信息
     * 4、宣传图片
     * 5、礼物详情
     */
    @RequestMapping(value = "/find/detail")
    public ResultInfo findUserDetailById(@RequestBody Map<String, Object> param) {
        try {
            Integer userId = (Integer) param.get("userId");
            if (userId == null) {
                return new ResultInfo(ErrCode.BAD_REQUEST, "", "参数错误");
            }

            UserInfoDTO userInfo = userInfoService.findUserDetailById(userId.longValue());
            if (null == userInfo) {
                return new ResultInfo(ErrCode.BAD_REQUEST, "", "查找的用户不存在");
            }

            return new ResultInfo(ErrCode.OK, userInfo, "查找成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultInfo(ErrCode.INTERNAL_SERVER_ERROR, e.getMessage(), "查找用户信息失败");
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
    private static ConcurrentHashMap<Long, UserInfoDTO> users = new ConcurrentHashMap<>();

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<UserInfoDTO> getUserList() {
        // 处理 "/users/"的请求，用来获取用户列表
        // 还可以通过@RequestParam从页面中传递参数来进行查询或者翻页信息的传递
        return new ArrayList<>(users.values());
    }

    @RequestMapping(value="/", method=RequestMethod.POST)
    public String postUser(@ModelAttribute UserInfoDTO user) {
        // 处理"/users/"的POST请求，用来创建User
        // 除了@ModelAttribute绑定参数之外，还可以通过@RequestParam从页面中传递参数
       /* users.put(user.getId(), user);*/
        return "success";
    }

    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public UserInfoDTO getUser(@PathVariable Long id) {
        // 处理"/users/{id}"的GET请求，用来获取url中id值的User信息
        // url中的id可通过@PathVariable绑定到函数的参数中
        return users.get(id);
    }

    @RequestMapping(value="/{id}", method=RequestMethod.PUT)
    public String putUser(@PathVariable Long id, @ModelAttribute UserInfoDTO user) {
        // 处理"/users/{id}"的PUT请求，用来更新User信息
        /*UserInfoDTO u = users.get(id);
        u.setAge(user.getAge());
        users.put(id, u);*/
        return "success";
    }

    @RequestMapping(value="/{id}", method=RequestMethod.DELETE)
    public String deleteUser(@PathVariable Long id) {
        // 处理"/users/{id}"的DELETE请求，用来删除User
        /*users.remove(id);*/
        return "success";
    }

}
