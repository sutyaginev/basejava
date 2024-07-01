package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;
import java.util.Objects;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void update(Resume resume) {
        int index = getIndex(resume.getUuid());

        if (index < 0) {
            System.out.printf("В хранилище нет резюме %s%n", resume.getUuid());
        } else {
            storage[index] = resume;
            System.out.printf("Резюме %s обновлено%n", resume.getUuid());
        }
    }

    public void save(Resume resume) {
        if (size >= STORAGE_LIMIT) {
            System.out.println("Хранилище переполнено");
        } else if (getIndex(resume.getUuid()) >= 0) {
            System.out.printf("В хранилище уже есть резюме %s%n", resume.getUuid());
        } else {
            storage[size] = resume;
            size++;
            System.out.printf("Резюме %s сохранено%n", resume.getUuid());
        }
    }

    public void delete(String uuid) {
        int index = getIndex(uuid);

        if (index < 0) {
            System.out.printf("В хранилище нет резюме %s%n", uuid);
        } else {
            storage[index] = storage[size - 1];
            storage[size - 1] = null;
            size--;
            System.out.printf("Резюме %s удалено%n", uuid);
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    protected int getIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (Objects.equals(storage[i].getUuid(), uuid)) {
                return i;
            }
        }

        return -1;
    }
}
