package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 10000;
    protected final Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size;

    public int size() {
        return size;
    }

    public Resume get(String uuid) {
        int index = getIndex(uuid);

        if (index < 0) {
            System.out.printf("В хранилище нет резюме %s%n", uuid);
            return null;
        }

        return storage[index];
    }

    protected abstract int getIndex(String uuid);
}
