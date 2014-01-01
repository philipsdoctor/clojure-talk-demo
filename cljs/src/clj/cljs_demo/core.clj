(ns cljs-demo.core
  (:use compojure.core)
  (:require [compojure.handler :as handler]
            [compojure.route :as route]
            [cljs-demo.templates.index :as indx]))

(defroutes app-routes
  (GET "/" [] (indx/index-template))
  (route/resources "/")
  (route/not-found "Page non found"))

(def handler
  (handler/site app-routes))
