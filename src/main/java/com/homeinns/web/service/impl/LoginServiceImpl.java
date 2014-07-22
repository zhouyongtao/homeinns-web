package com.homeinns.web.service.impl;

import com.homeinns.web.service.ILoginService;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2014/7/23.
 */
@Service
public class LoginServiceImpl implements ILoginService{

    @Override
    public boolean login(String userName, String pwd) {
        if(userName=="irving"){
            return true;
        }
        return false;
    }
}
