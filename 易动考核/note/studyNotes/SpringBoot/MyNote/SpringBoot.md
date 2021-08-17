

------



[TOC]



------



# SpringBoot

简化开发，约定大于配置

**Spring Boot的主要优点：**

- 为所有Spring开发者更快的入门
- **开箱即用**，提供各种默认配置来简化项目配置
- 内嵌式容器简化Web项目
- 没有冗余代码生成和XML配置的要求

# 主启动类（自动装配）

## 默认的主启动类

```java
//@SpringBootApplication 来标注一个主程序类
//说明这是一个Spring Boot应用
@SpringBootApplication
public class SpringbootApplication {
   public static void main(String[] args) {
       //以为是启动了一个方法，没想到启动了一个服务
       SpringApplication.run(SpringbootApplication.class, args);
   }
}
```



## @SpringBootApplication

作用：标注在某个类上说明这个类是SpringBoot的主配置类 ， SpringBoot就应该运行这个类的main方法来启动SpringBoot应用；

进入这个注解：上面还有很多其他注解

```java
@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan(
    excludeFilters = {@Filter(
        type = FilterType.CUSTOM,
        classes = {TypeExcludeFilter.class}
    ), @Filter(
        type = FilterType.CUSTOM,
        classes = {AutoConfigurationExcludeFilter.class}
    )}
)
public @interface SpringBootApplication {
    // ......
}
```

### @ComponentScan

这个注解在Spring中很重要 ,它对应XML配置中的元素。

作用：自动扫描并加载符合条件的组件或者bean ， 将这个bean定义加载到IOC容器中

### @SpringBootConfiguration

作用：SpringBoot的配置类 ，标注在某个类上 ， 表示这是一个SpringBoot的配置类；

我们继续进去这个注解查看

```java
// 点进去得到下面的 @Component
@Configurationpublic @interface SpringBootConfiguration {
    
}

@Componentpublic @interface Configuration {
    
}
```

这里的 @Configuration，说明这是一个配置类 ，配置类就是对应Spring的xml 配置文件；

里面的 @Component 这就说明，启动类本身也是Spring中的一个组件而已，负责启动应用！

我们回到 SpringBootApplication 注解中继续看。

### @EnableAutoConfiguration

**@EnableAutoConfiguration ：开启自动配置功能**

以前我们需要自己配置的东西，而现在SpringBoot可以自动帮我们配置 ；@EnableAutoConfiguration告诉SpringBoot开启自动配置功能，这样自动配置才能生效；

点进注解接续查看：

#### **@AutoConfigurationPackage ：自动配置包**

```java
@Import({Registrar.class})
public @interface AutoConfigurationPackage {

}
```

##### **@import** ：Spring底层注解@import ， 给容器中**导入一个组件**

**Registrar.class 作用：将主启动类的所在包及包下面所有子包里面的所有组件扫描到Spring容器 ；**

这个分析完了，退到上一步，继续看

#### **@Import({AutoConfigurationImportSelector.class}) ：给容器导入组件 ；**

AutoConfigurationImportSelector ：自动配置**导入选择器**，那么它会导入哪些组件的选择器呢？我们点击去这个类看源码：

1、这个类中有一个这样的方法

```java
// 获得候选的配置
protected List<String> getCandidateConfigurations(AnnotationMetadata metadata, AnnotationAttributes attributes) {
    //这里的getSpringFactoriesLoaderFactoryClass（）方法
    //返回的就是我们最开始看的启动自动导入配置文件的注解类；EnableAutoConfiguration
    List<String> configurations = SpringFactoriesLoader.loadFactoryNames(this.getSpringFactoriesLoaderFactoryClass(), this.getBeanClassLoader());
    Assert.notEmpty(configurations, "No auto configuration classes found in META-INF/spring.factories. If you are using a custom packaging, make sure that file is correct.");
    return configurations;
}
```

2、这个方法又调用了  SpringFactoriesLoader 类的静态方法！我们进入SpringFactoriesLoader类loadFactoryNames() 方法

