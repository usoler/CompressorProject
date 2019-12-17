package domain.dataStructure;

import java.util.LinkedList;
import java.util.List;

public class MacroBlockYCbCr {
    private List<Matrix<Float>> yBlocks;
    private Matrix<Float> cbBlock;
    private Matrix<Float> crBlock;

    /**
     * Constructs an empty {@link MacroBlockYCbCr}
     */
    public MacroBlockYCbCr() {
        this.yBlocks = new LinkedList<>();
    }

    /**
     * Constructs a new {@link MacroBlockYCbCr}
     *
     * @param yBlocks the {@link List<Matrix<Float>>} of luminance blocks
     * @param cbBlock the {@link Matrix<Float>} of chrominance blue block
     * @param crBlock the {@link Matrix<Float>} of chrominance red block
     */
    public MacroBlockYCbCr(List<Matrix<Float>> yBlocks, Matrix<Float> cbBlock, Matrix<Float> crBlock) {
        this.yBlocks = yBlocks;
        this.cbBlock = cbBlock;
        this.crBlock = crBlock;
    }

    /**
     * Gets the {@link List<Matrix<Float>>} of luminance blocks
     *
     * @return the {@link List<Matrix<Float>>} of luminance blocks
     */
    public List<Matrix<Float>> getyBlocks() {
        return yBlocks;
    }

    /**
     * Gets the {@link Matrix<Float>} of chrominance blue block
     *
     * @return the {@link Matrix<Float>} of chrominance blue block
     */
    public Matrix<Float> getCbBlock() {
        return cbBlock;
    }


    /**
     * Sets the chrominance blue block in the macroblock
     *
     * @param cbBlock the  {@link Matrix<Float>} of chrominance blue
     */
    public void setCbBlock(Matrix<Float> cbBlock) {
        this.cbBlock = cbBlock;
    }

    /**
     * Gets the {@link Matrix<Float>} of chrominance red block
     *
     * @return crBlock the {@link Matrix<Float>} of chrominance red block
     */
    public Matrix<Float> getCrBlock() {
        return crBlock;
    }

    /**
     * Sets the chrominance red block in the macroblock
     *
     * @param crBlock the  {@link Matrix<Float>} of chrominance red
     */
    public void setCrBlock(Matrix<Float> crBlock) {
        this.crBlock = crBlock;
    }

    /**
     * Adds a luminance block in the {@link List<Matrix<Float>>} of luminance blocks
     *
     * @param yBlock the {@link Matrix<Float>} of luminance block
     */
    public void addYBlock(Matrix<Float> yBlock) {
        this.yBlocks.add(yBlock);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MacroBlockYCbCr that = (MacroBlockYCbCr) o;
        return cbBlock.equals(that.cbBlock) &&
                crBlock.equals(that.crBlock) &&
                compareYBlocks(that.yBlocks);
    }

    private boolean compareYBlocks(List<Matrix<Float>> blocks) {
        boolean equal = true;

        for (int i = 0; i < blocks.size() && equal; ++i) {
            equal = this.yBlocks.get(i).equals(blocks.get(i));
        }

        return equal;
    }
}
