<%@page contentType='text/html' pageEncoding='UTF-8' session='false'%>
<%@taglib prefix='c' uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix='joelv' uri="http://joelv.be/tags"%>
<%@taglib prefix='spring' uri="http://www.springframework.org/tags"%>
<%@taglib prefix='form' uri="http://www.springframework.org/tags/form"%>
<!doctype html>
<html lang='en'>
<head>
<joelv:head title='${book.title}'/>
</head>
<body>
<joelv:menu/>
<dl>
  <dt><img src='${book.thumbnailUrl}'/></dt>
  <dt>Isbn:</dt><dd>${book.isbn10} (${book.isbn13})</dd>
  <dt>Title:</dt><dd>${book.title}</dd>
  <dt>Pages:</dt><dd>${book.pages}</dd>
  <dt>Year:</dt><dd>${book.year}</dd>
  <dt>Publisher:</dt><dd>${book.publisher.name}</dd>
  <dt>Author(s):</dt>
  <c:forEach items="${book.authors}" var='author'>
    <dd>${author}</dd>
  </c:forEach>
  <dt>Genre(s):<dt>
  <c:forEach items="${book.genres}" var='genre'>
    <dd>${genre.name}</dd>
  </c:forEach>
</dl>
<spring:url value='mybooks/{id}/delete' var='deleteUrl'>
  <spring:param name='id' value='${book.id}'/>
</spring:url>
<form:form method='post' action='${deleteUrl}'>
  <input type='submit' value='Delete'>
</form:form>
</body>
</html>