```java
public static List<String> loadFactoryNames(Class<?> factoryClass, @Nullable ClassLoader classLoader) {
    String factoryClassName = factoryClass.getName();
    //这里它又调用了 loadSpringFactories 方法
    return (List)loadSpringFactories(classLoader).getOrDefault(factoryClassName, Collections.emptyList());
}
```

3、我们继续点击查看 loadSpringFactories 方法

```java
private static Map<String, List<String>> loadSpringFactories(@Nullable ClassLoader classLoader) {
    //获得classLoader ， 我们返回可以看到这里得到的就是EnableAutoConfiguration标注的类本身
    MultiValueMap<String, String> result = (MultiValueMap)cache.get(classLoader);    if (result != null) {
        return result;
    } else {
        try {
            //去获取一个资源 "META-INF/spring.factories"
            Enumeration<URL> urls = classLoader != null ? classLoader.getResources("META-INF/spring.factories") : ClassLoader.getSystemResources("META-INF/spring.factories");
            LinkedMultiValueMap result = new LinkedMultiValueMap();
            //将读取到的资源遍历，封装成为一个Properties
            while(urls.hasMoreElements()) {
                URL url = (URL)urls.nextElement();
                UrlResource resource = new UrlResource(url);
                Properties properties = PropertiesLoaderUtils.loadProperties(resource);                Iterator var6 = properties.entrySet().iterator();
                while(var6.hasNext()) {
                    Entry<?, ?> entry = (Entry)var6.next();
                    String factoryClassName = ((String)entry.getKey()).trim();
                    String[] var9 = StringUtils.commaDelimitedListToStringArray((String)entry.getValue());
                    int var10 = var9.length;
                    for(int var11 = 0; var11 < var10; ++var11) {
                        String factoryName = var9[var11];
                        result.add(factoryClassName, factoryName.trim());
                    }
                }
            }
            cache.put(classLoader, result);
            return result;
        } catch (IOException var13) {
            throw new IllegalArgumentException("Unable to load factories from location [META-INF/spring.factories]", var13);
        }
    }
}
```

4、发现一个多次出现的文件：spring.factories，全局搜索

#### spring.factories

根据源头打开spring.factories ， 看到了很多自动配置的文件；这就是自动配置根源所在

**WebMvcAutoConfiguration**

【图片】

我们在上面的自动配置类随便找一个打开看看，比如 ：WebMvcAutoConfiguration

【图片】

**所以，自动配置真正实现是从classpath中搜寻所有的META-INF/spring.factories配置文件 ，并将其中对应的 org.springframework.boot.autoconfigure. 包下的配置项，通过反射实例化为对应标注了 @Configuration的JavaConfig形式的IOC容器配置类 ， 然后将这些都汇总成为一个实例并加载到IOC容器中。**

**结论：**

1. SpringBoot在启动的时候从类路径下的META-INF/spring.factories中获取EnableAutoConfiguration指定的值
2. 将这些值作为自动配置类导入容器 ， 自动配置类就生效 ， 帮我们进行自动配置工作；
3. 整个J2EE的整体解决方案和自动配置都在springboot-autoconfigure的jar包中；
4. 它会给容器中导入非常多的自动配置类 （xxxAutoConfiguration）, 就是给容器中导入这个场景需要的所有组件 ， 并配置好这些组件 ；
5. 有了自动配置类 ， 免去了我们手动编写配置注入功能组件等的工作；

### **SpringApplication**

#### 不简单的方法

我最初以为就是运行了一个main方法，没想到却开启了一个服务；

```java
@SpringBootApplication
public class SpringbootApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringbootApplication.class, args);
    }
}
```

#### **SpringApplication.run分析**

分析该方法主要分两部分，一部分是SpringApplication的实例化，二是run方法的执行；

#### SpringApplication

**这个类主要做了以下四件事情：**

1、推断应用的类型是普通的项目还是Web项目

2、查找并加载所有可用初始化器 ， 设置到initializers属性中

3、找出所有的应用程序监听器，设置到listeners属性中

4、推断并设置main方法的定义类，找到运行的主类

查看构造器：

