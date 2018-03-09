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
<form id='searchForm'>
  <label>Isbn:
  <input id='bookIsbn' autofocus='autofocus'/></label>
  <input type='submit' value='Search'>
</form>
<form>
  <dl>
    <dt><img id='thumb'/></dt>
    <dt>Isbn:</dt>
    <dd id='isbn'></dd>
    <dt>Title:</dt>
    <dd id='title'></dd>
    <dt>Pages:</dt>
    <dd id='pages'></dd>
    <dt>Year:</dt>
    <dd id='year'></dd>
    <dt>Publisher:</dt>
    <dd id='publisher'></dd>
    <dt>Author(s):</dt>
    <dd id='authors'></dd>
    <dt>Genre(s):</dt>
    <dd id='genres'></dd>
  </dl>
  <input type='submit' value='Add to library'>
  <c:url value='${bookUrl}' var='url'/>
  <script>
  document.getElementById('searchForm').onsubmit = searchBook;
  function searchBook() {
	  var request = new XMLHttpRequest();
	  request.open(
			  "GET", '${url}' + document.getElementById('bookIsbn').value, true);
	  request.setRequestHeader('accept', 'application/json');
	  request.onload = response;
	  request.send();
	  return false;
  }
  
  function response() {
	  switch(this.status) {
	  case 200:
		  var bookResource = JSON.parse(this.responseText);
		  var volumeInfo = bookResource.items[0].volumeInfo;
		  var industryIdentifiers = volumeInfo.industryIdentifiers;
		  var isbn10 = '';
		  var isbn13 = '';
		  for(var i=0;i<industryIdentifiers.length;i++) {
			  if(industryIdentifiers[i].type == 'ISBN_10') {
				  isbn10 = industryIdentifiers[i].identifier;
			  } else {
				  isbn13 = industryIdentifiers[i].identifier;
			  }
		  }
		  document.getElementById('thumb').setAttribute('src' , 
				  volumeInfo.imageLinks.thumbnail);
		  document.getElementById('isbn').innerHTML = isbn10 + ' (' + isbn13 + ')'; 
		  document.getElementById('title').innerHTML = volumeInfo.title;
		  document.getElementById('pages').innerHTML = volumeInfo.pageCount;
		  document.getElementById('year').innerHTML = volumeInfo.publishedDate.substring(0,4);
		  document.getElementById('publisher').innerHTML = volumeInfo.publisher;
		  for(var i=0;i<volumeInfo.authors.length;i++) {
			  document.getElementById('authors').innerHTML = volumeInfo.authors[i];
		  }
		  for(var i=0;i<volumeInfo.categories.length;i++) {
			  document.getElementById('genres').innerHTML = volumeInfo.categories[i];
		  }
		  break;
	  case 404:
		  alert('Book not found');
		  break;
	  default:
		  alert('Technical error');
	  }
  }
  </script>
</form>
</body>
</html>