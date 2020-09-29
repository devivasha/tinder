<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="img/favicon.ico">
    <title>Sign in</title>
    <#include "css/bootstrap.min.css">
    <#include "css/style.css">
</head>

<body class="text-center">
 <form class="form-signin" action=${rout} method="post">
        <h1 class="h3 mb-3 font-weight-normal">${message}</h1>
       <#list fields as field>
                      <label for=${field} class="sr-only">${field}</label>
                      <input class="form-control" type="text" name=${field} placeholder=${field}>
       </#list>

        <label for="inputPassword" class="sr-only">Password</label>
        <input type="password" name="Password" id="inputPassword" class="form-control" placeholder="Password">
        <input class="btn btn-lg btn-primary btn-block" type="submit" class="submit">
         <div class="or">or</div>
                <#if rout = "/login">
                <a href="/reg">Register</a>
                <#else>
                <a href="/login">Sign in</a>
                </#if>
        </input>
        <p class="mt-5 mb-3 text-muted">&copy; Tinder 2018</p>
    </form>
</body>
</html>