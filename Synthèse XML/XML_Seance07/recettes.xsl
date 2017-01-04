<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:template match="/">
		<html>
			<head>
				<title>Mes Recettes</title>
				<meta name="author" content="Sacré Christopher"/>
			</head>
			<body>
				<p>Ce recueil contient actuellement <xsl:value-of select="count(//recette)"/> recettes</p>
				<ul>
				<xsl:for-each select="//recette">
					<xsl:sort select="@type"/>
					<xsl:sort select="//titre"/>
					<li><xsl:element name="a">
						<xsl:attribute name="href">#<xsl:value-of select="position()"/></xsl:attribute>
						<xsl:value-of select=".//titre"/>
					</xsl:element></li>
				</xsl:for-each>
				</ul>
				<xsl:apply-templates select="//recette">
					<xsl:sort select="@type"/>
					<xsl:sort select="//titre"/>
				</xsl:apply-templates>
			</body>
		</html>
	</xsl:template>
	<xsl:template match="recette">
		<xsl:element name="h1">
			<xsl:attribute name="id"><xsl:value-of select="position()"/></xsl:attribute>
			<xsl:value-of select="position()"/>.<xsl:value-of select=".//titre"/>
		</xsl:element>
		<xsl:if test=".//preparation">
		<xsl:element name="p">
			<xsl:attribute name="style">color:blue;text-align:right;</xsl:attribute>
			Temps de préparation : <xsl:value-of select=".//preparation"/>
		</xsl:element>
		</xsl:if>
		<xsl:if test=".//cuisson">
		<xsl:element name="p">
			<xsl:attribute name="style">color:blue;text-align:right;</xsl:attribute>
			Temps de cuisson : <xsl:value-of select=".//cuisson"/>
		</xsl:element>
		</xsl:if>
		<xsl:element name="p">
			<xsl:attribute name="style">color:blue;</xsl:attribute>
			<xsl:value-of select=".//remarque"/>
		</xsl:element>
		<h2>Ingrédients</h2>
		<ul>
			<xsl:apply-templates select=".//ingredient">
				<xsl:sort select="."/>
			</xsl:apply-templates>
		</ul>
		<h2>Procédures</h2>
		<ol>
			<xsl:apply-templates select=".//instruction"/>
		</ol>
	</xsl:template>
	<xsl:template match="ingredient">
		<li><xsl:value-of select="@qte"/> <xsl:value-of select="@unite"/> <xsl:value-of select="."/></li>
	</xsl:template>
	<xsl:template match="instruction">
		<li><xsl:value-of select="."/></li>
	</xsl:template>
</xsl:stylesheet>