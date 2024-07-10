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
import java.util.UUID;

abstract class AbstractArrayStorageTest {

    private final Storage storage;

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";
    private static final Resume resume1 = new Resume(UUID_1);
    private static final Resume resume2 = new Resume(UUID_2);
    private static final Resume resume3 = new Resume(UUID_3);
    private static final Resume resume4 = new Resume(UUID_4);

    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @BeforeEach
    public void setUp() throws Exception {
        storage.clear();
        storage.save(resume1);
        storage.save(resume2);
        storage.save(resume3);
    }

    @Test
    void clear() {
        storage.clear();
        assertSize(0);
        Assertions.assertArrayEquals(storage.getAll(), new Resume[]{});
    }

    @Test
    void update() {
        Resume newResume1 = new Resume(UUID_1);
        storage.update(newResume1);
        Assertions.assertSame(newResume1, storage.get(UUID_1));
    }

    @Test
    void save() {
        storage.save(resume4);
        assertGet(resume4);
        assertSize(4);
    }

    @Test
    void saveExist() {
        Assertions.assertThrows(ExistStorageException.class, () -> {
            storage.save(resume1);
        });
    }

    @Test
    void saveOverflow() {
        storage.clear();

        for (int i = 0; i < AbstractArrayStorage.STORAGE_LIMIT; i++) {
            try {
                saveRandomResume();
            } catch (StorageException e) {
                Assertions.fail("Overflow occurred ahead of time");
            }
        }

        Assertions.assertThrows(StorageException.class, this::saveRandomResume);
    }

    void saveRandomResume() {
        storage.save(new Resume(UUID.randomUUID().toString()));
    }

    @Test
    void get() {
        getNotExist();
        assertGet(storage.get(UUID_1));
        assertGet(storage.get(UUID_2));
        assertGet(storage.get(UUID_3));
    }

    void assertGet(Resume resume) {
        Assertions.assertEquals(resume, storage.get(resume.getUuid()));
    }

    @Test
    void getNotExist() {
        Assertions.assertThrows(NotExistStorageException.class, () -> {
            storage.get("dummy");
        });
    }

    @Test
    void delete() {
        storage.delete(UUID_1);
        assertSize(2);
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
        assertSize(3);
    }

    void assertSize(int size) {
        Assertions.assertEquals(size, storage.size());
    }
}