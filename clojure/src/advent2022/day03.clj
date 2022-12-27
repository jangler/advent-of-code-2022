(ns advent2022.day03
  (:require [advent2022.day01 :refer [slurp-lines]]
            [clojure.set :as set]))

(defn rucksack-common-item [s]
  (let [[first-half second-half] (split-at (/ (count s) 2) s)]
    (first (filter #(some #{%} second-half) first-half))))

(defn priority [c]
  (cond (re-find #"[a-z]" (str c)) (+ 1 (- (int c) (int \a)))
        (re-find #"[A-Z]" (str c)) (+ 27 (- (int c) (int \A)))))

(defn solve-part-one [input]
  (->> input
       (map #(-> % rucksack-common-item priority))
       (reduce +)))

(defn group-common-item [xs]
  (first (apply set/intersection (map set xs))))

(defn solve-part-two [input]
  (->> input
       (partition 3)
       (map #(-> % group-common-item priority))
       (reduce +)))

(comment
  (def example-input (slurp-lines "../examples/day03.txt"))
  (def real-input (slurp-lines "../input/day03.txt"))

  (solve-part-one example-input)
  (solve-part-one real-input)

  (solve-part-two example-input)
  (solve-part-two real-input)
  :rcf)
