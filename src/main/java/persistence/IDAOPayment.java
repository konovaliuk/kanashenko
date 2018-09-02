/**
 * @version 1.0
 *
 * @date Sep 2, 2018
 *
 * Copyright by Mykyta Kanashchenko
 */
package persistence;

import entity.Payment;

public interface IDAOPayment {
	public int addPayment(Payment payment);
}
