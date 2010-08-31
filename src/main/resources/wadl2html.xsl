<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"
	xmlns:wadl="http://wadl.dev.java.net/2009/02" xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:html="http://www.w3.org/1999/xhtml"
	xmlns="http://www.w3.org/1999/xhtml" exclude-result-prefixes="xsl wadl xs html ns">

	<xsl:output method="html" encoding="UTF-8" indent="yes" />
	<xsl:strip-space elements="*"/>

	<xsl:variable name="title">Support Services API</xsl:variable>
	<xsl:variable name="base-url"><xsl:value-of select="/wadl:application/wadl:resources/@base"/></xsl:variable>

	<xsl:template match="wadl:application">
		<html>
		<head>
			<title><xsl:value-of select="$title"/></title>
			<style>
				div h1 {
					font-size: 1em;
				}
			</style>
		</head>
		<body>
		<h1><xsl:value-of select="$title"/></h1>
		<xsl:apply-templates/>
		</body>
		</html>
	</xsl:template>
	
	<xsl:template match="wadl:resources">
		<xsl:apply-templates/>
	</xsl:template>

	<xsl:template match="wadl:resource">
		<xsl:param name="path"/>
		<xsl:variable name="absolute-path"><xsl:value-of select="$path"/><xsl:value-of select="@path"/>/</xsl:variable>
		<div class="resource">
			<h1>Resource:&#160;<xsl:value-of select="$absolute-path" /></h1>
			<xsl:apply-templates select="wadl:method">
				<xsl:with-param name="path"><xsl:value-of select="$absolute-path"/></xsl:with-param>
			</xsl:apply-templates>
		</div> 
		<xsl:apply-templates select="wadl:resource">
			<xsl:with-param name="path"><xsl:value-of select="$absolute-path"/></xsl:with-param>
		</xsl:apply-templates>
	</xsl:template>

	<xsl:template match="wadl:method">
		<xsl:param name="path"/>
		<div class="method">
			<h1><xsl:value-of select="@name"/>&#160;<xsl:value-of select="$base-url"/><xsl:value-of select="$path"/></h1>
			<xsl:apply-templates/>
		</div>
	</xsl:template>

	<xsl:template match="wadl:request">
		<div class="request">
			<h1>Request</h1>
			<xsl:apply-templates/>
		</div>
	</xsl:template>

	<xsl:template match="wadl:response">
		<div class="response">
			<h1>Response&#160;<xsl:value-of select="@status"/></h1>
			<xsl:apply-templates/>
		</div>
	</xsl:template>

	<xsl:template match="wadl:representation">
		<div class="representation">
			<xsl:value-of select="@mediaType"/>
		</div>
	</xsl:template>

	<xsl:template match="wadl:param">
		<div class="param">
			<xsl:value-of select="@style" /> parameter: <xsl:value-of select="@name" />
		</div>
	</xsl:template>

</xsl:stylesheet>
