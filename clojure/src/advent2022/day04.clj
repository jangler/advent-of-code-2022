(ns advent2022.day04
  (:require [advent2022.day01 :refer [slurp-lines]]
            [clojure.set :as set]))

(defn parse [line] 
  (let [matches (re-seq #"\d+" line)
        nth-int #(Integer/parseInt (nth matches %))]
    [[(nth-int 0) (nth-int 1)]
     [(nth-int 2) (nth-int 3)]]))

(defn full-overlap? [a b]
  (let [fully-contains? (fn [x y]
                          (and (<= (first x) (first y))
                               (>= (second x) (second y))))]
    (or (fully-contains? a b)
        (fully-contains? b a))))

(defn solve-part-one [lines]
  (->> lines
       (map parse)
       (filter #(apply full-overlap? %))
       count))

(defn partial-overlap? [a b]
  (let [assignment-set (fn [[start end]]
                         (set (range start (inc end))))]
    (seq (set/intersection (assignment-set a) (assignment-set b)))))

(defn solve-part-two [lines]
  (->> lines
       (map parse)
       (filter #(apply partial-overlap? %))
       count))

(comment
  (def example-lines (slurp-lines "../examples/day04.txt"))
  (def real-lines (slurp-lines "../input/day04.txt"))

  (solve-part-one example-lines)
  (solve-part-one real-lines)

  (solve-part-two example-lines)
  (solve-part-two real-lines)
  :rcf)
