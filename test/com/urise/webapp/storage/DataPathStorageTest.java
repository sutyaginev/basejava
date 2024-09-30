package com.urise.webapp.storage;

import com.urise.webapp.storage.serializer.DataStreamSerializer;

class DataPathStorageTest extends AbstractStorageTest {

    public DataPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.getPath(), new DataStreamSerializer()));
    }
}