```java
public SpringApplication(ResourceLoader resourceLoader, Class... primarySources) {
    // ......
    this.webApplicationType = WebApplicationType.deduceFromClasspath();  
    this.setInitializers(this.getSpringFactoriesInstances();
    this.setListeners(this.getSpringFactoriesInstances(ApplicationListener.class));
    this.mainApplicationClass = this.deduceMainApplicationClass();
}
```

![](..\img\qispringboot启动流程.webp)





# **resouces文件夹的作用**

# 配置文件yml



## @Service、yaml基础语法

说明：语法要求严格！

1、空格不能省略

2、以缩进来控制层级关系，只要是左边对齐的一列数据都是同一个层级的。

3、属性和值的大小写都是十分敏感的。



**字面量：普通的值  [ 数字，布尔值，字符串  ]**

字面量直接写在后面就可以 ， 字符串默认不用加上双引号或者单引号；

```yaml
k: v
```

注意：

- “ ” 双引号，不会转义字符串里面的特殊字符 ， 特殊字符会作为本身想表示的意思；

  比如 ：name: "kuang \n shen"  输出 ：kuang  换行  shen

- '' 单引号，会转义特殊字符 ， 特殊字符最终会变成和普通字符一样输出

  比如 ：name: ‘kuang \n shen’  输出 ：kuang  \n  shen

  

**对象、Map（键值对）**

```yaml
#对象、Map格式
k:
	v1:
	v2:
```

在下一行来写对象的属性和值得关系，注意缩进；比如：

```yaml
student:
	name: qinjiang
	age: 3
```

行内写法

```yaml
ystudent: {name: qinjiang,age: 3}
```



**数组（ List、set ）**

用 - 值表示数组中的一个元素,比如：



```yaml
pets:
 - cat
 - dog
 - pig
```

行内写法

```yaml
pets: [cat,dog,pig]
```

**修改SpringBoot的默认端口号**

配置文件中添加，端口号的参数，就可以切换端口；

```yaml
server:
  port: 8082
```

## 注入配置文件

## yaml注入配置文件

1、**在springboot项目中的resources目录下新建一个文件 application.yml**

2、编写一个实体类 Dog；

```java
package com.xxx.springboot.pojo;
@Component
//注册bean到容器中
public class Dog {
    private String name;
    private Integer age;
    //有参无参构造、get、set方法、toString()方法
}
```

3、思考原来是如何给bean注入属性值的@Value，给狗狗类测试一下：

```java
@Component
//注册bean
public class Dog {
    @Value("阿黄")
    private String name;
    @Value("18")
    private Integer age;
}
```

4、在SpringBoot的测试类下注入狗狗输出一下

```java
@SpringBootTestclass DemoApplicationTests {
    @Autowired
    //将狗狗自动注入进来
    Dog dog;
    @Test
    public void contextLoads() {
        System.out.println(dog);
        //打印看下狗狗对象
    }
}
```

5、编写一个复杂一点的实体类：Person 类

```java
@Component
//注册bean到容器中
public class Person {
    private String name;
    private Integer age;
    private Boolean happy;
    private Date birth;
    private Map<String,Object> maps;
    private List<Object> lists;
    private Dog dog;
    //有参无参构造、get、set方法、toString()方法
}
```

6、使用yaml配置的方式进行注入，注意区别和优势，编写一个yaml配置

```yaml
person:
  name: qinjiang
  age: 3
  happy: false
  birth: 2000/01/01
  maps: {k1: v1,k2: v2}
  lists:
   - code
   - girl
   - music
  dog:
    name: 旺财
    age: 1
```

7、已经把person这个对象的所有值都写好了，我们现在来注入到我们的类中！

```java
/*@ConfigurationProperties作用：将配置文件中配置的每一个属性的值，映射到这个组件中；告诉SpringBoot将本类中的所有属性和配置文件中相关的配置进行绑定参数 prefix = “person” : 将配置文件中的person下面的所有属性一一对应*/
@Component
//注册bean
@ConfigurationProperties(prefix = "person")
public class Person {
    private String name;
    private Integer age;
    private Boolean happy; 
    private Date birth;
    private Map<String,Object> maps;
    private List<Object> lists;
    private Dog dog;
}
```

8、IDEA 提示，springboot配置注解处理器没有找到，查看文档，找到一个依赖

