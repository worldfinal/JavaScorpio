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
 * Class Hero.
 * 
 * @version $Revision$ $Date$
 */
public class Hero implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _number.
     */
    private java.lang.String _number;

    /**
     * Field _password.
     */
    private java.lang.String _password;

    /**
     * Field _name.
     */
    private java.lang.String _name;

    /**
     * Field _delay.
     */
    private long _delay;

    /**
     * keeps track of state for field: _delay
     */
    private boolean _has_delay;

    /**
     * Field _running_time.
     */
    private com.hsbc.frc.SevenHero.beans.Running_time _running_time;

    /**
     * Field _addBuildingCard.
     */
    private java.lang.String _addBuildingCard;


      //----------------/
     //- Constructors -/
    //----------------/

    public Hero() {
        super();
    }


      //-----------/
     //- Methods -/
    //-----------/

    /**
     */
    public void deleteDelay(
    ) {
        this._has_delay= false;
    }

    /**
     * Returns the value of field 'addBuildingCard'.
     * 
     * @return the value of field 'AddBuildingCard'.
     */
    public java.lang.String getAddBuildingCard(
    ) {
        return this._addBuildingCard;
    }

    /**
     * Returns the value of field 'delay'.
     * 
     * @return the value of field 'Delay'.
     */
    public long getDelay(
    ) {
        return this._delay;
    }

    /**
     * Returns the value of field 'name'.
     * 
     * @return the value of field 'Name'.
     */
    public java.lang.String getName(
    ) {
        return this._name;
    }

    /**
     * Returns the value of field 'number'.
     * 
     * @return the value of field 'Number'.
     */
    public java.lang.String getNumber(
    ) {
        return this._number;
    }

    /**
     * Returns the value of field 'password'.
     * 
     * @return the value of field 'Password'.
     */
    public java.lang.String getPassword(
    ) {
        return this._password;
    }

    /**
     * Returns the value of field 'running_time'.
     * 
     * @return the value of field 'Running_time'.
     */
    public com.hsbc.frc.SevenHero.beans.Running_time getRunning_time(
    ) {
        return this._running_time;
    }

    /**
     * Method hasDelay.
     * 
     * @return true if at least one Delay has been added
     */
    public boolean hasDelay(
    ) {
        return this._has_delay;
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
     * Sets the value of field 'addBuildingCard'.
     * 
     * @param addBuildingCard the value of field 'addBuildingCard'.
     */
    public void setAddBuildingCard(
            final java.lang.String addBuildingCard) {
        this._addBuildingCard = addBuildingCard;
    }

    /**
     * Sets the value of field 'delay'.
     * 
     * @param delay the value of field 'delay'.
     */
    public void setDelay(
            final long delay) {
        this._delay = delay;
        this._has_delay = true;
    }

    /**
     * Sets the value of field 'name'.
     * 
     * @param name the value of field 'name'.
     */
    public void setName(
            final java.lang.String name) {
        this._name = name;
    }

    /**
     * Sets the value of field 'number'.
     * 
     * @param number the value of field 'number'.
     */
    public void setNumber(
            final java.lang.String number) {
        this._number = number;
    }

    /**
     * Sets the value of field 'password'.
     * 
     * @param password the value of field 'password'.
     */
    public void setPassword(
            final java.lang.String password) {
        this._password = password;
    }

    /**
     * Sets the value of field 'running_time'.
     * 
     * @param running_time the value of field 'running_time'.
     */
    public void setRunning_time(
            final com.hsbc.frc.SevenHero.beans.Running_time running_time) {
        this._running_time = running_time;
    }

    /**
     * Method unmarshal.
     * 
     * @param reader
     * @throws org.exolab.castor.xml.MarshalException if object is
     * null or if any SAXException is thrown during marshaling
     * @throws org.exolab.castor.xml.ValidationException if this
     * object is an invalid instance according to the schema
     * @return the unmarshaled com.hsbc.frc.SevenHero.beans.Hero
     */
    public static com.hsbc.frc.SevenHero.beans.Hero unmarshal(
            final java.io.Reader reader)
    throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
        return (com.hsbc.frc.SevenHero.beans.Hero) Unmarshaller.unmarshal(com.hsbc.frc.SevenHero.beans.Hero.class, reader);
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
