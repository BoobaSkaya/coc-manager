/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boobaskaya.cocclanmanager.tools;

import java.io.File;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class JAXBTools {

    public static <T> void toFile(T obj, Class<T> klass, File outputFile) throws JAXBException{
    	JAXBContext jcb = JAXBContext.newInstance(klass);
        Marshaller m = jcb.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        m.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
        m.marshal(obj, outputFile);
    }
    
    public static <T> T fromFile(File inputFile, Class<T> klass) throws JAXBException{
        JAXBContext jcb = JAXBContext.newInstance(klass);
        Unmarshaller u = jcb.createUnmarshaller();
        @SuppressWarnings("unchecked")
		T o = (T) u.unmarshal(inputFile);
        return o;
    }
    
    
    
}
