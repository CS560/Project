
/**
 * CPSCUpcSvcCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */

    package org.tempuri;

    /**
     *  CPSCUpcSvcCallbackHandler Callback class, Users can extend this class and implement
     *  their own receiveResult and receiveError methods.
     */
    public abstract class CPSCUpcSvcCallbackHandler{



    protected Object clientData;

    /**
    * User can pass in any object that needs to be accessed once the NonBlocking
    * Web service call is finished and appropriate method of this CallBack is called.
    * @param clientData Object mechanism by which the user can pass in user data
    * that will be avilable at the time this callback is called.
    */
    public CPSCUpcSvcCallbackHandler(Object clientData){
        this.clientData = clientData;
    }

    /**
    * Please use this constructor if you don't want to set any clientData
    */
    public CPSCUpcSvcCallbackHandler(){
        this.clientData = null;
    }

    /**
     * Get the client data
     */

     public Object getClientData() {
        return clientData;
     }

        
           /**
            * auto generated Axis2 call back method for getRecallByDate method
            * override this method for handling normal response from getRecallByDate operation
            */
           public void receiveResultgetRecallByDate(
                    org.tempuri.CPSCUpcSvcStub.GetRecallByDateResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getRecallByDate operation
           */
            public void receiveErrorgetRecallByDate(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getRecallByUPC method
            * override this method for handling normal response from getRecallByUPC operation
            */
           public void receiveResultgetRecallByUPC(
                    org.tempuri.CPSCUpcSvcStub.GetRecallByUPCResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getRecallByUPC operation
           */
            public void receiveErrorgetRecallByUPC(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for reportIncident method
            * override this method for handling normal response from reportIncident operation
            */
           public void receiveResultreportIncident(
                    org.tempuri.CPSCUpcSvcStub.ReportIncidentResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from reportIncident operation
           */
            public void receiveErrorreportIncident(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getRecallByWord method
            * override this method for handling normal response from getRecallByWord operation
            */
           public void receiveResultgetRecallByWord(
                    org.tempuri.CPSCUpcSvcStub.GetRecallByWordResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getRecallByWord operation
           */
            public void receiveErrorgetRecallByWord(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getEmailSubscription method
            * override this method for handling normal response from getEmailSubscription operation
            */
           public void receiveResultgetEmailSubscription(
                    org.tempuri.CPSCUpcSvcStub.GetEmailSubscriptionResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getEmailSubscription operation
           */
            public void receiveErrorgetEmailSubscription(java.lang.Exception e) {
            }
                


    }
    