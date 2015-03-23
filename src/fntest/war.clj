;; Copyright 2008-2015 Red Hat, Inc, and individual contributors.
;;
;; This is free software; you can redistribute it and/or modify it
;; under the terms of the GNU Lesser General Public License as
;; published by the Free Software Foundation; either version 2.1 of
;; the License, or (at your option) any later version.
;;
;; This software is distributed in the hope that it will be useful,
;; but WITHOUT ANY WARRANTY; without even the implied warranty of
;; MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
;; Lesser General Public License for more details.
;;
;; You should have received a copy of the GNU Lesser General Public
;; License along with this software; if not, write to the Free
;; Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
;; 02110-1301 USA, or see the FSF site: http://www.fsf.org.

(ns fntest.war
  (:require [immutant.deploy-tools.war :as war]
            [clojure.java.io           :as io]
            [leiningen.core.project    :as prj]
            [leiningen.core.classpath  :as cp]
            [fntest.util :refer (status)])
  (:import java.io.File))

(defn enable-dev [project]
  (assoc project :dev? true))

(defn enable-nrepl [project port-file]
  (if port-file
    (update-in project [:nrepl] merge
      {:start? true
       :port 0
       :port-file (.getAbsolutePath port-file)})
    project))

(defn set-classpath [project]
  (assoc project :classpath (cp/get-classpath project)))

(defn set-init [project]
  (if (:main project)
    (assoc project :init-fn (symbol (str (:main project)) "-main"))
    project))

(defn project->war
  [root & {:keys [port-file profiles]}]
  (let [project (prj/read
                  (.getAbsolutePath (io/file root "project.clj"))
                  (or profiles [:dev :test]))]
    (status "Creating war file"
      (war/create-war
        (File/createTempFile "fntest" ".war")
        (-> project
          set-classpath
          set-init
          enable-dev
          (enable-nrepl port-file))))))