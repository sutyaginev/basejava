package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {

    protected final List<Resume> storage = new ArrayList<>();

    public void clear() {
        storage.clear();
    }

    public Resume[] getAll() {
        return storage.toArray(new Resume[0]);
    }

    public int size() {
        return storage.size();
    }

    @Override
    protected int getIndex(String uuid) {
        Resume searchKey = new Resume(uuid);
        return storage.indexOf(searchKey);
    }

    protected void insertElement(Resume resume, int index) {
        storage.add(resume);
    }

    @Override
    protected void fillDeletedElement(int index) {
        storage.remove(index);
    }

    @Override
    protected void updateElement(int index, Resume resume) {
        storage.set(index, resume);
    }

    @Override
    protected Resume getElement(int index) {
        return storage.get(index);
    }

    @Override
    protected void decreaseSize() {}

    @Override
    protected void increaseSize() {}

    @Override
    protected void handleExistingErrors(int index, Resume resume) {
        if (index >= 0) {
            throw new ExistStorageException(resume.getUuid());
        }
    }
}
