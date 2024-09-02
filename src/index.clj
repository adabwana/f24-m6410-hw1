^:kindly/hide-code
(ns index
  (:require 
       [assignments.hw1.utils :refer [question answer md]]
       [scicloj.kindly.v4.kind :as kind])
  (:import [java.time LocalDate]
           [java.time.format DateTimeFormatter]))

(let [formatter (DateTimeFormatter/ofPattern "M/d/yy")
      current-date (str (.format (LocalDate/now) formatter))]
  (md (str "

### Jaryt Salvo
**Date:** **" current-date "**

**Fall 2024 | MATH6410 Probability Theory**

*************

This project contains solutions to Homework 1 from the Probability Theory course (Math 6410) using Clojure. The code provided here demonstrates a functional programming approach to solving various probability problems, including binomial distributions, normal distributions, and calculations involving expected values and standard deviations.

The code is organized into different sections corresponding to each homework problem, with detailed explanations of the logic and mathematical steps involved. By using Clojure and its associated libraries, such as `scicloj.clay` for rendering, `tablecloth` for data manipulation, and `fastmath` for mathematical operations.

#### Utils

The `utils.clj` file contains various utility functions and helpers used throughout the homework solutions. It includes:

- Formatting functions for markdown and LaTeX rendering
- Statistical functions for binomial and normal distributions
- Visualization functions for creating plots
- Helper functions for calculations and data manipulation

These utilities are designed to streamline the problem-solving process and provide reusable components for statistical computations and visualizations.

The code in the `src/assignments` folder was rendered with [Clay](https://scicloj.github.io/clay/) and deployed with [Github Pages](https://pages.github.com/).

**Note:** Plots rendered from `ggplots` are not displayed correctly. The first `ggplot` per question displays nicely. The others, alas.")))

