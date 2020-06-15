class CreateProjects < ActiveRecord::Migration[5.2]
  def change
    create_table :projects, id: false do |t|
      t.string :project_id, null: false, primary_key: true
      t.string :name, null: false
    end
  end
end
