<%@ page import="com.urise.webapp.model.TextSection" %>
<%@ page import="com.urise.webapp.model.ListSection" %>
<%@ page import="com.urise.webapp.util.ResumeUtil" %>
<%@ page import="com.urise.webapp.util.DateUtil" %>
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
    <h2>${resume.fullName}&nbsp;<a href="resume?uuid=${resume.uuid}&action=edit"><img src="img/pencil.png"></a></h2>
    <p>
        <c:forEach var="contactEntry" items="${resume.contacts}">
            <jsp:useBean id="contactEntry"
                         type="java.util.Map.Entry<com.urise.webapp.model.ContactType, java.lang.String>"/>
            <%=contactEntry.getKey().toHtml(contactEntry.getValue())%><br/>
        </c:forEach>
    </p>

    <c:forEach var="sectionEntry" items="${resume.sections}">
        <jsp:useBean id="sectionEntry"
                     type="java.util.Map.Entry<com.urise.webapp.model.SectionType, com.urise.webapp.model.Section>"/>
        <c:set var="sectionType" value="${sectionEntry.key}"/>
        <c:set var="section" value="${sectionEntry.value}"/>

        <c:if test="${section != null}">
            <c:choose>
                <c:when test="${sectionType == 'OBJECTIVE' || sectionType == 'PERSONAL'}">
                    <c:set var="content" value="${section.content}"/>
                    <h3>${sectionType.title}:</h3>
                    ${content}
                </c:when>

                <c:when test="${sectionType == 'ACHIEVEMENT' || sectionType == 'QUALIFICATIONS'}">
                    <c:set var="items" value="${section.items}"/>
                    <h3>${sectionType.title}:</h3>
                    <c:forEach var="item" items="${items}">
                        <c:if test="${not empty fn:trim(item)}">
                            <li>${item}</li>
                        </c:if>
                    </c:forEach>
                </c:when>

                <c:when test="${sectionType == 'EXPERIENCE' || sectionType == 'EDUCATION'}">
                    <jsp:useBean id="section" type="com.urise.webapp.model.CompanySection"/>
                    <c:set var="companies" value="${section.companies}"/>
                    <h3>${sectionType.title}:</h3>
                    <c:forEach var="company" items="${companies}">
                        <c:if test="${not empty company.homePage.name}">
                            <c:choose>
                                <c:when test="${empty company.homePage.url}">
                                    <li><b>${company.homePage.name}</b></li>
                                </c:when>
                                <c:otherwise>
                                    <li><b><a href="${company.homePage.url}">${company.homePage.name}</a></b></li>
                                </c:otherwise>
                            </c:choose>
                        </c:if>

                        <c:forEach var="position" items="${company.positions}">
                            <jsp:useBean id="position" type="com.urise.webapp.model.Company.Position"/>
                            <div style="margin-left: 20px">
                                <c:if test="${not empty position.title}">
                                    <%=DateUtil.formatDate(position.getDateFrom())%> - <%=DateUtil.formatDate(position.getDateTo())%> : ${position.title}
                                    <br>
                                </c:if>

                                <c:if test="${not empty position.description}">
                                    ${position.description}
                                </c:if>
                            </div>
                        </c:forEach>
                    </c:forEach>
                </c:when>
            </c:choose>
        </c:if>
    </c:forEach>

</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
