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
					<th><spring:url value="" var="url">
							<c:forEach items='${param}' var='p'>
								<c:if test='${p.key != "sort"}'>
								  <spring:param name='${p.key}' value='${p.value}'/>
								</c:if>
							</c:forEach>
							<c:choose>
								<c:when test='${param.sort == "title,asc"}'>
									<spring:param name="sort" value="title,desc" />
								</c:when>
								<c:otherwise>
									<spring:param name="sort" value="title,asc" />
								</c:otherwise>
							</c:choose>
						</spring:url> <a href='${url}'>Title</a></th>
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
	  var eYearFilterSelectBox = document.getElementById('yearFilterSelectBox');
	  var eTitleFilterSelectBox = document.getElementById('titleFilterSelectBox');
	  onetime(eYearFilterSelectBox, 'focus', handler);
	  
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
		  var request = new XMLHttpRequest();
		  request.open("GET", '/booklibrary/books/mybooks/filter', true);
		  request.setRequestHeader('accept', 'application/json');
		  request.onload = function(){
			  switch(this.status) {
			  case 200:
				  var resource = JSON.parse(this.responseText);
				  var resourcePathYears = resource.years;
				  fillSelectBox(eYearFilterSelectBox, resourcePath);
				  break;
			  default:
				  alert('technical problem');
			  }
		  }
		  request.send();
		  return false;
	  }
	  
	  function fillSelectBox(eSelectBox, aArray) {
		  for(var i=0;i<aArray.length;i++) {
			    var sYear = aArray[i];
				var eOption = document.createElement('option');
			    eOption.value = sYear;
			    eOption.innerHTML = sYear;
			    eSelectBox.appendChild(eOption);
			  }
	  }
	</script>
</body>
</html>