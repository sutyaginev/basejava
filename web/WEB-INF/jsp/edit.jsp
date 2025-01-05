<%@ page import="com.urise.webapp.model.ContactType" %>
<%@ page import="com.urise.webapp.model.SectionType" %>
<%@ page import="com.urise.webapp.model.CompanySection" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="com.urise.webapp.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>

<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <form method="post" action="resume" enctype="application/x-www-form-urlencoded"
          onsubmit="return validateDates(this);">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <dl>
            <dt>Имя:</dt>
            <dd><input type="text" name="fullName" size=50 value="${resume.fullName}" pattern=".*\S.*"
                       title="Поле не должно быть пустым или содержать только пробелы." required></dd>
        </dl>
        <h3>Контакты:</h3>
        <c:forEach var="contactType" items="<%=ContactType.values()%>">
            <dl>
                <dt>${contactType.title}</dt>
                <dd><input type="text" name="${contactType.name()}" size=30 value="${resume.getContact(contactType)}">
                </dd>
            </dl>
        </c:forEach>
        <h3>Секции:</h3>
        <c:forEach var="sectionType" items="<%=SectionType.values()%>">
            <c:set var="section" value="${resume.getSection(sectionType)}"/>
            <dl>
                <c:choose>
                    <c:when test="${sectionType == 'OBJECTIVE' || sectionType == 'PERSONAL'}">
                        <dt>${sectionType.title}</dt>
                        <dd>
                            <textarea id="textSection" name="${sectionType.name()}" rows="1"
                                      cols="150">${fn:trim(section.content)}</textarea>
                        </dd>
                    </c:when>

                    <c:when test="${sectionType == 'ACHIEVEMENT' || sectionType == 'QUALIFICATIONS'}">
                        <c:set var="list" value=""/>
                        <c:forEach var="item" items="${section.items}" varStatus="loop">
                            <c:set var="list" value="${list}${fn:trim(item)}"/>
                            <c:if test="${!loop.last}">
                                <c:set var="list" value="${list}&#x000A;"/>
                            </c:if>
                        </c:forEach>
                        <dt>${sectionType.title}</dt>
                        <dd>
                            <textarea id="listSection" name="${sectionType.name()}" rows="10"
                                      cols="150">${list}</textarea>
                        </dd>
                    </c:when>

                    <c:when test="${sectionType == 'EXPERIENCE' || sectionType == 'EDUCATION'}">
                        <dt>${sectionType.title}</dt>
                        <c:forEach var="company" items="${section.companies}"
                                   varStatus="counter">
                            <div style="margin-left: 30px">
                                <dl>
                                    <dt>Компания:</dt>
                                    <dd><input type="text" name='${sectionType}' size=100
                                               value="${company.homePage.name}">
                                    </dd>
                                </dl>

                                <dl>
                                    <dt>Сайт компании:</dt>
                                    <dd><input type="text" name='${sectionType}url' size=100
                                               value="${company.homePage.url}"></dd>
                                </dl>
                                <br>

                                <c:forEach var="position" items="${company.positions}">
                                    <jsp:useBean id="position" type="com.urise.webapp.model.Company.Position"/>
                                    <div style="margin-left: 30px">
                                        <dl>
                                            <dt>Дата начала:</dt>
                                            <dd>
                                                <input type="date" name="${sectionType}${counter.index}dateFrom" size=10
                                                       value="${position.dateFrom}" placeholder="yyyy-MM-dd">
                                            </dd>
                                        </dl>
                                        <dl>
                                            <dt>Дата окончания:</dt>
                                            <dd>
                                                <input type="date" name="${sectionType}${counter.index}dateTo" size=10
                                                       value="${position.dateTo}" placeholder="yyyy-MM-dd">
                                            </dd>
                                        </dl>
                                        <dl>
                                            <dt>Должность:</dt>
                                            <dd><input type="text" name='${sectionType}${counter.index}title' size=75
                                                       value="${position.title}">
                                            </dd>
                                        </dl>
                                        <dl>
                                            <dt>Описание:</dt>
                                            <dd><textarea name="${sectionType}${counter.index}description" rows=5
                                                          cols=75>${position.description}</textarea></dd>
                                        </dl>
                                    </div>
                                </c:forEach>
                            </div>
                        </c:forEach>
                    </c:when>
                </c:choose>
            </dl>
        </c:forEach>
        <hr>
        <button type="submit">Сохранить</button>
        <button type="reset" onclick="window.history.back()">Отменить</button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
