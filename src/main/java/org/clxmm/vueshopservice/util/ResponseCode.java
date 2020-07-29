package org.clxmm.vueshopservice.util;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public enum ResponseCode {

    SUCCESS(200, "成功!"),
    FAIL(999, "失败!"),
    EXCEPTION(401, "失败"),
    ERROR(2, "参数错误."),
    NO_TOKEN(90, "无token,请重新登录!"),
    HTZ_OUTTOKEN(91, "待机时间过长,请重新启动!"),
    INVALID_TOKEN(92, "无效token!"),
    GROUP_OUTDATE(409, "您的使用期限已结束,请联系客服续费!"),
    LOGIN_ERROR(10, "用户名或密码错误"),
    SAVE_ERROR(11, "保存失败");

    /**
     * 存放CODE -> Enum的Map
     */
    private static final ConcurrentMap<Integer, ResponseCode> CODE_MAP = new ConcurrentHashMap<Integer, ResponseCode>(
            ResponseCode.values().length);

    /**
     * 填充CODE --> Enum的Map
     */
    static {
        for (ResponseCode codeTable : ResponseCode.values()) {
            CODE_MAP.put(codeTable.code, codeTable);
        }
    }

    /**
     * 响应码
     */
    private final int code;

    /**
     * 响应消息
     */
    private final String message;

    /**
     * 构造函数
     *
     * @param code    响应码
     * @param message 响应消息
     */
    ResponseCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 根据code获取消息对象
     * @param code
     * @return
     */
    public static ResponseCode fromCode(Integer code) {
        return CODE_MAP.get(code);
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}