package com.example.springboot.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "task_history")
public class TaskHistory {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "todo_id", nullable = false)
  private Todo todo;

  @Column(name = "old_state")
  private String oldState;

  @Column(name = "new_state")
  private String newState;

  @UpdateTimestamp
  @Column(name = "change_date")
  private LocalDateTime changeDate;

  @Column(name = "changed_by")
  private String changedBy;
}
