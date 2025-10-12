package presspal.contact.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static presspal.contact.testutil.Assert.assertThrows;
import static presspal.contact.testutil.TypicalPersons.ALICE;
import static presspal.contact.testutil.TypicalPersons.HOON;
import static presspal.contact.testutil.TypicalPersons.IDA;
import static presspal.contact.testutil.TypicalPersons.getTypicalContactBook;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import presspal.contact.commons.exceptions.DataLoadingException;
import presspal.contact.model.ContactBook;
import presspal.contact.model.ReadOnlyContactBook;

public class JsonContactBookStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonAddressBookStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readContactBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readContactBook(null));
    }

    private java.util.Optional<ReadOnlyContactBook> readContactBook(String filePath) throws Exception {
        return new JsonContactBookStorage(Paths.get(filePath)).readContactBook(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readContactBook("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataLoadingException.class, () -> readContactBook("notJsonFormatContactBook.json"));
    }

    @Test
    public void readContactBook_invalidPersonContactBook_throwDataLoadingException() {
        assertThrows(DataLoadingException.class, () -> readContactBook("invalidPersonContactBook.json"));
    }

    @Test
    public void readContactBook_invalidAndValidPersonContactBook_throwDataLoadingException() {
        assertThrows(DataLoadingException.class, () -> readContactBook("invalidAndValidPersonContactBook.json"));
    }

    @Test
    public void readAndSaveContactBook_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempAddressBook.json");
        ContactBook original = getTypicalContactBook();
        JsonContactBookStorage jsonAddressBookStorage = new JsonContactBookStorage(filePath);

        // Save in new file and read back
        jsonAddressBookStorage.saveContactBook(original, filePath);
        ReadOnlyContactBook readBack = jsonAddressBookStorage.readContactBook(filePath).get();
        assertEquals(original, new ContactBook(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addPerson(HOON);
        original.removePerson(ALICE);
        jsonAddressBookStorage.saveContactBook(original, filePath);
        readBack = jsonAddressBookStorage.readContactBook(filePath).get();
        assertEquals(original, new ContactBook(readBack));

        // Save and read without specifying file path
        original.addPerson(IDA);
        jsonAddressBookStorage.saveContactBook(original); // file path not specified
        readBack = jsonAddressBookStorage.readContactBook().get(); // file path not specified
        assertEquals(original, new ContactBook(readBack));

    }

    @Test
    public void saveContactBook_nullContactBook_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveContactBook(null, "SomeFile.json"));
    }

    /**
     * Saves {@code addressBook} at the specified {@code filePath}.
     */
    private void saveContactBook(ReadOnlyContactBook addressBook, String filePath) {
        try {
            new JsonContactBookStorage(Paths.get(filePath))
                    .saveContactBook(addressBook, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveContactBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveContactBook(new ContactBook(), null));
    }
}
