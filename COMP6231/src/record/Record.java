package record;

import java.util.HashSet;
import java.util.Set;

public class Record {
	private String RecordId;
	private String LastName;
	private String FirstName;
	private String Address;
	private String Phone;
	private String Specialization;
	private String Location;
	private Set<String> CoursesRegistered;
	private String Status;
	private String StatusDate;

	public Record(String FirstName, String LastName, String RecordId, String Address, String Phone,
			String Specialization, String Location) {
		this.FirstName = FirstName;
		this.LastName = LastName;
		this.RecordId = RecordId;
		this.Address = Address;
		this.Phone = Phone;
		this.Specialization = Specialization;
		this.Location = Location;
		this.CoursesRegistered = CoursesRegistered;
		this.Status = Status;
		this.StatusDate = StatusDate;

	}
	public Record(String FirstName, String LastName, String RecordId,Set<String> CoursesRegistered, String Status, String StatusDate) {
		this.FirstName = FirstName;
		this.LastName = LastName;
		this.RecordId = RecordId;
		this.CoursesRegistered = CoursesRegistered;
		this.Status = Status;
		this.StatusDate = StatusDate;
		
	}
	public String getRecordId() {
		return RecordId;
	}

	public void setRecordId(String recordId) {
		RecordId = recordId;
	}

	public String getLastName() {
		return LastName;
	}

	public void setLastName(String lastName) {
		LastName = lastName;
	}

	public String getFirstName() {
		return FirstName;
	}

	public void setFirstName(String firstName) {
		FirstName = firstName;
	}

	public String getAddress() {
		return Address;
	}

	public void setAddress(String address) {
		Address = address;
	}

	public String getPhone() {
		return Phone;
	}

	public void setPhone(String phone) {
		Phone = phone;
	}

	public String getSpecialization() {
		return Specialization;
	}

	public void setSpecialization(String specialization) {
		Specialization = specialization;
	}

	public String getLocation() {
		return Location;
	}

	public void setLocation(String location) {
		Location = location;
	}

	public Set<String> getCoursesRegistered() {
		return CoursesRegistered;
	}

	public void setCoursesRegistered(Set<String> coursesRegistered) {
		CoursesRegistered = coursesRegistered;
	}

	public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		Status = status;
	}

	public String getStatusDate() {
		return StatusDate;
	}

	public void setStatusDate(String statusDate) {
		StatusDate = statusDate;
	}
	public boolean editRecord ( String fieldName, String newValue) {
		Boolean result = false ;
		String FiledID = getRecordId().substring(0, 2);
		 if (FiledID.equalsIgnoreCase("TR")) {
				if (fieldName.equalsIgnoreCase("location")) {
					if ((newValue.equalsIgnoreCase("MTL")) || (newValue.equalsIgnoreCase("LVL"))
							|| (newValue.equalsIgnoreCase("DDO"))) {
						setLocation(newValue);
					} else {
						System.err.println("the Location must be One of ( MTL || LVL || DDO ) !");
						result = false;
					}
				} else if (fieldName.equalsIgnoreCase("phone")) {
					setPhone(newValue);
				} else if (fieldName.equalsIgnoreCase("address")) {
					setAddress(newValue);
				} else {
					result = false;
				}
			} else {
				if (fieldName.equalsIgnoreCase("coursesRegistered")) {
					Set<String> newCourseRegistered = new HashSet<>();
					String[] s = newValue.split("\\s");
					for (String course : s) {
						newCourseRegistered.add(course);
					}
					setCoursesRegistered(newCourseRegistered);
				} else if (fieldName.equalsIgnoreCase("status")) {
					if ((newValue.equalsIgnoreCase("active")) || (newValue.equalsIgnoreCase("inactive"))) {
						setStatus(newValue);
					} else {
						System.err.println("the status should be active or inactive.");
						result = false;
					}
				} else if (fieldName.equalsIgnoreCase("statusDate")) {
					setStatusDate(newValue);
				} else {
					result = false;
				}
			}
			return result;

		}

}


