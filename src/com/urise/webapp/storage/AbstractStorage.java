package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    public final void update(Resume resume) {
        int index = getIndex(resume.getUuid());

        if (index < 0) {
            throw new NotExistStorageException(resume.getUuid());
        } else {
            updateElement(index, resume);
        }
    }

    public final void save(Resume resume) {
        int index = getIndex(resume.getUuid());

        handleExistingErrors(index, resume);
        insertElement(resume, index);
        increaseSize();
    }

    public final Resume get(String uuid) {
        int index = getIndex(uuid);

        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }

        return getElement(index);
    }

    public final void delete(String uuid) {
        int index = getIndex(uuid);

        if (index < 0) {
            throw new NotExistStorageException(uuid);
        } else {
            // для array и sorted array будут разные реализации удаления
            fillDeletedElement(index);
            decreaseSize();
        }
    }

    protected abstract int getIndex(String uuid);
    protected abstract void updateElement(int index, Resume resume);
    protected abstract Resume getElement(int index);
    protected abstract void fillDeletedElement(int index);
    protected abstract void decreaseSize();
    protected abstract void increaseSize();
    protected abstract void handleExistingErrors(int index, Resume resume);
    protected abstract void insertElement(Resume resume, int index);

}
