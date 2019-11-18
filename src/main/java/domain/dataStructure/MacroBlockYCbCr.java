package domain.dataStructure;

import java.util.LinkedList;
import java.util.List;

public class MacroBlockYCbCr {
    private List<Matrix<Float>> yBlocks;
    private Matrix<Float> cbBlock;
    private Matrix<Float> crBlock;

    public MacroBlockYCbCr() {
        this.yBlocks = new LinkedList<Matrix<Float>>();
    }

    public MacroBlockYCbCr(List<Matrix<Float>> yBlocks, Matrix<Float> cbBlock, Matrix<Float> crBlock) {
        this.yBlocks = yBlocks;
        this.cbBlock = cbBlock;
        this.crBlock = crBlock;
    }

    public List<Matrix<Float>> getyBlocks() {
        return yBlocks;
    }

    public void setyBlocks(List<Matrix<Float>> yBlocks) {
        this.yBlocks = yBlocks;
    }

    public Matrix<Float> getCbBlock() {
        return cbBlock;
    }

    public void setCbBlock(Matrix<Float> cbBlock) {
        this.cbBlock = cbBlock;
    }

    public Matrix<Float> getCrBlock() {
        return crBlock;
    }

    public void setCrBlock(Matrix<Float> crBlock) {
        this.crBlock = crBlock;
    }

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
