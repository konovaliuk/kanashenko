package service;

import org.mockito.*;
import static org.junit.Assert.*;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import persistence.IDAOExhibition;
import persistence.IDAOExpoHall;
import persistence.IDAOTicket;
import persistence.IDAOVisitor;

@RunWith(MockitoJUnitRunner.class)
public class DaoServiceTest {
	
	@Mock
    private IDAOVisitor visitorDaoMock;	
	@Mock
    private IDAOExhibition exhibitionDaoMock;	
	@Mock
    private IDAOExpoHall expoHallDaoMock;	
	@Mock
    private IDAOTicket ticketDaoMock;
	
	@InjectMocks
    private DaoService daoService = new DaoService();

	@Test
	public void testIsVisitor_ValidUser() {
		
		//Given
        given(visitorDaoMock.isVisitor("admin", "qwerty")).willReturn(true);
		//When
        boolean validUser = daoService.isVisitor("admin", "qwerty");
        //Then
        assertThat(validUser, is(true));
        
        verify(visitorDaoMock, times(1)).isVisitor("admin", "qwerty");
	}
	
	@Test
	public void testIsVisitor_InvalidUser() {
		
		//When
        boolean validUser = daoService.isVisitor("asd", "23f2qf2fec");
        //Then
        assertThat(validUser, is(false));
        
        verify(visitorDaoMock, times(1)).isVisitor("asd", "23f2qf2fec");
	}
	

}
