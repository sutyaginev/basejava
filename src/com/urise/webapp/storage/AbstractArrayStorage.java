package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage implements Storage {

    protected static final int STORAGE_LIMIT = 10000;
    protected final Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public final void update(Resume resume) {
        int index = getIndex(resume.getUuid());

        if (index < 0) {
            throw new NotExistStorageException(resume.getUuid());
        } else {
            storage[index] = resume;
        }
    }

    public final void save(Resume resume) {
        int index = getIndex(resume.getUuid());

        if (size >= STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", resume.getUuid());
        } else {
            if (index >= 0) {
                throw new ExistStorageException(resume.getUuid());
            } else {
                insertElement(resume, index);
                size++;
            }
        }
    }

    public final Resume get(String uuid) {
        int index = getIndex(uuid);

        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }

        return storage[index];
    }

    public final void delete(String uuid) {
        int index = getIndex(uuid);

        if (index < 0) {
            throw new NotExistStorageException(uuid);
        } else {
            // для array и sorted array будут разные реализации удаления
            fillDeletedElement(index);
            storage[size - 1] = null;
            size--;
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public int size() {
        return size;
    }

    protected abstract int getIndex(String uuid);

    protected abstract void insertElement(Resume resume, int index);

    protected abstract void fillDeletedElement(int index);
}
