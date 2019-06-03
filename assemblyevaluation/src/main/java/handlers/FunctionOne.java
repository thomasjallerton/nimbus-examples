package handlers;

import com.google.common.primitives.Booleans;
import com.nimbusframework.nimbuscore.annotation.annotations.deployment.ForceDependency;
import com.nimbusframework.nimbuscore.annotation.annotations.function.BasicServerlessFunction;
import config.AppConfig;
import config.MyColour;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class FunctionOne {

    @BasicServerlessFunction
    @ForceDependency(classPaths = {"org.springframework.beans.ExtendedBeanInfoFactory"})
    public void functionOne() {

        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        MyColour colour = (MyColour) context.getBean("myColourBean");
        colour.printColour();
        int compared = Booleans.compare(true, false);
    }

}
