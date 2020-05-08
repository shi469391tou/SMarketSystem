package com.hopu.web.controller;

import com.github.pagehelper.PageInfo;
import com.hopu.domain.Provider;
import com.hopu.domain.User;
import com.hopu.service.ProvderServcie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/provider")
public class ProviderController {
    @Autowired
    private ProvderServcie provderServcie;

    @RequestMapping("/findAllProvider")
    @ResponseBody
    public List<Provider> findAllProvider(){
        List<Provider> providerList= provderServcie.findAllProvider();
        return providerList;
    }

    // 分页查询所有供应商
    @RequestMapping("/findAllByPage")
    public String findAllByPage(@RequestParam(value = "pageNum",defaultValue = "1") String pageNum,
                                @RequestParam(value = "pageSize",defaultValue = "5") String pageSize,
                                @RequestParam(value = "proCode",required = false) String proCode,
                                @RequestParam(value = "proName",required = false) String proName,
                                Model model){
        // 参数类型转换
        int num = Integer.parseInt(pageNum);
        int size = Integer.parseInt(pageSize);

        PageInfo<Provider> pageBean =provderServcie.findAllByPage(num,size,proCode,proName);
        model.addAttribute("pageBean",pageBean);
        model.addAttribute("proCode",proCode);
        model.addAttribute("proName",proName);
        // 视图跳转
        return "provider/provider_list";
    }

    // 跳转到添加供应商页面
    @GetMapping("/add")
    public String toAddPage(){
        return "provider/provider_add";
    }
    // 跳转到添加供应商页面
    @PostMapping("/add")
    public String add(Provider provider, HttpSession session){
        // 被当前登录用户所创建
        User login_user = (User) session.getAttribute("login_user");
        provider.setCreatedBy(login_user.getId());
        provderServcie.add(provider);
        return "redirect:/provider/findAllByPage";
    }
    // 跳转到修改供应商页面
    @GetMapping("/update")
    public String toUpdatePage(String id,Model model){
        Provider provider=provderServcie.findById(id);
        model.addAttribute("provider",provider);
        return "provider/provider_update";
    }
    // 跳转到添加供应商页面
    @PostMapping("/update")
    public String update(Provider provider, HttpSession session){
        // 被当前登录用户所创建
        User login_user = (User) session.getAttribute("login_user");
        provider.setModifyBy(login_user.getId());
        provderServcie.update(provider);
        return "redirect:/provider/findAllByPage";
    }

    // 删除供应商
    @GetMapping("/delete")
    public String delete(String id){
        provderServcie.delete(id);

        return "redirect:/provider/findAllByPage";
    }

    // 查看供应商
    @GetMapping("/view")
    public String view(String id,Model model){
        Provider provider = provderServcie.findById(id);
        model.addAttribute("provider",provider);
        return "provider/provider_view";
    }
}

