/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 1.1.2.1</a>, using an XML
 * Schema.
 * $Id$
 */

package com.hsbc.frc.SevenHero.beans;

  //---------------------------------/
 //- Imported classes and packages -/
//---------------------------------/

import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;

/**
 * Class SevenHeroConfig.
 * 
 * @version $Revision$ $Date$
 */
public class SevenHeroConfig implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _heros.
     */
    private com.hsbc.frc.SevenHero.beans.Heros _heros;

    /**
     * Field _news.
     */
    private com.hsbc.frc.SevenHero.beans.News _news;


      //----------------/
     //- Constructors -/
    //----------------/

    public SevenHeroConfig() {
        super();
    }


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Returns the value of field 'heros'.
     * 
     * @return the value of field 'Heros'.
     */
    public com.hsbc.frc.SevenHero.beans.Heros getHeros(
    ) {
        return this._heros;
    }

    /**
     * Returns the value of field 'news'.
     * 
     * @return the value of field 'News'.
     */
    public com.hsbc.frc.SevenHero.beans.News getNews(
    ) {
        return this._news;
    }

    /**
     * Method isValid.
     * 
     * @return true if this object is valid according to the schema
     */
    public boolean isValid(
    ) {
        try {
            validate();
        } catch (org.exolab.castor.xml.ValidationException vex) {
            return false;
        }
        return true;
    }

    /**
     * 
     * 
     * @param out
     * @throws org.exolab.castor.xml.MarshalException if object is
     * null or if any SAXException is thrown during marshaling
     * @throws org.exolab.castor.xml.ValidationException if this
     * object is an invalid instance according to the schema
     */
    public void marshal(
            final java.io.Writer out)
    throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
        Marshaller.marshal(this, out);
    }

    /**
     * 
     * 
     * @param handler
     * @throws java.io.IOException if an IOException occurs during
     * marshaling
     * @throws org.exolab.castor.xml.ValidationException if this
     * object is an invalid instance according to the schema
     * @throws org.exolab.castor.xml.MarshalException if object is
     * null or if any SAXException is thrown during marshaling
     */
    public void marshal(
            final org.xml.sax.ContentHandler handler)
    throws java.io.IOException, org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
        Marshaller.marshal(this, handler);
    }

    /**
     * Sets the value of field 'heros'.
     * 
     * @param heros the value of field 'heros'.
     */
    public void setHeros(
            final com.hsbc.frc.SevenHero.beans.Heros heros) {
        this._heros = heros;
    }

    /**
     * Sets the value of field 'news'.
     * 
     * @param news the value of field 'news'.
     */
    public void setNews(
            final com.hsbc.frc.SevenHero.beans.News news) {
        this._news = news;
    }

    /**
     * Method unmarshal.
     * 
     * @param reader
     * @throws org.exolab.castor.xml.MarshalException if object is
     * null or if any SAXException is thrown during marshaling
     * @throws org.exolab.castor.xml.ValidationException if this
     * object is an invalid instance according to the schema
     * @return the unmarshaled
     * com.hsbc.frc.SevenHero.beans.SevenHeroConfig
     */
    public static com.hsbc.frc.SevenHero.beans.SevenHeroConfig unmarshal(
            final java.io.Reader reader)
    throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
        return (com.hsbc.frc.SevenHero.beans.SevenHeroConfig) Unmarshaller.unmarshal(com.hsbc.frc.SevenHero.beans.SevenHeroConfig.class, reader);
    }

    /**
     * 
     * 
     * @throws org.exolab.castor.xml.ValidationException if this
     * object is an invalid instance according to the schema
     */
    public void validate(
    )
    throws org.exolab.castor.xml.ValidationException {
        org.exolab.castor.xml.Validator validator = new org.exolab.castor.xml.Validator();
        validator.validate(this);
    }

}
