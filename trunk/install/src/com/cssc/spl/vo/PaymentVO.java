/*
 * PaymentVO.java
 */

package com.cssc.spl.vo;

import com.cssc.spl.vo.common.CommonVO;
import java.io.Serializable;
import java.sql.Date;

/**
 *
 * @author Chander Singh
 * Created on November 20, 2007, 4:11 PM
 */
public class PaymentVO extends CommonVO implements Serializable {
    private long transactionId = -1;
    private String paymentType = "";
    private String refNbr = "";
    private String invoiceNbr = "";
    private String locationName = "";
    private String userId = "";
    private double amount = 0;
    
    private int responseCode = -1;
    private int approvalCode = -1;
    private String responseTxt = "";
    private String avsCode = "";
    private String paymentDateStr = "";
    private Date paymentDate = null;
    
    private String ccType = "";
    
    private int expMon = -1;
    private int expYear = -1;
    private int secCode = -1;
    
    public long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(long transactionId) {
        this.transactionId = transactionId;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public int getApprovalCode() {
        return approvalCode;
    }

    public void setApprovalCode(int approvalCode) {
        this.approvalCode = approvalCode;
    }

    public String getResponseTxt() {
        return responseTxt;
    }

    public void setResponseTxt(String responseTxt) {
        this.responseTxt = responseTxt;
    }

    public String getAvsCode() {
        return avsCode;
    }

    public void setAvsCode(String avsCode) {
        this.avsCode = avsCode;
    }

    public String getInvoiceNbr() {
        return invoiceNbr;
    }

    public void setInvoiceNbr(String invoiceNbr) {
        this.invoiceNbr = invoiceNbr;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getRefNbr() {
        return refNbr;
    }

    public void setRefNbr(String refNbr) {
        this.refNbr = refNbr;
    }

    public String getPaymentDateStr() {
        return paymentDateStr;
    }

    public void setPaymentDateStr(String paymentDateStr) {
        this.paymentDateStr = paymentDateStr;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public int getExpMon() {
        return expMon;
    }

    public void setExpMon(int expMon) {
        this.expMon = expMon;
    }

    public int getExpYear() {
        return expYear;
    }

    public void setExpYear(int expYear) {
        this.expYear = expYear;
    }

    public int getSecCode() {
        return secCode;
    }

    public void setSecCode(int secCode) {
        this.secCode = secCode;
    }

    public String getCcType() {
        return ccType;
    }

    public void setCcType(String ccType) {
        this.ccType = ccType;
    }
}
