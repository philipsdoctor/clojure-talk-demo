(ns cljs-demo.templates.index
  (:require [hiccup.core :refer [html]]
            [hiccup.page :as hpage]))

(defn index-template []
  (html [:html
          [:head (hpage/include-js "/js/demo.js")]
          [:body
            [:h1 "Hello World!"]
            [:button {:id "test-button"} "Click me!"]]]))
