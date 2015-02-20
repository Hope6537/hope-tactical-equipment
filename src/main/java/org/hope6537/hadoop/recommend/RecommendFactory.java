package org.hope6537.hadoop.recommend;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.*;
import org.apache.mahout.cf.taste.eval.RecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.eval.AverageAbsoluteDifferenceRecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.eval.GenericRecommenderIRStatsEvaluator;
import org.apache.mahout.cf.taste.impl.eval.RMSRecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.model.GenericBooleanPrefDataModel;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.neighborhood.ThresholdUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.*;
import org.apache.mahout.cf.taste.impl.recommender.knn.KnnItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.recommender.knn.Optimizer;
import org.apache.mahout.cf.taste.impl.recommender.slopeone.SlopeOneRecommender;
import org.apache.mahout.cf.taste.impl.recommender.svd.Factorizer;
import org.apache.mahout.cf.taste.impl.recommender.svd.SVDRecommender;
import org.apache.mahout.cf.taste.impl.similarity.*;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * 构造推荐系统数据结构
 */
public final class RecommendFactory {

    /**
     * 从文件中构造数据模型，构建三列的数据模型，userId，itemId，rating
     */
    public static DataModel buildDataModel(String file) throws TasteException, IOException {
        return new FileDataModel(new File(file));
    }

    /**
     * 从文件中构造数据模型[去掉前缀版本] userId,itemId 只有两列的，沒有评分
     */
    public static DataModel buildDataModelNoPref(String file) throws TasteException, IOException {
        return new GenericBooleanPrefDataModel(GenericBooleanPrefDataModel.toDataMap(new FileDataModel(new File(file))));
    }

    /**
     * 返回一个两列数据模型建造器
     */
    public static DataModelBuilder buildDataModelNoPrefBuilder() {
        return trainingData -> new GenericBooleanPrefDataModel(GenericBooleanPrefDataModel.toDataMap(trainingData));
    }

    /**
     * 构建距离算法类 基於UserCF类
     */
    public static UserSimilarity userSimilarity(SIMILARITY type, DataModel m) throws TasteException {
        switch (type) {
            case PEARSON:
                return new PearsonCorrelationSimilarity(m);
            case COSINE:
                return new UncenteredCosineSimilarity(m);
            case TANIMOTO:
                return new TanimotoCoefficientSimilarity(m);
            case LOGLIKELIHOOD:
                return new LogLikelihoodSimilarity(m);
            case SPEARMAN:
                return new SpearmanCorrelationSimilarity(m);
            case CITYBLOCK:
                return new CityBlockSimilarity(m);
            case EUCLIDEAN:
            default:
                return new EuclideanDistanceSimilarity(m);
        }
    }

    /**
     * 构建距离算法类 基于ItemCF类
     */
    public static ItemSimilarity itemSimilarity(SIMILARITY type, DataModel m) throws TasteException {
        switch (type) {
            case PEARSON:
                return new PearsonCorrelationSimilarity(m);
            case COSINE:
                return new UncenteredCosineSimilarity(m);
            case TANIMOTO:
                return new TanimotoCoefficientSimilarity(m);
            case LOGLIKELIHOOD:
                return new LogLikelihoodSimilarity(m);
            case CITYBLOCK:
                return new CityBlockSimilarity(m);
            case EUCLIDEAN:
            default:
                return new EuclideanDistanceSimilarity(m);
        }
    }

    /**
     * 定义相似度
     */
    public static ClusterSimilarity clusterSimilarity(SIMILARITY type, UserSimilarity us) throws TasteException {
        switch (type) {
            case NEAREST_NEIGHBOR_CLUSTER:
                return new NearestNeighborClusterSimilarity(us);
            case FARTHEST_NEIGHBOR_CLUSTER:
            default:
                return new FarthestNeighborClusterSimilarity(us);
        }
    }

    public static UserNeighborhood userNeighborhood(NEIGHBORHOOD type, UserSimilarity s, DataModel m, double num) throws TasteException {
        switch (type) {
            case NEAREST:
                /**
                 * 根据数量构建最近的距离
                 */
                return new NearestNUserNeighborhood((int) num, s, m);
            case THRESHOLD:
            default:
                /**
                 * 根据百分比去构建
                 */
                return new ThresholdUserNeighborhood(num, s, m);
        }
    }

