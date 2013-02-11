package fr.ethilvan.bukkit.portals.portal.blocks;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.bukkit.World;
import org.bukkit.block.Block;

import fr.aumgn.bukkitutils.geom.Vector;
import fr.ethilvan.bukkit.portals.portal.Portal;

public class PortalBlocks implements Iterable<Block> {

    public class PortalBlocksIterator implements Iterator<Block> {

        private Iterator<Vector> iterator;

        public PortalBlocksIterator() {
            iterator = blocks.iterator();
        }

        @Override
        public boolean hasNext() {
            return iterator.hasNext();
        }

        @Override
        public Block next() {
            return iterator.next().toBlock(world);
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException(); 
        }
    }

    private World world;
    private Orientation orientation;
    private Vector min, max;
    private Set<Vector> blocks;

    public PortalBlocks(Portal portal) {
        this.world = portal.getWorld();
        this.orientation = portal.getOrientation();
        this.min = portal.getBlockMinPoint();
        this.max = portal.getBlockMaxPoint();
        this.blocks = new HashSet<Vector>();
        Vector vec = portal.getPosition();
        PortalBlocksVisitor visitor =
                new PortalBlocksVisitor(this, vec);
        visitor.visit();
    }

    public World getWorld() {
        return world;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public Vector getMin() {
        return min;
    }

    public Vector getMax() {
        return max;
    }

    public Set<Vector> getBlocks() {
        return blocks;
    }

    @Override
    public Iterator<Block> iterator() {
        return new PortalBlocksIterator();
    }
}
