<?xml version="1.0" encoding="UTF-8"?>
<WebElementEntity>
   <description>Boton dx-button por texto dinamico</description>
   <name>BUTTON_DxTexto-Dyn</name>
   <tag></tag>
   <elementGuidId>e1a2b3c4-0090-4f1a-9001-000000000003</elementGuidId>
   <selectorCollection>
      <entry>
         <key>BASIC</key>
      </entry>
      <entry>
         <key>XPATH</key>
         <value>(//dx-button[@text='${textoBoton}'] | //dx-button//span[contains(@class, 'dx-button-text') and contains(text(), '${textoBoton}')])[1]</value>
      </entry>
   </selectorCollection>
   <selectorMethod>XPATH</selectorMethod>
   <smartLocatorEnabled>false</smartLocatorEnabled>
   <useRalativeImagePath>false</useRalativeImagePath>
</WebElementEntity>
