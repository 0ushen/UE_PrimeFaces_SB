package entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
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
@Table(name = "person")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Person.findAll", query = "SELECT p FROM Person p")
    , @NamedQuery(name = "Person.findByPersonId", query = "SELECT p FROM Person p WHERE p.personId = :personId")
    , @NamedQuery(name = "Person.findByAddress", query = "SELECT p FROM Person p WHERE p.address = :address")
    , @NamedQuery(name = "Person.findByCity", query = "SELECT p FROM Person p WHERE p.city = :city")
    , @NamedQuery(name = "Person.findByCountry", query = "SELECT p FROM Person p WHERE p.country = :country")
    , @NamedQuery(name = "Person.findByDateOfBirth", query = "SELECT p FROM Person p WHERE p.dateOfBirth = :dateOfBirth")
    , @NamedQuery(name = "Person.findByEmail", query = "SELECT p FROM Person p WHERE p.email = :email")
    , @NamedQuery(name = "Person.findByFirstName", query = "SELECT p FROM Person p WHERE p.firstName = :firstName")
    , @NamedQuery(name = "Person.findByIsJuryMember", query = "SELECT p FROM Person p WHERE p.isJuryMember = :isJuryMember")
    , @NamedQuery(name = "Person.findByIsTeacher", query = "SELECT p FROM Person p WHERE p.isTeacher = :isTeacher")
    , @NamedQuery(name = "Person.findByLastName", query = "SELECT p FROM Person p WHERE p.lastName = :lastName")
    , @NamedQuery(name = "Person.findByMobile", query = "SELECT p FROM Person p WHERE p.mobile = :mobile")
    , @NamedQuery(name = "Person.findByPostalCode", query = "SELECT p FROM Person p WHERE p.postalCode = :postalCode")
    , @NamedQuery(name="Person.findByEntity", //NOT USED BECAUSE OF BAD PERFORMANCE
                query="SELECT p FROM Person p WHERE "
                        + "(:firstName IS NULL OR p.firstName = :firstName) AND "
                        + "(:lastName IS NULL OR p.lastName = :lastName) AND "
                        + "(:country IS NULL OR p.country = :country) AND "
                        + "(:city IS NULL OR p.city = :city) AND "
                        + "(:postalCode IS NULL OR p.postalCode = :postalCode) AND "
                        + "(:address IS NULL OR LOWER(p.address) LIKE LOWER(:address2)) AND "
                        + "(CAST(:dateOfBirth AS DATE) IS NULL OR p.dateOfBirth = CAST(:dateOfBirth AS DATE)) AND "
                        + "(:email IS NULL OR LOWER(p.email) LIKE LOWER(:email2)) AND "
                        + "(CAST(:isTeacher AS BOOLEAN) IS NULL OR p.isTeacher = CAST(:isTeacher AS BOOLEAN)) AND "
                        + "(:mobile IS NULL OR p.mobile = :mobile)")
})
public class Person implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "person_id")
    private Integer personId;
    @Size(max = 255)
    @Column(name = "address")
    private String address;
    @Size(max = 255)
    @Column(name = "city")
    private String city;
    @Size(max = 255)
    @Column(name = "country")
    private String country;
    @Column(name = "date_of_birth")
    @Temporal(TemporalType.DATE)
    private Date dateOfBirth;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 255)
    @Column(name = "email")
    private String email;
    @Size(max = 255)
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "is_jury_member")
    private Boolean isJuryMember;
    @Column(name = "is_teacher")
    private Boolean isTeacher;
    @Size(max = 255)
    @Column(name = "last_name")
    private String lastName;
    @Size(max = 255)
    @Column(name = "mobile")
    private String mobile;
    @Size(max = 255)
    @Column(name = "postal_code")
    private String postalCode;
    @ManyToMany(mappedBy = "teacherList")
    private List<OrganizedUe> organizedUeTeacherList;
    @OneToMany(mappedBy = "teacher")
    private Collection<Planning> planningCollection;
    @OneToMany(mappedBy = "person")
    private Collection<StudentOrganizedUe> studentOrganizedUeCollection;
    @OneToMany(mappedBy = "teacher")
    private Collection<Section> sectionCollection;
    @OneToMany(mappedBy = "personId")
    private Collection<Presence> presenceCollection;

    public Person() {
    }

    public Person(Person person) {
        this.personId = person.personId;
        this.address = person.address;
        this.city = person.city;
        this.country = person.country;
        this.dateOfBirth = person.dateOfBirth;
        this.email = person.email;
        this.firstName = person.firstName;
        this.isJuryMember = person.isJuryMember;
        this.isTeacher = person.isTeacher;
        this.lastName = person.lastName;
        this.mobile = person.mobile;
        this.postalCode = person.postalCode;
        this.organizedUeTeacherList = person.organizedUeTeacherList;
        this.planningCollection = person.planningCollection;
        this.studentOrganizedUeCollection = person.studentOrganizedUeCollection;
        this.sectionCollection = person.sectionCollection;
        this.presenceCollection = person.presenceCollection;
    }
    
    

    public Person(Integer personId) {
        this.personId = personId;
    }

    public Integer getPersonId() {
        return personId;
    }

    public void setPersonId(Integer personId) {
        this.personId = personId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Boolean getIsJuryMember() {
        return isJuryMember;
    }

    public void setIsJuryMember(Boolean isJuryMember) {
        this.isJuryMember = isJuryMember;
    }

    public Boolean getIsTeacher() {
        return isTeacher;
    }

    public void setIsTeacher(Boolean isTeacher) {
        this.isTeacher = isTeacher;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    @XmlTransient
    public List<OrganizedUe> getOrganizedUeTeacherList() {
        return organizedUeTeacherList;
    }

    public void setOrganizedUeTeacherList(List<OrganizedUe> organizedUeTeacherList) {
        this.organizedUeTeacherList = organizedUeTeacherList;
    }
    
    

    @XmlTransient
    public Collection<Planning> getPlanningCollection() {
        return planningCollection;
    }

    public void setPlanningCollection(Collection<Planning> planningCollection) {
        this.planningCollection = planningCollection;
    }

    @XmlTransient
    public Collection<StudentOrganizedUe> getStudentOrganizedUeCollection() {
        return studentOrganizedUeCollection;
    }

    public void setStudentOrganizedUeCollection(Collection<StudentOrganizedUe> studentOrganizedUeCollection) {
        this.studentOrganizedUeCollection = studentOrganizedUeCollection;
    }

    @XmlTransient
    public Collection<Section> getSectionCollection() {
        return sectionCollection;
    }

    public void setSectionCollection(Collection<Section> sectionCollection) {
        this.sectionCollection = sectionCollection;
    }

    @XmlTransient
    public Collection<Presence> getPresenceCollection() {
        return presenceCollection;
    }

    public void setPresenceCollection(Collection<Presence> presenceCollection) {
        this.presenceCollection = presenceCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (personId != null ? personId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Person)) {
            return false;
        }
        Person other = (Person) object;
        if ((this.personId == null && other.personId != null) || (this.personId != null && !this.personId.equals(other.personId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Person{" + 
                "id=" + personId + 
                ", firstName=" + firstName + 
                ", lastName=" + lastName + 
                ", country=" + country + 
                ", city=" + city + 
                ", postalCode=" + postalCode + 
                ", address=" + address + 
                ", dateOfBirth=" + dateOfBirth + 
                ", email=" + email + 
                ", isTeacher=" + isTeacher + 
                ", mobile=" + mobile +
                '}';
    }
    
}
