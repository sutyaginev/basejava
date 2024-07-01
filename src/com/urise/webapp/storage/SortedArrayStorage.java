package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected int getIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }

    @Override
    protected void saveResume(Resume resume) {
        int insertIndex = getIndex(resume.getUuid()) * (-1) - 1;

        for (int i = size - 1; i >= insertIndex; i--) {
            storage[i + 1] = storage[i];
        }

        storage[insertIndex] = resume;
        size++;
    }

    @Override
    protected void deleteResume(int index) {
        for (int i = index + 1; i < size; i++) {
            storage[i - 1] = storage[i];
        }

        storage[size - 1] = null;
        size--;
    }
}
