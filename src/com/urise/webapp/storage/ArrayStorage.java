package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Objects;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    protected int getIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (Objects.equals(storage[i].getUuid(), uuid)) {
                return i;
            }
        }

        return -1;
    }

    @Override
    protected void saveResume(Resume resume) {
        storage[size] = resume;
        size++;
    }

    @Override
    protected void deleteResume(int index) {
        storage[index] = storage[size - 1];
        storage[size - 1] = null;
        size--;
    }
}
