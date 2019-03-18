package entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
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
@Table(name = "student_organized_ue")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "StudentOrganizedUe.findAll", query = "SELECT s FROM StudentOrganizedUe s")
    , @NamedQuery(name = "StudentOrganizedUe.findByComment", query = "SELECT s FROM StudentOrganizedUe s WHERE s.comment = :comment")
    , @NamedQuery(name = "StudentOrganizedUe.findByPersonId", query = "SELECT s FROM StudentOrganizedUe s WHERE s.studentOrganizedUePK.personId = :personId")
    , @NamedQuery(name = "StudentOrganizedUe.findByOrganizedUeId", query = "SELECT s FROM StudentOrganizedUe s WHERE s.studentOrganizedUePK.organizedUeId = :organizedUeId")})
public class StudentOrganizedUe implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected StudentOrganizedUePK studentOrganizedUePK;
    @Size(max = 255)
    @Column(name = "comment")
    private String comment;
    @OneToMany(mappedBy = "studentOrganizedUe")
    private Collection<Evaluation> evaluationCollection;
    @JoinColumn(name = "organized_ue_id", referencedColumnName = "organized_ue_id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private OrganizedUe organizedUe;
    @JoinColumn(name = "person_id", referencedColumnName = "person_id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Person person;

    public StudentOrganizedUe() {
    }

    public StudentOrganizedUe(StudentOrganizedUePK studentOrganizedUePK) {
        this.studentOrganizedUePK = studentOrganizedUePK;
    }

    public StudentOrganizedUe(int personId, int organizedUeId) {
        this.studentOrganizedUePK = new StudentOrganizedUePK(personId, organizedUeId);
    }

    public StudentOrganizedUePK getStudentOrganizedUePK() {
        return studentOrganizedUePK;
    }

    public void setStudentOrganizedUePK(StudentOrganizedUePK studentOrganizedUePK) {
        this.studentOrganizedUePK = studentOrganizedUePK;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @XmlTransient
    public Collection<Evaluation> getEvaluationCollection() {
        return evaluationCollection;
    }

    public void setEvaluationCollection(Collection<Evaluation> evaluationCollection) {
        this.evaluationCollection = evaluationCollection;
    }

    public OrganizedUe getOrganizedUe() {
        return organizedUe;
    }

    public void setOrganizedUe(OrganizedUe organizedUe) {
        this.organizedUe = organizedUe;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (studentOrganizedUePK != null ? studentOrganizedUePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof StudentOrganizedUe)) {
            return false;
        }
        StudentOrganizedUe other = (StudentOrganizedUe) object;
        if ((this.studentOrganizedUePK == null && other.studentOrganizedUePK != null) || (this.studentOrganizedUePK != null && !this.studentOrganizedUePK.equals(other.studentOrganizedUePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.StudentOrganizedUe[ studentOrganizedUePK=" + studentOrganizedUePK + " ]";
    }
    
}
