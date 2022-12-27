(ns advent2022.day06
  (:require [advent2022.day01 :refer [slurp-lines]]))
  
(defn marker-index [chars buffer]
  (->> (partition chars 1 buffer)
       (map-indexed vector)
       (filter (fn [[_ v]]
                 (apply distinct? v)))
       (ffirst)
       (+ chars)))
 
(def packet-index (partial marker-index 4))
(def message-index (partial marker-index 14))

(comment
  (def examples (slurp-lines "../examples/day06.txt"))
  (def input (slurp "../input/day06.txt"))
  
  ;; part one
  (map packet-index examples)
  (packet-index input)
  
  ;; part two
  (map message-index examples)
  (message-index input)
  :rcf)
