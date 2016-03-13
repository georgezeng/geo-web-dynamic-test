mkdir src/main/webapp/js/$1
browserify -t [ babelify --presets [ react ] ] src/$1/js/$2/main.js -o src/main/webapp/js/$1/$2-react.js
browserify -g uglifyify src/main/webapp/js/$1/$2-react.js > src/main/webapp/js/$1/$2.min.js
rm src/main/webapp/js/$1/$2-react.js