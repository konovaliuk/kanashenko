/**
 * 
 * Is a "Service layer" class that provides
 * wrapper DAO's methods for commands.
 *
 * @version 1.0
 *
 * @date Sep 2, 2018
 *
 * Copyright by Mykyta Kanashchenko
 */
package service;

import entity.Exhibition;
import entity.ExpoHall;
import entity.Visitor;
import persistence.IDAOExhibition;
import persistence.IDAOExpoHall;
import persistence.IDAOTicket;
import persistence.IDAOVisitor;

public class DaoService {

	private IDAOVisitor visitordao;
	private IDAOExhibition exhibitiondao;
	private IDAOExpoHall expohalldao;
	private IDAOTicket ticketdao;

	public DaoService() {
	}

	public DaoService(IDAOVisitor visitordao, IDAOExhibition exhibitiondao, IDAOExpoHall expohalldao,
			IDAOTicket ticketdao) {
		this.visitordao = visitordao;
		this.exhibitiondao = exhibitiondao;
		this.expohalldao = expohalldao;
		this.ticketdao = ticketdao;
	}
		
	public boolean isVisitor(String login, String password) {
		return visitordao.isVisitor(login, password);
	}

	public boolean isAdmin(String login, String password) {
		return visitordao.isAdmin(login, password);
	}

	public void addVisitor(Visitor visitor) {
		visitordao.addVisitor(visitor);
	}

	public int findVisitorId(String uname) {
		return visitordao.findVisitorId(uname);
	}

	public String findVisitorName(String uname) {
		return visitordao.findVisitorName(uname);
	}
	
	public  Exhibition[] findAllExhibition() {
		return exhibitiondao.findAllExhibition();
	}
	
	public  Exhibition findExhibitionbyName(String name) {
		return exhibitiondao.findExhibitionbyName(name);
	}
	
	public void addExhibition(Exhibition exhibition) {
		exhibitiondao.addExhibition(exhibition);
	}

	public ExpoHall[] findExpoHall(int exhibId) {
		return expohalldao.findExpoHallsbyId(exhibId);
	}
	
	public void addExpoHall(ExpoHall expoHall) {
		expohalldao.addExpoHall(expoHall);
	}

	public int howManyTickets(int exhibId) {
		return ticketdao.howManyTickets(exhibId);
	}
}
