/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JPAcontrollers;

import JPAcontrollers.exceptions.IllegalOrphanException;
import JPAcontrollers.exceptions.NonexistentEntityException;
import JPAcontrollers.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author manie
 */
public class ProjectJpaController implements Serializable {

    public ProjectJpaController(UserTransaction utx) {
        this.utx = utx;
        emf=JpaUtil.getEntityMaganerFactory();
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Project project) throws IllegalOrphanException, RollbackFailureException, Exception {
        List<String> illegalOrphanMessages = null;
        Category category1OrphanCheck = project.getCategory1();
        if (category1OrphanCheck != null) {
            Project oldProjectOfCategory1 = category1OrphanCheck.getProject();
            if (oldProjectOfCategory1 != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Category " + category1OrphanCheck + " already has an item of type Project whose category1 column cannot be null. Please make another selection for the category1 field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            User projectOwner = project.getProjectOwner();
            if (projectOwner != null) {
                projectOwner = em.getReference(projectOwner.getClass(), projectOwner.getUsername());
                project.setProjectOwner(projectOwner);
            }
            Category category1 = project.getCategory1();
            if (category1 != null) {
                category1 = em.getReference(category1.getClass(), category1.getId());
                project.setCategory1(category1);
            }
            em.persist(project);
            if (projectOwner != null) {
                projectOwner.getProjectCollection().add(project);
                projectOwner = em.merge(projectOwner);
            }
            if (category1 != null) {
                category1.setProject(project);
                category1 = em.merge(category1);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Project project) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Project persistentProject = em.find(Project.class, project.getId());
            User projectOwnerOld = persistentProject.getProjectOwner();
            User projectOwnerNew = project.getProjectOwner();
            Category category1Old = persistentProject.getCategory1();
            Category category1New = project.getCategory1();
            List<String> illegalOrphanMessages = null;
            if (category1New != null && !category1New.equals(category1Old)) {
                Project oldProjectOfCategory1 = category1New.getProject();
                if (oldProjectOfCategory1 != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Category " + category1New + " already has an item of type Project whose category1 column cannot be null. Please make another selection for the category1 field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (projectOwnerNew != null) {
                projectOwnerNew = em.getReference(projectOwnerNew.getClass(), projectOwnerNew.getUsername());
                project.setProjectOwner(projectOwnerNew);
            }
            if (category1New != null) {
                category1New = em.getReference(category1New.getClass(), category1New.getId());
                project.setCategory1(category1New);
            }
            project = em.merge(project);
            if (projectOwnerOld != null && !projectOwnerOld.equals(projectOwnerNew)) {
                projectOwnerOld.getProjectCollection().remove(project);
                projectOwnerOld = em.merge(projectOwnerOld);
            }
            if (projectOwnerNew != null && !projectOwnerNew.equals(projectOwnerOld)) {
                projectOwnerNew.getProjectCollection().add(project);
                projectOwnerNew = em.merge(projectOwnerNew);
            }
            if (category1Old != null && !category1Old.equals(category1New)) {
                category1Old.setProject(null);
                category1Old = em.merge(category1Old);
            }
            if (category1New != null && !category1New.equals(category1Old)) {
                category1New.setProject(project);
                category1New = em.merge(category1New);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = project.getId();
                if (findProject(id) == null) {
                    throw new NonexistentEntityException("The project with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Project project;
            try {
                project = em.getReference(Project.class, id);
                project.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The project with id " + id + " no longer exists.", enfe);
            }
            User projectOwner = project.getProjectOwner();
            if (projectOwner != null) {
                projectOwner.getProjectCollection().remove(project);
                projectOwner = em.merge(projectOwner);
            }
            Category category1 = project.getCategory1();
            if (category1 != null) {
                category1.setProject(null);
                category1 = em.merge(category1);
            }
            em.remove(project);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Project> findProjectEntities() {
        return findProjectEntities(true, -1, -1);
    }

    public List<Project> findProjectEntities(int maxResults, int firstResult) {
        return findProjectEntities(false, maxResults, firstResult);
    }

    private List<Project> findProjectEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Project.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Project findProject(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Project.class, id);
        } finally {
            em.close();
        }
    }

    public int getProjectCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Project> rt = cq.from(Project.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
