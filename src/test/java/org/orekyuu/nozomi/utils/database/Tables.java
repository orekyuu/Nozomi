package org.orekyuu.nozomi.utils.database;

import org.assertj.db.type.Table;

import javax.sql.DataSource;

public record Tables(DataSource datasource) {

    public Table projects() {
        return new Table(datasource, "projects");
    }
}
