package org.hyperic.hq.measurement;
// Generated Oct 19, 2006 11:49:50 AM by Hibernate Tools 3.1.0.beta4


import java.math.BigDecimal;

/**
 * MeasurementHistData generated by hbm2java
 */
public class MeasurementHistData  implements java.io.Serializable {

    // Fields    

     private MeasurementDataId id;
     private BigDecimal value;
     private BigDecimal minValue;
     private BigDecimal maxValue;

     // Constructors

    /** default constructor */
    public MeasurementHistData() {
    }

	/** minimal constructor */
    public MeasurementHistData(MeasurementDataId id) {
        this.id = id;
    }
    /** full constructor */
    public MeasurementHistData(MeasurementDataId id, BigDecimal value, BigDecimal minValue, BigDecimal maxValue) {
        this.id = id;
        this.value = value;
        this.minValue = minValue;
        this.maxValue = maxValue;
    }
    
   
    // Property accessors
    public MeasurementDataId getId() {
        return this.id;
    }
    
    public void setId(MeasurementDataId id) {
        this.id = id;
    }
    public BigDecimal getValue() {
        return this.value;
    }
    
    public void setValue(BigDecimal value) {
        this.value = value;
    }
    public BigDecimal getMinValue() {
        return this.minValue;
    }
    
    public void setMinValue(BigDecimal minValue) {
        this.minValue = minValue;
    }
    public BigDecimal getMaxValue() {
        return this.maxValue;
    }
    
    public void setMaxValue(BigDecimal maxValue) {
        this.maxValue = maxValue;
    }


   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof MeasurementHistData) ) return false;
		 MeasurementHistData castOther = ( MeasurementHistData ) other; 
         
		 return ( (this.getId()==castOther.getId()) || ( this.getId()!=null && castOther.getId()!=null && this.getId().equals(castOther.getId()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getId() == null ? 0 : this.getId().hashCode() );
         
         
         
         return result;
   }   


}


