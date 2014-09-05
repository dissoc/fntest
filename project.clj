(defproject org.immutant/fntest "0.5.6-SNAPSHOT"
  :description "A harness for running Immutant integration tests"
  :url "https://github.com/immutant/fntest"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[jboss-as-management "0.3.2"]
                 [org.clojure/tools.nrepl "0.2.1"]
                 [backtick "0.1.0"]
                 [bultitude "0.2.6"]]
  :profiles {:dev
             {:dependencies [[org.clojure/clojure "1.5.1"]]}}
  :signing {:gpg-key "BFC757F9"}
  :lein-release {:deploy-via :clojars})
