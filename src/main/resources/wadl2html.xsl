<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"
	xmlns:wadl="http://wadl.dev.java.net/2009/02" xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:html="http://www.w3.org/1999/xhtml"
	xmlns="http://www.w3.org/1999/xhtml" exclude-result-prefixes="xsl wadl xs html ns">

	<xsl:output method="html" encoding="UTF-8" indent="yes" />

	<xsl:variable name="title">Support Services API</xsl:variable>
	<xsl:variable name="baseUrl"><xsl:value-of select="/wadl:application/wadl:resources/@base"/></xsl:variable>

	<xsl:template match="wadl:application">
		<html>
		<head>
			<title><xsl:value-of select="$title"/></title>
		</head>
		<body>
		<h1><xsl:value-of select="$title"/></h1>
		<xsl:apply-templates/>
		</body>
		</html>
	</xsl:template>
	
	<xsl:template match="wadl:resources">
		resources
		<xsl:apply-templates/>
	</xsl:template>
	
	<xsl:template match="wadl:resource">
		<xsl:param name="path"/>
		<div class="resource">
		<xsl:value-of select="$path"/>		
		<xsl:apply-templates>
			<xsl:with-param name="path"><xsl:value-of select="$path"/>/<xsl:value-of select="@path"/></xsl:with-param>
		</xsl:apply-templates>
		</div> 
	</xsl:template>

</xsl:stylesheet>
