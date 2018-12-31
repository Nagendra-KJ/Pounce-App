package rvquizcorp.com.pounce_app;

import java.net.URI;

public class User {
    private String firstName;
    private String lastName;
    private String mobileNumber;
    private String emailAddress;
    private String userId;
    private URI profilePicPath;

    User(String userId, String firstName, String lastName, String mobileNumber, String emailAddress) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.mobileNumber = mobileNumber;
        this.emailAddress = emailAddress;
    }

    User(String userId, String firstName, String lastName, String mobileNumber, String emailAddress, URI profilePicPath) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.mobileNumber = mobileNumber;
        this.emailAddress = emailAddress;
        this.profilePicPath = profilePicPath;
    }

    User(){
        this.firstName="";
        this.lastName="";
        this.emailAddress="";
        this.mobileNumber="";
        this.userId="";
    }
    String getFirstName() {
        return firstName;
    }

    void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    String getLastName() {
        return lastName;
    }

    void setLastName(String lastName) {
        this.lastName = lastName;
    }

    String getMobileNumber() {
        return mobileNumber;
    }

    void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    String getEmailAddress() {
        return emailAddress;
    }

    void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getUserId() {
        return userId;
    }

    void setUserId(String userId) {
        this.userId = userId;
    }

    public URI getProfilePicPath() {
        return profilePicPath;
    }

    public void setProfilePicPath(URI profilePicPath) {
        this.profilePicPath = profilePicPath;
    }
}
