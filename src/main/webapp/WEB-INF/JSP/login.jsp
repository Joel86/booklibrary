<%@page contentType='text/html' pageEncoding='UTF-8' session='false'%>
<%@taglib prefix='c' uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix='joelv' uri="http://joelv.be/tags"%>
<%@taglib prefix='security' uri="http://www.springframework.org/security/tags"%>
<!doctype html>
<html lang='en'>
<head>
<joelv:head title="Login"/>
</head>
<body>
<joelv:menu/>
<h1>Login</h1>
<form method='post'>
  <label>Username:<input name='username' autofocus required/></label>
  <label>Password:<input name='password' type='password' required/></label>
  <security:csrfInput/>
  <input type='submit' value='Log in'>
  <c:if test='${param.error != null}'>
    <div class='error'>Wrong username or password</div>
  </c:if>
</form>
</body>
</html>