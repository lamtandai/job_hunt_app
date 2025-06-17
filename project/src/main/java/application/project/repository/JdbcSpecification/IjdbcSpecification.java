package application.project.repository.JdbcSpecification;

import java.util.Map;

public interface IjdbcSpecification <T> {
    String toSqlClause();
    Map<String, Object> getParameters();
    String getTableName();
    Class<T> getEntityClass();
}

