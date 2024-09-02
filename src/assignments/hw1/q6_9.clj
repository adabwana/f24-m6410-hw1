(ns assignments.hw1.q6-9
  (:require
   [assignments.hw1.utils :refer :all]
   [fastmath.core :as m]))

(question
 "Question 6 to 9")
(question
 "Question 6")
(sub-question
 "6) In Question 5, find the face value of the bond so that there is 90% chance that the price of the bound is more than this face value after one year.")


(let [mu 980 sd 40 p 0.9]
  (norm-plot-pval p mu sd :right))


(let [mu 980 sd 40 p 0.9
      p-minus-1 (- 1 p)
      z (inv-normal p-minus-1)
      x (z->x z mu sd)]
  (answer
   (str "Face value of the bond: " (m/approx x))))

(question
 "Question 7")
(sub-question
 "7) In Question 5, find the face value of the bond so that there is 50% chance that the price of the bound is more than this face value after one year. Interpret your answer.")


(let [mu 980 sd 40 p 0.5]
  (norm-plot-pval p mu sd :right))

(let [mu 980 sd 40 p 0.5
      p-minus-1 (- 1 p)
      z (inv-normal p-minus-1)
      x (z->x z mu sd)]
  (answer
   (str "Face value of the bond: " (m/approx x))))

(question
 "Question 8")
(sub-question
 "8) Annual benefit costs for career service employees at the University of Pittsburgh are approximately normal distributed with a mean of $28,600 and a standard deviation $2,700 in 1998.")
(sub-sub
 "a) Find the probability that a career service employee chosen at random has an annual benefit cost less than $20,000")


(let [mu 28600 sd 2700 x 20000]
  (norm-plot-threshold x mu sd :left))


(let [mu 28600 sd 2700 x 20000
      z (x->z x mu sd)
      prob (pnorm z)
      p prob]
  (answer
   (str "Probability that a career service employee chosen at random has an annual benefit cost less than $20,000: "
        (* 100 (m/approx p)) "%")))


(sub-sub
 "b) Find the probability that a career service employee chosen at random has an annual benefit cost more than $27,000.")


(let [mu 28600 sd 2700 x 27000]
  (norm-plot-threshold x mu sd :right))


(let [mu 28600 sd 2700 x 27000
      z (x->z x mu sd)
      prob (pnorm z)
      p (- 1 prob)]
  (answer
   (str "Probability that a career service employee chosen at random has an annual benefit cost more than $27,000: "
        (* 100 (m/approx p)) "%")))

(question
 "Question 9")
(sub-question
 "9) In Question 8, find the top 5 percent benefit cost.")


(let [mu 28600 sd 2700 p 0.05]
  (norm-plot-pval p mu sd :right))

(let [mu 28600 sd 2700 p 0.05
      p-minus-1 (- 1 p)
      z (inv-normal p-minus-1)
      x (z->x z mu sd)]
  (answer
   (str "Top 5 percent benefit cost: " (m/approx x))))