```xml
<!-- 导入配置文件处理器，配置文件进行绑定就会有提示，需要重启 -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-configuration-processor</artifactId>
    <optional>true</optional>
</dependency>
```

9、确认以上配置都OK之后，去测试类中测试：

```java
@SpringBootTest
class DemoApplicationTests {
    @Autowired
    Person person;
    //将person自动注入进来
    @Test
    public void contextLoads() {
        System.out.println(person);
        //打印person信息
    }
}
```

结果：所有值全部注入成功！

# @Mapper、@Repository注解



# 如何整合SSM：跑通一遍服务，从数据库到界面展示



# @Bean、@Configuration注解

# 全局异常处理

参考链接

# 其他

## thymeleaf

找到对应的pom依赖：

```xml
<!--thymeleaf-->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-thymeleaf</artifactId>
</dependency>
```

命名空间：

```html
xmlns:th="http://www.thymeleaf.org"
```

## springsecurity

## shiro

### maven

```xml
<!--SpringBoot 和 Shiro 整合包-->
		<!-- https://mvnrepository.com/artifact/org.apache.shiro/shiro-spring-boot-web-starter -->
		<dependency>
			<groupId>org.apache.shiro</groupId>
			<artifactId>shiro-spring-boot-web-starter</artifactId>
			<version>1.6.0</version>
		</dependency>
```



### Shiro 三大要素

subject -> ShiroFilterFactoryBean
securityManager -> DefaultWebSecurityManager
realm
实际操作中对象创建的顺序 ： realm -> securityManager -> subject



### 编写自定义的 realm ，需要继承 `AuthorizingRealm`

```java
//自定义的 Realm
public class UserRealm extends AuthorizingRealm {
    //授权
@Override
protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
    //打印一个提示
    System.out.println("执行了授权方法");
    return null;
}

//认证

@Override
protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
    //打印一个提示
    System.out.prin
```


### 新建一个 `ShiroConfig`配置文件

1、按照上面说的创建过程来写
创建 `Realm`

```java
//3. realm
//让 spring 托管自定义的 realm 类
@Bean
public UserRealm userRealm(){
    return new UserRealm();
}
```

2、`securityManager`

```java
public abstract class RealmSecurityManager extends CachingSecurityManager {
    private Collectoin<Realm> realms;
    
    public RealmSecurityManager(){
    
    }
}
```

需要传入一个 Realm

```java
//2. securityManager -> DefaultWebSecurityManager
    // @Qualifier("userRealm") 指定 Bean 的名字为 userRealm
    // spring 默认的 BeanName 就是方法名
    // name 属性 指定 BeanName
    @Bean(name = "SecurityManager")
    public DefaultWebSecurityManager getDefaultWebSecurity(@Qualifier("userRealm") UserRealm userRealm){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //需要关联自定义的 Realm，通过参数把 Realm 对象传递过来
        securityManager.setRealm(userRealm);
        return securityManager;
    }

```

3、创建 `subject`

![](..\img\创建subject.png)

需要传入一个 securityManager

```java
//1. subject -> ShiroFilterFactoryBean
// @Qualifier("securityManager") 指定 Bean 的名字为 securityManager

@Bean(name = "shiroFilterFactoryBean")
public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("SecurityManager") DefaultWebSecurityManager securityManager){
    ShiroFilterFactoryBean subject = new ShiroFilterFactoryBean();
    //设置安全管理器
    //需要关联 securityManager ，通过参数把 securityManager 对象传递过来
    subject.setSecurityManager(securityManager);
    return subject;
}

```

## 简单案例

通过 SecurityUtils 获取当前执行的用户 Subject

```java
Subject currentUser = SecurityUtils.getSubject();
```

通过 当前用户拿到 Session

```
Session session = currentUser.getSession();
```

用 Session 存值取值

```
session.setAttribute("someKey", "aValue");
        String value = (String) session.getAttribute("someKey");
```


判断用户是否被认证

```
currentUser.isAuthenticated()
```

执行登录操作

```
 currentUser.login(token);
```

打印其标识主体

```
currentUser.getPrincipal()
```

注销

```
currentUser.logout();
```



## 1. 登录拦截

