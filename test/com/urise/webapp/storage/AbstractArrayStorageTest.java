package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

abstract class AbstractArrayStorageTest {

    private final Storage storage;

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";

    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @BeforeEach
    public void setUp() throws Exception {
        storage.clear();
        storage.save(new Resume(UUID_1));
        storage.save(new Resume(UUID_2));
        storage.save(new Resume(UUID_3));
    }

    @Test
    void clear() {
        storage.clear();
        Assertions.assertEquals(0, storage.size());
    }

    @Test
    void update() {
        getNotExist();
        int oldStorageSize = storage.size();
        Resume newResume = new Resume("uuid1");
        storage.update(newResume);
        int newStorageSize = storage.size();
        Assertions.assertEquals(newResume, storage.get("uuid1"));
        Assertions.assertEquals(newStorageSize, oldStorageSize);
    }

    @Test
    void save() {
        // check saving new resume
        Assertions.assertThrows(ExistStorageException.class, () -> {
            storage.save(new Resume("uuid1"));
        });
        storage.save(new Resume("uuid4"));
        Assertions.assertEquals(4, storage.size());
        Assertions.assertTrue(Arrays.asList(storage.getAll()).contains(storage.get("uuid4")));

        // check overloading
        storage.clear();
        for (int i = 0; i < AbstractArrayStorage.STORAGE_LIMIT; i++) {
            try {
                storage.save(new Resume("uuid" + (i + 1)));
            } catch (StorageException e) {
                Assertions.fail("Overflow occurred ahead of time");
            }
        }
        Assertions.assertThrows(StorageException.class, () -> {
            storage.save(new Resume("uuid10001"));
        });

    }

    @Test
    void get() {
        getNotExist();
        Resume resume2 = storage.get(UUID_2);
        Resume anotherResume2 = storage.get(UUID_2);
        Assertions.assertNotNull(resume2);
        Assertions.assertNotNull(anotherResume2);
        Assertions.assertEquals(resume2, anotherResume2);
    }

    @Test
    void getNotExist() {
        Assertions.assertThrows(NotExistStorageException.class, () -> {
            storage.get("dummy");
        });
    }

    @Test
    void delete() {
        getNotExist();
        Assertions.assertNotNull(storage.get(UUID_1));
        storage.delete(UUID_1);
        Assertions.assertThrows(NotExistStorageException.class, () -> {
            storage.get(UUID_1);
        });
    }

    @Test
    void getAll() {
        List<Resume> allResumes = Arrays.asList(storage.getAll());
        Assertions.assertEquals(3, allResumes.size());
        Assertions.assertTrue(allResumes.contains(storage.get(UUID_1)));
        Assertions.assertTrue(allResumes.contains(storage.get(UUID_2)));
        Assertions.assertTrue(allResumes.contains(storage.get(UUID_3)));
    }

    @Test
    void size() {
        Assertions.assertEquals(3, storage.size());
    }
}