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
 * Class Period.
 * 
 * @version $Revision$ $Date$
 */
public class Period implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _from.
     */
    private long _from;

    /**
     * keeps track of state for field: _from
     */
    private boolean _has_from;

    /**
     * Field _to.
     */
    private long _to;

    /**
     * keeps track of state for field: _to
     */
    private boolean _has_to;


      //----------------/
     //- Constructors -/
    //----------------/

    public Period() {
        super();
    }


      //-----------/
     //- Methods -/
    //-----------/

    /**
     */
    public void deleteFrom(
    ) {
        this._has_from= false;
    }

    /**
     */
    public void deleteTo(
    ) {
        this._has_to= false;
    }

    /**
     * Returns the value of field 'from'.
     * 
     * @return the value of field 'From'.
     */
    public long getFrom(
    ) {
        return this._from;
    }

    /**
     * Returns the value of field 'to'.
     * 
     * @return the value of field 'To'.
     */
    public long getTo(
    ) {
        return this._to;
    }

    /**
     * Method hasFrom.
     * 
     * @return true if at least one From has been added
     */
    public boolean hasFrom(
    ) {
        return this._has_from;
    }

    /**
     * Method hasTo.
     * 
     * @return true if at least one To has been added
     */
    public boolean hasTo(
    ) {
        return this._has_to;
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
     * Sets the value of field 'from'.
     * 
     * @param from the value of field 'from'.
     */
    public void setFrom(
            final long from) {
        this._from = from;
        this._has_from = true;
    }

    /**
     * Sets the value of field 'to'.
     * 
     * @param to the value of field 'to'.
     */
    public void setTo(
            final long to) {
        this._to = to;
        this._has_to = true;
    }

    /**
     * Method unmarshal.
     * 
     * @param reader
     * @throws org.exolab.castor.xml.MarshalException if object is
     * null or if any SAXException is thrown during marshaling
     * @throws org.exolab.castor.xml.ValidationException if this
     * object is an invalid instance according to the schema
     * @return the unmarshaled com.hsbc.frc.SevenHero.beans.Period
     */
    public static com.hsbc.frc.SevenHero.beans.Period unmarshal(
            final java.io.Reader reader)
    throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
        return (com.hsbc.frc.SevenHero.beans.Period) Unmarshaller.unmarshal(com.hsbc.frc.SevenHero.beans.Period.class, reader);
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
