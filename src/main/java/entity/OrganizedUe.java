package entity;


import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;


@Entity
@Table(name = "organized_ue")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OrganizedUe.findAll", query = "SELECT o FROM OrganizedUe o")
    , @NamedQuery(name = "OrganizedUe.findByOrganizedUeId", query = "SELECT o FROM OrganizedUe o WHERE o.organizedUeId = :organizedUeId")
    , @NamedQuery(name = "OrganizedUe.findByEndDate", query = "SELECT o FROM OrganizedUe o WHERE o.endDate = :endDate")
    , @NamedQuery(name = "OrganizedUe.findByName", query = "SELECT o FROM OrganizedUe o WHERE o.name = :name")
    , @NamedQuery(name = "OrganizedUe.findByStartDate", query = "SELECT o FROM OrganizedUe o WHERE o.startDate = :startDate")
    , @NamedQuery(name = "OrganizedUe.findBySection", query = "SELECT o FROM OrganizedUe o WHERE o.ue.section.sectionId = :sectionId")
    , @NamedQuery(name = "OrganizedUe.findByAcademicYear", query = "SELECT o FROM OrganizedUe o WHERE o.startDate BETWEEN :firstDate AND :secondDate")
    , @NamedQuery(name = "OrganizedUe.findBySectionAndAcademicYear", query = "SELECT o FROM OrganizedUe o WHERE o.ue.section.sectionId = :sectionId AND o.startDate BETWEEN :firstDate AND :secondDate")})
public class OrganizedUe implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "organized_ue_id")
    private Integer organizedUeId;
    @Column(name = "end_date")
    @Temporal(TemporalType.DATE)
    private Date endDate;
    @Size(max = 255)
    @Column(name = "name")
    private String name;
    @Column(name = "start_date")
    @Temporal(TemporalType.DATE)
    private Date startDate;
    @JoinTable(name = "teacher_organized_ue", joinColumns = {
        @JoinColumn(name = "organized_ue_id", referencedColumnName = "organized_ue_id")}, inverseJoinColumns = {
        @JoinColumn(name = "person_id", referencedColumnName = "person_id")})
    @ManyToMany
    private List<Person> teacherList;
    @OneToMany(mappedBy = "organizedUeId")
    private Collection<Indicator> indicatorCollection;
    @OneToMany(mappedBy = "organizedUe")
    private List<Planning> planningList;
    @OneToMany(mappedBy = "organizedUe")
    private Collection<StudentOrganizedUe> studentOrganizedUeCollection;
    @JoinColumn(name = "level_id", referencedColumnName = "level_id")
    @ManyToOne
    private Level level;
    @JoinColumn(name = "ue_id", referencedColumnName = "ue_id")
    @ManyToOne
    private Ue ue;

    public OrganizedUe() {
    }
    
    // Constructor by copy
    public OrganizedUe(OrganizedUe o) {
        this.name = o.getName();
        this.startDate = o.getStartDate();
        this.endDate = o.getEndDate();
        this.teacherList = o.getTeacherList();
        //this.indicatorCollection = o.getIndicatorCollection();
        //this.planningCollection = o.getPlanningCollection();
        //this.studentOrganizedUeCollection = o.getStudentOrganizedUeCollection();
        this.level = o.getLevel();
        this.ue = o.getUe();
    }

    public OrganizedUe(Integer organizedUeId) {
        this.organizedUeId = organizedUeId;
    }

    public Integer getOrganizedUeId() {
        return organizedUeId;
    }

    public void setOrganizedUeId(Integer organizedUeId) {
        this.organizedUeId = organizedUeId;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @XmlTransient
    public List<Person> getTeacherList() {
        return teacherList;
    }

    public void setTeacherList(List<Person> teacherList) {
        this.teacherList = teacherList;
    }

    @XmlTransient
    public Collection<Indicator> getIndicatorCollection() {
        return indicatorCollection;
    }

    public void setIndicatorCollection(Collection<Indicator> indicatorCollection) {
        this.indicatorCollection = indicatorCollection;
    }
    
    @XmlTransient
    public List<Planning> getPlanningList() {
        return planningList;
    }

    public void setPlanningList(List<Planning> planningList) {
        this.planningList = planningList;
    }

    @XmlTransient
    public Collection<StudentOrganizedUe> getStudentOrganizedUeCollection() {
        return studentOrganizedUeCollection;
    }

    public void setStudentOrganizedUeCollection(Collection<StudentOrganizedUe> studentOrganizedUeCollection) {
        this.studentOrganizedUeCollection = studentOrganizedUeCollection;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public Ue getUe() {
        return ue;
    }

    public void setUe(Ue ue) {
        this.ue = ue;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (organizedUeId != null ? organizedUeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OrganizedUe)) {
            return false;
        }
        OrganizedUe other = (OrganizedUe) object;
        if ((this.organizedUeId == null && other.organizedUeId != null) || (this.organizedUeId != null && !this.organizedUeId.equals(other.organizedUeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.OrganizedUe[ organizedUeId=" + organizedUeId + 
                ", name=" + name + 
                ", UE=" + ue.getName() +
                ", level=" + level + 
                ", startDate=" + startDate + 
                ", enDate=" + endDate +
                ", teacherList=" + teacherList +
                " ]";
    }
    
}
