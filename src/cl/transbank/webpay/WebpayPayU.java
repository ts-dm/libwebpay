/**
  * @brief      Ecommerce Plugin for chilean Webpay
  * @category   Plugins/SDK
  * @author     Allware Ltda. (http://www.allware.cl)
  * @copyright  2015 Transbank S.A. (http://www.tranbank.cl)
  * @date       Jan 2016
  * @license    GNU LGPL
  * @version    2.0.1
  * @link       http://transbankdevelopers.cl/
  *
  * This software was created for easy integration of ecommerce
  * portals with Transbank Webpay solution.
  *
  * Required:
  *  - Java Runtime 7
  *
  * See documentation and how to install at link site
  *
  */

package cl.transbank.webpay;

import cl.transbank.webpay.security.SoapSignature;
import cl.transbank.webpay.security.WebpayCertificateHelper;

/**
 *
 * @author rbertuzzi
 */
public class WebpayPayU {
    
    private static final long DEFAULT_CONNECTION_TIMEOUT = 30000;
    private static final long DEFAUL_READ_TIMEOUT = 30000;
    private static final String COMPLETE_MODE_FRAGMENT = "WSCompleteWebpayService/complete?wsdl";
    private static final String NULLIFY_MODE_FRAGMENT = "WSCommerceIntegrationService/nullify?wsdl";
    private static final String NORMAL_MODE_FRAGMENT = "WSWebpayService/normal?wsdl";

    
    SoapSignature signature;
    String url;
    String commerceCode;
    long connectionTimeout; 
    long readTimeout;
    
    WebpayNormal normalTransaction;
    WebpayOneClick oneClickTransaction;
    WebpayMallNormal mallNormalTransaction;
    WebpayComplete completeTransaction;
    WebpayCapture captureTransaction;
    WebpayNullify nullifyTransaction;
    
    public WebpayPayU(String url, String commerceCode, SoapSignature signature){
        this(url, commerceCode, DEFAULT_CONNECTION_TIMEOUT, DEFAUL_READ_TIMEOUT);
        setSignature(signature);
    }
    
    public WebpayPayU(String url, String commerceCode, long connectionTimeout, long readTimeout){
        this.url = url;
        this.commerceCode = commerceCode;
        this.connectionTimeout = connectionTimeout;
        this.readTimeout = readTimeout;
    }
    
    public void setSignature(SoapSignature signature){
        this.signature = signature;
        WebpayCertificateHelper.checkCertificate(Webpay.Environment.INTEGRACION, signature);
    }
    
    public synchronized WebpayNormal getNormalTransaction() throws Exception {
        if (normalTransaction == null) {
            normalTransaction = new WebpayNormal(url + NORMAL_MODE_FRAGMENT, commerceCode,
                    signature, connectionTimeout, readTimeout);
        }
        return normalTransaction;
    }
//    
//    public synchronized WebpayOneClick getOneClickTransaction() throws Exception {
//        if (oneClickTransaction == null){
//            oneClickTransaction = new WebpayOneClick(mode, commerceCode, signature);
//        }
//        return oneClickTransaction;
//    }
//    
//    public synchronized WebpayMallNormal getMallNormalTransaction() throws Exception {
//        if (mallNormalTransaction == null){
//            mallNormalTransaction = new WebpayMallNormal(mode, commerceCode, signature);
//        }
//        return mallNormalTransaction;
//    }
    
    public synchronized WebpayComplete getCompleteTransaction() throws Exception {
        if (completeTransaction == null){
            completeTransaction = new WebpayComplete(url + COMPLETE_MODE_FRAGMENT, commerceCode, 
                    signature, connectionTimeout, readTimeout);
        }
        return completeTransaction;
    }
    
//    public synchronized WebpayCapture getCaptureTransaction() throws Exception {
//        if (captureTransaction == null){
//            captureTransaction = new WebpayCapture(mode, commerceCode, signature);
//        }
//        return captureTransaction;
//    }
//    
    public synchronized WebpayNullify getNullifyTransaction() throws Exception {
        if (nullifyTransaction == null){
            nullifyTransaction = new WebpayNullify(url + NULLIFY_MODE_FRAGMENT, commerceCode, signature);
        }
        return nullifyTransaction;
    }
    
}
