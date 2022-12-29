(ns advent2022.day08
  (:require [advent2022.day01 :refer [slurp-lines]]))

(defn parse [input]
  (let [parse-digit (fn [c]
                      (- (int c) (int \0)))
        parse-line (fn [line]
                     (vec (map parse-digit line)))]
    (vec (map parse-line input))))

(defn nth-column [n grid]
  (mapv #(nth % n) grid))

(defn lines-to-edge [grid x y]
  (let [row (nth grid y)
        col (nth-column x grid)]
    [(reverse (take y col))
     (drop (inc y) col)
     (drop (inc x) row)
     (reverse (take x row))]))

(defn visible [grid x y]
  (let [lines (lines-to-edge grid x y)
        tree (nth (nth grid y) x)]
    (some (fn [line]
            (every? #(< % tree) line))
          lines)))

(defn indices [grid]
  (for [x (range (count (first grid)))
        y (range (count grid))]
    [x y]))

(defn solve-part-one [input]
  (let [grid (parse input)]
    (->> (indices grid)
         (filter (fn [[x y]]
                   (visible grid x y)))
         count)))

(defn viewing-distance [tree line]
  (let [index (->> line
                   (map-indexed vector)
                   (filter (fn [[_ x]]
                             (>= x tree)))
                   ffirst)]
    (if (nil? index)
      (count line)
      (inc index))))

(defn scenic-score [grid x y]
  (let [tree (nth (nth grid y) x)]
    (->> (lines-to-edge grid x y)
         (map (partial viewing-distance tree))
         (reduce *))))

(defn solve-part-two [input]
  (let [grid (parse input)]
    (->> (indices grid)
         (map (fn [[x y]]
                   (scenic-score grid x y)))
         (reduce max))))

(comment
  (def example (slurp-lines "../examples/day08.txt"))
  (def input (slurp-lines "../input/day08.txt"))
  
  (solve-part-one example)
  (solve-part-one input)
  
  (solve-part-two example)
  (solve-part-two input)
  :rcf)
