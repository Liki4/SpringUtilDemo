package icu.Liki4.SpringUtilDemo.util;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONException;
import com.alibaba.fastjson2.JSONReader;
import com.alibaba.fastjson2.filter.Filter;
import org.springframework.context.annotation.Lazy;

import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

public class InvokeUtils {

    @Lazy
    private static final Filter autoTypeFilter = JSONReader.autoTypeFilter(
            Arrays.stream(SpringContextHolder.getApplicationContext().getBeanDefinitionNames())
                    .map(name -> {
                        int secondDotIndex = name.indexOf('.', name.indexOf('.')+1);
                        return secondDotIndex != -1 ? name.substring(0, secondDotIndex + 1) : null;
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet())
                    .toArray(new String[0])
    );

    public static Object invokeBeanMethod(String beanName, String methodName, Map<String, Object> params) throws Exception {
        Object beanObject = SpringContextHolder.getBean(beanName);
        Method beanMethod = Arrays.stream(beanObject.getClass().getMethods())
                .filter(method -> method.getName().equals(methodName))
                .findFirst()
                .orElse(null);

        if (beanMethod.getParameterCount() == 0) {
            return beanMethod.invoke(beanObject);
        } else {
            String[] parameterTypes = new String[beanMethod.getParameterCount()];
            Object[] parameterArgs = new Object[beanMethod.getParameterCount()];
            for (int i = 0; i < beanMethod.getParameters().length; i++) {
                Class<?> parameterType = beanMethod.getParameterTypes()[i];
                String parameterName = beanMethod.getParameters()[i].getName();
                parameterTypes[i] = parameterType.getName();
                if (!parameterType.isPrimitive()
                        && !Date.class.equals(parameterType)
                        && !Long.class.equals(parameterType)
                        && !Integer.class.equals(parameterType)
                        && !Boolean.class.equals(parameterType)
                        && !Double.class.equals(parameterType)
                        && !Float.class.equals(parameterType)
                        && !Short.class.equals(parameterType)
                        && !Byte.class.equals(parameterType)
                        && !Character.class.equals(parameterType)
                        && !String.class.equals(parameterType)
                        && !List.class.equals(parameterType)
                        && !Set.class.equals(parameterType)
                        && !Map.class.equals(parameterType)
                ) {
                    if (params.containsKey(parameterName)) {
                        parameterArgs[i] = JSON.parseObject(JSON.toJSONString(params.get(parameterName)), parameterType, autoTypeFilter);
                    } else {
                        try {
                            parameterArgs[i] = JSON.parseObject(JSON.toJSONString(params), parameterType, autoTypeFilter);
                        } catch (JSONException jsonException) {
                            for (Map.Entry<String, Object> entry : params.entrySet()) {
                                Object value = entry.getValue();
                                if (value instanceof String) {
                                    if (((String) value).contains("\"")) {
                                        params.put(entry.getKey(), JSON.parse((String) value));
                                    }
                                }
                            }
                            parameterArgs[i] = JSON.parseObject(JSON.toJSONString(params), parameterType, autoTypeFilter);
                        }
                    }
                } else {
                    parameterArgs[i] = params.getOrDefault(parameterName, null);
                }
            }
            return beanMethod.invoke(beanObject, parameterArgs);
        }
    }
}
