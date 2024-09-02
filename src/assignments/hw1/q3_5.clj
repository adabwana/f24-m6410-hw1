(ns assignments.hw1.q3-5
  (:require
    [assignments.hw1.utils :refer :all]
    [fastmath.core :as m]
    [fastmath.random :as rand]))


(question
  "Question 3 to 5")
(sub-question
  "3) Assume that healthy human body temperatures are normally distributed with a mean of 98.02 degrees Fahrenheit and a standard deviation of 0.62 degrees Fahrenheit. A hospital uses 101 degrees Fahrenheit as the lowest body temperature (laser body temperature reading) criterion of having a fever. What percentage of healthy persons would be misclassified as having a fever with this criterion? Explain.")


(let [threshold 101 mean-temp 98.02 sd-temp 0.62]
  (norm-plot-threshold threshold mean-temp sd-temp :right))


(let [mu 98.02 sd 0.62 x 101
      z (x->z x mu sd)
      prob (pnorm z)
      p (- 1 prob)]
  (answer
    (str "Percentage of healthy persons misclassified as having a fever: "
         (* 100 (m/approx p)) "%")))


(question
  "Question 4")
(sub-question
  "4) In Question 3, if a physician wants to select a minimum temperature with a rate of misclassifying healthy patients at 10%. What should that criterion be?")

(md "The `inv-normal` function calculates the inverse cumulative distribution function for a standard normal distribution:

$$\\Phi^{-1}(p) = \\mu + \\sigma z$$

This is used to find the value corresponding to a given probability in a normal distribution.")

(md "The `z->x` function converts a value from a standard normal distribution to a normal distribution:

$$x = \\mu + \\sigma z$$

This is used to find the value corresponding to a given probability in a normal distribution.")

(let [mu 98.02 sd 0.62 p-val 0.1]
  (norm-plot-pval p-val mu sd :right))


(defn rnorm [n mu sd]
  (let [dist (rand/distribution :normal {:mu mu :sd sd})]
    (repeatedly n #(rand/sample dist))))


(let [mu 98.02 sd 0.62 p 0.1 one-minus-p (- 1 p)
      z (inv-normal one-minus-p)
      x (z->x z mu sd)
      samples (rnorm 1000 mu sd)]
  (answer
    (str "Minimum temperature with a rate of misclassifying healthy patients at 10%: "
         (m/approx x))))


(question
  "Question 5")
(sub-question
  "5) A financial analyst states that the price of a long-term $1000 government bond (one year after purchasing) is normally distributed with an expected value $980 and a standard deviation $40.")
(sub-sub
  "a) Find the chance that the price is more than $1000 one year after purchasing. ")

(let [mu 980 sd 40 x 1000]
  (norm-plot-threshold x mu sd :right))


(let [mu 980 sd 40 x 1000
      z (x->z x mu sd)
      prob (pnorm z)
      p (- 1 prob)]
  (answer
    (str "Probability that the price is more than $1000 one year after purchasing: "
         (* 100 (m/approx p 4)) "%")))


(sub-sub
  "b) What is the chance that the price is between $960 and $1060 after purchase for one year?")

(let [mu 980 sd 40 x1 960 x2 1060]
  (norm-plot-thresh-btw x1 x2 mu sd))

(let [mu 980 sd 40 x1 960 x2 1060 z1 (x->z x1 mu sd)
      z2 (x->z x2 mu sd) p1 (pnorm z1) p2 (pnorm z2) p (- p2 p1)]
  (answer
    (str "Probability that the price is between $960 and $1060 after purchase for one year: "
         (* 100 (m/approx p 3)) "%")))
