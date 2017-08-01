(ns dev.lab.sdemo.score-service-test
  (:use [clojure.test])
  (:require [clojure.spec.alpha :as s]
            [dev.lab.sdemo.util :as u]
            [clojure.spec.gen.alpha :as g]
            [clojure.spec.test.alpha :as t]
            [clojure.test.check.clojure-test :as ct]
            [clojure.test.check.properties :as prop]
            [clojure.test.check.generators :as gen])
  (:import [dev.lab.sdemo Person ScoreService]))


(s/def :person/fname string?)
(s/def :person/lname string?)
(s/def :person/birthDate inst?)

(s/def :address/roadName string?)
(s/def :address/houseNo string?)
(s/def :address/postCode int?)
(s/def :address/city string?)
(s/def :address/country string?)

(s/def ::address (s/keys :req-un [:address/roadName
                                  :address/houseNo
                                  :address/postCode
                                  :address/city
                                  :address/country]))

(s/def ::person (s/keys :req-un [:person/fname
                                 :person/lname
                                 :person/birthDate]
                        :opt-un [::address]))

(s/def ::scoreType #{"HIGH" "LOW" "MEDIUM"})
(s/def :score/value int?)
(s/def ::score (s/keys :req-un [::scoreType]))

(s/fdef get-score
        :args (s/cat :v ::person)
        :ret ::score)


(defn get-score [v]
  (->> (u/to-jtype Person v)
       (.getScore (ScoreService.))
       (u/from-jtype)))


(ct/defspec get-score-test
            10
            (prop/for-all [v (s/gen ::person)]
                          (let [w (get-score v)]
                            (not= nil? w))))


(deftest fail-test
  (testing "Failing test "
    (is (= 1 1))))



