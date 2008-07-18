package org.hyperic.hq.hqu.rendit.helpers

import java.util.List

import org.hyperic.hq.auth.shared.SessionManager
import org.hyperic.hq.bizapp.server.session.AuthzBossEJBImpl as AuthzBoss
import org.hyperic.hq.auth.server.session.AuthManagerEJBImpl as AuthMan
import org.hyperic.hq.authz.server.session.AuthzSubject
import org.hyperic.hq.authz.server.session.AuthzSubjectManagerEJBImpl as SubjectMan

/**
 * The UserHelper can be used to find Users in the HQ system.
 */
class UserHelper extends BaseHelper {
    private authzBoss = AuthzBoss.one
    private subjectMan = SubjectMan.one
    private authMan = AuthMan.one

    UserHelper(AuthzSubject user) {
        super(user)
    }

    /**
     * Find all users
     * @return a List of {@link AuthzSubject}s
     */
    public List getAllUsers() {
        subjectMan.getAllSubjects(user, [], null).collect {
            subjectMan.findSubjectById(it.id)
        }
    }

    /**
     * Find a user by name
     * @return a {@link AuthzSubject}
     */
    public findUser(String name) {
        subjectMan.findSubjectByName(name)
    }

    /**
     * Get a user by id.
     * @return The {@link AuthzSubject} for this id, or null if the id does
     * not exist.
     */
    public getUser(Integer id) {
        subjectMan.getSubjectById(id)
    }

    /**
     * Create a user
     * @return a {@link AuthzSubject}s
     */
    public createUser(String userName, boolean active, String dsn,
                      String dept, String email, String first,
                      String last, String phone, String sms,
                      boolean html) {
        subjectMan.createSubject(user, userName, active, dsn, dept, email,
                                 first, last, phone, sms, html)
    }

    /**
     * Create a user with the given password.
     * @return a {@link AuthzSubject}s
     */
    public createUser(String userName, String pass, boolean active,
                      String dsn, String dept, String email,
                      String first, String last, String phone,
                      String sms, boolean html) {
        def user = subjectMan.createSubject(user, userName, active, dsn,
                                            dept, email, first, last, phone,
                                            sms, html)
        authMan.addUser(user, userName, pass)
        user
    }

    /**
     * Update a user
     * @param found The {@link AuthzSubject} to update.
     */
    public void updateUser(found, boolean active, String dsn,
                           String dept, String email, String first,
                           String last, String phone, String sms, boolean html) {
        subjectMan.updateSubject(user, found, active, dsn, dept, email, first,
                                 last, phone, sms, html)
    }
     
    /**
     * Update a user's password hash.
     */
    public void updateUserPassword(String subject, String hash) {
        authMan.changePasswordHash(user, subject.name, hash)
    }

    /**
     * Change the password for a user
     * @param subject The {@link AuthzSubject} to change the password for
     */
    public void changeUserPassword(subject, String password) {
        authMan.changePassword(user, subject.name, password)
    }

    /**
     * Remove a user from database
     */
    public void removeUser(Integer id) {
        int sessionId = SessionManager.instance.put(user)
        authzBoss.removeSubject(sessionId, [id] as Integer[])
    }
 }
