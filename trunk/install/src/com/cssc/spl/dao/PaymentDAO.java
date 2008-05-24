/*
 * PaymentDAO.java
 */

package com.cssc.spl.dao;

import com.cssc.spl.exception.CSSCApplicationException;
import com.cssc.spl.exception.CSSCSystemException;
import com.cssc.spl.exception.DbConnectionException;
import com.cssc.spl.util.DbConnection;
import com.cssc.spl.vo.PaymentVO;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.log4j.Logger;

/**
 *
 * @author Chander Singh
 * Created on November 20, 2007, 4:25 PM
 */
public class PaymentDAO {
    private Logger logger = null;
    private final String CSSC004E = "errors.CSSC004E";

    /** Creates a new instance of PaymentDAO */
    public PaymentDAO() {
        logger = Logger.getLogger(this.getClass());
    }

    /**
     *
     * @param paymentVOs
     * @param userId
     * @throws com.cssc.spl.exception.CSSCApplicationException
     * @throws com.cssc.spl.exception.CSSCSystemException
     */
    public void savePaymentDetails (PaymentVO[] paymentVOs, String userId) throws CSSCApplicationException, CSSCSystemException {
        logger.info ("Start savePaymentDetails (PaymentVO[])");
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            StringBuffer queryBuf = new StringBuffer ("INSERT INTO payment_tbl (TRANSACTION_ID, PAYMENT_TYPE, REF_NBR, RESPONSE_CODE, APPROVAL_CODE, " +
                    "RESPONSE_REASON_TXT, AVS_CODE, INVOICE_NBR, AMOUNT, LOCATION_NAME, USER_ID, PAYMENT_DT, CREATE_DT, CREATE_USER_ID_CD, " +
                    "LAST_UPDATE_USER_ID_CD, LAST_UPDATE_DT) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP, ?, ?, CURRENT_TIMESTAMP)");

            logger.debug ("Query: " + queryBuf.toString ());
            connection = DbConnection.getConnection();
            connection.setAutoCommit(false);
            ps = connection.prepareStatement(queryBuf.toString());

            for (int cnt = 0; cnt < paymentVOs.length; cnt++) {
                long transactionid = paymentVOs[cnt].getTransactionId();
                String paymentType = paymentVOs[cnt].getPaymentType();
                String refNbr = paymentVOs[cnt].getRefNbr();
                int responseCode = paymentVOs[cnt].getResponseCode();
                int approvalCode = paymentVOs[cnt].getApprovalCode();
                String responseTxt = paymentVOs[cnt].getResponseTxt();
                String avsCode = paymentVOs[cnt].getAvsCode();
                String invoiceNbr = paymentVOs[cnt].getInvoiceNbr();
                double amount = paymentVOs[cnt].getAmount();
                String locationName = paymentVOs[cnt].getLocationName();
                String userid = paymentVOs[cnt].getUserId();
                Date paymentDate = paymentVOs[cnt].getPaymentDate();

                ps.setLong (1, transactionid);
                ps.setString (2, paymentType);
                ps.setString (3, refNbr);
                ps.setInt (4, responseCode);
                ps.setInt (5, approvalCode);
                ps.setString (6, responseTxt);
                ps.setString (7, avsCode);
                ps.setString (8, invoiceNbr);
                ps.setDouble(9, amount);
                ps.setString (10, locationName);
                ps.setString (11, userid);
                ps.setDate (12, paymentDate);
                ps.setString(13, userId);
                ps.setString(14, userId);
                ps.addBatch();
            }
            ps.executeBatch();

            connection.commit();
            DbConnection.close(null, ps, null);
            
            if ("CC".equals (paymentVOs[0].getPaymentType())) {
                saveCreditCard(connection, paymentVOs[0]);
            }
            DbConnection.close(connection, ps, null);
            logger.info ("End savePaymentDetails (PaymentVO[])");
        } catch (DbConnectionException dbexp) {
            logger.error("Error Occured while extracting Database Conenction", dbexp);
            throw new CSSCSystemException (CSSC004E, "");
        } catch (SQLException sqlexp) {
             try {
                connection.rollback();
            } catch (SQLException sqlExp) {}
            logger.error ("Error Occured whle commiting/rollbacking operation: " + sqlexp.getMessage());
            throw new CSSCSystemException (CSSC004E, "");
        } catch (CSSCSystemException csscsexp) {
            try {
                connection.rollback();
            } catch (SQLException sqlExp) {}
            throw new CSSCSystemException (csscsexp.getErrorCode(), "");
        } finally {
            DbConnection.close(connection, null, null);
        }
    }

    /**
     * 
     * @param connection
     * @param paymentVO
     * @throws com.cssc.spl.exception.CSSCSystemException
     */
    public void saveCreditCard (Connection connection, PaymentVO paymentVO) throws CSSCSystemException {
        logger.info ("Start saveCreditCard (Connection, PaymentVO)");
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            StringBuffer queryBuf = new StringBuffer ("SELECT COUNT(1) FROM cc_tbl ");
            queryBuf.append ("WHERE CC_NBR = ? AND USER_ID = ?");
            ps = connection.prepareStatement (queryBuf.toString ());
            
            ps.setString (1, paymentVO.getRefNbr());
            ps.setString (2, paymentVO.getUserId());
            
            rs = ps.executeQuery();
            boolean insert = true;
            if (rs.next ()) {
                if (rs.getInt(1) > 0) {
                    insert = false;
                }
            }
            DbConnection.close(null, ps, rs);
            if (insert) {
                queryBuf = new StringBuffer ("INSERT INTO cc_tbl ");
                queryBuf.append("(CC_NBR, SEC_CODE, EXPIRY_MON, EXPIRY_YEAR, CC_TYPE, CC_OWNER_NAME, USER_ID) ");
                queryBuf.append ("VALUES (?, ?, ?, ?, ?, ?, ?)");

                ps = connection.prepareStatement (queryBuf.toString ());
                queryBuf = null;
                ps.setString (1, paymentVO.getRefNbr());
                ps.setInt (2, paymentVO.getSecCode());
                ps.setInt (3, paymentVO.getExpMon());
                ps.setInt (4, paymentVO.getExpYear());
                ps.setString (5, paymentVO.getCcType());
                ps.setString (6, paymentVO.getUserId());
                ps.setString (7, paymentVO.getUserId ());
                ps.executeUpdate();

                connection.commit();
                DbConnection.close (null, ps, null);
            }
            logger.info ("Start saveCreditCard (Connection, PaymentVO)");
        } catch (SQLException sqlexp) {
             try {
                connection.rollback();
            } catch (SQLException sqlExp) {}
            logger.error ("Error Occured whle commiting/rollbacking operation: " + sqlexp.getMessage());
            throw new CSSCSystemException (CSSC004E, "");
        }
    }
}
