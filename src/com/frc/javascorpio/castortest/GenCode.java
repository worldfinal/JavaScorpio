package com.frc.javascorpio.castortest;

import java.io.IOException;

import org.exolab.castor.builder.SourceGenerator;
import org.exolab.castor.builder.SourceGeneratorMain;
import org.xml.sax.InputSource;

public class GenCode {
	public static void generateBean() throws IOException {
		String xmlSchema = "K:/java/JavaScorpio/source/scorpio.xsd";
		String pack = "com.frc.javascorpio.castortest.beans";
		String arr[] = new String[]{"-i", xmlSchema,
				"-package", pack,
				"-types", "j2"};
		SourceGeneratorMain.main(arr);
		
		if (0 < 1) {
			return;
		}
		SourceGenerator generator = new SourceGenerator();
        
        InputSource inputSource = new InputSource(xmlSchema);
        generator.setDestDir("");
        generator.setSuppressNonFatalWarnings(true);
        
        // uncomment the next line to set a binding file for source generation
//      generator.setBinding(new InputSource(getClass().getResource("binding.xml").toExternalForm()));

        // uncomment the next lines to set custom properties for source generation
//      Properties properties = new Properties();
//      properties.load(getClass().getResource("builder.properties").openStream());
//      generator.setDefaultProperties(properties);

        generator.generateSource(inputSource, pack);
	}
	public static void main(String[] args) throws IOException {
		generateBean();
	}
}
