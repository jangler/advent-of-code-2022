(ns advent2022.day02
  (:require [advent2022.day01 :refer [slurp-lines]]))

(defn load-input [f]
  (->> (slurp-lines f)
       (map (fn [x]
              [(first x) (nth x 2)]))))

(def opponent-hand-key {\A :rock, \B :paper, \C :scissors})
(def your-hand-key {\X :rock, \Y :paper, \Z :scissors})

(defn decode [xyz-key round]
  [(opponent-hand-key (first round))
   (xyz-key (second round))])

(defn outcome [round]
  (let [winning-rounds [[:rock :paper] [:paper :scissors] [:scissors :rock]]]
    (cond (some #{round} winning-rounds) :win
          (= round (reverse round)) :draw
          :else :lose)))

(def hand-scores {:rock 1, :paper 2, :scissors 3})
(def outcome-scores {:lose 0, :draw 3, :win 6})

(defn score [round]
  (+ ((outcome round) outcome-scores)
     ((second round) hand-scores)))

(defn solve-part-one [input]
  (->> input
       (map #(->> % (decode your-hand-key) score))
       (reduce +)))

(def outcome-key {\X :lose, \Y :draw, \Z :win})

(defn convert [[opp-hand req-outcome]]
  [opp-hand
   (first (filter #(= req-outcome
                      (outcome [opp-hand %]))
                  [:rock :paper :scissors]))])

(defn solve-part-two [input]
  (->> input
       (map #(->> % (decode outcome-key) convert score))
       (reduce +)))

(comment
  (def example-input (load-input "../examples/day02.txt"))
  (def real-input (load-input "../input/day02.txt"))

  (solve-part-one example-input)
  (solve-part-one real-input)

  (solve-part-two example-input)
  (solve-part-two real-input)
  :rcf)
