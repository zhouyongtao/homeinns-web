package com.homeinns.web.cxf;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.ws.BindingType;

/**
 * Created by Irving on 2014/12/9.
 */
@WebService(targetNamespace = "http://www.cnblogs.com/")
@BindingType(javax.xml.ws.soap.SOAPBinding.SOAP12HTTP_BINDING)
public interface IHelloService {

    /**
     * say hello
     * @param username
     * @return
     */
    @WebMethod
    @WebResult String sayHello(@WebParam String username);

    /**
     * say goodbye
     * @param username
     * @return
     */
    String sayGoodBye(String username);
}