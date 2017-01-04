<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:template match="/">
		<html>
			<head>
				<title>Vidéothèque</title>
				<meta name="author" content="Sacré Christopher"/>
				<link rel="stylesheet" type="text/css" href="films.css"/>
			</head>
			<body>
				<xsl:element name="div">
					<xsl:attribute name="class">films</xsl:attribute>
					Mes <xsl:value-of select="count(//film)"/> films
					<ul>
					<xsl:for-each select="//film">
						<xsl:sort select="//titre"/>
						<li><xsl:element name="a">
							<xsl:variable name="monFilm"><xsl:text>#film_</xsl:text><xsl:value-of select="position()"/></xsl:variable>
							<xsl:attribute name="href"><xsl:value-of select="$monFilm"/></xsl:attribute>
							<xsl:value-of select=".//titre"/>
						</xsl:element></li>
					</xsl:for-each>
					</ul>
				</xsl:element>
				<xsl:apply-templates select="//film">
					<xsl:sort select="//titre"/>
				</xsl:apply-templates>
			</body>
		</html>
	</xsl:template>
	<xsl:template match="film">
		<xsl:apply-templates select="photo"/>
		<xsl:element name="h2">
			<xsl:attribute name="id">film_<xsl:value-of select="position()"/></xsl:attribute>
			<xsl:value-of select="titre"/>
		</xsl:element>
		<h4><xsl:value-of select="realisateur"/> (<xsl:value-of select="annee"/>)</h4>
		<h3>Casting</h3>
		<ul>
			<xsl:apply-templates select=".//acteur"/>
		</ul>
		<h3>Synopsis</h3>
		<xsl:apply-templates select="synopsis"/>
	</xsl:template>
	<xsl:template match="acteur">
		<li><xsl:value-of select="."/> (<xsl:value-of select="@personnage"/>)</li>
	</xsl:template>
	<xsl:template match="perso">
		<xsl:variable name="ref" select="@ref"/>
		<xsl:variable name="personnage" select="//acteur[@id=$ref]"/>
		<xsl:value-of select="$personnage/@personnage"/> (<xsl:value-of select="$personnage/text()"/>)
	</xsl:template>
	<xsl:template match="photo">
		<xsl:element name="img">
			<xsl:attribute name="class">photo</xsl:attribute>
			<xsl:attribute name="src">photo/<xsl:value-of select="@href"/></xsl:attribute>
			<xsl:attribute name="alt">photo de <xsl:value-of select="../titre"/></xsl:attribute>
		</xsl:element>
	</xsl:template>
</xsl:stylesheet>