<%@page contentType='text/html' pageEncoding='UTF-8' session='false'%>
<%@taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<%@taglib prefix='spring' uri="http://www.springframework.org/tags"%>
<%@taglib prefix='joelv' uri="http://joelv.be/tags"%>
<%@taglib prefix='form' uri="http://www.springframework.org/tags/form"%>
<!doctype html>
<html lang='en'>
<head>
<joelv:head title='My Books' />
</head>
<body>
	<joelv:menu />
	<h1>My Books</h1>
	<h5>Filter by:</h5>
	<form action='' method='get' id='yearFilterForm'>
	  <select id='yearFilterSelectBox' name='year' onchange='yearFilterForm.submit()'>
	    <option value=''>Year</option>
	  </select>
	</form>
	<form action='' method='get' id='titleFilterForm'>
	  <select id='titleFilterSelectBox' name='title' onchange='titleFilterForm.submit()'>
	    <option value=''>Title</option>
	  </select>
	</form>
	<form action='' method='get'>
	  <input type='submit' value='Reset Filters'>
	</form>
	<c:if test='${not empty page.content}'>
		<table>
			<thead>
				<tr>
					<th><c:url value="" var="url">
							<c:choose>
								<c:when test='${param.sort == "title,desc"}'>
									<c:param name="sort" value="title,asc" />
								</c:when>
								<c:otherwise>
									<c:param name="sort" value="title,desc" />
								</c:otherwise>
							</c:choose>
						</c:url> <a href='${url}'>Title</a></th>
					<th>Author(s)</th>
					<th>Year</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items='${page.content}' var='book'>
					<tr>
						<spring:url value='/books/{id}' var='detailUrl'>
							<spring:param name='id' value='${book.id}' />
						</spring:url>
						<td><a href='${detailUrl}'>${book.title}</a></td>
						<td><c:forEach items='${book.authors}' var='author'>
                  ${author}, 
                </c:forEach></td>
						<td>${book.year}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<p class='pagineren'>
			<c:forEach var="pageNr" begin="1" end="${page.totalPages}">
				<c:choose>
					<c:when test="${pageNr-1 == page.number}">
			${pageNr}
		  </c:when>
					<c:otherwise>
						<c:url value="" var="url">
							<c:param name="page" value="${pageNr-1}" />
							<c:param name="sort" value="${param.sort}" />
						</c:url>
						<a href="${url}">${pageNr}</a>
					</c:otherwise>
				</c:choose>
			</c:forEach>
		</p>
	</c:if>
	<script>
	  var eSelectBox = document.getElementById('yearFilterSelectBox');
	  onetime(eSelectBox, 'focus', handler);
	  
	  //function to ensure eventlistener fires only once
	  function onetime(node, type, callback){
		 //create event
		 node.addEventListener(type, function(e) {
			 e.target.removeEventListener(e.type, arguments.callee);
			 return callback(e);
		 });
	  }
	  //handler function
	  function handler(e) {
	    getConnection('/booklibrary/books/mybooks/years')
	  }
	  
	  function getConnection(url) {
		  var request = new XMLHttpRequest();
		  request.open("GET", url, true);
		  request.setRequestHeader('accept', 'application/json');
		  request.onload = response;
		  request.send();
		  return false;
	  }
	  
	  function response() {
		  switch(this.status) {
		  case 200:
			  var resource = JSON.parse(this.responseText);
			  var eSelectBox = document.getElementById('yearFilterSelectBox');
			  var resourcePath = resource.years;
			  for(var i=0;i<resourcePath.length;i++) {
				    var sYear = resourcePath[i];
					var eOption = document.createElement('option');
				    eOption.value = sYear;
				    eOption.innerHTML = sYear;
				    eSelectBox.appendChild(eOption);
				  }
			  break;
		  default:
			  alert('technical problem');
		  }
	  }
	</script>
</body>
</html>