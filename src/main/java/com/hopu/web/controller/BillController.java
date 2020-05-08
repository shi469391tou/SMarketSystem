package com.hopu.web.controller;

import com.github.pagehelper.PageInfo;
import com.hopu.domain.Bill;
import com.hopu.domain.Provider;
import com.hopu.domain.User;
import com.hopu.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/bill")
public class BillController {
    @Autowired
    private BillService billService;

    // 跳转到添加订单的页面
    @GetMapping("/add")
    public String toAddPage(){
        return "bill/bill_add";
    }
    @PostMapping("/add")
    public String add(Bill bill,HttpSession session){
        // 被当前登录用户所创建
        User login_user = (User) session.getAttribute("login_user");
        bill.setCreatedBy(login_user.getId());

        billService.add(bill);

        return "redirect:/bill/findAll";
    }

    // 列表分页查询
    @RequestMapping("/findAll")
    public String findAll(@RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
                          @RequestParam(value = "pageSize",defaultValue = "5") Integer pageSize,
                          @RequestParam(value = "productName",required = false) String productName,
                          @RequestParam(value = "provider_id",required = false) Integer provider_id,
                          @RequestParam(value = "isPayment",required = false) Integer isPayment,
                          Model model){
        if(provider_id!=null && provider_id==0){
            provider_id=null;
        }
        if(isPayment!=null && isPayment==0){
            isPayment=null;
        }
        PageInfo<Bill> pageBean =billService.findAll(pageNum,pageSize,productName,provider_id,isPayment);
        model.addAttribute("pageBean",pageBean);
        model.addAttribute("productName",productName);
        model.addAttribute("provider_id",provider_id);
        model.addAttribute("isPayment",isPayment);
         // 返回视图
        return "bill/bill_list";
    }
}
