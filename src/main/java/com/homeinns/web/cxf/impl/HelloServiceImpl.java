package com.homeinns.web.cxf.impl;
import com.homeinns.web.cxf.IHelloService;
import org.springframework.stereotype.Component;
import javax.jws.WebService;
import javax.xml.ws.BindingType;

/**
 * Created by Irving on 2014/12/9.
 */
@WebService(targetNamespace="http://www.cnblogs.com/")
@BindingType(javax.xml.ws.soap.SOAPBinding.SOAP12HTTP_BINDING)
@Component
public class HelloServiceImpl implements IHelloService {
    @Override
    public String sayHello(String username) {
        return "hello ," + username;
    }
    @Override
    public String sayGoodBye(String username) {
        return "goodbye ," + username;
    }
}
