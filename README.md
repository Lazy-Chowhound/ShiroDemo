# ShiroDemo

2021-08-23 

第一次提交

2021-08-24

1、校验登录状态，不可重复登录

2、完成自定义filter功能，流程如下
> OPTIONS请求直接放行；若已经登录且有相关权限，也直接放行；
> 若没有登录，则判断token是否有效，没有效或已过期则终止流程，返回报错信息；否则进行登录操作
> 若登录但没有相关权限，也直接终止流程，返回错误信息。

2021-08-30

将token保存在redis中

2021-08-31

1、增加wrapper类修改请求头（并未真正修改，只是让之后在调用getHeader方法的时候，如果获取的是指定字段，则返回的不是原有的值而是我们指定的值）

2、新增双token机制，流程如下
> 1、登录时生成用户token和refresh token，生成token的参数完全一样只是到期时间不同
> 用户token返回给用户，refresh token存入redis（如果之前改用户有refresh token
> 则覆盖）
>
> 2、未登录而直接使用token请求使用了自定义过滤器页面时，判断用户传过来的token有无过期
> 如果过期了，则判断redis中对应的refresh token有无过期，如果refresh token也过期了，
> 则重新登录；如果refresh token没有过期，则无论用户token有没有过期，都会生成新的用户
> token和refresh token，将新的用户token写入request进行登录，同时将新的用户token，
> 写入response返回头中，refresh token则直接覆盖原来的refresh token。这么做的目的
> 是防止这么一种情况：假设用户token到期时间是7天，然后就算用户7天内天天登录，然而到了7
> 天之后还是需要重新登录，这合理吗？所以采用双token，如果用户token到期了，但是refresh
> token并没有到期，就生成新的token，一是可以刷新token时间，二是经常变换token也有利于
> 安全。

2022-03-09

修改Shiro版本以修复漏洞