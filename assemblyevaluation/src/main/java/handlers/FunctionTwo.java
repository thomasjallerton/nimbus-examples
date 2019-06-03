package handlers;

import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.nimbusframework.nimbuscore.annotation.annotations.deployment.ForceDependency;
import com.nimbusframework.nimbuscore.annotation.annotations.function.BasicServerlessFunction;
import org.apache.commons.collections4.IterableMap;
import org.apache.commons.collections4.map.LinkedMap;

public class FunctionTwo {

    @BasicServerlessFunction
    @ForceDependency(classPaths = {"com.mysql.cj.jdbc.Driver"})
    public void functionTwo() {

        PhoneNumberUtil util = PhoneNumberUtil.getInstance();
        IterableMap<String, String> iterableMap = new LinkedMap<>();
    }

}

