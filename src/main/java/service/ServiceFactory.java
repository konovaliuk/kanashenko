/**
 * ServiceFactory.java
 *
 * @version 1.0
 *
 * @date Sep 2, 2018
 *
 * Copyright by Mykyta Kanashchenko
 */
package service;

import persistence.daoImpl.MySQLDaoFactory;

public class ServiceFactory {
	private static ServiceFactory uniqueFactory;
	
	public static synchronized ServiceFactory getServiceFactory() {
		if (uniqueFactory == null) {
			uniqueFactory = new ServiceFactory();
		}
		return uniqueFactory;
	}
	
	public DaoService getDaoService(){
        return new DaoService(MySQLDaoFactory.createVisitorDao(), MySQLDaoFactory.createExhibitionDao(),
                		MySQLDaoFactory.createExpoHallDao(), MySQLDaoFactory.createTicketDao());
    }
	
	public PaymentService getPaymentService(){
        return new PaymentService();
    }
}
