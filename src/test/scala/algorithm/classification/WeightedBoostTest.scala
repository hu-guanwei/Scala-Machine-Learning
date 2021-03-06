// Wei Chen - Weighted Boost Test
// 2018-09-26

import com.scalaml.TestData._
import com.scalaml.general.MatrixFunc._
import com.scalaml.algorithm._
import org.scalatest.funsuite.AnyFunSuite

class WeightedBoostSuite extends AnyFunSuite {

    val boost = new WeightedBoost()

    test("WeightedBoost Test : Clear") {
        assert(boost.clear())
    }

    test("WeightedBoost Test : Linear Data") {
        assert(boost.clear())
        assert(boost.config(Map[String, Any]()))
        assert(boost.train(LABELED_LINEAR_DATA))
        val result1 = boost.predict(UNLABELED_LINEAR_DATA)
        assert(arrayequal(result1, LABEL_LINEAR_DATA))

        val classifiers: Any = Array(
            new BayesianDecision,
            new DecisionTree,
            new GaussianProcess,
            new KNN,
            new LinearClassification,
            new LinearSVM,
            new Perceptron,
            new RandomForest
        )
        assert(boost.clear())
        assert(boost.config(Map("classifiers" -> classifiers)))
        assert(boost.train(LABELED_LINEAR_DATA))
        val result2 = boost.predict(UNLABELED_LINEAR_DATA)
        assert(arrayequal(result2, LABEL_LINEAR_DATA))
    }

    test("WeightedBoost Test : Nonlinear Data") {
        assert(boost.clear())
        assert(boost.config(Map[String, Any]()))
        assert(boost.train(LABELED_NONLINEAR_DATA))
        val result1 = boost.predict(UNLABELED_NONLINEAR_DATA)
        assert(arrayequal(result1, LABEL_NONLINEAR_DATA))

        val svm = new LinearSVM()
        svm.config(Map("cost" -> Map(1 -> 1.0, 2 -> 1.0)): Map[String, Any])
        val classifiers: Any = Array(
            new BayesianDecision,
            new DecisionTree,
            new GaussianProcess,
            new KNN,
            new LinearClassification,
            svm,
            new Perceptron,
            new RandomForest
        )
        assert(boost.clear())
        assert(boost.config(Map("classifiers" -> classifiers)))
        assert(boost.train(LABELED_NONLINEAR_DATA))
        val result2 = boost.predict(UNLABELED_NONLINEAR_DATA)
        assert(arrayequal(result2, LABEL_NONLINEAR_DATA))
    }

    test("WeightedBoost Test : Invalid Config & Data") {
        assert(boost.clear())
        assert(!boost.config(Map("classifiers" -> "test")))
        assert(boost.config(Map[String, Any]()))
        assert(!boost.train(Array((1, Array(1, 2)), (1, Array()))))
    }
}
