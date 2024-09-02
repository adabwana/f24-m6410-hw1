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
  (build))
