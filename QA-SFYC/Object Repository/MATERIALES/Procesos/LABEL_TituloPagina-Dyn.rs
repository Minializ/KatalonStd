<?xml version="1.0" encoding="UTF-8"?>
<WebElementEntity>
   <description>Titulo de pagina dinamico - busca texto dentro de spans y divs</description>
   <name>LABEL_TituloPagina-Dyn</name>
   <tag></tag>
   <elementGuidId>e1a2b3c4-0090-4f1a-9001-000000000001</elementGuidId>
   <selectorCollection>
      <entry>
         <key>BASIC</key>
      </entry>
      <entry>
         <key>XPATH</key>
         <value>(//span[contains(text(), '${titulo}')] | //div[contains(text(), '${titulo}')] | //h2[contains(text(), '${titulo}')])[1]</value>
      </entry>
   </selectorCollection>
   <selectorMethod>XPATH</selectorMethod>
   <smartLocatorEnabled>false</smartLocatorEnabled>
   <useRalativeImagePath>false</useRalativeImagePath>
</WebElementEntity>
