package mia.recommender.ch05.ylpt;

import java.util.Collection;

import org.apache.mahout.cf.taste.common.Refreshable;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
/**
 * 基于科室的物品相似性度量.
 * 认为相同科室的档案相似度为1，不同科室的相似度为-1，若其中有一方的科室信息未知，则相似度为0.
 * @author plz
 *
 */
public class FacultyItemSimilarity implements ItemSimilarity {

    @Override
    public void refresh(Collection<Refreshable> alreadyRefreshed) {
        // TODO Auto-generated method stub

    }

    @Override
    public double itemSimilarity(long itemID1, long itemID2)
            throws TasteException {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public double[] itemSimilarities(long itemID1, long[] itemID2s)
            throws TasteException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long[] allSimilarItemIDs(long itemID) throws TasteException {
        // TODO Auto-generated method stub
        return null;
    }

}
