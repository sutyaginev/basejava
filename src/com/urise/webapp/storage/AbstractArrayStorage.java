package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.util.Arrays;
import java.util.List;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage extends AbstractStorage<Integer> {

    protected static final int STORAGE_LIMIT = 10000;
    protected final Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void doUpdate(Integer index, Resume resume) {
        storage[index] = resume;
    }

    public void doSave(Integer index, Resume resume) {
        if (size >= STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", resume.getUuid());
        } else {
            insertElement(index, resume);
            size++;
        }
    }

    public Resume doGet(Integer index) {
        return storage[index];
    }

    protected void doDelete(Integer index) {
        fillDeletedElement(index);
        storage[size - 1] = null;
        size--;
    }

    @Override
    protected List<Resume> doCopyAll() {
        return Arrays.asList(Arrays.copyOf(storage, size));
    }

    public int size() {
        return size;
    }

    @Override
    protected boolean isExist(Integer searchKey) {
        return searchKey >= 0;
    }

    protected abstract void insertElement(int index, Resume resume);

    protected abstract void fillDeletedElement(int index);
}
