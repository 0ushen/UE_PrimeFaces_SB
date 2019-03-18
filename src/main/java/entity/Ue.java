package entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
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
@Table(name = "ue")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ue.findAll", query = "SELECT u FROM Ue u")
    , @NamedQuery(name = "Ue.findByUeId", query = "SELECT u FROM Ue u WHERE u.ueId = :ueId")
    , @NamedQuery(name = "Ue.findByCode", query = "SELECT u FROM Ue u WHERE u.code = :code")
    , @NamedQuery(name = "Ue.findByDescription", query = "SELECT u FROM Ue u WHERE u.description = :description")
    , @NamedQuery(name = "Ue.findByIsDecisive", query = "SELECT u FROM Ue u WHERE u.isDecisive = :isDecisive")
    , @NamedQuery(name = "Ue.findByName", query = "SELECT u FROM Ue u WHERE u.name = :name")
    , @NamedQuery(name = "Ue.findByNumOfPeriods", query = "SELECT u FROM Ue u WHERE u.numOfPeriods = :numOfPeriods")
    , @NamedQuery(name="UE.findByEntity", //NOT USED BECAUSE OF BAD PERFORMANCE
                query=  "SELECT u FROM Ue u WHERE " + 
                        "(:name IS NULL OR u.name = :name) AND " +
                        "(:description IS NULL OR u.description = :description) AND " + 
                        "(CAST(:sectionId AS INTEGER) IS NULL OR u.section.sectionId = CAST(:sectionId AS INTEGER)) AND " + 
                        "(:code IS NULL OR u.code = :code) AND " + 
                        "(CAST(:numOfPeriods AS INTEGER) IS NULL OR u.numOfPeriods = CAST(:numOfPeriods AS INTEGER)) AND " + 
                        "(CAST(:isDecisive AS BOOLEAN) IS NULL OR u.isDecisive = CAST(:isDecisive AS BOOLEAN))")
})
public class Ue implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ue_id")
    private Integer ueId;
    @Size(max = 255)
    @Column(name = "code")
    private String code;
    @Size(max = 255)
    @Column(name = "description")
    private String description;
    @Column(name = "is_decisive")
    private Boolean isDecisive;
    @Size(max = 255)
    @Column(name = "name")
    private String name;
    @Column(name = "num_of_periods")
    private Integer numOfPeriods;
    @JoinColumn(name = "section_id", referencedColumnName = "section_id")
    @ManyToOne
    private Section section;
    @OneToMany(mappedBy = "ue")
    private Collection<OrganizedUe> organizedUeCollection;
    @OneToMany(mappedBy = "ue")
    private List<Capacity> capacityList;

    public Ue() {
    }

    public Ue(Ue ue) {
        
        this.ueId = ue.ueId;
        this.code = ue.code;
        this.description = ue.description;
        this.isDecisive = ue.isDecisive;
        this.name = ue.name;
        this.numOfPeriods = ue.numOfPeriods;
        this.section = ue.section;
        this.organizedUeCollection = ue.organizedUeCollection;
        this.capacityList = ue.capacityList;
        
    }
    
    

    public Ue(Integer ueId) {
        this.ueId = ueId;
    }

    public Integer getUeId() {
        return ueId;
    }

    public void setUeId(Integer ueId) {
        this.ueId = ueId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getIsDecisive() {
        return isDecisive;
    }

    public void setIsDecisive(Boolean isDecisivee) {
        System.out.print(isDecisivee);
        this.isDecisive = isDecisivee;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNumOfPeriods() {
        return numOfPeriods;
    }

    public void setNumOfPeriods(Integer numOfPeriods) {
        this.numOfPeriods = numOfPeriods;
    }

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    @XmlTransient
    public Collection<OrganizedUe> getOrganizedUeCollection() {
        return organizedUeCollection;
    }

    public void setOrganizedUeCollection(Collection<OrganizedUe> organizedUeCollection) {
        this.organizedUeCollection = organizedUeCollection;
    }

    @XmlTransient
    public List<Capacity> getCapacityList() {
        return capacityList;
    }

    public void setCapacityList(List<Capacity> capacityList) {
        this.capacityList = capacityList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ueId != null ? ueId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ue)) {
            return false;
        }
        Ue other = (Ue) object;
        if ((this.ueId == null && other.ueId != null) || (this.ueId != null && !this.ueId.equals(other.ueId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Ue[ ueId=" + ueId +
                ", name=" + name + 
                ", code=" + code +
                ", number of periods=" + numOfPeriods +
                ", description=" + description +
                ", isDecisive=" + isDecisive +
                ", section=" + section.getName() +
                " ]";
    }
    
}
