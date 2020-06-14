require 'active_record'

env = ENV['ENV'] || 'development'
databases = YAML::load(File.open('config/database.yml'))
db_config = databases[env]

unless db_config
  puts "Error! Invalid environment. env=#{env}"
  return
end
db_config_admin = db_config.merge({'database' => 'mysql', 'schema_search_path' => 'public'})

namespace :db do

  desc 'Create the database'
  task :create do
    ActiveRecord::Base.establish_connection(db_config_admin)
    ActiveRecord::Base.connection.create_database(db_config['database'])
    puts "Database created. database=#{db_config['database']}"
  end

  desc 'Migrate the database'
  task :migrate do
    ActiveRecord::Base.establish_connection(db_config)
    ActiveRecord::Tasks::DatabaseTasks.migrate

    puts "Database migrated. database=#{db_config['database']}"
  end

  desc 'Drop the database'
  task :drop do
    ActiveRecord::Base.establish_connection(db_config_admin)
    ActiveRecord::Base.connection.drop_database(db_config['database'])
    puts "Database deleted. database=#{db_config['database']}"
  end

  desc 'Reset the database'
  task :reset => [:drop, :create, :migrate]

  desc "Migrate the database"
  task :rollback do
    ActiveRecord::Base.establish_connection(db_config)
    ActiveRecord::MigrationContext.new("db/migrate/").rollback
    Rake::Task["db:schema"].invoke
    puts "Last migration has been reverted."
  end
end

namespace :g do
  desc 'Generate migration'
  task :migration do
    name = ARGV[1] || raise('Specify name: rake g:migration your_migration')
    timestamp = Time.now.strftime('%Y%m%d%H%M%S')
    path = File.expand_path("../db/migrate/#{timestamp}_#{name}.rb", __FILE__)
    migration_class = name.split('_').map(&:capitalize).join

    File.open(path, 'w') do |file|
      file.write <<-EOF
class #{migration_class} < ActiveRecord::Migration[4.2]
  def change
    # TODO
  end
end
      EOF
    end

    puts "Migration #{path} created"
    abort # needed stop other tasks
  end
end
