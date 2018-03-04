<%@tag description='menu' pageEncoding='UTF-8'%>
<%@taglib prefix='c' uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix='security' uri='http://www.springframework.org/security/tags'%>
<header>
  <nav><ul>
    <security:authorize access='hasAuthority("user")'>
      <li><a href='<c:url value="/books/search"/>'>Add Book</a></li>
      <li><a href='<c:url value="/books/mybooks"/>'>My Books</a></li>
    </security:authorize>
    <security:authorize access='isAnonymous()'>
      <li><a href='<c:url value="/login"/>'>Log in</a></li>
    </security:authorize>
    <security:authorize access='isAuthenticated ()'>
      <li>
	    <form method='post' action='<c:url value="/logout"/>' id='logoutform'>
	      <input type='submit' value='Log out <security:authentication property="name"/>'
 			id='logoutbutton'>
	      <security:csrfInput/>
	    </form>
	  </li>
	</security:authorize>
  </ul></nav>
</header>