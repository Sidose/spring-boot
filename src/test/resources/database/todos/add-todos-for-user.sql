INSERT INTO todos (id, title, description, due_date, priority, status, created_at, updated_at, user_id, is_deleted)
VALUES (10, 'Task first', 'Description for task first', '2025-03-30 23:00:00', 'HIGH', 'PENDING', '2025-03-12 15:00:00',
        '2025-03-13 23:00:00', 4, 0),
       (11, 'Task second', 'Description for task second', '2025-03-30 23:00:00', 'MEDIUM', 'COMPLETED',
        '2025-03-13 16:00:00', '2025-03-14 23:00:00', 4, 0),
       (12, 'Task third', 'Description for task second', '2025-03-30 23:00:00', 'LOW', 'COMPLETED',
        '2025-03-14 17:00:00', '2025-03-15 23:00:00', 4, 0);