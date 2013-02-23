package fr.ethilvan.bukkit.dimensions;

import static org.junit.Assert.*;

import org.bukkit.World;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fr.aumgn.mockbukkit.MockBukkit;
import fr.ethilvan.bukkit.api.dimensions.Dimension;
import fr.ethilvan.bukkit.api.dimensions.Dimensions;
import fr.ethilvan.bukkit.impl.dimensions.EVDimensions;

public class DimensionsBukkitTest {

    private Dimensions dimensions;
    private World world1;
    private World world2;
    private World world3;
    private World world4;
    private World world5;

    @Before
    public void setup() {
        MockBukkit.setUp();
        world1 = MockBukkit.mockWorld("world1");
        world2 = MockBukkit.mockWorld("world2");
        world3 = MockBukkit.mockWorld("world3");
        world4 = MockBukkit.mockWorld("world4");
        world5 = MockBukkit.mockWorld("world5");

        this.dimensions = new EVDimensions(DimensionsTestUtil.config);
    }

    @After
    public void tearDown() {
        MockBukkit.tearDown();
    }

    @Test
    public void testMainWorld() {
        assertEquals(world1, dimensions.get("survival").getMainWorld());
        assertEquals(world4, dimensions.get("creative").getMainWorld());
    }

    @Test
    public void testWorldDisplayName() {
        Dimension survival = dimensions.get("survival");
        Dimension creative = dimensions.get("creative");

        assertEquals("World 1", survival.getWorldDisplayName(world1));
        assertEquals("World 2", survival.getWorldDisplayName(world2));
        assertEquals("World 3", survival.getWorldDisplayName(world3));
        assertEquals("World 4", creative.getWorldDisplayName(world4));
        assertEquals("World 5", creative.getWorldDisplayName(world5));
    }

    @Test
    public void testWorldsList() {
        for (World world : dimensions.get("survival")) {
            assertTrue(world.equals(world1) || world.equals(world2) ||
                    world.equals(world3));
        }

        for (World world : dimensions.get("creative")) {
            assertTrue(world.equals(world4) || world.equals(world5));
        }
    }
}
