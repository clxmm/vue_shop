package org.clxmm.vueshopservice.util;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author clx
 * @date 2020-08-11 20:45
 */
public class ParamUtil {


    public static Map<String, String> getParameterMap(HttpServletRequest request) {
        Map<String, String> map = new HashMap<>();
        Enumeration<String> enumeration = request.getParameterNames();
        while (enumeration.hasMoreElements()) {
            String propertyName = enumeration.nextElement();
            String propertyValue = request.getParameter(propertyName)
                    .trim();
            map.put(propertyName, propertyValue);
        }
        return map;
    }
}
