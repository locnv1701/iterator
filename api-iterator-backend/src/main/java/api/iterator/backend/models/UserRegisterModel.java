package api.iterator.backend.models;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class UserRegisterModel {
   @NotNull
   private long userTypeId;

   @NotBlank
   private String phoneNumber;

   @NotBlank
   private String email;

   @NotBlank
   private String firstName;

   @NotBlank
   private String lastName;

   @NotBlank
   private String userName;

   @NotBlank
   private String userPassword;

   // option for applicant
   private String entryLevel;
   private int experienceYear;

   public long getUserTypeId() {
      return userTypeId;
   }

   public String getPhoneNumber() {
      return phoneNumber;
   }

   public String getEmail() {
      return email;
   }

   public String getFirstName() {
      return firstName;
   }

   public String getLastName() {
      return lastName;
   }

   public String getUserName() {
      return userName;
   }

   public String getUserPassword() {
      return userPassword;
   }

   public String getEntryLevel() {
      return entryLevel;
   }

   public int getExperienceYear() {
      return experienceYear;
   }
}
