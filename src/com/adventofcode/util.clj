(ns com.adventofcode.util
  (:require
   [clojure.java.io :as io]))

(defn resources-lines
  "Construct a sequence of the lines contained in a resource file."
  [n]
  (with-open [f (-> n io/resource io/file io/reader)]
    (doall (line-seq f))))
