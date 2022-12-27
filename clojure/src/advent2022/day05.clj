(ns advent2022.day05
  (:require [advent2022.day01 :refer [slurp-lines]]
            [clojure.string :as str]))

(defn parse-crates [lines]
  (let [crate-sets (map #(map second (partition-all 4 %)) lines)
        stack-count (apply max (map count crate-sets))]
    (map (fn [i]
           (->> crate-sets
                (map #(nth % i))
                (filter #(not= \space %))))
         (range stack-count))))

(defn parse-move [line]
  (let [matches (re-matches #"move (\d+) from (\d+) to (\d+)" line)
        group-as-int #(Integer/parseInt (matches %))]
    {:count (group-as-int 1)
     :src (dec (group-as-int 2))
     :dst (dec (group-as-int 3))}))

(defn parse-input [lines]
  (let [crate-lines (drop-last 1 (take-while seq lines))
        move-lines (drop (+ 2 (count crate-lines)) lines)]
    {:stacks (parse-crates crate-lines)
     :moves (map parse-move move-lines)}))

(defn rearrange [move stacks crate-mover-9001?]
  (let [removed-crates (take (:count move) (nth stacks (:src move)))
        crates-to-place ((if crate-mover-9001? identity reverse)
                         removed-crates)]
    (map-indexed (fn [i stack]
                   (cond (= i (:src move)) (drop (:count move) stack)
                         (= i (:dst move)) (concat crates-to-place stack)
                         :else stack))
                 stacks)))

(defn solve [path crate-mover-9001?]
  (let [input (parse-input (slurp-lines path))]
    (->> (reduce (fn [stacks move]
                   (rearrange move stacks crate-mover-9001?))
                 (:stacks input)
                 (:moves input))
         (map first)
         str/join)))

(comment
  (def example-path "../examples/day05.txt")
  (def input-path "../input/day05.txt")

  ;; part one
  (solve example-path false)
  (solve input-path false)

  ;; part two
  (solve example-path true)
  (solve input-path true)
  :rcf)