在上面的 `getShiroFilterFactoryBean` 方法中加上需要拦截的登录请求

```java
@Bean(name = "shiroFilterFactoryBean")
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("SecurityManager") DefaultWebSecurityManager securityManager){
        ShiroFilterFactoryBean subject = new ShiroFilterFactoryBean();
        subject.setSecurityManager(securityManager);

//添加 Shiro 的内置过滤器=======================
        /*
            anon : 无需认证，就可以访问
            authc : 必须认证，才能访问
            user : 必须拥有 “记住我”功能才能用
            perms : 拥有对某个资源的权限才能访问
            role : 拥有某个角色权限才能访问
         */
        Map<String, String> map = new LinkedHashMap<>();
        // 设置 /user/addUser 这个请求,只有认证过才能访问
//        map.put("/user/addUser","authc");
//        map.put("/user/deleteUser","authc");
        // 设置 /user/ 下面的所有请求,只有认证过才能访问
        map.put("/user/*","authc");
        subject.setFilterChainDefinitionMap(map);
        // 设置登录的请求
        subject.setLoginUrl("/tologin");
//============================================
        return subject;
    }
```

## 2. 用户认证

在 Controller 中写一个登录的控制器

```java
//登录的方法
    @RequestMapping("/login")
    public String login(String username, String password, Model model) {
        //获取当前用户
        Subject subject = SecurityUtils.getSubject();
        //没有认证过
        //封装用户的登录数据,获得令牌
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);

    //登录 及 异常处理
    try {
        //用户登录
        subject.login(token);
        return "index";
    } catch (UnknownAccountException uae) {
        //如果用户名不存在
        System.out.println("用户名不存在");
        model.addAttribute("exception", "用户名不存在");
        return "login";
    } catch (IncorrectCredentialsException ice) {
        //如果密码错误
        System.out.println("密码错误");
        model.addAttribute("exception", "密码错误");
        return "login";
    }
}
```

**先执行了 自定义的 `UserRealm` 中的 `AuthenticationInfo` 方法，再执行了，登录的相关操作**

下面去自定义的 `UserRealm` 中的 `AuthenticationInfo` 方法中去获取用户信息

3.修改 `UserRealm` 中的 `AuthenticationInfo`

```java
	@Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //打印一个提示
        System.out.println("执行了认证方法");

    // 用户名密码(暂时先自定义一个做测试)
    String name = "root";
    String password = "1234";

    //通过参数获取登录的控制器中生成的 令牌
    UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
    //用户名认证
    if (!token.getUsername().equals(name)){
        // return null 就表示控制器中抛出的相关异常
        return null;
    }
    //密码认证， Shiro 自己做，为了避免和密码的接触
    //最后返回一个 AuthenticationInfo 接口的实现类，这里选择 SimpleAuthenticationInfo
    // 三个参数：获取当前用户的认证 ； 密码 ； 认证名
    return new SimpleAuthenticationInfo(principal, user.getPwd(), this.getName());
}
```

## 3.退出登录

1.在控制器中添加一个退出登录的方法

```java
//退出登录

@RequestMapping("/logout")
public String logout(){
    Subject subject = SecurityUtils.getSubject();
    subject.logout();
    return "login";
}
```

## 4.整合mybatis

### 1.配置

在pom.xml导入对应的依赖

```xml
		<!--shiro整合mybatis-->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
        </dependency>

        <dependency>
            <groupId>log4j</groupId>
            <artifactId>log4j</artifactId>
            <version>1.2.17</version>
        </dependency>

        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>druid</artifactId>
            <version>1.2.6</version>
        </dependency>

        <dependency>
            <groupId>org.mybatis.spring.boot</groupId>
            <artifactId>mybatis-spring-boot-starter</artifactId>
            <version>2.1.4</version>
        </dependency>
```

在resource目录下新建application.yaml配置文件

