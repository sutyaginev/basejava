package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Objects;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10000];
    private int size;

    public void clear() {
        for (int i = 0; i < size; i++) {
            storage[i] = null;
        }

        size = 0;
    }

    public void update(Resume r) {
        for (int i = 0; i < size; i++) {
            if (Objects.equals(storage[i].getUuid(), r.getUuid())) {
                storage[i] = r;
                System.out.printf("Резюме с uuid = %s обновлено%n", r.getUuid());
                return;
            }
        }

        System.out.printf("В хранилище нет резюме с uuid = %s%n", r.getUuid());
    }

    public void save(Resume r) {
        for (int i = 0; i < size; i++) {
            if (Objects.equals(storage[i].getUuid(), r.getUuid())) {
                System.out.printf("В хранилище уже есть резюме с uuid = %s%n", r.getUuid());
                return;
            }
        }

        if (size < 10000) {
            storage[size] = r;
            size++;
            System.out.printf("Резюме с uuid = %s сохранено%n", r.getUuid());
        }
    }

    public Resume get(String uuid) {
        for (int i = 0; i < size; i++) {
            if (Objects.equals(storage[i].getUuid(), uuid)) {
                return storage[i];
            }
        }

        System.out.printf("В хранилище нет резюме с uuid = %s%n", uuid);
        return null;
    }

    public void delete(String uuid) {
        for (int i = 0; i < size; i++) {
            if (Objects.equals(storage[i].getUuid(), uuid)) {
                storage[i] = storage[size - 1];
                storage[size - 1] = null;
                size--;
                System.out.printf("Резюме с uuid = %s удалено%n", uuid);
                return;
            }
        }

        System.out.printf("В хранилище нет резюме с uuid = %s%n", uuid);
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        Resume[] resumes = new Resume[size];
        System.arraycopy(storage, 0, resumes, 0, size);

        return resumes;
    }

    public int size() {
        return size;
    }
}
