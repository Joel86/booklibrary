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
  <input type='submit' id='submitButton' value='Search'>
</form>
<div id='bookInfo'>
</div>
<form id='addBookForm' class='hide' method='post' 
	action='<c:url value="/add"/>'>
  <input type='hidden' id='inputIsbn' name='inputIsbn'/>
  <input type='submit' value='Add to library'>
</form>
<span class='error'></span>
<c:url value='${bookUrl}' var='url'/>
	<script>
		document.getElementById('searchForm').onsubmit = searchBook;
		function searchBook() {
			document.getElementById('submitButton').disabled = true;
			clearList();
			var request = new XMLHttpRequest();
			request.open("GET", '${url}'
					+ document.getElementById('bookIsbn').value, true);
			request.setRequestHeader('accept', 'application/json');
			request.onload = response;
			request.send();
			return false;
		}

		function response() {
			switch (this.status) {
			case 200:
				var eDiv = document.getElementById('bookInfo');
				var eList = document.createElement('dl');
				eList.setAttribute('id', 'propertyList');
				document.getElementById('addBookForm').removeAttribute('class');
				document.getElementById('inputIsbn').setAttribute('value',
						document.getElementById('bookIsbn').value);
				var bookResource = JSON.parse(this.responseText);
				
				if (bookResource.totalItems != 0) {
					var volumeInfo = bookResource.items[0].volumeInfo;
					var industryIdentifiers = volumeInfo.industryIdentifiers;
					var image = volumeInfo.imageLinks;
					var isbn10;
					var isbn13;
					for (var i = 0; i < industryIdentifiers.length; i++) {
						if (industryIdentifiers[i].type == 'ISBN_10') {
							isbn10 = industryIdentifiers[i].identifier;
						} else {
							isbn13 = industryIdentifiers[i].identifier;
						}
					}
					var title = volumeInfo.title;
					var pages = volumeInfo.pageCount;
					var date = volumeInfo.publishedDate;
					var publisher = volumeInfo.publisher;
					var description = volumeInfo.description;
					
					if (typeof image != 'undefined') {
						var eTerm = document.createElement('dt')
						var eImg = document.createElement('img');
						eImg.setAttribute('src', image.thumbnail);
						eTerm.appendChild(eImg);
						eList.appendChild(eTerm);
					}
					var sTerm = document.createTextNode('Isbn:');
					var eTerm = document.createElement('dt');
					eTerm.appendChild(sTerm);
					var eDescription = document.createElement('dd');
					eDescription.innerHTML = isbn10 + ' ('
					+ isbn13 + ')';
					eList.appendChild(eTerm);
					eList.appendChild(eDescription);
					createListItem(title, 'Title:', eList);
					createListItem(pages, 'Pages:', eList);
					createListItem(date.substring(0,4), 'Year:', eList);
					createListItem(publisher, 'Publisher:', eList);
					if (typeof volumeInfo.authors != 'undefined' && 
							volumeInfo.authors.length != 0) {
						var eTerm = document.createElement('dt');
						var sTerm = document.createTextNode('Author(s):');
						eTerm.appendChild(sTerm);
						var eDescription = document.createElement('dd');
						var sDescription = '';
						for (var i = 0; i < volumeInfo.authors.length; i++) {
							sDescription += volumeInfo.authors[i] + '<br/>';
						}
						eDescription.innerHTML = sDescription;
						eList.appendChild(eTerm);
						eList.appendChild(eDescription);
					}
					if(typeof volumeInfo.genres != 'undefined' && 
							volumeInfo.genres.length != 0) {
						var eTerm = document.createElement('dt');
						var sTerm = document.createTextNode('Genre(s):');
						eTerm.appendChild(sTerm);
						var eDescription = document.createElement('dd');
						var sDescription = '';
						for (var i = 0; i < volumeInfo.categories.length; i++) {
							sDescription += volumeInfo.categories[i] + '<br/>';
						}
						sDescription.innerHTML = sDescription;
						eList.appendChild(eTerm);
						eList.appendChild(eDescription);
					}
					createListItem(description, 'Description:', eList);
					eDiv.appendChild(eList);
				} else {
					document.getElementById('addBookForm').setAttribute('class', 'hide');
					document.getElementsByClassName('error')[0].innerHTML = 'Book not found';
				}
				break;
			case 404:
				alert('Book not found');
				break;
			default:
				alert('Technical error');
			}
			document.getElementById('submitButton').disabled = false;
		}	
		function createListItem(sItem, sName, eList) {
			if(typeof sItem != 'undefined' && sItem != '') {
				var sTerm = document.createTextNode(sName);
				var eTerm = document.createElement('dt');
				eTerm.appendChild(sTerm);
				var eDescription = document.createElement('dd');
				eDescription.innerHTML = sItem;
				eList.appendChild(eTerm);
				eList.appendChild(eDescription);
			}
		}
		function clearList() {
			if(document.getElementById('propertyList')) {
				var eList = document.getElementById('propertyList');
				eList.parentNode.removeChild(eList);
			}
			document.getElementsByClassName('error')[0].innerHTML = '';
		}
	</script>
</body>
</html>