```yaml
spring:
  datasource:
    username: root
    password: 123456
    url: jdbc:mysql://localhost:3306/mybatis?useUnicode=true&characterEncoding=utf-8
    driver-class-name: com.mysql.cj.jdbc.Driver
    #    使用德鲁伊的数据源
    type: com.alibaba.druid.pool.DruidDataSource
    #Spring Boot 默认是不注入这些属性值的,需要自己绑定
    #druid 数据源专有配置
    initialSize: 5
    minIdle: 5
    maxActive: 20
    maxWait: 60000
    timeBetweenEvictionRunsMillis: 60000
    minEvictableIdleTimeMillis: 300000
    validationQuery: SELECT 1 FROM DUAL
    testWhileIdle: true
    testOnBorrow: false
    testOnReturn: false
    poolPreparedStatements: true

    #配置监控统计拦截的filters
    # stat:监控统计
    # log4j:日志记录(需要导入log4j依赖)
    # wall:防御sql注入
    filters: stat,wall,log4j
    maxPoolPreparedStatementPerConnectionSize: 20
    useGlobalDataSourceStat: true
    connectionProperties: druid.stat.mergeSql=true;druid.stat.slowSqlMillis=500
```

新建数据库连接绑定mybatis的数据库

application.properties文件里配置mybatis的相关设置

```properties
mybatis.type-aliases-package=com.oujiajun.pojo
mybatis.mapper-locations=classpath:mapper/*.xml
```

### 2.mybatis 鉴权

编写mapper

在编写UserMapper.xml

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.oujiajun.mapper.UserMapper">
    <select id="queryUserByName" parameterType="String" resultType="user">
        select * from user where name=#{name}
    </select>
</mapper>
```

UserMapperImpl实现类代码:

```java
package com.oujiajun.mapper;

import  com.oujiajun.pojo.User;
import  com.oujiajun.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserMapperImpl implements UserService {
    @Autowired
    UserMapper userMapper;
    @Override
    public User queryUserByName(String name) {
        return userMapper.queryUserByName(name);
    }
}
```

UserRealm代码修改如下:

```java
package com.oujiajun.config;

import com.oujiajun.pojo.User;
import com.oujiajun.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

//自定义的UserRealm
public class UserRealm extends AuthorizingRealm {
    @Autowired
    UserService userService;
//    授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("执行了授权的=>doGetAuthorizationInfo");
        return null;
    }
//认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("执行了认证的=>doGetAuthenticationInfo");
//        用户名,密码到数据库中取
//        链接真实的数据库
        UsernamePasswordToken userToken = (UsernamePasswordToken) token;
        User user = userService.queryUserByName(userToken.getUsername());
        if (user==null){//没有这个人
            return null;//UnknownAccoutException
        }
//        密码认证:shiro做
//         密码可以加密:md5,md5盐值加密
        return new SimpleAuthenticationInfo("",user.getPwd(),"");
    }
}
```

### 3.**实现请求授权**

在ShiroConfig添加

```java
filterMap.put("/user/add","perms[user:add]");
```

MyController写一个跳转到未授权的页面方法

```java
@ResponseBody
    @RequestMapping("/noauth")
    public String unauthorized(){
        return "无法访问此页面";
    }
```

ShiroConfig类添加

```java
bean.setUnauthorizedUrl("/noauth");
```

添加add页面的授权，在UserRealm添加权限

```java
SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
info.addStringPermission("user:add");
return info;
```

添加入doGetAuthorizationInfo(PrincipalCollection principalCollection)方法

但是所有登录的用户都有此权限,所以将数据库的表新增一个权限的字段,添加权限 

认证方法

doGetAuthenticationInfo(AuthenticationToken token)方法的最后一句改为

```java
return new SimpleAuthenticationInfo(user,user.getPwd(),"");//认证中传入user即可以在授权中取出
```

即

```java
//认证
@Override
protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
    System.out.println("执行了认证的=>doGetAuthenticationInfo");
    //        用户名,密码到数据库中取
    //        链接真实的数据库
    UsernamePasswordToken userToken = (UsernamePasswordToken) token;
    User user = userService.queryUserByName(userToken.getUsername());
    if (user==null){//没有这个人
        return null;//UnknownAccoutException
    }
    //        密码认证:shiro做
    //         密码可以加密:md5,md5盐值加密
    return new SimpleAuthenticationInfo(user,user.getPwd(),"");//认证中传入user即可以在授权中取出
}
```
授权方法

```java
//拿到登录的对象
Subject subject = SecurityUtils.getSubject()；
User currentUser = (User) subject.getPrincipal();//拿到user
//设置当前用户的权限
info.addStringPemission(currectUser.getPerms());
```

添加入doGetAuthorizationInfo(PrincipalCollection principalCollection)方法

最终得doGetAuthorizationInfo(PrincipalCollection principalCollection)方法如下

```java
	//    授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("执行了授权的=>doGetAuthorizationInfo");
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addStringPermission("user:add");
        //拿到登录的对象
        Subject subject = SecurityUtils.getSubject()；
        User currentUser = (User) subject.getPrincipal();//拿到user
        //设置当前用户的权限
        info.addStringPemission(currectUser.getPerms());
        return info;
    }
