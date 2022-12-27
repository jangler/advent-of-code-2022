(ns advent2022.day07
  (:require [advent2022.day01 :refer [slurp-lines]]
            [clojure.string :as str]))

(defn group-commands
  ([lines] (group-commands lines []))
  ([lines acc]
   (if (empty? lines)
     acc
     (let [cmd (first lines)
           [output remainder] (split-with #(not (str/starts-with? % "$"))
                                          (rest lines))]
       (group-commands remainder (conj acc (cons cmd output)))))))

(defn cd-root [state _output]
  (assoc state :cwd []))

(defn cd-up [state _output]
  (update state :cwd #(vec (drop-last 1 %))))

(defn cd [path state _output]
  (update state :cwd #(conj % path)))

(defn ls [state output]
  (let [parse-file (fn [line]
                     (let [[size name] (str/split line #" ")]
                       [(if (= size "dir")
                           0
                           (Integer/parseInt size))
                        name]))
        files (map parse-file output)]
    (assoc-in state [:tree (:cwd state)] files)))

(defn func-for-cmd [cmd]
  (cond (= cmd "$ cd /") cd-root
        (= cmd "$ cd ..") cd-up
        (str/starts-with? cmd "$ cd ") (partial cd (subs cmd 5))
        (= cmd "$ ls") ls
        :else (throw (Exception. (str "unknown command: " cmd)))))

(defn tree
  ([cmds] (tree cmds {}))
  ([cmds state]
   (if (empty? cmds)
     (:tree state)
     (let [cmd (ffirst cmds)
           output (rest (first cmds))
           func (func-for-cmd cmd)]
       (tree (rest cmds) (func state output))))))

(defn dir-size [tree dir]
  (->> (map vec tree)
       (filter (fn [[k _]]
                 (= dir (take (count dir) k))))
       (mapcat second)
       (map first)
       (reduce +)))

(defn dir-sizes [tree]
  (into {} (map #(vector %1 (dir-size tree %1)) (keys tree))))

(defn solve-part-one [input]
  (->> input group-commands tree dir-sizes
       (filter (fn [[_ v]] (<= v 100000)))
       (map second)
       (reduce +)))

(defn solve-part-two [input]
  (let [sizes (->> input group-commands tree dir-sizes)
        occupied-space (get sizes [])
        free-space (- 70000000 occupied-space)
        required-space (- 30000000 free-space)]
    (->> sizes
         (map second)
         (filter #(>= % required-space))
         sort
         first)))

(comment
  (def example (slurp-lines "../examples/day07.txt"))
  (def input (slurp-lines "../input/day07.txt"))
  
  (solve-part-one example)
  (solve-part-one input)
  
  (solve-part-two example)
  (solve-part-two input)
  :rcf)
