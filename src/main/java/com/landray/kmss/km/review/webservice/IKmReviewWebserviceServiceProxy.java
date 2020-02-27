package com.landray.kmss.km.review.webservice;

public class IKmReviewWebserviceServiceProxy implements com.landray.kmss.km.review.webservice.IKmReviewWebserviceService {
  private String _endpoint = null;
  private com.landray.kmss.km.review.webservice.IKmReviewWebserviceService iKmReviewWebserviceService = null;
  
  public IKmReviewWebserviceServiceProxy() {
    _initIKmReviewWebserviceServiceProxy();
  }
  
  public IKmReviewWebserviceServiceProxy(String endpoint) {
    _endpoint = endpoint;
    _initIKmReviewWebserviceServiceProxy();
  }
  
  private void _initIKmReviewWebserviceServiceProxy() {
    try {
      iKmReviewWebserviceService = (new com.landray.kmss.km.review.webservice.IKmReviewWebserviceServiceServiceLocator()).getIKmReviewWebserviceServicePort();
      if (iKmReviewWebserviceService != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)iKmReviewWebserviceService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)iKmReviewWebserviceService)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (iKmReviewWebserviceService != null)
      ((javax.xml.rpc.Stub)iKmReviewWebserviceService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public com.landray.kmss.km.review.webservice.IKmReviewWebserviceService getIKmReviewWebserviceService() {
    if (iKmReviewWebserviceService == null)
      _initIKmReviewWebserviceServiceProxy();
    return iKmReviewWebserviceService;
  }
  
  public java.lang.String addReview(com.landray.kmss.km.review.webservice.KmReviewParamterForm arg0) throws java.rmi.RemoteException, com.landray.kmss.km.review.webservice.Exception{
    if (iKmReviewWebserviceService == null)
      _initIKmReviewWebserviceServiceProxy();
    return iKmReviewWebserviceService.addReview(arg0);
  }
  
  
}