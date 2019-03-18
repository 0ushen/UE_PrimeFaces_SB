package entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;


@Entity
@Table(name = "capacity")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Capacity.findAll", query = "SELECT c FROM Capacity c")
    , @NamedQuery(name = "Capacity.findByCapacityId", query = "SELECT c FROM Capacity c WHERE c.capacityId = :capacityId")
    , @NamedQuery(name = "Capacity.findByDescription", query = "SELECT c FROM Capacity c WHERE c.description = :description")
    , @NamedQuery(name = "Capacity.findByIsThresholdOfSuccess", query = "SELECT c FROM Capacity c WHERE c.isThresholdOfSuccess = :isThresholdOfSuccess")
    , @NamedQuery(name = "Capacity.findByName", query = "SELECT c FROM Capacity c WHERE c.name = :name")
    , @NamedQuery(name = "Capacity.findByUe", query = "SELECT c FROM Capacity c WHERE c.ue.ueId = :ueId")
    , @NamedQuery(name="Capacity.findByEntity",
                query=  "SELECT c FROM Capacity c WHERE " + 
                        "(:name IS NULL OR c.name = :name) AND " +
                        "(:description IS NULL OR c.description = :description) AND " + 
                        "(:ueId IS NULL OR c.ue.ueId = :ueId) AND " + 
                        "(:isThresholdOfSuccess IS NULL OR c.isThresholdOfSuccess = :isThresholdOfSuccess)")
})
public class Capacity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "capacity_id")
    private Integer capacityId;
    @Size(max = 255)
    @Column(name = "description")
    private String description;
    @Column(name = "is_threshold_of_success")
    private Boolean isThresholdOfSuccess;
    @Size(max = 255)
    @Column(name = "name")
    private String name;
    @OneToMany(mappedBy = "capacity")
    private Collection<Indicator> indicatorCollection;
    @JoinColumn(name = "ue_id", referencedColumnName = "ue_id")
    @ManyToOne
    private Ue ue;
    

    public Capacity() {
    }
    
    // Constructor by copy
    public Capacity(Capacity capacity) {
        
        this.capacityId = capacity.capacityId;
        this.description = capacity.description;
        this.isThresholdOfSuccess = capacity.isThresholdOfSuccess;
        this.name = capacity.name;
        this.indicatorCollection = capacity.indicatorCollection;
        this.ue = capacity.ue;
        
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (capacityId != null ? capacityId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Capacity)) {
            return false;
        }
        Capacity other = (Capacity) object;
        if ((this.capacityId == null && other.capacityId != null) || (this.capacityId != null && !this.capacityId.equals(other.capacityId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Capacity[ capacityId = " + capacityId + 
                " | name = " + name + 
                " | description = "  + description +
                " ]";
    }
    
    

    public Capacity(Integer capacityId) {
        this.capacityId = capacityId;
    }

    public Integer getCapacityId() {
        return capacityId;
    }

    public void setCapacityId(Integer capacityId) {
        this.capacityId = capacityId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getIsThresholdOfSuccess() {
        return isThresholdOfSuccess;
    }

    public void setIsThresholdOfSuccess(Boolean isThresholdOfSuccess) {
        this.isThresholdOfSuccess = isThresholdOfSuccess;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlTransient
    public Collection<Indicator> getIndicatorCollection() {
        return indicatorCollection;
    }

    public void setIndicatorCollection(Collection<Indicator> indicatorCollection) {
        this.indicatorCollection = indicatorCollection;
    }

    public Ue getUe() {
        return ue;
    }

    public void setUe(Ue ue) {
        this.ue = ue;
    }
    
    
    
}
