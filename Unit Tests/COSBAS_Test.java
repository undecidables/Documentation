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
}