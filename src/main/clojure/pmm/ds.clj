(ns pmm.ds
  (:require [clojure.java.jdbc :as jdbc]
            [clojure.contrib.java-utils :as java-utils])
  (:import [com.atomikos.jdbc AtomikosDataSourceBean]))

(def pmm-ds (doto (AtomikosDataSourceBean.)
                (.setUniqueResourceName "pmm-ds")
                (.setXaDataSourceClassName "org.hsqldb.jdbc.pool.JDBCXADataSource")
                (.setXaProperties 
                  (java-utils/as-properties {"user" "sa" 
                                             "password" "" 
                                             "url" "jdbc:hsqldb:mem:pmm"}))
                (.setPoolSize 10)))

(def pmm-db {:datasource pmm-ds})

;; create database tables
(defn create-tables []
  (jdbc/with-connection
    pmm-db
    (jdbc/create-table
      :customer
      [:id :bigint "GENERATED BY DEFAULT AS IDENTITY" "PRIMARY KEY"]
      [:firstname "varchar(255)"]
      [:lastname "varchar(255)"]
      [:email "varchar(255)"]
      [:zipcode "varchar(16)"])))

(create-tables)
