package com.plusone.mup.persistence.models;

import java.util.Objects;

// TODO
//   https://ppbruna.medium.com/why-you-should-not-use-lombok-f7556662e8c3 TELL DON'T ASK principle
//   seems like an interesting read
public class User {
    private final String username;
    private final String emailAddress;
    private final String firstName;
    private final String lastName;
    private final String isoCountryCode;

    private User(Builder builder) {
        this.username = builder.username;
        this.emailAddress = builder.emailAddress;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        // TODO ? Locale.IsoCountryCode ?
        this.isoCountryCode = builder.isoCountryCode;
    }

    public String getUsername() {
        return username;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getIsoCountryCode() {
        return isoCountryCode;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", isoCountryCode='" + isoCountryCode + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(username, user.username) && Objects.equals(emailAddress, user.emailAddress) && Objects.equals(firstName, user.firstName) && Objects.equals(lastName, user.lastName) && Objects.equals(isoCountryCode, user.isoCountryCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, emailAddress, firstName, lastName, isoCountryCode);
    }

    public static UsernameStep builder() {
        return new Builder();
    }

    // step builder - https://blog.jayway.com/2012/02/07/builder-pattern-with-a-twist/
    static class Builder implements UsernameStep, EmailStep, FirstNameStep, LastNameStep, IsoCountryCodeStep, Build {
        // TODO: validation (email regex, number of chars etc)
        private String username;
        private String emailAddress;
        private String firstName;
        private String lastName;
        private String isoCountryCode;

        public EmailStep withUsername(String username) {
            this.username = username;
            return this;
        }

        public FirstNameStep withEmailAddress(String emailAddress) {
            this.emailAddress = emailAddress;
            return this;
        }

        public LastNameStep withFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public IsoCountryCodeStep withLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Build withIsoCountryCode(String isoCountryCode) {
            this.isoCountryCode = isoCountryCode;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }

    interface UsernameStep {
        EmailStep withUsername(String username);
    }

    interface EmailStep {
        FirstNameStep withEmailAddress(String emailAddress);
    }

    interface FirstNameStep {
        LastNameStep withFirstName(String firstName);
    }

    interface LastNameStep {
        IsoCountryCodeStep withLastName(String lastName);
    }

    interface IsoCountryCodeStep {
        Build withIsoCountryCode(String isoCountryCode);
    }

    interface Build {
        User build();
    }
}
