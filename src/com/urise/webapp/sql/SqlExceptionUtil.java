package com.urise.webapp.sql;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.StorageException;

import java.sql.SQLException;
import java.util.Objects;

public final class SqlExceptionUtil {

    private SqlExceptionUtil() {
    }

    public static StorageException returnException(SQLException e) {
        if (Objects.equals(e.getSQLState(), "23505")) {
            return new ExistStorageException(null);
        }

        return new StorageException(e);
    }
}
