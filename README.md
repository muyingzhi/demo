# SpringBoot Security 支持ajax登录方式的处理

要实现标题之目标，有很多方案，比如奖表单登录和ajax登录分别采用不同的url

## 前提：只使用spring security完成身份认证和鉴权
## 定义规则：
* （一）使用唯一的登录url，如/login，表单请求登录和ajax请求登录，都通过/login完成。
* （二）登录过程：
1. 表单请求登录，成功转向指定的url，如/main；登录失败转向继续登录url,如/login?error，并且提供设置request.setAttribute(“exception”,exp),将错误信息返回；
2. ajax请求登录，成功或失败都提供json数据放回，json提供成功或失败的标志；
* （三）未登录时请求受保护资源，
1. ajax请求，返回status=401（前端根据此状态，再做处理）
2. form请求，跳转到登录页
* （四）服务端可扩展（可以集群部署，任意服务down机，其已经登录用户仍然可以成功访问）
* （五）支持移动端访问（无session）

## 应对策略：
* （一）使用UsernamePasswordAuthenticationFilter，为了支持ajax访问，需要自定义此filter（myUpFilter），根据http header信息判断出ajax请求，并解析body而获得username和password，再提供给security做认证；
* （二）为myUpFilter设置自定义的“success”和“failure”的handler，其中都要分别对form请求、ajax请求做处理，按规则要求向response写数据；
* （三）未登录时请求，原本会在ExceptionTranslationFilter被拦截，然后跳转到loginFormUrl。所以这里需要增加对ajax请求的处理，将放回的response头信息status=401；
* （四）http访问在北网关转发后，到达的服务端是随机的。所以，应当实现session共享，或者session写入可共享的第三方空间（如redis），或者session不再是唯一保留已登录用户信息的方式
* （五）无session访问可以通jwt方式实现，即服务端生成加密的token，客户端登录成功后获取token并保存（localstrage），再次http请求需要带上token；服务端解析token，进行验证；验证成功将认证结果写入SecurityHolder，使security的filterchain能够正确运作；这也解决了（四）所需，只要客户端还保留有token，那么带着token的http访问即可通关filterchain的验证。

## 在springsecurity框架下的实现：
1. 自定义UsernamePasswordAuthenticationFilter，并引入jwt的token算法，自定义的successHandler、failureHandler，实现登录的处理，ajax请求成功后返回内容带有token，form请求成功后设置request.setAttribute(“token”,token)；
2. 自定义AuthenticationWithTokenFilter，支持没有session验证信息情况下通过jwt的token实现验证；
3. 自定义AjaxAuthenticationEntryPoint，用于未登录时ajax访问能够返回status=401而不是直接redirect到/login
4. 自定义SecurityConfig，将以上自定义对象组合到http配置中，请配置可自由访问的路由，需要保护的路由等；

