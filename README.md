# Nozomi
Deployment tools for WebApplication.

# Features
- [ ] Deploy web application from Web UI
- [ ] Deploy lock

# Setup (Dev Environment)
## Start middleware
```
$ docker-compose up
```

## Run migration
```
# Create the database
$ gradlew rake -Pparam=db:create
# Create the test database
$ ENV=test gradlew rake -Pparam=db:create

# Migrate the database
$ gradlew rake -Pparam=db:migrate
# Migrate the test database
$ ENV=test gradlew rake -Pparam=db:migrate

# Create the migration
$ gradlew rake -Pparam="g:migration create_example_table"
```