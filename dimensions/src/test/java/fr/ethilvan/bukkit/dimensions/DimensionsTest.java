package fr.ethilvan.bukkit.dimensions;

import static org.junit.Assert.*;

import org.bukkit.Difficulty;
import org.bukkit.GameMode;
import org.junit.Before;
import org.junit.Test;

import fr.ethilvan.bukkit.api.dimensions.Dimensions;
import fr.ethilvan.bukkit.impl.dimensions.EVDimensions;

public class DimensionsTest {

    private Dimensions dimensions;

    @Before
    public void setup() {
        this.dimensions = new EVDimensions(DimensionsTestUtil.config);
    }

    @Test
    public void testMain() {
        assertEquals(dimensions.get("survival"), dimensions.getMain());
    }

    @Test
    public void testDimensionDisplayName() {
        assertEquals("Survival", dimensions.get("survival").getDisplayName());
        assertEquals("Creative !", dimensions.get("creative").getDisplayName());
    }

    @Test
    public void testDimensionDifficulty() {
        assertEquals(Difficulty.HARD,
                dimensions.get("survival").getConfig().getDifficulty());
        assertEquals(Difficulty.PEACEFUL,
                dimensions.get("creative").getConfig().getDifficulty());
    }

    @Test
    public void testDimensionGameMode() {
        assertEquals(GameMode.SURVIVAL,
                dimensions.get("survival").getConfig().getGameMode());
        assertEquals(GameMode.CREATIVE,
                dimensions.get("creative").getConfig().getGameMode());
    }

    @Test
    public void testFlags() {
        assertTrue( dimensions.get("survival").getConfig().hasFlag("tag1"));
        assertFalse(dimensions.get("survival").getConfig().hasFlag("tag2"));
        assertTrue( dimensions.get("survival").getConfig().hasFlag("tag3"));

        assertFalse(dimensions.get("creative").getConfig().hasFlag("tag1"));
        assertTrue( dimensions.get("creative").getConfig().hasFlag("tag2"));
        assertTrue( dimensions.get("creative").getConfig().hasFlag("tag3"));
    }
}
