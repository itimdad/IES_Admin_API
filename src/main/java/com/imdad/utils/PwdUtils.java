package com.imdad.utils;

import org.springframework.stereotype.Component;

import java.util.Random;


@Component
public class PwdUtils {

    public String pwdGenerator(){

        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder password = new StringBuilder();

        for (int i = 0; i < 7; i++) {
            password.append(chars.charAt(random.nextInt(chars
                    .length())));
        }


        return password.toString();
    }


}
