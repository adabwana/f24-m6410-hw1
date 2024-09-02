(ns assignments.hw1.q2
  (:require
    [assignments.hw1.utils :refer :all]
    [fastmath.core :as m]
    [fastmath.random :as r]
    [scicloj.hanamicloth.v1.api :as haclo]
    [tablecloth.api :as tc]))

(question
  "Question 2")
(sub-question
  "2) The length of a rattlesnake is normally distributed with a mean of 42 inches and a standard deviation of 2 inches.")

(md "We can visualize the Normal Distribution with the following function:")

(defn normal-dist-viz [mu sd]
  (let [x-range (range (- mu (* 4 sd)) (+ mu (* 4 sd)) 0.1)
        norm-dist (r/distribution :normal {:mu mu :sd sd})
        data (tc/dataset
               {:x x-range
                :y (map #(r/pdf norm-dist %) x-range)})]
    (-> data
        (haclo/layer-area
          {:=x          :x
           :=y          :y
           :=mark-color "lightblue"}))))

(normal-dist-viz 42 2)

(sub-sub
  "a) Find and interpret the chance that the length of a randomly selected rattlesnake is longer than 42 inches.")

(md "The `x->z` function converts a value from a normal distribution to a standard normal distribution:

$$z = \\frac{x - \\mu}{\\sigma}$$

This is used to find probabilities for normally distributed variables.")

(let [mu 42 sd 2 x 42
      z (x->z x mu sd)
      prob (pnorm z)
      p (- 1 prob)]
  (answer
    (str "Probability that the length of a randomly selected rattlesnake is longer than 42 inches: "
         (* 100 (m/approx p 4)) "%")))


(let [mu 42 sd 2 x 42]
  (norm-plot-threshold x mu sd :right))

(sub-sub
  "b) Find and interpret the chance that the length of a randomly selected rattlesnake is between 30 and 40 inches.")

(md "The `cdf-normal` function calculates the cumulative distribution function for a standard normal distribution:

$$\\Phi(z) = \\frac{1}{\\sqrt{2\\pi}} \\int_{-\\infty}^z e^{-t^2/2} dt$$

This is used to find probabilities for normally distributed variables.")

(let [x1 30 x2 40
      z1 (x->z x1 42 2) z2 (x->z x2 42 2)
      p1 (pnorm z1) p2 (pnorm z2)
      p (- p2 p1)]
  (answer
    (str
       "Probability that the length of a randomly selected rattlesnake is between 30 and 40 inches: "
       (* 100 (m/approx p 3)) "%")))

(let [x1 30 x2 40 mu 42 sd 2]
  (norm-plot-thresh-btw x1 x2 mu sd))

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
