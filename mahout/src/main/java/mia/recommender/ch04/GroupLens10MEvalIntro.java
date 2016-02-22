package mia.recommender.ch04;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.common.Weighting;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.eval.RecommenderEvaluator;
import org.apache.mahout.cf.taste.example.grouplens.GroupLensDataModel;
import org.apache.mahout.cf.taste.impl.eval.AverageAbsoluteDifferenceRecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.recommender.slopeone.SlopeOneRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

import mia.util.FileUtil;

import java.io.File;

final class GroupLens10MEvalIntro {

    private GroupLens10MEvalIntro() {
    }

    public static void main(String[] args) throws Exception {
/*        File dataFile = new File(Thread.currentThread()
                .getContextClassLoader()
                .getResource("recommender/ch04/ratings.dat").toURI());*/
        File dataFile=FileUtil.getFile("recommender/ch04/ratings.dat");
        DataModel model = new GroupLensDataModel(dataFile);

        RecommenderEvaluator evaluator = new AverageAbsoluteDifferenceRecommenderEvaluator();
        RecommenderBuilder recommenderBuilder = new RecommenderBuilder() {
            @Override
            public Recommender buildRecommender(DataModel model)
                    throws TasteException {
                // 无权重.
               /* UserSimilarity similarity = new PearsonCorrelationSimilarity(
                        model);*/
                // 有权重.
                UserSimilarity similarity = new PearsonCorrelationSimilarity(
                        model,Weighting.WEIGHTED);
                // Changes of neighbor number affect the result.
                UserNeighborhood neighborhood = new NearestNUserNeighborhood(
                        200, similarity, model);
                /*return new GenericUserBasedRecommender(model, neighborhood,
                        similarity);*/
                return new SlopeOneRecommender(model);
            }
        };
        long beginTime=System.nanoTime();
        double score = evaluator.evaluate(recommenderBuilder, null, model,
                0.95, 0.05);
        long timeConsumed=System.nanoTime()-beginTime;
        System.out.println(score);
        System.out.println("Time consumed is :"+timeConsumed/1000000000l);
    }

}
