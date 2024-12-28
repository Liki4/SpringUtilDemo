package icu.Liki4.SpringUtilDemo.controller;

import com.alibaba.fastjson2.JSON;
import icu.Liki4.SpringUtilDemo.base.BaseResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;
import java.util.Objects;

import static icu.Liki4.SpringUtilDemo.util.InvokeUtils.*;

@Controller
@RequestMapping(value="/api")
public class APIGatewayController {

    @RequestMapping(value="/gateway", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse doPost(HttpServletRequest request) throws Exception {
        try {
            String body = IOUtils.toString(request.getReader());
            Map<String, Object> map = JSON.parseObject(body, Map.class);

            String beanName = (String) map.get("beanName");
            String methodName = (String) map.get("methodName");
            Map<String, Object> params = (Map<String, Object>) map.get("params");

            Object result = invokeBeanMethod(beanName, methodName, params);
            return new BaseResponse(200, null, result);
        } catch (Exception e) {
            return new BaseResponse(500, Objects.requireNonNullElse(e.getCause(), e).getMessage(), null);
        }
    }
}
