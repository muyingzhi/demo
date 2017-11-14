
# SpringBoot Security 支持ajax登录方式的处理

要实现标题之目标，有很多方案，比如奖表单登录和ajax登录分别采用不同的url

前提：使用唯一的登录url，如/login
定义规则：
- [ ] 表单请求登录和ajax请求登录，都通过/login完成。
- [ ] 表单请求登录，成功转向指定的url，如/main；登录失败转向继续登录url,如/login?error，并且提供设置request.setAttribute(“exception”,exp),将错误信息返回；
- [ ] ajax请求登录，成功或失败都提供json数据放回，json提供成功或失败的标志；

架构设计：
1 应在UsernamePasswordAuthenticationFilter之前设置自定义filter，如名字为MyFilter
2 MyFilter应完成三个事情：解析请求的username、password，因为表单与ajax请求的内容格式不同；登录成功时，分别对表单和ajax请求给出返回内容；登录失败时，分别对表单和ajax请求给出返回内容；
3 在SecurityConfig中设置Filter，FormLogin等；自定义filter放置在UsernamePasswordAuthenticationFilter之前

问题：
- [ ] spring security是基于sessionid的，如何实现移动端无sessionid的登录呢？
- [ ] spring secruity登录后的authentication是写在http session，那么为了实现后端服务的扩展，如何在多个服务节点共享（或复制）？（直接写入redis，或者无状态登录处理JWT)
