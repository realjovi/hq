package org.hyperic.hq.measurement;
// Generated Oct 19, 2006 11:49:50 AM by Hibernate Tools 3.1.0.beta4



/**
 * MeasurementArg generated by hbm2java
 */
public class MeasurementArg  implements java.io.Serializable {

    // Fields    

     private Integer id;
     private long _version_;
     private Integer cid;
     private Integer placement;
     private Integer ticks;
     private double weight;
     private Integer previous;
     private MeasurementTemplate template;
     private MeasurementTemplate templateArg;

     // Constructors

    /** default constructor */
    public MeasurementArg() {
    }

	/** minimal constructor */
    public MeasurementArg(Integer placement) {
        this.placement = placement;
    }
    /** full constructor */
    public MeasurementArg(Integer cid, Integer placement, Integer ticks, double weight, Integer previous, MeasurementTemplate template, MeasurementTemplate templateArg) {
        this.cid = cid;
        this.placement = placement;
        this.ticks = ticks;
        this.weight = weight;
        this.previous = previous;
        this.template = template;
        this.templateArg = templateArg;
    }
    
   
    // Property accessors
    public Integer getId() {
        return this.id;
    }
    
    private void setId(Integer id) {
        this.id = id;
    }
    public long get_version_() {
        return this._version_;
    }
    
    public void set_version_(long _version_) {
        this._version_ = _version_;
    }
    public Integer getCid() {
        return this.cid;
    }
    
    public void setCid(Integer cid) {
        this.cid = cid;
    }
    public Integer getPlacement() {
        return this.placement;
    }
    
    public void setPlacement(Integer placement) {
        this.placement = placement;
    }
    public Integer getTicks() {
        return this.ticks;
    }
    
    public void setTicks(Integer ticks) {
        this.ticks = ticks;
    }
    public double getWeight() {
        return this.weight;
    }
    
    public void setWeight(double weight) {
        this.weight = weight;
    }
    public Integer getPrevious() {
        return this.previous;
    }
    
    public void setPrevious(Integer previous) {
        this.previous = previous;
    }
    public MeasurementTemplate getTemplate() {
        return this.template;
    }
    
    public void setTemplate(MeasurementTemplate template) {
        this.template = template;
    }
    public MeasurementTemplate getTemplateArg() {
        return this.templateArg;
    }
    
    public void setTemplateArg(MeasurementTemplate templateArg) {
        this.templateArg = templateArg;
    }


   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof MeasurementArg) ) return false;
		 MeasurementArg castOther = ( MeasurementArg ) other; 
         
		 return ( (this.getId()==castOther.getId()) || ( this.getId()!=null && castOther.getId()!=null && this.getId().equals(castOther.getId()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getId() == null ? 0 : this.getId().hashCode() );
         
         
         
         
         
         
         
         
         return result;
   }   


}


