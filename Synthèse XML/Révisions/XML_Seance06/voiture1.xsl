<?xml version="1.0" encoding="ISO-8859-1" ?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:output method="html" version="html4.01" encoding="ISO-8859-1"
doctype-public="-//W3C//DTD HTML 4.01//EN" 
doctype-system="http://www.w3.org/TR/html4/strict.dtd" />
<xsl:template match="/">
		<html>
			<head>
				<title>Mes voitures</title>
			</head>
			<body>
			<xsl:apply-templates/>			
			</body>
		</html>
</xsl:template>	

	<xsl:template match="voiture">	
		<table border="1">  	
				<tr><th colspan="2">Voiture de marque <xsl:value-of select="marque"/></th></tr>
				<tr><xsl:apply-templates /></tr>
		</table>	
		<br/>	
	</xsl:template >
	
	<xsl:template match="modele">
		<td><xsl:value-of select="."/></td>
	</xsl:template >

	<xsl:template match="immatriculation">
		<td><xsl:value-of select="."/></td>
	</xsl:template >

	<xsl:template match="marque">
	</xsl:template >

	<xsl:template match="couleur">
	</xsl:template >	
</xsl:stylesheet>