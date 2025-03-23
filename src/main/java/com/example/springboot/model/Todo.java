package com.example.springboot.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@SQLDelete(sql = "UPDATE todos SET is_deleted=true WHERE id=?")
@SQLRestriction("is_deleted=false")
@Table(name = "todos")
public class Todo {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String title;

  private String description;

  @Column(name = "due_date")
  private LocalDateTime dueDate;

  @Enumerated(EnumType.STRING)
  private Priority priority = Priority.MEDIUM;

  @Enumerated(EnumType.STRING)
  private Status status = Status.PENDING;

  @CreationTimestamp
  @Column(name = "created_at", nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @UpdateTimestamp
  @Column(name = "updated_at", nullable = false)
  private LocalDateTime updatedAt;

  @Column(name = "user_id")
  private Long userId;

  @Column(name = "is_deleted", nullable = false, columnDefinition = "TINYINT(1)")
  private boolean isDeleted = false;

  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  @OneToMany(mappedBy = "todo", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<TaskHistory> taskHistories = new ArrayList<>();
}
