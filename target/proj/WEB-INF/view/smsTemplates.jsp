<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<h2 align="center">СМС щаблоны</h2>
<br>
<div class="rightSideCommonSpace">
    <form:form modelAttribute="entityBucket">
        <table>
            <tr>
                <td>
                    ${entityBucket.SmsTemplates}
                </td>
            </tr>
        </table>
        <form:textarea path="" />
    </form:form>
</div>