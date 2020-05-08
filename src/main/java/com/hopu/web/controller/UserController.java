package com.hopu.web.controller;

import com.hopu.domain.User;
import com.hopu.service.UserService;
import com.hopu.utils.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    // 跳转到用户添加页码
    @RequestMapping("/toUserAddPage")
    public String toUserAddPage(){
        return "user/user_add";
    }
    // 添加用户
    @RequestMapping("/addUser")
    public String addUser(User user,HttpSession session){
        // 被当前登录用户所创建
        User login_user = (User) session.getAttribute("login_user");
        user.setCreatedBy(login_user.getId());
        userService.add(user);
        // 添加完成之后，重定向到查询所有页面
        return "redirect:/user/findAll";
    }


    // 用户登录
    @RequestMapping("/login")
    public String login(String userCode, String userPassword, Model model, HttpSession session){
        User user= userService.login(userCode,userPassword);
        // 判断用户是否登录成功
        if(user==null){
            // 登录失败
            model.addAttribute("loginError","用户名或密码错误，请重新登！");
            return "forward:/login.jsp";
        }else {
            // 先把数据放在session域对象中
            // 尽量不要直接把密码放在session或者cookie中
            session.setAttribute("login_user",user);
            // 重定向到查询所有用户，然后跳转到用户列表页面
            return "redirect:/user/findAll";
        }
    }
    // 查询所有用户
//    @RequestMapping("/findAll")
//    public String login(@RequestParam(value ="pageNum",defaultValue = "1") String pageNum,
//                        @RequestParam(value = "pageSize",defaultValue = "5") String pageSize,
//                        Model model){
//        // 查询分页对象
//        PageBean<User> pageBean =userService.findByPage(pageNum,pageSize);
//        // 存储在request域对象中
//        model.addAttribute("pageBean",pageBean);
//        // 响应到用户列表页面
//        return "user/user_list";
//    }
    @RequestMapping("/findAll")
    public String login(@RequestParam(value ="pageNum",defaultValue = "1") String pageNum,
                        @RequestParam(value = "pageSize",defaultValue = "5") String pageSize,
                        @RequestParam(value = "userName",required = false) String userName,
                        @RequestParam(value = "role.id",required = false) String roleId,
                        Model model){
        // 查询分页对象
        PageBean<User> pageBean =userService.findByPage(pageNum,pageSize,userName,roleId);
        // 存储在request域对象中
        model.addAttribute("pageBean",pageBean);
        model.addAttribute("userName",userName);
        model.addAttribute("roleId",roleId);
        // 响应到用户列表页面
        return "user/user_list";
    }

    // 用户退出
    @RequestMapping("/logout")
    public String logout(HttpSession session){
        // 清除session中的登录对象
        session.removeAttribute("login_user");
        // 回到首页或者登录页
        return "redirect:/login.jsp";
    }

    // 用户注册
    @RequestMapping("/register")
    public String logout(){
       // 用户注册的头像处理
        // 获取的头像，将头像图片保存在某个位置：本地项目某个目录下、某个磁盘空间下、静态资服务器目录下
        // 接着，要把用户以及头像的地址存在数据库。

        return "redirect:/login.jsp";
    }
}
