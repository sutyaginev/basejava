package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;
import java.util.Objects;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10000];
    private int size;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void update(Resume r) {
        int resumeIndex = findResumeIndexByUuid(r.getUuid());

        if (resumeIndex < 0) {
            System.out.printf("В хранилище нет резюме с uuid = %s%n", r.getUuid());
        } else {
            storage[resumeIndex] = r;
            System.out.printf("Резюме с uuid = %s обновлено%n", r.getUuid());
        }
    }

    public void save(Resume r) {
        int resumeIndex = findResumeIndexByUuid(r.getUuid());

        if (resumeIndex < 0) {
            if (size < 10000) {
                storage[size] = r;
                size++;
                System.out.printf("Резюме с uuid = %s сохранено%n", r.getUuid());
            }
        } else {
            System.out.printf("В хранилище уже есть резюме с uuid = %s%n", r.getUuid());
        }
    }

    public Resume get(String uuid) {
        int resumeIndex = findResumeIndexByUuid(uuid);

        if (resumeIndex < 0) {
            System.out.printf("В хранилище нет резюме с uuid = %s%n", uuid);
            return null;
        } else {
            return storage[resumeIndex];
        }
    }

    public void delete(String uuid) {
        int resumeIndex = findResumeIndexByUuid(uuid);

        if (resumeIndex < 0) {
            System.out.printf("В хранилище нет резюме с uuid = %s%n", uuid);
        } else {
            storage[resumeIndex] = storage[size - 1];
            storage[size - 1] = null;
            size--;
            System.out.printf("Резюме с uuid = %s удалено%n", uuid);
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

    private int findResumeIndexByUuid(String uuid) {
        for (int i = 0; i < size; i++) {
            if (Objects.equals(storage[i].getUuid(), uuid)) {
                return i;
            }
        }

        return -1;
    }
}
