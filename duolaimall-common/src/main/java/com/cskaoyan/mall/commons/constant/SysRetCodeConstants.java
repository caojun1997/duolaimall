package com.cskaoyan.mall.commons.constant;

/**
 */
public enum SysRetCodeConstants {
    // 系统公用
    SUCCESS                             ("000000", "成功"),

    TOKEN_VALID_FAILED                  ("000001","token校验失败"),
    GET_TOKEN_FAILED                    ("000002","token校验失败"),
    KAPTCHA_CODE_ERROR                  ("000003","验证码不正确"),
    REQUEST_FORMAT_ILLEGAL              ("000004", "请求数据格式非法"),
    REQUEST_IP_ILLEGAL                  ("000005", "请求IP非法"),
    REQUEST_CHECK_FAILURE               ("000006", "请求数据校验失败"),
    DATA_NOT_EXIST                      ("000007", "数据不存在"),
    DATA_REPEATED                       ("000008", "数据重复"),
    REQUEST_DATA_NOT_EXIST              ("000009", "传入对象不能为空"),
    REQUEST_DATA_ERROR                  ("000010", "必要的参数不正确"),
    REQUISITE_PARAMETER_NOT_EXIST       ("000011", "必要的参数不能为空"),
    DB_EXCEPTION                        ("000012", "数据库异常"),
    SYSTEM_TIMEOUT                      ("000013", "系统超时"),
    SYSTEM_ERROR                        ("000014", "系统错误"),
    TOMANY_REQUEST_ERROR                 ("429","后端服务触发流控"),
    BACKGROUD_DEGRADE_ERROR             ("604","后端服务触发降级"),
    BAD_GATEWAY                         ("502","网关服务异常");

    private String code;
    private String message;

    SysRetCodeConstants(String code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * Getter method for property <tt>code</tt>.
     *
     * @return property value of code
     */
    public String getCode() {
        return code;
    }

    /**
     * Setter method for property <tt>code</tt>.
     *
     * @param code value to be assigned to property code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Getter method for property <tt>message</tt>.
     *
     * @return property value of message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Setter method for property <tt>message</tt>.
     *
     * @param message value to be assigned to property message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    public static String getMessage(String code) {
        for (SysRetCodeConstants s : SysRetCodeConstants.values()) {
            if (null == code)
                break;
            if (s.code.equals(code)) {
                return s.message;
            }
        }
        return null;
    }
}
