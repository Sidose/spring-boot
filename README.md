# GlobalExceptionHandler and Data Validation

## Pre requirements

- Run the following command to run the MySQL instance as a Docker container on local port 3308:

```shell
docker pull mysql:latest && docker run -d --name mysql_local -e MYSQL_ROOT_PASSWORD=1234 -e MYSQL_DATABASE=db -e MYSQL_USER=user -e MYSQL_PASSWORD=password -p 3308:3306 mysql:latest && echo 'MySQL Docker container started as mysql_local'
```

- Check the status of running container:

```shell
docker ps --filter name=mysql_local --format 'table {{.Names}}\t{{.Status}}'
```

- Stop the MySQL container:

```shell
docker stop mysql_local && docker rm mysql_local && echo 'MySQL Docker container stopped and removed'
```

## Create a schema

- Connect to a MySQL instance using IntelliJ Idea
- Run `./init.sql` file

## Testing

1. Save todo:

    ```shell
    curl --location 'http://localhost:8080/todos' --header 'Content-Type: application/json' \
    --data '{
      "title": "Add app",
      "description": "Todo management app"
      "dtu_date": "2025-04-02T14:30:45.123", 
      "priority": "medium",
      "status": "in_progress",
      "created_at": "2025-03-02T14:30:45.123",
      "updated_at": "2025-03-02T14:30:45.123",
      "user_id": 1,
      "is_deleted": false
    }' 
    ```

2. Update todo:

   ```shell
   curl --location 'http://localhost:8080/todos/1' --header 'Content-Type: application/json' \
   --data '{
     "id": 1,
     "title": "Add app",
     "description": "Todo management app"
     "dtu_date": "2025-04-02T14:30:45.123",
     "priority": "medium",
     "status": "in_progress",
     "created_at": "2025-03-02T14:30:45.123",
     "updated_at": "2025-03-02T14:30:45.123",
     "user_id": 1,
     "is_deleted": false
   }'
   ```

3. Delete todo:

   ```shell
   curl --location --request DELETE 'http://localhost:8080/todos/1'
   ```

4. Get list of history tasks:

    ```shell
    curl --location 'http://localhost:8080/todos/1/history'
    ```