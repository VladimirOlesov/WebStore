package com.example.webstore.model.entity;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@AttributeOverride(name = "Id", column = @Column(name = "user_id"))
@Table(name = "users")
public class User extends BaseEntity {

  @Column(name = "username", nullable = false)
  private String username;

  @Column(name = "password", nullable = false)
  private String password;

  @Column(name = "email", nullable = false)
  private String email;

  @Column(name = "phone_number")
  private String phoneNumber;

  @Column(name = "registration_date")
  private Timestamp registrationDate;

  @Column(name = "first_name")
  private String firstName;

  @Column(name = "last_name")
  private String lastName;
}