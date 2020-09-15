<html>
    <head>
        <!-- meta charset 指定了页面编码, 否则中文会乱码 -->
        <meta charset="utf-8">
        <!-- title 是浏览器显示的页面标题 -->
        <title>login</title>

        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z" crossorigin="anonymous">
        <link rel="stylesheet" href="/static?file=login.css">

    </head>
    <body>
        <form class="form-signin" action="/login" method="post">
            <img class="mb-4" src="/static?file=doge.gif" alt="" width="72" height="72">
            <h1 class="h3 mb-3 font-weight-normal">Please sign in</h1>
            <label class="sr-only">Account</label>
            <input type="text" id="inputEmail"  name="username" class="form-control" placeholder="Account" required="" autofocus="">
            <label for="inputPassword" class="sr-only">Password</label>
            <input type="password" id="inputPassword" name="password" class="form-control" placeholder="Password" required="">
            <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
            <a href="/register">create new account</a>
            <p class="mt-5 mb-3 text-muted">© 2020</p>
            <h3>${result}</h3>
        </form>


<#--        <a href='/login'>LOGIN</a>-->
<#--        <a href='/'>HOME</a>-->
<#--        <h1>登录</h1>-->
<#--        <form action="/login" method="post">-->
<#--            <input type="text" name="username" placeholder="请输入用户名">-->
<#--            <br>-->
<#--            <input type="text" name="password" placeholder="请输入密码">-->
<#--            <br>-->
<#--            <button type="submit">登录</button>-->
<#--        </form>-->
<#--        <h3>${result}</h3>-->
    </body>
</html>