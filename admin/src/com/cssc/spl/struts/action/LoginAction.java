/*
 * LoginAction.java
 */
package com.cssc.spl.struts.action;

import com.cssc.spl.bo.UserBO;
import com.cssc.spl.exception.CSSCApplicationException;
import com.cssc.spl.exception.CSSCSystemException;
import com.cssc.spl.struts.form.LoginForm;
import com.cssc.spl.util.Validator;
import com.cssc.spl.vo.GenUserVO;
import com.cssc.spl.vo.UserVO;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

/**
 *
 * @author Chander Singh
 * Created on October 15, 2007, 5:03 PM
 */
public class LoginAction extends Action {
    //Constants for defining Errors, Messages and warnings.
    private final String ERRORS = "errors";
    
    //Constants defined for forward mapping results
    private final String SUCCESS = "success";
    private final String ERROR = "error";
    
    private Logger logger = null;
    
    private final String CSSC001E = "errors.CSSC001E";   
    
    private final String LOGIN_ADMIN_PATH = "/admlogin";
    private final String LOGIN_GEN_PATH = "/userlogin";
    private final String INDEX_PATH = "/index";
    private final String LOGIN_SPEC_PATH = "/spclogin";

    private final String ADMIN_PATH = "/admin";
    private final String GEN_PATH = "/user";
    private final String SPEC_PATH = "/spec";
    
    private final String GENERALIST= "GEN";
        
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger = Logger.getLogger (LoginAction.class);
        logger.info ("Start execute (ActionMapping, ActionForm, HttpServletrequest, HttpServletResponse)");
        ActionForward forward = null;
        LoginForm loginForm = (LoginForm) form;
        ActionMessages messages = new ActionMessages ();
        ActionErrors errors = new ActionErrors ();
        HttpSession session = request.getSession();
        try {
            String path = mapping.getPath();
            logger.debug ("Path requested: " + path);
            if (ADMIN_PATH.equals (path)) {
                loginForm.setUserType("ADMIN");
                forward = mapping.findForward(SUCCESS);
            }        
            if (GEN_PATH.equals(path)) {
                loginForm.setUserType("SPEC");
                forward = mapping.findForward(SUCCESS);
            }
            if (INDEX_PATH.equals (path)) {
                forward = mapping.findForward(SUCCESS);
            }
            if (SPEC_PATH.equals(path)) {
                loginForm.setUserType("SPEC");
                forward = mapping.findForward(SUCCESS);
            }
            if (LOGIN_ADMIN_PATH.equals (path) || LOGIN_SPEC_PATH.equals(path)) {
                errors = loginForm.validate(mapping, request);
                if (errors.isEmpty()) {
                    String userName = loginForm.getUserid();
                    String password = loginForm.getPassword();
                    String userType = loginForm.getUserType ();

                    UserVO userVO = new UserVO ();
                    userVO.setUsername(userName);
                    userVO.setPassword(password);
                    userVO.setUserType(userType);

                    UserBO userBO = new UserBO ();
                    userVO = userBO.authenticateUser(userVO);

                    if (Validator.isBlank(userVO.getFirstName())) {
                        messages.add(ERRORS, new ActionMessage (CSSC001E, "User Id/Password"));
                        forward = mapping.findForward(ERROR);
                    } else {
                        session.setAttribute(userVO.getUserType(), userVO);
                        forward = mapping.findForward(SUCCESS);
                    }
                }
            }
            if (LOGIN_GEN_PATH.equals(path)) {
                errors = loginForm.validate(mapping, request);
                logger.debug ("Errors: " + errors.isEmpty());
                if (errors.isEmpty()) {           
                    messages = handleGeneralistLogin (loginForm, request);
                    if (!messages.isEmpty()) {
                        forward = mapping.findForward(ERROR);
                    } else {
                        forward = mapping.findForward(SUCCESS);
                    }                
                }
            }
        } catch (CSSCSystemException csscsexp) {
            messages.add(ERRORS, new ActionMessage (csscsexp.getErrorCode()));
            forward = mapping.findForward(ERROR);
        } catch (CSSCApplicationException csscaexp) {
            messages.add(ERRORS, new ActionMessage (csscaexp.getErrorCode()));
            forward = mapping.findForward(ERROR);
        }
        if (!errors.isEmpty()) {
            Iterator errorIter = errors.get(ERRORS + ".label.userid");
            while (errorIter.hasNext()) {
                messages.add(ERRORS + ".label.userid", (ActionMessage) errorIter.next());
            }
            errorIter = errors.get(ERRORS + ".label.password");
            while (errorIter.hasNext()) {
                messages.add(ERRORS + ".label.password", (ActionMessage) errorIter.next());
            }
            errorIter = errors.get(ERRORS + ".label.location");
            while (errorIter.hasNext()) {
                messages.add(ERRORS + ".label.location", (ActionMessage) errorIter.next());
            }
            errorIter = errors.get(ERRORS + ".label.locpassword");
            while (errorIter.hasNext()) {
                messages.add(ERRORS + ".label.locpassword", (ActionMessage) errorIter.next());
            }
            errorIter = errors.get(ERRORS + ".label.specialistid");
            while (errorIter.hasNext()) {
                messages.add(ERRORS + ".label.specialistid", (ActionMessage) errorIter.next());
            }            
            forward = mapping.findForward (ERROR);
        }
        saveMessages(request, messages);
        logger.info ("End execute (ActionMapping, ActionForm, HttpServletrequest, HttpServletResponse)");
        return forward;
    } 
    
    private ActionMessages handleGeneralistLogin (LoginForm loginForm, HttpServletRequest request) throws CSSCApplicationException, CSSCSystemException {
        logger.info ("Start handleGeneralistLogin (LoginForm, HttpServletRequest)");
        ActionMessages messages = new ActionMessages ();
        
        String userName = loginForm.getUserid();
        String locationPwd = loginForm.getLocpassword();
        
        UserBO userBO = new UserBO ();
        GenUserVO generalistVO = userBO.authenticateGeneralist (locationPwd, userName);
        if (Validator.isBlank(generalistVO.getGeneralistid())) {
            messages.add(ERRORS, new ActionMessage (CSSC001E, "User Id/Download Password"));
        } else {
            HttpSession session = request.getSession();
            session.setAttribute(GENERALIST, generalistVO);
        }
        logger.info ("Start handleGeneralistLogin (LoginForm, HttpServletRequest)");
        return messages;
    }
}