    /**
     * 基于用户的推荐模型
     */
    public static RecommenderBuilder userRecommender(final UserSimilarity us, final UserNeighborhood un, boolean pref) throws TasteException {
        return pref ? model -> new GenericUserBasedRecommender(model, un, us) : model -> new GenericBooleanPrefUserBasedRecommender(model, un, us);
    }

    /**
     * 基于物品的推荐模型
     */
    public static RecommenderBuilder itemRecommender(final ItemSimilarity is, boolean pref) throws TasteException {
        return pref ? model -> new GenericItemBasedRecommender(model, is) : model -> new GenericBooleanPrefItemBasedRecommender(model, is);
    }

    public static RecommenderBuilder slopeOneRecommender() throws TasteException {
        return dataModel -> new SlopeOneRecommender(dataModel);
    }

    public static RecommenderBuilder svdRecommender(final Factorizer factorizer) throws TasteException {
        return dataModel -> new SVDRecommender(dataModel, factorizer);
    }

    public static RecommenderBuilder itemKNNRecommender(final ItemSimilarity is, final Optimizer op, final int n) throws TasteException {
        return dataModel -> new KnnItemBasedRecommender(dataModel, is, op, n);
    }

    public static RecommenderBuilder treeClusterRecommender(final ClusterSimilarity cs, final int n) throws TasteException {
        return dataModel -> new TreeClusteringRecommender(dataModel, cs, n);
    }

    /**
     * 打印出推荐输出结果
     */
    public static void showItems(long uid, List<RecommendedItem> recommendations, boolean skip) {
        if (!skip || recommendations.size() > 0) {
            System.out.printf("uid:%s,", uid);
            for (RecommendedItem recommendation : recommendations) {
                System.out.printf("(%s,%f)", recommendation.getItemID(), recommendation.getValue());
            }
            System.out.println();
        }
    }

    public static RecommenderEvaluator buildEvaluator(EVALUATOR type) {
        switch (type) {
            case RMS:
                return new RMSRecommenderEvaluator();
            case AVERAGE_ABSOLUTE_DIFFERENCE:
            default:
                return new AverageAbsoluteDifferenceRecommenderEvaluator();
        }
    }

    /**
     * 计算全查率和准查率
     */
    public static void evaluate(EVALUATOR type, RecommenderBuilder rb, DataModelBuilder mb, DataModel dm, double trainPt) throws TasteException {
        System.out.printf("%s Evaluater Score:%s\n", type.toString(), buildEvaluator(type).evaluate(rb, mb, dm, trainPt, 1.0));
    }

    public static void evaluate(RecommenderEvaluator re, RecommenderBuilder rb, DataModelBuilder mb, DataModel dm, double trainPt) throws TasteException {
        System.out.printf("Evaluater Score:%s\n", re.evaluate(rb, mb, dm, trainPt, 1.0));
    }

    /**
     * statsEvaluator
     */
    public static void statsEvaluator(RecommenderBuilder rb, DataModelBuilder mb, DataModel m, int topn) throws TasteException {
        RecommenderIRStatsEvaluator evaluator = new GenericRecommenderIRStatsEvaluator();
        IRStatistics stats = evaluator.evaluate(rb, mb, m, null, topn, GenericRecommenderIRStatsEvaluator.CHOOSE_THRESHOLD, 1.0);
        // System.out.printf("Recommender IR Evaluator: %s\n", stats);
        System.out.printf("Recommender IR Evaluator: [Precision:%s,Recall:%s]\n", stats.getPrecision(), stats.getRecall());
    }

    /**
     * 定义各种距离
     */
    public enum SIMILARITY {
        PEARSON, EUCLIDEAN, COSINE, TANIMOTO, LOGLIKELIHOOD, SPEARMAN, CITYBLOCK, FARTHEST_NEIGHBOR_CLUSTER, NEAREST_NEIGHBOR_CLUSTER
    }

    /**
     * 构建近邻
     */
    public enum NEIGHBORHOOD {
        NEAREST, THRESHOLD
    }

    /**
     * 构建推荐模型
     */
    public enum RECOMMENDER {
        USER, ITEM
    }

    /**
     * 算法的评估
     * 根据结果距离统计 平均距离和平方距离[无太大影响]
     */
    public enum EVALUATOR {
        AVERAGE_ABSOLUTE_DIFFERENCE, RMS
    }

}
