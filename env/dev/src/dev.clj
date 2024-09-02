(ns dev
  (:require [scicloj.clay.v2.api :as clay]))

(defn build []
  (clay/make!
   {:format              [:quarto :html]
    :book                {:title "Homework 1"}
    :subdirs-to-sync     ["notebooks" "data"]
    :source-path         ["src/index.clj"
                          "src/assignments/hw1/utils.clj"
                          "src/assignments/hw1/q1.clj"
                          "src/assignments/hw1/q2.clj"
                          "src/assignments/hw1/q3_5.clj"
                          "src/assignments/hw1/q6_9.clj"]
    :base-target-path    "docs"
    :clean-up-target-dir true}))

(comment
  ;with index.md clay wont find in src and complains about docs/_book
  (build))


(comment
  (defn build []                                              ;for clay 63
    (clay/make!
      {:format              [:quarto :html]
       :book                {:title "Support Vector Machine"}
       :base-source-path    "src"
       :base-target-path    "docs"                            ;default
       :subdirs-to-sync     ["notebooks" "data"]
       :clean-up-target-dir true
       :source-path         [
                             ;"index.clj"                      ;index.md
                             "assignment/problem9-5.ipynb"]})))


