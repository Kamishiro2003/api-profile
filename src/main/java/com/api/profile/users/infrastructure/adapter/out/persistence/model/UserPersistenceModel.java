package com.api.profile.users.infrastructure.adapter.out.persistence.model;

import com.api.profile.users.domain.model.user.DocumentType;
import com.api.profile.users.domain.model.user.RoleEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents a user with essential account information.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Entity
@Table(
    name = "users",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "documentId_unique_constraint",
            columnNames = {"documentId"}
        ),
        @UniqueConstraint(
            name = "email_unique_constraint",
            columnNames = {"email"}
        )
    }
)
public class UserPersistenceModel {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(updatable = false)
  private String id;

  @Column(length = 150, nullable = false)
  private String name;

  @Column(length = 150, nullable = false)
  private String lastName;

  private DocumentType documentType;

  @Column(length = 30, nullable = false)
  private String documentId;

  @Column(length = 15, nullable = false)
  private String phoneNumber;

  @Column(length = 200, nullable = false)
  private String address;

  @Column(length = 200, nullable = false)
  private String email;

  @Column(nullable = false)
  private String password;

  private RoleEnum role;

  private LocalDateTime createdAt;

  private LocalDateTime updatedAt;

  private Boolean isActive;
}
