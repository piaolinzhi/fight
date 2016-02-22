package mia.recommender.ch02;

import java.io.File;
import java.net.URL;
import java.util.List;

import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class RecommenderIntro {

    private static final Logger LOG = LoggerFactory
            .getLogger(RecommenderIntro.class);

    private RecommenderIntro() {
    }

    public static void main(String[] args) throws Exception {
        // 准备数据.
        URL dataURL = ClassLoader.getSystemClassLoader()
                .getResource("recommender/ch02/intro.csv");
        File dataFile = new File(dataURL.toURI());
        LOG.debug(dataURL.toString());
        LOG.debug(dataURL.toURI().toString());
        LOG.debug(dataFile.getAbsolutePath());

        DataModel model = new FileDataModel(dataFile);

        UserSimilarity similarity = new PearsonCorrelationSimilarity(
                model);
        UserNeighborhood neighborhood = new NearestNUserNeighborhood(2,
                similarity, model);
        // Create recommender engine.
        Recommender recommender = new GenericUserBasedRecommender(model,
                neighborhood, similarity);

        List<RecommendedItem> recommendations = recommender.recommend(1,
                2);

        for(RecommendedItem recommendation : recommendations) {
            System.out.println(recommendation);
            LOG.info(recommendation == null ? "null"
                    : recommendation.toString());
        }

    }

}
