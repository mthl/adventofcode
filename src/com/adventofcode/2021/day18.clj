(ns com.adventofcode.2021.day18
  (:require
   [clojure.edn :as edn]))

(defn parse-pairs
  [lines]
  (mapv edn/read-string lines))

(defn- height
  [pair]
  (if-not (vector? pair)
    0
    (inc (max (-> pair first height)
              (-> pair second height)))))

(defn explode?
  [pair]
  (> (height pair) 4))

(defn- regular-numbers
  [pair]
  (if-not (vector? pair)
    [pair]
    (concat (regular-numbers (first pair))
            (regular-numbers (second pair)))))

(defn split?
  [pair]
  (let [nums (regular-numbers pair)]
    (boolean (some #(> % 9) nums))))

(defn- add-left
  [pair n]
  (if (number? pair)
    (+ pair n)
    (let [[l r] pair]
      [(add-left l n) r])))

(defn- add-right
  [pair n]
  (if (number? pair)
    (+ pair n)
    (let [[l r] pair]
      [l (add-right r n)])))

(defn no-meta
  [pair]
  (if (number? pair)
    pair
    (with-meta pair nil)))

(defn explode
  ([pair]
   (no-meta (explode pair 1)))
  ([pair h]
   (cond
     (number? pair) pair

     (< h 4) (let [[l r] pair
                   l* (explode l (inc h))]
               (cond
                 (or (contains? (meta l*) ::add-left)
                     (contains? (meta l*) ::added))
                 (with-meta [(no-meta l*) r] (meta l*))

                 (contains? (meta l*) ::add-right)
                 (with-meta [(no-meta l*)
                             (add-left r (-> l* meta ::add-right))]
                   {::added true})

                 :else
                 (let [r* (explode r (inc h))]
                   (cond
                     (or (contains? (meta r*) ::add-right)
                         (contains? (meta r*) ::added))
                     (with-meta [l* (no-meta r*)] (meta r*))
                     
                     (contains? (meta r*) ::add-left)
                     (with-meta [(add-right l* (-> r* meta ::add-left))
                                 (no-meta r*)]
                       {::added true})

                     :else
                     [(no-meta l*) (no-meta r*)]))))

     (vector? (first pair)) (let [[[l r] rr] pair]
                              (with-meta [0 (add-left rr r)] {::add-left l}))

     (vector? (second pair)) (let [[ll [l r]] pair]
                               (with-meta [(add-right ll l) 0] {::add-right r}))

     :else pair)))

(defn split
  [pair]
  (cond
    (vector? pair) (let [[l r] pair
                         l* (split l)]
                     (if (-> l* meta ::splitted?)
                       (with-meta [(no-meta l*) r] (meta l*))
                       (let [r* (split r)]
                         (if (-> r* meta ::splitted?)
                           (with-meta [l (no-meta r*)] (meta r*))
                           [(no-meta l) (no-meta r*)]))))

    (<= pair 9) pair

    :else (let [middle (/ pair 2)]
            (with-meta [(int (Math/floor middle))
                        (int (Math/ceil middle))]
              {::splitted? true}))))

(defn- reduce-pair
  [pair]
  (cond
    (explode? pair) (recur (no-meta (explode pair)))
    (split? pair) (recur (no-meta (split pair)))
    :else pair))

(defn add
  [a b]
  (reduce-pair [a b]))

(defn sum
  [pairs]
  (reduce add (first pairs) (rest pairs)))

(defn magnitude
  [pair]
  (if (number? pair)
    pair
    (let [[l r] pair]
      (+ (* 3 (magnitude l))
         (* 2 (magnitude r))))))

(defn largest-magnitude
  [pairs]
  (->> (for [a pairs
             b pairs]
         (magnitude (add a b)))
       (reduce max Long/MIN_VALUE)))
