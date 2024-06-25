import java.util.Objects;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];

    void clear() {
        int storageSize = this.size();
        for (int i = 0; i < storageSize; i++) {
            storage[i] = null;
        }
    }

    void save(Resume r) {
        int storageSize = this.size();
        if (storageSize < 10000) {
            storage[storageSize] = r;
        }
    }

    Resume get(String uuid) {
        for (int i = 0; i < this.size(); i++) {
            if (Objects.equals(storage[i].uuid, uuid)) {
                return storage[i];
            }
        }
        return null;
    }

    void delete(String uuid) {
        int deletedResumeIndex = -1;
        int storageSize = this.size();

        for (int i = 0; i < storageSize; i++) {
            if (Objects.equals(storage[i].uuid, uuid)) {
                deletedResumeIndex = i;
            }
        }

        if (deletedResumeIndex == -1) {
            return;
        }

        for (int i = deletedResumeIndex + 1; i < storageSize; i++) {
            storage[i - 1] = storage[i];
        }

        storage[storageSize - 1] = null;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        int storageSize = this.size();
        Resume[] resumes = new Resume[storageSize];

        System.arraycopy(storage, 0, resumes, 0, storageSize);

        return resumes;
    }

    int size() {
        int size = 0;

        while (storage[size] != null) {
            size++;
        }

        return size;
    }
}
