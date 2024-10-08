(ns assignments.hw1.utils
  (:require
   [clojisr.v1.applications.plotting :refer [plot->svg]]
   [clojisr.v1.r :refer [r+ require-r]]
   [fastmath.core :as m]
   [fastmath.random :as rand]
   [fastmath.stats :as s]
   [scicloj.hanamicloth.v1.api :as haclo]
   [scicloj.kindly.v4.api :as kindly]
   [scicloj.kindly.v4.kind :as kind]
   [tablecloth.api :as tc]))

(kind/md "## Utils")

(require-r '[stats :refer [dnorm]]
           '[ggplot2 :refer [ggplot aes geom_area geom_vline scale_x_continuous
                             geom_text stat_function labs theme_minimal theme_set]])

(comment
  (theme_set (theme_minimal)))

;; Formatting code
(def md (comp kindly/hide-code kind/md))
(def question (fn [content] ((comp kindly/hide-code kind/md) (str "## " content "\n---"))))
(def sub-question (fn [content] ((comp kindly/hide-code kind/md) (str "#### *" content "*"))))
(def sub-sub (fn [content] ((comp kindly/hide-code kind/md) (str "***" content "***"))))
(def answer (fn [content] (kind/md (str "> <span style=\"color: black; font-size: 1.5em;\">**" content "**</span>"))))
(def formula (comp kindly/hide-code kind/tex))


;; Calculator steps
(def calculator-buttons ["MENU" "2" "F5" "F5" "F1"])
(def calculator-descriptions ["Open main menu"
                              "Select STAT"
                              "Open DIST menu"
                              "Select BINM (Binomial)"
                              "Choose bpd (probability density)"])

(defn format-button [btn]
  (str "`" btn "`"))

(defn steps-table [{:keys [buttons descriptions]}]
  (let [steps (range 1 (inc (count buttons)))]
    (tc/dataset {:step        steps
                 :button      buttons
                 :description descriptions})))

(def calculator-steps
  (steps-table {:buttons      calculator-buttons
                :descriptions calculator-descriptions}))

(defn x->z
  "Converts a value x to its corresponding z-score.
   x: value to convert
   mu: mean of the distribution
   sigma: standard deviation of the distribution"
  [x mu sigma]
  (/ (- x mu) sigma))

(defn z->x
  "Converts a z-score back to its corresponding x value.
   z: z-score to convert
   mu: mean of the distribution
   sigma: standard deviation of the distribution"
  [z mu sigma]
  (+ (* z sigma) mu))


;; Binomial distribution
(defn ncr
  "Calculates the binomial coefficient (n choose k).
   n: total number of items
   k: number of items to choose"
  [n k]
  (/ (reduce * (range 1 (inc n)))
     (* (reduce * (range 1 (inc k)))
        (reduce * (range 1 (inc (- n k)))))))

(defn pmf-binomial
  "Calculates the probability mass function for a binomial distribution.
   k: number of successes
   n: number of trials
   p: probability of success on each trial"
  [k n p]
  (let [binom-coeff (ncr n k)]
    (* binom-coeff (Math/pow p k) (Math/pow (- 1 p) (- n k)))))

(defn cmf-binomial
  "Calculates the cumulative mass function for a binomial distribution.
   k: number of successes
   n: number of trials
   p: probability of success on each trial"
  [k n p]
  (let [probabilities (map #(pmf-binomial % n p) (range (inc k)))]
    (reduce + probabilities)))

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
          :=y :probability}))))

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
    [plot (md (str "Mean of samples: " (m/approx mean-value 4)))]))

