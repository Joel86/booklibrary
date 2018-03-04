<%@page contentType='text/html' pageEncoding='UTF-8' session='false'%>
<%@taglib prefix='c' uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix='form' uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix='joelv' uri="http://joelv.be/tags"%>
<!doctype html>
<html lang='en'>
<head>
<joelv:head title='Add book'/>
</head>
<body>
<joelv:menu/>
<h1>Add Book:</h1>
<c:url value='/books/search' var='url'/>
<form:form action='${url}' commandName='isbnForm' method='get'>
  <form:label path="isbn">Isbn:</form:label>
  <form:input path="isbn" autofocus='autofocus'/>
  <input type='submit' value='Search'>
</form:form>
<c:choose>
  <c:when test='${empty error and not empty book}'>
    <c:url value='/books/add' var='url'/>
    <form:form commandName='book' id='bookform' action='${url}'>
      <dl>
        <dt><img src='${book.thumbnailUrl}'/></dt>
        <form:hidden path='thumbnailUrl'/>
        <dt>Isbn:</dt><dd>${book.isbn10} (${book.isbn13})</dd>
        <form:hidden path='isbn10'/>
        <form:hidden path='isbn13'/>
        <dt>Title:</dt><dd>${book.title}</dd>
        <form:hidden path='title'/>
        <dt>Pages:</dt><dd>${book.pages}</dd>
        <form:hidden path='pages'/>
        <dt>Year:</dt><dd>${book.year}</dd>
        <form:hidden path='year'/>
        <dt>Publisher:</dt><dd>${book.publisher.name}</dd>
        <form:hidden path='publisher.name'/>
        <dt>Author(s):</dt>
        <c:forEach items="${book.authors}" var='author' varStatus='count'>
	      <dd>${author}</dd>
	      <form:hidden path='authors[${count.count - 1}].name'/>
	      <form:hidden path='authors[${count.count - 1}].surname'/>
        </c:forEach> 
        <dt>Genre(s):</dt>
        <c:forEach items="${book.genres}" var='genre' varStatus='count'>
	      <dd>${genre.name}</dd>
	      <form:hidden path='genres[${count.count - 1}].name'/>
        </c:forEach>
      </dl>
      <input type='submit' value='Add to library'>
    </form:form>
  </c:when>
  <c:when test='${not empty error}'>
    <div class='error'>${error}</div>
  </c:when>
</c:choose>
</body>
</html>