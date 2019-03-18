/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Laurence
 */
@Entity
@Table(name = "evaluation")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Evaluation.findAll", query = "SELECT e FROM Evaluation e")
    , @NamedQuery(name = "Evaluation.findByEvaluationId", query = "SELECT e FROM Evaluation e WHERE e.evaluationId = :evaluationId")
    , @NamedQuery(name = "Evaluation.findByComment", query = "SELECT e FROM Evaluation e WHERE e.comment = :comment")
    , @NamedQuery(name = "Evaluation.findByResult", query = "SELECT e FROM Evaluation e WHERE e.result = :result")})
public class Evaluation implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "evaluation_id")
    private Integer evaluationId;
    @Size(max = 255)
    @Column(name = "comment")
    private String comment;
    @Size(max = 255)
    @Column(name = "result")
    private String result;
    @JoinColumn(name = "indicator_id", referencedColumnName = "indicator_id")
    @ManyToOne
    private Indicator indicatorId;
    @JoinColumns({
        @JoinColumn(name = "persion_id", referencedColumnName = "person_id")
        , @JoinColumn(name = "organized_ue_id", referencedColumnName = "organized_ue_id")})
    @ManyToOne
    private StudentOrganizedUe studentOrganizedUe;

    public Evaluation() {
    }

    public Evaluation(Integer evaluationId) {
        this.evaluationId = evaluationId;
    }

    public Integer getEvaluationId() {
        return evaluationId;
    }

    public void setEvaluationId(Integer evaluationId) {
        this.evaluationId = evaluationId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Indicator getIndicatorId() {
        return indicatorId;
    }

    public void setIndicatorId(Indicator indicatorId) {
        this.indicatorId = indicatorId;
    }

    public StudentOrganizedUe getStudentOrganizedUe() {
        return studentOrganizedUe;
    }

    public void setStudentOrganizedUe(StudentOrganizedUe studentOrganizedUe) {
        this.studentOrganizedUe = studentOrganizedUe;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (evaluationId != null ? evaluationId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Evaluation)) {
            return false;
        }
        Evaluation other = (Evaluation) object;
        if ((this.evaluationId == null && other.evaluationId != null) || (this.evaluationId != null && !this.evaluationId.equals(other.evaluationId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Evaluation[ evaluationId=" + evaluationId + " ]";
    }
    
}
