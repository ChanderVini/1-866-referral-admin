/*
 * GenUserBO.java
 */

package com.cssc.spl.bo;

import com.cssc.spl.dao.GenUserDAO;
import com.cssc.spl.exception.CSSCApplicationException;
import com.cssc.spl.exception.CSSCSystemException;
import com.cssc.spl.vo.LocationVO;
import org.apache.log4j.Logger;

/**
 *
 * @author Chander Singh
 * Created on November 28, 2007, 3:05 PM
 */
public class GenUserBO {
    private Logger logger = null;
    
    /** Creates a new instance of GenUserBO */
    public GenUserBO() {
        logger = Logger.getLogger(this.getClass());
    }
    
    public void updateLocationVO (LocationVO locationVO, String userId) throws CSSCApplicationException, CSSCSystemException {
        logger.info ("Start updateLocationVO (LocationVO, String)");
        GenUserDAO generalistDAO = new GenUserDAO ();
        generalistDAO.updateLocationVO(locationVO, userId);
        logger.info ("Start updateLocationVO (LocationVO, String)");
    }
}
