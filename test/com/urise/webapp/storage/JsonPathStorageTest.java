    package com.urise.webapp.storage;

    import com.urise.webapp.storage.serializer.JsonStreamSerializer;

    class JsonPathStorageTest extends AbstractStorageTest {

    public JsonPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.getPath(), new JsonStreamSerializer()));
    }
}
