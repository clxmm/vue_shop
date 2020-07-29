package org.clxmm.vueshopservice.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * 返回结果封装
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseBean {

    /**
     * 状态码
     */
    private int code;

    /**
     * 返回信息
     */
    private String msg;

    /**
     * 返回信息
     */
    private String token;

    /**
     * 返回的数据
     */
    private Object data;

    public ResponseBean() {
    }


    //成功返回
    public static ResponseBean success(){
        return success(null);
    }

    public static ResponseBean success(Object object){
        ResponseBean responseBean=new ResponseBean();
        responseBean.setCode(200);
        responseBean.setMsg("成功!");
        responseBean.setData(object);
        return responseBean;
    }


    /**
     * 失败返回
     * @param code
     * @param msg
     * @param data
     */
    public ResponseBean(int code, String msg, Object data) {
        this.code=code;
        this.msg=msg;
        this.data=data;
    }

    public ResponseBean(ResponseCode responseCode, Object data) {
        this.code = responseCode.getCode();
        this.msg = responseCode.getMessage();
        this.data = data;
    }



    public static ResponseBean fail(int code){
        return fail(code,null);
    }

    public  ResponseBean(ResponseCode responseCode){
        this.code = responseCode.getCode();
        this.msg = responseCode.getMessage();
    }

    public static ResponseBean fail(int code,Object data){
        ResponseBean responseBean=new ResponseBean();
        ResponseCode responseCode= ResponseCode.fromCode(code);
        responseBean.setCode(responseCode.getCode());
        responseBean.setMsg(responseCode.getMessage());
        responseBean.setData(data);
        return responseBean;
    }


    public String getToken() {
       /* ServletRequestAttributes ra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = ra.getRequest();
        User user = (User) request.getAttribute(BaseApiAction.ATTRI_MEMBER);
        if (user != null) {
            String token = (String) request.getAttribute(BaseApiAction.TOKEN);
            return JWTUtil.refreshToken(token, user.getUserName(), user.getPassword());
        }
        return null;*/
        return "getToken";
    }
}