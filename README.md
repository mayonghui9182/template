## 前言
   本工程提供一个后台管理系统的服务开发模板工程，包含 权限管理，用户管理，角色管理，
字典管理等基础功能；整合了 jwt ，spring security，swagger 等技术；


## Getting Start

1、 不设置 profile ，默认激活的是 autoTest profile ；

2、 如需要发布 int 、uat 、生产 环境时需要设置默认环境变量，激活相应环境的 profile 如下：

       spring.profiles.active=prd  

   激活生产环境的配置文件；

3、 运行 Application.java 中 main 方法， 或者 在工程根目录执行如下命令： 

       mvn clean package -Dmaven.test.skip=true

   编译打包工程为 jar 包后执行 java -jar 运行jar包，
   由于自动化测试完善中，为不影响打包先过滤测试案例；

4、 访问 http://localhost:8080/ 进入后台管理系统服务 index 页面；

5、将工程导入 eclipse ，由于 使用了 org.mapstruct 做对象之间的属性映射，导入eclipse 需要将 target\generated-sources\annotations 路径添加到eclipse的编译路径下；

    由于 org.mapstruct 需要通过 mvn compile 生成映射类，所以导入eclipse后需要手动执行下 mvn compile


## 原则

1、 由于自动化测试采用 内嵌 H2 数据库，因此在写 mybatis 的 sql 或者 jpa 的 sql 时不使用
和特定数据库相关的库函数，如 DATE_FORMAT， concat 等；

2、 尽量使用 lombok 注解，减少手写代码量，如使用 @Slf4j ，@Data ，@Getter ，@Setter 等编译时
生成 log ，get、set 方法等；

3、 mybatis 的 mapper 文件以  *Mapper.xml 结尾， 并且其中的 sql 需要 format 以满足可读性；

4、 尽量采用 **hibernate-validator** 做数据有效性校验；

## 关于权限方面

1、采用spring security控制接口权限

 1.1、权限控制有两种方式：（1）注解(@RolesAllowed @PreAuthorize等),角色编号必须以“ROLE_”为开头（2）后台配置菜单，角色

 1.2、当对应接口上无注解，并且后台没有将对应接口配置到菜单时，默认为允许所有人访问

 1.3、注解和后台配置的方式同时生效，有一种验证不通过，即不通过
 

2、采用自定义属性"auth_id"控制页面按钮显示

 2.1、用户登录时用户的权限信息存储在cookie
 
 2.2、对应的按钮上添加自定义标签"anth_id={}",例如：
 
   <a href='javascript:void(0)' class='bg-button-hover' onclick='showRootAddDialog()' auth_id='/api/v1/menus/add'>
   
 2.3、页面加载完毕或者jqgrid表格加载完毕以后调用validateAuthority()方法;
 
 2.4、需要引入的js（顺序不能打乱）
 
    <script src="../../js/jquery.min.js?v=2.1.4"></script>
    <script src="../../js/biz/common.js"></script>
    <script src="../../js/biz/auth.js"></script>

## 关于web请求中token操作

1、登录时生成token,存储在localStorage中,每次请求通过header传递到后台

2、每次请求会通过JWTFilter验证token，当token不足5min过期时,刷新token过期时间，通过response的header返回

3、修改密码时会重新生成token,并通过@CachePut注解存储用户信息到cache，覆盖浏览器localStorage中的token


## 升级spring boot 2.0.0 相关说明
   升级sys-manager-service的  spring boot 1.5.x 版本到  spring boot 2.0.0 的相关修改说明；


## 主要升级后修改如下：

1、修改 pom.xml 文件中 spring boot 版本和 cloud 版本 properties 的配置 如下， 由于 spring cloud 、
  是在  Finchley 版本中升级的 spring boot 2.0所以同时升级  spring cloud 版本； 
  
    <spring-cloud.version>Finchley.M7</spring-cloud.version>
    <spring-boot.version>2.0.0.RELEASE</spring-boot.version>

2、pom.xml 文件修改spring-cloud-starter-eureka、spring-cloud-starter-hystrix 的命名如下，
  spring cloud升级 spring boot2.0 后修改了依赖 netflix jar 的 starter 命名；

    <dependency>
      <groupId>org.springframework.cloud</groupId>
      <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
    </dependency>
    <dependency>
      <groupId>org.springframework.cloud</groupId>
      <artifactId>spring-cloud-starter-netflix-hystrix</artifactId>
    </dependency>

3、由于在 spring boot 2.0 删除了 spring-boot-starter-mobile依赖，因此删除该 starter 添加如下依赖：
  
    <dependency>
      <groupId>org.springframework.mobile</groupId>
      <artifactId>spring-mobile-device</artifactId>
      <version>2.0.0.M1</version>
    </dependency>

4、 删除无用的依赖；
  由于 pagehelper-spring-boot-starter 还未升级到 spring boot 2.0 先删除 

    <dependency>
      <groupId>com.github.pagehelper</groupId>
      <artifactId>pagehelper-spring-boot-starter</artifactId>
      <version>${pagehelper-spring-boot-starter.version}</version>
    </dependency>

    <dependency>
      <groupId>org.webjars</groupId>
      <artifactId>webjars-locator</artifactId>
    </dependency>
    <dependency>
      <groupId>org.hibernate</groupId>
      <artifactId>hibernate-validator</artifactId>
    </dependency>
    <dependency>
      <groupId>org.json</groupId>
      <artifactId>json</artifactId>
    </dependency>

5、Tracer,Span 升级为 brave 的Tracer、Span对象

    import brave.Span;
    import brave.Tracer;

6、 spring data jpa ，查询 删除方法名变更的修改