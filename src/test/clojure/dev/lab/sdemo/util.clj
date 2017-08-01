(ns dev.lab.sdemo.util
  (:require [cheshire.core :as c])
  (:import [com.google.gson Gson]))


(def gson (Gson.))

(defn from-json [v t]
  (.fromJson gson v t))

(defn to-json [v]
  (.toJson gson v))

(defn to-jtype [t v]
  (-> v
      (c/generate-string)
      (from-json t)))

(defn from-jtype [v]
  (-> v
      (to-json)
      (c/decode)))

