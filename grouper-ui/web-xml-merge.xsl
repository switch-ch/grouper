<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
	<xsl:output doctype-public="-//Sun Microsystems, Inc.//DTD Web Application 2.3//EN" doctype-system="http://java.sun.com/dtd/web-app_2_3.dtd" indent="yes"/>
	<xsl:preserve-space elements="*"/>
	<xsl:param name="mergeXmlFile">C:\delete\grouper-qs\grouper-ui/temp/95.web.custom.filtered.xml</xsl:param>
	<xsl:param name="mergeTagsXmlFile">c:/projects/GrouperComplete/grouper-ui/web-xml-merge-tags.xml</xsl:param>
	<xsl:variable name="mergeXml" select="document($mergeXmlFile)"/>
	<xsl:variable name="mergeTagsXml" select="document($mergeTagsXmlFile)"/>
	<xsl:variable name="docRoot" select="/web-app"/>
	<xsl:template match="/">
		<web-app>
			<xsl:comment>DO NOT EDIT THIS FILE. IT WILL BE OVERWRITTEN. CHANGE YOUR FILE specified by the build.properties value [additional.web.xml]. The contents of that file are merged into ${grouper-ui}/webapp/WEB-INF/web.core.xml</xsl:comment>
			<xsl:choose>
				<xsl:when test="not($mergeXmlFile) or starts-with($mergeXmlFile,'$')">
					<xsl:copy-of select="$docRoot/*"/>
				</xsl:when>
				<xsl:otherwise>
					<xsl:apply-templates select="/web-app"/>
				</xsl:otherwise>
			</xsl:choose>
		</web-app>
	</xsl:template>
	<xsl:template match="web-app">
		<xsl:for-each select="$mergeTagsXml/merge-tags/tag">
			<xsl:call-template name="merge"/>
		</xsl:for-each>
	</xsl:template>
	<xsl:template name="merge">
		<xsl:choose>
			<xsl:when test="./@type='single'">
				<xsl:call-template name="singleType"/>
			</xsl:when>
			<xsl:when test="./@type='list'">
				<xsl:call-template name="listType"/>
			</xsl:when>
			<xsl:otherwise>
				<xsl:call-template name="sequenceType"/>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	<xsl:template name="singleType">
		<!-- If tag appears it should only appear once. Don't overwrite-->
		<xsl:variable name="tagname">
			<xsl:value-of select="@name"/>
		</xsl:variable>
		<xsl:choose>
			<xsl:when test="boolean($docRoot/*[name()=$tagname])">
				<xsl:copy-of select="$docRoot/*[name()=$tagname]"/>
			</xsl:when>
			<xsl:otherwise>
				<xsl:copy-of select="$mergeXml/web-app/*[name()=$tagname]"/>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	<xsl:template name="listType">
		<!-- if exist in either file put list tags then put merge list items then existing
    without duplicates -->
		<xsl:variable name="key">
			<xsl:value-of select="@key"/>
		</xsl:variable>
		<xsl:variable name="tagname">
			<xsl:value-of select="@name"/>
		</xsl:variable>
		<xsl:if test="boolean($docRoot/*[name()=$tagname]) or boolean($mergeXml/web-app/*[name()=$tagname])">
			<xsl:for-each select="./*[1]">
				<xsl:copy>
					<xsl:copy-of select="$mergeXml/web-app/*[name()=$tagname]/*[name()=$key]"/>
					<xsl:for-each select="$docRoot/*[name()=$tagname]/*[name()=$key]">
						<xsl:variable name="docText">
							<xsl:value-of select="./child::text()"/>
						</xsl:variable>
						<xsl:if test="not($mergeXml/web-app/*[name()=$tagname]/*[name()=$key]/child::text()=$docText)">
							<xsl:copy-of select="."/>
						</xsl:if>
					</xsl:for-each>
				</xsl:copy>
			</xsl:for-each>
		</xsl:if>
	</xsl:template>
	<xsl:template name="sequenceType">
	   <xsl:comment>Processing <xsl:value-of select="@name"/></xsl:comment>
		<xsl:variable name="key">
			<xsl:value-of select="@key"/>
		</xsl:variable>
		<xsl:variable name="tagname">
			<xsl:value-of select="@name"/>
		</xsl:variable>
		
		<xsl:for-each select="$mergeXml/web-app/*[name()=$tagname]">
			<xsl:if test="./*[name()=$key] or $tagname != 'error-page'">
				<xsl:comment>Inserting tag from merge file</xsl:comment>
				<xsl:copy-of select="."/>
			</xsl:if>
		</xsl:for-each>
		
		<xsl:for-each select="$docRoot/*[name()=$tagname]">
				
				<xsl:choose>
					<xsl:when test="$tagname='security-constraint'">
					
						<xsl:variable name="curDocKey">
							<xsl:value-of select="./web-resource-collection/url-pattern/child::text()"/>
						</xsl:variable>
						<xsl:variable name="mergeDocKey">
							<xsl:value-of select="$mergeXml/web-app/security-constraint/web-resource-collection/url-pattern/child::text()"/>
						</xsl:variable>
						
						<xsl:if test="not($curDocKey=$mergeDocKey)">
							<xsl:comment>Inserting tag from base file</xsl:comment>
							<xsl:copy-of select="."/>
						</xsl:if>
					</xsl:when>
					<xsl:otherwise>
					  <xsl:if test="./*[name()=$key]">
						<xsl:variable name="curDocKey">
							<xsl:value-of select="./*[name()=$key]"/>
						</xsl:variable>
						<xsl:variable name="mergeDocKey">
							<xsl:value-of select="$mergeXml/web-app/*[name()=$tagname]/*[name()=$key and child::text()=$curDocKey]"/>
						</xsl:variable>
						
							<xsl:if test="$tagname='security-constraint'">
	
						</xsl:if>
						<!-- If tag with same key didn't exist in merge file, insert here -->
						<xsl:if test="not(boolean($mergeXml/web-app/*[name()=$tagname]/*[name()=$key and child::text()=$curDocKey]))">
						<xsl:comment>Inserting tag from base file</xsl:comment>
							<xsl:copy-of select="."/>
						</xsl:if>
					  </xsl:if>
					</xsl:otherwise>
				</xsl:choose>
			
		</xsl:for-each>
	</xsl:template>
</xsl:stylesheet>
