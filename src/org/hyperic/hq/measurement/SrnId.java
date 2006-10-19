package org.hyperic.hq.measurement;
// Generated Oct 19, 2006 11:49:50 AM by Hibernate Tools 3.1.0.beta4



/**
 * SrnId generated by hbm2java
 */
public class SrnId  implements java.io.Serializable {

    // Fields    

     private Integer appdefType;
     private Integer instanceId;

     // Constructors

    /** default constructor */
    public SrnId() {
    }

    /** full constructor */
    public SrnId(Integer appdefType, Integer instanceId) {
        this.appdefType = appdefType;
        this.instanceId = instanceId;
    }
    
   
    // Property accessors
    public Integer getAppdefType() {
        return this.appdefType;
    }
    
    public void setAppdefType(Integer appdefType) {
        this.appdefType = appdefType;
    }
    public Integer getInstanceId() {
        return this.instanceId;
    }
    
    public void setInstanceId(Integer instanceId) {
        this.instanceId = instanceId;
    }



  // The following is extra code specified in the hbm.xml files

        public boolean equals(Object other)
        {
            if (this == other) return true;
            if (other == null) return false;
            if (!(other instanceof SrnId)) return false;
            SrnId castOther = (SrnId)other;
            return this.getAppdefType().intValue() == castOther.getAppdefType().intValue() &&
                   this.getInstanceId().intValue() == castOther.getInstanceId().intValue();
        }

        public int hashCode()
        {
            return 17;
        }
      
  // end of extra code specified in the hbm.xml files

}


