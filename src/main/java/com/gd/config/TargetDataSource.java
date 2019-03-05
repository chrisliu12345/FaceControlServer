package com.gd.config;
//为了不影响业务代码而实现数据源切换，我决定使用AOP切换数据源，为了准确的知道哪个地方需要切换哪个数据源，
// 我这里使用自定义注解的方式，
// 如果你又更好的方式也推荐你使用自己的方式。创建自定义注解类：TargetDataSource：

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TargetDataSource {
    DataSourceKey dataSourceKey();

}
