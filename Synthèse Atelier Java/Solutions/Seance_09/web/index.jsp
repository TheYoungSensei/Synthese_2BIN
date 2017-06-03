<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta charset="UTF-8" />
<meta name="author" content="Emmeline Leconte" />
<title>Gimme UML</title>
<!-- une icÃ´ne dans la barre du navigateur-->
<link rel=icon href="images/icone.png" type="image/png">
<style type="text/css">
body {
	background-color: #DDD;
}

table {
	border: 1px solid black;
}

.uml {
	border: 1px solid black;
	border-color: black;
}

th {
	text-align: center;
	background-color: white;
}

.well {
	background-color: #CCC;
	padding: 20px;
}

.static {
	text-decoration: underline;
}

.abstract {
	font-style: italic;
}
.list-unstyled{
list-style-type:none;
}
</style>
</head>
<body>
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

	<div class="container">
		<header class="row">
			<div class="col-lg-12">
				<h1>Gimme gimme ma classe en UML</h1>
			</div>
		</header>
		<div class="row">
			<article class="col-lg-5 col-md-5">
				<form class="well form-inline" action="soumettre.html" method="post"
					enctype="multipart/form-data">
					<div class="form-group">
						<input type="file" class="btn btn-default" id="fichier"
							name="fichier" SIZE="40">
					</div>
					<div class="form-group">
						<input value="Générer l'UML" type="submit" class="btn btn-default" />
					</div>
				</form>
			</article>
			<article class="col-lg-7 col-md-7">
				<c:if test="${nom!=null}">
					<table class="table-condensed">
						<caption>La classe ${nom}</caption>
						<thead>
							<tr>
								<th
									<c:if test="${identite.abstrait}"> class="abstract"
											</c:if>>${identite.nom}</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td class="uml">
									<ul class="list-unstyled">
										<c:forEach var="attribut" items="${attributs}">
											<li
												<c:if test="${attribut.deClasse}"> class="static"
											</c:if>>${attribut.visibilite.valeur}
												${attribut.nom}</li>
										</c:forEach>
									</ul>
								</td>
							</tr>
							<tr>
								<td class="uml"><ul class="list-unstyled">
										<c:forEach var="constructeur" items="${constructeurs}">
											<li
												<c:if test="${constructeur.deClasse}"> class="static"
											</c:if>
												<c:if test="${constructeur.abstrait}"> class="abstract"
											</c:if>>${constructeur.visibilite.valeur}
												${constructeur.nom}</li>
										</c:forEach>


										<c:forEach var="methode" items="${methodes}">
											<li
												<c:if test="${methode.deClasse}"> class="static"
											</c:if>
												<c:if test="${methode.abstrait}"> class="abstract"
											</c:if>>${methode.visibilite.valeur}
												${methode.nom}</li>
										</c:forEach>
									</ul></td>
							</tr>
						</tbody>
					</table>
				</c:if>
			</article>
		</div>

		<footer class="row">
			<p>Copyright ${initParam.poo}</p>
		</footer>
	</div>
</body>

</html>

