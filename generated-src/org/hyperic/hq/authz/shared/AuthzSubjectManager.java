/*
 * Generated by XDoclet - Do not edit!
 */
package org.hyperic.hq.authz.shared;

import javax.ejb.CreateException;
import javax.ejb.FinderException;
import javax.ejb.RemoveException;

import org.hyperic.hq.auth.shared.SubjectNotFoundException;
import org.hyperic.hq.authz.server.session.AuthzSubject;
import org.hyperic.util.config.ConfigResponse;
import org.hyperic.util.pager.PageControl;
import org.hyperic.util.pager.PageList;

/**
 * Local interface for AuthzSubjectManager.
 */
public interface AuthzSubjectManager
{
   /**
    * Create a subject.
    * @param whoami The current running user.
    * @return Value-object for the new Subject.
    */
   public AuthzSubject createSubject( AuthzSubject whoami,String name,boolean active,String dsn,String dept,String email,String first,String last,String phone,String sms,boolean html ) throws PermissionException, CreateException;

   /**
    * Update user settings for the target
    * @param whoami The current running user.
    * @param target The subject to save. The rest of the parameters specify settings to update. If they are null, then no change will be made to them.
    */
   public void updateSubject( AuthzSubject whoami,AuthzSubject target,Boolean active,String dsn,String dept,String email,String firstName,String lastName,String phone,String sms,Boolean useHtml ) throws PermissionException;

   /**
    * Check if a subject can modify users
    */
   public void checkModifyUsers( AuthzSubject caller ) throws PermissionException;

   /**
    * Delete the specified subject.
    * @param whoami The current running user.
    * @param subject The ID of the subject to delete.
    */
   public void removeSubject( AuthzSubject whoami,Integer subject ) throws RemoveException, PermissionException;

   public AuthzSubject findByAuth( String name,String authDsn ) ;

   public AuthzSubject findSubjectById( AuthzSubject whoami,Integer id ) throws PermissionException;

   public AuthzSubject findSubjectById( Integer id ) ;

   public AuthzSubject getSubjectById( Integer id ) ;

   public AuthzSubject findSubjectByName( AuthzSubject whoami,String name ) throws PermissionException;

   public AuthzSubject findSubjectByName( String name ) ;

   public PageList<AuthzSubject> findMatchingName( String name,PageControl pc ) ;

   /**
    * List all subjects in the system
    * @param excludes the IDs of subjects to exclude from result    */
   public PageList<AuthzSubjectValue> getAllSubjects( AuthzSubject whoami,java.util.Collection<Integer> excludes,PageControl pc ) throws FinderException, PermissionException;

   /**
    * Get the subjects with the specified ids NOTE: This method returns an empty PageList if a null or empty array of ids is received.
    * @param ids the subject ids
    */
   public PageList<AuthzSubjectValue> getSubjectsById( AuthzSubject subject,java.lang.Integer[] ids,PageControl pc ) throws PermissionException;

   /**
    * Find the e-mail of the subject specified by id
    * @param id id of the subject.
    * @return The e-mail address of the subject
    */
   public String getEmailById( Integer id ) ;

   /**
    * Find the e-mail of the subject specified by name
    * @param userName Name of the subjects.
    * @return The e-mail address of the subject
    */
   public String getEmailByName( String userName ) ;

   /**
    * Get the Preferences for a specified user
    */
   public ConfigResponse getUserPrefs( AuthzSubject who,Integer subjId ) throws PermissionException;

   /**
    * Set the Preferences for a specified user
    */
   public void setUserPrefs( AuthzSubject who,Integer subjId,ConfigResponse prefs ) throws PermissionException;

   public AuthzSubject getOverlordPojo(  ) ;

   /**
    * Find the subject that has the given name and authentication source.
    * @param name Name of the subject.
    * @param authDsn DSN of the authentication source. Authentication sources are defined externally.
    * @return The value-object of the subject of the given name and authenticating source.
    */
   public AuthzSubject findSubjectByAuth( String name,String authDsn ) throws SubjectNotFoundException;

}
