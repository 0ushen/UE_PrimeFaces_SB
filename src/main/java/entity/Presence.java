package entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;



@Entity
@Table(name = "presence")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Presence.findAll", query = "SELECT p FROM Presence p")
    , @NamedQuery(name = "Presence.findByPresenceId", query = "SELECT p FROM Presence p WHERE p.presenceId = :presenceId")
    , @NamedQuery(name = "Presence.findByComment", query = "SELECT p FROM Presence p WHERE p.comment = :comment")
    , @NamedQuery(name = "Presence.findByIsPresent", query = "SELECT p FROM Presence p WHERE p.isPresent = :isPresent")})
public class Presence implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "presence_id")
    private Integer presenceId;
    @Size(max = 255)
    @Column(name = "comment")
    private String comment;
    @Column(name = "is_present")
    private Boolean isPresent;
    @JoinColumn(name = "person_id", referencedColumnName = "person_id")
    @ManyToOne
    private Person personId;
    @JoinColumn(name = "planning_id", referencedColumnName = "planning_id")
    @ManyToOne
    private Planning planning;

    public Presence() {
    }

    public Presence(Integer presenceId) {
        this.presenceId = presenceId;
    }

    public Integer getPresenceId() {
        return presenceId;
    }

    public void setPresenceId(Integer presenceId) {
        this.presenceId = presenceId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Boolean getIsPresent() {
        return isPresent;
    }

    public void setIsPresent(Boolean isPresent) {
        this.isPresent = isPresent;
    }

    public Person getPersonId() {
        return personId;
    }

    public void setPersonId(Person personId) {
        this.personId = personId;
    }

    public Planning getPlanning() {
        return planning;
    }

    public void setPlanning(Planning planning) {
        this.planning = planning;
    }

    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (presenceId != null ? presenceId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Presence)) {
            return false;
        }
        Presence other = (Presence) object;
        if ((this.presenceId == null && other.presenceId != null) || (this.presenceId != null && !this.presenceId.equals(other.presenceId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Presence[ presenceId=" + presenceId + " ]";
    }
    
}
