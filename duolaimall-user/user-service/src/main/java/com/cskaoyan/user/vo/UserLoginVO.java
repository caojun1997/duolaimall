package com.cskaoyan.user.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author op027
 * @date 2022年04月23日 15:04
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserLoginVO {
    private Long uid;
    private String username;
}
