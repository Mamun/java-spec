# java-spec
testing java application with clojure spec

## Java data model and service

```java
public class Person{
  private String fname;
  private String lname;
  private Date birthDate;
  private Address address;
  //....
}

public class Address{
    private String roadName;
    private String houseNo;
    private String postCode;
    private String city;
    private String country;
  //....
}

public class Score{
  private Score value;
  //setter and getter method here
}

public class ScoreService {
    public Score getScore(Person person){
        //do business logic
        Score score = new Score();
        score.setScoreType(Score.ScoreType.HIGH);
        return score;
    }
}
```

## Clojure spec for java data model
```clj
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
```
## Generate Sample data

```clj
(g/sample (s/gen ::person) 2)
;;Sample data
({:fname "", :lname "", :birthDate #inst"1969-12-31T23:59:59.999-00:00"}
 {:fname "k",
  :lname "",
  :birthDate #inst"1970-01-01T00:00:00.000-00:00",
:address {:roadName "O", :houseNo "R", :postCode -1, :city "0", :country "4"}})
```

## Run maven test 
```clj
mvn clojure:test

[INFO] --- clojure-maven-plugin:1.8.1:test (default-cli) @ sdemo ---

Testing dev.lab.sdemo.util

Ran 0 tests containing 0 assertions.
0 failures, 0 errors.

Testing dev.lab.sdemo.score-service-test
{:result true, :num-tests 10, :seed 1501674877185, :test-var "get-score-test"}

Ran 1 tests containing 1 assertions.
0 failures, 0 errors.
Tests run: , Assertions: 1, Failures: 0, Errors: 0
```
