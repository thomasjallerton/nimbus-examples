package com.nimbusframework.examples.basicfunctions;

import com.nimbusframework.nimbuscore.annotations.function.BasicServerlessFunction;

public class Calculator {

    @BasicServerlessFunction
    public int add(AddRequest addRequest) {
        int result = addRequest.getNumber1() + addRequest.getNumber2();
        System.out.println(addRequest.getNumber1() + " + " + addRequest.getNumber2() + " = " + result);
        return result;
    }

}
