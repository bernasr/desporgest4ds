<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Associar árbitro</title>
</head>
<body>
	<script type="text/javascript"
		src="http://ajax.googleapis.com/ajax/libs/jquery/1.4/jquery.min.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$('#designacao').change(function() {
				var p = $('#designacao').find(":selected").val();
				if (p == "") {
					return;
				}
				$.ajax({
					type : "post",
					url : "getEncontros",
					data : "prova=" + p,
					success : function(msg) {
						$("#encontros").empty();
						$("#encontros").append($("<option></option>").attr("value", "").text("Selecione um Encontro"));
						$.each(msg, function(ind, o) {
							$("#encontros").append($("<option></option>")
									.attr("value", o.id)
									.text("Nº: " + o.id + " Data: " + o.data + " Participantes: " + o.part));
						});
					}
				});
			});
		});
	</script>
	<form action="associarArbitro" method="post">
		<div class="mandatory_fiel">
			<label for="designacao">Designação da prova:</label> <select
				name="designacao" id="designacao">
				<option value="">Selecione uma prova</option>
				<c:forEach var="designacao" items="${model.provasDesignacao}">
					<c:choose>
						<c:when test="${model.prova == designacao}">
							<option selected="selected" value="${designacao}">${designacao}</option>
						</c:when>
						<c:otherwise>
							<option value="${designacao}">${designacao}</option>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</select>
		</div>
		<div>
			<label for="encontro">Encontros com arbitros incompletos</label> <select
				name="encontro" id="encontros">
				<option value="">Selecione um Encontro</option>
			</select>
		</div>
		<div class="mandatory_fiel">
			<label for="designacao">Número federativo do árbitro:</label> <select
				name="numFederativo" id="numFederativo">
				<option value="">Selecione um árbitro</option>
				<c:forEach var="arbitro" items="${model.arbitros}">
					<option value="${arbitro.numeroFederativo}">${arbitro.numeroFederativo}</option>
				</c:forEach>
			</select>
		</div>

		<div class="button" align="right">
			<input type="submit" value="Associar árbitro">
		</div>
	</form>
	<c:if test="${model.hasMessages}">
		<p>Mensagens</p>
		<ul>
			<c:forEach var="mensagem" items="${model.messages}">
				<li>${mensagem}
			</c:forEach>
		</ul>
	</c:if>
</body>
</html>