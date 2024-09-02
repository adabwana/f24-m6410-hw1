(ns assignments.hw1.q1
  (:require
   [assignments.hw1.utils :refer :all]
   [fastmath.core :as m]
   [fastmath.random :as rand]
   [fastmath.stats :as s]
   [scicloj.hanamicloth.v1.api :as haclo]
   [tablecloth.api :as tc]))

(question "Question 1")
(sub-question
 "1) Medical reports have shown that approximately 80% of North American smokers died of lung cancer. Among four smokers who recently died in Huron, Ohio,")
(sub-sub
 "a) what is the chance that exactly 2 of them are due to lung cancer?")
(md "The `ncr` function calculates the number of combinations (n choose k):\n\n")
(formula "C_n^k = \\frac{n!}{k!(n-k)!}")
(md "This is used in the binomial probability distribution formula.")
(md "The `pdf-binomial` function calculates the probability mass function for a binomial distribution:")
(formula
 "P(X = k) = \\binom{n}{k} p^k (1-p)^{n-k}")
(md
 "Where:
        - $n$ is the number of trials
        - $k$ is the number of successes
        - $p$ is the probability of success on each trial")
(md
 "We can visualize the `n` and `p` specified Binomial Distribution with the following function:")


(comment
  (defn bin-cmf-viz
    "Creates a visualization of the binomial cumulative distribution function.
     n: number of trials
     p: probability of success on each trial"
    [n p]
    (let [data (tc/dataset
                {:k           (mapv str (range (inc n)))
                 :probability (map #(pmf-binomial % n p) (range (inc n)))
                 :cumulative  (reductions + (map #(pmf-binomial % n p) (range (inc n))))})]
      (-> data
          (haclo/layer-bar
           {:=x :k
            :=y :probability})))))


(bin-cmf-viz 4 0.8)


(md
 "Either observing the `probability` at `k=2`, above, or calculating it with the `pdf-binomial` function:")


(let [p 0.8 n 4 k 2
      probability (pmf-binomial k n p)]
  (answer
   (str
    "Probability of exactly " k " out of " n " smokers dying from lung cancer: "
    (* 100 (m/approx probability 4)) "%")))


(md
 (str
  "This can be done on the Calculator Casio fx-9750GII by doing the following steps:"
  (tc/add-column calculator-steps :button #(map format-button (:button %)))))


(sub-sub
 "b) What is the chance that at least 3 of them are due to lung cancer?")
(md
 "Notice that the probability of at least 3 is 1 minus the probability of less than 3 (at most 2). The `pdf-binomial` function calculates the probability mass function for a binomial distribution:")
(formula
 "P(X \\geq k) = 1 - P(X < k) = 1 - (P(X = 0) + P(X = 1) + ... + P(X = k-1))")
(formula
 "= \\sum_{i=0}^{k-1} P(X = i)")


(let [p 0.8 n 4 k 3
      prob (cmf-binomial (dec k) n p)
      probability (- 1 prob)]
  (answer
   (str
    "Probability of at least " k " out of " n " smokers dying from lung cancer: "
    (* 100 (m/approx probability 4)) "%")))


(sub-sub
 "c) What is the chance that at most 3 of them died of lung cancer?")


(let [p 0.8 n 4 k 3
      prob (cmf-binomial k n p)
      pdf (pmf-binomial (inc k) n p)
      probability (- 1 pdf)]
  (answer
   (str
    "Probability of at most " k " out of " n " smokers dying from lung cancer: "
    (* 100 (m/approx prob)) "%")))


(sub-sub
 "d) Interpret the expected value and standard deviation of the number of lung cancer patients among the four smokers who recently died in Huron, Ohio.")
(md
 "We can visualize the expected value of the number of lung cancer patients among the four smokers who recently died in Huron, Ohio by sampling the binomial distribution:")


(comment
  (defn binomial-dist-viz [sample-size n p]
    (let [binomial-dist (rand/distribution :binomial {:p p :trials n})
          samples (repeatedly sample-size #(rand/sample binomial-dist))
          data (-> (tc/dataset {:value samples})
                   (tc/group-by :value)
                   (tc/aggregate {:count tc/row-count})
                   (tc/add-column :with-cancer #(map str (:$group-name %))))
          plot (-> data
                   (haclo/layer-bar
                    {:=x :with-cancer
                     :=y :count}))
          mean-value (s/mean samples)]
      [plot (md (str "Mean of samples: " (m/approx mean-value 4)))])))


(binomial-dist-viz 1000 4 0.8)


(md
 "Looking at the many sampled Binomial distributions, we see most of the time the number of lung cancer patients is around 3. This is the expected value, shown below, with its theoretical expected value and standard deviation calculated with the following formulas:")
(formula
 "E(X) = n * p")


(defn expected-value [n p]
  (* n p))


(formula
 "SD(X) = \\sqrt{(n * p * (1 - p))}")


(defn standard-deviation [n p]
  (Math/sqrt (* n p (- 1 p))))


(let [n 4
      p 0.8
      expected-value (expected-value n p)
      standard-deviation (standard-deviation n p)]
  (answer
   (str
    "Expected value: " expected-value ";   Standard deviation: " (m/approx standard-deviation))))
