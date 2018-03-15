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
</body>
</html>