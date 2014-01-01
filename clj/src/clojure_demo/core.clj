; what's going on here?  We have a namespace "clojure-demo.core", note that - turns into _ in actual file names
; words that start with a : are keywords, they are similar stand ins for things like magic numbers, enums, etc in
; other languages

(ns clojure-demo.core
  (:require [clojure.pprint :refer [pprint]]  ; a great readability tool
            [clojure.repl :refer :all] ; tools that make your life in a repl easier, probably worth removing
                                       ; in production code
            ))


; While there are many ways to use more structured data, such as defrecord
; for this example we'll do something very simple, we'll use a map

(def phil-contact
  {:email "philip.s.doctor_dontspamme@gmail.com"
   :first-name "Philip"
   :last-name "Doctor"})

(def dance-commander-contact
  {:email "obey.the.dance.commander@gmail.com"
   :first-name "Dance"
   :last-name "Commander"})

(def some-guy-contact
  {:email "some.guy@yahoo.com"
   :first-name "Some"
   :last-name "Guy"})


; a vector is an ordered collection, allow for random access, etc it is written
; like this [:a :b] alternatively we can use a list, lists look like this '(:a :b)
; or a set like this #{:a :b} these are the basic clojure data types (in addition to map above)
; this is our stand in for a non-relational db
(def contacts [phil-contact dance-commander-contact some-guy-contact])

; explore your data
(pprint contacts)

; easy way to get an understanding of what's in a namespace
(keys (ns-publics 'clojure.pprint))
; learn more about something from the docstring
(doc clojure.pprint/formatter)

; explore macros with some ease....
(with-out-str (println "test"))
(macroexpand '(with-out-str (println "test")))

; on to the problem, we need to get at the e-mail address of all of our contacts and find our if they are gmail
; there's many ways to approach this problem, but the first is to consider how to iterate over our data

; first easy solution, we can use map
(map #(get % :email) contacts)

; but we only want to deal with gmail addresses, we *could* iterate again
(filter #(.contains % "@gmail.com") (map #(get % :email) contacts))

; can we do better if we're smarter with map?
; let's start by making a new function, this is getting hairy,
; first inclination is to do like this:
(defn email-if-gmail
  [contact]
  (let [email (get contact :email)]
    (if (.contains email "@gmail.com")
      email)))

; a few things, first passing larger data structures into a fn and then
; pulling them apart is SO common (i.e. first line of code is a (let [])
; that gets the vars we need) that instead we can use a destructuring bind
(defn email-if-gmail-better
  [{email :email}]
  (if (.contains email "@gmail.com")
    email))

; second does this even do what we want?
(map email-if-gmail-better contacts)

; so no, not really, we still have to iterate over the entire collection
; just sometimes we deal with nil

; try it from a different direction...
(defn gmail-contact?
  [{email :email}]
  (.contains email "@gmail.com"))

(filter gmail-contact? contacts)
; So here we only have one iteration, but the output is a collection of contacts, rather than just the e-mail
; in practice, this is probably the solution I'd pick, and then deal with destructuring the data again in my
; next function...

(map #(str "Dear " (:email %) " please read our e-mail.") (filter gmail-contact? contacts))

; however, it's worth looking at,
; how else can we handle iteration?

; let's take a look at loop ... recur, it's a tail call optimized form that makes recursive calls simple
(loop [i 5]
  (println i)
  (if (> i 0)
    (recur (- i 1))))

; so we can do this, although we're building an intermediate collection here
(defn gmail? [email]
  (.contains email "@gmail.com"))

(loop [[contact & remaining] contacts contacts-to-email []]
  (let [email (:email contact)
        new-contacts-to-email
          (if (gmail? email)
            (conj contacts-to-email email)
            contacts-to-email)]
    (if (empty? remaining)
      new-contacts-to-email
      (recur remaining new-contacts-to-email))))


; another option, if you're feeling imperative, and this might be an appropriate choice here given
; that all we want is side effects....

(doseq [contact contacts]
  (let [email (:email contact)]
    (if (gmail? email)
      (println (str "I'm a stand in for the email function, emailing " email)))))



