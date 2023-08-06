package cat.itacademy.minddy.data.config;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HierarchicalIdTest {

    HierarchicalId id = new HierarchicalId().setHolderId("ABCDEFGHIJ");

    @Test
    void getFirstParent_test() {
        assertEquals("IJ", id.getFirstParent());
    }

    @Test
    void getAllParents_test() {
        assertEquals("EF", id.getAllParents().get(2));
    }
    @Test
    void mergedIdConstructor() {
        assertEquals("IJ", new HierarchicalId("userId","ABCDEFGHIJ").getOwnId());
        assertEquals("ABCDEFGH", new HierarchicalId("userId","ABCDEFGHIJ").getHolderId());
    }
}
