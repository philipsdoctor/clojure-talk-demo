(ns demo.core
  (:require [dommy.core :refer [listen! unlisten!]]
            [clojure.browser.repl :as repl])
  (:require-macros [dommy.macros :refer [sel1]]))

(defn alerter []
  (js/alert "hi!"))

(defn ^:export init []
  (when (and js/document
             (.-getElementById js/document))
    (do
      (repl/connect "http://localhost:9000/repl")
      (listen! (sel1 :#test-button) :click alerter))))

(set! (.-onload js/window) init)


;(in-ns 'demo.core)
;(unlisten! (sel1 :#test-button) :click alerter)
;(listen! (sel1 :#test-button) :click #(js/alert "change things!"))
;(.-innerText (sel1 :#test-button))
