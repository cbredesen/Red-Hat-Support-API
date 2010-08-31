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
				body {
				    font-family: "Liberation Sans", "Lucida Grande", "Luxi Sans", "Trebuchet MS", "Bitstream Vera Sans", helvetica, verdana, arial, sans-serif;				
				}

				div h1 {
					font-size: 1em;
				}
				
				div.method {
					margin: 1ex 1em;
				}
				
				div.resource {
					border: 1px solid silver;
					background-color: #F3F3F3;
					margin: 2ex 0;
					padding: 1ex 1em;
					-moz-border-radius: 10px;
					-webkit-border-radius: 10px;				
				}
				
				div.resource h1 {
					font-size: 1.5em;
				}

				div.method h1 {
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

	<xsl:template match="wadl:doc">
		<div class="doc">
			<xsl:if test="@title">
			<h1><xsl:value-of select="@title" /></h1>
			</xsl:if>
			<xsl:value-of select="." />
		</div>
	</xsl:template>

	<xsl:template match="wadl:resources">
		<xsl:apply-templates/>
	</xsl:template>

	<xsl:template match="wadl:resource">
		<xsl:param name="path"/>
		<xsl:variable name="absolute-path"><xsl:value-of select="$path"/><xsl:value-of select="@path"/>/</xsl:variable>
		<div class="resource">
			<h1>Resource:&#160;<xsl:value-of select="$absolute-path" /></h1>
			<xsl:apply-templates select="wadl:doc" />
			<xsl:apply-templates select="wadl:param" />
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
			<xsl:apply-templates />
			<xsl:if test="wadl:representation">
				<ul class="representation">
				<xsl:for-each select="wadl:representation">
					<li><xsl:call-template name="representation"/></li>
				</xsl:for-each>
				</ul>
			</xsl:if>
		</div>
	</xsl:template>

	<xsl:template match="wadl:response">
		<div class="response">
			<h1>Response&#160;<xsl:value-of select="@status"/></h1>
			<xsl:apply-templates />
			<xsl:if test="wadl:representation">
				<ul class="representation">
				<xsl:for-each select="wadl:representation">
					<li><xsl:call-template name="representation"/></li>
				</xsl:for-each>
				</ul>
			</xsl:if>
		</div>
	</xsl:template>

	<xsl:template name="representation">
		<xsl:value-of select="@mediaType"/>
		<xsl:if test="@element">
			&#160;(Element: <xsl:value-of select="@element"/>)
		</xsl:if>
	</xsl:template>

	<xsl:template match="wadl:param">
		<div class="param">
			<xsl:value-of select="@style" /> parameter: <xsl:value-of select="@name" />
		</div>
	</xsl:template>

</xsl:stylesheet>
