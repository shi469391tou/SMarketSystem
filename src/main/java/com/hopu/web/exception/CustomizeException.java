package com.hopu.web.exception;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class CustomizeException implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        // 对于具体异常肯定要处理（针对于程序员）
        // 把异常信息，通过流的形式写到某个日志文件中
        // 获取错误位置
        StackTraceElement stackTrace = e.getStackTrace()[0];
        // 获取错误信息
        String message = e.getMessage();
        // 获取错误日期
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 hh-mm-ss");
        String format = simpleDateFormat.format(new Date());
        // 以字符缓冲流的形式写到日志文件
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("D:\\error.log",true));
            bufferedWriter.write(stackTrace+";"+message+";"+format);
            bufferedWriter.newLine();

            bufferedWriter.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        // 这一部分，属于异常处理视图跳转(针对于访客)
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("error");
        return modelAndView;
    }
}
