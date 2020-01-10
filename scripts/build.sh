#!/bin/sh

mvn clean install spring-boot:repackage

docker build -t monitor .