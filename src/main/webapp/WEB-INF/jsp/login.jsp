<%--
  Created by IntelliJ IDEA.
  User: ChenJinSheng
  Date: 2018/11/28
  Time: 21:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>CJS——智能办公</title>
    <link href="${pageContext.request.contextPath}/css/base.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/login.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/jquery/jquery-1.11.0.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/jquery/jquery-migrate-1.2.1.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/bootstrap/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/easyUI/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/easyUI/easyui-lang-zh_CN.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/easyUI/easyui.css">

    <script type="text/javascript">
        $(function () {
            $("#vimg").click(function () {
                $(this).attr("src", "${pageContext.request.contextPath}/createCode?timer="+new Date().getTime());
            }).mouseover(function () {
                $(this).css("cursor", "pointers");
            });

            /**
             * 回车事件
             */
            $(document).keydown(function (event) {
                if (event.keyCode == 13){
                    //可以進入
                    $("#login_id").trigger("click");
                }
            });
            
            $("#login_id").bind("click",function () {
                //可以進入
                var userId = $("#userId").val();
                var password = $("#password").val();
                var vcode = $("#vcode").val();

                //定义一个校验结果
                var msg = "";
                if (!/^\w{2,20}$/.test(userId.trim())) {
                    msg = "登录名必须是2~20个字符";
                } else if (!/^\w{6,20}$/.test(password)) {
                    msg = "密码必须是6~20个字符";
                }else if (!/^\w{4}$/.test(vcode)) {
                    msg = "验证码格式不正确";
                }

                if (msg != "") {
                    //可以进入
                    $.messager.alert("登录提示", "<span style='color:red;'>" + msg + "</span>", "error");
                    return;
                }
                var params = $("#loginForm").serialize();

                /**
                 * 发起异步请求登录
                 */
                $.ajax({
                    url: "${pageContext.request.contextPath}/loginAjax",
                    type: "post",
                    dataType: "json",
                    data: params ,
                    async: true ,
                    success: function (data) {
                        if (data.status == 1) {
                            /**
                             * 1:登录成功
                             * 2：密码错误
                             * 3：用户不存在
                             * 4：验证码错误
                             * 5：账户还没有激活
                             * @type {string}
                             */
                            //跳转主界面
                            window.location = "${pageContext.request.contextPath}/oa/main";
                        } else {
                            $("#vcode").trigger("click");
                            $.messager.alert("登录提示", "<span style='color:red;'>" + data.tip + "</span>", "error");
                        }
                    },
                    error: function () {
                        $.messager.alert("登录提示", "<span style='color: red'>您登录失败</span>","error");
                    }
                })
            });
        })
    </script>
</head>
<body>
<div class="login-hd">
    <div class="left-bg"></div>
    <div class="right-bg"></div>
    <div class="hd-inner">
        <span class="logo"></span>
        <span class="split"></span>
        <span class="sys-name">CJS智能办公平台</span>

    </div>
</div>
<div class="login-bd">
    <div class="bd-inner">
        <div class="inner-wrap">
            <div class="lg-zone">
                <div class="lg-box">
                    <div class="panel-heading" style="background-color:#11a9e2;">
                        <h3 class="panel-title" style="color: #FFFFFF;font-style: italic">用户登录</h3>
                    </div>
                    <form id="loginForm">
                        <div class="form-horizontal" style="padding-top: 20px; padding-bottom: 30px;padding-left: 20px">
                            <div class="form-group" style="padding: 5px;">
                                <div class="col-md-11">
                                    <input class="form-control" id="userId" name="userId" type="text" placeholder="账号/邮箱">
                                </div>
                            </div>
                            <div class="form-group" style="padding: 5px;">
                                <div class="col-md-11">
                                    <input class="form-control" id="password" name="password" type="password" placeholder="请输入密码">
                                </div>
                            </div>
                            <div class="form-group" style="padding: 5px;">
                                <div class="col-md-11">
                                    <div class="input-group">
                                        <input class="form-control" id="vcode" name="vcode" type="text" placeholder="验证码">
                                        <span class="input-group-addon" id="basic-addon2">
                                            <img class="check-code" src="${pageContext.request.contextPath}/createCode" alt="" id="vimg">
                                        </span>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="tips clearfix">
                            <label><input type="checkbox" checked="checked">记住用户名</label>
                            <a href="javascript:;" class="register">忘记密码?</a>
                        </div>
                        <div class="enter">
                            <a href="javascript:;" id="login_id" class="purchaser">登录</a>
                            <a href="javascript:;" class="supplier" onclick="javascript:window.location='main.html'">重置</a>
                        </div>
                    </form>
                </div>
            </div>
            <div class="lg-poster"></div>
        </div>
    </div>
</div>
<div class="login-ft">
    <div class="ft-inner">
        <div class="about-us">
            <a href="#">关于我们</a>
            <a href="#">法律声明</a>
            <a href="#">服务条款</a>
            <a href="#">联系方式</a>
        </div>
        <div class="address">
            微信：15622759269
            &nbsp;
            &nbsp;
            CSDN:<a href="https://blog.csdn.net/weixin_39400271">KamShingChan</a>
            &nbsp;
            &nbsp;
            博客园：<a href="https://www.cnblogs.com/SysoCjs/">Syso(Alt+/)Cjs</a>
            Copyright&nbsp;&nbsp;₠&nbsp;2018&nbsp;CJS&nbsp;版权所有
        </div>
        <div class="other-info">
            建议使用火狐、谷歌浏览器，不建议使用IE浏览器！
        </div>
    </div>
</div>
</body>
</html>
<script type="text/javascript"></script>