(ns advent2022.day01
  (:require [clojure.java.io :as io]))

(defn slurp-lines [f]
  (with-open [rdr (io/reader f)]
    (vec (line-seq rdr))))

(defn partition-when
  "Partitions a collection by elements matching a predicate.
   Matching elements are dropped from the result."
  ([pred coll] (partition-when pred coll nil))
  ([pred coll acc]
   (if (empty? coll)
     acc
     (let [[taken dropped] (split-with (complement pred) coll)]
       (partition-when pred (rest dropped) (cons taken acc))))))

(defn load-input [f]
  (let [parse-int #(Integer/parseInt %)]
    (->> (slurp-lines f)
         (partition-when empty?)
         (map #(map parse-int %)))))

(defn solve-part-one [input]
  (->> input
       (map #(reduce + %))
       (apply max)))

(defn solve-part-two [input]
  (->> input
       (map #(reduce + %))
       sort
       (take-last 3)
       (reduce +)))

(comment
  (def example-input (load-input "../examples/day01.txt"))
  (def real-input (load-input "../input/day01.txt"))
  
  (solve-part-one example-input)
  (solve-part-one real-input)
  
  (solve-part-two example-input)
  (solve-part-two real-input)
  :rcf)
