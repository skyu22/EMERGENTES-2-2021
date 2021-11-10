<%@page import="com.emergentes.modelo.eventos"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
eventos pro=(eventos) request.getAttribute("event");
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1><c:if test ="${event.id == 0}">
                Nuevo 
        </c:if>
                <c:if test ="${event.id != 0}">
                Editar 
        </c:if>
            </h1>
        <form action="MainController" method="post">
             <input type="hidden" name="id" value="${event.id}">
            <table>
                <tr>
                    <td>Titulo</td>
                    <td><input type="text" name="titulo" value="${event.titulo}"></td>
                </tr>
                <tr>
                    <td>Expositor</td>
                    <td><input type="text" name="expositor" value="${event.expositor}"></td>
                </tr>
                <tr>
                    <td>Fecha</td>
                    <td><input type="date" name="fecha" value="${event.fecha}"></td>
                </tr>
                <tr>
                    <td>Hora</td>
                    <td><input type="text" name="hora" value="${event.hora}"></td>
                </tr>
               <tr>
                    <td>Cupos</td>
                    <td><input type="text" name="cupo" value="${event.cupo}"></td>
                </tr>
                
                <tr>
                    <td></td>
                    <td><input type="submit" value="ENVIAR"></td>
                </tr>
                
            </table>
        </form>
    </body>
</html>
