package com.example.springboot.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
@SQLDelete(sql = "Update todos Set is_deleted = true WHERE id = ?")
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

  public Todo(Long id, String title, String description, LocalDateTime dueDate, Priority priority, Status status, LocalDateTime createdAt, LocalDateTime updatedAt, Long userId) {
    this.id = id;
    this.title = title;
    this.description = description;
    this.dueDate = dueDate;
    this.priority = priority;
    this.status = status;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
    this.userId = userId;
  }

  public Todo() {

  }

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
  private Long userId = 1L;

  @Column(name = "is_deleted", nullable = false)
  private boolean isDeleted = false;

  public String getInfo() {
    return "Title: " + title
      + "\nDescription: " + description
      + "\nDue date: " + dueDate
      + "\nPriority: " + priority
      + "\nStatus: " + status;
  }
}