import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class COSBASTest {

	final boolean isAvailable = true;
	final boolean isNotAvailable = false;
	
	@Test
	public void ViewAvailabilityTest(){
		//We need to test if we query the calendar linked with the user
		//for a certain date and time if they are available or not.
		COSBAS system = new COSBAS();
		boolean available = system.queryAvailablility("30 February 2015", "14:20", 3); //Params are only propositional: (Date, Time, HoursNeeded)
		//The hours needed parameter is needed so that we can controll possible overlapping appointments.
		assertEquals("Failed to read the availability of the user.", isAvailable, available);
	}

	@Test
	public void SetAvailabilityTest(){
		COSBAS system = new COSBAS();
		system.setAppointment("Self", "1 June 1994", "12:00", 1);
		//Parameter usage: (How is the appointment assigned, Date, Time, HoursNeeded)
		boolean available = system.queryAvailablility("1 June 1994", "12:00", 1);
		assertEquals("Could not establish that there is already an appointment scheduled by the staff member.", isNotAvailable, available);

		system.setAppointment("Approval", "1 June 1994", "13:15", 1.5);
		//This is to show that the appointment was requested by a user and was approved by the staff member.
		boolean available = system.queryAvailablility("1 June 1994", "13:00", 1);
		assertEquals("Could not establish that there is already an appointment scheduled by an approval.", isNotAvailable, available);
	}

	@Test
	public void BiometricAccessTest(){
		COSBAS system = new COSBAS();

		boolean access = system.requestAccess(AccessRequest);
		if (access){
			system.sendSignal(); //to give access
		}

		assertEquals("Unable to grant access", true, access);
	}

	@Test
	public void TestLogin(){
		COSBAS system = new COSBAS();
		String username = "Pietertjie";
		String password = "**********";
		boolean loggedIn = system.authenticateLogin(username, password);
		assertEquals("Could not log in user.", true, loggedIn);
	}

	@Test
	public void TestLogout(){
		COSBAS system = new COSBAS();
		system.logout();
		boolean success = system.cookieDestroyed();
		assertEquals("Did not log user out successfully.", false, success);
	}

	@Test
	public void TestAppointmentRequest(){
		//Will test if an appointment was set by approval from the staff member.
		//Test if temporary access codes have been revoked for the specific appointment participants.
	}

	@Test
	public void TestCancelRequest(){
		//Will test if an appointment was cancelled and removed from the calendar.
	}

}