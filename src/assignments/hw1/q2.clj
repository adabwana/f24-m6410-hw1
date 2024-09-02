(ns assignments.hw1.q2
  (:require
   [assignments.hw1.utils :refer :all]
   [fastmath.core :as m]
   [fastmath.random :as rand]
   [scicloj.hanamicloth.v1.api :as haclo]
   [tablecloth.api :as tc]))

(question
 "Question 2")
(sub-question
 "2) The length of a rattlesnake is normally distributed with a mean of 42 inches and a standard deviation of 2 inches.")

(md "We can visualize the Normal Distribution with the following function:")

(comment
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
            :=mark-color "lightblue"})))))

(normal-dist-viz 42 2)

(sub-sub
 "a) Find and interpret the chance that the length of a randomly selected rattlesnake is longer than 42 inches.")

(md "The `x->z` function converts a value from a normal distribution to a standard normal distribution:

$$z = \\frac{x - \\mu}{\\sigma}$$

This is used to find probabilities for normally distributed variables.")


(comment
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
          plot->svg))))

(let [mu 42 sd 2 x 42]
  (norm-plot-threshold x mu sd :right))


(let [mu 42 sd 2 x 42
      z (x->z x mu sd)
      prob (pnorm z)
      p (- 1 prob)]
  (answer
   (str "Probability that the length of a randomly selected rattlesnake is longer than 42 inches: "
        (* 100 (m/approx p 4)) "%")))


(sub-sub
 "b) Find and interpret the chance that the length of a randomly selected rattlesnake is between 30 and 40 inches.")

(md "The `cdf-normal` function calculates the cumulative distribution function for a standard normal distribution:

$$\\Phi(z) = \\frac{1}{\\sqrt{2\\pi}} \\int_{-\\infty}^z e^{-t^2/2} dt$$

This is used to find probabilities for normally distributed variables.")


(comment
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
          plot->svg))))

(let [x1 30 x2 40 mu 42 sd 2]
  (norm-plot-thresh-btw x1 x2 mu sd))


(let [x1 30 x2 40
      z1 (x->z x1 42 2) z2 (x->z x2 42 2)
      p1 (pnorm z1) p2 (pnorm z2)
      p (- p2 p1)]
  (answer
   (str
    "Probability that the length of a randomly selected rattlesnake is between 30 and 40 inches: "
    (* 100 (m/approx p 3)) "%")))


(sub-sub
 "c) Find and interpret the chance that the length of a randomly selected rattlesnake is longer than 52 inches.")


(let [x 52 mu 42 sd 2]
  (norm-plot-threshold x mu sd :right))


(let [x 52
      z (x->z x 42 2)
      prob (pnorm z)
      p (- 1 prob)]
  (answer
   (str
    "Probability that the length of a randomly selected rattlesnake is longer than 52 inches: "
    (* 100 (m/approx p)) "%")))