```

最后UserRealm类为

```java
package com.oujiajun.config;

import com.oujiajun.pojo.User;
import com.oujiajun.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

//自定义的UserRealm
public class UserRealm extends AuthorizingRealm {
    @Autowired
    UserService userService;
	//    授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("执行了授权的=>doGetAuthorizationInfo");
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addStringPermission("user:add");
        //拿到登录的对象
        Subject subject = SecurityUtils.getSubject()；
        User currentUser = (User) subject.getPrincipal();//拿到user
        //设置当前用户的权限
        info.addStringPemission(currectUser.getPerms());
        return info;
    }
    
    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("执行了认证的=>doGetAuthenticationInfo");
        //        用户名,密码到数据库中取
        //        链接真实的数据库
        UsernamePasswordToken userToken = (UsernamePasswordToken) token;
        User user = userService.queryUserByName(userToken.getUsername());
        if (user==null){//没有这个人
            return null;//UnknownAccoutException
        }
        
        //        密码认证:shiro做
        //         密码可以加密:md5,md5盐值加密
        return new SimpleAuthenticationInfo(user,user.getPwd(),"");//认证中传入user即可以在授权中取出
    }
}
```

## 5.shiro整合thymeleaf

 导入对应的依赖

```xml
<!--        shiro整合thymeleaf-->
<dependency>
    <groupId>com.github.theborakompanioni</groupId>
    <artifactId>thymeleaf-extras-shiro</artifactId>
    <version>2.0.0</version>
</dependency>
```

在index.html添加约束

> xmlns:shiro="http://www.thymeleaf.org/thymeleaf-extras-shiro"

代码

```html
<div shiro:hasPermission="user:add">
    <a>添加</a>
</div>
```

如果要在登录后不显示登录按钮

则在认证中加入

```java
Subject subject = SecurityUtils.getSubject();
Session session = subject.getSession();
session.setAttribute("loginUser",user);
```

即

```java
//认证
@Override
protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
    System.out.println("执行了认证的=>doGetAuthenticationInfo");
    //        用户名,密码到数据库中取
    //        链接真实的数据库
    UsernamePasswordToken userToken = (UsernamePasswordToken) token;
    User user = userService.queryUserByName(userToken.getUsername());
    if (user==null){//没有这个人
        return null;//UnknownAccoutException
    }
    Subject subject = SecurityUtils.getSubject();
    Session session = subject.getSession();
    session.setAttribute("loginUser",user);
    //        密码认证:shiro做
    //         密码可以加密:md5,md5盐值加密
    return new SimpleAuthenticationInfo(user,user.getPwd(),"");//认证中传入user即可以在授权中取出
}
```
html页面

```html
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.themeleaf.org"
        xmlns:shiro="http://www.thymeleaf.org/thymeleaf-extras-shiro">
<head>
    <meta charset="UTF-8">
    <title>首页</title>
</head>
<body>
<h1>首页</h1>
<p  th:text="${msg}"></p>

<div shiro:hasPermission="user:add">
    <a th:href="@{/user/add}">添加</a>
</div>

<div shiro:hasPermission="user:update">
    <a th:href="@{/user/update}">更新</a>
</div>

<br>
<div th:if="${session.loginUser==null}"><a th:href="@{/toLogin}">登录</a></div>
<div><a th:href="@{/logout}">注销</a></div>

</body>
</html>
```





