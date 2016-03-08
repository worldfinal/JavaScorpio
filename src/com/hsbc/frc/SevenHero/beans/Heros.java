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
 * Class Heros.
 * 
 * @version $Revision$ $Date$
 */
public class Heros implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _heroList.
     */
    private java.util.Vector _heroList;


      //----------------/
     //- Constructors -/
    //----------------/

    public Heros() {
        super();
        this._heroList = new java.util.Vector();
    }


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * 
     * 
     * @param vHero
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addHero(
            final com.hsbc.frc.SevenHero.beans.Hero vHero)
    throws java.lang.IndexOutOfBoundsException {
        this._heroList.addElement(vHero);
    }

    /**
     * 
     * 
     * @param index
     * @param vHero
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addHero(
            final int index,
            final com.hsbc.frc.SevenHero.beans.Hero vHero)
    throws java.lang.IndexOutOfBoundsException {
        this._heroList.add(index, vHero);
    }

    /**
     * Method enumerateHero.
     * 
     * @return an Enumeration over all
     * com.hsbc.frc.SevenHero.beans.Hero elements
     */
    public java.util.Enumeration enumerateHero(
    ) {
        return this._heroList.elements();
    }

    /**
     * Method getHero.
     * 
     * @param index
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     * @return the value of the com.hsbc.frc.SevenHero.beans.Hero
     * at the given index
     */
    public com.hsbc.frc.SevenHero.beans.Hero getHero(
            final int index)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._heroList.size()) {
            throw new IndexOutOfBoundsException("getHero: Index value '" + index + "' not in range [0.." + (this._heroList.size() - 1) + "]");
        }
        
        return (com.hsbc.frc.SevenHero.beans.Hero) _heroList.get(index);
    }

    /**
     * Method getHero.Returns the contents of the collection in an
     * Array.  <p>Note:  Just in case the collection contents are
     * changing in another thread, we pass a 0-length Array of the
     * correct type into the API call.  This way we <i>know</i>
     * that the Array returned is of exactly the correct length.
     * 
     * @return this collection as an Array
     */
    public com.hsbc.frc.SevenHero.beans.Hero[] getHero(
    ) {
        com.hsbc.frc.SevenHero.beans.Hero[] array = new com.hsbc.frc.SevenHero.beans.Hero[0];
        return (com.hsbc.frc.SevenHero.beans.Hero[]) this._heroList.toArray(array);
    }

    /**
     * Method getHeroCount.
     * 
     * @return the size of this collection
     */
    public int getHeroCount(
    ) {
        return this._heroList.size();
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
     */
    public void removeAllHero(
    ) {
        this._heroList.clear();
    }

    /**
     * Method removeHero.
     * 
     * @param vHero
     * @return true if the object was removed from the collection.
     */
    public boolean removeHero(
            final com.hsbc.frc.SevenHero.beans.Hero vHero) {
        boolean removed = _heroList.remove(vHero);
        return removed;
    }

    /**
     * Method removeHeroAt.
     * 
     * @param index
     * @return the element removed from the collection
     */
    public com.hsbc.frc.SevenHero.beans.Hero removeHeroAt(
            final int index) {
        java.lang.Object obj = this._heroList.remove(index);
        return (com.hsbc.frc.SevenHero.beans.Hero) obj;
    }

    /**
     * 
     * 
     * @param index
     * @param vHero
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void setHero(
            final int index,
            final com.hsbc.frc.SevenHero.beans.Hero vHero)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._heroList.size()) {
            throw new IndexOutOfBoundsException("setHero: Index value '" + index + "' not in range [0.." + (this._heroList.size() - 1) + "]");
        }
        
        this._heroList.set(index, vHero);
    }

    /**
     * 
     * 
     * @param vHeroArray
     */
    public void setHero(
            final com.hsbc.frc.SevenHero.beans.Hero[] vHeroArray) {
        //-- copy array
        _heroList.clear();
        
        for (int i = 0; i < vHeroArray.length; i++) {
                this._heroList.add(vHeroArray[i]);
        }
    }

    /**
     * Method unmarshal.
     * 
     * @param reader
     * @throws org.exolab.castor.xml.MarshalException if object is
     * null or if any SAXException is thrown during marshaling
     * @throws org.exolab.castor.xml.ValidationException if this
     * object is an invalid instance according to the schema
     * @return the unmarshaled com.hsbc.frc.SevenHero.beans.Heros
     */
    public static com.hsbc.frc.SevenHero.beans.Heros unmarshal(
            final java.io.Reader reader)
    throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
        return (com.hsbc.frc.SevenHero.beans.Heros) Unmarshaller.unmarshal(com.hsbc.frc.SevenHero.beans.Heros.class, reader);
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
