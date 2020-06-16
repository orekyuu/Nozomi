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
# Install libraries
$ bundle install --path vendor/bundle

# Create the database
$ bundle exec rake db:create
# Create the test database
$ ENV=test bundle exec rake db:create

# Migrate the database
$ bundle exec rake db:migrate
# Migrate the test database
$ ENV=test bundle exec rake db:migrate

# Create the migration
$ bundle exec rake g:migration create_example_table
```

## Run migration (Docker)
```
# Start bash of ruby container
$ docker exec -it nozomi_dev-ruby_1 bash
# Install libraries
$ bundle install

# Create the database
# bundle exec rake db:create
```