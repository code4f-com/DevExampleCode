package com.tuanpla.javaxt.sql;

//******************************************************************************
//**  Value Class
//******************************************************************************
/**
 *   Used to represent a value for a given field in the database. The value can
 *   be converted into a number of Java primatives (strings, integers, doubles,
 *   booleans, etc).
 *
 ******************************************************************************/

public class Value extends com.tuanpla.utils.Value {

  //**************************************************************************
  //** Constructor
  //**************************************************************************
  /** Creates a new instance of Value. */

    protected Value(Object value){
        super(value);
    }

    public java.sql.Timestamp toTimeStamp(){
        com.tuanpla.utils.Date date = toDate();
        if (date==null) return null;
        else return new java.sql.Timestamp(date.getDate().getTime());
    }
}
