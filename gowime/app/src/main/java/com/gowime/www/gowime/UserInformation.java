package com.gowime.www.gowime;

public class UserInformation {

    private String firstName;
    private String lastName;
    private String aboutMe;
    private String myWork;
    private String email;
    private Boolean notificationChoice;
    private String searchRadius;

    public UserInformation() {
    }

    public UserInformation(String firstName, String lastName, String aboutMe, String myWork, String email, Boolean notificationChoice, String searchRadius) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.aboutMe = aboutMe;
        this.myWork = myWork;
        this.email = email;
        this.notificationChoice = notificationChoice;
        this.searchRadius = searchRadius;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAboutMe() {
        return aboutMe;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }

    public String getMyWork() {
        return myWork;
    }

    public void setMyWork(String myWork) {
        this.myWork = myWork;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getNotificationChoice() {
        return notificationChoice;
    }

    public void setNotificationChoice(Boolean notificationChoice) {
        this.notificationChoice = notificationChoice;
    }

    public String getSearchRadius() {
        return searchRadius;
    }

    public void setSearchRadius(String searchRadius) {
        this.searchRadius = searchRadius;
    }
}

