<%@page contentType='text/html' pageEncoding='UTF-8' session='false'%>
<%@taglib prefix='c' uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix='joelv' uri="http://joelv.be/tags"%>
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
</body>
</html>