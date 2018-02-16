#!/bin/bash

cd api

mvn javadoc:javadoc

sitePath="$(pwd)/target/site/"

cd $HOME

rm -Rf gh-pages

git config --global user.email "travis@travis-ci.org"
git config --global user.name "travis-ci"
git clone --quiet --branch=gh-pages https://${GH_TOKEN}@github.com/ggam/java9-container gh-pages > /dev/null

cd gh-pages

git rm -rf .
cp -Rf $sitePath .
git add -f .
git commit -m "Updated site"
git push -fq origin gh-pages > /dev/null

echo -e "Published site.\n"
