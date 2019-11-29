package com.arthur.awesome.community.advice;

import com.alibaba.fastjson.JSON;
import com.arthur.awesome.community.dto.ResponseDTO;
import com.arthur.awesome.community.exception.CustomizeErrorCode;
import com.arthur.awesome.community.exception.CustomizeException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@ControllerAdvice
public class CustomizeExceptionHandler {
    @ExceptionHandler(Exception.class)
    ModelAndView handle(HttpServletRequest request,
                        HttpServletResponse response,
                        Throwable ex,
                        Model model) {
        HttpStatus status = getStatus(request);

        final String contentType = request.getContentType();
        if ("application/json".equals(contentType)) {
            ResponseDTO responseDTO;
            if (ex instanceof CustomizeException) {
                responseDTO = ResponseDTO.errorOf((CustomizeException) ex);
            } else {
                responseDTO = ResponseDTO.errorOf(CustomizeErrorCode.SYSTEM_ERROR);
            }

            response.setContentType("application/json");
            response.setStatus(200);
            response.setCharacterEncoding("utf-8");
            try {
                final PrintWriter writer = response.getWriter();
                writer.write(JSON.toJSONString(responseDTO));
                writer.close();
            } catch (IOException e) {

            }
        } else {
            if (ex instanceof CustomizeException) {
                model.addAttribute("message", ex.getMessage());
            } else {
                model.addAttribute("message", CustomizeErrorCode.SYSTEM_ERROR.getMessage());
            }
        }
        return new ModelAndView("error");
    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }
}
