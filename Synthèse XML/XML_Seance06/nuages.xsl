<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output method="html" version="html4.01" encoding="ISO-8859-1" 
doctype-public="-//W3C//DTD HTML 4.01//EN" doctype-system="http://www.w3.org/TR/html4/strict.dtd"/>
<xsl:template match="/">
	<html>
		<head>
			<title>Collection de Nuages</title>
			<meta name="author" content="Sacré Christopher"/>
		</head>
		<body>
			<h1>Les Nuages</h1>
			<xsl:apply-templates select="nuages/nuage"/>
			<hr/>
			<p>L'espèce <em>castellanus</em> est une espèce de <xsl:value-of select="count(//espece[.='castellanus'])"/> nuages différents</p>
		</body>
	</html>
</xsl:template>
<xsl:template match="nuage">
	<h2><xsl:value-of select="./nom"></xsl:value-of></h2>
	<p>Ce type de nuage possède les expèces suivantes : </p>
	<ul>
		<xsl:apply-templates select="./espece"/>
	</ul>
	<xsl:choose>
		<xsl:when test="hydrometeores">
			<p><xsl:value-of select="./hydrometeores"/></p>
		</xsl:when>
		<xsl:otherwise>
			<p>Pas d'HydroMétéores précisées</p>
		</xsl:otherwise>
	</xsl:choose>
	<xsl:apply-templates select="altitude">
		<xsl:with-param name="nomNuage" select="./nom"/>
	</xsl:apply-templates>
</xsl:template>
<xsl:template match="espece">
	<li><em><xsl:value-of select="."/></em><xsl:if test=".='castellanus'">*</xsl:if></li>
</xsl:template>
<xsl:template match="altitude">
	<xsl:param name="nomNuage"/>
	<p><xsl:value-of select="$nomNuage"></xsl:value-of> culmine minimum <xsl:value-of select="@min"/> mètres et maximum à <xsl:value-of select="@max"/> mètres </p>
</xsl:template>
</xsl:stylesheet>