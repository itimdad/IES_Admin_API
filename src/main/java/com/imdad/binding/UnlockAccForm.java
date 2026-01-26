package com.imdad.binding;

import lombok.Data;

@Data
public class UnlockAccForm {

    private Integer userId;
    private String email;
    private String oldPwd;
    private String newPwd;
    private String confirmPwd;
}
