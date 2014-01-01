(defproject cljs "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :source-paths ["src/clj"]

  :dependencies [[org.clojure/clojure "1.5.1"]
                 [compojure "1.1.5"]
                 [hiccup "1.0.4"]
                 [prismatic/dommy "0.1.1"]]

  :plugins [[lein-cljsbuild "0.3.2"]
            [lein-ring "0.8.5"]]

  ;; ring tasks configuration
  :ring {:handler cljs-demo.core/handler}

  ;; cljsbuild tasks configuration
  :cljsbuild {:builds
              [{:source-paths ["src/cljs"]

                :compiler {:output-to "resources/public/js/demo.js"
                           :optimizations :whitespace
                           :pretty-print true}}]})

