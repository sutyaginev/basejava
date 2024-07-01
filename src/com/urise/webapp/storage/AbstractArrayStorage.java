package com.urise.webapp.storage;

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
        } else {
            if (getIndex(resume.getUuid()) >= 0) {
                System.out.printf("В хранилище уже есть резюме %s%n", resume.getUuid());
            } else {
                saveResume(resume);
                System.out.printf("Резюме %s сохранено%n", resume.getUuid());
            }
        }
    }

    public Resume get(String uuid) {
        int index = getIndex(uuid);

        if (index < 0) {
            System.out.printf("В хранилище нет резюме %s%n", uuid);
            return null;
        }

        return storage[index];
    }

    public void delete(String uuid) {
        int index = getIndex(uuid);

        if (index < 0) {
            System.out.printf("В хранилище нет резюме %s%n", uuid);
        } else {
            // для array и sorted array будут разные реализации удаления
            deleteResume(index);
            System.out.printf("Резюме %s удалено%n", uuid);
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

    protected abstract void saveResume(Resume resume);

    protected abstract void deleteResume(int index);
}
