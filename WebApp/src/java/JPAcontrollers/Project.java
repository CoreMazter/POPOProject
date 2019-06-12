/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JPAcontrollers;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author manie
 */
@Entity
@Table(name = "project")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Project.findAll", query = "SELECT p FROM Project p")
    , @NamedQuery(name = "Project.findById", query = "SELECT p FROM Project p WHERE p.id = :id")
    , @NamedQuery(name = "Project.findByCategory", query = "SELECT p FROM Project p WHERE p.category = :category")
    , @NamedQuery(name = "Project.findByElaborationDate", query = "SELECT p FROM Project p WHERE p.elaborationDate = :elaborationDate")
    , @NamedQuery(name = "Project.findByProgress", query = "SELECT p FROM Project p WHERE p.progress = :progress")})
public class Project implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "category")
    private int category;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "title")
    private String title;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "memberList")
    private String memberList;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "description")
    private String description;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "objectives")
    private String objectives;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Column(name = "img1")
    private byte[] img1;
    @Lob
    @Column(name = "img2")
    private byte[] img2;
    @Lob
    @Column(name = "img3")
    private byte[] img3;
    @Lob
    @Column(name = "img4")
    private byte[] img4;
    @Lob
    @Column(name = "img5")
    private byte[] img5;
    @Column(name = "elaborationDate")
    @Temporal(TemporalType.DATE)
    private Date elaborationDate;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Column(name = "requirementDoc")
    private byte[] requirementDoc;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Column(name = "exe")
    private byte[] exe;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Column(name = "sourceCode")
    private byte[] sourceCode;
    @Basic(optional = false)
    @NotNull
    @Column(name = "progress")
    private short progress;
    @JoinColumn(name = "projectOwner", referencedColumnName = "username")
    @ManyToOne(optional = false)
    private User projectOwner;
    @JoinColumn(name = "id", referencedColumnName = "id", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Category category1;

    public Project() {
    }

    public Project(Integer id) {
        this.id = id;
    }

    public Project(Integer id, int category, String title, String memberList, String description, String objectives, byte[] img1, byte[] requirementDoc, byte[] exe, byte[] sourceCode, short progress) {
        this.id = id;
        this.category = category;
        this.title = title;
        this.memberList = memberList;
        this.description = description;
        this.objectives = objectives;
        this.img1 = img1;
        this.requirementDoc = requirementDoc;
        this.exe = exe;
        this.sourceCode = sourceCode;
        this.progress = progress;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMemberList() {
        return memberList;
    }

    public void setMemberList(String memberList) {
        this.memberList = memberList;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getObjectives() {
        return objectives;
    }

    public void setObjectives(String objectives) {
        this.objectives = objectives;
    }

    public byte[] getImg1() {
        return img1;
    }

    public void setImg1(byte[] img1) {
        this.img1 = img1;
    }

    public byte[] getImg2() {
        return img2;
    }

    public void setImg2(byte[] img2) {
        this.img2 = img2;
    }

    public byte[] getImg3() {
        return img3;
    }

    public void setImg3(byte[] img3) {
        this.img3 = img3;
    }

    public byte[] getImg4() {
        return img4;
    }

    public void setImg4(byte[] img4) {
        this.img4 = img4;
    }

    public byte[] getImg5() {
        return img5;
    }

    public void setImg5(byte[] img5) {
        this.img5 = img5;
    }

    public Date getElaborationDate() {
        return elaborationDate;
    }

    public void setElaborationDate(Date elaborationDate) {
        this.elaborationDate = elaborationDate;
    }

    public byte[] getRequirementDoc() {
        return requirementDoc;
    }

    public void setRequirementDoc(byte[] requirementDoc) {
        this.requirementDoc = requirementDoc;
    }

    public byte[] getExe() {
        return exe;
    }

    public void setExe(byte[] exe) {
        this.exe = exe;
    }

    public byte[] getSourceCode() {
        return sourceCode;
    }

    public void setSourceCode(byte[] sourceCode) {
        this.sourceCode = sourceCode;
    }

    public short getProgress() {
        return progress;
    }

    public void setProgress(short progress) {
        this.progress = progress;
    }

    public User getProjectOwner() {
        return projectOwner;
    }

    public void setProjectOwner(User projectOwner) {
        this.projectOwner = projectOwner;
    }

    public Category getCategory1() {
        return category1;
    }

    public void setCategory1(Category category1) {
        this.category1 = category1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Project)) {
            return false;
        }
        Project other = (Project) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "JPAcontrollers.Project[ id=" + id + " ]";
    }
    
}
