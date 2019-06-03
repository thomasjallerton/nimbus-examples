package models;

import com.nimbusframework.nimbuscore.annotation.annotations.database.DatabaseLanguage;
import com.nimbusframework.nimbuscore.annotation.annotations.database.RelationalDatabase;

@RelationalDatabase(
        name="evaluationdb",
        databaseLanguage = DatabaseLanguage.MYSQL,
        username = "test",
        password = "28397ksjhdf")
public class Database {}
