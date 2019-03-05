package com.gd.config;


import org.springframework.context.annotation.Configuration;

/**
 * Created by dell on 2016/12/8.
 * Good Luck !
 * へ　　　　　／|
 * 　　/＼7　　　 ∠＿/
 * 　 /　│　　 ／　／
 * 　│　Z ＿,＜　／　　 /`ヽ
 * 　│　　　　　ヽ　　 /　　〉
 * 　 Y　　　　　`　 /　　/
 * 　ｲ●　､　●　　⊂⊃〈　　/
 * 　()　 へ　　　　|　＼〈
 * 　　>ｰ ､_　 ィ　 │ ／／
 * 　 / へ　　 /　ﾉ＜| ＼＼
 * 　 ヽ_ﾉ　　(_／　 │／／
 * 　　7　　　　　　　|／
 * 　　＞―r￣￣`ｰ―＿
 */
@Configuration
public class DataSourceConfig {
/*单数据库连接池
   @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DruidDataSource dataSource() {
        return new DruidDataSource();
    }

    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactoryBean sqlSessionFactoryBean(DruidDataSource dataSource) {

        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setTypeAliasesPackage("com.qmy.springbootreadwrite.domain");
        return sqlSessionFactoryBean;
    }

    @Bean
    public PlatformTransactionManager transactionManager(DruidDataSource dataSource) {
        DataSourceTransactionManager dataSourceTransactionManager =  new DataSourceTransactionManager(dataSource());
        dataSourceTransactionManager.setDataSource(dataSource);
        return dataSourceTransactionManager;
    }

*/

}
