/*
 * RegisterAction.java
 */

package com.cssc.spl.struts.action;

import com.cssc.spl.bo.UserBO;
import com.cssc.spl.exception.CSSCApplicationException;
import com.cssc.spl.exception.CSSCSystemException;
import com.cssc.spl.struts.form.RegisterForm;
import com.cssc.spl.util.CSSCUtil;
import com.cssc.spl.util.Constants;
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
import org.apache.struts.util.LabelValueBean;

/**
 *
 * @author Chander Singh
 * Created on November 6, 2007, 10:50 AM
 */
public class RegisterAction extends Action {
    //Constants for defining Errors, Messages and warnings.
    private final String ERRORS = "errors";
        
    //Constants defined for forward mapping results
    private final String SUCCESS = "success";
    private final String ERROR = "error";
    
    private Logger logger = null;
        
    private final String STATE_DATA = "STATE_DATA";
    private final String COUNTRY_DATA = "COUNTRY_DATA";
    
    private final String GENERALIST= "GEN";
    private final String SPECIALIST = "SPEC";
    private final String SYSTEM = "SYSTEM";
    
    private final String GEN_REGISTER_MAP = "/userreg";
    private final String GEN_REGISTER_SUBMIT_MAP = "/userregsub";   
    
    /**
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws java.lang.Exception
     */
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger = Logger.getLogger (this.getClass());
        logger.info ("Start execute (Actionmapping, ActionForm, HttpServletRequest, HttpServletResponse)");
        String path = mapping.getPath();
        ActionForward forward = null;
        RegisterForm registerForm = (RegisterForm) form;
        ActionMessages messages = new ActionMessages ();
        try {
            logger.debug ("Path requested: " + path);
            if (GEN_REGISTER_MAP.equals (path)) {
                logger.debug("In Register Map");
                messages = handleListGenRegister (registerForm, request);
                forward = mapping.findForward(SUCCESS);
            }
           
            if (GEN_REGISTER_SUBMIT_MAP.equals (path)) {
                ActionErrors errors = registerForm.validate(mapping, request);
                if (errors.isEmpty()) {
                    messages = handleGenRegister (registerForm, request);
                    forward = mapping.findForward (SUCCESS);
                } else {
                    extractErrors (messages, errors);
                    Iterator errorIter = errors.get(ERRORS+".label.location");
                    while (errorIter.hasNext()) {
                        ActionMessage message = (ActionMessage)errorIter.next();
                        messages.add (ERRORS+".label.location", message);
                    }
                    errorIter = errors.get(ERRORS+".label.locpwd");
                    while (errorIter.hasNext()) {
                        ActionMessage message = (ActionMessage)errorIter.next();
                        messages.add (ERRORS+".label.locpwd", message);
                    }
                    errorIter = errors.get(ERRORS+".label.specialist");
                    while (errorIter.hasNext()) {
                        ActionMessage message = (ActionMessage)errorIter.next();
                        messages.add (ERRORS+".label.specialist", message);
                    }
                    forward = mapping.findForward(ERROR);        
                }
            }
            
        } catch (CSSCSystemException csscsexp) {
            messages.add(ERRORS, new ActionMessage (csscsexp.getErrorCode()));
            forward = mapping.findForward(ERROR);
        } catch (CSSCApplicationException csscaexp) {
            messages.add(ERRORS, new ActionMessage (csscaexp.getErrorCode()));
            forward = mapping.findForward(ERROR);
        }
        saveMessages(request, messages);
        logger.debug("Forward Request: " + forward.getPath());
        return forward;
    }
    
    /**
     * 
     * @param messages
     * @param errors
     */
    private void extractErrors (ActionMessages messages, ActionErrors errors) {
        Iterator errorIter = errors.get(ERRORS+".label.fname");
        while (errorIter.hasNext()) {
            ActionMessage message = (ActionMessage)errorIter.next();
            messages.add (ERRORS+".label.fname", message);
        }        
        errorIter = errors.get(ERRORS+".label.company");
        while (errorIter.hasNext()) {
            ActionMessage message = (ActionMessage)errorIter.next();
            messages.add (ERRORS+".label.company", message);
        }
        errorIter = errors.get(ERRORS+".label.lname");
        while (errorIter.hasNext()) {
            ActionMessage message = (ActionMessage)errorIter.next();
            messages.add (ERRORS+".label.lname", message);
        }
        errorIter = errors.get(ERRORS+".label.address1");
        while (errorIter.hasNext()) {
            ActionMessage message = (ActionMessage)errorIter.next();
            messages.add (ERRORS+".label.address1", message);
        }
        errorIter = errors.get(ERRORS+".label.city");
        while (errorIter.hasNext()) {
            ActionMessage message = (ActionMessage)errorIter.next();
            messages.add (ERRORS+".label.city", message);
        }
        errorIter = errors.get(ERRORS+".label.state");
        while (errorIter.hasNext()) {
            ActionMessage message = (ActionMessage)errorIter.next();
            messages.add (ERRORS+".label.state", message);
        }
        errorIter = errors.get(ERRORS+".label.country");
        while (errorIter.hasNext()) {
            Object obj = errorIter.next();
            logger.debug (obj);
            ActionMessage message = (ActionMessage)obj;
            messages.add (ERRORS+".label.country", message);
        }
        errorIter = errors.get(ERRORS+".label.zipcode");
        while (errorIter.hasNext()) {
            ActionMessage message = (ActionMessage)errorIter.next();
            messages.add (ERRORS+".label.zipcode", message);
        }
        errorIter = errors.get(ERRORS+".label.telephone");
        while (errorIter.hasNext()) {
            ActionMessage message = (ActionMessage)errorIter.next();
            messages.add (ERRORS+".label.telephone", message);
        }
        errorIter = errors.get(ERRORS+".label.fax");
        while (errorIter.hasNext()) {
            ActionMessage message = (ActionMessage)errorIter.next();
            messages.add (ERRORS+".label.fax", message);
        }
        errorIter = errors.get(ERRORS+".label.email");
        while (errorIter.hasNext()) {
            ActionMessage message = (ActionMessage)errorIter.next();
            messages.add (ERRORS+".label.email", message);
        }
        errorIter = errors.get(ERRORS+".label.password");
        while (errorIter.hasNext()) {
            ActionMessage message = (ActionMessage)errorIter.next();
            messages.add (ERRORS+".label.password", message);
        }
        errorIter = errors.get(ERRORS+".label.repassword");
        while (errorIter.hasNext()) {
            ActionMessage message = (ActionMessage)errorIter.next();
            messages.add (ERRORS+".label.repassword", message);
        }
    }
    
    /**
     * 
     * @param registerForm
     * @param request
     * @return
     * @throws com.cssc.spl.exception.CSSCApplicationException
     * @throws com.cssc.spl.exception.CSSCSystemException
     */
    private ActionMessages handleListGenRegister (RegisterForm registerForm, HttpServletRequest request) throws CSSCApplicationException, CSSCSystemException {
        logger.info ("Start handleListGenRegister (RegisterForm, HttpServletRequest)");
        ActionMessages messages = new ActionMessages ();
        String stateData = Constants.getProperty(STATE_DATA);
        LabelValueBean[] stateBeans = CSSCUtil.getLabelValueBeans(stateData, ":", ";");
        registerForm.setStateBeans (stateBeans);
                
        String countryData = Constants.getProperty(COUNTRY_DATA);
        LabelValueBean[] countryBeans = CSSCUtil.getLabelValueBeans(countryData, ":", ";");
        registerForm.setCountryBeans (countryBeans);
        
        logger.info ("End handleListGenRegister (RegisterForm, HttpServletRequest)");
        return messages;
    }
    
    /**
     * 
     * @param registerForm
     * @param request
     * @return
     * @throws com.cssc.spl.exception.CSSCApplicationException
     * @throws com.cssc.spl.exception.CSSCSystemException
     */
     private ActionMessages handleGenRegister (RegisterForm registerForm, HttpServletRequest request) throws CSSCApplicationException, CSSCSystemException {
        logger.info ("Start handleGenRegister (RegisterForm, HttpServletRequest)");
        ActionMessages messages = new ActionMessages ();
        UserVO userVO = registerForm.getUserVO();
        userVO.setUserType(SPECIALIST);
        UserBO userBO = new UserBO ();
        GenUserVO generalistVO = userBO.createGeneralist (userVO, SYSTEM);                
        HttpSession session = request.getSession();
        session.setAttribute(GENERALIST, generalistVO);
        logger.info ("End handleGenRegister (RegisterForm, HttpServletRequest)");
        return messages;
    }
}