;; Normal distribution
(defn rnorm [n mu sd]
  (let [dist (rand/distribution :normal {:mu mu :sd sd})]
    (repeatedly n #(rand/sample dist))))


(defn pnorm
  "Calculates the cumulative distribution function (CDF) of the standard normal distribution.
   z: z-score"
  [z]
  (rand/cdf (rand/distribution :normal) z))


(defn inv-normal
  "Calculates the inverse of the standard normal cumulative distribution function.
   p: probability (0 < p < 1)"
  [p]
  (rand/icdf (rand/distribution :normal) p))


(defn dnorm-clj
  "Calculates the probability density function (PDF) of the normal distribution.
     If given one argument:
       z: z-score (assumes standard normal distribution with mean 0 and sd 1)
     If given three arguments:
       x: value
     mu: mean of the distribution
     sd: standard deviation of the distribution"
  ([z]
   (rand/pdf (rand/distribution :normal) z))
  ([x mu sd]
   (rand/pdf (rand/distribution :normal {:mu mu :sd sd}) x)))


(comment
  (defn dnorm)
  "Calculates the probability density function (PDF) of the standard normal distribution.
   If given one argument:
     z: z-score (assumes standard normal distribution with mean 0 and sd 1)
   If given three arguments:
     x: value
     mu: mean of the distribution
     sd: standard deviation of the distribution"
  ([x mean sd]
   (let [coefficient (/ 1 (* sd (Math/sqrt (* 2 Math/PI))))
         exponent (/ (Math/pow (- x mean) 2) (* -2 (Math/pow sd 2)))]
     (* coefficient (Math/exp exponent))))
  ([z]
   (dnorm z 0 1)))

;; Plots
(defn normal-dist-viz [mu sd]
  (let [x-range (range (- mu (* 4 sd)) (+ mu (* 4 sd)) 0.1)
        norm-dist (rand/distribution :normal {:mu mu :sd sd})
        data (tc/dataset
              {:x x-range
               :y (map #(rand/pdf norm-dist %) x-range)})]
    (-> data
        (haclo/layer-area
         {:=x          :x
          :=y          :y
          :=mark-color "lightblue"}))))


(comment
  (defn left-boundary->x [mu sd left-area]
    (let [z-score (inv-normal left-area)
          x-value (z->x z-score mu sd)
          x-min (- mu (* 2.5 sd))
          x-max (+ mu (* 2.5 sd))
          x-range (range x-min x-max 1)
          norm-dist (r/distribution :normal {:mu mu :sd sd})
          data (tc/dataset
                {:x x-range
                 :y (map #(r/pdf norm-dist %) x-range)})]
      (-> data
          (haclo/layer-area
           {:=x            :x
            :=y            :y
            :=mark-color   "lightblue"
            :=mark-opacity 0.7})
          (haclo/layer-line
           {:=x          (m/approx x-value)
            :=mark-color "darkblue"}))))

  (left-boundary->x 980 40 0.1)

  (concat (range -4 0 0.1) [0]))


(defn norm-plot-threshold
  "Creates a ggplot2 plot for the temperature distribution."
  [threshold mu sd shade-direction]
  (let [z-score (/ (- threshold mu) sd)
        percentage (* 100 (if (= shade-direction :right)
                            (- 1 (pnorm z-score))
                            (pnorm z-score)))
        xlim [(- mu (* 5 sd)) (+ mu (* 5 sd))]]
    (-> (ggplot :data (tc/dataset {:x xlim}) (aes :x 'x))
        (r+ (stat_function :fun dnorm :args [mu sd]
                           :geom "area" :fill "lightblue" :alpha 0.7)
            (stat_function :fun dnorm :args [mu sd]
                           :xlim (if (= shade-direction :right)
                                   [threshold (second xlim)]
                                   [(first xlim) threshold])
                           :geom "area" :fill "red" :alpha 0.3)
            (geom_vline :xintercept threshold :color "red" :linetype "dashed")
            (geom_text :x threshold :y 0 :angle 90 :hjust -0.5 :vjust -0.5
                       :label (str "Threshold:" threshold))
            (labs :title "Distribution of X"
                  :subtitle (str "Percentage "
                                 (if (= shade-direction :right) "above" "below")
                                 " threshold: "
                                 (format "%.4f" percentage) "%")
                  :x "X"
                  :y "Density")
            (scale_x_continuous :limits xlim)
            (theme_minimal))
        plot->svg)))

(defn norm-plot-thresh-btw
  [lower-bound upper-bound mu sd]
  (let [z-score-lower (/ (- lower-bound mu) sd)
        z-score-upper (/ (- upper-bound mu) sd)
        percentage (* 100 (- (pnorm z-score-upper) (pnorm z-score-lower)))
        xlim [(- mu (* 5 sd)) (+ mu (* 5 sd))]]
    (-> (ggplot :data (tc/dataset {:x xlim}) (aes :x 'x))
        (r+ (stat_function :fun dnorm :args [mu sd]
                           :geom "area" :fill "lightblue" :alpha 0.7)
            (stat_function :fun dnorm :args [mu sd]
                           :xlim [lower-bound upper-bound]
                           :geom "area" :fill "red" :alpha 0.3)
            (geom_vline :xintercept lower-bound :color "red" :linetype "dashed")
            (geom_vline :xintercept upper-bound :color "red" :linetype "dashed")
            (geom_text :x lower-bound :y 0 :angle 90 :hjust -0.5 :vjust -0.5
                       :label (str "Lower: " lower-bound))
            (geom_text :x upper-bound :y 0 :angle 90 :hjust -0.5 :vjust -0.5
                       :label (str "Upper: " upper-bound))
            (labs :title "Distribution of X"
                  :subtitle (str "Percentage between thresholds: "
                                 (format "%.4f" percentage) "%")
                  :x "X"
                  :y "Density")
            (scale_x_continuous :limits xlim)
            (theme_minimal))
        plot->svg)))

(defn norm-plot-pval
  "Creates a ggplot2 plot for the temperature distribution with p-value shading."
  [p-val mean-temp sd-temp direction]
  (let [z-score (inv-normal (if (= direction :right) (- 1 p-val) p-val))
        threshold (z->x z-score mean-temp sd-temp)
        xlim [(- mean-temp (* 5 sd-temp)) (+ mean-temp (* 5 sd-temp))]]
    (-> (ggplot :data (tc/dataset {:x xlim}) (aes :x 'x))
        (r+ (stat_function :fun dnorm :args [mean-temp sd-temp]
                           :geom "area" :fill "lightblue" :alpha 0.7)
            (stat_function :fun dnorm :args [mean-temp sd-temp]
                           :xlim (if (= direction :right)
                                   [threshold (second xlim)]
                                   [(first xlim) threshold])
                           :geom "area" :fill "red" :alpha 0.3)
            (geom_vline :xintercept threshold :color "red" :linetype "dashed")
            (geom_text :x threshold :y 0 :angle 90 :hjust -0.5 :vjust -0.5
                       :label (str "Threshold: " (format "%.2f" threshold)))
            (labs :title "Distribution of X"
                  :subtitle (str "Percent of data in red: " (* 100 p-val) "%")
                  :x "X"
                  :y "Density")
            (scale_x_continuous :limits xlim)
            (theme_minimal))
        plot->svg)))

(comment
  (defn create-temperature-plot)
  "Creates a ggplot2 plot for the temperature distribution."
  [plot-data threshold mean-temp sd-temp shade-direction]
  (let [z-score (/ (- threshold mean-temp) sd-temp)
        percentage (* 100 (if (= shade-direction :right)
                            (- 1 (pnorm z-score))
                            (pnorm z-score)))
        shaded-data (tc/select-rows plot-data
                                    (if (= shade-direction :right)
                                      #(>= (:temperature %) threshold)
                                      #(<= (:temperature %) threshold)))]
    (-> (ggplot :data plot-data (aes :x 'temperature :y 'density))
        (r+ (geom_area :fill "lightblue" :alpha 0.7)
            (geom_area :data shaded-data
                       :mapping (aes :x 'temperature :y 'density)
                       :fill "red" :alpha 0.3)
            (geom_vline :xintercept threshold :color "red" :linetype "dashed")
            (geom_text :x threshold :y 0 :angle 90 :hjust -0.5 :vjust -0.5
                       :label (str "Threshold (" threshold "°F)"))
            (labs :title "Distribution of Temperatures"
                  :subtitle (str "Percentage "
                                 (if (= shade-direction :right) "above" "below")
                                 " threshold: "
                                 (format "%.4f" percentage) "%")
                  :x "Temperature (°F)"
                  :y "Density")
            (theme_minimal)))))
