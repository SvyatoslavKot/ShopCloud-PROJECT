//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.1 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2022.08.15 at 08:05:31 AM MSK 
//


package com.example.shop_module.app.xsd.greting;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the greting package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: greting
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetGreetingRequest }
     * 
     */
    public GetGreetingRequest createGetGreetingRequest() {
        return new GetGreetingRequest();
    }

    /**
     * Create an instance of {@link GetGreetingResponse }
     * 
     */
    public GetGreetingResponse createGetGreetingResponse() {
        return new GetGreetingResponse();
    }

    /**
     * Create an instance of {@link Greeting }
     * 
     */
    public Greeting createGreeting() {
        return new Greeting();
    }

}
